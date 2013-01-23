#!/bin/sh
while [ $# -gt 0 ]; do
  echo $#
  case "$1" in
      A)
      shift
      SERVICE=$1
      echo $1
      shift
      ;;
      B)
      SERVICE=rcfilecat
      echo $1 
      shift
      ;;
      C)
      HELP=_help 
      echo $1
      shift
      ;;
      D)
      DEBUG=$1
      echo $1
      shift
      ;;
    *)
      break
      ;;
  esac
   echo $#
done

