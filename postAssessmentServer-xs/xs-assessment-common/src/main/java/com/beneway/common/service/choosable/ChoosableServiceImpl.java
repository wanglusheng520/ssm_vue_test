package com.beneway.common.service.choosable;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.beneway.common.dao.choosable.ChoosableDao;
import com.beneway.common.entity.choosable.Choosable;
import org.springframework.stereotype.Service;

@Service
public class ChoosableServiceImpl extends ServiceImpl<ChoosableDao, Choosable> implements ChoosableService {
}
