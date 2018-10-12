package com.liqihua.crud.dao;

import com.liqihua.crud.entity.TestPersonEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author liqihua
 * @since 2018-10-11
 */
public interface TestPersonDao extends BaseMapper<TestPersonEntity> {

    public List<TestPersonEntity> test1();

}
