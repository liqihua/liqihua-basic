package com.liqihua.config.redisson;

import cn.hutool.core.util.StrUtil;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.FstCodec;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {
    /**
     * 集群模式
     */
    /*@Value("${redis.clusterConf}")
    private String confPath;*/

    /**
     * 单机模式
     */
    @Value("${redis.database}")
    private Integer database;
    @Value("${redis.host}")
    private String address;
    @Value("${redis.port}")
    private Integer port;
    @Value("${redis.password}")
    private String password;


    /**
     * 集群模式
     * @return
     */
    /*@Bean
    public RedissonClient redissonClient(){
        try {
            InputStream inputStream = new PathMatchingResourcePatternResolver().getResource(confPath).getInputStream();
            Config config = Config.fromJSON(inputStream);
            config.setCodec(new FstCodec());
            return Redisson.create(config);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }*/

    @Bean
    public RedissonClient redissonClient(){
        Config config = new Config();
        config.setCodec(new FstCodec());
        SingleServerConfig server =  config.useSingleServer();
        server.setDatabase(database);
        server.setAddress("redis://"+address+":"+port);
        if(StrUtil.isNotBlank(password)) {
            server.setPassword(password);
        }
        return Redisson.create(config);
    }







}
