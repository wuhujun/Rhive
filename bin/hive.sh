#!/bin/sh 
# $# 脚本参数的个数
echo $#
# $0 脚本名称本身。 
echo $0
# $n 第n个参数
echo $1
shift
echo $1
shift 
echo $1
shift
echo $1
#. 在当前shell中执行指定文件中的脚本
. ./help.sh
echo "$1  this is \$1" 
echo $2
while [ $# -gt 0 ]; do
  case "$1" in
    --service)
      shift
      SERVICE=$1
      shift
      ;;
    --rcfilecat)
      SERVICE=rcfilecat
      shift
      ;;
    --help)
      HELP=_help
      shift
      ;;
    --debug*)
      DEBUG=$1
      shift
      ;;
    *)
      break
      ;;
  esac
done
echo $SERVICE 
echo $HELP
echo $DEBUG
