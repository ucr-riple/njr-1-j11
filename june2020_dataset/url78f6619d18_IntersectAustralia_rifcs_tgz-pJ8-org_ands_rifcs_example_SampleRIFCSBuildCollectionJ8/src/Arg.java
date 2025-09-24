/*     */ package org.ands.rifcs.base;
/*     */ 
/*     */ import org.w3c.dom.Node;
/*     */ 
/*     */ public class Arg extends RIFCSElement
/*     */ {
/*     */   protected Arg(Node n)
/*     */     throws RIFCSException
/*     */   {
/*  41 */     super(n, "arg");
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
/*     */   public void setRequired(String required)
/*     */   {
/*  78 */     super.setAttributeValue("required", required);
/*     */   }
/*     */ 
/*     */   public String getRequired()
/*     */   {
/*  91 */     return super.getAttributeValue("required");
/*     */   }
/*     */ 
/*     */   public void setUse(String use)
/*     */   {
/* 103 */     super.setAttributeValue("use", use);
/*     */   }
/*     */ 
/*     */   public String getUse()
/*     */   {
/* 116 */     return super.getAttributeValue("use");
/*     */   }
/*     */ 
/*     */   public void setName(String name)
/*     */   {
/* 128 */     super.setTextContent(name);
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/* 140 */     return super.getTextContent();
/*     */   }
/*     */ }

/* Location:           /Users/przemyslaw/Downloads/rifcs-api-1.3.0.jar
 * Qualified Name:     org.ands.rifcs.base.Arg
 * JD-Core Version:    0.6.2
 */