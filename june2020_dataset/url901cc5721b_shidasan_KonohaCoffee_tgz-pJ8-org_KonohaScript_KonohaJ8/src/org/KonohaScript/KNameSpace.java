/****************************************************************************
 * Copyright (c) 2012, the Konoha project authors. All rights reserved.
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ***************************************************************************/

package org.KonohaScript;

import java.util.ArrayList;
import java.util.HashMap;

import org.KonohaScript.SyntaxTree.TypedNode;

public final class KNameSpace implements KonohaParserConst {
	public Konoha KonohaContext;
	KNameSpace ParentNameSpace;
	ArrayList<KNameSpace> ImportedNameSpaceList;

	@SuppressWarnings("unchecked")
	KNameSpace(Konoha konoha, KNameSpace parent) {
		this.KonohaContext = konoha;
		this.ParentNameSpace = parent;

		if(parent != null) {
			ImportedTokenMatrix = new KFunc[KonohaChar.MAX];
			for (int i = 0; i < KonohaChar.MAX; i++) {
				if (parent.ImportedTokenMatrix[i] != null) {
					ImportedTokenMatrix[i] = parent.GetTokenFunc(i).Duplicate();
				}
			}
			if(parent.ImportedSymbolTable != null) {
				ImportedSymbolTable = (HashMap<String,Object>)parent.ImportedSymbolTable.clone();
			}
		}
	}

	// class
	public final KClass LookupTypeInfo(String ClassName) {
		try {
			return KonohaContext.LookupTypeInfo(Class.forName(ClassName));

		}
		catch(ClassNotFoundException e) {
		}
		return null;
	}

	public final KClass LookupTypeInfo(Class<?> ClassInfo) {
		return KonohaContext.LookupTypeInfo(ClassInfo);
	}

	KFunc MergeFunc(KFunc f, KFunc f2) {
		if (f == null)
			return f2;
		if (f2 == null)
			return f;
		return f.Merge(f2);
	}

	KFunc[] DefinedTokenMatrix;
	KFunc[] ImportedTokenMatrix;

	KFunc GetDefinedTokenFunc(int kchar) {
		return (DefinedTokenMatrix != null) ? DefinedTokenMatrix[kchar] : null;
	}

	KFunc GetTokenFunc(int kchar) {
		if (ImportedTokenMatrix == null) {
			return null;
		}
		if (ImportedTokenMatrix[kchar] == null) {
			KFunc func = null;
			if (ParentNameSpace != null) {
				func = ParentNameSpace.GetTokenFunc(kchar);
			}
			func = MergeFunc(func, GetDefinedTokenFunc(kchar));
			assert (func != null);
			ImportedTokenMatrix[kchar] = func;
		}
		return ImportedTokenMatrix[kchar];
	}

	public void AddTokenFunc(String keys, Object callee, String name) {
		if (DefinedTokenMatrix == null) {
			DefinedTokenMatrix = new KFunc[KonohaChar.MAX];
		}
		if (ImportedTokenMatrix == null) {
			ImportedTokenMatrix = new KFunc[KonohaChar.MAX];
		}
		for (int i = 0; i < keys.length(); i++) {
			int kchar = KonohaChar.JavaCharToKonohaChar(keys.charAt(i));
			DefinedTokenMatrix[kchar] = KFunc.NewFunc(callee, name, DefinedTokenMatrix[kchar]);
			ImportedTokenMatrix[kchar] = KFunc.NewFunc(callee, name, GetTokenFunc(kchar));
			//KonohaDebug.P("key="+kchar+", " + name + ", " + GetTokenFunc(kchar));
		}
	}

	public ArrayList<KToken> Tokenize(String text, long uline) {
		return new KTokenizer(this, text, uline).Tokenize();
	}

	static final String MacroPrefix = "@$";  // FIXME: use different symbol tables
	
	KFunc GetDefinedMacroFunc(String Symbol) {
		if(DefinedSymbolTable != null) {
			Object object = DefinedSymbolTable.get(MacroPrefix + Symbol);
			return (object instanceof KFunc) ? (KFunc)object : null;
		}
		return null;
	}

	KFunc GetMacroFunc(String Symbol) {		
		Object o = GetSymbol(MacroPrefix + Symbol);
		return (o instanceof KFunc) ? (KFunc) o : null;
	}

	public void AddMacroFunc(String Symbol, Object Callee, String MethodName) {
		DefineSymbol(MacroPrefix + Symbol, new KFunc(Callee, MethodName, null));
	}

	HashMap<String, Object> DefinedSymbolTable;
	HashMap<String, Object> ImportedSymbolTable;

	Object GetDefinedSymbol(String symbol) {
		return (DefinedSymbolTable != null) ? DefinedSymbolTable.get(symbol) : null;
	}

	public Object GetSymbol(String symbol) {
		return ImportedSymbolTable.get(symbol);
	}

	public void DefineSymbol(String Symbol, Object Value) {
		if (DefinedSymbolTable == null) {
			DefinedSymbolTable = new HashMap<String, Object>();
		}
		DefinedSymbolTable.put(Symbol, Value);
		if (ImportedSymbolTable == null) {
			ImportedSymbolTable = new HashMap<String, Object>();
		}
		ImportedSymbolTable.put(Symbol, Value);
	}

