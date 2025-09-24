/*     */ package org.ands.rifcs.base;
/*     */ 
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ 
/*     */ public class CitationInfo extends RIFCSElement
/*     */ {
/*  33 */   private CitationMetadata cm = null;
/*     */ 
/*     */   protected CitationInfo(Node n)
/*     */     throws RIFCSException
/*     */   {
/*  45 */     super(n, "citationInfo");
/*  46 */     initStructures();
/*     */   }
/*     */ 
/*     */   public CitationMetadata newCitationMetadata()
/*     */     throws RIFCSException
/*     */   {
/*  64 */     return new CitationMetadata(newElement("citationMetadata"));
/*     */   }
/*     */ 
/*     */   public void setCitation(String citation, String style)
/*     */     throws RIFCSException
/*     */   {
/*  81 */     Element e = newElement("fullCitation");
/*  82 */     e.setTextContent(citation);
/*  83 */     if (style != null)
/*     */     {
/*  85 */       e.setAttribute("style", style);
/*     */     }
/*  87 */     getElement().appendChild(e);
/*     */   }
/*     */ 
/*     */   public String getCitation()
/*     */   {
/* 101 */     NodeList nl = super.getElements("fullCitation");
/* 102 */     if (nl.getLength() > 0)
/*     */     {
/* 104 */       return nl.item(0).getTextContent();
/*     */     }
/*     */ 
/* 107 */     return null;
/*     */   }
/*     */ 
/*     */   public String getCitationStyle()
/*     */   {
/* 121 */     NodeList nl = super.getElements("fullCitation");
/* 122 */     if (nl.getLength() > 0)
/*     */     {
/* 124 */       return ((Element)nl.item(0)).getAttribute("style");
/*     */     }
/*     */ 
/* 127 */     return null;
/*     */   }
/*     */ 
/*     */   public void addCitationMetadata(CitationMetadata citationMetadata)
/*     */   {
/* 139 */     getElement().appendChild(citationMetadata.getElement());
/* 140 */     this.cm = citationMetadata;
/*     */   }
/*     */ 
/*     */   public CitationMetadata getCitationMetadata()
/*     */   {
/* 152 */     return this.cm;
/*     */   }
/*     */ 
/*     */   private void initStructures()
/*     */     throws RIFCSException
/*     */   {
/* 159 */     NodeList nl = super.getElements("citationMetadata");
/*     */ 
/* 161 */     if (nl.getLength() > 0)
/*     */     {
/* 163 */       this.cm = new CitationMetadata(nl.item(0));
/*     */     }
/*     */   }
/*     */ }

/* Location:           /Users/przemyslaw/Downloads/rifcs-api-1.3.0.jar
 * Qualified Name:     org.ands.rifcs.base.CitationInfo
 * JD-Core Version:    0.6.2
 */