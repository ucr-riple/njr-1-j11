/*    */ package org.ands.rifcs.base;
/*    */ 
/*    */ import org.w3c.dom.Node;
/*    */ 
/*    */ public class CitationDate extends RIFCSElement
/*    */ {
/*    */   protected CitationDate(Node n)
/*    */     throws RIFCSException
/*    */   {
/* 41 */     super(n, "date");
/*    */   }
/*    */ 
/*    */   public void setType(String type)
/*    */   {
/* 53 */     super.setAttributeValue("type", type);
/*    */   }
/*    */ 
/*    */   public String getType()
/*    */   {
/* 66 */     return super.getAttributeValue("type");
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
 * Qualified Name:     org.ands.rifcs.base.CitationDate
 * JD-Core Version:    0.6.2
 */