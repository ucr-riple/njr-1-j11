/*    */ package org.ands.rifcs.base;
/*    */ 
/*    */ public class RIFCSException extends Exception
/*    */ {
/*    */   public RIFCSException(String reason)
/*    */   {
/* 37 */     super(reason);
/*    */   }
/*    */ 
/*    */   public RIFCSException(String reason, Throwable cause)
/*    */   {
/* 52 */     super(reason, cause);
/*    */   }
/*    */ 
/*    */   public RIFCSException(Throwable cause)
/*    */   {
/* 64 */     super(cause);
/*    */   }
/*    */ 
/*    */   public String getMessage()
/*    */   {
/* 76 */     return super.getMessage();
/*    */   }
/*    */ }

/* Location:           /Users/przemyslaw/Downloads/rifcs-api-1.3.0.jar
 * Qualified Name:     org.ands.rifcs.base.RIFCSException
 * JD-Core Version:    0.6.2
 */