package generator;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.querys.MySqlQuery;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liqihua
 * @since 2018/10/10
 */
public class CodeGenerator {


    /**
     * 代码生成
     */
    @Test
    public void make(){
        String url = "jdbc:mysql://127.0.0.1:3306/liqihua_admin?characterEncoding=utf8&serverTimezone=Hongkong";
        String username = "root";
        String password = "123";
        String dir = "H://";//代码生成在哪个位置，一般是项目工作目录以外的位置，以防错误覆盖


        String author = "liqihua";//作者
        String parent = "com.liqihua";//父级路径
        String moduleName = "sys";//在哪个包下生成，代码最后会生成在 parent.moduleName 下，如：com.liqihua.project
        String tablePrefix = "";//表前缀，生成的java类名会去掉前缀
        String[] tables = new String[] { "sys_menu" };//生成哪个表


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
        gc.setAuthor(author);//设置作者

        /**
         * 设置各个层的类名
         */
        gc.setServiceName("%sService");
        gc.setControllerName("%sApiController");
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
        IDbQuery dbQuery = new MySqlQuery();
        dbQuery.fieldCustom();
        dsc.setDbQuery(dbQuery);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        /**
         * 自动填充字段
         */
        List<TableFill> tableFillList = new ArrayList<>();
        tableFillList.add(new TableFill("create_date",FieldFill.INSERT));
        tableFillList.add(new TableFill("update_date",FieldFill.INSERT_UPDATE));
        strategy.setTableFillList(tableFillList);
        strategy.setRestControllerStyle(true);//用@RestController代替@Controller
        strategy.setSuperControllerClass("com.liqihua.common.basic.BaseController");//设置controller的父类
        strategy.setTablePrefix(new String[] { tablePrefix });// 表前缀
        strategy.setNaming(NamingStrategy.underline_to_camel);// 表名生成策略
        strategy.setInclude(tables);//生成哪个表的代码

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent(parent);//父级路径
        pc.setModuleName(moduleName);////在哪个包下生成
        pc.setMapper("dao");
        pc.setXml("dao.mapper");
        pc.setController("controller.api");

        mpg.setGlobalConfig(gc);// 全局配置
        mpg.setDataSource(dsc);// 数据源配置
        mpg.setStrategy(strategy);// 策略配置
        mpg.setPackageInfo(pc);// 包配置





        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                /*Map<String, Object> map = new HashMap<>();
                map.put("aaa","3333");
                setMap(map);*/
            }
        };

        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig("/templates/vo.java.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                String fileName = dir  + pc.getParent().replace(".","/") +/* pc.getModuleName() +*/ "/" + pc.getEntity() + "/vo/" + tableInfo.getEntityName().replace("Entity","VO") + StringPool.DOT_JAVA;
                return fileName;
            }
        });

        cfg.setFileOutConfigList(focList);
        //添加自定义生成配置
        mpg.setCfg(cfg);
        // 执行生成
        mpg.execute();



    }

}
