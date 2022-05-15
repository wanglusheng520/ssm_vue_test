package com.beneway.common.entity.startagency;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
public class StartAgency {

    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    private Integer unitId;

    private String type;

    /**
     * 投资性质
     */
    private String investType;

    /**
     * 项目类型
     */
    private String projectType;


}
