package com.liqihua.common.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ReflectUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liqihua
 * @since 2018/11/15
 */
public class SysBeanUtil {

    /**
     * 拷贝整个list
     * 由于hutool只有单个bean的拷贝，没有整个List的拷贝，需要封装一个list的拷贝
     * @param source
     * @param cls
     * @param <T>
     * @return
     */
    public static<T> List<T> copyList(List<?> source, Class<T> cls) {
        if(source == null || source.size() == 0){
            return new ArrayList<>();
        }
        List<T> targetList = new ArrayList<>(source.size());
        for (Object obj : source) {
            T target = null;
            if(obj != null){
                target = ReflectUtil.newInstance(cls);
                BeanUtil.copyProperties(obj, target);
            }
            targetList.add(target);
        }
        return targetList;
    }
}
