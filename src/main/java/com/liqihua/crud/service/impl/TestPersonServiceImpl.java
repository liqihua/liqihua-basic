package com.liqihua.crud.service.impl;

import com.liqihua.crud.entity.TestPersonEntity;
import com.liqihua.crud.dao.TestPersonDao;
import com.liqihua.crud.service.TestPersonService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liqihua
 * @since 2018-10-11
 */
@Service
public class TestPersonServiceImpl extends ServiceImpl<TestPersonDao, TestPersonEntity> implements TestPersonService {

}
