/*    */ package pt.tecnico.uilib.forms;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ interface Messages
/*    */ {
/*    */   static String keyAlreadyExists(String paramString1, String paramString2) {
/* 14 */     return String.format("O campo '%s' está duplicado do formulário '%s'.", new Object[] { paramString2, paramString1 });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static String formNotFilled(String paramString) {
/* 22 */     return String.format("O formulário '%s' não está preenchido. Invoque o método parse() primeiro.", new Object[] { paramString });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static String keyNotFound(String paramString1, String paramString2) {
/* 31 */     return String.format("O campo '%s' não existe no formulário '%s'.", new Object[] { paramString2, paramString1 });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static String typeMismatch(String paramString1, String paramString2, String paramString3, String paramString4) {
/* 42 */     return String.format("O campo '%s' do formulário '%s' tem o tipo '%s' e não '%s'.", new Object[] { paramString2, paramString1, paramString3, paramString4 });
/*    */   }
/*    */ }


/* Location:              C:\Users\tomgl\OneDrive\Documents\Hava\po-uilib (1).jar!\pt\tecnic\\uilib\forms\Messages.class
 * Java compiler version: 15 (59.0)
 * JD-Core Version:       1.1.3
 */