/*     */ package org.ands.rifcs.base;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.io.StringWriter;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import javax.xml.transform.Result;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.transform.Transformer;
/*     */ import javax.xml.transform.TransformerException;
/*     */ import javax.xml.transform.TransformerFactory;
/*     */ import javax.xml.transform.dom.DOMSource;
/*     */ import javax.xml.transform.stream.StreamResult;
/*     */ import javax.xml.transform.stream.StreamSource;
/*     */ import javax.xml.validation.Schema;
/*     */ import javax.xml.validation.SchemaFactory;
/*     */ import javax.xml.validation.Validator;
/*     */ import org.w3c.dom.DOMConfiguration;
/*     */ import org.w3c.dom.DOMImplementation;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ import org.w3c.dom.ls.DOMImplementationLS;
/*     */ import org.w3c.dom.ls.LSOutput;
/*     */ import org.w3c.dom.ls.LSSerializer;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ public class RIFCSWrapper
/*     */ {
/*  63 */   private Document doc = null;
/*  64 */   private RIFCS rifcs = null;
/*     */ 
/*     */   public RIFCSWrapper()
/*     */     throws RIFCSException
/*     */   {
/*     */     try
/*     */     {
/*  79 */       DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
/*  80 */       factory.setNamespaceAware(true);
/*  81 */       DocumentBuilder builder = factory.newDocumentBuilder();
/*  82 */       this.doc = builder.newDocument();
/*  83 */       Element root = this.doc.createElementNS("http://ands.org.au/standards/rif-cs/registryObjects", "registryObjects");
/*  84 */       root.setAttributeNS("http://www.w3.org/2001/XMLSchema-instance", "xsi:schemaLocation", "http://ands.org.au/standards/rif-cs/registryObjects http://services.ands.org.au/home/orca/schemata/registryObjects.xsd");
/*  85 */       this.doc.appendChild(root);
/*  86 */       this.rifcs = new RIFCS(this.doc);
/*     */     }
/*     */     catch (ParserConfigurationException pce)
/*     */     {
/*  90 */       throw new RIFCSException(pce);
/*     */     }
/*     */   }
/*     */ 
/*     */   public RIFCSWrapper(Document d)
/*     */     throws RIFCSException
/*     */   {
/* 105 */     this.doc = d;
/* 106 */     this.rifcs = new RIFCS(d);
/*     */   }
/*     */ 
/*     */   public Document getRIFCSDocument()
/*     */   {
/* 118 */     return this.doc;
/*     */   }
/*     */ 
/*     */   public RIFCS getRIFCSObject()
/*     */   {
/* 130 */     return this.rifcs;
/*     */   }
/*     */ 
/*     */   public void write(OutputStream os)
/*     */   {
/* 142 */     DOMImplementation impl = this.doc.getImplementation();
/* 143 */     DOMImplementationLS implLS = (DOMImplementationLS)impl.getFeature("LS", "3.0");
/*     */ 
/* 145 */     LSOutput lso = implLS.createLSOutput();
/* 146 */     lso.setByteStream(os);
/* 147 */     LSSerializer writer = implLS.createLSSerializer();
/* 148 */     DOMConfiguration domConfig = writer.getDomConfig();
/* 149 */     domConfig.setParameter("format-pretty-print", Boolean.valueOf(true));
/* 150 */     writer.write(this.doc, lso);
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*     */     try
/*     */     {
/* 164 */       TransformerFactory factory = TransformerFactory.newInstance();
/* 165 */       Transformer transformer = factory.newTransformer();
/* 166 */       StringWriter writer = new StringWriter();
/* 167 */       Result result = new StreamResult(writer);
/* 168 */       Source source = new DOMSource(this.doc);
/* 169 */       transformer.transform(source, result);
/* 170 */       writer.close();
/* 171 */       return writer.toString();
/*     */     }
/*     */     catch (TransformerException te)
/*     */     {
/* 175 */       return null;
/*     */     }
/*     */     catch (IOException ioe) {
/*     */     }
/* 179 */     return null;
/*     */   }
/*     */ 
/*     */   public void validate()
/*     */     throws SAXException, MalformedURLException, IOException, ParserConfigurationException
/*     */   {
/* 205 */     SchemaFactory factory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
/*     */ 
/* 207 */     Schema schema = factory.newSchema(doXercesWorkaround());
/*     */ 
/* 210 */     Validator validator = schema.newValidator();
/* 211 */     validator.validate(new DOMSource(this.doc));
/*     */   }
/*     */ 
/*     */   private Source doXercesWorkaround()
/*     */     throws SAXException, MalformedURLException, IOException, ParserConfigurationException
/*     */   {
/* 218 */     DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
/* 219 */     factory.setNamespaceAware(true);
/* 220 */     DocumentBuilder builder = factory.newDocumentBuilder();
/*     */ 
/* 222 */     Document docRO = builder.parse(new URL("http://services.ands.org.au/home/orca/schemata/registryObjects.xsd").openStream());
/* 223 */     Document docActivity = builder.parse(new URL("http://services.ands.org.au/home/orca/schemata/activity.xsd").openStream());
/* 224 */     Document docCollection = builder.parse(new URL("http://services.ands.org.au/home/orca/schemata/collection.xsd").openStream());
/* 225 */     Document docParty = builder.parse(new URL("http://services.ands.org.au/home/orca/schemata/party.xsd").openStream());
/* 226 */     Document docService = builder.parse(new URL("http://services.ands.org.au/home/orca/schemata/service.xsd").openStream());
/* 227 */     Document docTypes = builder.parse(new URL("http://services.ands.org.au/home/orca/schemata/registryTypes.xsd").openStream());
/*     */ 
/* 229 */     removeElements(docRO, "xsd:include");
/* 230 */     Element xmlImport = docRO.createElementNS("http://www.w3.org/2001/XMLSchema", "xsd:import");
/* 231 */     xmlImport.setAttribute("namespace", "http://www.w3.org/XML/1998/namespace");
/* 232 */     xmlImport.setAttribute("schemaLocation", "http://www.w3.org/2001/xml.xsd");
/* 233 */     Element root = docRO.getDocumentElement();
/* 234 */     root.insertBefore(xmlImport, root.getElementsByTagName("xsd:element").item(0));
/*     */ 
/* 236 */     removeElements(docActivity, "xsd:include");
/* 237 */     removeElements(docCollection, "xsd:include");
/* 238 */     removeElements(docParty, "xsd:include");
/* 239 */     removeElements(docService, "xsd:include");
/* 240 */     removeElements(docTypes, "xsd:import");
/*     */ 
/* 242 */     addToSchema(docRO, docActivity);
/* 243 */     addToSchema(docRO, docCollection);
/* 244 */     addToSchema(docRO, docParty);
/* 245 */     addToSchema(docRO, docService);
/* 246 */     addToSchema(docRO, docTypes);
/*     */ 
/* 248 */     return new DOMSource(docRO);
/*     */   }
/*     */ 
/*     */   private void removeElements(Document targetDoc, String element)
/*     */   {
/* 256 */     NodeList nl = targetDoc.getDocumentElement().getElementsByTagName(element);
/*     */ 
/* 258 */     Node[] n = new Node[nl.getLength()];
/*     */ 
/* 262 */     for (int i = 0; i < nl.getLength(); i++)
/*     */     {
/* 264 */       n[i] = nl.item(i);
/*     */     }
/*     */ 
/* 267 */     for (int i = 0; i < n.length; i++)
/*     */     {
/* 269 */       targetDoc.getDocumentElement().removeChild(n[i]);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void addToSchema(Document targetDoc, Document sourceDoc)
/*     */   {
/* 278 */     NodeList nl = sourceDoc.getDocumentElement().getChildNodes();
/* 279 */     for (int i = 0; i < nl.getLength(); i++)
/*     */     {
/* 281 */       Node n = targetDoc.importNode(nl.item(i), true);
/* 282 */       targetDoc.getDocumentElement().appendChild(n);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void validate(String schemaUrl)
/*     */     throws SAXException, MalformedURLException, IOException
/*     */   {
/* 306 */     SchemaFactory factory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
/*     */ 
/* 308 */     URL theSchema = new URL(schemaUrl);
/*     */ 
/* 310 */     Source schemaFile = new StreamSource(theSchema.openStream());
/* 311 */     Schema schema = factory.newSchema(schemaFile);
/*     */ 
/* 314 */     Validator validator = schema.newValidator();
/*     */ 
/* 316 */     validator.validate(new DOMSource(this.doc));
/*     */   }
/*     */ }

/* Location:           /Users/przemyslaw/Downloads/rifcs-api-1.3.0.jar
 * Qualified Name:     org.ands.rifcs.base.RIFCSWrapper
 * JD-Core Version:    0.6.2
 */