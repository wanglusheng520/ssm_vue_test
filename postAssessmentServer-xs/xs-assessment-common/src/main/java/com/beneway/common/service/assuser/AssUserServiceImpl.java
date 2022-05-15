package com.beneway.common.service.assuser;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.beneway.common.dao.assuser.AssUserDao;
import com.beneway.common.entity.assuser.AssUser;
import org.springframework.stereotype.Service;

@Service
public class AssUserServiceImpl extends ServiceImpl<AssUserDao , AssUser> implements AssUserService {
}
