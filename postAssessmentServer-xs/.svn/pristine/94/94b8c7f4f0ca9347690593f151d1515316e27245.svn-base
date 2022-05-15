package com.beneway.common.entity.putrule;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 规则项明细表
 */


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class PutRuleItem {

    @TableId(type = IdType.ASSIGN_ID)
    private String id;


    /**
     * 规则表id
     */
    private String putRuleId;

    /**
     *
     */
    private String rule_item_name;


    private String rule_item_content;

    /**
     * 字段名
     */
    private String fieldName;

    /**
     * 规则
     */
    private String symbol;
    /**
     * 具体内容
     */
    private String fieldVal;
}
