package com.liqihua.crud.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liqihua.crud.entity.TestPersonEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liqihua
 * @since 2018-10-11
 */
public interface TestPersonService extends IService<TestPersonEntity> {


    public List<TestPersonEntity> test1();


}
