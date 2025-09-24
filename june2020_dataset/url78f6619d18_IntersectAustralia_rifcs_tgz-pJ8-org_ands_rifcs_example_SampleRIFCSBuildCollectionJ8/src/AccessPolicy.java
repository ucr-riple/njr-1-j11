/*    */ package org.ands.rifcs.base;
/*    */ 
/*    */ import org.w3c.dom.Node;
/*    */ 
/*    */ public class AccessPolicy extends RIFCSElement
/*    */ {
/*    */   protected AccessPolicy(Node n)
/*    */     throws RIFCSException
/*    */   {
/* 41 */     super(n, "accessPolicy");
/*    */   }
/*    */ 
/*    */   public void setValue(String value)
/*    */   {
/* 53 */     super.setTextContent(value);
/*    */   }
/*    */ 
/*    */   public String getValue()
/*    */   {
/* 64 */     return super.getTextContent();
/*    */   }
/*    */ }

/* Location:           /Users/przemyslaw/Downloads/rifcs-api-1.3.0.jar
 * Qualified Name:     org.ands.rifcs.base.AccessPolicy
 * JD-Core Version:    0.6.2
 */