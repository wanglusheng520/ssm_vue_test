package com.beneway.common.service.userlogin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.beneway.common.common.utils.SpringContextHolder;
import com.beneway.common.common.utils.base.PageUtils;
import com.beneway.common.dao.userlogin.UserloginLogDao;
import com.beneway.common.entity.system.Result;
import com.beneway.common.entity.userlogin.UserloginLog;
import com.beneway.common.service.system.login.LoginuserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;


@Service("userloginLogService")
public class UserloginLogServiceImpl extends ServiceImpl<UserloginLogDao, UserloginLog> implements UserloginLogService {

    @Autowired
    private UserloginLogDao userloginLogDao;

    @Override
    public IPage<UserloginLog> queryPage(Map<String, Object> params) {
        Page page = PageUtils.getPage(params);
        IPage<UserloginLog> iPage = userloginLogDao.queryPage(page, params);
        return iPage;
    }

}
