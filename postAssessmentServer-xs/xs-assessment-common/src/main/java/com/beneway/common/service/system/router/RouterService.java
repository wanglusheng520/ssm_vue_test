package com.beneway.common.service.system.router;


import com.baomidou.mybatisplus.extension.service.IService;
import com.beneway.common.entity.system.router.Router;
import com.beneway.common.entity.system.Result;

/**
 * @author wangJun
 * @createTime 2021-09-06
 */
public interface RouterService extends IService<Router> {
    Result router(Integer id);

    Result allRouter();

    Result add(Router router);

    Result delete(Integer id);

    Result getOne(Router router);

    Result update(Router router);

}
