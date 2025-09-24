/*     */ package org.ands.rifcs.base;
/*     */ 
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ 
/*     */ public class RelatedInfo extends RIFCSElement
/*     */ {
/*  33 */   private Identifier identifier = null;
/*     */ 
/*     */   protected RelatedInfo(Node n)
/*     */     throws RIFCSException
/*     */   {
/*  45 */     super(n, "relatedInfo");
/*  46 */     initStructures();
/*     */   }
/*     */ 
/*     */   public void setType(String type)
/*     */   {
/*  58 */     super.setAttributeValue("type", type);
/*     */   }
/*     */ 
/*     */   public String getType()
/*     */   {
/*  71 */     return super.getAttributeValue("type");
/*     */   }
/*     */ 
/*     */   public Identifier newIdentifier()
/*     */     throws RIFCSException
/*     */   {
/*  89 */     return new Identifier(newElement("identifier"));
/*     */   }
/*     */ 
/*     */   public void setIdentifier(String identifier, String type)
/*     */     throws RIFCSException
/*     */   {
/* 105 */     this.identifier = newIdentifier();
/* 106 */     this.identifier.setValue(identifier);
/* 107 */     this.identifier.setType(type);
/* 108 */     getElement().appendChild(this.identifier.getElement());
/*     */   }
/*     */ 
/*     */   public Identifier getIdentifier()
/*     */   {
/* 120 */     return this.identifier;
/*     */   }
/*     */ 
/*     */   public void setTitle(String title)
/*     */   {
/* 132 */     Element e = newElement("title");
/* 133 */     e.setTextContent(title);
/* 134 */     getElement().appendChild(e);
/*     */   }
/*     */ 
/*     */   public String getTitle()
/*     */   {
/* 146 */     NodeList nl = super.getElements("title");
/* 147 */     if (nl.getLength() == 1)
/*     */     {
/* 149 */       return nl.item(0).getTextContent();
/*     */     }
/*     */ 
/* 152 */     return null;
/*     */   }
/*     */ 
/*     */   public void setNotes(String notes)
/*     */   {
/* 164 */     Element e = newElement("notes");
/* 165 */     e.setTextContent(notes);
/* 166 */     getElement().appendChild(e);
/*     */   }
/*     */ 
/*     */   public String getNotes()
/*     */   {
/* 178 */     NodeList nl = super.getElements("notes");
/* 179 */     if (nl.getLength() == 1)
/*     */     {
/* 181 */       return nl.item(0).getTextContent();
/*     */     }
/*     */ 
/* 184 */     return null;
/*     */   }
/*     */ 
/*     */   private void initStructures()
/*     */     throws RIFCSException
/*     */   {
/* 191 */     NodeList nl = super.getElements("identifier");
/*     */ 
/* 193 */     if (nl.getLength() > 0)
/*     */     {
/* 195 */       this.identifier = new Identifier(nl.item(0));
/*     */     }
/*     */   }
/*     */ }

/* Location:           /Users/przemyslaw/Downloads/rifcs-api-1.3.0.jar
 * Qualified Name:     org.ands.rifcs.base.RelatedInfo
 * JD-Core Version:    0.6.2
 */