	public KSyntax GetSyntax(String symbol) {
		Object o = GetSymbol(symbol);
		return (o instanceof KSyntax) ? (KSyntax) o : null;
	}

	public void AddSyntax(KSyntax Syntax) {
		Syntax.PackageNameSpace = this;
		Syntax.ParentSyntax = GetSyntax(Syntax.SyntaxName);
		DefineSymbol(Syntax.SyntaxName, Syntax);
	}

	public void DefineSyntax(String SyntaxName, int flag, Object Callee, String ParseMethod, String TypeMethod) {
		AddSyntax(new KSyntax(SyntaxName, flag, Callee, ParseMethod, TypeMethod));
	}

	public void ImportNameSpace(KNameSpace ns) {
		if(ImportedNameSpaceList == null) {
			ImportedNameSpaceList = new ArrayList<KNameSpace>();
			ImportedNameSpaceList.add(ns);
		}
		if (ImportedTokenMatrix == null) {
			ImportedTokenMatrix = new KFunc[KonohaChar.MAX];
		}

		if(ns.DefinedTokenMatrix != null) {
			for (int i = 0; i < KonohaChar.MAX; i++) {
				if (ns.DefinedTokenMatrix[i] != null) {
					ImportedTokenMatrix[i] = MergeFunc(GetTokenFunc(i), ns.DefinedTokenMatrix[i]);
				}
			}
		}
		// if(ns.DefinedSymbolTable != null) {
		// Set<Entry<String,Object>> data = DefinedSymbolTable.entrySet();
		// }
	}

	public int PreProcess(ArrayList<KToken> tokenList, int BeginIdx, int EndIdx, ArrayList<KToken> BufferList) {
		return new LexicalConverter(this, /*TopLevel*/true, /*SkipIndent*/false).Do(tokenList, BeginIdx, EndIdx, BufferList);
	}

	// TypedNode Type(KGamma gma, UntypedNode node) {
	// return node.Syntax.InvokeTypeFunc(gma, node);
	// }


	String GetSourcePosition(long uline) {
		return "(eval:" + (int) uline + ")";
	}

	public void Message(int Level, KToken Token, String Message) {
		if (!Token.IsErrorToken()) {
			if (Level == Error) {
				Message = "(error) " + GetSourcePosition(Token.uline) + " " + Message;
				Token.SetErrorMessage(Message);
			} else if (Level == Warning) {
				Message = "(warning) " + GetSourcePosition(Token.uline) + " " + Message;
			} else if (Level == Info) {
				Message = "(info) " + GetSourcePosition(Token.uline) + " " + Message;
			}
			System.out.println(Message);
		}
	}
		
	public void Eval(String text, long uline) {
		System.out.println("Eval: " + text);
		ArrayList<KToken> BufferList = Tokenize(text, uline);
		int next = BufferList.size();
		PreProcess(BufferList, 0, next, BufferList);
		UntypedNode UNode = UntypedNode.ParseNewNode2(this, null, BufferList, next, BufferList.size(), AllowEmpty);
		System.out.println("untyped tree: " + UNode);
//		if(UNode != null) {
//			TypedNode TNode = TypedNode.Type(KGamma, UNode, null);
//			TNode.Eval();
//		}
	}
}

class KTokenizer implements KonohaParserConst {
	KNameSpace ns;
	String SourceText;
	long CurrentLine;
	ArrayList<KToken> SourceList;

	KTokenizer(KNameSpace ns, String text, long CurrentLine) {
		this.ns = ns;
		this.SourceText = text;
		this.CurrentLine = CurrentLine;
		this.SourceList = new ArrayList<KToken>();
	}

	int TokenizeFirstToken(ArrayList<KToken> tokenList) {
		return 0;
	}

	int StampLine(int StartIdx) {
		for (int i = StartIdx; i < SourceList.size(); i++) {
			KToken token = SourceList.get(i);
			if (token.ResolvedSyntax == KSyntax.IndentSyntax) {
				CurrentLine = CurrentLine + 1;
			}
			token.uline = CurrentLine;
		}
		return SourceList.size();
	}
	
	int DispatchFunc(int KonohaChar, int pos) {
		KFunc FuncStack = ns.GetTokenFunc(KonohaChar);
		int UnusedIdx = SourceList.size();
		while (FuncStack != null) {
			int NextIdx = FuncStack.InvokeTokenFunc(ns, SourceText, pos, SourceList);
			if (NextIdx != -1) {
				UnusedIdx = StampLine(UnusedIdx);
				return NextIdx;
			}
			FuncStack = FuncStack.Pop();
		}
		KToken Token = new KToken(SourceText.substring(pos));
		Token.uline = CurrentLine;
		SourceList.add(Token);
		ns.Message(Error, Token, "undefined token: " + Token.ParsedText);
		return SourceText.length();
	}

	ArrayList<KToken> Tokenize() {
		int pos = 0, len = SourceText.length();
		pos = TokenizeFirstToken(SourceList);
		while (pos < len) {
			int kchar = KonohaChar.JavaCharToKonohaChar(SourceText.charAt(pos));
			int pos2 = DispatchFunc(kchar, pos);
			if (!(pos < pos2))
				break;
			pos = pos2;
		}
		//KToken.DumpTokenList(SourceList);
		return SourceList;
	}

}
