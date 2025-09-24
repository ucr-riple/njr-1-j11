/*     */ package org.ands.rifcs.ch;
/*     */ 
/*     */ import java.util.Stack;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.Locator;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.helpers.DefaultHandler;
/*     */ 
/*     */ public class DefaultRIFCSHandler extends DefaultHandler
/*     */   implements RIFCSHandler
/*     */ {
/*     */   private static final int BUFFER_SIZE = 4096;
/*  51 */   private Document doc = null;
/*     */ 
/*  54 */   private Stack<Element> elements = new Stack();
/*     */   private Locator locator;
/*     */ 
/*     */   public void setDocumentLocator(Locator locator)
/*     */   {
/*  67 */     this.locator = locator;
/*     */   }
/*     */ 
/*     */   public void startDocument()
/*     */     throws SAXException
/*     */   {
/*     */     try
/*     */     {
/*  81 */       this.doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*  85 */       throw new SAXException(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void startElement(String uri, String localName, String qName, Attributes attributes)
/*     */     throws SAXException
/*     */   {
/* 115 */     Element e = null;
/*     */ 
/* 117 */     if (uri.length() > 0)
/*     */     {
/* 119 */       e = this.doc.createElementNS(uri, qName);
/*     */     }
/*     */     else
/*     */     {
/* 123 */       e = this.doc.createElement(qName);
/*     */     }
/*     */ 
/* 126 */     for (int i = 0; i < attributes.getLength(); i++)
/*     */     {
/* 128 */       e.setAttribute(attributes.getQName(i), attributes.getValue(i));
/*     */     }
/*     */ 
/* 131 */     this.elements.push(e);
/*     */   }
/*     */ 
/*     */   public void characters(char[] chars, int start, int length)
/*     */     throws SAXException
/*     */   {
/* 154 */     String s = new String(chars, start, length);
/* 155 */     if (!s.matches("\\s+"))
/*     */     {
/* 157 */       Element e = (Element)this.elements.peek();
/*     */ 
/* 159 */       if (e.getTextContent().length() == 0)
/*     */       {
/* 161 */         e.setTextContent(s);
/*     */       }
/*     */       else
/*     */       {
/* 165 */         e.setTextContent(e.getTextContent() + s);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void skippedEntity(String name)
/*     */     throws SAXException
/*     */   {
/* 185 */     String s = "&" + name + ";";
/* 186 */     char[] text = s.toCharArray();
/* 187 */     characters(text, 0, text.length);
/*     */   }
/*     */ 
/*     */   public void endElement(String uri, String localName, String qName)
/*     */     throws SAXException
/*     */   {
/* 210 */     Element e = (Element)this.elements.pop();
/*     */ 
/* 212 */     if (!this.elements.empty())
/*     */     {
/* 214 */       ((Element)this.elements.peek()).appendChild(e);
/*     */     }
/*     */     else
/*     */     {
/* 218 */       this.doc.appendChild(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   private String printLocation(String s)
/*     */   {
/* 236 */     int line = this.locator.getLineNumber();
/* 237 */     int column = this.locator.getColumnNumber();
/* 238 */     return s + " at line " + line + "; column " + column;
/*     */   }
/*     */ 
/*     */   public Document getDocument()
/*     */   {
/* 251 */     return this.doc;
/*     */   }
/*     */ }

/* Location:           /Users/przemyslaw/Downloads/rifcs-api-1.3.0.jar
 * Qualified Name:     org.ands.rifcs.ch.DefaultRIFCSHandler
 * JD-Core Version:    0.6.2
 */