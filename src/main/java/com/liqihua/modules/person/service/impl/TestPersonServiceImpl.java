package com.liqihua.modules.person.service.impl;

import com.liqihua.modules.person.entity.TestPersonEntity;
import com.liqihua.modules.person.dao.TestPersonDao;
import com.liqihua.modules.person.service.TestPersonService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 测试人员 服务实现类
 * </p>
 *
 * @author liqihua
 * @since 2018-12-26
 */
@Service
public class TestPersonServiceImpl extends ServiceImpl<TestPersonDao, TestPersonEntity> implements TestPersonService {

}
