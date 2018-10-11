package com.liqihua.config.db;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;

/**
 * 在插入和更新数据时设置相应时间值
 * @author liqihua
 * @since 2018/4/24
 */
public class MyBatisPlusObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        LocalDateTime now = LocalDateTime.now();
        this.setFieldValByName("createDate", now, metaObject);
        this.setFieldValByName("updateDate", now, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateDate", LocalDateTime.now(), metaObject);
    }

}
