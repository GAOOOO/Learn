package com.bs.demo.generator;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.querys.MySqlQuery;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.keywords.MySqlKeyWordsHandler;

import java.util.Collections;

public class GeneratorCodeConfig {
    public static void main(String[] args) {
        //数据源配置
        DataSourceConfig dataSourceConfig = new DataSourceConfig
                .Builder("jdbc:mysql://127.0.0.1:3306/learn", "root", "gaofeng19981019")
                .dbQuery(new MySqlQuery())
                .schema("mybatis-plus")
                .typeConvert(new MySqlTypeConvert())
                .keyWordsHandler(new MySqlKeyWordsHandler())
                .build();

        AutoGenerator mpg = new AutoGenerator(dataSourceConfig);

        //策略配置
        StrategyConfig strategyConfig = new StrategyConfig.Builder()
                //表名
                .addInclude("teacher")
                .addTablePrefix()
                .build();
        mpg.strategy(strategyConfig);

        //全局配置
        GlobalConfig globalConfig = new GlobalConfig.Builder()
                .disableOpenDir()
                .outputDir(System.getProperty("user.dir") + "/src/main/java")
                .author("gf")
                .enableSwagger()
                .dateType(DateType.TIME_PACK)
                .commentDate("yyyy-MM-dd")
                .build();
        mpg.global(globalConfig);

        //包配置
        PackageConfig packageConfig = new PackageConfig.Builder()
                .parent("com.bs")
                .moduleName("demo")
                .controller("controller")
                .entity("entity")
                .service("service")
                .serviceImpl("service.impl")
                .mapper("mapper")
                .xml("mapper.xml")
                .pathInfo(Collections.singletonMap(OutputFile.mapperXml, System.getProperty("user.dir") + "/src/main/resources/mapper"))
                .build();
        mpg.packageInfo(packageConfig);

        //模板配置
        TemplateConfig templateConfig = new TemplateConfig.Builder()
                .entity("/templates/entity.java")
                .service("/templates/service.java")
                .serviceImpl("/templates/serviceImpl.java")
                .mapper("/templates/mapper.java")
                .mapperXml("/templates/mapper.xml")
                .controller("/templates/controller.java")
                .build();
        mpg.template(templateConfig);


        mpg.execute();
    }

}
