package com.beneway.common.entity.schemeagencytarget;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.beneway.common.entity.detailtarget.DetailTarget;
import com.beneway.common.entity.satanswer.SatAnswer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class SchemeAgencyTarget {

    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    private String schemeAgencyId;

    private String targetId;

    private String target;

    private String itemId;

}
