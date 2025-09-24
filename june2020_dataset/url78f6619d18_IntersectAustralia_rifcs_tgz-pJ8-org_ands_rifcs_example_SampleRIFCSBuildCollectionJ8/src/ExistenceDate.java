/*    */ package org.ands.rifcs.base;
/*    */ 
/*    */ import org.w3c.dom.Element;
/*    */ import org.w3c.dom.Node;
/*    */ 
/*    */ public class ExistenceDate extends RIFCSElement
/*    */ {
/* 31 */   protected CommonDateElement startDate = null;
/* 32 */   protected CommonDateElement endDate = null;
/*    */ 
/*    */   protected ExistenceDate(Node n)
/*    */     throws RIFCSException
/*    */   {
/* 43 */     super(n, "existenceDates");
/*    */   }
/*    */ 
/*    */   public void setStartDate(String value, String dateFormat)
/*    */     throws RIFCSException
/*    */   {
/* 53 */     CommonDateElement dateElement = new CommonDateElement(newElement("startDate"));
/* 54 */     dateElement.setDateFormat(dateFormat);
/* 55 */     dateElement.setValue(value);
/* 56 */     this.startDate = dateElement;
/* 57 */     getElement().appendChild(this.startDate.getElement());
/*    */   }
/*    */ 
/*    */   public CommonDateElement getStartDate()
/*    */   {
/* 65 */     return this.startDate;
/*    */   }
/*    */ 
/*    */   public void setEndDate(String value, String dateFormat)
/*    */     throws RIFCSException
/*    */   {
/* 75 */     CommonDateElement dateElement = new CommonDateElement(newElement("endDate"));
/* 76 */     dateElement.setDateFormat(dateFormat);
/* 77 */     dateElement.setValue(value);
/* 78 */     this.endDate = dateElement;
/* 79 */     getElement().appendChild(this.endDate.getElement());
/*    */   }
/*    */ 
/*    */   public CommonDateElement getEndDate()
/*    */   {
/* 89 */     return this.endDate;
/*    */   }
/*    */ }

/* Location:           /Users/przemyslaw/Downloads/rifcs-api-1.3.0.jar
 * Qualified Name:     org.ands.rifcs.base.ExistenceDate
 * JD-Core Version:    0.6.2
 */