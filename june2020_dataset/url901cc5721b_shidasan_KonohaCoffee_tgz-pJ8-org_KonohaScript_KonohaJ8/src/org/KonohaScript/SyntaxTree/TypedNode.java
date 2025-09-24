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

package org.KonohaScript.SyntaxTree;

import org.KonohaScript.KToken;
import org.KonohaScript.KClass;
import org.KonohaScript.CodeGen.ASTVisitor;

class NotSupportedNodeError extends RuntimeException {
	private static final long serialVersionUID = 1L;

	NotSupportedNodeError() {
		super();
	}
}

public abstract class TypedNode {

	TypedNode ParentNode = null;
	TypedNode PreviousNode = null;
	TypedNode NextNode = null;

	public final TypedNode GetHeadNode() {
		TypedNode Node = this;
		while(Node.PreviousNode != null) {
			Node = Node.ParentNode;
		}
		return Node;
	}

	public final TypedNode GetTailNode() {
		TypedNode Node = this;
		while(Node.NextNode != null) {
			Node = Node.NextNode;
		}
		return Node;
	}
	
	public final void LinkNode(TypedNode Node) {
		Node.PreviousNode = this;
		this.NextNode = Node;
	}

	public TypedNode(KClass TypeInfo) {
		this.TypeInfo = TypeInfo;
		this.SourceToken = null;
	}

	public TypedNode(KClass TypeInfo, KToken SourceToken) {
		this.TypeInfo = TypeInfo;
		this.SourceToken = SourceToken;
	}

	public KClass TypeInfo;
	public KToken SourceToken;
	
	public boolean Evaluate(ASTVisitor Visitor) {
		throw new NotSupportedNodeError();
	}

	public final boolean IsError() {
		return (this instanceof ErrorNode);
	}
	
}
