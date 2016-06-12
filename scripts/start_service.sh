#! /bin/bash

base_dir=$(dirname $0)
cd $base_dir/../luna-service-impl;
mvn clean package -DskipTests -Pdev;
bin/start.sh
