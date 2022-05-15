package com.beneway.common.entity.system.router;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author wangJun
 * @createTime 2021-09-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Router implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer pid;

    private String name;

    private String path;

    private String component;

    private Integer type;

    private String title;

    private String icon;

    private Integer hidden;

    private String redirect;

    private String sequence;

    @TableField(exist = false)
    private Meta meta;

    @TableField(exist = false)
    private List<Router> children;



}
