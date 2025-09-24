/*     */ package org.ands.rifcs.example;
/*     */ 
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import org.ands.rifcs.base.Activity;
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
/*     */ import org.ands.rifcs.base.Spatial;
/*     */ import org.ands.rifcs.base.Temporal;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ public class SampleRIFCSBuildActivity
/*     */ {
/*  34 */   private static RIFCS rifcs = null;
/*     */ 
/*     */   public static void main(String[] args)
/*     */     throws RIFCSException, FileNotFoundException, SAXException, ParserConfigurationException, IOException
/*     */   {
/*  43 */     RIFCSWrapper mw = new RIFCSWrapper();
/*  44 */     rifcs = mw.getRIFCSObject();
/*  45 */     RegistryObject r = rifcs.newRegistryObject();
/*  46 */     r.setKey("Activity");
/*  47 */     r.setGroup("ANDS");
/*  48 */     r.setOriginatingSource("http://myrepository.au.edu");
/*  49 */     Activity a = r.newActivity();
/*  50 */     a.setType("activity");
/*     */ 
/*  52 */     Identifier identifier = a.newIdentifier();
/*  53 */     identifier.setType("handle");
/*  54 */     identifier.setValue("hdl:7651/myhandlesuffix");
/*  55 */     a.addIdentifier(identifier);
/*     */ 
/*  57 */     Name n = a.newName();
/*  58 */     n.setType("primary");
/*  59 */     NamePart np = n.newNamePart();
/*  60 */     np.setValue("Sample Collection");
/*  61 */     n.addNamePart(np);
/*  62 */     a.addName(n);
/*     */ 
/*  64 */     Location l = a.newLocation();
/*  65 */     Address ad = l.newAddress();
/*  66 */     Electronic e = ad.newElectronic();
/*  67 */     e.setValue("http://myrepository.au.edu/collections/collection1");
/*  68 */     e.setType("url");
/*  69 */     ad.addElectronic(e);
/*  70 */     l.addAddress(ad);
/*  71 */     a.addLocation(l);
/*     */ 
/*  73 */     Coverage cov = a.newCoverage();
/*  74 */     Spatial sp = cov.newSpatial();
/*  75 */     Temporal tmp = cov.newTemporal();
/*  76 */     tmp.addDate("1999-3-4", "dateFrom", "W3C");
/*  77 */     tmp.addDate("1999-3-4", "dateFrom", "W3C");
/*  78 */     sp.setValue("126.773437,-23.598894 127.652343,-27.405585 131.519531,-27.093039 131.167968,-24.081241 130.464843,-20.503868 127.828124,-19.843884 123.960937,-20.339134 123.433593,-22.141282 123.433593,-25.040485 123.785156,-28.183080 126.773437,-23.598894");
/*  79 */     sp.setType("kmlPolyCoords");
/*  80 */     cov.addSpatial(sp);
/*  81 */     cov.addTemporal(tmp);
/*  82 */     a.addCoverage(cov);
/*     */ 
/*  84 */     RelatedObject ro = a.newRelatedObject();
/*  85 */     ro.setKey("collection1");
/*  86 */     ro.addRelation("isOutputOf", null, null, null);
/*  87 */     a.addRelatedObject(ro);
/*  88 */     RelatedObject ro2 = a.newRelatedObject();
/*  89 */     ro2.setKey("party1");
/*  90 */     ro2.addRelation("isOwnerOf", null, null, null);
/*  91 */     a.addRelatedObject(ro2);
/*  92 */     RelatedObject ro3 = a.newRelatedObject();
/*  93 */     ro3.setKey("service1");
/*  94 */     ro3.addRelation("supports", null, null, null);
/*  95 */     a.addRelatedObject(ro3);
/*     */ 
/*  97 */     a.addSubject("subject1", "local", "identifier1", "en");
/*  98 */     a.addSubject("subject2", "local", "identifier2", "en");
/*     */ 
/* 100 */     a.addDescription("This is a sample description", "brief", null);
/*     */ 
/* 102 */     RelatedInfo ri = a.newRelatedInfo();
/* 103 */     ri.setIdentifier("http://external-server.edu/related-page.htm", "uri");
/* 104 */     ri.setTitle("A related information resource");
/* 105 */     ri.setNotes("Notes about the related information resource");
/* 106 */     a.addRelatedInfo(ri);
/*     */ 
/* 109 */     Right right = a.newRight();
/* 110 */     right.setAccessRights("Access Right Value", "Access Rights Uri", "Access Right Type");
/* 111 */     right.setLicence("Licence Value", "Licence Uri", "Licence Type");
/* 112 */     right.setRightsStatement("Right Statement Value", "Right Statement Uri");
/* 113 */     a.addRight(right);
/* 114 */     right = a.newRight();
/* 115 */     right.setAccessRights("Access Right Value2", "Access Rights Uri2", "Access Right Type2");
/* 116 */     right.setLicence("Licence Value2", "Licence Uri2", "Licence Type2");
/* 117 */     right.setRightsStatement("Right Statement Value2", "Right Statement Uri2");
/* 118 */     a.addRight(right);
/* 119 */     a.addExistenceDate("01-01-01", "dd-mm-yy", "12-12-12", "dd-mm-yy");
/*     */ 
/* 121 */     RelatedInfo relatedInfo = a.newRelatedInfo();
/* 122 */     relatedInfo.setIdentifier("related info", "text");
/* 123 */     relatedInfo.setNotes("Notes");
/* 124 */     relatedInfo.setTitle("Title");
/* 125 */     relatedInfo.setType("Type");
/* 126 */     a.addRelatedInfo(relatedInfo);
/* 127 */     relatedInfo = a.newRelatedInfo();
/* 128 */     relatedInfo.setIdentifier("related info1", "text");
/* 129 */     relatedInfo.setNotes("Notes1");
/* 130 */     relatedInfo.setTitle("Title1");
/* 131 */     relatedInfo.setType("Type");
/* 132 */     a.addRelatedInfo(relatedInfo);
/*     */ 
/* 134 */     r.addActivity(a);
/*     */ 
/* 136 */     rifcs.addRegistryObject(r);
/* 137 */     mw.write(System.out);
/* 138 */     mw.validate();
/*     */   }
/*     */ }

/* Location:           /Users/przemyslaw/Downloads/rifcs-api-1.3.0.jar
 * Qualified Name:     org.ands.rifcs.example.SampleRIFCSBuildActivity
 * JD-Core Version:    0.6.2
 */