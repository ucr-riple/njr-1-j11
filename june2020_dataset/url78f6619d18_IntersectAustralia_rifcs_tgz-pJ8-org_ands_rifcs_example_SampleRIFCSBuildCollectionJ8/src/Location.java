/*     */ package org.ands.rifcs.base;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ 
/*     */ public class Location extends RIFCSElement
/*     */ {
/*  36 */   private List<Address> addresses = new ArrayList();
/*  37 */   private List<Spatial> spatials = new ArrayList();
/*     */ 
/*     */   protected Location(Node n)
/*     */     throws RIFCSException
/*     */   {
/*  50 */     super(n, "location");
/*  51 */     initStructures();
/*     */   }
/*     */ 
/*     */   public void setDateFrom(Date dateFrom)
/*     */   {
/*  64 */     super.setAttributeValue("dateFrom", RegistryObject.formatDate(dateFrom));
/*     */   }
/*     */ 
/*     */   public void setDateFrom(String dateFrom)
/*     */   {
/*  78 */     super.setAttributeValue("dateFrom", dateFrom);
/*     */   }
/*     */ 
/*     */   public String getDateFrom()
/*     */   {
/*  87 */     return super.getAttributeValue("dateFrom");
/*     */   }
/*     */ 
/*     */   public void setDateTo(Date dateTo)
/*     */   {
/* 100 */     super.setAttributeValue("dateTo", RegistryObject.formatDate(dateTo));
/*     */   }
/*     */ 
/*     */   public void setDateTo(String dateTo)
/*     */   {
/* 114 */     super.setAttributeValue("dateTo", dateTo);
/*     */   }
/*     */ 
/*     */   public String getDateTo()
/*     */   {
/* 123 */     return super.getAttributeValue("dateTo");
/*     */   }
/*     */ 
/*     */   public Address newAddress()
/*     */     throws RIFCSException
/*     */   {
/* 141 */     return new Address(newElement("address"));
/*     */   }
/*     */ 
/*     */   public void addAddress(Address address)
/*     */   {
/* 153 */     getElement().appendChild(address.getElement());
/* 154 */     this.addresses.add(address);
/*     */   }
/*     */ 
/*     */   public List<Address> getAddresses()
/*     */   {
/* 166 */     return this.addresses;
/*     */   }
/*     */ 
/*     */   public Spatial newSpatial()
/*     */     throws RIFCSException
/*     */   {
/* 184 */     return new Spatial(newElement("spatial"));
/*     */   }
/*     */ 
/*     */   public void addSpatial(Spatial spatial)
/*     */   {
/* 196 */     getElement().appendChild(spatial.getElement());
/* 197 */     this.spatials.add(spatial);
/*     */   }
/*     */ 
/*     */   public List<Spatial> getSpatials()
/*     */   {
/* 209 */     return this.spatials;
/*     */   }
/*     */ 
/*     */   private void initStructures()
/*     */     throws RIFCSException
/*     */   {
/* 216 */     initSpatials();
/* 217 */     initAddresses();
/*     */   }
/*     */ 
/*     */   private void initSpatials() throws RIFCSException
/*     */   {
/* 222 */     NodeList nl = super.getElements("spatial");
/*     */ 
/* 224 */     for (int i = 0; i < nl.getLength(); i++)
/*     */     {
/* 226 */       this.spatials.add(new Spatial(nl.item(i)));
/*     */     }
/*     */   }
/*     */ 
/*     */   private void initAddresses() throws RIFCSException
/*     */   {
/* 232 */     NodeList nl = super.getElements("address");
/*     */ 
/* 234 */     for (int i = 0; i < nl.getLength(); i++)
/*     */     {
/* 236 */       this.addresses.add(new Address(nl.item(i)));
/*     */     }
/*     */   }
/*     */ }

/* Location:           /Users/przemyslaw/Downloads/rifcs-api-1.3.0.jar
 * Qualified Name:     org.ands.rifcs.base.Location
 * JD-Core Version:    0.6.2
 */