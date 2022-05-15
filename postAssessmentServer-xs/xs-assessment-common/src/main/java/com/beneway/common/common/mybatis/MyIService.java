package com.beneway.common.common.mybatis;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @email 2434017367@qq.com
 * @author: zhy
 * @date: 2021/9/13
 * @time: 16:15
 */
public interface MyIService<T> extends IService<T> {

    default boolean isExist(Wrapper<T> queryWrapper){
        int count = this.count(queryWrapper);
        return count > 0;
    }

}
