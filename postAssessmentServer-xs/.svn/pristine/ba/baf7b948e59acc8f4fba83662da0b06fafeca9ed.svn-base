package com.beneway.common.earlywarn.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.beneway.common.common.enums.MarkEnum;
import com.beneway.common.common.result.Result;
import com.beneway.common.common.utils.jwt.LoginUserUtils;
import com.beneway.common.earlywarn.dao.EarlyWarnDao;
import com.beneway.common.earlywarn.entity.EarlyWarn;
import com.beneway.common.earlywarn.entity.enums.WarnTypeEnum;
import com.beneway.common.earlywarn.service.EarlyWarnService;
import com.beneway.common.entity.majorproject.MajorProject;
import com.beneway.common.entity.normativedoc.NormativeDoc;
import com.beneway.common.entity.operationlog.OperationLog;
import com.beneway.common.service.majorproject.MajorProjectService;
import com.beneway.common.service.normativedoc.NormativeDocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EarlyWarnServiceImpl extends ServiceImpl<EarlyWarnDao, EarlyWarn> implements EarlyWarnService {

    @Autowired
    private EarlyWarnDao earlyWarnDao;

    @Autowired
    private MajorProjectService majorProjectService;

    @Autowired
    private NormativeDocService normativeDocService;

    @Override
    public Result earlyWarn() {
        Integer supUnitId = LoginUserUtils.getSupUnitId();
        List<EarlyWarn> list = earlyWarnDao.indexEarlyWarn(supUnitId);
        //根据这个去查找对应的规范性文件或者重大项目
        for (EarlyWarn EarlyWarn : list) {
            switch (EarlyWarn.getAssType()){
                case "P":
                    MajorProject majorProject = majorProjectService.getById(EarlyWarn.getAssId());
                    EarlyWarn.setMajorProject(majorProject);
                    break;
                case "N":
                    NormativeDoc normativeDoc = normativeDocService.getById(EarlyWarn.getAssId());
                    EarlyWarn.setNormativeDoc(normativeDoc);
                    break;
            }
        }
        return Result.ok(list);
    }

    @Override
    public Result earlyWarnCount() {
        Integer integer = earlyWarnDao.earlyWarnCount();
        return Result.ok(integer);
    }

    @Override
    public String isHaveNoStandard(String assId, String assType) {
        List<EarlyWarn> list = lambdaQuery()
                .eq(EarlyWarn::getAssId, assId)
                .eq(EarlyWarn::getAssType, assType)
                .eq(EarlyWarn::getWarnType, WarnTypeEnum.NO_STANDARD)
                .ne(EarlyWarn::getWarnMark, MarkEnum.YES).list();
        if(list.isEmpty()){
            return null;
        }
        return "3";
    }

    @Override
    public Result assEarly(EarlyWarn earlyWarn) {
        earlyWarn = lambdaQuery()
                .eq(EarlyWarn::getAssId, earlyWarn.getAssId())
                .eq(EarlyWarn::getAssType, earlyWarn.getAssType())
                .eq(EarlyWarn::getWarnType, WarnTypeEnum.NO_STANDARD)
                .ne(EarlyWarn::getWarnMark, MarkEnum.YES).one();
        return Result.ok(earlyWarn);
    }
}
