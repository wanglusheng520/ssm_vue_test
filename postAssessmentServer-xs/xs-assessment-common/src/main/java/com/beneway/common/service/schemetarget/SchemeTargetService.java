package com.beneway.common.service.schemetarget;

import com.baomidou.mybatisplus.extension.service.IService;
import com.beneway.common.common.result.Result;
import com.beneway.common.entity.detailtarget.fo.DetailTargetFo;
import com.beneway.common.entity.scheme.Scheme;
import com.beneway.common.entity.scheme.fo.SchemeFo;
import com.beneway.common.entity.scheme.vo.SchemeVo;
import com.beneway.common.entity.schemetarget.SchemeTarget;
import com.beneway.common.entity.schemetarget.fo.SchemeTargetFo;
import com.beneway.common.entity.schemetarget.vo.SchemeTargetVo;

import java.util.List;

public interface SchemeTargetService extends IService<SchemeTarget> {

    /**
     * 制定方案时调用
     * @param detailTargetFoList
     * @param scheme
     */
    void saveList(List<DetailTargetFo> detailTargetFoList, Scheme scheme);

    /**
     * 获取方案详情时调用
     */
    void getAnswer(List<SchemeTargetVo> schemeTargetVoList,SchemeVo schemeVo);

}
