/*     */ package org.ands.rifcs.base;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ 
/*     */ public class Activity extends RIFCSElement
/*     */ {
/*  36 */   private List<Identifier> identifiers = new ArrayList();
/*  37 */   private List<Name> names = new ArrayList();
/*  38 */   private List<Location> locations = new ArrayList();
/*  39 */   private List<Coverage> coverages = new ArrayList();
/*  40 */   private List<RelatedObject> relatedObjects = new ArrayList();
/*  41 */   private List<Subject> subjects = new ArrayList();
/*  42 */   private List<Description> descriptions = new ArrayList();
/*  43 */   private List<Right> rights = new ArrayList();
/*  44 */   private List<ExistenceDate> existenceDates = new ArrayList();
/*  45 */   private List<RelatedInfo> ris = new ArrayList();
/*     */ 
/*     */   protected Activity(Node n)
/*     */     throws RIFCSException
/*     */   {
/*  57 */     super(n, "activity");
/*  58 */     initStructures();
/*     */   }
/*     */ 
/*     */   public void setType(String type)
/*     */   {
/*  70 */     super.setAttributeValue("type", type);
/*     */   }
/*     */ 
/*     */   public String getType()
/*     */   {
/*  83 */     return super.getAttributeValue("type");
/*     */   }
/*     */ 
/*     */   public void setDateModified(Date date)
/*     */   {
/*  96 */     super.setAttributeValue("dateModified", RegistryObject.formatDate(date));
/*     */   }
/*     */ 
/*     */   public void setDateModified(String date)
/*     */   {
/* 110 */     super.setAttributeValue("dateModified", date);
/*     */   }
/*     */ 
/*     */   public String getDateModified()
/*     */   {
/* 123 */     return super.getAttributeValue("dateModified");
/*     */   }
/*     */ 
/*     */   public Identifier newIdentifier()
/*     */     throws RIFCSException
/*     */   {
/* 141 */     return new Identifier(newElement("identifier"));
/*     */   }
/*     */ 
/*     */   public void addIdentifier(Identifier identifier)
/*     */   {
/* 158 */     getElement().appendChild(identifier.getElement());
/* 159 */     this.identifiers.add(identifier);
/*     */   }
/*     */ 
/*     */   public List<Identifier> getIdentifiers()
/*     */   {
/* 171 */     return this.identifiers;
/*     */   }
/*     */ 
/*     */   public Name newName()
/*     */     throws RIFCSException
/*     */   {
/* 189 */     return new Name(newElement("name"));
/*     */   }
/*     */ 
/*     */   public void addName(Name name)
/*     */   {
/* 206 */     getElement().appendChild(name.getElement());
/* 207 */     this.names.add(name);
/*     */   }
/*     */ 
/*     */   public List<Name> getNames()
/*     */   {
/* 219 */     return this.names;
/*     */   }
/*     */ 
/*     */   public Location newLocation()
/*     */     throws RIFCSException
/*     */   {
/* 237 */     return new Location(newElement("location"));
/*     */   }
/*     */ 
/*     */   public void addLocation(Location location)
/*     */   {
/* 254 */     getElement().appendChild(location.getElement());
/* 255 */     this.locations.add(location);
/*     */   }
/*     */ 
/*     */   public List<Location> getLocations()
/*     */   {
/* 267 */     return this.locations;
/*     */   }
/*     */ 
/*     */   public Coverage newCoverage()
/*     */     throws RIFCSException
/*     */   {
/* 285 */     return new Coverage(newElement("coverage"));
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
/* 310 */     return this.coverages;
/*     */   }
/*     */ 
/*     */   public RelatedObject newRelatedObject()
/*     */     throws RIFCSException
/*     */   {
/* 328 */     return new RelatedObject(newElement("relatedObject"));
/*     */   }
/*     */ 
/*     */   public void addRelatedObject(RelatedObject relatedObject)
/*     */   {
/* 345 */     getElement().appendChild(relatedObject.getElement());
/* 346 */     this.relatedObjects.add(relatedObject);
/*     */   }
/*     */ 
/*     */   public List<RelatedObject> getRelatedObjects()
/*     */   {
/* 358 */     return this.relatedObjects;
/*     */   }
/*     */ 
/*     */   public Subject newSubject()
/*     */     throws RIFCSException
/*     */   {
/* 376 */     return new Subject(newElement("subject"));
/*     */   }
/*     */ 
/*     */   public void addSubject(Subject subject)
/*     */   {
/* 393 */     getElement().appendChild(subject.getElement());
/* 394 */     this.subjects.add(subject);
/*     */   }
/*     */ 
/*     */   public void addSubject(String value, String type, String termIdentifier, String lang)
/*     */   {
/* 414 */     Subject subject = null;
/*     */     try {
/* 416 */       subject = newSubject();
/*     */     } catch (RIFCSException e) {
/* 418 */       e.printStackTrace();
/*     */     }
/* 420 */     subject.setValue(value);
/* 421 */     subject.setType(type);
/* 422 */     subject.setTermIdentifier(termIdentifier);
/* 423 */     subject.setLanguage(lang);
/*     */ 
/* 425 */     getElement().appendChild(subject.getElement());
/* 426 */     this.subjects.add(subject);
/*     */   }
/*     */ 
/*     */   public List<Subject> getSubjects()
/*     */   {
/* 439 */     return this.subjects;
/*     */   }
/*     */ 
/*     */   public Description newDescription()
/*     */     throws RIFCSException
/*     */   {
/* 457 */     return new Description(newElement("description"));
/*     */   }
/*     */ 
/*     */   public void addDescription(Description description)
/*     */   {
/* 474 */     getElement().appendChild(description.getElement());
/* 475 */     this.descriptions.add(description);
/*     */   }
/*     */ 
/*     */   public void addDescription(String description, String type, String language)
/*     */     throws RIFCSException
/*     */   {
/* 491 */     Description d = newDescription();
/* 492 */     d.setType(type);
/* 493 */     d.setValue(description);
/* 494 */     if (language != null) {
/* 495 */       d.setLanguage(language);
/*     */     }
/* 497 */     getElement().appendChild(d.getElement());
/* 498 */     this.descriptions.add(d);
/*     */   }
/*     */ 
/*     */   public List<Description> getDescriptions()
/*     */   {
/* 510 */     return this.descriptions;
/*     */   }
/*     */ 
/*     */   public Right newRight() throws RIFCSException
/*     */   {
/* 515 */     return new Right(newElement("rights"));
/*     */   }
/*     */ 
/*     */   public void addRight(Right right)
/*     */   {
/* 532 */     getElement().appendChild(right.getElement());
/* 533 */     this.rights.add(right);
/*     */   }
/*     */ 
/*     */   public List<Right> getRights()
/*     */   {
/* 545 */     return this.rights;
/*     */   }
/*     */ 
/*     */   public ExistenceDate newExistenceDate()
/*     */     throws RIFCSException
/*     */   {
/* 552 */     return new ExistenceDate(newElement("existenceDates"));
/*     */   }
/*     */ 
/*     */   public void addExistenceDate(ExistenceDate existenceDate)
/*     */   {
/* 569 */     getElement().appendChild(existenceDate.getElement());
/* 570 */     this.existenceDates.add(existenceDate);
/*     */   }
/*     */ 
/*     */   public void addExistenceDate(String startVal, String startDateFormat, String endVal, String endDateFormat)
/*     */   {
/*     */     try
/*     */     {
/* 592 */       ExistenceDate date = newExistenceDate();
/* 593 */       date.setStartDate(startVal, startDateFormat);
/* 594 */       date.setEndDate(endVal, endDateFormat);
/* 595 */       getElement().appendChild(date.getElement());
/* 596 */       this.existenceDates.add(date);
/*     */     } catch (RIFCSException e) {
/* 598 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   public List<ExistenceDate> getExistenceDates()
/*     */   {
/* 611 */     return this.existenceDates;
/*     */   }
/*     */ 
/*     */   public RelatedInfo newRelatedInfo()
/*     */     throws RIFCSException
/*     */   {
/* 629 */     return new RelatedInfo(newElement("relatedInfo"));
/*     */   }
/*     */ 
/*     */   public void addRelatedInfo(RelatedInfo relatedInfo)
/*     */   {
/* 641 */     getElement().appendChild(relatedInfo.getElement());
/* 642 */     this.ris.add(relatedInfo);
/*     */   }
/*     */ 
/*     */   public List<RelatedInfo> getRelatedInfo()
/*     */   {
/* 654 */     return this.ris;
/*     */   }
/*     */ 
/*     */   private void initStructures()
/*     */     throws RIFCSException
/*     */   {
/* 661 */     initIdentifiers();
/* 662 */     initNames();
/* 663 */     initLocations();
/* 664 */     initCoverage();
/* 665 */     initRelatedObjects();
/* 666 */     initSubjects();
/* 667 */     initDescriptions();
/* 668 */     initRelatedInfo();
/*     */   }
/*     */ 
/*     */   private void initIdentifiers() throws RIFCSException
/*     */   {
/* 673 */     NodeList nl = super.getElements("identifier");
/*     */ 
/* 675 */     for (int i = 0; i < nl.getLength(); i++)
/*     */     {
/* 677 */       this.identifiers.add(new Identifier(nl.item(i)));
/*     */     }
/*     */   }
/*     */ 
/*     */   private void initNames() throws RIFCSException
/*     */   {
/* 683 */     NodeList nl = super.getElements("name");
/*     */ 
/* 685 */     for (int i = 0; i < nl.getLength(); i++)
/*     */     {
/* 687 */       this.names.add(new Name(nl.item(i)));
/*     */     }
/*     */   }
/*     */ 
/*     */   private void initLocations() throws RIFCSException
/*     */   {
/* 693 */     NodeList nl = super.getElements("location");
/*     */ 
/* 695 */     for (int i = 0; i < nl.getLength(); i++)
/*     */     {
/* 697 */       this.locations.add(new Location(nl.item(i)));
/*     */     }
/*     */   }
/*     */ 
/*     */   private void initCoverage() throws RIFCSException
/*     */   {
/* 703 */     NodeList nl = super.getElements("coverage");
/*     */ 
/* 705 */     for (int i = 0; i < nl.getLength(); i++)
/*     */     {
/* 707 */       this.coverages.add(new Coverage(nl.item(i)));
/*     */     }
/*     */   }
/*     */ 
/*     */   private void initRelatedObjects() throws RIFCSException
/*     */   {
/* 713 */     NodeList nl = super.getElements("relatedObject");
/*     */ 
/* 715 */     for (int i = 0; i < nl.getLength(); i++)
/*     */     {
/* 717 */       this.relatedObjects.add(new RelatedObject(nl.item(i)));
/*     */     }
/*     */   }
/*     */ 
/*     */   private void initSubjects() throws RIFCSException
/*     */   {
/* 723 */     NodeList nl = super.getElements("subject");
/*     */ 
/* 725 */     for (int i = 0; i < nl.getLength(); i++)
/*     */     {
/* 727 */       this.subjects.add(new Subject(nl.item(i)));
/*     */     }
/*     */   }
/*     */ 
/*     */   private void initDescriptions() throws RIFCSException
/*     */   {
/* 733 */     NodeList nl = super.getElements("description");
/*     */ 
/* 735 */     for (int i = 0; i < nl.getLength(); i++)
/*     */     {
/* 737 */       this.descriptions.add(new Description(nl.item(i)));
/*     */     }
/*     */   }
/*     */ 
/*     */   private void initRelatedInfo() throws RIFCSException
/*     */   {
/* 743 */     NodeList nl = super.getElements("relatedInfo");
/*     */ 
/* 745 */     for (int i = 0; i < nl.getLength(); i++)
/*     */     {
/* 747 */       this.ris.add(new RelatedInfo(nl.item(i)));
/*     */     }
/*     */   }
/*     */ }

/* Location:           /Users/przemyslaw/Downloads/rifcs-api-1.3.0.jar
 * Qualified Name:     org.ands.rifcs.base.Activity
 * JD-Core Version:    0.6.2
 */