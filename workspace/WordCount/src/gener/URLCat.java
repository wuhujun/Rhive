package gener;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import java.io.*;

public class URLCat {

        public static void main(String[] args) {
        if(args.length==1){
                  //  Auto-generated method stub
            Configuration conf = new Configuration();
            try {
                    FileSystem fs = FileSystem.get(conf);
                    Path f = new Path(args[0]);
                    FSDataInputStream is = fs.open(f);
                    String buffer = new String();
                   // the next byte of data, or -1 if the end of the stream is reached. 
                   while(is.read()!=-1){
                     buffer = is.readUTF();
                     System.out.println(buffer);
                    }
                    is.close();
            } catch (IOException e) {
            //  Auto-generated catch block
                    e.printStackTrace();
            }

        }else{
                System.err.println("Usage : URLcat <input url>");
        }

     }

}

