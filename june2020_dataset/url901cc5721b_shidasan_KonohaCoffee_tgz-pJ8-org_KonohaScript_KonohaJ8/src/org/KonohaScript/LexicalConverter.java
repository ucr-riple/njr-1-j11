package org.KonohaScript;

import java.util.ArrayList;

public final class LexicalConverter implements KonohaParserConst {
	
	public boolean TopLevel;
	public boolean SkipIndent;
	int LastIndent;
	
	public LexicalConverter(KNameSpace ns, boolean TopLevel, boolean SkipIndent) {
		this.ns = ns;
		this.TopLevel = TopLevel;
		this.SkipIndent = SkipIndent;
		LastIndent = 0;
	}

	KNameSpace ns;
	public KSyntax GetSyntax(String Symbol) {
		return ns.GetSyntax(Symbol);
	}
	
	public void ResolveTokenSyntax(KToken Token) {
		Token.ResolvedObject = ns.GetSymbol(Token.ParsedText);
		if(Token.ResolvedObject == null) {
			Token.ResolvedSyntax = ns.GetSyntax("$Symbol");
		}
		else if(Token.ResolvedObject instanceof KClass) {
			Token.ResolvedSyntax = ns.GetSyntax("$Type");
		}
		else if(Token.ResolvedObject instanceof KSyntax) {
			Token.ResolvedSyntax = (KSyntax)Token.ResolvedObject;
		}
		else {
			Token.ResolvedSyntax = KSyntax.ConstSyntax;
		}
	}

	static int Indent(String Text) {
		int indent = 0;
		for(int i = 0; i < Text.length(); i++) {
			char ch = Text.charAt(i);
			if(ch == '\t') {
				indent = indent + 4; continue;
			}
			if(ch == ' ') {
				indent = indent + 1; continue;
			}
			break;
		}
		return indent;
	}
	
	public int Do(ArrayList<KToken> SourceList, int BeginIdx, int EndIdx, ArrayList<KToken> BufferList) {
		int c = BeginIdx;
		while (c < EndIdx) {
			KToken Token = SourceList.get(c);
			if (Token.ResolvedSyntax == null) {
				KFunc Macro = ns.GetMacroFunc(Token.ParsedText);
				//KonohaDebug.P("symbol='"+Token.ParsedText+"', macro="+Macro);
				if (Macro != null) {
					int nextIdx = Macro.InvokeMacroFunc(this, SourceList, c, EndIdx, BufferList);
					if (nextIdx == BreakPreProcess) {
						return c + 1;
					}
					c = nextIdx;
					continue;
				}
				ResolveTokenSyntax(Token);
			}
			assert (Token.ResolvedSyntax != null);
			c = c + 1;
			if(Token.ResolvedSyntax == KSyntax.IndentSyntax) {
				if(this.SkipIndent) continue;
				LastIndent = Indent(Token.ParsedText);
			}
			BufferList.add(Token);
		}
		return c;
	}

}
