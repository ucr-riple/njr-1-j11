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

package org.KonohaScript.GrammarSet;

import java.util.ArrayList;

import org.KonohaScript.*;
import org.KonohaScript.SyntaxTree.*;

public final class MiniKonoha implements KonohaParserConst {
	// Token

	public int WhiteSpaceToken(KNameSpace ns, String SourceText, int pos, ArrayList<KToken> ParsedTokenList) {
		for (; pos < SourceText.length(); pos++) {
			char ch = SourceText.charAt(pos);
			if(!Character.isWhitespace(ch)) {
				break;
			}
		}
		return pos;
	}

	public int IndentToken(KNameSpace ns, String SourceText, int pos, ArrayList<KToken> ParsedTokenList) {
		int LineStart = pos + 1;
		pos = pos + 1;
		for (; pos < SourceText.length(); pos++) {
			char ch = SourceText.charAt(pos);
			if(!Character.isWhitespace(ch)) {
				break;
			}
		}
		KToken Token = new KToken((LineStart < pos) ? SourceText.substring(LineStart, pos) : "");
		Token.ResolvedSyntax = KSyntax.IndentSyntax;
		ParsedTokenList.add(Token);			
		return pos;
	}
	
	public int SingleSymbolToken(KNameSpace ns, String SourceText, int pos, ArrayList<KToken> ParsedTokenList) {
		KToken Token = new KToken(SourceText.substring(pos, pos + 1));
		ParsedTokenList.add(Token);
		return pos + 1;
	}

	public int SymbolToken(KNameSpace ns, String SourceText, int pos, ArrayList<KToken> ParsedTokenList) {
		int start = pos;
		for (; pos < SourceText.length(); pos++) {
			char ch = SourceText.charAt(pos);
			if(!Character.isLetter(ch) && !Character.isDigit(ch) && ch != '_') {
				break;
			}
		}
		KToken Token = new KToken(SourceText.substring(start, pos));
		ParsedTokenList.add(Token);
		return pos;
	}

	public int MemberToken(KNameSpace ns, String SourceText, int pos, ArrayList<KToken> ParsedTokenList) {
		int start = pos + 1;
		for (; pos < SourceText.length(); pos++) {
			char ch = SourceText.charAt(pos);
			if(!Character.isLetter(ch) && !Character.isDigit(ch) && ch != '_') {
				break;
			}
		}
		KToken Token = new KToken(SourceText.substring(start, pos));
		Token.ResolvedSyntax = KSyntax.MemberSyntax;
		ParsedTokenList.add(Token);
		return pos;
	}

	public int NumberLiteralToken(KNameSpace ns, String SourceText, int pos, ArrayList<KToken> ParsedTokenList) {
		int start = pos;
		for (; pos < SourceText.length(); pos++) {
			char ch = SourceText.charAt(pos);
			if(!Character.isDigit(ch)) {
				break;
			}
		}
		KToken token = new KToken(SourceText.substring(start, pos));
		token.ResolvedSyntax = ns.GetSyntax("$IntegerLiteral");
		ParsedTokenList.add(token);
		return pos;
	}

	public int StringLiteralToken(KNameSpace ns, String SourceText, int pos, ArrayList<KToken> ParsedTokenList) {
		int start = pos + 1;
		char prev = '"';
		pos = start;
		while (pos < SourceText.length()) {
			char ch = SourceText.charAt(pos);
			if (ch == '"' && prev == '\\') {
				KToken token = new KToken(SourceText.substring(start, pos - start));
				token.ResolvedSyntax = ns.GetSyntax("$StringLiteral");
				ParsedTokenList.add(token);
				return pos + 1;
			}
			if (ch == '\n') {
				KToken token = new KToken(SourceText.substring(start, pos - start));
				ns.Message(Error, token, "expected \" to close the string literal");
				ParsedTokenList.add(token);
				return pos;
			}
			pos = pos + 1;
			prev = ch;
		}
		return pos;
	}

	// Macro

	public int OpenParenthesisMacro(LexicalConverter Lexer, ArrayList<KToken> SourceList, int BeginIdx, int EndIdx, ArrayList<KToken> BufferList) {
		ArrayList<KToken> GroupList = new ArrayList<KToken>();
		KToken BeginToken = SourceList.get(BeginIdx);
		GroupList.add(BeginToken);
		int nextIdx = Lexer.Do(SourceList, BeginIdx + 1, EndIdx, GroupList);
		KToken LastToken = GroupList.get(GroupList.size()-1);
		if (!LastToken.EqualsText(")")) { // ERROR
			LastToken.SetErrorMessage("must close )");
		}
		else {
			KToken GroupToken = new KToken("( ... )", BeginToken.uline);
			GroupToken.SetGroup(Lexer.GetSyntax("()"), GroupList);
			BufferList.add(GroupToken);
		}
		//KonohaDebug.P("BeginIdx=" + BeginIdx + ",nextIdx="+nextIdx + ",EndIdx="+EndIdx);
		return nextIdx;
	}

	public int CloseParenthesisMacro(LexicalConverter Lexer, ArrayList<KToken> SourceList, int BeginIdx, int EndIdx, ArrayList<KToken> BufferList) {
		KToken Token = SourceList.get(BeginIdx);
		if(BufferList.size() == 0 || !BufferList.get(0).EqualsText("(")) {
			Token.SetErrorMessage("mismatched )");
		}
		BufferList.add(Token);
		return BreakPreProcess;
	}

	public int OpenBraceMacro(LexicalConverter Lexer, ArrayList<KToken> SourceList, int BeginIdx, int EndIdx, ArrayList<KToken> BufferList) {
		ArrayList<KToken> GroupList = new ArrayList<KToken>();
		KToken BeginToken = SourceList.get(BeginIdx);
		GroupList.add(BeginToken);
		int nextIdx = Lexer.Do(SourceList, BeginIdx + 1, EndIdx, GroupList);
		KToken LastToken = GroupList.get(GroupList.size()-1);
		if (!LastToken.EqualsText("}")) { // ERROR
			LastToken.SetErrorMessage("must close }");
		}
		else {
			KToken GroupToken = new KToken("{ ... }", BeginToken.uline);
			GroupToken.SetGroup(Lexer.GetSyntax("{}"), GroupList);
			BufferList.add(GroupToken);
		}
		return nextIdx;
	}

	public int CloseBraceMacro(LexicalConverter Lexer, ArrayList<KToken> SourceList, int BeginIdx, int EndIdx, ArrayList<KToken> BufferList) {
		KToken Token = SourceList.get(BeginIdx);
		if(BufferList.size() == 0 || !BufferList.get(0).EqualsText("{")) {
			Token.SetErrorMessage("mismatched }");
		}
		BufferList.add(Token);
		return BreakPreProcess;
	}

	public int OpenCloseBraceMacro(LexicalConverter Lexer, ArrayList<KToken> SourceList, int BeginIdx, int EndIdx, ArrayList<KToken> BufferList) {
		int BraceLevel = 0;
		ArrayList<KToken> GroupList = new ArrayList<KToken>();
		for(int i = BeginIdx; i < EndIdx; i++) {
			KToken Token = SourceList.get(i);
			GroupList.add(Token);
			if(Token.EqualsText("{")) {
				BraceLevel++;
			}
			if(Token.EqualsText("}")) {
				BraceLevel--;
				if(BraceLevel == 0) {
					KToken GroupToken = new KToken("{ ... }", SourceList.get(BeginIdx).uline);
					GroupToken.SetGroup(Lexer.GetSyntax("${}"), GroupList);
					BufferList.add(GroupToken);
					return i+1;
				}
			}
		}
		SourceList.get(BeginIdx).SetErrorMessage("must close }");
		BufferList.add(SourceList.get(BeginIdx));
		return EndIdx;
	}

	public int OpenBracketMacro(LexicalConverter Lexer, ArrayList<KToken> SourceList, int BeginIdx, int EndIdx, ArrayList<KToken> BufferList) {
		ArrayList<KToken> GroupList = new ArrayList<KToken>();
		KToken BeginToken = SourceList.get(BeginIdx);
		GroupList.add(BeginToken);
		int nextIdx = Lexer.Do(SourceList, BeginIdx + 1, EndIdx, GroupList);
		KToken LastToken = GroupList.get(GroupList.size()-1);
		if (!LastToken.EqualsText("]")) { // ERROR
			LastToken.SetErrorMessage("must close ]");
		}
		else {
			KToken GroupToken = new KToken("[ ... ]", BeginToken.uline);
			GroupToken.SetGroup(Lexer.GetSyntax("[]"), GroupList);
			BufferList.add(GroupToken);
		}
		return nextIdx;
	}

	public int CloseBracketMacro(LexicalConverter Lexer, ArrayList<KToken> SourceList, int BeginIdx, int EndIdx, ArrayList<KToken> BufferList) {
		KToken Token = SourceList.get(BeginIdx);
		if(BufferList.size() == 0 || !BufferList.get(0).EqualsText("[")) {
			Token.SetErrorMessage("mismatched ]");
		}
		BufferList.add(Token);
		return BreakPreProcess;
	}

	public int MergeOperatorMacro(LexicalConverter Lexer, ArrayList<KToken> SourceList, int BeginIdx, int EndIdx, ArrayList<KToken> BufferList) {
		KToken Token = SourceList.get(BeginIdx);
		if(BufferList.size() > 0) {
			KToken PrevToken = BufferList.get(BufferList.size()-1);			
			if(PrevToken.ResolvedSyntax != null && PrevToken.uline == Token.uline) {
//				if(!Character.isLetter(PrevToken.ParsedText.charAt(0))) {
					String MergedOperator = PrevToken.ParsedText + Token.ParsedText;
					KSyntax Syntax = Lexer.GetSyntax(MergedOperator);
					if(Syntax != null) {
						PrevToken.ResolvedSyntax = Syntax;
						PrevToken.ParsedText = MergedOperator;
						return BeginIdx + 1;
					}
//				}
			}
		}
		Lexer.ResolveTokenSyntax(Token);
		BufferList.add(Token);
		return BeginIdx + 1;
	}
	
	// Parse

	public int ParseSymbol(UntypedNode Node, ArrayList<KToken> TokenList, int BeginIdx, int EndIdx, int ParseOption) {
		return BeginIdx+1;
	}

	public int ParseUniaryOperator(UntypedNode Node, ArrayList<KToken> TokenList, int BeginIdx, int EndIdx, int ParseOption) {
		int NextIdx = EndIdx;
		for(int i = BeginIdx+1; i < EndIdx; i++) {
			KToken Token = TokenList.get(i);
			if(Token.ResolvedSyntax.IsBinaryOperator() || Token.ResolvedSyntax.IsSuffixOperator() || Token.ResolvedSyntax.IsDelim()) {
				NextIdx = i;
				break;
			}
		}
		Node.SetAtNode(0, UntypedNode.ParseNewNode2(Node.NodeNameSpace, null, TokenList, BeginIdx+1, NextIdx, 0));
		return NextIdx;
	}

