/*     */ package org.ands.rifcs.base;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ 
/*     */ public class Physical extends RIFCSElement
/*     */ {
/*  35 */   private List<AddressPart> addressParts = new ArrayList();
/*     */ 
/*     */   protected Physical(Node n)
/*     */     throws RIFCSException
/*     */   {
/*  47 */     super(n, "physical");
/*  48 */     initStructures();
/*     */   }
/*     */ 
/*     */   public void setType(String type)
/*     */   {
/*  60 */     super.setAttributeValue("type", type);
/*     */   }
/*     */ 
/*     */   public String getType()
/*     */   {
/*  73 */     return super.getAttributeValue("type");
/*     */   }
/*     */ 
/*     */   public void setLanguage(String lang)
/*     */   {
/*  85 */     super.setAttributeValueNS("http://www.w3.org/XML/1998/namespace", "xml:lang", lang);
/*     */   }
/*     */ 
/*     */   public String getLanguage()
/*     */   {
/*  98 */     return super.getAttributeValueNS("http://www.w3.org/XML/1998/namespace", "xml:lang");
/*     */   }
/*     */ 
/*     */   public AddressPart newAddressPart()
/*     */     throws RIFCSException
/*     */   {
/* 116 */     return new AddressPart(newElement("addressPart"));
/*     */   }
/*     */ 
/*     */   public void addAddressPart(AddressPart addressPart)
/*     */   {
/* 133 */     getElement().appendChild(addressPart.getElement());
/* 134 */     this.addressParts.add(addressPart);
/*     */   }
/*     */ 
/*     */   public List<AddressPart> getAddressParts()
/*     */   {
/* 146 */     return this.addressParts;
/*     */   }
/*     */ 
/*     */   private void initStructures()
/*     */     throws RIFCSException
/*     */   {
/* 153 */     NodeList nl = super.getElements("addressPart");
/*     */ 
/* 155 */     for (int i = 0; i < nl.getLength(); i++)
/*     */     {
/* 157 */       this.addressParts.add(new AddressPart(nl.item(i)));
/*     */     }
/*     */   }
/*     */ }

/* Location:           /Users/przemyslaw/Downloads/rifcs-api-1.3.0.jar
 * Qualified Name:     org.ands.rifcs.base.Physical
 * JD-Core Version:    0.6.2
 */