/*     */ package org.ands.rifcs.base;
/*     */ 
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ 
/*     */ public class Relation extends RIFCSElement
/*     */ {
/*     */   protected Relation(Node n)
/*     */     throws RIFCSException
/*     */   {
/*  43 */     super(n, "relation");
/*     */   }
/*     */ 
/*     */   public void setType(String type)
/*     */   {
/*  55 */     super.setAttributeValue("type", type);
/*     */   }
/*     */ 
/*     */   public String getType()
/*     */   {
/*  68 */     return super.getAttributeValue("type");
/*     */   }
/*     */ 
/*     */   public void setDescription(String descValue)
/*     */   {
/*  80 */     Element desc = newElement("description");
/*  81 */     desc.setTextContent(descValue);
/*  82 */     getElement().appendChild(desc);
/*     */   }
/*     */ 
/*     */   public String getDescription()
/*     */   {
/*  94 */     NodeList nl = super.getElements("description");
/*  95 */     if (nl.getLength() == 1)
/*     */     {
/*  97 */       return nl.item(0).getTextContent();
/*     */     }
/*     */ 
/* 100 */     return null;
/*     */   }
/*     */ 
/*     */   public void setDescriptionLanguage(String lang)
/*     */   {
/* 112 */     NodeList nl = super.getElements("description");
/* 113 */     if (nl.getLength() == 1)
/*     */     {
/* 115 */       ((Element)nl.item(0)).setAttributeNS("http://www.w3.org/XML/1998/namespace", "xml:lang", lang);
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getDescriptionLanguage()
/*     */   {
/* 129 */     NodeList nl = super.getElements("description");
/* 130 */     if (nl.getLength() == 1)
/*     */     {
/* 132 */       return ((Element)nl.item(0)).getAttributeNS("http://www.w3.org/XML/1998/namespace", "xml:lang");
/*     */     }
/*     */ 
/* 135 */     return null;
/*     */   }
/*     */ 
/*     */   public void setURL(String urlValue)
/*     */   {
/* 148 */     Element url = newElement("url");
/* 149 */     url.setTextContent(urlValue);
/* 150 */     getElement().appendChild(url);
/*     */   }
/*     */ 
/*     */   public String getURL()
/*     */   {
/* 163 */     NodeList nl = super.getElements("url");
/* 164 */     if (nl.getLength() == 1)
/*     */     {
/* 166 */       return nl.item(0).getTextContent();
/*     */     }
/*     */ 
/* 169 */     return null;
/*     */   }
/*     */ }

/* Location:           /Users/przemyslaw/Downloads/rifcs-api-1.3.0.jar
 * Qualified Name:     org.ands.rifcs.base.Relation
 * JD-Core Version:    0.6.2
 */