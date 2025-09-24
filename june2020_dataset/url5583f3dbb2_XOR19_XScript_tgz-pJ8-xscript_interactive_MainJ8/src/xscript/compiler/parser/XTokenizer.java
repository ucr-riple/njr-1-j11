package xscript.compiler.parser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.tools.DiagnosticListener;

import xscript.compiler.XDiagnostic;
import xscript.compiler.XFileReader;
import xscript.compiler.XPosition;
import xscript.compiler.treemaker.XKeyword;

public class XTokenizer implements Iterator<XToken> {

	private static final int EOF = -1;
	
	private XFileReader fileReader;
	private DiagnosticListener<String> diagnosticListener;
	
	private XPosition start;
	private XToken next;
	private XToken token;
	private int c;
	private int spaces;
	private int newLines;
	private String doc=null;
	private List<XDiagnostic> diagnostics = new ArrayList<XDiagnostic>();
	
	private List<XToken> marks = new ArrayList<XToken>();
	private List<Integer> pos = new ArrayList<Integer>();
	private int acPos;
	
	public XTokenizer(XFileReader fileReader, DiagnosticListener<String> diagnosticListener) {
		this.fileReader = fileReader;
		this.diagnosticListener = diagnosticListener;
		nextChar();
	}
	
	public XToken getToken(){
		return token;
	}
	
	@Override
	public XToken next(){
		if(next==null){
			hasNext();
		}
		token=next;
		next=null;
		return token;
	}

	@Override
	public boolean hasNext() {
		if(next==null){
			if(token!=null && token.kind==XTokenKind.EOF){
				next = token;
				return false;
			}else{
				next = tryGetNext();
			}
		}
		return next.kind!=XTokenKind.EOF;
	}

	private void nextChar(){
		c = fileReader.read();
	}
	
	private XToken tryGetNext(){
		if(!pos.isEmpty() && marks.size()>acPos){
			return marks.get(acPos++);
		}else if(pos.isEmpty() && !marks.isEmpty()){
			return marks.remove(0);
		}
		XToken token = tryGetNext2();
		if(!pos.isEmpty()){
			marks.add(token);
			acPos++;
		}
		return token;
	}
	
	private XToken tryGetNext2(){
		spaces = 0;
		newLines = 0;
		while(true){
			while(c==' '||c=='\t'||c=='\r'||c=='\n'){
				if(c==' '||c=='\t'){
					spaces++;
				}else if(c=='\n'){
					newLines++;
				}
				nextChar();
			}
			start = fileReader.getPosition();
			if(c==EOF){
				return makeToken(XTokenKind.EOF, null);
			}else if(c=='"' || c=='\''){
				return makeString(c=='\'');
			}else if(charOkForRadix(10, c)){
				return makeNumber(false);
			}else if(Character.isAlphabetic((char)c) || c=='_'){
				return makeIdent();
			}else if(c=='/'){
				nextChar();
				if(c=='/' || c=='*'){
					readLineComment(c=='*');
				}else{
					return makeToken(XTokenKind.KEYWORD, XKeyword.DIV);
				}
			}else{
				if(c=='.'){
					nextChar();
					if(charOkForRadix(10, c)){
						return makeNumber(true);
					}
					return makeToken(XTokenKind.KEYWORD, XKeyword.DOT);
				}else{
					XKeyword kw = XKeyword.getKeyword(Character.toString((char)c));
					if(kw!=null){
						nextChar();
						return makeToken(XTokenKind.KEYWORD, kw);
					}else{
						addDiagnostic2("char.unknown", (char)c);
						nextChar();
					}
				}
			}
		}
	}
	
	private XToken makeString(boolean makeChar){
		nextChar();
		String string = "";
		char e = makeChar?'\'':'"';
		while(c!=e && c!=EOF){
			if(c=='\\'){
				nextChar();
				if(c=='b'){
					string += '\b';
				}else if(c=='t'){
					string += '\t';
				}else if(c=='n'){
					string += '\n';
				}else if(c=='r'){
					string += '\r';
				}else if(c=='f'){
					string += '\f';
				}else if(c=='"'){
					string += '\"';
				}else if(c=='\''){
					string += '\'';
				}else if(c=='\\'){
					string += '\\';
				}else{
					addDiagnostic((makeChar?"char":"string")+".invalid.escape", (char)c);
				}
			}else{
				string += (char)c;
			}
			nextChar();
		}
		if(c==EOF){
			addDiagnostic((makeChar?"char":"string")+".unexpected.eof");
		}else{
			nextChar();
			if(makeChar && string.length()!=1){
				if(string.length()==0){
					addDiagnostic("char.invalid.empty");
				}else{
					addDiagnostic("char.invalid.length");
				}
			}
		}
		if(makeChar){
			long dc = 0;
			if(string.length()>0){
				dc = string.charAt(0);
			}
			return makeToken(XTokenKind.INT, dc);
		}
		return makeToken(XTokenKind.STRING, string);
	}
	
