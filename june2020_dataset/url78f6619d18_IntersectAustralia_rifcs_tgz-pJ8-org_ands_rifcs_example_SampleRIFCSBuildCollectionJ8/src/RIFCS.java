/*     */ package org.ands.rifcs.base;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.NodeList;
/*     */ 
/*     */ public class RIFCS
/*     */ {
/*  60 */   private Document doc = null;
/*  61 */   private HashMap<String, RegistryObject> ros = new HashMap();
/*  62 */   private HashMap<String, ArrayList<RegistryObject>> rosByClass = new HashMap();
/*     */ 
/*     */   public RIFCS()
/*     */     throws RIFCSException
/*     */   {
/*     */     try
/*     */     {
/*  76 */       DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
/*  77 */       factory.setNamespaceAware(true);
/*  78 */       DocumentBuilder builder = factory.newDocumentBuilder();
/*  79 */       this.doc = builder.newDocument();
/*  80 */       Element root = this.doc.createElementNS("http://ands.org.au/standards/rif-cs/registryObjects", "registryObjects");
/*  81 */       root.setAttributeNS("http://www.w3.org/2001/XMLSchema-instance", "xsi:schemaLocation", "http://ands.org.au/standards/rif-cs/registryObjects http://services.ands.org.au/home/orca/schemata/registryObjects.xsd");
/*  82 */       this.doc.appendChild(root);
/*  83 */       initObjectClassMap();
/*     */     }
/*     */     catch (ParserConfigurationException pce)
/*     */     {
/*  87 */       throw new RIFCSException(pce);
/*     */     }
/*     */   }
/*     */ 
/*     */   public RegistryObject newRegistryObject()
/*     */     throws RIFCSException
/*     */   {
/* 106 */     Element ro = this.doc.createElementNS("http://ands.org.au/standards/rif-cs/registryObjects", "registryObject");
/* 107 */     return new RegistryObject(ro);
/*     */   }
/*     */ 
/*     */   public RIFCS(Document d)
/*     */     throws RIFCSException
/*     */   {
/* 121 */     this.doc = d;
/* 122 */     initObjectClassMap();
/* 123 */     initRegistryObjects();
/*     */   }
/*     */ 
/*     */   public Document getDocument()
/*     */   {
/* 134 */     return this.doc;
/*     */   }
/*     */ 
/*     */   public List<RegistryObject> getCollections()
/*     */   {
/* 146 */     return (List)this.rosByClass.get("collection");
/*     */   }
/*     */ 
/*     */   public List<RegistryObject> getActivities()
/*     */   {
/* 158 */     return (List)this.rosByClass.get("activity");
/*     */   }
/*     */ 
/*     */   public List<RegistryObject> getParties()
/*     */   {
/* 170 */     return (List)this.rosByClass.get("party");
/*     */   }
/*     */ 
/*     */   public List<RegistryObject> getServices()
/*     */   {
/* 183 */     return (List)this.rosByClass.get("service");
/*     */   }
/*     */ 
/*     */   public Map<String, RegistryObject> getRegistryObjects()
/*     */   {
/* 195 */     return this.ros;
/*     */   }
/*     */ 
/*     */   public void addRegistryObject(RegistryObject r)
/*     */     throws RIFCSException
/*     */   {
/* 207 */     this.doc.getDocumentElement().appendChild(r.getElement());
/* 208 */     this.ros.put(r.getKey(), r);
/* 209 */     ((ArrayList)this.rosByClass.get(r.getObjectClassName())).add(r);
/*     */   }
/*     */ 
/*     */   private void initRegistryObjects()
/*     */     throws RIFCSException
/*     */   {
/* 218 */     NodeList nl = this.doc.getElementsByTagNameNS("http://ands.org.au/standards/rif-cs/registryObjects", "registryObject");
/*     */ 
/* 220 */     for (int i = 0; i < nl.getLength(); i++)
/*     */     {
/* 222 */       RegistryObject ro = new RegistryObject(nl.item(i));
/* 223 */       this.ros.put(ro.getKey(), ro);
/* 224 */       ((ArrayList)this.rosByClass.get(ro.getObjectClassName())).add(ro);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void initObjectClassMap()
/*     */   {
/* 234 */     this.rosByClass.put("collection", new ArrayList());
/* 235 */     this.rosByClass.put("activity", new ArrayList());
/* 236 */     this.rosByClass.put("party", new ArrayList());
/* 237 */     this.rosByClass.put("service", new ArrayList());
/*     */   }
/*     */ }

/* Location:           /Users/przemyslaw/Downloads/rifcs-api-1.3.0.jar
 * Qualified Name:     org.ands.rifcs.base.RIFCS
 * JD-Core Version:    0.6.2
 */