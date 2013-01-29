#!/usr/local/bin/Rscript
library(rJava)
library(Rserve)
library(RHive)
rhive.init();
rhive.connect();
d <- rhive.query('select * from ia')
#rhive.query('load data inpath \'input/data.txt\' overwrite into table gener ')
dataframe <-d;
tableinformation<-rhive.query('show  tables ');
tableinformation
class(dataframe);
summary(dataframe); 
gener<-rhive.query('select * from gener');
colnames(gener)
class(gener)
str(gener)
#summary(gener)
t1<-as.numeric(as.character(gener[,1]))
gener<-gener[,-1];
gener[,1]<-as.numeric(as.character(gener[,1]))
gener[,2]<-as.numeric(as.character(gener[,2]))
gener[,3]<-as.numeric(as.character(gener[,3]))
gener
model<-lm(col2~col3,data=gener); 
summary(model);
model
#gener

rhive.close()
