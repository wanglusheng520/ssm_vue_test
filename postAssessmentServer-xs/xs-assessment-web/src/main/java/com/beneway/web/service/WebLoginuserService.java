package com.beneway.web.service;

import com.beneway.common.common.enums.UserloginLogTypeEnum;
import com.beneway.common.entity.system.login.Loginuser;

/**
 * @email 2434017367@qq.com
 * @author: zhy
 * @date: 2021/12/14
 * @time: 15:51
 */
public interface WebLoginuserService {

    /**
     * 用户登录
     * @param loginuser
     * @param typeEnum
     * @return
     */
    String login(Loginuser loginuser, UserloginLogTypeEnum typeEnum);

}
