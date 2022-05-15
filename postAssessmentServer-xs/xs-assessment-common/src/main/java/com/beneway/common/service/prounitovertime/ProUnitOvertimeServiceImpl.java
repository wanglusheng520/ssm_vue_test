package com.beneway.common.service.prounitovertime;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.beneway.common.common.enums.MarkEnum;
import com.beneway.common.common.result.Result;
import com.beneway.common.common.utils.ClassUtil;
import com.beneway.common.dao.prounitovertime.ProUnitOvertimeDao;
import com.beneway.common.entity.prounitovertime.ProUnitOvertime;
import com.beneway.common.entity.scheme.Scheme;
import com.beneway.common.entity.schemeagency.SchemeAgency;
import com.beneway.common.entity.schemeagency.vo.SchemeAgencyVo;
import com.beneway.common.service.scheme.SchemeService;
import com.beneway.common.service.schemeagency.SchemeAgencyService;
import com.beneway.common.system.entity.sys_unit.SysUnit;
import com.beneway.common.system.entity.sys_unit.vo.SysUnitComVo;
import com.beneway.common.system.service.sys_unit.SysUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class ProUnitOvertimeServiceImpl extends ServiceImpl<ProUnitOvertimeDao , ProUnitOvertime> implements ProUnitOvertimeService {

    @Autowired
    ProUnitOvertimeDao proUnitOvertimeDao;

    @Autowired
    SchemeService schemeService;

    @Autowired
    SchemeAgencyService schemeAgencyService;

    @Autowired
    SysUnitService sysUnitService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveList(List<ProUnitOvertime> proUnitOvertimeList, Scheme scheme){
        int i = 0;
        for(ProUnitOvertime proUnitOvertime:proUnitOvertimeList){
            i++;
            proUnitOvertime.setSchemeId(scheme.getId())
                    .setMark(MarkEnum.NO)
                            .setPeriod(i);
            proUnitOvertimeDao.insert(proUnitOvertime);
        }
    }

    @Override
    public Result detailProUnitOverTime(String assId, String assType) {
        //获取方案
        Scheme scheme = schemeService.getOne(new QueryWrapper<Scheme>().lambda()
                .eq(Scheme::getAssId, assId)
                .eq(Scheme::getAssType, assType));
        if(scheme == null) return Result.ok();
        //获取方案单位关联
        List<SchemeAgency> list = schemeAgencyService.list(new QueryWrapper<SchemeAgency>().lambda()
                .eq(SchemeAgency::getSchemeId, scheme.getId()));
        List<SchemeAgencyVo> schemeAgencyVos = ClassUtil.toClassList(list, SchemeAgencyVo.class);
        for (SchemeAgencyVo schemeAgencyVo : schemeAgencyVos) {
            schemeAgencyVo.setSysUnitComVo(sysUnitService.getUnitInfo(schemeAgencyVo.getAgencyId()));
        }
        //获取期数
        List<ProUnitOvertime> proUnitOvertimes = lambdaQuery()
                .eq(ProUnitOvertime::getSchemeId, scheme.getId())
                .orderByAsc(ProUnitOvertime::getPeriod)
                .list();
        return Result.ok().put("proUnits" , proUnitOvertimes).put("schemeAgencys" , schemeAgencyVos);
    }

}