	private static boolean charOkForRadix(int radix, int c){
		if(radix==2){
			return c=='0' || c=='1';
		}else if(radix==8){
			return c>='0' && c<='7';
		}else if(radix==10){
			return c>='0' && c<='9';
		}else if(radix==16){
			return (c>='a' && c<='f') || (c>='A' && c<='F') || (c>='0' && c<='9');
		}
		return false;
	}
	
	private String scannDigit(int radix){
		String number = "";
		while(charOkForRadix(radix, c) || c=='_'){
			if(c!='_'){
				number += (char)c;
			}
			nextChar();
		}
		return number;
	}
	
	private XToken makeNumber(boolean dot){
		int radix = 10;
		XTokenKind kind = XTokenKind.INT;
		String number = "";
		if(!dot){
			if(c=='0'){
				nextChar();
				if(c=='x' || c=='X'){
					nextChar();
					radix = 16;
				}else if(c=='b' || c=='B'){
					nextChar();
					radix = 2;
				}else{
					number = "0";
				}
			}
			number += scannDigit(radix);
		}
		if((c=='.' || dot) && radix==10){
			kind = XTokenKind.FLOAT;
			if(!dot){
				nextChar();
			}
			boolean nothing = number.isEmpty();
			if(nothing)
				number = "0";
			number += ".";
			String scann = scannDigit(radix);
			if(scann.isEmpty() && nothing){
				scann = "0";
				addDiagnostic("literal.single_dot");
			}
			number += scann;
		}
		if(radix==10 && (c=='e' || c=='E')){
			nextChar();
			number += "e";
			if(c=='+' || c=='-'){
				number += (char)c;
				nextChar();
			}
			String scann = scannDigit(radix);
			if(scann.isEmpty()){
				scann = "0";
				addDiagnostic("literal.empty_exponent");
			}
			number += scann;
		}
		if(c=='f' || c=='F' || c=='d' || c=='D'){
			kind = XTokenKind.FLOAT;
			nextChar();
		}else if(c=='l' || c=='L'){
			kind = XTokenKind.INT;
			nextChar();
		}
		try{
			if(kind==XTokenKind.INT){
				return makeToken(kind, Long.parseLong(number, radix));
			}else if(kind==XTokenKind.FLOAT){
				return makeToken(kind, Double.parseDouble(number));
			}
		}catch(NumberFormatException e){
			addDiagnostic("number.format", e.getMessage());
		}
		return makeToken(XTokenKind.INT, 0);
	}
	
	private XToken makeIdent(){
		String ident = "";
		while(Character.isAlphabetic((char)c)||charOkForRadix(10, c)||c=='_'){
			ident += (char)c;
			nextChar();
		}
		XKeyword kw = XKeyword.getKeyword(ident);
		if(kw!=null){
			return makeToken(XTokenKind.KEYWORD, kw);
		}
		return makeToken(XTokenKind.IDENT, ident);
	}
	
	private void readLineComment(boolean multiline){
		nextChar();
		if(multiline){
			boolean doc = c=='*';
			if(doc){
				nextChar();
			}
			if(c=='/'){
				nextChar();
			}else{
				String comment = "";
				while(c!=EOF){
					if(c=='*'){
						nextChar();
						if(c=='/')
							break;
						comment += '*';
					}
					comment += (char)c;
					if(c=='\n'){
						newLines++;
					}
					nextChar();
				}
				if(c!='/'){
					addDiagnostic("comment.unexpected.eof");
				}else{
					nextChar();
				}
				if(doc){
					if(this.doc==null){
						this.doc = comment;
					}else{
						this.doc += comment;
					}
				}
			}
		}else{
			while(c!='\n' && c!=EOF){
				nextChar();
			}
		}
	}
	
	private XToken makeToken(XTokenKind kind, Object data){
		XToken token = new XToken(kind, start, fileReader.getPosition().pos, spaces, newLines, data, doc);
		doc = null;
		for(XDiagnostic diagnostic:diagnostics){
			diagnostic.setEnd(token.end);
			diagnosticListener.report(diagnostic);
		}
		diagnostics.clear();
		return token;
	}
	
	private void addDiagnostic(String message, Object...args){
		diagnostics.add(new XDiagnostic(fileReader.getPosition(), start.pos, message, args));
	}
	
	private void addDiagnostic2(String message, Object...args){
		diagnosticListener.report(new XDiagnostic(fileReader.getPosition(), start.pos, start.pos, message, args));
	}
	
	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
	
	public void mark(){
		if(acPos==0 || marks.isEmpty()){
			acPos=1;
		}
		marks.add(acPos-1, token);
		pos.add(acPos);
	}
	
	public void apply(){
		pos.remove(pos.size()-1);
		if(pos.isEmpty()){
			marks.clear();
			acPos=0;
		}
	}
	
	public void reset(){
		acPos = pos.remove(pos.size()-1);
		if(pos.isEmpty()){
			token = marks.remove(0);
		}else{
			token = marks.get(acPos-1);
		}
	}
	
}
