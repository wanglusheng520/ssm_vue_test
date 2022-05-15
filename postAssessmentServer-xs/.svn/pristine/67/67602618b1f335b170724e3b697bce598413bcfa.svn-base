package com.beneway.common.entity.detailtargetitem;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.models.auth.In;
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
public class DetailTargetItem {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    private String detailTargetId;

    private String item;

    @TableField(exist = false)
    private Integer agencyId;
    @TableField(exist = false)
    private String operator;

}
