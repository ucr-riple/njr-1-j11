/*     */ package org.ands.rifcs.base;
/*     */ 
/*     */ import java.util.Date;
/*     */ import org.w3c.dom.Node;
/*     */ 
/*     */ public class DateElement extends RIFCSElement
/*     */ {
/*     */   protected DateElement(Node n)
/*     */     throws RIFCSException
/*     */   {
/*  43 */     super(n, "date");
/*     */   }
/*     */ 
/*     */   public DateElement(Node n, String name) throws RIFCSException
/*     */   {
/*  48 */     super(n, name);
/*     */   }
/*     */ 
/*     */   public void setType(String type)
/*     */   {
/*  60 */     super.setAttributeValue("type", type);
/*     */   }
/*     */ 
/*     */   public String getType()
/*     */   {
/*  73 */     return super.getAttributeValue("type");
/*     */   }
/*     */ 
/*     */   public void setValue(String value)
/*     */   {
/*  85 */     super.setTextContent(value);
/*     */   }
/*     */ 
/*     */   public void setValue(Date value)
/*     */   {
/*  97 */     super.setTextContent(RegistryObject.formatDate(value));
/*     */   }
/*     */ 
/*     */   public String getValue()
/*     */   {
/* 109 */     return super.getTextContent();
/*     */   }
/*     */ }

/* Location:           /Users/przemyslaw/Downloads/rifcs-api-1.3.0.jar
 * Qualified Name:     org.ands.rifcs.base.DateElement
 * JD-Core Version:    0.6.2
 */