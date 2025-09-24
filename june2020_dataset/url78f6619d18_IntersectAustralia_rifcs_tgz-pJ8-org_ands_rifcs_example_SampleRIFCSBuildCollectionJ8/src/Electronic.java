/*     */ package org.ands.rifcs.base;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ 
/*     */ public class Electronic extends RIFCSElement
/*     */ {
/*  36 */   List<Arg> args = new ArrayList();
/*     */ 
/*     */   protected Electronic(Node n)
/*     */     throws RIFCSException
/*     */   {
/*  48 */     super(n, "electronic");
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
/*     */   public Arg newArg()
/*     */     throws RIFCSException
/*     */   {
/*  92 */     return new Arg(newElement("arg"));
/*     */   }
/*     */ 
/*     */   public void addArg(String name, String required, String type, String use)
/*     */     throws RIFCSException
/*     */   {
/* 113 */     Arg arg = newArg();
/* 114 */     arg.setName(name);
/* 115 */     arg.setRequired(required);
/* 116 */     arg.setType(type);
/* 117 */     arg.setUse(use);
/* 118 */     addArg(arg);
/*     */   }
/*     */ 
/*     */   public List<Arg> getArgs()
/*     */   {
/* 130 */     return this.args;
/*     */   }
/*     */ 
/*     */   public void addArg(Arg arg)
/*     */   {
/* 142 */     getElement().appendChild(arg.getElement());
/* 143 */     this.args.add(arg);
/*     */   }
/*     */ 
/*     */   public void setValue(String valueUri)
/*     */   {
/* 156 */     Element value = newElement("value");
/* 157 */     value.setTextContent(valueUri);
/* 158 */     getElement().appendChild(value);
/*     */   }
/*     */ 
/*     */   public String getValue()
/*     */   {
/* 171 */     NodeList nl = super.getElements("value");
/* 172 */     if (nl.getLength() == 1)
/*     */     {
/* 174 */       return nl.item(0).getTextContent();
/*     */     }
/*     */ 
/* 177 */     return null;
/*     */   }
/*     */ 
/*     */   private void initStructures()
/*     */     throws RIFCSException
/*     */   {
/* 184 */     NodeList nl = super.getElements("arg");
/*     */ 
/* 186 */     for (int i = 0; i < nl.getLength(); i++)
/*     */     {
/* 188 */       this.args.add(new Arg(nl.item(i)));
/*     */     }
/*     */   }
/*     */ }

/* Location:           /Users/przemyslaw/Downloads/rifcs-api-1.3.0.jar
 * Qualified Name:     org.ands.rifcs.base.Electronic
 * JD-Core Version:    0.6.2
 */