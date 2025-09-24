/*     */ package org.ands.rifcs.base;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ 
/*     */ public class CitationMetadata extends RIFCSElement
/*     */ {
/*  36 */   private Identifier identifier = null;
/*  37 */   private List<Contributor> names = new ArrayList();
/*  38 */   private List<CitationDate> dates = new ArrayList();
/*     */ 
/*     */   protected CitationMetadata(Node n)
/*     */     throws RIFCSException
/*     */   {
/*  50 */     super(n, "citationMetadata");
/*  51 */     initStructures();
/*     */   }
/*     */ 
/*     */   public Contributor newContributor()
/*     */     throws RIFCSException
/*     */   {
/*  69 */     return new Contributor(newElement("contributor"));
/*     */   }
/*     */ 
/*     */   public CitationDate newCitationDate()
/*     */     throws RIFCSException
/*     */   {
/*  87 */     return new CitationDate(newElement("date"));
/*     */   }
/*     */ 
/*     */   public Identifier newIdentifier()
/*     */     throws RIFCSException
/*     */   {
/* 105 */     return new Identifier(newElement("identifier"));
/*     */   }
/*     */ 
/*     */   public void setIdentifier(String identifier, String type)
/*     */     throws RIFCSException
/*     */   {
/* 121 */     this.identifier = newIdentifier();
/* 122 */     this.identifier.setValue(identifier);
/* 123 */     this.identifier.setType(type);
/* 124 */     getElement().appendChild(this.identifier.getElement());
/*     */   }
/*     */ 
/*     */   public Identifier getIdentifier()
/*     */   {
/* 137 */     return this.identifier;
/*     */   }
/*     */ 
/*     */   public void setTitle(String title)
/*     */   {
/* 149 */     Element e = newElement("title");
/* 150 */     e.setTextContent(title);
/* 151 */     getElement().appendChild(e);
/*     */   }
/*     */ 
/*     */   public String getTitle()
/*     */   {
/* 164 */     NodeList nl = super.getElements("title");
/* 165 */     if (nl.getLength() == 1)
/*     */     {
/* 167 */       return nl.item(0).getTextContent();
/*     */     }
/*     */ 
/* 170 */     return null;
/*     */   }
/*     */ 
/*     */   public void setURL(String url)
/*     */   {
/* 182 */     Element value = newElement("url");
/* 183 */     value.setTextContent(url);
/* 184 */     getElement().appendChild(value);
/*     */   }
/*     */ 
/*     */   public String getURL()
/*     */   {
/* 196 */     NodeList nl = super.getElements("url");
/* 197 */     if (nl.getLength() == 1)
/*     */     {
/* 199 */       return nl.item(0).getTextContent();
/*     */     }
/*     */ 
/* 202 */     return null;
/*     */   }
/*     */ 
/*     */   public void setEdition(String edition)
/*     */   {
/* 214 */     Element e = newElement("edition");
/* 215 */     e.setTextContent(edition);
/* 216 */     getElement().appendChild(e);
/*     */   }
/*     */ 
/*     */   public String getEdition()
/*     */   {
/* 228 */     NodeList nl = super.getElements("edition");
/* 229 */     if (nl.getLength() == 1)
/*     */     {
/* 231 */       return nl.item(0).getTextContent();
/*     */     }
/*     */ 
/* 234 */     return null;
/*     */   }
/*     */ 
/*     */   public void setPublisher(String publisher)
/*     */   {
/* 245 */     Element e = newElement("publisher");
/* 246 */     e.setTextContent(publisher);
/* 247 */     getElement().appendChild(e);
/*     */   }
/*     */ 
/*     */   public String getPublisher()
/*     */   {
/* 259 */     NodeList nl = super.getElements("publisher");
/* 260 */     if (nl.getLength() == 1)
/*     */     {
/* 262 */       return nl.item(0).getTextContent();
/*     */     }
/*     */ 
/* 265 */     return null;
/*     */   }
/*     */ 
/*     */   public void setPlacePublished(String placePublished)
/*     */   {
/* 277 */     Element e = newElement("placePublished");
/* 278 */     e.setTextContent(placePublished);
/* 279 */     getElement().appendChild(e);
/*     */   }
/*     */ 
/*     */   public String getPlacePublished()
/*     */   {
/* 291 */     NodeList nl = super.getElements("placePublished");
/* 292 */     if (nl.getLength() == 1)
/*     */     {
/* 294 */       return nl.item(0).getTextContent();
/*     */     }
/*     */ 
/* 297 */     return null;
/*     */   }
/*     */ 
/*     */   public void setContext(String context)
/*     */   {
/* 310 */     Element e = newElement("context");
/* 311 */     e.setTextContent(context);
/* 312 */     getElement().appendChild(e);
/*     */   }
/*     */ 
/*     */   public String getContext()
/*     */   {
/* 325 */     NodeList nl = super.getElements("context");
/* 326 */     if (nl.getLength() == 1)
/*     */     {
/* 328 */       return nl.item(0).getTextContent();
/*     */     }
/*     */ 
/* 331 */     return null;
/*     */   }
/*     */ 
/*     */   public void addContributor(Contributor contributor)
/*     */   {
/* 343 */     getElement().appendChild(contributor.getElement());
/* 344 */     this.names.add(contributor);
/*     */   }
/*     */ 
/*     */   public List<Contributor> getContributors()
/*     */   {
/* 357 */     return this.names;
/*     */   }
/*     */ 
/*     */   public void addDate(CitationDate date)
/*     */   {
/* 369 */     getElement().appendChild(date.getElement());
/* 370 */     this.dates.add(date);
/*     */   }
/*     */ 
/*     */   public List<CitationDate> getDates()
/*     */   {
/* 383 */     return this.dates;
/*     */   }
/*     */ 
/*     */   private void initStructures()
/*     */     throws RIFCSException
/*     */   {
/* 390 */     initIdentifier();
/* 391 */     initContributors();
/* 392 */     initCitationDates();
/*     */   }
/*     */ 
/*     */   private void initIdentifier()
/*     */     throws RIFCSException
/*     */   {
/* 399 */     NodeList nl = super.getElements("identifier");
/*     */ 
/* 401 */     if (nl.getLength() > 0)
/*     */     {
/* 403 */       this.identifier = new Identifier(nl.item(0));
/*     */     }
/*     */   }
/*     */ 
/*     */   private void initContributors()
/*     */     throws RIFCSException
/*     */   {
/* 410 */     NodeList nl = super.getElements("contributor");
/*     */ 
/* 412 */     for (int i = 0; i < nl.getLength(); i++)
/*     */     {
/* 414 */       this.names.add(new Contributor(nl.item(i)));
/*     */     }
/*     */   }
/*     */ 
/*     */   private void initCitationDates()
/*     */     throws RIFCSException
/*     */   {
/* 421 */     NodeList nl = super.getElements("date");
/*     */ 
/* 423 */     for (int i = 0; i < nl.getLength(); i++)
/*     */     {
/* 425 */       this.dates.add(new CitationDate(nl.item(i)));
/*     */     }
/*     */   }
/*     */ }

/* Location:           /Users/przemyslaw/Downloads/rifcs-api-1.3.0.jar
 * Qualified Name:     org.ands.rifcs.base.CitationMetadata
 * JD-Core Version:    0.6.2
 */