package com.beneway.common.service.userlogin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.beneway.common.entity.system.Result;
import com.beneway.common.entity.userlogin.UserloginLog;

import java.util.Map;

/**
 *
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2021-06-05 17:32:55
 */
public interface UserloginLogService extends IService<UserloginLog> {

        IPage<UserloginLog> queryPage(Map<String, Object> params);

}

