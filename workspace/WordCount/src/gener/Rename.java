package gener;

//Rename.java
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class Rename{

  public static void main(String[] args) throws Exception {
	  
      Configuration conf=new Configuration();
      
      FileSystem hdfs=FileSystem.get(conf);
      
      Path frpaht=new Path("/user/root/testfile.txt");    //the old name of the file.
      
      Path topath=new Path("/user/root/New_testfile.txt");    //the new name of the file. 
      
      boolean isRename=hdfs.rename(frpaht, topath);
      
      String result=isRename ? "Succeed" : "Failed" ;
      
      System.out.println("The file is renamed as " + result);

  }
} 
