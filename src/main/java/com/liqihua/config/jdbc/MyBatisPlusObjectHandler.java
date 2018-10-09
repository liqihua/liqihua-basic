package com.liqihua.config.jdbc;

import com.baomidou.mybatisplus.mapper.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Date;

/**
 * 在插入和更新数据时设置相应时间值
 * @author liqihua
 * @since 2018/4/24
 */
public class MyBatisPlusObjectHandler extends MetaObjectHandler{

    @Override
    public void insertFill(MetaObject metaObject) {
        Date date = new Date();
        this.setFieldValByName("createDate", date, metaObject);
        this.setFieldValByName("modDate", date, metaObject);
        this.setFieldValByName("delUser", "0", metaObject);
        this.setFieldValByName("delKefu", "0", metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("modDate", new Date(), metaObject);
    }
}
