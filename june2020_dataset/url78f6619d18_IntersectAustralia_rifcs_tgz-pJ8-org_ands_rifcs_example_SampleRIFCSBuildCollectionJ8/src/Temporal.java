/*     */ package org.ands.rifcs.base;
/*     */ 
/*     */ import java.text.DateFormat;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ 
/*     */ public class Temporal extends RIFCSElement
/*     */ {
/*  40 */   private List<TemporalCoverageDate> dates = new ArrayList();
/*  41 */   private List<Element> texts = new ArrayList();
/*     */ 
/*     */   protected Temporal(Node n)
/*     */     throws RIFCSException
/*     */   {
/*  53 */     super(n, "temporal");
/*     */   }
/*     */ 
/*     */   public TemporalCoverageDate newDate()
/*     */     throws RIFCSException
/*     */   {
/*  76 */     return new TemporalCoverageDate(newElement("date"));
/*     */   }
/*     */ 
/*     */   public List<TemporalCoverageDate> getDates()
/*     */   {
/*  88 */     return this.dates;
/*     */   }
/*     */ 
/*     */   public List<String> getText()
/*     */   {
/* 100 */     ArrayList al = new ArrayList();
/* 101 */     for (Iterator i = this.texts.iterator(); i.hasNext(); )
/*     */     {
/* 103 */       al.add(((Element)i.next()).getTextContent());
/*     */     }
/* 105 */     return al;
/*     */   }
/*     */ 
/*     */   public void addText(String text)
/*     */   {
/* 117 */     Element e = newElement("text");
/* 118 */     e.setTextContent(text);
/* 119 */     getElement().appendChild(e);
/* 120 */     this.texts.add(e);
/*     */   }
/*     */ 
/*     */   public void addDate(String date, String type)
/*     */     throws RIFCSException
/*     */   {
/* 138 */     addDate(date, type, "W3C");
/*     */   }
/*     */ 
/*     */   public void addDate(String date, String type, String dateFormat)
/*     */     throws RIFCSException
/*     */   {
/* 154 */     TemporalCoverageDate de = newDate();
/* 155 */     de.setType(type);
/* 156 */     de.setDateFormat(dateFormat);
/* 157 */     de.setValue(date);
/* 158 */     getElement().appendChild(de.getElement());
/* 159 */     this.dates.add(de);
/*     */   }
/*     */ 
/*     */   public void addDate(Date date, String type)
/*     */     throws RIFCSException
/*     */   {
/* 176 */     DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
/* 177 */     String text = df.format(date);
/* 178 */     String result = text.substring(0, 22) + ":" + text.substring(22);
/* 179 */     addDate(result, type);
/*     */   }
/*     */ 
/*     */   private void initStructures()
/*     */     throws RIFCSException
/*     */   {
/* 186 */     initTexts();
/* 187 */     initDates();
/*     */   }
/*     */ 
/*     */   private void initTexts() throws RIFCSException
/*     */   {
/* 192 */     NodeList nl = super.getElements("text");
/*     */ 
/* 194 */     for (int i = 0; i < nl.getLength(); i++)
/*     */     {
/* 196 */       this.texts.add((Element)nl.item(i));
/*     */     }
/*     */   }
/*     */ 
/*     */   private void initDates() throws RIFCSException
/*     */   {
/* 202 */     NodeList nl = super.getElements("date");
/*     */ 
/* 204 */     for (int i = 0; i < nl.getLength(); i++)
/*     */     {
/* 206 */       this.dates.add(new TemporalCoverageDate(nl.item(i)));
/*     */     }
/*     */   }
/*     */ }

/* Location:           /Users/przemyslaw/Downloads/rifcs-api-1.3.0.jar
 * Qualified Name:     org.ands.rifcs.base.Temporal
 * JD-Core Version:    0.6.2
 */