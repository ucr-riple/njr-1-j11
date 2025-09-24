/*     */ package org.ands.rifcs.base;
/*     */ 
/*     */ import org.w3c.dom.Node;
/*     */ 
/*     */ public class Spatial extends RIFCSElement
/*     */ {
/*     */   protected Spatial(Node n)
/*     */     throws RIFCSException
/*     */   {
/*  41 */     super(n, "spatial");
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
/*     */   public void setLanguage(String lang)
/*     */   {
/*  78 */     super.setAttributeValueNS("http://www.w3.org/XML/1998/namespace", "xml:lang", lang);
/*     */   }
/*     */ 
/*     */   public String getLanguage()
/*     */   {
/*  91 */     return super.getAttributeValueNS("http://www.w3.org/XML/1998/namespace", "xml:lang");
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
 * Qualified Name:     org.ands.rifcs.base.Spatial
 * JD-Core Version:    0.6.2
 */