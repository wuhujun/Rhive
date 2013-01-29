#!/usr/local/bin/Rscript
library(rJava)
library(Rserve)
library(RHive)
rhive.init();
rhive.connect("192.168.3.146");
tables<-rhive.list.tables();
Desc<-rhive.desc.table('gener') ;
mode<-rhive.basic.mode("gener","col1") ;
range<-rhive.basic.range("gener","col1");
rhive.hdfs.connect();
rhive.hdfs.ls();
du<-rhive.hdfs.du(); 
print("The du of the hdfs")
du
gener<-rhive.query("select * from ia");
querstr<-"create table records (id int , name string , value double )";
# Result<-rhive.query(querstr);
D<-rhive.query('show tables ')
rhive.hdfs.du();
#rhive.query("dfs -ls");
system("hive -f script.sql")
rhive.close()
#system("ls ");
system("hive -f script.sql");
#system("Rscript sys.r")
