package com.beneway.common.service.judgmentin;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.beneway.common.common.enums.StatusEnum;
import com.beneway.common.common.result.Result;
import com.beneway.common.common.utils.jwt.LoginUserUtils;
import com.beneway.common.dao.judgmentin.JudgmentInDao;
import com.beneway.common.dao.majorproject.MajorProjectDao;
import com.beneway.common.dao.normativedoc.NormativeDocDao;
import com.beneway.common.entity.judgmentin.JudgmentIn;
import com.beneway.common.entity.judgmentin.fo.JudgmentInFo;
import com.beneway.common.entity.majorproject.MajorProject;
import com.beneway.common.entity.normativedoc.NormativeDoc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Description:
 *
 * @author zxc
 * @date 2022/2/22 14:52
 */
@Service
public class JudgmentInServiceImp extends ServiceImpl<JudgmentInDao, JudgmentIn> implements JudgmentInService {

    @Autowired
    JudgmentInDao judgmentInDao;
    @Autowired
    MajorProjectDao majorProjectDao;
    @Autowired
    NormativeDocDao normativeDocDao;

    @Transactional
    public Result add(JudgmentInFo judgmentIn){
        judgmentIn.setJudgmentUser(LoginUserUtils.getLoginUserId())
                .setJudgmentTime(new Date());
        judgmentInDao.insert(judgmentIn);

        //主表状态致为Y
        //投资项目
        if("P".equals(judgmentIn.getAssType())){
            MajorProject majorProject = MajorProject.builder()
                    .id(judgmentIn.getAssId())
                    .projectGrade(judgmentIn.getJudgmentGrade())
                    .projectStatus(StatusEnum.YRK.getStatus())
                    .projectStage("A")
                    .build();
            majorProjectDao.updateById(majorProject);
        }
        //规范性文件
        if("N".equals(judgmentIn.getAssType())){
            NormativeDoc normativeDoc = NormativeDoc.builder()
                    .id(judgmentIn.getAssId())
                    .normativeGrade(judgmentIn.getJudgmentGrade())
                    .normativeStatus(StatusEnum.YRK.getStatus())
                    .normativeStage("A")
                    .build();
            normativeDocDao.updateById(normativeDoc);
        }
        return Result.ok();
    }

    /**
     * 1.传入类型assType
     * 2.根据assType查找相应规则
     * 3.利用规则进行研判入库
     */
    @Transactional
    public Result auto(JudgmentInFo judgmentIn){
        return Result.ok();
    }
}
