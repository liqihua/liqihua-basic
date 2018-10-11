package com.liqihua.common.code;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.junit.Test;

/**
 * @author liqihua
 * @since 2018/10/10
 */
public class MakeCode {


    /**
     * 代码生成
     */
    @Test
    public void make(){
        String url = "jdbc:mysql://127.0.0.1:3306/java?characterEncoding=utf8&serverTimezone=Hongkong";
        String username = "root";
        String password = "123";
        String dir = "H://";//代码生成在哪个位置，一般是项目工作目录以外的位置，以防错误覆盖


        String author = "liqihua";//作者
        String parent = "com.liqihua";//父级路径
        String moduleName = "crud";//在哪个包下生成，代码最后会生成在 parent.moduleName 下，如：com.liqihua.project
        String tablePrefix = "";//表前缀，生成的java类名会去掉前缀
        String tableName = "test_person";//表名，全称


        AutoGenerator mpg = new AutoGenerator();
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(dir);//代码生成在哪个位置，一般是项目工作目录以外的位置，以防错误覆盖
        gc.setFileOverride(true);
        gc.setActiveRecord(true);// 不需要ActiveRecord特性的请改为false
        gc.setEnableCache(false);// XML 二级缓存
        gc.setBaseResultMap(true);// XML ResultMap
        gc.setBaseColumnList(false);// XML columList
        gc.setAuthor(author);
        /**
         * 设置各个层的类名
         */
        gc.setServiceName("%sService");
        gc.setControllerName("%sSysController");
        gc.setEntityName("%sEntity");
        gc.setMapperName("%sDao");
        gc.setXmlName("%sDao");

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername(username);
        dsc.setPassword(password);
        dsc.setUrl(url);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setTablePrefix(new String[] { tablePrefix });// 表前缀
        strategy.setNaming(NamingStrategy.underline_to_camel);// 表名生成策略
        strategy.setInclude(new String[] { tableName });//生成哪个表的代码

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent(parent);//父级路径
        pc.setModuleName(moduleName);////在哪个包下生成
        pc.setMapper("dao");
        pc.setXml("dao.mapper");
        pc.setController("controller.sys");

        mpg.setGlobalConfig(gc);// 全局配置
        mpg.setDataSource(dsc);// 数据源配置
        mpg.setStrategy(strategy);// 策略配置
        mpg.setPackageInfo(pc);// 包配置

        // 执行生成
        mpg.execute();
    }

}
