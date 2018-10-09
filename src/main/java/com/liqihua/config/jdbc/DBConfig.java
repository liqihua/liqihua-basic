package com.liqihua.config.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Configuration
@MapperScan(basePackages = {"com.liqihua.**.mapper*"}, sqlSessionFactoryRef = "sqlSessionFactoryManager")
public class DBConfig {
    @Autowired
    Environment environment;
    /**
     * mapper的xml位置
     */
    public static final String[] DATASOURCE_MAPPER_LOACTIONS = {"classpath*:mapper/*Mapper.xml"};


    @Value("${jdbc.manage.url}")
    private String url;
    @Value("${jdbc.manage.username}")
    private String username;
    @Value("${jdbc.manage.password}")
    private String password;
    @Value("${jdbc.manage.driverClassName}")
    private String driverClass;


    @Primary
    @Bean(name = "dataSourceManager")
    public DataSource dataSourceManager() {
        String shardingjdbcFile = environment.getProperty("jdbc.manage.shardingjdbc.file");
        if(Tool.isNotBlank(shardingjdbcFile)){
            //shardingjdbc方式
            try {
                InputStream is = new PathMatchingResourcePatternResolver().getResource(shardingjdbcFile).getInputStream();
                DataSource dataSource = MasterSlaveDataSourceFactory.createDataSource(Tool.InputStream2byte(is));
                is.close();
                return dataSource;
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            //单库方式
            DruidDataSource dataSource = new DruidDataSource();
            dataSource.setDriverClassName(driverClass);
            dataSource.setUrl(url);
            dataSource.setUsername(username);
            dataSource.setPassword(password);
            return dataSource;
        }
        return null;
    }


    @Primary
    @Bean(name = "transactionManagerManager")
    public DataSourceTransactionManager transactionManagerManager() {
        return new DataSourceTransactionManager(dataSourceManager());
    }


    @Primary
    @Bean(name = "sqlSessionFactoryManager")
    public SqlSessionFactory sqlSessionFactoryManager(@Qualifier("dataSourceManager") DataSource dataSourceManager) throws Exception {
        MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
        bean.setDataSource(dataSourceManager);
        //设置mapper的xml路径
        Resource[] resources = getMapperResource();
        if(resources != null){
            bean.setMapperLocations(resources);
        }
        //分页插件
        PaginationInterceptor pageInterceptor = new PaginationInterceptor();
        pageInterceptor.setLocalPage(true);
        pageInterceptor.setDialectType("mysql");
        Interceptor[] interceptors = {pageInterceptor};
        bean.setPlugins(interceptors);
        //插入数据字段预处理
        GlobalConfiguration config = new GlobalConfiguration();
        config.setMetaObjectHandler(new MyBatisPlusObjectHandler());
        bean.setGlobalConfig(config);
        
        //支持驼峰模式
        bean.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);
        return bean.getObject();
    }

    /**
     * mapper的xml路径处理
     * 把String[]转Resource[]
     * @return
     */
    protected Resource[] getMapperResource(){
        try {
            if(DATASOURCE_MAPPER_LOACTIONS != null && DATASOURCE_MAPPER_LOACTIONS.length > 0) {
                List<Resource> resourceList = new ArrayList<>();
                for (String location : DATASOURCE_MAPPER_LOACTIONS) {
                    Resource[] resourceArr = new Resource[0];

                    resourceArr = new PathMatchingResourcePatternResolver().getResources(location);

                    for (Resource resource : resourceArr) {
                        resourceList.add(resource);
                    }
                }
                Resource[] resources = new Resource[resourceList.size()];
                for (int i = 0; i < resourceList.size(); i++) {
                    resources[i] = resourceList.get(i);
                }
                return resources;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
