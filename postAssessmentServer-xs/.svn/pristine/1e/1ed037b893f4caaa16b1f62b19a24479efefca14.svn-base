package com.beneway.common.common.mybatis;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @email 2434017367@qq.com
 * @author: zhy
 * @date: 2021/9/13
 * @time: 16:28
 */
public class MyServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> {

    /**
     * 判断是否存在数据
     * @param queryWrapper
     * @return
     */
    public boolean isExist(Wrapper<T> queryWrapper){
        int count = this.count(queryWrapper);
        return count > 0;
    }

}
