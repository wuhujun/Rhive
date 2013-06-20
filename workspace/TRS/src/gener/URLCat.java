package gener;

import java.io.InputStream;
import java.net.URL;

import org.apache.hadoop.fs.FsUrlStreamHandlerFactory;
import org.apache.hadoop.io.IOUtils;

public class URLCat {

public static void main(String[] args) throws Exception {
 URL.setURLStreamHandlerFactory(new FsUrlStreamHandlerFactory());
 InputStream in = null;
 String url = "hdfs://Master.Hadoop:9000/user/root/input/sale_record20090101.bcp" ;
 System.out.println(args.length);
 if(args.length>=1){
	 url = args[0] ;
 }
 try {
   in = new URL(url).openStream();
   IOUtils.copyBytes(in, System.out, 4096, false);
 } finally {
   IOUtils.closeStream(in);
 }
}
}
