package gener;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Extract {
     /**
      * 提取始发站和终点站。
      */
   public List <String> ExtractStation(String record){
	     String regex = "[\u4e00-\u9fa5]+" ;
         List <String > station = new ArrayList<String>() ;
         Pattern p = Pattern.compile(regex);
         Matcher m = p.matcher(record);
         while(m.find()){
        	 station.add(m.group());
         }
         return station ;
   }
   /**
    * 提取日期
    */
   public List<String>  ExtractDate(String record){
	   String regex = "20[01]\\d{1}[01]\\d{1}[0123]\\d{1}";
	   List <String> date = new ArrayList<String>();
	   Pattern pattern = Pattern.compile(regex);  
	   Matcher matcher = pattern.matcher(record);   
	   while (matcher.find()) {  
		   date.add(matcher.group());  
		} 
	   return date  ;
   }
   /**
    * 提取发车时间。
    */
   public String ExtractStartTime(String record){
	  String regex = "\\w*{3} \\d{2} \\d{4}  \\d{1}:\\d{2}:\\d{2}:\\d{3}\\w*";  
	  String startTime =new String();
	  Pattern patern  = Pattern.compile(regex);
	  Matcher matcher = patern.matcher(record);
	  if(matcher.find()){
		  startTime = matcher.group() ;
	  }
	  return startTime ;
   }
   
   /**
    * 提取电报码
    */
   public List<String> ExtractTeleCode(String record){
	   List<String> teleCode = new ArrayList<String>() ;
	   String regex = " [A-Z][A-Z][A-Z]  ";  
	   Pattern patern  = Pattern.compile(regex);
	   Matcher matcher = patern.matcher(record);
	   while(matcher.find()){
			  teleCode.add(matcher.group()) ;
		  }
	   return teleCode ;
   }
   
}