	public int ParseParenthesis(UntypedNode Node, ArrayList<KToken> TokenList, int BeginIdx, int EndIdx, int ParseOption) {
		KToken GroupToken = TokenList.get(BeginIdx);
		UntypedNode BodyNode = Node.GetSuffixBodyNode();
		if(BodyNode != null) {
			ArrayList<KToken> GroupList = GroupToken.GetGroupList();
			BodyNode.AppendTokenList(",", GroupList, 1, GroupList.size()-1, AllowEmpty|CreateNullNode);
			BodyNode.Syntax = KSyntax.ApplyMethodSyntax;
		}
		else {
			Node.SetAtNode(0, UntypedNode.ParseGroup(Node.NodeNameSpace, GroupToken, 0));
		}
		return BeginIdx+1;
	}

//	public int TypeCheck_Getter()
//	{
//		VAR_TypeCheck2(stmt, expr, ns, reqc);
//		kNode *self = KLIB TypeCheckNodeAt(kctx, expr, 1, ns, KClass_INFER, 0);
//		if(kNode_IsError(self)) {
//			KReturn(self);
//		}
//		kToken *fieldToken = expr->NodeList->TokenItems[0];
//		ksymbol_t fn = fieldToken->symbol;
//		kMethod *mtd = KLIB kNameSpace_GetGetterMethodNULL(kctx, ns, KClass_(self->typeAttr), fn);
//		if(mtd != NULL) {
//			KReturn(KLIB TypeCheckMethodParam(kctx, mtd, expr, ns, reqc));
//		}
//		else {  // dynamic field    o.name => o.get(name)
//			kparamtype_t p[1] = {{KType_Symbol}};
//			kparamId_t paramdom = KLIB Kparamdom(kctx, 1, p);
//			mtd = KLIB kNameSpace_GetMethodBySignatureNULL(kctx, ns, KClass_(self->typeAttr), KMethodNameAttr_Getter, paramdom, 1, p);
//			if(mtd != NULL) {
//				KLIB kArray_Add(kctx, expr->NodeList, new_UnboxConstNode(kctx, ns, KType_Symbol, KSymbol_Unmask(fn)));
//				KReturn(KLIB TypeCheckMethodParam(kctx, mtd, expr, ns, reqc));
//			}
//		}
//		KLIB MessageNode(kctx, stmt, fieldToken, ns, ErrTag, "undefined field: %s", kString_text(fieldToken->text));
//	}

	// If Statement
	public final static int IfCond = 0;
	public final static int IfThen = 1;
	public final static int IfElse = 2;
	
	public int ParseIf(UntypedNode UNode, ArrayList<KToken> TokenList, int BeginIdx, int EndIdx, int ParseOption) {
		int NextIdx = UNode.MatchCond(IfCond, TokenList, BeginIdx + 1, EndIdx, ParseOption);
		NextIdx = UNode.MatchSingleBlock(IfThen, TokenList, NextIdx, EndIdx, ParseOption);
		int NextIdx2 = UNode.MatchKeyword(-1, "else", TokenList, NextIdx, EndIdx, AllowEmpty|AllowSkip);
		if(NextIdx == NextIdx2 && NextIdx != -1) {  // skiped
			UNode.SetAtNode(IfElse, UntypedNode.NewNullNode(UNode.NodeNameSpace, TokenList, NextIdx2));
		}
		else {
			NextIdx2 = UNode.MatchSingleBlock(IfElse, TokenList, NextIdx2, EndIdx, ParseOption);
		}
		return NextIdx2;
	}

	public TypedNode TypeIf(KGamma Gamma, UntypedNode UNode, KClass TypeInfo) {
		TypedNode CondNode = UNode.TypeNodeAt(IfCond, Gamma, UNode.NodeNameSpace.KonohaContext.BooleanType, 0);
		if(CondNode.IsError()) return CondNode;
		TypedNode ThenNode = UNode.TypeNodeAt(IfThen, Gamma, TypeInfo, 0);
		if(ThenNode.IsError()) return ThenNode;
		TypedNode ElseNode = UNode.TypeNodeAt(IfElse, Gamma, ThenNode.TypeInfo, 0);
		if(ElseNode.IsError()) return ElseNode;
		return new IfNode(ThenNode.TypeInfo, CondNode, ThenNode, ElseNode);
	}
		
	// Return Statement

	public int ParseReturn(UntypedNode UNode, ArrayList<KToken> TokenList, int BeginIdx, int EndIdx, int ParseOption) {
		int NextIdx = UntypedNode.FindDelim(TokenList, BeginIdx, EndIdx);
		UNode.AddParsedNode(UntypedNode.ParseNewNode2(UNode.NodeNameSpace, null, TokenList, BeginIdx + 1, NextIdx, AllowEmpty|CreateNullNode));
		return NextIdx;
	}

	public TypedNode TypeReturn(KGamma Gamma, UntypedNode UNode, KClass TypeInfo) {
		TypedNode CondNode = UNode.TypeNodeAt(IfCond, Gamma, KClass.BooleanType, 0);
		if(CondNode.IsError()) return CondNode;
		TypedNode ThenNode = UNode.TypeNodeAt(IfThen, Gamma, TypeInfo, 0);
		if(CondNode.IsError()) return ThenNode;
		TypedNode ElseNode = UNode.TypeNodeAt(IfElse, Gamma, ThenNode.TypeInfo, TypeCheckPolicy_AllowEmpty);
		if(CondNode.IsError()) return ThenNode;
		return new IfNode(ThenNode.TypeInfo, CondNode, ThenNode, CondNode);
	}

	public final static int VarDeclName  = 0;
	public final static int VarDeclValue = 1;

	public int ParseVarDeclIteration(UntypedNode UNode, KToken TypeToken, ArrayList<KToken> TokenList, int BeginIdx, int EndIdx, int ParseOption) {
		int NextIdx = UNode.MatchSyntax(VarDeclName, "$Symbol", TokenList, BeginIdx, EndIdx, ParseOption);
		int SkipIdx = UNode.MatchKeyword(-1, "=", TokenList, NextIdx, EndIdx, AllowEmpty|AllowSkip);
		if(NextIdx < SkipIdx) {
			for(NextIdx = SkipIdx; NextIdx < EndIdx; NextIdx++) {
				KToken DelimToken = TokenList.get(NextIdx);
				if(DelimToken.EqualsText(",")) {
					UNode.MatchExpression(VarDeclValue, TokenList, SkipIdx, NextIdx, 0);
					UntypedNode NewNode = new UntypedNode(UNode.NodeNameSpace, TypeToken);
					UNode.LinkNode(NewNode);
					return ParseVarDeclIteration(NewNode, TypeToken, TokenList, NextIdx+1, EndIdx, 0);
				}
				if(DelimToken.ResolvedSyntax.IsDelim()) {
					UNode.MatchExpression(VarDeclValue, TokenList, SkipIdx, NextIdx, 0);
					return NextIdx+1;
				}
			}
		}
		if(!(SkipIdx < EndIdx)) return EndIdx;
		if(SkipIdx != -1 ) {
			KToken DelimToken = TokenList.get(SkipIdx);
			if(DelimToken.EqualsText(",")) {
				UNode.SetAtNode(VarDeclValue, UntypedNode.NewNullNode(UNode.NodeNameSpace, TokenList, SkipIdx));
				UntypedNode NewNode = new UntypedNode(UNode.NodeNameSpace, TypeToken);
				UNode.LinkNode(NewNode);
				return ParseVarDeclIteration(NewNode, TypeToken, TokenList, SkipIdx+1, EndIdx, 0);
			}
			if(DelimToken.ResolvedSyntax.IsDelim()) {
				UNode.SetAtNode(VarDeclValue, UntypedNode.NewNullNode(UNode.NodeNameSpace, TokenList, SkipIdx));
				return SkipIdx+1;
			}
			if(UntypedNode.IsAllowNoMatch(ParseOption)) {
				return NoMatch;
			}
			UNode.ReportError(DelimToken, "unexpected token: " + DelimToken.ParsedText, ParseOption);
			return EndIdx;
		}
		return NoMatch;
	}
	
	public int ParseVarDecl(UntypedNode UNode, ArrayList<KToken> TokenList, int BeginIdx, int EndIdx, int ParseOption) {
		KToken.DumpTokenList(0, "ParseVarDecl", TokenList, BeginIdx, EndIdx);
		int SymbolIdx = BeginIdx + 1;
		int AfterSymbolIdx  = UNode.MatchSyntax(-1, "$Symbol", TokenList, SymbolIdx, EndIdx, ParseOption);
		int NextIdx = UNode.MatchKeyword(-1, "()", TokenList, AfterSymbolIdx, EndIdx, AllowSkip|ParseOption);
		System.out.printf("SymbolIdx=%d,  AfterSymbolIdx=%d, NextIdx=%d, EndIdx=%d\n", SymbolIdx, AfterSymbolIdx, NextIdx, EndIdx);
		if(NextIdx == BeginIdx + 3) return -1;
		return ParseVarDeclIteration(UNode, TokenList.get(BeginIdx), TokenList, BeginIdx + 1, EndIdx, ParseOption);
	}

	public TypedNode TypeVarDecl(KGamma Gamma, UntypedNode UNode, KClass TypeInfo) {
		assert(UNode.KeyToken.ResolvedSyntax == KSyntax.TypeSyntax);
		return null; // TODO
	}
	
	public final static int MethodDeclReturn = 0;
	public final static int MethodDeclClass  = 1;
	public final static int MethodDeclName = 2;
	public final static int MethodDeclBlock  = 3;
	public final static int MethodDeclParam  = 4;
	
	public int ParseMethodDecl(UntypedNode UNode, ArrayList<KToken> TokenList, int BeginIdx, int EndIdx, int ParseOption) {
		int NextIdx = UNode.MatchSyntax(MethodDeclClass, "$Type", TokenList, BeginIdx + 1, EndIdx, AllowEmpty|AllowSkip);
		if(NextIdx > BeginIdx + 1) {
			NextIdx = UNode.MatchKeyword(-1, ".", TokenList, NextIdx, EndIdx, 0);
		}
		else {
			NextIdx = BeginIdx + 1;
			UNode.SetAtNode(MethodDeclClass, UntypedNode.NewNullNode(UNode.NodeNameSpace, TokenList, NextIdx));
		}
		NextIdx = UNode.MatchSyntax(MethodDeclName, "$Symbol", TokenList, NextIdx, EndIdx, ParseOption);
		NextIdx = UNode.MatchKeyword(-1, "()", TokenList, NextIdx, EndIdx, ParseOption);
		if(NextIdx == -1 || UNode.Syntax.IsError()) return NextIdx;
		NextIdx = UNode.MatchSyntax(MethodDeclBlock, "{}", TokenList, NextIdx, EndIdx, ParseOption);
		return NextIdx;
	}
	
