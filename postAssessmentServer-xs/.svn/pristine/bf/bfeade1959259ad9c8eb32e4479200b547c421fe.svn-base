package com.beneway.common.service.system.login;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.beneway.common.common.enums.UserloginLogTypeEnum;
import com.beneway.common.entity.system.Result;
import com.beneway.common.entity.system.login.Loginuser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

public interface LoginuserService extends IService<Loginuser> {

    /**
     * 检测账号名冲突
     * 查出除自己以外的，账号为这个值的用户，如果不为空，则账号重复
     */
    boolean checkAccount(Loginuser loginuser);

    Loginuser findById(String id);

    Loginuser getSafeById(String id);

    Result update(Loginuser loginuser);

    Result insert(Loginuser loginuser);

    Result delete(String id);

    Result resetPWD(String userId, String oldPWD, String newPWD, String checkPWD);

    /*
        新的方法
     */

    /**
     * 用户管理分页查询
     * @param params
     * @return
     */
    IPage<Loginuser> queryPage(Map<String, Object> params);

    IPage<Loginuser> queryPages(Map<String, Object> params);

    Result del(String id);
}
