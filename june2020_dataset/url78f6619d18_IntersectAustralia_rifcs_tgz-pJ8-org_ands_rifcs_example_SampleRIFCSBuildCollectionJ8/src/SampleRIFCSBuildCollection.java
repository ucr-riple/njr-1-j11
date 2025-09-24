/*     */ package org.ands.rifcs.example;
/*     */ 
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import org.ands.rifcs.base.Address;
/*     */ import org.ands.rifcs.base.CitationInfo;
/*     */ import org.ands.rifcs.base.CitationMetadata;
/*     */ import org.ands.rifcs.base.Collection;
/*     */ import org.ands.rifcs.base.Contributor;
/*     */ import org.ands.rifcs.base.Coverage;
/*     */ import org.ands.rifcs.base.Electronic;
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
/*     */ public class SampleRIFCSBuildCollection
/*     */ {
/*  34 */   private static RIFCS rifcs = null;
/*     */ 
/*     */   public static void main(String[] args)
/*     */     throws RIFCSException, FileNotFoundException, SAXException, ParserConfigurationException, IOException
/*     */   {
/*  43 */     RIFCSWrapper mw = new RIFCSWrapper();
/*  44 */     rifcs = mw.getRIFCSObject();
/*  45 */     RegistryObject r = rifcs.newRegistryObject();
/*  46 */     r.setKey("collection1");
/*  47 */     r.setGroup("ANDS");
/*  48 */     r.setOriginatingSource("http://myrepository.au.edu");
/*  49 */     Collection c = r.newCollection();
/*  50 */     c.setType("collection");
/*  51 */     c.addIdentifier("hdl:7651/myhandlesuffix", "handle");
/*  52 */     Right right = c.newRight();
/*  53 */     right.setAccessRights("Access Right Value", "Access Rights Uri", "Access Right Type");
/*  54 */     right.setLicence("Licence Value", "Licence Uri", "Licence Type");
/*  55 */     right.setRightsStatement("Right Statement Value", "Right Statement Uri");
/*  56 */     c.addRight(right);
/*  57 */     right = c.newRight();
/*  58 */     right.setAccessRights("Access Right Value2", "Access Rights Uri2", "Access Right Type2");
/*  59 */     right.setLicence("Licence Value2", "Licence Uri2", "Licence Type2");
/*  60 */     right.setRightsStatement("Right Statement Value2", "Right Statement Uri2");
/*  61 */     c.addRight(right);
/*  62 */     Name n = c.newName();
/*  63 */     n.setType("primary");
/*  64 */     NamePart np = n.newNamePart();
/*  65 */     np.setValue("Sample Collection");
/*  66 */     n.addNamePart(np);
/*  67 */     c.addName(n);
/*  68 */     Location l = c.newLocation();
/*  69 */     Address a = l.newAddress();
/*  70 */     Electronic e = a.newElectronic();
/*  71 */     e.setValue("http://myrepository.au.edu/collections/collection1");
/*  72 */     e.setType("url");
/*  73 */     a.addElectronic(e);
/*  74 */     l.addAddress(a);
/*  75 */     c.addLocation(l);
/*  76 */     RelatedObject ro = c.newRelatedObject();
/*  77 */     ro.setKey("activity1");
/*  78 */     ro.addRelation("isOutputOf", null, null, null);
/*  79 */     c.addRelatedObject(ro);
/*  80 */     RelatedObject ro2 = c.newRelatedObject();
/*  81 */     ro2.setKey("party1");
/*  82 */     ro2.addRelation("isOwnerOf", null, null, null);
/*  83 */     c.addRelatedObject(ro2);
/*  84 */     RelatedObject ro3 = c.newRelatedObject();
/*  85 */     ro3.setKey("service1");
/*  86 */     ro3.addRelation("supports", null, null, null);
/*  87 */     c.addRelatedObject(ro3);
/*  88 */     c.addSubject("subject1", "local", null);
/*  89 */     c.addSubject("subject2", "local", null);
/*  90 */     Coverage cov = c.newCoverage();
/*  91 */     Spatial sp = cov.newSpatial();
/*  92 */     Temporal tmp = cov.newTemporal();
/*  93 */     tmp.addDate("1999-3-4", "dateFrom", "W3C");
/*  94 */     tmp.addDate("1999-3-4", "dateFrom", "W3C");
/*  95 */     sp.setValue("126.773437,-23.598894 127.652343,-27.405585 131.519531,-27.093039 131.167968,-24.081241 130.464843,-20.503868 127.828124,-19.843884 123.960937,-20.339134 123.433593,-22.141282 123.433593,-25.040485 123.785156,-28.183080 126.773437,-23.598894");
/*  96 */     sp.setType("kmlPolyCoords");
/*  97 */     cov.addSpatial(sp);
/*  98 */     cov.addTemporal(tmp);
/*  99 */     c.addCoverage(cov);
/* 100 */     c.addDescription("This is a sample description", "brief", null);
/* 101 */     RelatedInfo ri = c.newRelatedInfo();
/* 102 */     ri.setIdentifier("http://external-server.edu/related-page.htm", "uri");
/* 103 */     ri.setTitle("A related information resource");
/* 104 */     ri.setNotes("Notes about the related information resource");
/* 105 */     c.addRelatedInfo(ri);
/* 106 */     CitationInfo ci = c.newCitationInfo();
/* 107 */     ci.setCitation("sasdgasdgsdgasdgasdgasdgasdgasdgsadgasdgasdgsg", "howardsss");
/* 108 */     c.addCitationInfo(ci);
/* 109 */     CitationInfo ci2 = c.newCitationInfo();
/* 110 */     CitationMetadata cim = ci2.newCitationMetadata();
/* 111 */     cim.setIdentifier("sdjhksdghkashdgkjashgd", "pod");
/* 112 */     Contributor cCont = cim.newContributor();
/* 113 */     cCont.setSeq(0);
/* 114 */     cCont.addNamePart("Monus", "surname");
/* 115 */     cCont.addNamePart("Leo", "sgiven");
/* 116 */     cim.addContributor(cCont);
/* 117 */     cim.setTitle("ashgfjhsagfjashgf");
/* 118 */     cim.setEdition("editionksjadhkjsahf");
/* 119 */     cim.setPlacePublished("sjdhgkjahsdgkahgkahsdkghaksdghkajhg");
/* 120 */     cim.setURL("sdjhgksjhgdk");
/* 121 */     cim.setContext("shdgjsgjasgjahdsgjsd");
/* 122 */     c.addCitationInfo(ci2);
/* 123 */     ci2.addCitationMetadata(cim);
/* 124 */     c.addDescription("Êº¢Â≠ó‰ªÆÂêç‰∫§„Åò„ÇäÊñáÊº¢Â≠ó‰ªÆÂêç‰∫§„Åò„ÇäÊñáÊº¢Â≠ó‰ªÆÂêç‰∫§„Åò„ÇäÊñáÊº¢Â≠ó‰ªÆÂêç‰∫§„Åò„ÇäÊñáÊº¢Â≠ó‰ªÆÂêç‰∫§„Åò„ÇäÊñáÊº¢Â≠ó‰ªÆÂêç‰∫§„Åò„ÇäÊñáÊº¢Â≠ó‰ªÆÂêç‰∫§„Åò„ÇäÊñáÊº¢Â≠ó‰ªÆÂêç‰∫§„Åò„ÇäÊñáÊº¢Â≠ó‰ªÆÂêç‰∫§„Åò„ÇäÊñáÊº¢Â≠ó‰ªÆÂêç‰∫§„Åò„ÇäÊñáÊº¢Â≠ó‰ªÆÂêç‰∫§„Åò„ÇäÊñáÊº¢Â≠ó‰ªÆÂêç‰∫§„Åò„ÇäÊñáÊº¢Â≠ó‰ªÆÂêç‰∫§„Åò„ÇäÊñá", "full", "eng");
/* 125 */     r.addCollection(c);
/* 126 */     rifcs.addRegistryObject(r);
/* 127 */     mw.write(System.out);
/* 128 */     mw.validate();
/*     */   }
/*     */ }

/* Location:           /Users/przemyslaw/Downloads/rifcs-api-1.3.0.jar
 * Qualified Name:     org.ands.rifcs.example.SampleRIFCSBuildCollection
 * JD-Core Version:    0.6.2
 */