#! /bin/bash

#mvn install:install-file  -Dfile=/Users/chenshangan/svn/workspace/luna-service-impl/lib/QRCode.jar -DgroupId=ms.luna  -DartifactId=qrcode -Dversion=1.0 -Dpackaging=jar
#mvn install:install-file  -Dfile=/Users/chenshangan/svn/workspace/luna-service-impl/lib/cosapi.jar -DgroupId=ms.luna  -DartifactId=cosapi -Dversion=1.0 -Dpackaging=jar
#mvn install:install-file  -Dfile=/Users/chenshangan/svn/workspace/luna-service-impl/lib/redisnum-interface.jar -DgroupId=ms.luna  -DartifactId=redisnum-interface -Dversion=1.0 -Dpackaging=jar
#mvn install:install-file  -Dfile=/Users/chenshangan/svn/workspace/luna-service-impl/lib/com.util.mailsender.jar -DgroupId=ms.luna  -DartifactId=com.util.mailsender -Dversion=1.0 -Dpackaging=jar
#mvn deploy:deploy-file  -Dfile=/Users/chenshangan/svn/workspace/luna-service-impl/lib/QRCode.jar -DgroupId=ms.luna  -DartifactId=qrcode -Dversion=1.0 -Dpackaging=jar -DrepositoryId=local-nexus-thirdparty -Durl=http://luna-test.visualbusiness.cn/nexus/content/repositories/thirdparty

#mvn deploy:deploy-file  -Dfile=/Users/chenshangan/svn/workspace/luna-service-impl/lib/cosapi.jar -DgroupId=ms.luna  -DartifactId=cosapi -Dversion=1.0 -Dpackaging=jar -DrepositoryId=local-nexus-release -Durl=http://luna-test.visualbusiness.cn/nexus/content/repositories/releases

#mvn deploy:deploy-file  -Dfile=/Users/chenshangan/svn/workspace/luna-service-impl/lib/redisnum-interface.jar -DgroupId=ms.luna  -DartifactId=redisnum-interface -Dversion=1.0 -Dpackaging=jar -DrepositoryId=local-nexus-release -Durl=http://luna-test.visualbusiness.cn/nexus/content/repositories/releases

base_dir=$(dirname $0)
cd $base_dir/../
git pull
cd luna-service-common
mvn clean install -Pdev -DskipTests

cd ..
cd luna-service-generator
mvn clean install

cd ..
cd luna-service-interface
mvn clean install

cd ..
cd luna-web
mvn clean package -Pdev -DskipTests
