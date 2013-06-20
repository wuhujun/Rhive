package gener ;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
 

public class CheckFiles {

    public static void main(String[] args) throws Exception {
    	if(args.length==1){
        Configuration conf=new Configuration();

        FileSystem hdfs=FileSystem.get(conf);

        Path findf=new Path(args[0]);

        boolean isExists=hdfs.exists(findf);

        System.out.println("Exist? "+isExists);
        
        hdfs.close();
    	}else{
    		 System.err.println("Usage : CheckFiles  <input url> ");
    	}
    }

} 