	public void LoadDefaultSyntax(KNameSpace ns) {
		ns.DefineSymbol("void",    ns.KonohaContext.VoidType); // FIXME
		ns.DefineSymbol("boolean", ns.KonohaContext.BooleanType);
		ns.DefineSymbol("int",     ns.LookupTypeInfo(Integer.class));
		ns.DefineSymbol("String",  ns.LookupTypeInfo(String.class));
		ns.DefineSymbol("true",    new Boolean(true));
		ns.DefineSymbol("false",   new Boolean(false));
				
		ns.AddTokenFunc(" \t", this, "WhiteSpaceToken");		
		ns.AddTokenFunc("\n", this, "IndentToken");		
		ns.AddTokenFunc("(){}[]<>,;+-*/%=&|!", this, "SingleSymbolToken");
		ns.AddTokenFunc("Aa", this, "SymbolToken");
		ns.AddTokenFunc(".", this, "MemberToken");
		ns.AddTokenFunc("\"", this, "StringLiteralToken");
		ns.AddTokenFunc("1", this, "NumberLiteralToken");
		
		// Macro
		ns.AddMacroFunc("(", this, "OpenParenthesisMacro");
		ns.AddMacroFunc(")", this, "CloseParenthesisMacro");
		ns.AddMacroFunc("{", this, "OpenBraceMacro");
		ns.AddMacroFunc("}", this, "CloseBraceMacro");
		ns.AddMacroFunc("[", this, "OpenBracketMacro");
		ns.AddMacroFunc("]", this, "CloseBracketMacro");
		ns.AddMacroFunc("=", this, "MergeOperatorMacro");
		ns.AddMacroFunc("&", this, "MergeOperatorMacro");
		ns.AddMacroFunc("|", this, "MergeOperatorMacro");
		//ns.AddSymbol(symbol, constValue);

		ns.DefineSyntax("*", BinaryOperator|Precedence_CStyleMUL, this, null, null);
		ns.DefineSyntax("/", BinaryOperator|Precedence_CStyleMUL, this, null, null);
		ns.DefineSyntax("%", BinaryOperator|Precedence_CStyleMUL, this, null, null);
		
		ns.DefineSyntax("+", Term|BinaryOperator|Precedence_CStyleADD, this, "ParseUniaryOperator", null);
		ns.DefineSyntax("-", Term|BinaryOperator|Precedence_CStyleADD, this, "ParseUniaryOperator", null);

		ns.DefineSyntax("<", BinaryOperator|Precedence_CStyleCOMPARE, this, null, null);
		ns.DefineSyntax("<=", BinaryOperator|Precedence_CStyleCOMPARE, this, null, null);
		ns.DefineSyntax(">", BinaryOperator|Precedence_CStyleCOMPARE, this, null, null);
		ns.DefineSyntax(">=", BinaryOperator|Precedence_CStyleCOMPARE, this, null, null);

		ns.DefineSyntax("==", BinaryOperator|Precedence_CStyleEquals, this, null, null);
		ns.DefineSyntax("!=", BinaryOperator|Precedence_CStyleEquals, this, null, null);

		ns.DefineSyntax("=", BinaryOperator|Precedence_CStyleAssign|LeftJoin, this, null, "TypeAssign");

		ns.DefineSyntax("&&", BinaryOperator|Precedence_CStyleAND, this, null, "TypeAssign");
		ns.DefineSyntax("||", BinaryOperator|Precedence_CStyleOR, this, null, "TypeAssign");
		ns.DefineSyntax("!",  Term, this, "ParseUniaryOperator", "TypeAssign");
		ns.DefineSyntax(";",  Precedence_CStyleDelim, this, null, null);

		ns.DefineSyntax("$Synbol", Term, this, "ParseSymbol", "TypeSymbol");
		ns.DefineSyntax("$Member", Precedence_CStyleSuffixCall, this, "ParseMember", "TypeMember");
		ns.DefineSyntax("()",      Term|Precedence_CStyleSuffixCall, this, "ParseMember", "TypeMember");
		ns.DefineSyntax("$StringLiteral",  Term, this, "ParseStrngLiteral", null);
		ns.DefineSyntax("$IntegerLiteral", Term, this, "ParseNumberLiteral", null);

		ns.DefineSyntax("$Symbol", Term, this, "ParseSymbol", "TypeSymbol");
		ns.DefineSyntax("$Const",  Term, this, "ParseConst", "TypeConst");
		
		ns.DefineSyntax("$Type", Term, this, "ParseTypeSymbol", "TypeTypeSymbol");
		ns.DefineSyntax("$Type", Term, this, "ParseMethodDecl", "TypeMethodDecl");
		ns.DefineSyntax("$Type", Term, this, "ParseVarDecl", "TypeVarDecl");

		ns.DefineSyntax("if", Term, this, "ParseIfNode", "TypeIfNode");
		ns.DefineSyntax("return", Term, this, "ParseReturnNode", "TypeReturnNode");

		DefineIntegerMethod(ns);
		
	}
	
	void DefineIntegerMethod (KNameSpace ns) {
		KClass BaseClass = ns.LookupTypeInfo(Integer.class);
		KParam BinaryParam = KParam.ParseOf(ns, "int int x");
		KParam UniaryParam = KParam.ParseOf(ns, "int");
		
		BaseClass.DefineMethod(ImmutableMethod|ConstMethod, "+", UniaryParam, this, "Plus");
		BaseClass.DefineMethod(ImmutableMethod|ConstMethod, "+", BinaryParam, this, "Add");
		BaseClass.DefineMethod(ImmutableMethod|ConstMethod, "-", UniaryParam, this, "Minus");
		BaseClass.DefineMethod(ImmutableMethod|ConstMethod, "-", BinaryParam, this, "Sub");
		
		if(KonohaDebug.UseBuiltInTest) {
			assert(BaseClass.LookupMethod("+", 0) != null);
			assert(BaseClass.LookupMethod("+", 1) != null);
			assert(BaseClass.LookupMethod("+", 2) == null);
			KMethod m = BaseClass.LookupMethod("+", 1);
			Object[] p = new Object[2];
			p[0] = new Integer(1);
			p[1] = new Integer(2);
			System.out.println("******* 1+2=" + m.Eval(p));
		}
	}
	
	public static int Plus(int x) {
		return +x;
	}

	public static int Add(int x, int y) {
		return x + y;
	}

	public static int Minus(int x) {
		return -x;
	}

	public static int Sub(int x, int y) {
		return x - y;
	}

}

//static KMETHOD PatternMatch_Expression()
//{
//	VAR_PatternMatch(stmt, name, TokenList, BeginIdx, EndIdx);
//	int returnIdx = BeginIdx;
//	kNode *expr = ParseNewNode(kctx, kNode_ns(stmt), TokenList, &returnIdx, EndIdx, (ParseOption)(ParseExpressionOption|OnlyPatternMatch), NULL);
//	if(expr != K_NULLNODE) {
//		kNode_AddParsedObject(kctx, stmt, name, UPCAST(expr));
//	}
//	return (returnIdx);
//}
//
//static KMETHOD PatternMatch_Type()
//{
//	VAR_PatternMatch(stmt, name, TokenList, BeginIdx, EndIdx);
//	KClass *foundClass = NULL;
//	int returnIdx = ParseTypePattern(kctx, kNode_ns(stmt), TokenList, BeginIdx, EndIdx, &foundClass);
//	if(foundClass != NULL) {
//		kTokenVar *tk = new_(TokenVar, 0, OnVirtualField);
//		kNode_AddParsedObject(kctx, stmt, name, UPCAST(tk));
//		kToken_SetTypeId(kctx, tk, kNode_ns(stmt), foundClass->typeId);
//	}
//	return (returnIdx);
//}
//
//static KMETHOD ParseTerm();
//static KMETHOD PatternMatch_MethodName()
//{
//	VAR_Parse(stmt, name, TokenList, BeginIdx, OpIdx, EndIdx);
//	if(OpIdx == -1) {
//		int returnIdx = -1;
//		kTokenVar *tk = TokenList->TokenVarItems[BeginIdx];
//		if(IS_String(tk->text)) {
//			kNode_AddParsedObject(kctx, stmt, name, UPCAST(tk));
//			returnIdx = BeginIdx + 1;
//		}
//		return (returnIdx);
//	}
//	else {
//		ParseTerm(kctx, sfp);
//	}
//}
//
//static KMETHOD PatternMatch_CStyleBlock()
//{
//	VAR_PatternMatch(stmt, name, TokenList, BeginIdx, EndIdx);
//	kToken *tk = TokenList->TokenItems[BeginIdx];
////	KdumpTokenArray(kctx, TokenList, BeginIdx, EndIdx);
//	if(tk->tokenType == TokenType_LazyBlock || tk->resolvedSyntaxInfo->keyword == KSymbol_BraceGroup) {
//		kNode_AddParsedObject(kctx, stmt, name, UPCAST(tk));
//		return (BeginIdx+1);
//	}
//	kNode *block = ParseNewNode(kctx, kNode_ns(stmt), TokenList, &BeginIdx, EndIdx, ParseMetaPatternOption, NULL);
//	kNode_AddParsedObject(kctx, stmt, name, UPCAST(block));
//	return (BeginIdx);
//}
//
//static kbool_t IsStatementEnd(KonohaContext *kctx, kToken *tk)
//{
//	return kToken_IsStatementSeparator(tk);
//}
//
//static KMETHOD Parse_Block()
//{
//	VAR_Parse(block, name, TokenList, BeginIdx, OpIdx, EndIdx);
//	if(OpIdx != -1) {
//		AppendParsedNode(kctx, block, TokenList, BeginIdx, EndIdx, IsStatementEnd, ParseMetaPatternOption, NULL);
//		return (EndIdx);
//	}
//}
//
//static KMETHOD ParseBlock()
//{
//	VAR_Expression(node, TokenList, BeginIdx, OpIdx, EndIdx);
//	if(BeginIdx == OpIdx) {
//		kNameSpace *ns = kNode_ns(node);
//		kToken *groupToken = kToken_ToBraceGroup(kctx, TokenList->TokenVarItems[BeginIdx], ns, NULL);
//		AppendParsedNode(kctx, node, RangeGroup(groupToken->GroupTokenList), IsStatementEnd, ParseMetaPatternOption, NULL);
//		return (BeginIdx+1);
//	}
//	return (-1);
//}
//
//static KMETHOD TypeCheck_Block()
//{
//	VAR_TypeCheck2(stmt, expr, ns, reqc);
//	KReturn(TypeCheckBlock(kctx, expr, ns, reqc));
//}
//
//static KMETHOD PatternMatch_Token()
//{
//	VAR_PatternMatch(stmt, name, TokenList, BeginIdx, EndIdx);
//	DBG_ASSERT(BeginIdx < EndIdx);
//	kToken *tk = TokenList->TokenItems[BeginIdx];
//	if(!kToken_IsStatementSeparator(tk) && !kToken_IsIndent(tk)) {
//		kNode_AddParsedObject(kctx, stmt, name, UPCAST(tk));
//		return (BeginIdx+1);
//	}
//	return (-1);
//}
//
//static KMETHOD PatternMatch_TypeDecl()
//{
//	VAR_PatternMatch(stmt, name, TokenList, BeginIdx, EndIdx);
//	kNameSpace *ns = kNode_ns(stmt);
//	KClass *foundClass = NULL;
//	int nextIdx = ParseTypePattern(kctx, ns, TokenList, BeginIdx, EndIdx, &foundClass);
//	//DBG_P("@ nextIdx = %d < %d", nextIdx, EndIdx);
//	if(nextIdx != -1) {
//		nextIdx = TokenUtils_SkipIndent(TokenList, nextIdx, EndIdx);
//		if(nextIdx < EndIdx) {
//			kToken *tk = TokenList->TokenItems[nextIdx];
//			if(tk->tokenType == KSymbol_SymbolPattern) {
//				return (ParseSyntaxPattern(kctx, ns, stmt, stmt->syn, TokenList, BeginIdx, EndIdx));
//			}
//		}
//	}
//	return (-1);
//}
//
//static KMETHOD PatternMatch_MethodDecl()
//{
//	VAR_PatternMatch(stmt, name, TokenList, BeginIdx, EndIdx);
//	kNameSpace *ns = kNode_ns(stmt);
//	KClass *foundClass = NULL;
//	int nextIdx = ParseTypePattern(kctx, ns, TokenList, BeginIdx, EndIdx, &foundClass);
//	//DBG_P("@ nextIdx = %d < %d found=%p", nextIdx, EndIdx, foundClass);
//	KLIB dumpTokenArray(kctx, 0, TokenList, BeginIdx, EndIdx);
//	if(nextIdx != -1) {
//		nextIdx = TokenUtils_SkipIndent(TokenList, nextIdx, EndIdx);
//		if(nextIdx < EndIdx) {
//			kToken *tk = TokenList->TokenItems[nextIdx];
//			if(ParseTypePattern(kctx, ns, TokenList, nextIdx, EndIdx, NULL) != -1) {
//				return (ParseSyntaxPattern(kctx, ns, stmt, stmt->syn, TokenList, BeginIdx, EndIdx));
//			}
//			if(tk->tokenType == KSymbol_ParenthesisGroup) {
//				return (ParseSyntaxPattern(kctx, ns, stmt, stmt->syn, TokenList, BeginIdx, EndIdx));
//			}
//			if(tk->tokenType == KSymbol_SymbolPattern) {
//				int symbolNextIdx = TokenUtils_SkipIndent(TokenList, nextIdx + 1, EndIdx);
//				if(symbolNextIdx < EndIdx && TokenList->TokenItems[symbolNextIdx]->tokenType == KSymbol_ParenthesisGroup) {
//					return (ParseSyntaxPattern(kctx, ns, stmt, stmt->syn, TokenList, BeginIdx, EndIdx));
//				}
//				return (-1);
//			}
//		}
//	}
//	return (-1);
//}

