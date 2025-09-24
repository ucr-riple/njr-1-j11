/*     */ package org.ands.rifcs.base;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ 
/*     */ public class ROElement
/*     */ {
/*  42 */   private Element e = null;
/*     */ 
/*     */   protected ROElement(Node n)
/*     */     throws RIFCSException
/*     */   {
/*  55 */     if (n == null)
/*     */     {
/*  57 */       throw new RIFCSException("Null Node passed to constructor");
/*     */     }
/*     */ 
/*  60 */     if (!(n instanceof Element))
/*     */     {
/*  62 */       throw new RIFCSException("Node of type Element required in constructor");
/*     */     }
/*     */ 
/*  65 */     String name = n.getNodeName();
/*     */ 
/*  67 */     if ((!name.equals("activity")) && 
/*  68 */       (!name.equals("collection")) && 
/*  69 */       (!name.equals("party")) && 
/*  70 */       (!name.equals("service")))
/*     */     {
/*  72 */       throw new RIFCSException("Invalid regsitry object type: " + name);
/*     */     }
/*     */ 
/*  76 */     this.e = ((Element)n);
/*     */   }
/*     */ 
/*     */   protected String getAttributeValue(String name)
/*     */   {
/*  92 */     return this.e.getAttribute(name);
/*     */   }
/*     */ 
/*     */   protected void setAttributeValue(String name, String value)
/*     */   {
/* 108 */     this.e.setAttribute(name, value);
/*     */   }
/*     */ 
/*     */   protected void setAttributeValueNS(String ns, String name, String value)
/*     */   {
/* 126 */     this.e.setAttributeNS(ns, name, value);
/*     */   }
/*     */ 
/*     */   protected String getAttributeValue(String ns, String localName)
/*     */   {
/* 146 */     return this.e.getAttributeNS(ns, localName);
/*     */   }
/*     */ 
/*     */   protected String getText()
/*     */   {
/* 158 */     return this.e.getTextContent();
/*     */   }
/*     */ 
/*     */   protected void setText(String value)
/*     */   {
/* 170 */     this.e.setTextContent(value);
/*     */   }
/*     */ 
/*     */   protected NodeList getElements(String localName)
/*     */   {
/* 185 */     return this.e.getElementsByTagNameNS("http://ands.org.au/standards/rif-cs/registryObjects", localName);
/*     */   }
/*     */ 
/*     */   protected List<Node> getChildElements(String localName)
/*     */   {
/* 201 */     NodeList nl = this.e.getChildNodes();
/* 202 */     List l = new ArrayList();
/* 203 */     for (int i = 0; i < nl.getLength(); i++)
/*     */     {
/* 205 */       if ((nl.item(i).getNodeType() == 1) && 
/* 206 */         (nl.item(i).getLocalName().equals(localName)))
/*     */       {
/* 208 */         l.add(nl.item(i));
/*     */       }
/*     */     }
/*     */ 
/* 212 */     return l;
/*     */   }
/*     */ 
/*     */   protected Element getElement()
/*     */   {
/* 224 */     return this.e;
/*     */   }
/*     */ 
/*     */   protected Element newElement(String elementName)
/*     */   {
/* 237 */     return getElement().getOwnerDocument().createElementNS("http://ands.org.au/standards/rif-cs/registryObjects", elementName);
/*     */   }
/*     */ }

/* Location:           /Users/przemyslaw/Downloads/rifcs-api-1.3.0.jar
 * Qualified Name:     org.ands.rifcs.base.ROElement
 * JD-Core Version:    0.6.2
 */