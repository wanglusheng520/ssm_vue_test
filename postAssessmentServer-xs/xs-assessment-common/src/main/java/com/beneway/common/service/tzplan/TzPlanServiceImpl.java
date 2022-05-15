package com.beneway.common.service.tzplan;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.beneway.common.dao.tzplan.TzPlanDao;
import com.beneway.common.entity.tzplan.TzPlan;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public class TzPlanServiceImpl extends ServiceImpl<TzPlanDao , TzPlan> implements TzPlanService {
}
