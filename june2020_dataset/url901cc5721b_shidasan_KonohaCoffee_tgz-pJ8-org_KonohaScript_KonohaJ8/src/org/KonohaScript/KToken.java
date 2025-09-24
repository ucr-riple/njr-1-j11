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

public final class KToken {

	public long uline;
	public String ParsedText;

	public boolean EqualsText(String text) {
		return ParsedText.equals(text);
	}

	public KToken(String text) {
		this.ParsedText = text;
	}

	public KToken(String text, long uline) {
		this.ParsedText = text;
		this.uline = uline;
	}
	
	final static int ErrorTokenFlag = 1;
	final static int GroupTokenFlag = (1 << 1);
	int flag;

	public boolean IsErrorToken() {
		return ((flag & ErrorTokenFlag) == ErrorTokenFlag);
	}

	public boolean IsGroupToken() {
		return ((flag & GroupTokenFlag) == GroupTokenFlag);
	}


	public KSyntax ResolvedSyntax;
	Object ResolvedObject;

	public void SetGroup(KSyntax syntax, ArrayList<KToken> group) {
		ResolvedSyntax = syntax;
		ResolvedObject = group;
		flag |= GroupTokenFlag;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<KToken> GetGroupList() {
		assert (IsGroupToken());
		return (ArrayList<KToken>) ResolvedObject;
	}

	public String SetErrorMessage(String msg) {
		ResolvedSyntax = KSyntax.ErrorSyntax;
		flag |= ErrorTokenFlag;
		ResolvedObject = msg;
		return msg;
	}

	// Debug
	private final static String Tab = "  ";
	void Dump(int Level) {
		String Syntax = (ResolvedSyntax == null) ? "null" : ResolvedSyntax.SyntaxName;
		System.out.println("[" + Syntax + "+" + (int) uline + "] '" + ParsedText + "'");
		if (IsGroupToken()) {
			ArrayList<KToken> group = GetGroupList();
			DumpTokenList(Level + 1, null, group, 0, group.size());
		}
	}

	public static void DumpTokenList(int Level, String Message, ArrayList<KToken> TokenList, int BeginIdx, int EndIdx) {
		if(Message != null) {
			KonohaDebug.Indent(Level, Tab);			
			System.out.println("Begin: " + Message);
			Level++;
		}
		for (int i = BeginIdx; i < EndIdx; i++) {
			KToken Token = TokenList.get(i);
			KonohaDebug.Indent(Level, Tab);			
			System.out.print("<"+i +"> "); 
			Token.Dump(Level);
		}
		if(Message != null) {
			Level--;
			KonohaDebug.Indent(Level, Tab);			
			System.out.println("End: " + Message);
		}
	}

	public static void DumpTokenList(ArrayList<KToken> TokenList) {
		DumpTokenList(0, null, TokenList, 0, TokenList.size());
	}

}
