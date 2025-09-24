/*     */ package org.ands.rifcs.base;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ 
/*     */ public class Contributor extends RIFCSElement
/*     */ {
/*  36 */   private List<NamePart> nameParts = new ArrayList();
/*     */ 
/*     */   protected Contributor(Node n)
/*     */     throws RIFCSException
/*     */   {
/*  48 */     super(n, "contributor");
/*  49 */     initStructures();
/*     */   }
/*     */ 
/*     */   public void setSeq(int seq)
/*     */   {
/*  62 */     super.setAttributeValue("seq", String.valueOf(seq));
/*     */   }
/*     */ 
/*     */   public int getSeq()
/*     */   {
/*  76 */     String seq = super.getAttributeValue("seq");
/*  77 */     if ((seq == null) || (seq.equals("")))
/*     */     {
/*  79 */       return -1;
/*     */     }
/*  81 */     return Integer.valueOf(seq).intValue();
/*     */   }
/*     */ 
/*     */   public NamePart newNamePart()
/*     */     throws RIFCSException
/*     */   {
/*  99 */     return new NamePart(newElement("namePart"));
/*     */   }
/*     */ 
/*     */   public void addNamePart(NamePart namePart)
/*     */   {
/* 111 */     getElement().appendChild(namePart.getElement());
/* 112 */     this.nameParts.add(namePart);
/*     */   }
/*     */ 
/*     */   public void addNamePart(String namePart, String type)
/*     */     throws RIFCSException
/*     */   {
/* 127 */     NamePart np = newNamePart();
/* 128 */     np.setValue(namePart);
/* 129 */     np.setType(type);
/* 130 */     getElement().appendChild(np.getElement());
/* 131 */     this.nameParts.add(np);
/*     */   }
/*     */ 
/*     */   public List<NamePart> getNameParts()
/*     */   {
/* 143 */     return this.nameParts;
/*     */   }
/*     */ 
/*     */   private void initStructures()
/*     */     throws RIFCSException
/*     */   {
/* 150 */     NodeList nl = super.getElements("namePart");
/*     */ 
/* 152 */     for (int i = 0; i < nl.getLength(); i++)
/*     */     {
/* 154 */       this.nameParts.add(new NamePart(nl.item(i)));
/*     */     }
/*     */   }
/*     */ }

/* Location:           /Users/przemyslaw/Downloads/rifcs-api-1.3.0.jar
 * Qualified Name:     org.ands.rifcs.base.Contributor
 * JD-Core Version:    0.6.2
 */