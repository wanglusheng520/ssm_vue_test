package com.beneway.common.service.assessstart;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.beneway.common.common.result.Result;
import com.beneway.common.common.utils.ClassUtil;
import com.beneway.common.common.utils.jwt.LoginUserUtils;
import com.beneway.common.dao.assessstart.AssessStartDao;
import com.beneway.common.entity.assessstart.AssessStart;
import com.beneway.common.entity.assessstart.vo.AssessStartVo;
import com.beneway.common.entity.prounitovertime.ProUnitOvertime;
import com.beneway.common.entity.scheme.Scheme;
import com.beneway.common.service.prounitovertime.ProUnitOvertimeService;
import com.beneway.common.service.scheme.SchemeService;
import com.beneway.common.system.entity.sys_unit.SysUnit;
import com.beneway.common.system.entity.sys_unit.vo.SysUnitComVo;
import com.beneway.common.system.service.sys_unit.SysUnitService;
import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.workqueue.WorkQueue;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AssessStartServiceImpl extends ServiceImpl<AssessStartDao , AssessStart> implements AssessStartService {

    @Autowired
    private AssessStartDao assessStartDao;
    @Autowired
    private SysUnitService sysUnitService;
    @Autowired
    private SchemeService schemeService;
    @Autowired
    private ProUnitOvertimeService proUnitOvertimeService;

    private AssessStartService getCurrThis(){
        AssessStartService currentProxy = (AssessStartService) AopContext.currentProxy();
        return currentProxy;
    }

    @Override
    public Result getUnit(AssessStart assessStart) {
        AssessStart a = assessStartDao.selectOne(
                new QueryWrapper<AssessStart>()
                        .lambda()
                        .eq(AssessStart::getAssId,assessStart.getAssId())
        );
        if(null == a){
            return Result.ok(null);
        }

        AssessStartVo assessStartVo = ClassUtil.toClass(a,AssessStartVo.class);
        SysUnitComVo sysUnitComVo = sysUnitService.getUnitInfo(a.getQtUnit());
        assessStartVo.setSysUnitComVoQT(sysUnitComVo);
        List<SysUnitComVo> sysUnitComVoList = sysUnitService.getUnitInfoList(a.getXtUnit());
        assessStartVo.setSysUnitComVoListXT(sysUnitComVoList);

        //获取方案
        Scheme scheme = schemeService.getOne(new QueryWrapper<Scheme>()
                .lambda().eq(Scheme::getAssId, assessStart.getAssId()).select(Scheme::getId));
        //获取截至时间
        List<ProUnitOvertime> list = proUnitOvertimeService.list(
                new QueryWrapper<ProUnitOvertime>()
                        .lambda()
                        .eq(ProUnitOvertime::getSchemeId, scheme.getId())
        );
        assessStartVo.setProUnitOvertimeList(list);
        return Result.ok(assessStartVo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveMakePlan(Scheme scheme,String xtUnit) {
        AssessStart assessStart = AssessStart.builder()
                .createTime(new Date())
                .createUser(LoginUserUtils.getLoginUserId())
                .assType(scheme.getAssType())
                .assId(scheme.getAssId())
                .qtUnit(LoginUserUtils.getLoginUserInfo().getSupUnitId())
                .xtUnit(xtUnit)
                .build();
        getCurrThis().save(assessStart);
    }

    @Override
    public Result myParticipation() {
        Integer supUnitId = LoginUserUtils.getSupUnitId();
        List<AssessStart> assessStarts = assessStartDao.selectList(new QueryWrapper<AssessStart>()
                .lambda().select(AssessStart::getQtUnit, AssessStart::getXtUnit,AssessStart::getAssId));
        Set<String> assId = new HashSet<>();
        for (AssessStart assessStart : assessStarts) {
            Integer qtUnit = assessStart.getQtUnit();
            String xtUnit = assessStart.getXtUnit();
            if(qtUnit.equals(supUnitId)){
                assId.add(assessStart.getAssId());
            }
            if(StringUtils.isNotBlank(xtUnit)){
                for (String s : xtUnit.split(",")) {
                    if(s.equals(supUnitId.toString())){
                        assId.add(assessStart.getAssId());
                    }
                }
            }
        }
        return Result.ok(assId.size());
    }

    @Override
    public Result getDetail(AssessStart assessStart) {
        AssessStart ass = lambdaQuery().eq(AssessStart::getAssId, assessStart.getAssId())
                .eq(AssessStart::getAssType, assessStart.getAssType()).one();
        if(ass == null){
            return Result.ok();
        }
        SysUnit one = sysUnitService.getOne(new QueryWrapper<SysUnit>().lambda().eq(SysUnit::getId, ass.getQtUnit()).select(SysUnit::getUnitName));
        ass.setAgencyName(one.getUnitName());
        return Result.ok(ass);
    }
}
