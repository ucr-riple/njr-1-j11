/*     */ package org.ands.rifcs.base;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ 
/*     */ public class Service extends RIFCSElement
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
/*  46 */   private List<AccessPolicy> aps = new ArrayList();
/*     */ 
/*     */   protected Service(Node n)
/*     */     throws RIFCSException
/*     */   {
/*  58 */     super(n, "service");
/*  59 */     initStructures();
/*     */   }
/*     */ 
/*     */   public void setType(String type)
/*     */   {
/*  71 */     super.setAttributeValue("type", type);
/*     */   }
/*     */ 
/*     */   public String getType()
/*     */   {
/*  84 */     return super.getAttributeValue("type");
/*     */   }
/*     */ 
/*     */   public void setDateModified(Date date)
/*     */   {
/*  97 */     super.setAttributeValue("dateModified", RegistryObject.formatDate(date));
/*     */   }
/*     */ 
/*     */   public void setDateModified(String date)
/*     */   {
/* 111 */     super.setAttributeValue("dateModified", date);
/*     */   }
/*     */ 
/*     */   public String getDateModified()
/*     */   {
/* 124 */     return super.getAttributeValue("dateModified");
/*     */   }
/*     */ 
/*     */   public Identifier newIdentifier()
/*     */     throws RIFCSException
/*     */   {
/* 142 */     return new Identifier(newElement("identifier"));
/*     */   }
/*     */ 
/*     */   public void addIdentifier(Identifier identifier)
/*     */   {
/* 154 */     getElement().appendChild(identifier.getElement());
/* 155 */     this.identifiers.add(identifier);
/*     */   }
/*     */ 
/*     */   public List<Identifier> getIdentifiers()
/*     */   {
/* 167 */     return this.identifiers;
/*     */   }
/*     */ 
/*     */   public Name newName()
/*     */     throws RIFCSException
/*     */   {
/* 185 */     return new Name(newElement("name"));
/*     */   }
/*     */ 
/*     */   public void addName(Name name)
/*     */   {
/* 197 */     getElement().appendChild(name.getElement());
/* 198 */     this.names.add(name);
/*     */   }
/*     */ 
/*     */   public List<Name> getNames()
/*     */   {
/* 210 */     return this.names;
/*     */   }
/*     */ 
/*     */   public Location newLocation()
/*     */     throws RIFCSException
/*     */   {
/* 228 */     return new Location(newElement("location"));
/*     */   }
/*     */ 
/*     */   public void addLocation(Location location)
/*     */   {
/* 240 */     getElement().appendChild(location.getElement());
/* 241 */     this.locations.add(location);
/*     */   }
/*     */ 
/*     */   public List<Location> getLocations()
/*     */   {
/* 253 */     return this.locations;
/*     */   }
/*     */ 
/*     */   public Coverage newCoverage()
/*     */     throws RIFCSException
/*     */   {
/* 271 */     return new Coverage(newElement("coverage"));
/*     */   }
/*     */ 
/*     */   public void addCoverage(Coverage coverage)
/*     */   {
/* 283 */     getElement().appendChild(coverage.getElement());
/* 284 */     this.coverages.add(coverage);
/*     */   }
/*     */ 
/*     */   public List<Coverage> getCoverage()
/*     */   {
/* 296 */     return this.coverages;
/*     */   }
/*     */ 
/*     */   public RelatedObject newRelatedObject()
/*     */     throws RIFCSException
/*     */   {
/* 314 */     return new RelatedObject(newElement("relatedObject"));
/*     */   }
/*     */ 
/*     */   public void addRelatedObject(RelatedObject relatedObject)
/*     */   {
/* 326 */     getElement().appendChild(relatedObject.getElement());
/* 327 */     this.relatedObjects.add(relatedObject);
/*     */   }
/*     */ 
/*     */   public List<RelatedObject> getRelatedObjects()
/*     */   {
/* 339 */     return this.relatedObjects;
/*     */   }
/*     */ 
/*     */   public Subject newSubject()
/*     */     throws RIFCSException
/*     */   {
/* 357 */     return new Subject(newElement("subject"));
/*     */   }
/*     */ 
/*     */   public void addSubject(Subject subject)
/*     */   {
/* 369 */     getElement().appendChild(subject.getElement());
/* 370 */     this.subjects.add(subject);
/*     */   }
/*     */ 
/*     */   public void addSubject(String value, String type, String termIdentifier, String lang)
/*     */   {
/* 393 */     Subject subject = null;
/*     */     try {
/* 395 */       subject = newSubject();
/*     */     } catch (RIFCSException e) {
/* 397 */       e.printStackTrace();
/*     */     }
/* 399 */     subject.setValue(value);
/* 400 */     subject.setType(type);
/* 401 */     subject.setTermIdentifier(termIdentifier);
/* 402 */     subject.setLanguage(lang);
/*     */ 
/* 404 */     getElement().appendChild(subject.getElement());
/* 405 */     this.subjects.add(subject);
/*     */   }
/*     */ 
/*     */   public List<Subject> getSubjects()
/*     */   {
/* 417 */     return this.subjects;
/*     */   }
/*     */ 
/*     */   public Description newDescription()
/*     */     throws RIFCSException
/*     */   {
/* 435 */     return new Description(newElement("description"));
/*     */   }
/*     */ 
/*     */   public void addDescription(Description description)
/*     */   {
/* 447 */     getElement().appendChild(description.getElement());
/* 448 */     this.descriptions.add(description);
/*     */   }
/*     */ 
/*     */   public void addDescription(String description, String type, String language)
/*     */     throws RIFCSException
/*     */   {
/* 465 */     Description d = newDescription();
/* 466 */     d.setType(type);
/* 467 */     d.setValue(description);
/* 468 */     if (language != null) {
/* 469 */       d.setLanguage(language);
/*     */     }
/* 471 */     getElement().appendChild(d.getElement());
/* 472 */     this.descriptions.add(d);
/*     */   }
/*     */ 
/*     */   public List<Description> getDescriptions()
/*     */   {
/* 483 */     return this.descriptions;
/*     */   }
/*     */ 
/*     */   public Right newRight() throws RIFCSException
/*     */   {
/* 488 */     return new Right(newElement("rights"));
/*     */   }
/*     */ 
/*     */   public void addRight(Right right)
/*     */   {
/* 505 */     getElement().appendChild(right.getElement());
/* 506 */     this.rights.add(right);
/*     */   }
/*     */ 
/*     */   public List<Right> getRights()
/*     */   {
/* 518 */     return this.rights;
/*     */   }
/*     */ 
/*     */   public ExistenceDate newExistenceDate()
/*     */     throws RIFCSException
/*     */   {
/* 525 */     return new ExistenceDate(newElement("existenceDates"));
/*     */   }
/*     */ 
/*     */   public void addExistenceDate(ExistenceDate existenceDate)
/*     */   {
/* 542 */     getElement().appendChild(existenceDate.getElement());
/* 543 */     this.existenceDates.add(existenceDate);
/*     */   }
/*     */ 
/*     */   public void addExistenceDate(String startVal, String startDateFormat, String endVal, String endDateFormat)
/*     */   {
/*     */     try
/*     */     {
/* 566 */       ExistenceDate date = newExistenceDate();
/* 567 */       date.setStartDate(startVal, startDateFormat);
/* 568 */       date.setEndDate(endVal, endDateFormat);
/* 569 */       getElement().appendChild(date.getElement());
/* 570 */       this.existenceDates.add(date);
/*     */     } catch (RIFCSException e) {
/* 572 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   public List<ExistenceDate> getExistenceDates()
/*     */   {
/* 584 */     return this.existenceDates;
/*     */   }
/*     */ 
/*     */   public AccessPolicy newAccessPolicy()
/*     */     throws RIFCSException
/*     */   {
/* 603 */     return new AccessPolicy(newElement("accessPolicy"));
/*     */   }
/*     */ 
/*     */   public void addAccessPolicy(AccessPolicy accessPolicy)
/*     */   {
/* 615 */     getElement().appendChild(accessPolicy.getElement());
/* 616 */     this.aps.add(accessPolicy);
/*     */   }
/*     */ 
/*     */   public void addAccessPolicy(String accessPloicyVal)
/*     */   {
/* 627 */     AccessPolicy policy = null;
/*     */     try {
/* 629 */       policy = newAccessPolicy();
/*     */     }
/*     */     catch (RIFCSException e) {
/* 632 */       e.printStackTrace();
/*     */     }
/* 634 */     policy.setValue(accessPloicyVal);
/* 635 */     getElement().appendChild(policy.getElement());
/* 636 */     this.aps.add(policy);
/*     */   }
/*     */ 
/*     */   public List<AccessPolicy> getAccessPolicies()
/*     */   {
/* 647 */     return this.aps;
/*     */   }
/*     */ 
/*     */   public RelatedInfo newRelatedInfo()
/*     */     throws RIFCSException
/*     */   {
/* 665 */     return new RelatedInfo(newElement("relatedInfo"));
/*     */   }
/*     */ 
/*     */   public void addRelatedInfo(RelatedInfo relatedInfo)
/*     */   {
/* 677 */     getElement().appendChild(relatedInfo.getElement());
/* 678 */     this.ris.add(relatedInfo);
/*     */   }
/*     */ 
/*     */   public List<RelatedInfo> getRelatedInfo()
/*     */   {
/* 690 */     return this.ris;
/*     */   }
/*     */ 
/*     */   private void initStructures()
/*     */     throws RIFCSException
/*     */   {
/* 697 */     initIdentifiers();
/* 698 */     initNames();
/* 699 */     initLocations();
/* 700 */     initCoverage();
/* 701 */     initRelatedObjects();
/* 702 */     initSubjects();
/* 703 */     initDescriptions();
/* 704 */     initRelatedInfo();
/* 705 */     initAccessPolicies();
/*     */   }
/*     */ 
/*     */   private void initIdentifiers() throws RIFCSException
/*     */   {
/* 710 */     NodeList nl = super.getElements("identifier");
/*     */ 
/* 712 */     for (int i = 0; i < nl.getLength(); i++)
/*     */     {
/* 714 */       this.identifiers.add(new Identifier(nl.item(i)));
/*     */     }
/*     */   }
/*     */ 
/*     */   private void initNames() throws RIFCSException
/*     */   {
/* 720 */     NodeList nl = super.getElements("name");
/*     */ 
/* 722 */     for (int i = 0; i < nl.getLength(); i++)
/*     */     {
/* 724 */       this.names.add(new Name(nl.item(i)));
/*     */     }
/*     */   }
/*     */ 
/*     */   private void initLocations() throws RIFCSException
/*     */   {
/* 730 */     NodeList nl = super.getElements("location");
/*     */ 
/* 732 */     for (int i = 0; i < nl.getLength(); i++)
/*     */     {
/* 734 */       this.locations.add(new Location(nl.item(i)));
/*     */     }
/*     */   }
/*     */ 
/*     */   private void initCoverage() throws RIFCSException
/*     */   {
/* 740 */     NodeList nl = super.getElements("coverage");
/*     */ 
/* 742 */     for (int i = 0; i < nl.getLength(); i++)
/*     */     {
/* 744 */       this.coverages.add(new Coverage(nl.item(i)));
/*     */     }
/*     */   }
/*     */ 
/*     */   private void initRelatedObjects() throws RIFCSException
/*     */   {
/* 750 */     NodeList nl = super.getElements("relatedObject");
/*     */ 
/* 752 */     for (int i = 0; i < nl.getLength(); i++)
/*     */     {
/* 754 */       this.relatedObjects.add(new RelatedObject(nl.item(i)));
/*     */     }
/*     */   }
/*     */ 
/*     */   private void initSubjects() throws RIFCSException
/*     */   {
/* 760 */     NodeList nl = super.getElements("subject");
/*     */ 
/* 762 */     for (int i = 0; i < nl.getLength(); i++)
/*     */     {
/* 764 */       this.subjects.add(new Subject(nl.item(i)));
/*     */     }
/*     */   }
/*     */ 
/*     */   private void initDescriptions() throws RIFCSException
/*     */   {
/* 770 */     NodeList nl = super.getElements("description");
/*     */ 
/* 772 */     for (int i = 0; i < nl.getLength(); i++)
/*     */     {
/* 774 */       this.descriptions.add(new Description(nl.item(i)));
/*     */     }
/*     */   }
/*     */ 
/*     */   private void initRelatedInfo() throws RIFCSException
/*     */   {
/* 780 */     NodeList nl = super.getElements("relatedInfo");
/*     */ 
/* 782 */     for (int i = 0; i < nl.getLength(); i++)
/*     */     {
/* 784 */       this.ris.add(new RelatedInfo(nl.item(i)));
/*     */     }
/*     */   }
/*     */ 
/*     */   private void initAccessPolicies() throws RIFCSException
/*     */   {
/* 790 */     NodeList nl = super.getElements("accessPolicy");
/*     */ 
/* 792 */     for (int i = 0; i < nl.getLength(); i++)
/*     */     {
/* 794 */       this.aps.add(new AccessPolicy(nl.item(i)));
/*     */     }
/*     */   }
/*     */ }

/* Location:           /Users/przemyslaw/Downloads/rifcs-api-1.3.0.jar
 * Qualified Name:     org.ands.rifcs.base.Service
 * JD-Core Version:    0.6.2
 */