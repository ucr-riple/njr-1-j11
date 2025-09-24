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
/*     */ import org.ands.rifcs.base.Party;
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
/*     */ public class SampleRIFCSBuildParty
/*     */ {
/*  34 */   private static RIFCS rifcs = null;
/*     */ 
/*     */   public static void main(String[] args)
/*     */     throws RIFCSException, FileNotFoundException, SAXException, ParserConfigurationException, IOException
/*     */   {
/*  43 */     RIFCSWrapper mw = new RIFCSWrapper();
/*  44 */     rifcs = mw.getRIFCSObject();
/*  45 */     RegistryObject r = rifcs.newRegistryObject();
/*  46 */     r.setKey("Party");
/*  47 */     r.setGroup("ANDS");
/*  48 */     r.setOriginatingSource("http://myrepository.au.edu");
/*  49 */     Party p = r.newParty();
/*  50 */     p.setType("party");
/*     */ 
/*  52 */     Identifier identifier = p.newIdentifier();
/*  53 */     identifier.setType("handle");
/*  54 */     identifier.setValue("hdl:7651/myhandlesuffix");
/*  55 */     p.addIdentifier(identifier);
/*     */ 
/*  57 */     Name n = p.newName();
/*  58 */     n.setType("primary");
/*  59 */     NamePart np = n.newNamePart();
/*  60 */     np.setValue("Sample Collection");
/*  61 */     n.addNamePart(np);
/*  62 */     p.addName(n);
/*     */ 
/*  64 */     Location l = p.newLocation();
/*  65 */     Address ad = l.newAddress();
/*  66 */     Electronic e = ad.newElectronic();
/*  67 */     e.setValue("http://myrepository.au.edu/collections/collection1");
/*  68 */     e.setType("url");
/*  69 */     ad.addElectronic(e);
/*  70 */     l.addAddress(ad);
/*  71 */     p.addLocation(l);
/*     */ 
/*  73 */     Coverage cov = p.newCoverage();
/*  74 */     Spatial sp = cov.newSpatial();
/*  75 */     Temporal tmp = cov.newTemporal();
/*  76 */     tmp.addDate("1999-3-4", "dateFrom", "W3C");
/*  77 */     tmp.addDate("1999-3-4", "dateFrom", "W3C");
/*  78 */     sp.setValue("126.773437,-23.598894 127.652343,-27.405585 131.519531,-27.093039 131.167968,-24.081241 130.464843,-20.503868 127.828124,-19.843884 123.960937,-20.339134 123.433593,-22.141282 123.433593,-25.040485 123.785156,-28.183080 126.773437,-23.598894");
/*  79 */     sp.setType("kmlPolyCoords");
/*  80 */     cov.addSpatial(sp);
/*  81 */     cov.addTemporal(tmp);
/*  82 */     p.addCoverage(cov);
/*     */ 
/*  84 */     RelatedObject ro = p.newRelatedObject();
/*  85 */     ro.setKey("collection1");
/*  86 */     ro.addRelation("isOutputOf", null, null, null);
/*  87 */     p.addRelatedObject(ro);
/*  88 */     RelatedObject ro2 = p.newRelatedObject();
/*  89 */     ro2.setKey("party1");
/*  90 */     ro2.addRelation("isOwnerOf", null, null, null);
/*  91 */     p.addRelatedObject(ro2);
/*  92 */     RelatedObject ro3 = p.newRelatedObject();
/*  93 */     ro3.setKey("service1");
/*  94 */     ro3.addRelation("supports", null, null, null);
/*  95 */     p.addRelatedObject(ro3);
/*     */ 
/*  97 */     p.addSubject("subject1", "local", "identifier1", "en");
/*  98 */     p.addSubject("subject2", "local", "identifier2", "en");
/*     */ 
/* 100 */     p.addDescription("This is a sample description", "brief", null);
/*     */ 
/* 102 */     RelatedInfo ri = p.newRelatedInfo();
/* 103 */     ri.setIdentifier("http://external-server.edu/related-page.htm", "uri");
/* 104 */     ri.setTitle("A related information resource");
/* 105 */     ri.setNotes("Notes about the related information resource");
/* 106 */     p.addRelatedInfo(ri);
/*     */ 
/* 109 */     Right right = p.newRight();
/* 110 */     right.setAccessRights("Access Right Value", "Access Rights Uri", "Access Right Type");
/* 111 */     right.setLicence("Licence Value", "Licence Uri", "Licence Type");
/* 112 */     right.setRightsStatement("Right Statement Value", "Right Statement Uri");
/* 113 */     p.addRight(right);
/* 114 */     right = p.newRight();
/* 115 */     right.setAccessRights("Access Right Value2", "Access Rights Uri2", "Access Right Type2");
/* 116 */     right.setLicence("Licence Value2", "Licence Uri2", "Licence Type2");
/* 117 */     right.setRightsStatement("Right Statement Value2", "Right Statement Uri2");
/* 118 */     p.addRight(right);
/* 119 */     p.addExistenceDate("01-01-01", "dd-mm-yy", "12-12-12", "dd-mm-yy");
/*     */ 
/* 121 */     RelatedInfo relatedInfo = p.newRelatedInfo();
/* 122 */     relatedInfo.setIdentifier("related info", "text");
/* 123 */     relatedInfo.setNotes("Notes");
/* 124 */     relatedInfo.setTitle("Title");
/* 125 */     relatedInfo.setType("Type");
/* 126 */     p.addRelatedInfo(relatedInfo);
/* 127 */     relatedInfo = p.newRelatedInfo();
/* 128 */     relatedInfo.setIdentifier("related info1", "text");
/* 129 */     relatedInfo.setNotes("Notes1");
/* 130 */     relatedInfo.setTitle("Title1");
/* 131 */     relatedInfo.setType("Type");
/* 132 */     p.addRelatedInfo(relatedInfo);
/*     */ 
/* 134 */     r.addParty(p);
/*     */ 
/* 136 */     rifcs.addRegistryObject(r);
/* 137 */     mw.write(System.out);
/* 138 */     mw.validate();
/*     */   }
/*     */ }

/* Location:           /Users/przemyslaw/Downloads/rifcs-api-1.3.0.jar
 * Qualified Name:     org.ands.rifcs.example.SampleRIFCSBuildParty
 * JD-Core Version:    0.6.2
 */