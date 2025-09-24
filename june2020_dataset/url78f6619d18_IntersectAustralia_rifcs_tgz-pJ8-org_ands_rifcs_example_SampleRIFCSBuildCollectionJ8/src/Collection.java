/*     */ package org.ands.rifcs.base;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ 
/*     */ public class Collection extends RIFCSElement
/*     */ {
/*  35 */   private List<Identifier> identifiers = new ArrayList();
/*  36 */   private List<Name> names = new ArrayList();
/*  37 */   private List<Location> locations = new ArrayList();
/*  38 */   private List<Coverage> coverages = new ArrayList();
/*  39 */   private List<RelatedObject> relatedObjects = new ArrayList();
/*  40 */   private List<Subject> subjects = new ArrayList();
/*  41 */   private List<Description> descriptions = new ArrayList();
/*  42 */   private List<Right> rightsList = new ArrayList();
/*  43 */   private List<RelatedInfo> ris = new ArrayList();
/*  44 */   private List<CitationInfo> cis = new ArrayList();
/*     */ 
/*     */   protected Collection(Node n)
/*     */     throws RIFCSException
/*     */   {
/*  55 */     super(n, "collection");
/*  56 */     initStructures();
/*     */   }
/*     */ 
/*     */   public void setType(String type)
/*     */   {
/*  66 */     super.setAttributeValue("type", type);
/*     */   }
/*     */ 
/*     */   public String getType()
/*     */   {
/*  76 */     return super.getAttributeValue("type");
/*     */   }
/*     */ 
/*     */   public void setDateAccessioned(Date date)
/*     */   {
/*  88 */     super.setAttributeValue("dateAccessioned", RegistryObject.formatDate(date));
/*     */   }
/*     */ 
/*     */   public void setDateAccessioned(String date)
/*     */   {
/* 101 */     super.setAttributeValue("dateAccessioned", date);
/*     */   }
/*     */ 
/*     */   public String getDateAccessioned()
/*     */   {
/* 111 */     return super.getAttributeValue("dateAccessioned");
/*     */   }
/*     */ 
/*     */   public void setDateModified(Date date)
/*     */   {
/* 122 */     super.setAttributeValue("dateModified", RegistryObject.formatDate(date));
/*     */   }
/*     */ 
/*     */   public void setDateModified(String date)
/*     */   {
/* 134 */     super.setAttributeValue("dateModified", date);
/*     */   }
/*     */ 
/*     */   public String getDateModified()
/*     */   {
/* 144 */     return super.getAttributeValue("dateModified");
/*     */   }
/*     */ 
/*     */   public Identifier newIdentifier()
/*     */     throws RIFCSException
/*     */   {
/* 160 */     return new Identifier(newElement("identifier"));
/*     */   }
/*     */ 
/*     */   public void addIdentifier(Identifier identifier)
/*     */   {
/* 170 */     getElement().appendChild(identifier.getElement());
/* 171 */     this.identifiers.add(identifier);
/*     */   }
/*     */ 
/*     */   public void addIdentifier(String identifier, String type)
/*     */     throws RIFCSException
/*     */   {
/* 186 */     Identifier i = newIdentifier();
/* 187 */     i.setType(type);
/* 188 */     i.setValue(identifier);
/* 189 */     getElement().appendChild(i.getElement());
/* 190 */     this.identifiers.add(i);
/*     */   }
/*     */ 
/*     */   public List<Identifier> getIdentifiers()
/*     */   {
/* 199 */     return this.identifiers;
/*     */   }
/*     */ 
/*     */   public Name newName()
/*     */     throws RIFCSException
/*     */   {
/* 215 */     return new Name(newElement("name"));
/*     */   }
/*     */ 
/*     */   public void addName(Name name)
/*     */   {
/* 225 */     getElement().appendChild(name.getElement());
/* 226 */     this.names.add(name);
/*     */   }
/*     */ 
/*     */   public List<Name> getNames()
/*     */   {
/* 235 */     return this.names;
/*     */   }
/*     */ 
/*     */   public Location newLocation()
/*     */     throws RIFCSException
/*     */   {
/* 251 */     return new Location(newElement("location"));
/*     */   }
/*     */ 
/*     */   public void addLocation(Location location)
/*     */   {
/* 261 */     getElement().appendChild(location.getElement());
/* 262 */     this.locations.add(location);
/*     */   }
/*     */ 
/*     */   public List<Location> getLocations()
/*     */   {
/* 271 */     return this.locations;
/*     */   }
/*     */ 
/*     */   public Coverage newCoverage()
/*     */     throws RIFCSException
/*     */   {
/* 287 */     return new Coverage(newElement("coverage"));
/*     */   }
/*     */ 
/*     */   public void addCoverage(Coverage coverage)
/*     */   {
/* 297 */     getElement().appendChild(coverage.getElement());
/* 298 */     this.coverages.add(coverage);
/*     */   }
/*     */ 
/*     */   public List<Coverage> getCoverage()
/*     */   {
/* 307 */     return this.coverages;
/*     */   }
/*     */ 
/*     */   public RelatedObject newRelatedObject()
/*     */     throws RIFCSException
/*     */   {
/* 323 */     return new RelatedObject(newElement("relatedObject"));
/*     */   }
/*     */ 
/*     */   public void addRelatedObject(RelatedObject relatedObject)
/*     */   {
/* 333 */     getElement().appendChild(relatedObject.getElement());
/* 334 */     this.relatedObjects.add(relatedObject);
/*     */   }
/*     */ 
/*     */   public List<RelatedObject> getRelatedObjects()
/*     */   {
/* 343 */     return this.relatedObjects;
/*     */   }
/*     */ 
/*     */   public Subject newSubject()
/*     */     throws RIFCSException
/*     */   {
/* 359 */     return new Subject(newElement("subject"));
/*     */   }
/*     */ 
/*     */   public void addSubject(Subject subject)
/*     */   {
/* 369 */     getElement().appendChild(subject.getElement());
/* 370 */     this.subjects.add(subject);
/*     */   }
/*     */ 
/*     */   public void addSubject(String subject, String type, String language)
/*     */     throws RIFCSException
/*     */   {
/* 386 */     Subject s = newSubject();
/* 387 */     s.setType(type);
/* 388 */     s.setValue(subject);
/* 389 */     if (language != null) {
/* 390 */       s.setLanguage(language);
/*     */     }
/* 392 */     getElement().appendChild(s.getElement());
/* 393 */     this.subjects.add(s);
/*     */   }
/*     */ 
/*     */   public List<Subject> getSubjects()
/*     */   {
/* 402 */     return this.subjects;
/*     */   }
/*     */ 
/*     */   public Description newDescription()
/*     */     throws RIFCSException
/*     */   {
/* 418 */     return new Description(newElement("description"));
/*     */   }
/*     */ 
/*     */   public void addDescription(Description description)
/*     */   {
/* 428 */     getElement().appendChild(description.getElement());
/* 429 */     this.descriptions.add(description);
/*     */   }
/*     */ 
/*     */   public void addDescription(String description, String type, String language)
/*     */     throws RIFCSException
/*     */   {
/* 445 */     Description d = newDescription();
/* 446 */     d.setType(type);
/* 447 */     d.setValue(description);
/* 448 */     if (language != null) {
/* 449 */       d.setLanguage(language);
/*     */     }
/* 451 */     getElement().appendChild(d.getElement());
/* 452 */     this.descriptions.add(d);
/*     */   }
/*     */ 
/*     */   public List<Description> getDescriptions()
/*     */   {
/* 461 */     return this.descriptions;
/*     */   }
/*     */ 
/*     */   public Right newRight() {
/* 465 */     Right right = null;
/*     */     try {
/* 467 */       right = new Right(newElement("rights"));
/*     */     } catch (RIFCSException e) {
/* 469 */       e.printStackTrace();
/*     */     }
/* 471 */     return right;
/*     */   }
/*     */ 
/*     */   public void addRight(Right right)
/*     */   {
/* 482 */     getElement().appendChild(right.getElement());
/* 483 */     this.rightsList.add(right);
/*     */   }
/*     */ 
/*     */   public List<Right> getRightsList()
/*     */   {
/* 493 */     return this.rightsList;
/*     */   }
/*     */ 
/*     */   public RelatedInfo newRelatedInfo()
/*     */     throws RIFCSException
/*     */   {
/* 509 */     return new RelatedInfo(newElement("relatedInfo"));
/*     */   }
/*     */ 
/*     */   public void addRelatedInfo(RelatedInfo relatedInfo)
/*     */   {
/* 519 */     getElement().appendChild(relatedInfo.getElement());
/* 520 */     this.ris.add(relatedInfo);
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public void addRelatedInfo(String relatedInfoURI)
/*     */     throws RIFCSException
/*     */   {
/* 538 */     RelatedInfo ri = newRelatedInfo();
/* 539 */     ri.setIdentifier(relatedInfoURI, "uri");
/* 540 */     getElement().appendChild(ri.getElement());
/* 541 */     this.ris.add(ri);
/*     */   }
/*     */ 
/*     */   public List<RelatedInfo> getRelatedInfo()
/*     */   {
/* 550 */     return this.ris;
/*     */   }
/*     */ 
/*     */   public CitationInfo newCitationInfo() throws RIFCSException {
/* 554 */     return new CitationInfo(newElement("citationInfo"));
/*     */   }
/*     */ 
/*     */   public void addCitationInfo(CitationInfo citationInfo) {
/* 558 */     getElement().appendChild(citationInfo.getElement());
/* 559 */     this.cis.add(citationInfo);
/*     */   }
/*     */ 
/*     */   public List<CitationInfo> getCitationInfos() {
/* 563 */     return this.cis;
/*     */   }
/*     */ 
/*     */   private void initStructures() throws RIFCSException
/*     */   {
/* 568 */     initIdentifiers();
/* 569 */     initNames();
/* 570 */     initLocations();
/* 571 */     initCoverage();
/* 572 */     initRelatedObjects();
/* 573 */     initSubjects();
/* 574 */     initDescriptions();
/* 575 */     initRelatedInfo();
/* 576 */     initCitationInfo();
/*     */   }
/*     */ 
/*     */   private void initIdentifiers() throws RIFCSException
/*     */   {
/* 581 */     NodeList nl = super.getElements("identifier");
/*     */ 
/* 583 */     for (int i = 0; i < nl.getLength(); i++)
/* 584 */       this.identifiers.add(new Identifier(nl.item(i)));
/*     */   }
/*     */ 
/*     */   private void initNames() throws RIFCSException
/*     */   {
/* 589 */     NodeList nl = super.getElements("name");
/* 590 */     for (int i = 0; i < nl.getLength(); i++)
/* 591 */       this.names.add(new Name(nl.item(i)));
/*     */   }
/*     */ 
/*     */   private void initLocations() throws RIFCSException
/*     */   {
/* 596 */     NodeList nl = super.getElements("location");
/*     */ 
/* 598 */     for (int i = 0; i < nl.getLength(); i++)
/* 599 */       this.locations.add(new Location(nl.item(i)));
/*     */   }
/*     */ 
/*     */   private void initCoverage() throws RIFCSException
/*     */   {
/* 604 */     NodeList nl = super.getElements("coverage");
/*     */ 
/* 606 */     for (int i = 0; i < nl.getLength(); i++)
/* 607 */       this.coverages.add(new Coverage(nl.item(i)));
/*     */   }
/*     */ 
/*     */   private void initRelatedObjects() throws RIFCSException
/*     */   {
/* 612 */     NodeList nl = super.getElements("relatedObject");
/*     */ 
/* 614 */     for (int i = 0; i < nl.getLength(); i++)
/* 615 */       this.relatedObjects.add(new RelatedObject(nl.item(i)));
/*     */   }
/*     */ 
/*     */   private void initSubjects() throws RIFCSException
/*     */   {
/* 620 */     NodeList nl = super.getElements("subject");
/*     */ 
/* 622 */     for (int i = 0; i < nl.getLength(); i++)
/* 623 */       this.subjects.add(new Subject(nl.item(i)));
/*     */   }
/*     */ 
/*     */   private void initDescriptions() throws RIFCSException
/*     */   {
/* 628 */     NodeList nl = super.getElements("description");
/*     */ 
/* 630 */     for (int i = 0; i < nl.getLength(); i++)
/* 631 */       this.descriptions.add(new Description(nl.item(i)));
/*     */   }
/*     */ 
/*     */   private void initRelatedInfo() throws RIFCSException
/*     */   {
/* 636 */     NodeList nl = super.getElements("relatedInfo");
/*     */ 
/* 638 */     for (int i = 0; i < nl.getLength(); i++)
/* 639 */       this.ris.add(new RelatedInfo(nl.item(i)));
/*     */   }
/*     */ 
/*     */   private void initCitationInfo() throws RIFCSException
/*     */   {
/* 644 */     NodeList nl = super.getElements("citationInfo");
/*     */ 
/* 646 */     for (int i = 0; i < nl.getLength(); i++)
/* 647 */       this.cis.add(new CitationInfo(nl.item(i)));
/*     */   }
/*     */ }

/* Location:           /Users/przemyslaw/Downloads/rifcs-api-1.3.0.jar
 * Qualified Name:     org.ands.rifcs.base.Collection
 * JD-Core Version:    0.6.2
 */