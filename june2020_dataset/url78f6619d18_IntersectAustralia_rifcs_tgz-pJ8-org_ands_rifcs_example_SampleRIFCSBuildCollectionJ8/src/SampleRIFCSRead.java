/*    */ package org.ands.rifcs.example;
/*    */ 
/*    */ import java.io.FileInputStream;
/*    */ import java.io.FileNotFoundException;
/*    */ import java.io.IOException;
/*    */ import java.io.PrintStream;
/*    */ import java.net.MalformedURLException;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import javax.xml.parsers.ParserConfigurationException;
/*    */ import org.ands.rifcs.base.Collection;
/*    */ import org.ands.rifcs.base.Name;
/*    */ import org.ands.rifcs.base.NamePart;
/*    */ import org.ands.rifcs.base.RIFCS;
/*    */ import org.ands.rifcs.base.RIFCSException;
/*    */ import org.ands.rifcs.base.RIFCSWrapper;
/*    */ import org.ands.rifcs.base.RegistryObject;
/*    */ import org.ands.rifcs.ch.RIFCSReader;
/*    */ import org.xml.sax.SAXException;
/*    */ 
/*    */ public class SampleRIFCSRead
/*    */ {
/* 39 */   private static RIFCS rifcs = null;
/*    */ 
/*    */   public static void main(String[] args) throws RIFCSException, FileNotFoundException, SAXException, ParserConfigurationException, IOException, MalformedURLException
/*    */   {
/* 43 */     RIFCSReader rr = new RIFCSReader();
/* 44 */     rr.mapToDOM(new FileInputStream(args[0]));
/* 45 */     RIFCSWrapper rw = new RIFCSWrapper(rr.getDocument());
/* 46 */     rw.validate();
/* 47 */     RIFCS rifcs = rw.getRIFCSObject();
/*    */ 
/* 49 */     List list = rifcs.getCollections();
/*    */     Iterator j;
/* 50 */     for (Iterator i = list.iterator(); i.hasNext(); 
/* 55 */       j.hasNext())
/*    */     {
/* 52 */       RegistryObject ro = (RegistryObject)i.next();
/* 53 */       Collection c = (Collection)ro.getClassObject();
/* 54 */       j = c.getNames().iterator();
///* 55 */       continue;
/*    */ 
///* 57 */       Name n = (Name)j.next();
///* 58 */       if (n.getType().equals("primary"))
///*    */       {
///* 60 */         Iterator k = n.getNameParts().iterator();
///* 61 */         while (k.hasNext())
///* 62 */           System.out.println(((NamePart)k.next()).getValue() + " (" + ro.getKey() + ")");
///*    */       }
/*    */     }
/*    */   }
/*    */ }

/* Location:           /Users/przemyslaw/Downloads/rifcs-api-1.3.0.jar
 * Qualified Name:     org.ands.rifcs.example.SampleRIFCSRead
 * JD-Core Version:    0.6.2
 */