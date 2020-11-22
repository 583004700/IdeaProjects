package com.demo.mydemo.test.mp3;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.junit.Test;

public class TestMp {

    /**
     * 代码生成 示例代码
     */
    @Test
    public void testGenerator() {
        //1.全局配置
        GlobalConfig config = new GlobalConfig();
        config.setActiveRecord(true) //是否支持AR模式
                .setAuthor("zhuwb")  //作者
                .setOutputDir("E:\\IdeaProjects\\sgg\\mybatisplus\\src\\main\\java")    //生成路径
                .setFileOverride(true)  //文件覆盖
                .setIdType(IdType.AUTO)     //主键策略
                .setServiceName("%sService")         //设置生成的service接口的名字的首字母是否为I
                .setBaseResultMap(true)
                .setBaseColumnList(true);

        //2.数据源配置
        DataSourceConfig dsConfig = new DataSourceConfig();
        dsConfig.setDbType(DbType.MYSQL)
                .setDriverName("com.mysql.jdbc.Driver")
                .setUrl("jdbc:mysql://localhost:3306/mp")
                .setUsername("root")
                .setPassword("root");
        //3.策略配置
        StrategyConfig stConfig = new StrategyConfig();
        stConfig.setCapitalMode(true)   //全局大写命名
                .setDbColumnUnderline(true)     //设置表名字段名是否使用下划线
                .setNaming(NamingStrategy.underline_to_camel)   //驼峰
                .setTablePrefix("tbl_")
                .setInclude("tbl_employee");
        //4.包名策略配置
        PackageConfig pkConfig = new PackageConfig();
        pkConfig.setParent("com.demo.mydemo.mp3")
                .setMapper("mapper")
                .setService("service")
                .setController("controller")
                .setEntity("beans")
                .setXml("mapper");
        //5.整合配置
        AutoGenerator ag = new AutoGenerator();
        ag.setGlobalConfig(config)
                .setDataSource(dsConfig)
                .setStrategy(stConfig)
                .setPackageInfo(pkConfig);
        //6.执行
        ag.execute();
    }

}
