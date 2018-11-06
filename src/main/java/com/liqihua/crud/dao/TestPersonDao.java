package com.liqihua.crud.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.liqihua.crud.entity.TestPersonEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author liqihua
 * @since 2018-11-06
 */
public interface TestPersonDao extends BaseMapper<TestPersonEntity> {


    @Select("SELECT * FROM test_person")
    public List<TestPersonEntity> testPage(IPage<TestPersonEntity> page);

}
