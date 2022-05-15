package com.beneway.common.service.putrule;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.beneway.common.common.result.Result;
import com.beneway.common.entity.putrule.PutRule;
import com.beneway.common.entity.putrule.fo.PutRuleFo;
import com.beneway.common.entity.putrule.fo.PutRuleQueryFo;
import com.beneway.common.entity.putrule.vo.PutRuleVo;

import java.util.Map;

public interface PutRuleService extends IService<PutRule> {
    Page<PutRuleVo> queryPage(PutRuleQueryFo putRuleQueryFo);


    Result add(PutRuleFo putRuleFo);

//    Result getOneDetail(PutRule putRule);
//
//    Result del(PutRule putRule);
//
//    Result edit(PutRule putRule);
//
//    Result isWork(PutRule putRule);
}
