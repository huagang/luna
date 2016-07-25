1.工程目录介绍(生成的持久层内容版本是：mybatis2.x)

    [mybatis-generator-base]为自定义插件部分，基本不需要修改。
    [mybatis-generator]为MBG生成工程
  
2.使用时，分为两个过程：

    2.1 初始配置
    
        2.1.1 [mybatis-generator-base]
        
            有预先定义好的DAO超类，如果需要更改，请自行更改包的位置
            // FIXME: 1/4 自定义包的位置
            super("com.visualbusiness.biz.common.MsBaseDAO");
        
        
        2.1.2 [mybatis-generator]
            2.1.2.1 修改DB连接信息等（pom.xml）
                <!-- FIXME: 2/4  START-->
        		<driverClass>com.mysql.jdbc.Driver</driverClass>
        		<connectionURL>jdbc:mysql://115.159.67.120:3306/vr3d</connectionURL>
        		<username>vbdev</username>
        		<password>vb2015</password>
        		<package.base>com.visualbusiness.luna.biz</package.base>
        		<!-- FIXME: 2/4  END-->
            2.1.2.2 如果需要生成乐观锁支持方法，需要打开以下注释内容（如果不需要的话，则不需要动）（generatorConfig.xml）
                <!-- FIXME: 3/4 需要乐观锁的时候放开，并且需要自行定义乐观锁异常(默认不需要) -->
        		<!-- <plugin
        			type="com.visualbusiness.mybatis.gen.plugin.OptimisticLockMethodPlugin">
        			<property name="deleteFlagColumnName" value="del_flg" />
        			<property name="offValue" value="0" />
        			<property name="updateDatetimeColumnName" value="update_time" />
        		</plugin> -->
        	
    
    2.2 定义需要生成的表，生成DAO，MODEL和SQLMAP部分（generatorConfig.xml）
        <!-- FIXME: 4/4 生成对应表-->
		<table tableName="vr3d_mst_module"></table>

备注：生成命令为 [clean mybatis-generator:generate]

    
    