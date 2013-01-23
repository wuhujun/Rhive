#!/bin/sh
while [ "$#" -gt 0 ];
do
	:echo $1 
	echo $#
	shift 
done
exit 1;
