/*     */ package org.ands.rifcs.base;
/*     */ 
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ 
/*     */ public class Right extends RIFCSElement
/*     */ {
/*  31 */   protected RightsInfo rightsStatement = null;
/*  32 */   protected RightsTypedInfo licence = null;
/*  33 */   protected RightsTypedInfo accessRights = null;
/*     */ 
/*     */   protected Right(Node n)
/*     */     throws RIFCSException
/*     */   {
/*  44 */     super(n, "rights");
/*     */   }
/*     */ 
/*     */   public void setRightsStatement(RightsInfo rightsStatement)
/*     */     throws RIFCSException
/*     */   {
/*  54 */     this.rightsStatement = rightsStatement;
/*  55 */     getElement().appendChild(this.rightsStatement.getElement());
/*     */   }
/*     */ 
/*     */   public void setRightsStatement(String value)
/*     */     throws RIFCSException
/*     */   {
/*  65 */     setRightsStatement(value, null);
/*     */   }
/*     */ 
/*     */   public void setRightsStatement(String value, String rightsUri)
/*     */     throws RIFCSException
/*     */   {
/*  76 */     RightsInfo rightsStatement = new RightsInfo(newElement("rightsStatement"));
/*  77 */     rightsStatement.setRightsUri(value);
/*  78 */     if (rightsUri != null)
/*  79 */       rightsStatement.setRightsUri(rightsUri);
/*  80 */     setRightsStatement(rightsStatement);
/*     */   }
/*     */ 
/*     */   public RightsInfo getRightsStatement()
/*     */   {
/*  89 */     return this.rightsStatement;
/*     */   }
/*     */ 
/*     */   public void setLicence(RightsTypedInfo licence)
/*     */     throws RIFCSException
/*     */   {
/*  98 */     this.licence = licence;
/*  99 */     getElement().appendChild(this.licence.getElement());
/*     */   }
/*     */ 
/*     */   public void setLicence(String value)
/*     */     throws RIFCSException
/*     */   {
/* 109 */     setLicence(value, null);
/*     */   }
/*     */ 
/*     */   public void setLicence(String value, String type)
/*     */     throws RIFCSException
/*     */   {
/* 120 */     setLicence(value, type, null);
/*     */   }
/*     */ 
/*     */   public void setLicence(String value, String rightsUri, String type)
/*     */     throws RIFCSException
/*     */   {
/* 132 */     RightsTypedInfo licence = new RightsTypedInfo(newElement("licence"));
/* 133 */     licence.setValue(value);
/* 134 */     if (rightsUri != null)
/* 135 */       licence.setRightsUri(rightsUri);
/* 136 */     if (type != null)
/* 137 */       licence.setType(type);
/* 138 */     setLicence(licence);
/*     */   }
/*     */ 
/*     */   public RightsTypedInfo getLicence()
/*     */   {
/* 147 */     return this.licence;
/*     */   }
/*     */ 
/*     */   public void setAccessRights(RightsTypedInfo accessRights)
/*     */     throws RIFCSException
/*     */   {
/* 157 */     this.accessRights = accessRights;
/* 158 */     getElement().appendChild(this.accessRights.getElement());
/*     */   }
/*     */ 
/*     */   public void setAccessRights(String value)
/*     */     throws RIFCSException
/*     */   {
/* 168 */     setAccessRights(value, null);
/*     */   }
/*     */ 
/*     */   public void setAccessRights(String value, String type)
/*     */     throws RIFCSException
/*     */   {
/* 179 */     setAccessRights(value, type, null);
/*     */   }
/*     */ 
/*     */   public void setAccessRights(String value, String rightsUri, String type)
/*     */     throws RIFCSException
/*     */   {
/* 191 */     RightsTypedInfo accessRights = new RightsTypedInfo(newElement("accessRights"));
/* 192 */     accessRights.setValue(value);
/* 193 */     if (rightsUri != null)
/* 194 */       accessRights.setRightsUri(rightsUri);
/* 195 */     if (type != null)
/* 196 */       accessRights.setType(type);
/* 197 */     setLicence(accessRights);
/*     */   }
/*     */ 
/*     */   public RightsTypedInfo getAccessRights()
/*     */   {
/* 206 */     return this.accessRights;
/*     */   }
/*     */ }

/* Location:           /Users/przemyslaw/Downloads/rifcs-api-1.3.0.jar
 * Qualified Name:     org.ands.rifcs.base.Right
 * JD-Core Version:    0.6.2
 */