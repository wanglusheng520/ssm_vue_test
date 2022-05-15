package com.beneway.common.service.operationlog;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.beneway.common.common.enums.StageEnum;
import com.beneway.common.common.result.Result;
import com.beneway.common.common.utils.jwt.LoginUserUtils;
import com.beneway.common.dao.operationlog.OperationLogDao;
import com.beneway.common.entity.majorproject.MajorProject;
import com.beneway.common.entity.normativedoc.NormativeDoc;
import com.beneway.common.entity.operationlog.OperationLog;
import com.beneway.common.service.majorproject.MajorProjectService;
import com.beneway.common.service.normativedoc.NormativeDocService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OperationLogServiceImpl extends ServiceImpl<OperationLogDao , OperationLog> implements OperationLogService {

    @Autowired
    private OperationLogDao operationLogDao;

    @Autowired
    private MajorProjectService majorProjectService;

    @Autowired
    private NormativeDocService normativeDocService;

    @Override
    public Result newAssesEvent() {
        Integer supUnitId = LoginUserUtils.getSupUnitId();
        List<OperationLog> operationLogs = operationLogDao.newAssesEvent(supUnitId);
        //根据这个去查找对应的规范性文件或者重大项目
        for (OperationLog operationLog : operationLogs) {
            switch (operationLog.getAssType()){
                case "P":
                    MajorProject majorProject = majorProjectService.getById(operationLog.getAssId());
                    operationLog.setMajorProject(majorProject);
                    break;
                case "N":
                    NormativeDoc normativeDoc = normativeDocService.getById(operationLog.getAssId());
                    operationLog.setNormativeDoc(normativeDoc);
                    break;
            }
        }
        return Result.ok(operationLogs);
    }

    @Override
    public Result assessAmount() {
        //总评估数
        Integer allAssess = operationLogDao.assessAmount();
        //以评估数
        Integer yiAssess = operationLogDao.yiAssess(StageEnum.D_EVALUATED.getStage() , StageEnum.Z_WAIT_RECTIFICATION.getStage() , StageEnum.F_ALREADY_RECTIFICATION.getStage());
        //总整改数
        Integer yizg = operationLogDao.yizg(StageEnum.F_ALREADY_RECTIFICATION.getStage());
        Map<String, Integer> res = new HashMap<>();
        res.put("allAssess" , allAssess);
        res.put("yiAssess" , yiAssess);
        res.put("yizg" , yizg);
        return Result.ok(res);
    }
}
