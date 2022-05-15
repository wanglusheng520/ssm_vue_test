package com.beneway.web.service;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.beneway.common.common.enums.UserloginLogTypeEnum;
import com.beneway.web.common.utils.IPUtil;
import com.beneway.common.common.utils.exception.RRException;
import com.beneway.common.common.utils.jwt.LoginUserUtils;
import com.beneway.common.entity.system.agency.Agency;
import com.beneway.common.entity.system.login.Loginuser;
import com.beneway.common.entity.userlogin.UserloginLog;
import com.beneway.common.service.system.agency.AgencyService;
import com.beneway.common.service.userlogin.UserloginLogService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.function.Consumer;

/**
 * @email 2434017367@qq.com
 * @author: zhy
 * @date: 2021/12/14
 * @time: 15:52
 */
@Log4j2
@Service
public class WebLoginuserServiceImpl implements WebLoginuserService {

    @Autowired
    private AgencyService agencyService;

    @Autowired
    private UserloginLogService userloginLogService;

    /**
     * 用户登录
     * @param loginuser
     * @param typeEnum
     * @return
     */
    @Override
    public String login(Loginuser loginuser, UserloginLogTypeEnum typeEnum) {
        String userId = loginuser.getId();

        // 判断用户是否为超级管理员,是不用校验
        if (!LoginUserUtils.isAdmin()) {
            Boolean del = loginuser.getDel();
            String errMsg;
            Consumer<String> printErrLog = eMsg -> {
                log.error("userId:" + userId + " username:" + loginuser.getUsername() + " errorMsg:" + eMsg);
            };
            if (del){
                errMsg = "用户不存在，请联系管理员";
                printErrLog.accept(errMsg);
                throw new RRException(errMsg);
            }
            // 判断用户单位是否存在
            Integer supAgencyId = loginuser.getSupAgencyId();
            Integer agencyId = loginuser.getAgencyId();
            LambdaQueryWrapper<Agency> wrapper = new LambdaQueryWrapper<>();
            agencyService.packLqw(wrapper);
            wrapper.in(Agency::getId, supAgencyId, agencyId);
            int count = agencyService.count(wrapper);
            if (count < 2){
                errMsg = "用户所在单位不存在，请联系管理员";
                printErrLog.accept(errMsg);
                throw new RRException(errMsg);
            }

            // 保存登录日志
            String ipAddr = IPUtil.getIpAddr();
            UserloginLog userloginLog = new UserloginLog();
            userloginLog.setUserId(userId);
            userloginLog.setLoginTime(new Date());
            userloginLog.setIpAddress(ipAddr);
            userloginLog.setType(typeEnum.getType());
            userloginLogService.save(userloginLog);
        }

        // 生成token
        StpUtil.login(userId, typeEnum.getDevice());
        String tokenValue = StpUtil.getTokenValue();

        return tokenValue;
    }

}
