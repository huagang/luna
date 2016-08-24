#!/bin/bash


base_dir=$(dirname $0)
front_dir=$base_dir/../luna-web/src/main/webapp
jsp_file="$front_dir/$1.jsp"
css_file="$front_dir/styles/$1.css"
js_file="$front_dir/scripts/$1.js"
if [ ! -f "$jsp_file" ];then
    echo "$jsp_file"
    touch "$jsp_file"
fi
if [ ! -f "$css_file" ];then
    echo "$css_file"
    touch "$css_file"
fi
if [ ! -f "$js_file" ];then
    echo "$js_file"
    touch "$js_file"
fi
echo "创建成功"

