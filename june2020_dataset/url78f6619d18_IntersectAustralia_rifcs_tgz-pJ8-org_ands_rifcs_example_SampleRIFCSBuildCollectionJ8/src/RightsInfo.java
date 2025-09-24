/*    */ package org.ands.rifcs.base;
/*    */ 
/*    */ import org.w3c.dom.Node;
/*    */ 
/*    */ public class RightsInfo extends RIFCSElement
/*    */ {
/*    */   protected RightsInfo(Node n)
/*    */     throws RIFCSException
/*    */   {
/* 41 */     super(n, n.getNodeName());
/*    */   }
/*    */ 
/*    */   public void setRightsUri(String rightsUri)
/*    */   {
/* 53 */     super.setAttributeValue("rightsUri", rightsUri);
/*    */   }
/*    */ 
/*    */   public String getRightsUri()
/*    */   {
/* 66 */     return super.getAttributeValue("rightsUri");
/*    */   }
/*    */ 
/*    */   public void setValue(String value)
/*    */   {
/* 78 */     super.setTextContent(value);
/*    */   }
/*    */ 
/*    */   public String getValue()
/*    */   {
/* 90 */     return super.getTextContent();
/*    */   }
/*    */ }

/* Location:           /Users/przemyslaw/Downloads/rifcs-api-1.3.0.jar
 * Qualified Name:     org.ands.rifcs.base.RightsInfo
 * JD-Core Version:    0.6.2
 */