/*     */ package org.ands.rifcs.base;
/*     */ 
/*     */ import java.util.Date;
/*     */ import org.w3c.dom.Node;
/*     */ 
/*     */ public class CommonDateElement extends RIFCSElement
/*     */ {
/*     */   protected CommonDateElement(Node n)
/*     */     throws RIFCSException
/*     */   {
/*  43 */     super(n, n.getNodeName());
/*     */   }
/*     */ 
/*     */   public void setDateFormat(String type)
/*     */   {
/*  55 */     super.setAttributeValue("dateFormat", type);
/*     */   }
/*     */ 
/*     */   public String getDateFormat()
/*     */   {
/*  68 */     return super.getAttributeValue("dateFormat");
/*     */   }
/*     */ 
/*     */   public void setValue(String value)
/*     */   {
/*  80 */     super.setTextContent(value);
/*     */   }
/*     */ 
/*     */   public void setValue(Date value)
/*     */   {
/*  92 */     super.setTextContent(RegistryObject.formatDate(value));
/*     */   }
/*     */ 
/*     */   public String getValue()
/*     */   {
/* 104 */     return super.getTextContent();
/*     */   }
/*     */ }

/* Location:           /Users/przemyslaw/Downloads/rifcs-api-1.3.0.jar
 * Qualified Name:     org.ands.rifcs.base.CommonDateElement
 * JD-Core Version:    0.6.2
 */