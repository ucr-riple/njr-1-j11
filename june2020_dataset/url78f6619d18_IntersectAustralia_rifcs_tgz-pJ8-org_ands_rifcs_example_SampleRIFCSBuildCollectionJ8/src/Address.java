/*     */ package org.ands.rifcs.base;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ 
/*     */ public class Address extends RIFCSElement
/*     */ {
/*  35 */   private List<Electronic> electronics = new ArrayList();
/*  36 */   private List<Physical> physicals = new ArrayList();
/*     */ 
/*     */   protected Address(Node n)
/*     */     throws RIFCSException
/*     */   {
/*  49 */     super(n, "address");
/*  50 */     initStructures();
/*     */   }
/*     */ 
/*     */   public Electronic newElectronic()
/*     */     throws RIFCSException
/*     */   {
/*  68 */     return new Electronic(newElement("electronic"));
/*     */   }
/*     */ 
/*     */   public void addElectronic(Electronic electronic)
/*     */   {
/*  80 */     getElement().appendChild(electronic.getElement());
/*  81 */     this.electronics.add(electronic);
/*     */   }
/*     */ 
/*     */   public List<Electronic> getElectronics()
/*     */   {
/*  93 */     return this.electronics;
/*     */   }
/*     */ 
/*     */   public Physical newPhysical()
/*     */     throws RIFCSException
/*     */   {
/* 111 */     return new Physical(newElement("physical"));
/*     */   }
/*     */ 
/*     */   public void addPhysical(Physical physical)
/*     */   {
/* 123 */     if (this.physicals == null)
/*     */     {
/* 125 */       this.physicals = new ArrayList();
/*     */     }
/*     */ 
/* 128 */     getElement().appendChild(physical.getElement());
/* 129 */     this.physicals.add(physical);
/*     */   }
/*     */ 
/*     */   public List<Physical> getPhysicalAddresses()
/*     */   {
/* 141 */     return this.physicals;
/*     */   }
/*     */ 
/*     */   private void initStructures()
/*     */     throws RIFCSException
/*     */   {
/* 148 */     initPhysicals();
/* 149 */     initElectronics();
/*     */   }
/*     */ 
/*     */   private void initPhysicals() throws RIFCSException
/*     */   {
/* 154 */     NodeList nl = super.getElements("physical");
/*     */ 
/* 156 */     for (int i = 0; i < nl.getLength(); i++)
/*     */     {
/* 158 */       this.physicals.add(new Physical(nl.item(i)));
/*     */     }
/*     */   }
/*     */ 
/*     */   private void initElectronics() throws RIFCSException
/*     */   {
/* 164 */     NodeList nl = super.getElements("electronic");
/*     */ 
/* 166 */     for (int i = 0; i < nl.getLength(); i++)
/*     */     {
/* 168 */       this.electronics.add(new Electronic(nl.item(i)));
/*     */     }
/*     */   }
/*     */ }

/* Location:           /Users/przemyslaw/Downloads/rifcs-api-1.3.0.jar
 * Qualified Name:     org.ands.rifcs.base.Address
 * JD-Core Version:    0.6.2
 */