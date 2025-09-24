/*     */ package org.ands.rifcs.base;
/*     */ 
/*     */ import org.w3c.dom.Node;
/*     */ 
/*     */ public class RightsTypedInfo extends RIFCSElement
/*     */ {
/*     */   protected RightsTypedInfo(Node n)
/*     */     throws RIFCSException
/*     */   {
/*  41 */     super(n, n.getNodeName());
/*     */   }
/*     */ 
/*     */   public void setType(String type)
/*     */   {
/*  53 */     super.setAttributeValue("type", type);
/*     */   }
/*     */ 
/*     */   public String getType()
/*     */   {
/*  66 */     return super.getAttributeValue("type");
/*     */   }
/*     */ 
/*     */   public void setRightsUri(String rightsUri)
/*     */   {
/*  78 */     super.setAttributeValue("rightsUri", rightsUri);
/*     */   }
/*     */ 
/*     */   public String getRightsUri()
/*     */   {
/*  91 */     return super.getAttributeValue("rightsUri");
/*     */   }
/*     */ 
/*     */   public void setValue(String value)
/*     */   {
/* 103 */     super.setTextContent(value);
/*     */   }
/*     */ 
/*     */   public String getValue()
/*     */   {
/* 115 */     return super.getTextContent();
/*     */   }
/*     */ }

/* Location:           /Users/przemyslaw/Downloads/rifcs-api-1.3.0.jar
 * Qualified Name:     org.ands.rifcs.base.RightsTypedInfo
 * JD-Core Version:    0.6.2
 */