/* ------------------------------------------------------------------------ */

//static KMETHOD ParseParsedNode()
//{
//	VAR_Expression(stmt, TokenList, BeginIdx, OpIdx, EndIdx);
//	if(BeginIdx == OpIdx) {
//		kToken *tk = TokenList->TokenItems[OpIdx];
//		DBG_ASSERT(IS_Node(tk->parsedNode));
//		KReturn(tk->parsedNode);
//	}
//}
//
//static KMETHOD Parse_Pattern()
//{
//	VAR_Parse(node, name, TokenList, BeginIdx, OpIdx, EndIdx);
//	if(BeginIdx == OpIdx) {
//		kToken *tk = TokenList->TokenVarItems[BeginIdx];
//		node->syn = tk->resolvedSyntaxInfo;
//		KFieldSet(node, node->KeyOperatorToken, tk);
//		return (ParseSyntaxPattern(kctx, kNode_ns(node), node, node->syn, TokenList, BeginIdx, EndIdx));
//	}
//	return (-1);
//}

//
///* ------------------------------------------------------------------------ */
///* Expression TyCheck */
//
//static kString *ResolveStringEscapeSequenceNULL(KonohaContext *kctx, kToken *tk, size_t start)
//{
//	KBuffer wb;
//	KLIB KBuffer_Init(&(kctx->stack->cwb), &wb);
//	const char *text = kString_text(tk->text) + start;
//	const char *end  = kString_text(tk->text) + kString_size(tk->text);
//	KLIB KBuffer_Write(kctx, &wb, kString_text(tk->text), start);
//	while(text < end) {
//		int ch = *text;
//		if(ch == '\\' && *(text+1) != '\0') {
//			switch (*(text+1)) {
//			/* compatible with ECMA-262
//			 * http://ecma-international.org/ecma-262/5.1/#sec-7.8.4
//			 */
//			case 'b':  ch = '\b'; text++; break;
//			case 't':  ch = '\t'; text++; break;
//			case 'n':  ch = '\n'; text++; break;
//			case 'v':  ch = '\v'; text++; break;
//			case 'f':  ch = '\f'; text++; break;
//			case 'r':  ch = '\r'; text++; break;
//			case '"':  ch = '"';  text++; break;
//			case '\'': ch = '\''; text++; break;
//			case '\\': ch = '\\'; text++; break;
//			default: return NULL;
//			}
//		}
//		{
//			char buf[1] = {ch};
//			KLIB KBuffer_Write(kctx, &wb, (const char *)buf, 1);
//		}
//		text++;
//	}
//	return KLIB KBuffer_Stringfy(kctx, &wb, OnGcStack, StringPolicy_FreeKBuffer);
//}
//
//static KMETHOD TypeCheck_TextLiteral()
//{
//	VAR_TypeCheck2(stmt, expr, ns, reqc);
//	kToken *tk = expr->TermToken;
//	kString *text = tk->text;
//	if(kToken_Is(RequiredReformat, tk)) {
//		const char *escape = strchr(kString_text(text), '\\');
//		DBG_ASSERT(escape != NULL);
//		text = ResolveStringEscapeSequenceNULL(kctx, tk, escape - kString_text(text));
//		if(text == NULL) {
//			KReturn(ERROR_UndefinedEscapeSequence(kctx, stmt, tk));
//		}
//	}
//	kString_Set(Literal, ((kStringVar *)text), true);
//	KReturn(KLIB kNode_SetConst(kctx, expr, NULL, UPCAST(text)));
//}
//
//static KMETHOD TypeCheck_Type()
//{
//	VAR_TypeCheck2(stmt, expr, ns, reqc);
//	DBG_ASSERT(Token_isVirtualTypeLiteral(expr->TermToken));
//	KReturn(KLIB kNode_SetVariable(kctx, expr, KNode_Null, expr->TermToken->resolvedTypeId, 0));
//}
//
//static KMETHOD TypeCheck_true()
//{
//	VAR_TypeCheck2(stmt, expr, ns, reqc);
//	KReturn(KLIB kNode_SetUnboxConst(kctx, expr, KType_Boolean, (uintptr_t)1));
//}
//
//static KMETHOD TypeCheck_false()
//{
//	VAR_TypeCheck2(stmt, expr, ns, reqc);
//	KReturn(KLIB kNode_SetUnboxConst(kctx, expr, KType_Boolean, (uintptr_t)0));
//}
//
//static KMETHOD TypeCheck_IntLiteral()
//{
//	VAR_TypeCheck2(stmt, expr, ns, reqc);
//	kToken *tk = expr->TermToken;
//	long long n = strtoll(kString_text(tk->text), NULL, 0);
//	KReturn(KLIB kNode_SetUnboxConst(kctx, expr, KType_Int, (uintptr_t)n));
//}
//
//static KMETHOD TypeCheck_AndOperator()
//{
//	VAR_TypeCheck2(stmt, expr, ns, reqc);
//	kNode *returnNode = TypeCheckNodeAt(kctx, expr, 1, ns, KClass_Boolean, 0);
//	if(!kNode_IsError(returnNode)) {
//		returnNode = TypeCheckNodeAt(kctx, expr, 2, ns, KClass_Boolean, 0);
//		if(!kNode_IsError(returnNode)) {
//			returnNode = kNode_Type(expr, KNode_And, KType_Boolean);
//		}
//	}
//	KReturn(returnNode);
//}
//
//static KMETHOD TypeCheck_OrOperator()
//{
//	VAR_TypeCheck2(stmt, expr, ns, reqc);
//	kNode *returnNode = TypeCheckNodeAt(kctx, expr, 1, ns, KClass_Boolean, 0);
//	if(!kNode_IsError(returnNode)) {
//		returnNode = TypeCheckNodeAt(kctx, expr, 2, ns, KClass_Boolean, 0);
//		if(!kNode_IsError(returnNode)) {
//			returnNode = kNode_Type(expr, KNode_Or, KType_Boolean);
//		}
//	}
//	KReturn(returnNode);
//}
//
//static kbool_t kNode_IsGetter(kNode *expr)
//{
//	if(kNode_node(expr) == KNode_MethodCall) {  // check getter and transform to setter
//		kMethod *mtd = expr->NodeList->MethodItems[0];
//		DBG_ASSERT(IS_Method(mtd));
//		if(KMethodName_IsGetter(mtd->mn)) return true;
//	}
//	return false;
//}
//
//static kNode *MakeNodeSetter(KonohaContext *kctx, kNode *expr, kNameSpace *ns, kNode *rightHandNode, KClass *reqc)
//{
//	kMethod *mtd = expr->NodeList->MethodItems[0];
//	DBG_ASSERT(KMethodName_IsGetter(mtd->mn));
//	KClass *c = KClass_(mtd->typeId);
//	kParam *pa = kMethod_GetParam(mtd);
//	int i, psize = pa->psize + 1;
//	kparamtype_t *p = ALLOCA(kparamtype_t, psize);
//	for(i = 0; i < (int) pa->psize; i++) {
//		p[i].typeAttr = pa->paramtypeItems[i].typeAttr;
//	}
//	p[pa->psize].typeAttr = expr->typeAttr;
//	kparamId_t paramdom = KLIB Kparamdom(kctx, psize, p);
//	kMethod *foundMethod = kNameSpace_GetMethodBySignatureNULL(kctx, ns, c, KMethodName_ToSetter(mtd->mn), paramdom, psize, p);
//	if(foundMethod == NULL) {
//		p[pa->psize].typeAttr = pa->rtype;   /* transform "T1 A.get(T2)" to "void A.set(T2, T1)" */
//		paramdom = KLIB Kparamdom(kctx, psize, p);
//		foundMethod = kNameSpace_GetMethodBySignatureNULL(kctx, ns, c, KMethodName_ToSetter(mtd->mn), paramdom, psize, p);
//	}
//	if(foundMethod != NULL) {
//		KFieldSet(expr->NodeList, expr->NodeList->MethodItems[0], foundMethod);
//		KLIB kArray_Add(kctx, expr->NodeList, rightHandNode);
//		return KLIB TypeCheckMethodParam(kctx, foundMethod, expr, ns, reqc);
//	}
//	return KLIB MessageNode(kctx, expr, NULL, ns, ErrTag, "undefined setter");
//}
//
//static KMETHOD TypeCheck_Assign()
//{
//	VAR_TypeCheck2(stmt, expr, ns, reqc);
//	kNode *leftHandNode = KLIB TypeCheckNodeAt(kctx, expr, 1, ns, KClass_INFER, TypeCheckPolicy_AllowVoid);
//	kNode *rightHandNode = KLIB TypeCheckNodeAt(kctx, expr, 2, ns, KClass_(leftHandNode->typeAttr), 0);
//	if(kNode_IsError(leftHandNode)) {
//		KReturn(leftHandNode);
//	}
//	if(kNode_IsError(rightHandNode)) {
//		KReturn(rightHandNode);
//	}
//	kNode *returnNode = K_NULLNODE;
//	if(kNode_node(leftHandNode) == KNode_Local || kNode_node(leftHandNode) == KNode_Field) {
//		if(KTypeAttr_Is(ReadOnly, leftHandNode->typeAttr)) {
//			returnNode = KLIB MessageNode(kctx, expr, leftHandNode->TermToken, ns, ErrTag, "read only: %s", KToken_t(leftHandNode->TermToken));
//		}
//		else {
//			returnNode = kNode_Type(expr, KNode_Assign, leftHandNode->typeAttr);
//		}
//	}
//	else if(kNode_IsGetter(leftHandNode)) {
//		returnNode = MakeNodeSetter(kctx, leftHandNode, ns, rightHandNode, reqc);
//	}
//	else {
//		KDump(leftHandNode);
//		returnNode = KLIB MessageNode(kctx, expr, NULL, ns, ErrTag, "assignment: variable name is expected");
//	}
//	KReturn(returnNode);
//}
//
//static int AddLocalVariable(KonohaContext *kctx, kNameSpace *ns, ktypeattr_t typeAttr, ksymbol_t name)
//{
//	struct KGammaStack *s = &(ns->genv->localScope);
//	int index = s->varsize;
//	if(!(s->varsize < s->capacity)) {
//		s->capacity *= 2;
//		size_t asize = sizeof(KGammaStackDecl) * s->capacity;
//		KGammaStackDecl *v = (KGammaStackDecl *)KMalloc_UNTRACE(asize);
//		memcpy(v, s->varItems, asize/2);
//		if(s->allocsize > 0) {
//			KFree(s->varItems, s->allocsize);
//		}
//		s->varItems = v;
//		s->allocsize = asize;
//	}
//	s->varItems[index].typeAttr = typeAttr;
//	s->varItems[index].name = name;
//	s->varsize += 1;
//	return index;
//}
//
//static kNode *new_GetterNode(KonohaContext *kctx, kToken *tkU, kMethod *mtd, kNode *expr)
//{
//	return new_TypedNode(kctx, kNode_ns(expr), KNode_MethodCall, kMethod_GetReturnType(mtd), 2, mtd, expr);
//}
//
//static kNode *TypeVariableNULL(KonohaContext *kctx, kNode *expr, kNameSpace *ns, KClass *reqc)
//{
//	DBG_ASSERT(expr->typeAttr == KType_var);
//	kToken *tk = expr->TermToken;
//	ksymbol_t symbol = tk->symbol;
//	int i;
//	struct KGammaLocalData *genv = ns->genv;
//	for(i = genv->localScope.varsize - 1; i >= 0; i--) {
//		if(genv->localScope.varItems[i].name == symbol) {
//			return KLIB kNode_SetVariable(kctx, expr, KNode_Local, genv->localScope.varItems[i].typeAttr, i);
//		}
//	}
//	if(kNameSpace_Is(ImplicitField, ns)) {
//		if(genv->localScope.varItems[0].typeAttr != KType_void) {
//			KClass *ct = genv->thisClass;
//			if(ct->fieldsize > 0) {
//				for(i = ct->fieldsize; i >= 0; i--) {
//					if(ct->fieldItems[i].name == symbol && ct->fieldItems[i].typeAttr != KType_void) {
//						return KLIB kNode_SetVariable(kctx, expr, KNode_Field, ct->fieldItems[i].typeAttr, longid((khalfword_t)i, 0));
//					}
//				}
//			}
//			kMethod *mtd = kNameSpace_GetGetterMethodNULL(kctx, ns, genv->thisClass, symbol);
//			if(mtd != NULL) {
//				return new_GetterNode(kctx, tk, mtd, new_VariableNode(kctx, ns, KNode_Local, genv->thisClass->typeId, 0));
//			}
//		}
//	}
//	if(genv->thisClass->baseTypeId == KType_Func) {
//		kMethod *mtd = kNameSpace_GetGetterMethodNULL(kctx, ns, genv->thisClass, symbol);
//		if(mtd != NULL) {
//			return new_GetterNode(kctx, tk, mtd, new_VariableNode(kctx, ns, KNode_Local, genv->thisClass->typeId, 0));
//		}
//	}
//
//	if((kNameSpace_IsTopLevel(ns) || kNameSpace_Is(ImplicitGlobalVariable, ns)) && ns->globalObjectNULL != NULL) {
//		KClass *globalClass = kObject_class(ns->globalObjectNULL);
//		kMethod *mtd = kNameSpace_GetGetterMethodNULL(kctx, ns, globalClass, symbol);
//		if(mtd != NULL) {
//			return new_GetterNode(kctx, tk, mtd, new_ConstNode(kctx, ns, globalClass, ns->globalObjectNULL));
//		}
//	}
//	kMethod *mtd = kNameSpace_GetNameSpaceFuncNULL(kctx, ns, symbol, reqc);  // finding function
//	if(mtd != NULL) {
//		kParam *pa = kMethod_GetParam(mtd);
//		KClass *ct = KLIB KClass_Generics(kctx, KClass_Func, pa->rtype, pa->psize, (kparamtype_t *)pa->paramtypeItems);
//		kFuncVar *fo = (kFuncVar *)KLIB new_kObject(kctx, OnGcStack, ct, (uintptr_t)mtd);
//		//KFieldSet(fo, fo->self, UPCAST(ns));
//		return new_ConstNode(kctx, ns, ct, UPCAST(fo));
//	}
//	if(symbol != KSymbol_Noname) {
//		KKeyValue *kv = kNameSpace_GetConstNULL(kctx, ns, symbol, false/*isLocalOnly*/);
//		if(kv != NULL) {
//			if(KTypeAttr_Is(Boxed, kv->typeAttr)) {
//				KLIB kNode_SetConst(kctx, expr, NULL, kv->ObjectValue);
//			}
//			else {
//				KLIB kNode_SetUnboxConst(kctx, expr, kv->typeAttr, kv->unboxValue);
//			}
//			return expr;
//		}
//	}
//	return NULL;
//}
//
//static KMETHOD TypeCheck_Symbol()
//{
//	VAR_TypeCheck2(stmt, expr, ns, reqc);
//	kNode *texpr = TypeVariableNULL(kctx, expr, ns, reqc);
//	if(texpr == NULL) {
//		kToken *tk = expr->TermToken;
//		texpr = kNodeToken_Message(kctx, stmt, tk, ErrTag, "undefined name: %s", KToken_t(tk));
//	}
//	KReturn(texpr);
//}
//
//static KClass* ResolveTypeVariable(KonohaContext *kctx, KClass *varType, KClass *thisClass)
//{
//	return varType->realtype(kctx, varType, thisClass);
//}
//
//static int TypeCheckPolicy_(ktypeattr_t attrtype)
//{
//	int pol = 0;
//	if(KTypeAttr_Is(Coercion, attrtype)) {
//		pol = pol | TypeCheckPolicy_Coercion;
//	}
//	return pol;
//}
//
//static kNodeVar* TypeMethodCallNode(KonohaContext *kctx, kNodeVar *expr, kMethod *mtd, KClass *reqClass)
//{
//	kNode *thisNode = kNode_At(expr, 1);
//	KFieldSet(expr->NodeList, expr->NodeList->MethodItems[0], mtd);
//	KClass *typedClass = ResolveTypeVariable(kctx, kMethod_GetReturnType(mtd), KClass_(thisNode->typeAttr));
//	if(kNode_node(thisNode) == KNode_New) {
//		typedClass = KClass_(thisNode->typeAttr);
//	}
//	else if(kMethod_Is(SmartReturn, mtd) && reqClass->typeId != KType_var) {
//		typedClass = reqClass;
//	}
//	kNode_Type(expr, KNode_MethodCall, typedClass->typeId);
//	return expr;
//}
//
//static kNode *BoxThisNode(KonohaContext *kctx, kNode *expr, kNameSpace *ns, kMethod *mtd, KClass **thisClassRef)
//{
//	kNode *thisNode = expr->NodeList->NodeItems[1];
//	KClass *thisClass = KClass_(thisNode->typeAttr);
//	DBG_ASSERT(thisClass->typeId != KType_var);
//	//DBG_P("mtd_cid=%s this=%s", KType_text(mtd->typeId), KClass_text(thisClass));
//	if(!KType_Is(UnboxType, mtd->typeId) && KClass_Is(UnboxType, thisClass)) {
//		thisNode = kNode_SetNodeAt(kctx, expr, 1, BoxNode(kctx, thisNode, ns, thisClass));
//	}
//	thisClassRef[0] = thisClass;
//	return thisNode;
//}
//
//static kNode *TypeCheckMethodParam(KonohaContext *kctx, kMethod *mtd, kNode *expr, kNameSpace *ns, KClass* reqc)
//{
//	DBG_ASSERT(IS_Method(mtd));
//	KFieldSet(expr->NodeList, expr->NodeList->MethodItems[0], mtd);
//	KClass *thisClass = NULL;
//	kNode *thisNode = BoxThisNode(kctx, expr, ns, mtd, &thisClass);
//	kbool_t isConst = kNode_IsConstValue(thisNode);
//	kParam *pa = kMethod_GetParam(mtd);
//	DBG_ASSERT(pa->psize + 2U <= kArray_size(expr->NodeList));
//	size_t i;
//	for(i = 0; i < pa->psize; i++) {
//		size_t n = i + 2;
//		KClass* paramType = ResolveTypeVariable(kctx, KClass_(pa->paramtypeItems[i].typeAttr), thisClass);
//		int tycheckPolicy = TypeCheckPolicy_(pa->paramtypeItems[i].typeAttr);
//		kNode *texpr = KLIB TypeCheckNodeAt(kctx, expr, n, ns, paramType, tycheckPolicy);
//		if(kNode_IsError(texpr)) {
//			KLIB MessageNode(kctx, expr, NULL, ns, InfoTag, "%s.%s%s accepts %s at the parameter %d", kMethod_Fmt3(mtd), KClass_text(paramType), (int)i+1);
//			return texpr;
//		}
//		if(!kNode_IsConstValue(texpr)) isConst = 0;
//	}
//	expr = TypeMethodCallNode(kctx, expr, mtd, reqc);
//	if(isConst && kMethod_Is(Const, mtd)) {
//		KClass *rtype = ResolveTypeVariable(kctx, KClass_(pa->rtype), thisClass);
//		return MakeNodeConst(kctx, expr, rtype);
//	}
//	return expr;
//}
//
////static kNode *TypeCheckDynamicCallParams(KonohaContext *kctx, kNode *stmt, kNodeVar *expr, kMethod *mtd, kNameSpace *ns, kString *name, kmethodn_t mn, KClass *reqClass)
////{
////	size_t i;
////	kParam *pa = kMethod_GetParam(mtd);
////	KClass* ptype = (pa->psize == 0) ? KClass_Object : KClass_(pa->paramtypeItems[0].typeAttr);
////	for(i = 2; i < kArray_size(expr->NodeList); i++) {
////		kNode *texpr = KLIB TypeCheckNodeAt(kctx, expr, i, ns, ptype, 0);
////		if(kNode_IsError(texpr) /* texpr = K_NULLNODE */) return texpr;
////	}
////	kNode_AddNode(kctx, expr, new_ConstNode(kctx, kNode_ns(expr), NULL, UPCAST(name)));
////	return TypeMethodCallNode(kctx, expr, mtd, reqClass);
////}
////
//static kMethod *kNameSpace_GuessCoercionMethodNULL(KonohaContext *kctx, kNameSpace *ns, kToken *tk, KClass *thisClass)
//{
//	const char *name = kString_text(tk->text);
//	if(name[1] == 'o' && (name[0] == 't' || name[0] == 'T')) {
//		KClass *c = KLIB kNameSpace_GetClassByFullName(kctx, ns, name + 2, kString_size(tk->text) - 2, NULL);
//		if(c != NULL) {
//			return KLIB kNameSpace_GetCoercionMethodNULL(kctx, ns, thisClass, c);
//		}
//	}
//	return NULL;
//}
//
//static kMethod *LookupOverloadedMethod(KonohaContext *kctx, kMethod *mtd, kNode *expr, kNameSpace *ns)
//{
//	KClass *thisClass = KClass_(expr->NodeList->NodeItems[1]->typeAttr);
//	size_t i, psize = kArray_size(expr->NodeList) - 2;
//	kparamtype_t *p = ALLOCA(kparamtype_t, psize);
//	kParam *pa = kMethod_GetParam(mtd);
//	for(i = 0; i < psize; i++) {
//		size_t n = i + 2;
//		KClass *paramType = (i < pa->psize) ? ResolveTypeVariable(kctx, KClass_(pa->paramtypeItems[i].typeAttr), thisClass) : KClass_INFER;
//		kNode *texpr = KLIB TypeCheckNodeAt(kctx, expr, n, ns, paramType, TypeCheckPolicy_NoCheck);
//		if(kNode_IsError(texpr)) return NULL;
//		p[i].typeAttr = texpr->typeAttr;
//	}
//	kparamId_t paramdom = KLIB Kparamdom(kctx, psize, p);
//	return kNameSpace_GetMethodBySignatureNULL(kctx, ns, thisClass, mtd->mn, paramdom, psize, p);
//}
//
//static kMethod *LookupMethod(KonohaContext *kctx, kNode *expr, kNameSpace *ns)
//{
//	KClass *thisClass = KClass_(kNode_At(expr, 1)->typeAttr);
//	kToken *methodToken = expr->NodeList->TokenVarItems[0];
//	DBG_ASSERT(IS_Token(methodToken));
//	size_t psize = kArray_size(expr->NodeList) - 2;
//	kMethod *mtd = kNameSpace_GetMethodByParamSizeNULL(kctx, ns, thisClass, methodToken->symbol, psize, KMethodMatch_CamelStyle);
//	if(mtd == NULL && psize == 0) {
//		mtd = kNameSpace_GuessCoercionMethodNULL(kctx, ns, methodToken, thisClass);
//	}
//	if(mtd == NULL) {
//		if(methodToken->text != TS_EMPTY) {  // find Dynamic Call ..
//			mtd = kNameSpace_GetMethodByParamSizeNULL(kctx, ns, thisClass, 0/*NONAME*/, 1, KMethodMatch_NoOption);
////			if(mtd != NULL) {
////				return TypeCheckDynamicCallParams(kctx, stmt, expr, mtd, ns, methodToken->text, methodToken->symbol, reqc);
////			}
//		}
////		if(methodToken->symbol == MN_new && psize == 0 && KClass_(kNode_At(expr, 1)->typeAttr)->baseTypeId == KType_Object) {
////			return kNode_At(expr, 1);  // new Person(); // default constructor
////		}
//		KLIB MessageNode(kctx, expr, methodToken, ns, ErrTag, "undefined method: %s.%s%s", KClass_text(thisClass), KSymbol_Fmt2(methodToken->symbol));
//	}
//	if(mtd != NULL) {
//		if(kMethod_Is(Overloaded, mtd)) {
//			mtd = LookupOverloadedMethod(kctx, mtd, expr, ns);
//		}
//	}
//	return mtd;
//}
//
//static KMETHOD TypeCheck_MethodCall()
//{
//	VAR_TypeCheck(expr, ns, reqc);
//	kNode *texpr = K_NULLNODE;
//	KDump(expr);
//	DBG_ASSERT(IS_Array(expr->NodeList));
//	kMethod *mtd = expr->NodeList->MethodItems[0];
//	DBG_ASSERT(mtd != NULL);
//	if(!IS_Method(mtd)) {
//		texpr = KLIB TypeCheckNodeAt(kctx, expr, 1, ns, KClass_INFER, 0);
//		mtd = (kNode_IsError(texpr)) ? NULL : LookupMethod(kctx, expr, ns);
//	}
//	if(mtd != NULL) {
//		texpr = TypeCheckMethodParam(kctx, mtd, expr, ns, reqc);
//		KReturn(texpr);
//	}
//	if(kNode_IsError(texpr)) {
//		KReturn(texpr);
//	}
//	KReturn(expr);
//}
//
//// --------------------------------------------------------------------------
//// FuncStyleCall
//
//static kNode *TypeFuncParam(KonohaContext *kctx, kNodeVar *expr, kNameSpace *ns)
//{
//	KClass *thisClass = KClass_(kNode_At(expr, 0)->typeAttr);
//	kParam *pa = KClass_cparam(thisClass);
//	size_t i, size = kArray_size(expr->NodeList);
//	if(pa->psize + 2U != size) {
//		return KLIB MessageNode(kctx, expr, NULL, ns, ErrTag, "function %s takes %d parameter(s), but given %d parameter(s)", KClass_text(thisClass), (int)pa->psize, (int)size-2);
//	}
//	for(i = 0; i < pa->psize; i++) {
//		size_t n = i + 2;
//		kNode *texpr = KLIB TypeCheckNodeAt(kctx, expr, n, ns, KClass_(pa->paramtypeItems[i].typeAttr), 0);
//		if(kNode_IsError(texpr) /* texpr = K_NULLNODE */) {
//			return texpr;
//		}
//	}
//	kMethod *mtd = KLIB kNameSpace_GetMethodByParamSizeNULL(kctx, kNode_ns(expr), KClass_Func, KMethodName_("Invoke"), -1, KMethodMatch_NoOption);
//	/* [before] [Func, nulltoken, Arg1, Arg2, Arg3 ..]
//	 * [after]  [null, Func, Arg1, Arg2, Arg3 ..]
//	 */
//	kArray *List = expr->NodeList;
//	KFieldSet(List, List->ObjectItems[1], List->ObjectItems[0]);
//
//	DBG_ASSERT(mtd != NULL);
//	return TypeMethodCallNode(kctx, expr, mtd, KClass_(thisClass->p0));
//}
//
//static kMethod* TypeFirstNodeAndLookupMethod(KonohaContext *kctx, kNodeVar *exprN, kNameSpace *ns, KClass *reqc)
//{
//	kNodeVar *firstNode = (kNodeVar *)kNode_At(exprN, 0);
//	kToken *termToken = firstNode->TermToken;
//	ksymbol_t funcName = termToken->symbol;
//	struct KGammaLocalData *genv = ns->genv;
//	int i;
//	for(i = genv->localScope.varsize - 1; i >= 0; i--) {
//		if(genv->localScope.varItems[i].name == funcName && KType_IsFunc(genv->localScope.varItems[i].typeAttr)) {
//			KLIB kNode_SetVariable(kctx, firstNode, KNode_Local, genv->localScope.varItems[i].typeAttr, i);
//			return NULL;
//		}
//	}
//	int paramsize = kArray_size(exprN->NodeList) - 2;
//	if(genv->localScope.varItems[0].typeAttr != KType_void) {
//		KClass *ct = genv->thisClass;
//		kMethod *mtd = kNameSpace_GetMethodByParamSizeNULL(kctx, ns, genv->thisClass, funcName, paramsize, KMethodMatch_CamelStyle);
//		if(mtd != NULL) {
//			KFieldSet(exprN->NodeList, exprN->NodeList->NodeItems[1], new_VariableNode(kctx, ns, KNode_Local, ct->typeId, 0));
//			return mtd;
//		}
//		if(ct->fieldsize) {
//			for(i = ct->fieldsize; i >= 0; i--) {
//				if(ct->fieldItems[i].name == funcName && KType_IsFunc(ct->fieldItems[i].typeAttr)) {
//					KLIB kNode_SetVariable(kctx, firstNode, KNode_Field, ct->fieldItems[i].typeAttr, longid((khalfword_t)i, 0));
//					return NULL;
//				}
//			}
//		}
//		mtd = kNameSpace_GetGetterMethodNULL(kctx, ns, genv->thisClass, funcName);
//		if(mtd != NULL && kMethod_IsReturnFunc(mtd)) {
//			KFieldSet(exprN->NodeList, exprN->NodeList->NodeItems[0], new_GetterNode(kctx, termToken, mtd, new_VariableNode(kctx, ns, KNode_Local, genv->thisClass->typeId, 0)));
//			return NULL;
//		}
//	}
//	{
//		KKeyValue* kvs = kNameSpace_GetConstNULL(kctx, ns, funcName, false/*isLocalOnly*/);
//		if(kvs != NULL && KTypeAttr_Unmask(kvs->typeAttr) == VirtualType_StaticMethod) {
//			KClass *c = KClass_((ktypeattr_t)kvs->unboxValue);
//			ksymbol_t alias = (ksymbol_t)(kvs->unboxValue >> (sizeof(ktypeattr_t) * 8));
//			kMethod *mtd = kNameSpace_GetMethodByParamSizeNULL(kctx, ns, c, alias, paramsize, KMethodMatch_NoOption);
//			if(mtd != NULL && kMethod_Is(Static, mtd)) {
//				KFieldSet(exprN->NodeList, exprN->NodeList->NodeItems[1], new_ConstNode(kctx, ns, c, KLIB Knull(kctx, c)));
//				return mtd;
//			}
//		}
//	}
//	{
//		kMethod *mtd = kNameSpace_GetMethodByParamSizeNULL(kctx, ns, kObject_class(ns), funcName, paramsize, KMethodMatch_CamelStyle);
//		if(mtd != NULL) {
//			KFieldSet(exprN->NodeList, exprN->NodeList->NodeItems[1], new_ConstNode(kctx, ns, kObject_class(ns), UPCAST(ns)));
//			return mtd;
//		}
//	}
//	if((kNameSpace_IsTopLevel(ns) || kNameSpace_Is(ImplicitGlobalVariable,ns)) && ns->globalObjectNULL != NULL) {
//		KClass *globalClass = kObject_class(ns->globalObjectNULL);
//		kMethod *mtd = kNameSpace_GetGetterMethodNULL(kctx, ns, globalClass, funcName);
//		if(mtd != NULL && kMethod_IsReturnFunc(mtd)) {
//			KFieldSet(exprN->NodeList, exprN->NodeList->NodeItems[0], new_GetterNode(kctx, termToken, mtd, new_ConstNode(kctx, ns, globalClass, ns->globalObjectNULL)));
//			return NULL;
//		}
//		return mtd;
//	}
//	return NULL;
//}
//
//static KMETHOD TypeCheck_FuncStyleCall()
//{
//	VAR_TypeCheck(expr, ns, reqc);
//	DBG_ASSERT(expr->NodeList->NodeItems[1] == K_NULLNODE);
//	kNode *firstNode = kNode_At(expr, 0);
//	DBG_ASSERT(IS_Node(firstNode));
//	DBG_P(">>>>>>>>>>>>> firstNode=%s%s", KSymbol_Fmt2(firstNode->syn->keyword));
//	if(firstNode->syn->keyword == KSymbol_MemberPattern) {
//		KFieldSet(expr, expr->KeyOperatorToken, firstNode->KeyOperatorToken);
//		KFieldSet(expr->NodeList, expr->NodeList->ObjectItems[1], firstNode->NodeList->ObjectItems[1]);
//		KFieldSet(expr->NodeList, expr->NodeList->ObjectItems[0], firstNode->NodeList->ObjectItems[0]);
//		TypeCheck_MethodCall(kctx, sfp);
//		return;
//	}
//	else if(firstNode->syn->keyword == KSymbol_new) {
//		KFieldSet(expr, expr->KeyOperatorToken, firstNode->KeyOperatorToken);
//		KFieldSet(expr->NodeList, expr->NodeList->NodeItems[1], firstNode);
//		KFieldSet(expr->NodeList, expr->NodeList->TokenItems[0], firstNode->KeyOperatorToken);
//		TypeCheck_MethodCall(kctx, sfp);
//		return;
//	}
//	else if(kNode_isSymbolTerm(kNode_At(expr, 0))) {
//		kMethod *mtd = TypeFirstNodeAndLookupMethod(kctx, expr, ns, reqc);
//		if(mtd != NULL) {
//			if(kMethod_Is(Overloaded, mtd)) {
//				DBG_P("overloaded found %s.%s%s", kMethod_Fmt3(mtd));
//				mtd = LookupOverloadedMethod(kctx, mtd, expr, ns);
//			}
//			KReturn(TypeCheckMethodParam(kctx, mtd, expr, ns, reqc));
//		}
//		if(!KType_IsFunc(kNode_At(expr, 0)->typeAttr)) {
//			kToken *tk = kNode_At(expr, 0)->TermToken;
//			DBG_ASSERT(IS_Token(tk));  // TODO: make error message in case of not Token
//			KReturn(KLIB MessageNode(kctx, expr, tk, ns, ErrTag, "undefined function: %s", KToken_t(tk)));
//		}
//	}
//	else {
//		if(TypeCheckNodeAt(kctx, expr, 0, ns, KClass_INFER, 0) != K_NULLNODE) {
//			if(!KType_IsFunc(expr->NodeList->NodeItems[0]->typeAttr)) {
//				KReturn(KLIB MessageNode(kctx, expr, NULL, ns, ErrTag, "function is expected"));
//			}
//		}
//	}
//	KReturn(TypeFuncParam(kctx, expr, ns));
//}
//
//// ---------------------------------------------------------------------------
//// Statement Node
//
//
///* TypeDecl */
//
//static kNode *TypeDeclLocalVariable(KonohaContext *kctx, kNode *stmt, kNameSpace *ns, ktypeattr_t typeAttr, kNode *termNode, kNode *exprNode, kObject *thunk)
//{
//	int index = AddLocalVariable(kctx, ns, typeAttr, termNode->TermToken->symbol);
//	KLIB kNode_SetVariable(kctx, termNode, KNode_Local, typeAttr, index);
//	return new_TypedNode(kctx, ns, KNode_Assign, KClass_void, 3, K_NULLTOKEN, termNode, exprNode);
//}
//
//static void kNode_DeclType(KonohaContext *kctx, kNode *stmt, kNameSpace *ns, ktypeattr_t typeAttr, kNode *declNode, kObject *thunk, KTypeDeclFunc TypeDecl)
//{
//	kNode *newstmt = NULL;
//	if(TypeDecl == NULL) TypeDecl = TypeDeclLocalVariable;
//	if(declNode->syn->keyword == KSymbol_COMMA) {
//		size_t i;
//		for(i = 1; i < kArray_size(declNode->NodeList); i++) {
//			kNode_DeclType(kctx, stmt, ns, typeAttr, kNode_At(declNode, i), thunk, TypeDecl);
//			if(kNode_IsError(stmt)) break;
//		}
//	}
//	else if(declNode->syn->keyword == KSymbol_LET && kNode_isSymbolTerm(kNode_At(declNode, 1))) {
//		kNode *exprNode = TypeCheckNodeAt(kctx, declNode, 2, ns, KClass_(typeAttr), 0);
//		if(kNode_IsError(exprNode)) {
//			// this is neccesarry to avoid 'int a = a + 1;';
//			kNode_ToError(kctx, stmt, exprNode->ErrorMessage);
//			return;
//		}
//		kNode *nameNode = kNode_At(declNode, 1);
//		if(KTypeAttr_Unmask(typeAttr) == KType_var) {
//			ktypeattr_t attr = KTypeAttr_Attr(typeAttr);
//			kToken *termToken = nameNode->TermToken;
//			typeAttr = exprNode->typeAttr | attr;
//			kNodeToken_Message(kctx, stmt, termToken, InfoTag, "%s%s has type %s", KSymbol_Fmt2(termToken->symbol), KType_text(typeAttr));
//		}
//		newstmt = TypeDecl(kctx, stmt, ns, typeAttr, nameNode, exprNode, thunk);
//	}
//	else if(kNode_isSymbolTerm(declNode)) {
//		if(typeAttr == KType_var  || !KType_Is(Nullable, typeAttr)) {
//			kNode_Message(kctx, stmt, ErrTag, "%s %s%s: initial value is expected", KType_text(typeAttr), KSymbol_Fmt2(declNode->TermToken->symbol));
//			return;
//		}
//		kNode *exprNode = new_VariableNode(kctx, ns, KNode_Null, typeAttr, 0);
//		newstmt = TypeDecl(kctx, stmt, ns, typeAttr, declNode, exprNode, thunk);
//	}
//	else {
//		kNode_Message(kctx, stmt, ErrTag, "type declaration: variable name is expected");
//		return;
//	}
//	if(newstmt != NULL && !kNode_IsError(stmt)) {
//		kNode_Set(OpenBlock, stmt, true);
//		kNode_AddNode(kctx, stmt, newstmt);
//		kNode_Type(stmt, KNode_Block, KType_void);
//	}
//}
//
//static KMETHOD Statement_TypeDecl()
//{
//	VAR_TypeCheck(stmt, ns, reqc);
//	if(kNameSpace_IsTopLevel(ns)) {
//		KLIB MessageNode(kctx, stmt, NULL, ns, ErrTag, "unsupported global variable; use Syntax.GlobalVariable");
//		KLIB MessageNode(kctx, stmt, NULL, ns, InfoTag, "global variable is defined in Syntax.GlobalVariable");
//	}
//	else {
//		kToken *tk   = KLIB kNode_GetToken(kctx, stmt, KSymbol_TypePattern, NULL);
//		kNode  *expr = KLIB kNode_GetNode(kctx, stmt, KSymbol_ExprPattern, NULL);
//		ktypeattr_t typeAttr = Token_typeLiteral(tk);
//		kNode_DeclType(kctx, stmt, ns, typeAttr, expr, NULL, TypeDeclLocalVariable);
//	}
//	KReturn(stmt);
//}
//
//// ------------------
//// Method Utilities for MethodDecl
//
//static KMETHOD KMethodFunc_LazyCompilation()
//{
//	KonohaStack *esp = kctx->esp;
//	kMethod *mtd = sfp[K_MTDIDX].calledMethod;
//	kString *text = mtd->SourceToken->text;
//	kfileline_t uline = mtd->SourceToken->uline;
//	int baseIndent = mtd->SourceToken->indent;
//	DBG_P("<<lazy compilation>>: %s.%s%s baseIndent=%d", KType_text(mtd->typeId), KMethodName_Fmt2(mtd->mn), baseIndent);
//	kMethod_Compile(kctx, mtd, NULL, mtd->LazyCompileNameSpace, text, uline, baseIndent, DefaultCompileOption/*HatedLazyCompile*/);
//	((KonohaContextVar *)kctx)->esp = esp;
//	mtd->invokeKMethodFunc(kctx, sfp); // call again;
//}
//
//static void kMethod_SetLazyCompilation(KonohaContext *kctx, kMethodVar *mtd, kNode *stmt, kNameSpace *ns)
//{
//	kToken *sourceToken = KLIB kNode_GetToken(kctx, stmt, KSymbol_BlockPattern, NULL);
//	if(sourceToken != NULL && sourceToken->tokenType == TokenType_LazyBlock) {
//		KFieldSet(mtd, mtd->SourceToken, sourceToken);
//		KFieldSet(mtd, mtd->LazyCompileNameSpace, ns);
//		KLIB kMethod_SetFunc(kctx, mtd, KMethodFunc_LazyCompilation);
//		KLIB kArray_Add(kctx, KGetParserContext(kctx)->definedMethodList, mtd);
//	}
//}
//
///* In the future, DoLazyCompilation is extended to compile untyped parameters */
//
//static kMethod* kMethod_DoLazyCompilation(KonohaContext *kctx, kMethod *mtd, kparamtype_t *callparamNULL, int options)
//{
//	if(mtd->invokeKMethodFunc == KMethodFunc_LazyCompilation) {
//		kString *text = mtd->SourceToken->text;
//		kfileline_t uline = mtd->SourceToken->uline;
//		int baseIndent = mtd->SourceToken->indent;
//		((kMethodVar *)mtd)->invokeKMethodFunc = NULL; // TO avoid recursive compile
//		mtd = kMethod_Compile(kctx, mtd, callparamNULL, mtd->LazyCompileNameSpace, text, uline, baseIndent, options|HatedLazyCompile);
//		DBG_ASSERT(mtd->invokeKMethodFunc != KMethodFunc_LazyCompilation);
//	}
//	return mtd;
//}
//
///* ------------------------------------------------------------------------ */
///* [ParamDecl] */
//
//static void CheckCStyleParam(KonohaContext *kctx, KTokenSeq* tokens)
//{
//	int i, count = 0;
//	for(i = 0; i < tokens->EndIdx; i++) {
//		kTokenVar *tk = tokens->TokenList->TokenVarItems[i];
//		if(tk->symbol == KSymbol_void) {
//			tokens->EndIdx = i; //  f(void) = > f()
//			return;
//		}
//		if(tk->symbol == KSymbol_COMMA) {
//			tk->resolvedSyntaxInfo = K_NULLTOKEN->resolvedSyntaxInfo;
//			count++;
//		}
//	}
//	if(count == 0 && tokens->BeginIdx < tokens->EndIdx) {
//		KFieldSet(tokens->TokenList, tokens->TokenList->TokenItems[tokens->EndIdx], K_NULLTOKEN); // to ensure block
//		tokens->EndIdx += 1;
//	}
//}
//
//static KMETHOD PatternMatch_CStyleParam()
//{
//	VAR_PatternMatch(stmt, name, TokenList, BeginIdx, EndIdx);
//	int returnIdx = -1;
//	kToken *tk = TokenList->TokenItems[BeginIdx];
//	if(tk->resolvedSyntaxInfo->keyword == KSymbol_ParenthesisGroup) {
//		KTokenSeq param = {kNode_ns(stmt), RangeGroup(tk->GroupTokenList)};
//		CheckCStyleParam(kctx, &param);
//		kNode *block = ParseNewNode(kctx, param.ns, param.TokenList, &param.BeginIdx, param.EndIdx, ParseMetaPatternOption, NULL);
//		kNode_AddParsedObject(kctx, stmt, name, UPCAST(block));
//		returnIdx = BeginIdx + 1;
//	}
//	return (returnIdx);
//}
//
//static kParam *kNode_GetParamNULL(KonohaContext *kctx, kNode *stmt, kNameSpace* ns)
//{
//	kParam *pa = (kParam *)kNode_GetObjectNULL(kctx, stmt, KSymbol_ParamPattern);
//	if(pa == NULL || !IS_Param(pa)) {
//		kSyntax *syn = kSyntax_(kNode_ns(stmt), KSymbol_ParamPattern);
//		TypeNode(kctx, syn, stmt, ns, KClass_void);
//		if(kNode_IsError(stmt)) return NULL;
//	}
//	pa = (kParam *)kNode_GetObjectNULL(kctx, stmt, KSymbol_ParamPattern);
//	DBG_ASSERT(IS_Param(pa));
//	return pa;
//}
//
//static kbool_t SetParamType(KonohaContext *kctx, kNode *stmt, int n, kparamtype_t *p)
//{
//	kToken *typeToken  = KLIB kNode_GetToken(kctx, stmt, KSymbol_TypePattern, NULL);
//	kNode  *expr = KLIB kNode_GetNode(kctx, stmt, KSymbol_ExprPattern, NULL);
//	DBG_ASSERT(typeToken != NULL);
//	DBG_ASSERT(expr != NULL);
//	if(kNode_isSymbolTerm(expr)) {
//		kToken *tkN = expr->TermToken;
//		p[n].name = tkN->symbol;
//		p[n].typeAttr = Token_typeLiteral(typeToken);
//		return true;
//	}
//	return false;
//}
//
//static KMETHOD Statement_ParamDecl()
//{
//	VAR_TypeCheck(stmt, ns, reqc);
//	kToken *returnTypeToken = KLIB kNode_GetToken(kctx, stmt, KSymbol_TypePattern, NULL); // type
//	ktypeattr_t returnType =  (returnTypeToken == NULL) ? KType_void : Token_typeLiteral(returnTypeToken);
//	kParam *pa = NULL;
//	kNode *params = (kNode *)kNode_GetObjectNULL(kctx, stmt, KSymbol_ParamPattern);
//	if(params == NULL) {
//		pa = new_kParam(kctx, returnType, 0, NULL);
//	}
//	else if(IS_Node(params)) {
//		size_t i, psize = kNode_GetNodeListSize(kctx, params);
//		kparamtype_t *p = ALLOCA(kparamtype_t, psize);
//		for(i = 0; i < psize; i++) {
//			p[i].typeAttr = KType_void;
//			p[i].name = 0;
//			kNode *node = params->NodeList->NodeItems[i];
//			if(node->syn->keyword != KSymbol_TypeDeclPattern || !SetParamType(kctx, node, i, p)) {
//				KReturn(KLIB MessageNode(kctx, stmt, NULL, ns, ErrTag, "Argument(%d) No Type declaration", i));
//			}
//		}
//		pa = new_kParam(kctx, returnType, psize, p);
//	}
//	if(pa != NULL && IS_Param(pa)) {
//		KLIB kObjectProto_SetObject(kctx, stmt, KSymbol_ParamPattern, KType_Param, pa);
//		KReturn(kNode_Type(stmt, KNode_Done, KType_void));
//	}
//	KReturn(KLIB MessageNode(kctx, stmt, NULL, ns, ErrTag, "expected parameter declaration"));
//}
//
///* MethodDecl */
//
//static ktypeattr_t kNode_GetClassId(KonohaContext *kctx, kNode *stmt, kNameSpace *ns, ksymbol_t kw, ktypeattr_t defcid)
//{
//	kToken *tk = (kToken *)kNode_GetObjectNULL(kctx, stmt, kw);
//	if(tk == NULL || !IS_Token(tk)) {
//		return defcid;
//	}
//	else {
//		DBG_ASSERT(Token_isVirtualTypeLiteral(tk));
//		return Token_typeLiteral(tk);
//	}
//}
//
//static ksymbol_t kNode_GetMethodName(KonohaContext *kctx, kNode *stmt, kmethodn_t defmn)
//{
//	kToken *tk = (kToken *)kNode_GetObjectNULL(kctx, stmt, KSymbol_SymbolPattern);
//	return (tk == NULL) ? defmn : tk->symbol;
//}
//
//static KMETHOD Statement_MethodDecl()
//{
//	VAR_TypeCheck(stmt, ns, reqc);
//	static KFlagSymbolData MethodDeclFlag[] = {
//		{kMethod_Public}, {kMethod_Const}, {kMethod_Static},
//		{kMethod_Virtual}, {kMethod_Final}, {kMethod_Override},
//		{kMethod_Restricted},
//	};
//	if(MethodDeclFlag[0].symbol == 0) {   // this is a tricky technique
//		MethodDeclFlag[0].symbol = KSymbol_("@Public");
//		MethodDeclFlag[1].symbol = KSymbol_("@Const");
//		MethodDeclFlag[2].symbol = KSymbol_("@Static");
//		MethodDeclFlag[3].symbol = KSymbol_("@Virtual");
//		MethodDeclFlag[4].symbol = KSymbol_("@Final");
//		MethodDeclFlag[5].symbol = KSymbol_("@Override");
//		MethodDeclFlag[6].symbol = KSymbol_("@Restricted");
//	}
//	uintptr_t flag      = kNode_ParseFlag(kctx, stmt, MethodDeclFlag, 0);
//	ktypeattr_t typeId  = kNode_GetClassId(kctx, stmt, ns, KSymbol_("ClassName"), kObject_typeId(ns));
//	kmethodn_t mn       = kNode_GetMethodName(kctx, stmt, MN_new);
//	kParam *pa          = kNode_GetParamNULL(kctx, stmt, ns);
//	if(KType_Is(Singleton, typeId)) { flag |= kMethod_Static; }
//	if(KType_Is(Final, typeId)) { flag |= kMethod_Final; }
//	if(pa != NULL) {  // if pa is NULL, error is printed out.
//		INIT_GCSTACK();
//		kMethodVar *mtd = (kMethodVar *)KLIB new_kMethod(kctx, _GcStack, flag, typeId, mn, NULL);
//		KLIB kMethod_SetParam(kctx, mtd, pa->rtype, pa->psize, (kparamtype_t *)pa->paramtypeItems);
//		KMakeTrace(trace, sfp);
//		if((mtd = kNameSpace_AddMethod(kctx, ns, mtd, trace)) != NULL) {
//			kMethod_SetLazyCompilation(kctx, mtd, stmt, ns);
//		}
//		RESET_GCSTACK();
//	}
//	KReturn(kNode_Type(stmt, KNode_Done, KType_void));
//}
//
///* ------------------------------------------------------------------------ */
//
//#define PATTERN(T)  KSymbol_##T##Pattern
//#define GROUP(T)    KSymbol_##T##Group
//#define TOKEN(T)    KSymbol_##T
//
//static void DefineDefaultSyntax(KonohaContext *kctx, kNameSpace *ns)
//{
//	DBG_ASSERT(KSymbol_("$Param") == KSymbol_ParamPattern);
//	DBG_ASSERT(KSymbol_(".") == KSymbol_DOT);
//	DBG_ASSERT(KSymbol_(":") == KSymbol_COLON);
//	DBG_ASSERT(KSymbol_("true") == KSymbol_true);
//	DBG_ASSERT(KSymbol_("return") == KSymbol_return);
//	DBG_ASSERT(KSymbol_("new") == MN_new);
//	kFunc *TermFunc = KSugarFunc(ns, ParseTerm);
//	kFunc *OperatorFunc = KSugarFunc(ns, ParseOperator);
//	kFunc *MethodCallFunc = KSugarFunc(ns, TypeCheck_MethodCall);
//	kFunc *patternParseFunc = KSugarFunc(ns, Parse_Pattern);
//	kSyntaxVar *nullSyntax = (kSyntaxVar *)KNULL(Syntax);
//	nullSyntax->precedence_op2 = Precedence_CStyleStatementEnd;
//	nullSyntax->precedence_op1 = Precedence_CStyleStatementEnd;
//	nullSyntax->ParseFuncNULL = KSugarFunc(ns, Parse_Block);
//	KDEFINE_SYNTAX SYNTAX[] = {
//		{ KSymbol_END, },
//	};
//	kNameSpace_DefineSyntax(kctx, ns, SYNTAX, NULL);
//	((kTokenVar *)K_NULLTOKEN)->resolvedSyntaxInfo = kNameSpace_GetSyntax(kctx, ns, KSymbol_SEMICOLON);
//	KPARSERM->termParseFunc     = TermFunc;
//	KPARSERM->opParseFunc       = OperatorFunc;
//	KPARSERM->patternParseFunc  = patternParseFunc;
//	KPARSERM->methodTypeFunc    = MethodCallFunc;
//	// Syntax Rule
//	kSyntax_AddPattern(kctx, kSyntax_(ns, PATTERN(TypeDecl)), "$Type $Expr", 0, NULL);
//	kSyntax_AddPattern(kctx, kSyntax_(ns, PATTERN(MethodDecl)), "$Type [ClassName: $Type] [$Symbol] $Param [$Block]", 0, NULL);
//	kSyntax_AddPattern(kctx, kSyntax_(ns, TOKEN(if)), "\"if\" \"(\" $Expr \")\" $Block [\"else\" else: $Block]", 0, NULL);
//	kSyntax_AddPattern(kctx, kSyntax_(ns, TOKEN(else)), "\"else\" $Block", 0, NULL);
//	kSyntax_AddPattern(kctx, kSyntax_(ns, TOKEN(return)), "\"return\" [$Expr]", 0, NULL);
//}
