/*     */ package org.ands.rifcs.base;
/*     */ 
/*     */ import org.w3c.dom.Node;
/*     */ 
/*     */ public class Subject extends RIFCSElement
/*     */ {
/*     */   protected Subject(Node n)
/*     */     throws RIFCSException
/*     */   {
/*  41 */     super(n, "subject");
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
/*     */   public void setTermIdentifier(String termIdentifier)
/*     */   {
/*  77 */     super.setAttributeValue("termIdentifier", termIdentifier);
/*     */   }
/*     */ 
/*     */   public String getTermIdentifier()
/*     */   {
/*  90 */     return super.getAttributeValue("termIdentifier");
/*     */   }
/*     */ 
/*     */   public void setLanguage(String lang)
/*     */   {
/* 102 */     super.setAttributeValueNS("http://www.w3.org/XML/1998/namespace", "xml:lang", lang);
/*     */   }
/*     */ 
/*     */   public String getLanguage()
/*     */   {
/* 115 */     return super.getAttributeValueNS("http://www.w3.org/XML/1998/namespace", "xml:lang");
/*     */   }
/*     */ 
/*     */   public void setValue(String value)
/*     */   {
/* 127 */     super.setTextContent(value);
/*     */   }
/*     */ 
/*     */   public String getValue()
/*     */   {
/* 139 */     return super.getTextContent();
/*     */   }
/*     */ }

/* Location:           /Users/przemyslaw/Downloads/rifcs-api-1.3.0.jar
 * Qualified Name:     org.ands.rifcs.base.Subject
 * JD-Core Version:    0.6.2
 */