/*     */ package org.ands.rifcs.base;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ 
/*     */ public class RelatedObject extends RIFCSElement
/*     */ {
/*  36 */   List<Relation> relations = new ArrayList();
/*     */ 
/*     */   protected RelatedObject(Node n)
/*     */     throws RIFCSException
/*     */   {
/*  48 */     super(n, "relatedObject");
/*  49 */     initStructures();
/*     */   }
/*     */ 
/*     */   public void setKey(String keyValue)
/*     */   {
/*  61 */     Element key = newElement("key");
/*  62 */     key.setTextContent(keyValue);
/*  63 */     getElement().appendChild(key);
/*     */   }
/*     */ 
/*     */   public String getKey()
/*     */   {
/*  75 */     NodeList nl = super.getElements("key");
/*  76 */     if (nl.getLength() == 1)
/*     */     {
/*  78 */       return nl.item(0).getTextContent();
/*     */     }
/*     */ 
/*  81 */     return null;
/*     */   }
/*     */ 
/*     */   public Relation newRelation()
/*     */     throws RIFCSException
/*     */   {
/*  99 */     return new Relation(newElement("relation"));
/*     */   }
/*     */ 
/*     */   public void addRelation(String type, String url, String description, String descriptionLanguage)
/*     */     throws RIFCSException
/*     */   {
/* 120 */     Relation relation = newRelation();
/*     */ 
/* 122 */     relation.setType(type);
/*     */ 
/* 124 */     if (url != null)
/*     */     {
/* 126 */       relation.setURL(url);
/*     */     }
/*     */ 
/* 129 */     if (description != null)
/*     */     {
/* 131 */       relation.setDescription(description);
/*     */     }
/*     */ 
/* 134 */     if (descriptionLanguage != null)
/*     */     {
/* 136 */       relation.setDescriptionLanguage(descriptionLanguage);
/*     */     }
/*     */ 
/* 139 */     addRelation(relation);
/*     */   }
/*     */ 
/*     */   public List<Relation> getRelations()
/*     */   {
/* 151 */     return this.relations;
/*     */   }
/*     */ 
/*     */   public void addRelation(Relation relation)
/*     */   {
/* 163 */     getElement().appendChild(relation.getElement());
/* 164 */     this.relations.add(relation);
/*     */   }
/*     */ 
/*     */   private void initStructures()
/*     */     throws RIFCSException
/*     */   {
/* 171 */     NodeList nl = super.getElements("relation");
/*     */ 
/* 173 */     for (int i = 0; i < nl.getLength(); i++)
/*     */     {
/* 175 */       this.relations.add(new Relation(nl.item(i)));
/*     */     }
/*     */   }
/*     */ }

/* Location:           /Users/przemyslaw/Downloads/rifcs-api-1.3.0.jar
 * Qualified Name:     org.ands.rifcs.base.RelatedObject
 * JD-Core Version:    0.6.2
 */