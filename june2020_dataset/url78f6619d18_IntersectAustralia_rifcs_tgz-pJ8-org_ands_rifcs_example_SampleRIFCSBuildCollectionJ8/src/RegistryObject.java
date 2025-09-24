/*     */ package org.ands.rifcs.base;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ 
/*     */ public class RegistryObject extends RIFCSElement
/*     */ {
/*  38 */   private String objectClass = null;
/*     */ 
/*     */   protected RegistryObject(Node n)
/*     */     throws RIFCSException
/*     */   {
/*  50 */     super(n, "registryObject");
/*  51 */     initStructures();
/*     */   }
/*     */ 
/*     */   public void setKey(String keyValue)
/*     */   {
/*  63 */     Element key = newElement("key");
/*  64 */     key.setTextContent(keyValue);
/*  65 */     getElement().appendChild(key);
/*     */   }
/*     */ 
/*     */   public String getKey()
/*     */   {
/*  77 */     List nl = super.getChildElements("key");
/*  78 */     if (nl.size() == 1)
/*     */     {
/*  80 */       return ((Node)nl.get(0)).getTextContent();
/*     */     }
/*     */ 
/*  83 */     return null;
/*     */   }
/*     */ 
/*     */   public void setOriginatingSource(String sourceValue)
/*     */   {
/*  95 */     Element source = newElement("originatingSource");
/*  96 */     source.setTextContent(sourceValue);
/*  97 */     getElement().appendChild(source);
/*     */   }
/*     */ 
/*     */   public void setOriginatingSource(String sourceValue, String type)
/*     */   {
/* 113 */     Element source = newElement("originatingSource");
/* 114 */     source.setTextContent(sourceValue);
/* 115 */     source.setAttribute("type", type);
/* 116 */     getElement().appendChild(source);
/*     */   }
/*     */ 
/*     */   public void setOriginatingSourceType(String type)
/*     */   {
/* 129 */     NodeList nl = super.getElements("originatingSource");
/* 130 */     if (nl.getLength() == 1)
/*     */     {
/* 132 */       ((Element)nl.item(0)).setAttribute("type", type);
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getOriginatingSource()
/*     */   {
/* 145 */     NodeList nl = super.getElements("originatingSource");
/* 146 */     if (nl.getLength() == 1)
/*     */     {
/* 148 */       return nl.item(0).getTextContent();
/*     */     }
/*     */ 
/* 151 */     return null;
/*     */   }
/*     */ 
/*     */   public String getOriginatingSourceType()
/*     */   {
/* 163 */     NodeList nl = super.getElements("originatingSource");
/* 164 */     if (nl.getLength() == 1)
/*     */     {
/* 166 */       if (((Element)nl.item(0)).hasAttribute("type"))
/*     */       {
/* 168 */         return ((Element)nl.item(0)).getAttribute("type");
/*     */       }
/*     */ 
/* 172 */       return null;
/*     */     }
/*     */ 
/* 176 */     return null;
/*     */   }
/*     */ 
/*     */   public void setGroup(String group)
/*     */   {
/* 189 */     super.setAttributeValue("group", group);
/*     */   }
/*     */ 
/*     */   public String getGroup()
/*     */   {
/* 202 */     return super.getAttributeValue("group");
/*     */   }
/*     */ 
/*     */   public Collection newCollection()
/*     */     throws RIFCSException
/*     */   {
/* 220 */     Element coll = newElement("collection");
/* 221 */     return new Collection(coll);
/*     */   }
/*     */ 
/*     */   public Activity newActivity()
/*     */     throws RIFCSException
/*     */   {
/* 239 */     Element activity = newElement("activity");
/* 240 */     return new Activity(activity);
/*     */   }
/*     */ 
/*     */   public Party newParty()
/*     */     throws RIFCSException
/*     */   {
/* 258 */     Element party = newElement("party");
/* 259 */     return new Party(party);
/*     */   }
/*     */ 
/*     */   public Service newService()
/*     */     throws RIFCSException
/*     */   {
/* 277 */     Element service = newElement("service");
/* 278 */     return new Service(service);
/*     */   }
/*     */ 
/*     */   public void addCollection(Collection collection)
/*     */   {
/* 290 */     getElement().appendChild(collection.getElement());
/* 291 */     this.objectClass = "collection";
/*     */   }
/*     */ 
/*     */   public void addActivity(Activity activity)
/*     */   {
/* 303 */     getElement().appendChild(activity.getElement());
/* 304 */     this.objectClass = "party";
/*     */   }
/*     */ 
/*     */   public void addParty(Party party)
/*     */   {
/* 316 */     getElement().appendChild(party.getElement());
/* 317 */     this.objectClass = "party";
/*     */   }
/*     */ 
/*     */   public void addService(Service service)
/*     */   {
/* 329 */     getElement().appendChild(service.getElement());
/* 330 */     this.objectClass = "service";
/*     */   }
/*     */ 
/*     */   public String getObjectClassName()
/*     */   {
/* 343 */     return this.objectClass;
/*     */   }
/*     */ 
/*     */   public RIFCSElement getClassObject()
/*     */     throws RIFCSException
/*     */   {
/* 360 */     NodeList nl = super.getElements(this.objectClass);
/*     */ 
/* 362 */     if (nl.getLength() != 1)
/*     */     {
/* 364 */       return null;
/*     */     }
/*     */ 
/* 367 */     if (this.objectClass.equals("collection"))
/*     */     {
/* 369 */       return new Collection(nl.item(0));
/*     */     }
/* 371 */     if (this.objectClass.equals("party"))
/*     */     {
/* 373 */       return new Party(nl.item(0));
/*     */     }
/* 375 */     if (this.objectClass.equals("activity"))
/*     */     {
/* 377 */       return new Activity(nl.item(0));
/*     */     }
/* 379 */     if (this.objectClass.equals("service"))
/*     */     {
/* 381 */       return new Service(nl.item(0));
/*     */     }
/*     */ 
/* 384 */     return null;
/*     */   }
/*     */ 
/*     */   private void initStructures()
/*     */   {
/* 390 */     List nl = super.getChildElements();
/* 391 */     for (Iterator i = nl.iterator(); i.hasNext(); )
/*     */     {
/* 393 */       Node n = (Node)i.next();
/* 394 */       if (n.getNodeName().equals("collection"))
/*     */       {
/* 396 */         this.objectClass = "collection";
/* 397 */         break;
/*     */       }
/* 399 */       if (n.getNodeName().equals("party"))
/*     */       {
/* 401 */         this.objectClass = "party";
/* 402 */         break;
/*     */       }
/* 404 */       if (n.getNodeName().equals("activity"))
/*     */       {
/* 406 */         this.objectClass = "activity";
/* 407 */         break;
/*     */       }
/* 409 */       if (n.getNodeName().equals("service"))
/*     */       {
/* 411 */         this.objectClass = "service";
/* 412 */         break;
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           /Users/przemyslaw/Downloads/rifcs-api-1.3.0.jar
 * Qualified Name:     org.ands.rifcs.base.RegistryObject
 * JD-Core Version:    0.6.2
 */