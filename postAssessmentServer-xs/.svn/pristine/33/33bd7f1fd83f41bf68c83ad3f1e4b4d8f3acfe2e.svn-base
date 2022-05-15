package com.beneway.common.entity.system.dict;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Dict implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer neKey;
    private String neValue;
    private String content;
    private Integer isDelete;
    private String option01;

    @TableField(exist = false)
    private String title;//用来给tree用的标题
}
