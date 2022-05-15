package com.beneway.common.entity.detailtargetitem.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.beneway.common.entity.detailtargetitem.DetailTargetItem;
import com.beneway.common.entity.satanswer.SatAnswer;
import com.beneway.common.entity.satanswer.vo.SatAnswerVo;
import com.beneway.common.system.entity.sys_unit.vo.SysUnitComVo;
import lombok.Data;

import java.util.List;

@Data
public class DetailTargetItemVo extends DetailTargetItem {

    private List<SatAnswerVo> satAnswerVoList;

    private SysUnitComVo sysUnitComVo;

    private String targetValue;
}
