package com.beneway.common.service.startagency;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.beneway.common.common.enums.AssessmentTypeEnums;
import com.beneway.common.common.result.Result;
import com.beneway.common.common.utils.ClassUtil;
import com.beneway.common.common.utils.jwt.LoginUserUtils;
import com.beneway.common.common.utils.page.PageUtils;
import com.beneway.common.dao.startagency.StartAgencyDao;
import com.beneway.common.entity.majorproject.fo.MajorProjectQueryFo;
import com.beneway.common.entity.startagency.StartAgency;
import com.beneway.common.entity.startagency.fo.StartAgencyFo;
import com.beneway.common.entity.startagency.fo.StartAgencyQueryFo;
import com.beneway.common.entity.startagency.vo.StartAgencyVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Slf4j
public class StartAgencyServiceImpl extends ServiceImpl<StartAgencyDao , StartAgency> implements StartAgencyService {

    @Autowired
    private StartAgencyDao startAgencyDao;

    @Override
    public Page<StartAgencyVo> queryPage(StartAgencyQueryFo startAgencyQueryFo){
        Page param = PageUtils.getPage(startAgencyQueryFo);
        Page<StartAgencyVo> page = startAgencyDao.queryPage(param,startAgencyQueryFo);
        return page;
    }

    @Override
    @Transactional
    public Result del(String id) {
        startAgencyDao.deleteById(id);
        return Result.ok();
    }

    @Override
    @Transactional
    public Result addOrUpdate(StartAgencyFo startAgencyFo) {
        StartAgency startAgency = ClassUtil.toClass(startAgencyFo,StartAgency.class);
        startAgency.setProjectType(String.join(",",startAgencyFo.getProjectTypeList()))
                .setInvestType(String.join(",",startAgencyFo.getInvestTypeList()));
        if(StringUtils.isNotEmpty(startAgency.getId())){
            updateById(startAgency);
        }else{
            save(startAgency);
        }
        return Result.ok();
    }

    @Override
    public Result getById(String id) {
        StartAgency startAgency = startAgencyDao.selectById(id);
        StartAgencyFo startAgencyFo = ClassUtil.toClass(startAgency,StartAgencyFo.class);
        return Result.ok(startAgencyFo);
    }

    @Override
    public void setProjectQuery(MajorProjectQueryFo majorProjectQueryFo) {
        List<StartAgency> startAgencyList = startAgencyDao.selectList(
                new QueryWrapper<StartAgency>()
                        .lambda()
                        .eq(StartAgency::getUnitId, LoginUserUtils.getSupUnitId())
                        .eq(StartAgency::getType, AssessmentTypeEnums.PROJECT)
                        .orderByAsc()
        );
        StringBuffer investTypes = new StringBuffer();
        StringBuffer projectTypes = new StringBuffer();
        for(StartAgency startAgency:startAgencyList){
            if(StringUtils.isNotEmpty(startAgency.getInvestType())){
                investTypes.append(startAgency.getInvestType()).append(",");
            }
            if(StringUtils.isNotEmpty(startAgency.getProjectType())){
                projectTypes.append(startAgency.getProjectType()).append(",");
            }
        }
        majorProjectQueryFo.setInvestTypes(investTypes.toString());
        majorProjectQueryFo.setProjectTypes(projectTypes.toString());
    }
}
