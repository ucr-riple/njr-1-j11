/*     */ package org.ands.rifcs.base;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ 
/*     */ public class Name extends RIFCSElement
/*     */ {
/*  36 */   private List<NamePart> nameParts = new ArrayList();
/*     */ 
/*     */   protected Name(Node n)
/*     */     throws RIFCSException
/*     */   {
/*  48 */     super(n, "name");
/*  49 */     initStructures();
/*     */   }
/*     */ 
/*     */   public void setType(String type)
/*     */   {
/*  61 */     super.setAttributeValue("type", type);
/*     */   }
/*     */ 
/*     */   public String getType()
/*     */   {
/*  74 */     return super.getAttributeValue("type");
/*     */   }
/*     */ 
/*     */   public void setLanguage(String lang)
/*     */   {
/*  86 */     super.setAttributeValueNS("http://www.w3.org/XML/1998/namespace", "xml:lang", lang);
/*     */   }
/*     */ 
/*     */   public String getLanguage()
/*     */   {
/*  99 */     return super.getAttributeValueNS("http://www.w3.org/XML/1998/namespace", "xml:lang");
/*     */   }
/*     */ 
/*     */   public void setDateFrom(Date dateFrom)
/*     */   {
/* 112 */     super.setAttributeValue("dateFrom", RegistryObject.formatDate(dateFrom));
/*     */   }
/*     */ 
/*     */   public void setDateFrom(String dateFrom)
/*     */   {
/* 126 */     super.setAttributeValue("dateFrom", dateFrom);
/*     */   }
/*     */ 
/*     */   public String getDateFrom()
/*     */   {
/* 135 */     return super.getAttributeValue("dateFrom");
/*     */   }
/*     */ 
/*     */   public void setDateTo(Date dateTo)
/*     */   {
/* 147 */     super.setAttributeValue("dateTo", RegistryObject.formatDate(dateTo));
/*     */   }
/*     */ 
/*     */   public void setDateTo(String dateTo)
/*     */   {
/* 161 */     super.setAttributeValue("dateTo", dateTo);
/*     */   }
/*     */ 
/*     */   public String getDateTo()
/*     */   {
/* 170 */     return super.getAttributeValue("dateTo");
/*     */   }
/*     */ 
/*     */   public NamePart newNamePart()
/*     */     throws RIFCSException
/*     */   {
/* 188 */     return new NamePart(newElement("namePart"));
/*     */   }
/*     */ 
/*     */   public void addNamePart(NamePart namePart)
/*     */   {
/* 200 */     getElement().appendChild(namePart.getElement());
/* 201 */     this.nameParts.add(namePart);
/*     */   }
/*     */ 
/*     */   public void addNamePart(String namePart, String type)
/*     */     throws RIFCSException
/*     */   {
/* 216 */     NamePart np = newNamePart();
/* 217 */     np.setValue(namePart);
/* 218 */     np.setType(type);
/* 219 */     getElement().appendChild(np.getElement());
/* 220 */     this.nameParts.add(np);
/*     */   }
/*     */ 
/*     */   public List<NamePart> getNameParts()
/*     */   {
/* 232 */     return this.nameParts;
/*     */   }
/*     */ 
/*     */   private void initStructures()
/*     */     throws RIFCSException
/*     */   {
/* 239 */     NodeList nl = super.getElements("namePart");
/*     */ 
/* 241 */     for (int i = 0; i < nl.getLength(); i++)
/*     */     {
/* 243 */       this.nameParts.add(new NamePart(nl.item(i)));
/*     */     }
/*     */   }
/*     */ }

/* Location:           /Users/przemyslaw/Downloads/rifcs-api-1.3.0.jar
 * Qualified Name:     org.ands.rifcs.base.Name
 * JD-Core Version:    0.6.2
 */