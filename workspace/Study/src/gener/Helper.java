package gener;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern; 
import java.util.regex.Matcher; 
/**
 * 正则表达式在Java中的应用。
 * @author ia
 *
 */
public class Helper {
   public static void main(String args[]){
	   String a = "3227001 35      4049    1       3    " +
	   		"   Dec 30 2008  4:51:00:000AM      F078865         15      1       1 " +
	   		"      9       1       470000T17710    T177            20081230        0504    2   " +
	   		"    3       13      3032    1               上海南          SNH     H       55            " +
	   		"                  杭州            HZH     5751    H       56      CSA                       " +
	   		"      173     290     0       0       0       0       0       0 " +
	   		"      0       0       0       0       0       0       0       0    " +
	   		"   0       0       0       10      0       0       0                      " +
	   		" H       H1      0       0       0       29016   K00     0                  " +
	   		"                                                     " +
	   		"                0       0                       173           " +
	   		"          4       20081230                        280     A                  " +
	   		"                                     290     0       0.000   0       SNH   " +
	   		"          20090101                        00      H1 ";
	  // 匹配日期
	   String b  =  a.replaceAll("\\d{4}\\d{2}\\d{2}", " 日期 " ) ;
	   String c = a.replaceAll("[0-9&&[345]]", " ");
	   String d = a.replaceAll("[A-Z[a-z[0-9[-]]]]", "  ");
	   String e = b.replaceAll("[A-Za-z0-9-:.]" , "  ");
              e = e.replaceAll(" ++", ":");
//       String [] Raw = e.split(":");
//       for(String iter : Raw){
//    	   System.out.println(iter);
//       }
//	   System.out.println(e.trim());
	   /**
	    * str : 待匹配的字符串。
	    * regex : 正则表达式字符串。
	    */
	   String str = a;  
	   String regex = " [A-Z][A-Z][A-Z]  ";  
	   //Compiles the given regular expression into a pattern
	   Pattern pattern = Pattern.compile(regex);  
	   // Creates a matcher that will match the given input against this pattern.
	   Matcher matcher = pattern.matcher(str);   
//	   while (matcher.find()) {  
//		    System.out.println(matcher.group());  
//		}
	   String  fromTeleCode = ExtractTeleCode(str);
	   System.out.println(fromTeleCode) ;
	   
   }
   public static  String  ExtractTeleCode(String record){
	   String [] teleCode = new String [4] ;
	   String regex = " [A-Z][A-Z][A-Z]  ";  
	   Pattern patern  = Pattern.compile(regex);
	   Matcher matcher = patern.matcher(record);
	   int i = 0 ;
	   while(matcher.find()){
			  teleCode[i] = matcher.group() ;
			  i++ ;
		  }
	   for( i =0 ; i!=teleCode.length ; i++){
		   System.out.print(teleCode[i]);
	   }
	   return teleCode[0] ;
   }
   
   public String []  ExtractFromStation( String reacord  ){
       String [] Station = new String [2];     
       String tmp = reacord.replaceAll("[A-Za-z0-9-:]" , "  ");
	   Station   =tmp.split("   ");
	   return Station ;
   } 
   //判断字符是否为中文字符。
   public static boolean isChinese(char a) {
	        int v = (int)a;
	        return (v >=19968 && v <= 171941);
	   }
   public static String ExtractChinese(String str){
	   String SubString = new String() ;
	   for(int i = 0 ;i<str.length(); i++){
		       if( isChinese(str.charAt(i))){
		    	   SubString = SubString +str.charAt(i);
		       }
	   }
	   return  SubString;
   }

   public static enum Counters {ROWS} 
   
   public static void Cat(){
	   for(int i =0 ;i!=100;i++){
		   System.out.println(i*i);
	   }
   }
   
}
class RegexUtil{
	
   public String RegDate( String str){
	   String substr = new String();
	   
	   return substr ;
   }
}
