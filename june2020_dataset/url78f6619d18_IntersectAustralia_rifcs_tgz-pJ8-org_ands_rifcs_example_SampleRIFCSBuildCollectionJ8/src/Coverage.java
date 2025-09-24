/*     */ package org.ands.rifcs.base;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ 
/*     */ public class Coverage extends RIFCSElement
/*     */ {
/*  36 */   private List<Spatial> spatials = new ArrayList();
/*  37 */   private List<Temporal> temporals = new ArrayList();
/*     */ 
/*     */   protected Coverage(Node n)
/*     */     throws RIFCSException
/*     */   {
/*  50 */     super(n, "coverage");
/*  51 */     initStructures();
/*     */   }
/*     */ 
/*     */   public Spatial newSpatial()
/*     */     throws RIFCSException
/*     */   {
/*  69 */     return new Spatial(newElement("spatial"));
/*     */   }
/*     */ 
/*     */   public void addSpatial(Spatial spatial)
/*     */   {
/*  81 */     getElement().appendChild(spatial.getElement());
/*  82 */     this.spatials.add(spatial);
/*     */   }
/*     */ 
/*     */   public List<Spatial> getSpatials()
/*     */   {
/*  94 */     return this.spatials;
/*     */   }
/*     */ 
/*     */   public Temporal newTemporal()
/*     */     throws RIFCSException
/*     */   {
/* 112 */     return new Temporal(newElement("temporal"));
/*     */   }
/*     */ 
/*     */   public void addTemporal(Temporal temporal)
/*     */   {
/* 124 */     getElement().appendChild(temporal.getElement());
/* 125 */     this.temporals.add(temporal);
/*     */   }
/*     */ 
/*     */   public void addTemporal(String text)
/*     */     throws RIFCSException
/*     */   {
/* 139 */     Temporal t = newTemporal();
/* 140 */     t.addText(text);
/* 141 */     getElement().appendChild(t.getElement());
/* 142 */     this.temporals.add(t);
/*     */   }
/*     */ 
/*     */   public void addTemporalDate(Date date, String type)
/*     */     throws RIFCSException
/*     */   {
/* 159 */     Temporal t = newTemporal();
/* 160 */     t.addDate(date, type);
/* 161 */     getElement().appendChild(t.getElement());
/* 162 */     this.temporals.add(t);
/*     */   }
/*     */ 
/*     */   public void addTemporalDate(String date, String type)
/*     */     throws RIFCSException
/*     */   {
/* 179 */     Temporal t = newTemporal();
/* 180 */     t.addDate(date, type);
/* 181 */     getElement().appendChild(t.getElement());
/* 182 */     this.temporals.add(t);
/*     */   }
/*     */ 
/*     */   public List<Temporal> getTemporals()
/*     */   {
/* 194 */     return this.temporals;
/*     */   }
/*     */ 
/*     */   private void initStructures()
/*     */     throws RIFCSException
/*     */   {
/* 201 */     initSpatials();
/* 202 */     initTemporals();
/*     */   }
/*     */ 
/*     */   private void initSpatials() throws RIFCSException
/*     */   {
/* 207 */     NodeList nl = super.getElements("spatial");
/*     */ 
/* 209 */     for (int i = 0; i < nl.getLength(); i++)
/*     */     {
/* 211 */       this.spatials.add(new Spatial(nl.item(i)));
/*     */     }
/*     */   }
/*     */ 
/*     */   private void initTemporals() throws RIFCSException
/*     */   {
/* 217 */     NodeList nl = super.getElements("temporal");
/*     */ 
/* 219 */     for (int i = 0; i < nl.getLength(); i++)
/*     */     {
/* 221 */       this.temporals.add(new Temporal(nl.item(i)));
/*     */     }
/*     */   }
/*     */ }

/* Location:           /Users/przemyslaw/Downloads/rifcs-api-1.3.0.jar
 * Qualified Name:     org.ands.rifcs.base.Coverage
 * JD-Core Version:    0.6.2
 */