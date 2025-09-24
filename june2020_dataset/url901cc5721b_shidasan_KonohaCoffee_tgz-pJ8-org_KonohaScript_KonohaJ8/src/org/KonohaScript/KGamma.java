package org.KonohaScript;

import java.lang.reflect.InvocationTargetException;

import org.KonohaScript.SyntaxTree.*;

//struct KGammaStack {
//KGammaStackDecl *varItems;
//size_t varsize;
//size_t capacity;
//size_t allocsize;  // set size if not allocated  (by default on stack)
//} ;
//
//struct KGammaLocalData {
//khalfflag_t  flag;   khalfflag_t cflag;
//KClass   *thisClass;
//kMethod  *currentWorkingMethod;
//struct KGammaStack    localScope;
//} ;

public class KGamma {
	
	KClass GetLocalType(String Symbol) {
		return null;
	}
	
	int GetLocalIndex(String Symbol) {
		return -1;
	}

	
	
	public static TypedNode TypeNode(KGamma Gamma, UntypedNode Node, KClass ReqType) {
		TypedNode TNode;
		try {
			TNode = (TypedNode)Node.Syntax.TypeMethod.invoke(Node.Syntax.TypeObject, Gamma, Node, ReqType);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			TNode = new ErrorNode(ReqType, Node.KeyToken, "internal error: " + e);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			TNode = new ErrorNode(ReqType, Node.KeyToken, "internal error: " + e);
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			TNode = new ErrorNode(ReqType, Node.KeyToken, "internal error: " + e);
		}
		if(TNode == null) {
			TNode = new ErrorNode(ReqType, Node.KeyToken, "undefined type checker: " + Node.Syntax);
		}
		return TNode;
	}
	
	public static TypedNode TypeCheckNode(KGamma Gamma, UntypedNode UNode, KClass TypeInfo, int TypeCheckPolicy) {
		TypedNode TNode, TPrevNode = null;
		KClass NodeTypeInfo = TypeInfo;
		while(UNode != null) {
			NodeTypeInfo = (UNode.NextNode != null) ? KClass.VoidType : TypeInfo;
			TNode = TypeCheckEachNode(Gamma, TypeNode(Gamma, UNode, NodeTypeInfo), NodeTypeInfo, TypeCheckPolicy);
			if(TPrevNode != null && TPrevNode != TNode) {
				TPrevNode.LinkNode(TNode);
			}
			if(TNode.IsError()) {
				break;
			}
			TPrevNode = TNode;
			UNode = UNode.NextNode;
		}
		return TPrevNode.GetHeadNode();
	}

	public static TypedNode TypeCheckEachNode(KGamma Gamma, TypedNode Node, KClass ReqType, int TypeCheckPolicy) {
//		if(Node.TypeInfo == null) {
//			
//		}
		return Node;
	}

}
