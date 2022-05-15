package com.beneway.common.service.syslog;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.beneway.common.dao.syslog.SysLogDao;
import com.beneway.common.entity.syslog.SysLog;
import org.springframework.stereotype.Service;

/**
 * @Author zxc
 */
@Service("SysLogService")
public class SysLogServiceImp extends ServiceImpl<SysLogDao, SysLog> implements SysLogService{

}
