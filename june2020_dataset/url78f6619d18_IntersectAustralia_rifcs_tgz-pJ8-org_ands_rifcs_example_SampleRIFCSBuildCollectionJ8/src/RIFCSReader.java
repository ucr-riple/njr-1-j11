/*     */ package org.ands.rifcs.ch;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import javax.xml.parsers.SAXParser;
/*     */ import javax.xml.parsers.SAXParserFactory;
/*     */ import org.w3c.dom.Document;
/*     */ import org.xml.sax.InputSource;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ public class RIFCSReader
/*     */ {
/*  43 */   private Document doc = null;
/*     */ 
/*     */   public void mapToDOM(InputStream is)
/*     */     throws SAXException, ParserConfigurationException, IOException
/*     */   {
/*  68 */     SAXParserFactory spf = SAXParserFactory.newInstance();
/*  69 */     spf.setFeature("http://xml.org/sax/features/namespaces", true);
/*  70 */     spf.setFeature("http://xml.org/sax/features/namespace-prefixes", true);
/*  71 */     SAXParser sp = spf.newSAXParser();
/*  72 */     DefaultRIFCSHandler ch = new DefaultRIFCSHandler();
/*     */ 
/*  74 */     InputSource source = new InputSource(is);
/*     */ 
/*  76 */     sp.parse(source, ch);
/*     */ 
/*  78 */     this.doc = ch.getDocument();
/*     */   }
/*     */ 
/*     */   public void mapToDOM(InputStream is, DefaultRIFCSHandler ch)
/*     */     throws SAXException, ParserConfigurationException, IOException
/*     */   {
/*  98 */     SAXParserFactory spf = SAXParserFactory.newInstance();
/*  99 */     spf.setFeature("http://xml.org/sax/features/namespaces", true);
/* 100 */     spf.setFeature("http://xml.org/sax/features/namespace-prefixes", true);
/* 101 */     SAXParser sp = spf.newSAXParser();
/*     */ 
/* 103 */     InputSource source = new InputSource(is);
/* 104 */     sp.parse(source, ch);
/* 105 */     this.doc = ch.getDocument();
/*     */   }
/*     */ 
/*     */   public Document getDocument()
/*     */   {
/* 118 */     return this.doc;
/*     */   }
/*     */ }

/* Location:           /Users/przemyslaw/Downloads/rifcs-api-1.3.0.jar
 * Qualified Name:     org.ands.rifcs.ch.RIFCSReader
 * JD-Core Version:    0.6.2
 */