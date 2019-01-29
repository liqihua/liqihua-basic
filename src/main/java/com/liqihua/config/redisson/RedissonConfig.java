package com.liqihua.config.redisson;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.FstCodec;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.io.InputStream;

@Configuration
public class RedissonConfig {
    @Value("${redisson.conf.location}")
    private String confPath;

    @Bean
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
    }



}
