#! /bin/bash
# add new table in mybatis-generator/src/main/resources/generatorConfig.xml
# run mvn clean mybatis-generator:generate
base_dir=$(dirname $0)
cd $base_dir/../
generator_dir=mybatis-generater/mybatis-generator
generator_build_dir=$generator_dir/build
base_dao_dir=$generator_build_dir/gen
custom_dao_dir=$generator_build_dir/main
rm -rf $generator_build_dir
cd $generator_dir
mvn clean mybatis-generator:generate
cd ../../

# copy to luna-service-generator
cp -r $base_dao_dir/ms/luna/biz/dao/*.java luna-service-impl/src/main/java/ms/luna/biz/dao/
cp -r $base_dao_dir/ms/luna/biz/dao/model/*.java luna-service-impl/src/main/java/ms/luna/biz/dao/model/

# copy to luna-service-implement
cp -r $base_dao_dir/ms/luna/biz/dao/sqlmap/*.xml luna-service-impl/src/main/resources/ms/luna/biz/dao/sqlmap/
cp -r $custom_dao_dir/ms/luna/biz/dao/custom/sqlmap/*.xml luna-service-impl/src/main/resources/ms/luna/biz/dao/custom/sqlmap/ 
cp -r $custom_dao_dir/ms/luna/biz/dao/custom/*.java luna-service-impl/src/main/java/ms/luna/biz/dao/custom/

# copy sqlMapConfig manually
echo "WARN: copy the following to luna-service-impl/src/main/resources/SqlMapConfig.xml"
echo -e "\n"
cat $base_dao_dir/sqlmap/SqlMapConfig.xml
echo -e "\n"
