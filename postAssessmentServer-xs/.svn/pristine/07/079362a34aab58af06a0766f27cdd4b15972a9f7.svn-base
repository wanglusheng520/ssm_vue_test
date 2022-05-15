package com.beneway.common.entity.assessstart.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.beneway.common.entity.assessstart.AssessStart;
import com.beneway.common.entity.prounitovertime.ProUnitOvertime;
import com.beneway.common.entity.scheme.vo.SchemeVo;
import com.beneway.common.system.entity.sys_unit.vo.SysUnitComVo;
import lombok.Data;

import java.util.List;

@Data
public class AssessStartVo extends AssessStart {

    /**
     * 牵头单位
     */
    private SysUnitComVo sysUnitComVoQT;
    /**
     * 协同单位
     */
    private List<SysUnitComVo> sysUnitComVoListXT;

    /**
     * 方案
     */
    private SchemeVo schemeVo;

    private List<ProUnitOvertime> proUnitOvertimeList;

}
