/*     */ package org.ands.rifcs.base;
/*     */ 
/*     */ import java.util.Date;
/*     */ import org.w3c.dom.Node;
/*     */ 
/*     */ public class TemporalCoverageDate extends RIFCSElement
/*     */ {
/*     */   protected TemporalCoverageDate(Node n)
/*     */     throws RIFCSException
/*     */   {
/*  43 */     super(n, "date");
/*     */   }
/*     */ 
/*     */   public void setDateFormat(String dateFormat)
/*     */   {
/*  54 */     super.setAttributeValue("dateFormat", dateFormat);
/*     */   }
/*     */ 
/*     */   public String DateFormat()
/*     */   {
/*  67 */     return super.getAttributeValue("dateFormat");
/*     */   }
/*     */ 
/*     */   public void setType(String type)
/*     */   {
/*  80 */     super.setAttributeValue("type", type);
/*     */   }
/*     */ 
/*     */   public String getType()
/*     */   {
/*  93 */     return super.getAttributeValue("type");
/*     */   }
/*     */ 
/*     */   public void setValue(String value)
/*     */   {
/* 105 */     super.setTextContent(value);
/*     */   }
/*     */ 
/*     */   public void setValue(Date value)
/*     */   {
/* 117 */     super.setTextContent(RegistryObject.formatDate(value));
/*     */   }
/*     */ 
/*     */   public String getValue()
/*     */   {
/* 129 */     return super.getTextContent();
/*     */   }
/*     */ }

/* Location:           /Users/przemyslaw/Downloads/rifcs-api-1.3.0.jar
 * Qualified Name:     org.ands.rifcs.base.TemporalCoverageDate
 * JD-Core Version:    0.6.2
 */