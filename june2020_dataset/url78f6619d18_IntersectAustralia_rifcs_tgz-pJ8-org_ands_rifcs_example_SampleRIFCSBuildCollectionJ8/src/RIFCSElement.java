/*     */ package org.ands.rifcs.base;
/*     */ 
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.TimeZone;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ 
/*     */ public class RIFCSElement
/*     */ {
/*  40 */   private Element e = null;
/*     */ 
/*     */   protected RIFCSElement(Node n, String name)
/*     */     throws RIFCSException
/*     */   {
/*  56 */     if (n == null)
/*     */     {
/*  58 */       throw new RIFCSException("Null Node passed to constructor");
/*     */     }
/*     */ 
/*  61 */     if ((n instanceof Element))
/*     */     {
/*  63 */       if (!n.getNodeName().endsWith(name))
/*     */       {
/*  65 */         throw new RIFCSException("Mismatch tag name. Node tag is: " + n.getNodeName() + ", expected: " + name);
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/*  70 */       throw new RIFCSException("Node of type Element required in constructor");
/*     */     }
/*     */ 
/*  73 */     this.e = ((Element)n);
/*     */   }
/*     */ 
/*     */   protected String getAttributeValue(String name)
/*     */   {
/*  89 */     return this.e.getAttribute(name);
/*     */   }
/*     */ 
/*     */   protected void setAttributeValue(String name, String value)
/*     */   {
/* 105 */     this.e.setAttribute(name, value);
/*     */   }
/*     */ 
/*     */   protected void setAttributeValueNS(String ns, String name, String value)
/*     */   {
/* 123 */     this.e.setAttributeNS(ns, name, value);
/*     */   }
/*     */ 
/*     */   protected String getAttributeValueNS(String ns, String name)
/*     */   {
/* 138 */     return this.e.getAttributeNS(ns, name);
/*     */   }
/*     */ 
/*     */   protected String getAttributeValue(String ns, String localName)
/*     */   {
/* 158 */     return this.e.getAttributeNS(ns, localName);
/*     */   }
/*     */ 
/*     */   protected String getTextContent()
/*     */   {
/* 170 */     return this.e.getTextContent();
/*     */   }
/*     */ 
/*     */   protected void setTextContent(String value)
/*     */   {
/* 182 */     this.e.setTextContent(value);
/*     */   }
/*     */ 
/*     */   protected NodeList getElements(String localName)
/*     */   {
/* 197 */     return this.e.getElementsByTagNameNS("http://ands.org.au/standards/rif-cs/registryObjects", localName);
/*     */   }
/*     */ 
/*     */   protected List<Node> getChildElements(String localName)
/*     */   {
/* 213 */     NodeList nl = this.e.getChildNodes();
/* 214 */     List l = new ArrayList();
/* 215 */     for (int i = 0; i < nl.getLength(); i++)
/*     */     {
/* 217 */       if ((nl.item(i).getNodeType() == 1) && 
/* 218 */         (nl.item(i).getLocalName().equals(localName)))
/*     */       {
/* 220 */         l.add(nl.item(i));
/*     */       }
/*     */     }
/*     */ 
/* 224 */     return l;
/*     */   }
/*     */ 
/*     */   protected List<Node> getChildElements()
/*     */   {
/* 236 */     NodeList nl = this.e.getChildNodes();
/* 237 */     List l = new ArrayList();
/* 238 */     for (int i = 0; i < nl.getLength(); i++)
/*     */     {
/* 240 */       if (nl.item(i).getNodeType() == 1)
/*     */       {
/* 242 */         l.add(nl.item(i));
/*     */       }
/*     */     }
/*     */ 
/* 246 */     return l;
/*     */   }
/*     */ 
/*     */   protected Element getElement()
/*     */   {
/* 258 */     return this.e;
/*     */   }
/*     */ 
/*     */   protected Element newElement(String elementName)
/*     */   {
/* 271 */     return getElement().getOwnerDocument().createElementNS("http://ands.org.au/standards/rif-cs/registryObjects", elementName);
/*     */   }
/*     */ 
/*     */   protected static String formatDate(Date date)
/*     */   {
/* 283 */     SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
/* 284 */     Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
/* 285 */     cal.setTime(date);
/* 286 */     df.setCalendar(cal);
/* 287 */     return df.format(cal.getTime());
/*     */   }
/*     */ }

/* Location:           /Users/przemyslaw/Downloads/rifcs-api-1.3.0.jar
 * Qualified Name:     org.ands.rifcs.base.RIFCSElement
 * JD-Core Version:    0.6.2
 */