package gener;

public class ChineseHandle {
   private  String str ;
   
   public String getStr(){
	   return this.str ;
   }
   
   public void SetStr( String Str){
	   this.str = Str ;
   }
   
   public ChineseHandle(String Str){
	   this.str = Str ;
   }
   /**
    *  判断中文字符。
    * @param a
    * @return
    */
   public boolean isChinese(char a) {
       int v = (int)a;
       return (v >=19968 && v <= 171941);
   }
   /**
    * 提取字符串中的中文。
    * @param str
    * @return
    */
   public  String ExtractChinese(){
	   String SubString = new String() ;
	   for(int i = 0 ;i<str.length(); i++){
		       if( isChinese(str.charAt(i))){
		    	   SubString = SubString +str.charAt(i);
		       }
	   }
	   return  SubString;
   }
   
}
