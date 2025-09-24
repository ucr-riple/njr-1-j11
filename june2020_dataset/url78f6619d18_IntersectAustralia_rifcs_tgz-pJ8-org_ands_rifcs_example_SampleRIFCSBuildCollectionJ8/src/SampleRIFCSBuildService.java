/*     */ package org.ands.rifcs.example;
/*     */ 
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import org.ands.rifcs.base.Address;
/*     */ import org.ands.rifcs.base.Coverage;
/*     */ import org.ands.rifcs.base.Electronic;
/*     */ import org.ands.rifcs.base.Identifier;
/*     */ import org.ands.rifcs.base.Location;
/*     */ import org.ands.rifcs.base.Name;
/*     */ import org.ands.rifcs.base.NamePart;
/*     */ import org.ands.rifcs.base.RIFCS;
/*     */ import org.ands.rifcs.base.RIFCSException;
/*     */ import org.ands.rifcs.base.RIFCSWrapper;
/*     */ import org.ands.rifcs.base.RegistryObject;
/*     */ import org.ands.rifcs.base.RelatedInfo;
/*     */ import org.ands.rifcs.base.RelatedObject;
/*     */ import org.ands.rifcs.base.Right;
/*     */ import org.ands.rifcs.base.Service;
/*     */ import org.ands.rifcs.base.Spatial;
/*     */ import org.ands.rifcs.base.Temporal;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ public class SampleRIFCSBuildService
/*     */ {
/*  34 */   private static RIFCS rifcs = null;
/*     */ 
/*     */   public static void main(String[] args)
/*     */     throws RIFCSException, FileNotFoundException, SAXException, ParserConfigurationException, IOException
/*     */   {
/*  43 */     RIFCSWrapper mw = new RIFCSWrapper();
/*  44 */     rifcs = mw.getRIFCSObject();
/*  45 */     RegistryObject r = rifcs.newRegistryObject();
/*  46 */     r.setKey("Service");
/*  47 */     r.setGroup("ANDS");
/*  48 */     r.setOriginatingSource("http://myrepository.au.edu");
/*  49 */     Service s = r.newService();
/*  50 */     s.setType("service");
/*     */ 
/*  52 */     Identifier identifier = s.newIdentifier();
/*  53 */     identifier.setType("handle");
/*  54 */     identifier.setValue("hdl:7651/myhandlesuffix");
/*  55 */     s.addIdentifier(identifier);
/*     */ 
/*  57 */     Name n = s.newName();
/*  58 */     n.setType("primary");
/*  59 */     NamePart np = n.newNamePart();
/*  60 */     np.setValue("Sample Collection");
/*  61 */     n.addNamePart(np);
/*  62 */     s.addName(n);
/*     */ 
/*  64 */     Location l = s.newLocation();
/*  65 */     Address ad = l.newAddress();
/*  66 */     Electronic e = ad.newElectronic();
/*  67 */     e.setValue("http://myrepository.au.edu/collections/collection1");
/*  68 */     e.setType("url");
/*  69 */     ad.addElectronic(e);
/*  70 */     l.addAddress(ad);
/*  71 */     s.addLocation(l);
/*     */ 
/*  73 */     Coverage cov = s.newCoverage();
/*  74 */     Spatial sp = cov.newSpatial();
/*  75 */     Temporal tmp = cov.newTemporal();
/*  76 */     tmp.addDate("1999-3-4", "dateFrom", "W3C");
/*  77 */     tmp.addDate("1999-3-4", "dateFrom", "W3C");
/*  78 */     sp.setValue("126.773437,-23.598894 127.652343,-27.405585 131.519531,-27.093039 131.167968,-24.081241 130.464843,-20.503868 127.828124,-19.843884 123.960937,-20.339134 123.433593,-22.141282 123.433593,-25.040485 123.785156,-28.183080 126.773437,-23.598894");
/*  79 */     sp.setType("kmlPolyCoords");
/*  80 */     cov.addSpatial(sp);
/*  81 */     cov.addTemporal(tmp);
/*  82 */     s.addCoverage(cov);
/*     */ 
/*  84 */     RelatedObject ro = s.newRelatedObject();
/*  85 */     ro.setKey("collection1");
/*  86 */     ro.addRelation("isOutputOf", null, null, null);
/*  87 */     s.addRelatedObject(ro);
/*  88 */     RelatedObject ro2 = s.newRelatedObject();
/*  89 */     ro2.setKey("party1");
/*  90 */     ro2.addRelation("isOwnerOf", null, null, null);
/*  91 */     s.addRelatedObject(ro2);
/*  92 */     RelatedObject ro3 = s.newRelatedObject();
/*  93 */     ro3.setKey("service1");
/*  94 */     ro3.addRelation("supports", null, null, null);
/*  95 */     s.addRelatedObject(ro3);
/*     */ 
/*  97 */     s.addSubject("subject1", "local", "identifier1", "en");
/*  98 */     s.addSubject("subject2", "local", "identifier2", "en");
/*     */ 
/* 100 */     s.addDescription("This is a sample description", "brief", null);
/*     */ 
/* 102 */     s.addAccessPolicy("Access Policy");
/*     */ 
/* 104 */     Right right = s.newRight();
/* 105 */     right.setAccessRights("Access Right Value", "Access Rights Uri", "Access Right Type");
/* 106 */     right.setLicence("Licence Value", "Licence Uri", "Licence Type");
/* 107 */     right.setRightsStatement("Right Statement Value", "Right Statement Uri");
/* 108 */     s.addRight(right);
/* 109 */     right = s.newRight();
/* 110 */     right.setAccessRights("Access Right Value2", "Access Rights Uri2", "Access Right Type2");
/* 111 */     right.setLicence("Licence Value2", "Licence Uri2", "Licence Type2");
/* 112 */     right.setRightsStatement("Right Statement Value2", "Right Statement Uri2");
/* 113 */     s.addRight(right);
/*     */ 
/* 115 */     s.addExistenceDate("01-01-01", "dd-mm-yy", "12-12-12", "dd-mm-yy");
/*     */ 
/* 117 */     RelatedInfo relatedInfo = s.newRelatedInfo();
/* 118 */     relatedInfo.setIdentifier("related info", "text");
/* 119 */     relatedInfo.setNotes("Notes");
/* 120 */     relatedInfo.setTitle("Title");
/* 121 */     relatedInfo.setType("Type");
/* 122 */     s.addRelatedInfo(relatedInfo);
/* 123 */     relatedInfo = s.newRelatedInfo();
/* 124 */     relatedInfo.setIdentifier("related info1", "text");
/* 125 */     relatedInfo.setNotes("Notes1");
/* 126 */     relatedInfo.setTitle("Title1");
/* 127 */     relatedInfo.setType("Type");
/* 128 */     s.addRelatedInfo(relatedInfo);
/*     */ 
/* 130 */     r.addService(s);
/*     */ 
/* 132 */     rifcs.addRegistryObject(r);
/* 133 */     mw.write(System.out);
/* 134 */     mw.validate();
/*     */   }
/*     */ }

/* Location:           /Users/przemyslaw/Downloads/rifcs-api-1.3.0.jar
 * Qualified Name:     org.ands.rifcs.example.SampleRIFCSBuildService
 * JD-Core Version:    0.6.2
 */