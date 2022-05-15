package com.beneway.common.entity.schemetarget;


import com.baomidou.mybatisplus.annotation.IdType;
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
public class SchemeTarget {

    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 方案id
     */
    private String schemeId;
    /**
     * 指标id
     */
    private String detailTargetId;
    /**
     * 目标值
     */
    private String expect;

    private String answer;

    private String result;
}
