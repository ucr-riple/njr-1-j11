/*     */ package org.ands.rifcs.base;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ 
/*     */ public class Party extends RIFCSElement
/*     */ {
/*  35 */   private List<Identifier> identifiers = new ArrayList();
/*  36 */   private List<Name> names = new ArrayList();
/*  37 */   private List<Location> locations = new ArrayList();
/*  38 */   private List<Coverage> coverages = new ArrayList();
/*  39 */   private List<RelatedObject> relatedObjects = new ArrayList();
/*  40 */   private List<Subject> subjects = new ArrayList();
/*  41 */   private List<Description> descriptions = new ArrayList();
/*  42 */   private List<Right> rights = new ArrayList();
/*  43 */   private List<ExistenceDate> existenceDates = new ArrayList();
/*  44 */   private List<RelatedInfo> ris = new ArrayList();
/*     */ 
/*     */   protected Party(Node n)
/*     */     throws RIFCSException
/*     */   {
/*  55 */     super(n, "party");
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
/*     */   public void setDateModified(Date date)
/*     */   {
/*  87 */     super.setAttributeValue("dateModified", 
/*  88 */       RegistryObject.formatDate(date));
/*     */   }
/*     */ 
/*     */   public void setDateModified(String date)
/*     */   {
/* 100 */     super.setAttributeValue("dateModified", date);
/*     */   }
/*     */ 
/*     */   public String getDateModified()
/*     */   {
/* 110 */     return super.getAttributeValue("dateModified");
/*     */   }
/*     */ 
/*     */   public Identifier newIdentifier()
/*     */     throws RIFCSException
/*     */   {
/* 126 */     return new Identifier(newElement("identifier"));
/*     */   }
/*     */ 
/*     */   public void addIdentifier(Identifier identifier)
/*     */   {
/* 136 */     getElement().appendChild(identifier.getElement());
/* 137 */     this.identifiers.add(identifier);
/*     */   }
/*     */ 
/*     */   public List<Identifier> getIdentifiers()
/*     */   {
/* 146 */     return this.identifiers;
/*     */   }
/*     */ 
/*     */   public Name newName()
/*     */     throws RIFCSException
/*     */   {
/* 162 */     return new Name(newElement("name"));
/*     */   }
/*     */ 
/*     */   public void addName(Name name)
/*     */   {
/* 172 */     getElement().appendChild(name.getElement());
/* 173 */     this.names.add(name);
/*     */   }
/*     */ 
/*     */   public List<Name> getNames()
/*     */   {
/* 182 */     return this.names;
/*     */   }
/*     */ 
/*     */   public Location newLocation()
/*     */     throws RIFCSException
/*     */   {
/* 198 */     return new Location(newElement("location"));
/*     */   }
/*     */ 
/*     */   public void addLocation(Location location)
/*     */   {
/* 208 */     getElement().appendChild(location.getElement());
/* 209 */     this.locations.add(location);
/*     */   }
/*     */ 
/*     */   public List<Location> getLocations()
/*     */   {
/* 218 */     return this.locations;
/*     */   }
/*     */ 
/*     */   public Coverage newCoverage()
/*     */     throws RIFCSException
/*     */   {
/* 234 */     return new Coverage(newElement("coverage"));
/*     */   }
/*     */ 
/*     */   public void addCoverage(Coverage coverage)
/*     */   {
/* 244 */     getElement().appendChild(coverage.getElement());
/* 245 */     this.coverages.add(coverage);
/*     */   }
/*     */ 
/*     */   public List<Coverage> getCoverage()
/*     */   {
/* 254 */     return this.coverages;
/*     */   }
/*     */ 
/*     */   public RelatedObject newRelatedObject()
/*     */     throws RIFCSException
/*     */   {
/* 270 */     return new RelatedObject(
/* 271 */       newElement("relatedObject"));
/*     */   }
/*     */ 
/*     */   public void addRelatedObject(RelatedObject relatedObject)
/*     */   {
/* 281 */     getElement().appendChild(relatedObject.getElement());
/* 282 */     this.relatedObjects.add(relatedObject);
/*     */   }
/*     */ 
/*     */   public List<RelatedObject> getRelatedObjects()
/*     */   {
/* 291 */     return this.relatedObjects;
/*     */   }
/*     */ 
/*     */   public Subject newSubject()
/*     */     throws RIFCSException
/*     */   {
/* 307 */     return new Subject(newElement("subject"));
/*     */   }
/*     */ 
/*     */   public void addSubject(Subject subject)
/*     */   {
/* 317 */     getElement().appendChild(subject.getElement());
/* 318 */     this.subjects.add(subject);
/*     */   }
/*     */ 
/*     */   public void addSubject(String value, String type, String termIdentifier, String lang)
/*     */   {
/* 341 */     Subject subject = null;
/*     */     try {
/* 343 */       subject = newSubject();
/*     */     } catch (RIFCSException e) {
/* 345 */       e.printStackTrace();
/*     */     }
/* 347 */     subject.setValue(value);
/* 348 */     subject.setType(type);
/* 349 */     subject.setTermIdentifier(termIdentifier);
/* 350 */     subject.setLanguage(lang);
/*     */ 
/* 352 */     getElement().appendChild(subject.getElement());
/* 353 */     this.subjects.add(subject);
/*     */   }
/*     */ 
/*     */   public List<Subject> getSubjects()
/*     */   {
/* 363 */     return this.subjects;
/*     */   }
/*     */ 
/*     */   public Description newDescription()
/*     */     throws RIFCSException
/*     */   {
/* 379 */     return new Description(newElement("description"));
/*     */   }
/*     */ 
/*     */   public void addDescription(Description description)
/*     */   {
/* 389 */     getElement().appendChild(description.getElement());
/* 390 */     this.descriptions.add(description);
/*     */   }
/*     */ 
/*     */   public void addDescription(String description, String type, String language)
/*     */     throws RIFCSException
/*     */   {
/* 406 */     Description d = newDescription();
/* 407 */     d.setType(type);
/* 408 */     d.setValue(description);
/* 409 */     if (language != null) {
/* 410 */       d.setLanguage(language);
/*     */     }
/* 412 */     getElement().appendChild(d.getElement());
/* 413 */     this.descriptions.add(d);
/*     */   }
/*     */ 
/*     */   public List<Description> getDescriptions()
/*     */   {
/* 423 */     return this.descriptions;
/*     */   }
/*     */ 
/*     */   public Right newRight() throws RIFCSException {
/* 427 */     return new Right(newElement("rights"));
/*     */   }
/*     */ 
/*     */   public void addRight(Right right)
/*     */   {
/* 441 */     getElement().appendChild(right.getElement());
/* 442 */     this.rights.add(right);
/*     */   }
/*     */ 
/*     */   public List<Right> getRights()
/*     */   {
/* 451 */     return this.rights;
/*     */   }
/*     */ 
/*     */   public ExistenceDate newExistenceDate() throws RIFCSException {
/* 455 */     return new ExistenceDate(
/* 456 */       newElement("existenceDates"));
/*     */   }
/*     */ 
/*     */   public void addExistenceDate(ExistenceDate existenceDate)
/*     */   {
/* 470 */     getElement().appendChild(existenceDate.getElement());
/* 471 */     this.existenceDates.add(existenceDate);
/*     */   }
/*     */ 
/*     */   public void addExistenceDate(String startVal, String startDateFormat, String endVal, String endDateFormat)
/*     */   {
/*     */     try
/*     */     {
/* 494 */       ExistenceDate date = newExistenceDate();
/* 495 */       date.setStartDate(startVal, startDateFormat);
/* 496 */       date.setEndDate(endVal, endDateFormat);
/* 497 */       getElement().appendChild(date.getElement());
/* 498 */       this.existenceDates.add(date);
/*     */     } catch (RIFCSException e) {
/* 500 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   public List<ExistenceDate> getExistenceDates()
/*     */   {
/* 510 */     return this.existenceDates;
/*     */   }
/*     */ 
/*     */   public RelatedInfo newRelatedInfo()
/*     */     throws RIFCSException
/*     */   {
/* 526 */     return new RelatedInfo(newElement("relatedInfo"));
/*     */   }
/*     */ 
/*     */   public void addRelatedInfo(RelatedInfo relatedInfo)
/*     */   {
/* 536 */     getElement().appendChild(relatedInfo.getElement());
/* 537 */     this.ris.add(relatedInfo);
/*     */   }
/*     */ 
/*     */   public List<RelatedInfo> getRelatedInfo()
/*     */   {
/* 546 */     return this.ris;
/*     */   }
/*     */ 
/*     */   private void initStructures() throws RIFCSException
/*     */   {
/* 551 */     initIdentifiers();
/* 552 */     initNames();
/* 553 */     initLocations();
/* 554 */     initCoverage();
/* 555 */     initRelatedObjects();
/* 556 */     initSubjects();
/* 557 */     initDescriptions();
/* 558 */     initRelatedInfo();
/*     */   }
/*     */ 
/*     */   private void initIdentifiers() throws RIFCSException {
/* 562 */     NodeList nl = super.getElements("identifier");
/*     */ 
/* 564 */     for (int i = 0; i < nl.getLength(); i++)
/* 565 */       this.identifiers.add(new Identifier(nl.item(i)));
/*     */   }
/*     */ 
/*     */   private void initNames() throws RIFCSException
/*     */   {
/* 570 */     NodeList nl = super.getElements("name");
/*     */ 
/* 572 */     for (int i = 0; i < nl.getLength(); i++)
/* 573 */       this.names.add(new Name(nl.item(i)));
/*     */   }
/*     */ 
/*     */   private void initLocations() throws RIFCSException
/*     */   {
/* 578 */     NodeList nl = super.getElements("location");
/*     */ 
/* 580 */     for (int i = 0; i < nl.getLength(); i++)
/* 581 */       this.locations.add(new Location(nl.item(i)));
/*     */   }
/*     */ 
/*     */   private void initCoverage() throws RIFCSException
/*     */   {
/* 586 */     NodeList nl = super.getElements("coverage");
/*     */ 
/* 588 */     for (int i = 0; i < nl.getLength(); i++)
/* 589 */       this.coverages.add(new Coverage(nl.item(i)));
/*     */   }
/*     */ 
/*     */   private void initRelatedObjects() throws RIFCSException
/*     */   {
/* 594 */     NodeList nl = super.getElements("relatedObject");
/*     */ 
/* 596 */     for (int i = 0; i < nl.getLength(); i++)
/* 597 */       this.relatedObjects.add(new RelatedObject(nl.item(i)));
/*     */   }
/*     */ 
/*     */   private void initSubjects() throws RIFCSException
/*     */   {
/* 602 */     NodeList nl = super.getElements("subject");
/*     */ 
/* 604 */     for (int i = 0; i < nl.getLength(); i++)
/* 605 */       this.subjects.add(new Subject(nl.item(i)));
/*     */   }
/*     */ 
/*     */   private void initDescriptions() throws RIFCSException
/*     */   {
/* 610 */     NodeList nl = super.getElements("description");
/*     */ 
/* 612 */     for (int i = 0; i < nl.getLength(); i++)
/* 613 */       this.descriptions.add(new Description(nl.item(i)));
/*     */   }
/*     */ 
/*     */   private void initRelatedInfo() throws RIFCSException
/*     */   {
/* 618 */     NodeList nl = super.getElements("relatedInfo");
/*     */ 
/* 620 */     for (int i = 0; i < nl.getLength(); i++)
/* 621 */       this.ris.add(new RelatedInfo(nl.item(i)));
/*     */   }
/*     */ }

/* Location:           /Users/przemyslaw/Downloads/rifcs-api-1.3.0.jar
 * Qualified Name:     org.ands.rifcs.base.Party
 * JD-Core Version:    0.6.2
 */