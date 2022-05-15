package com.beneway.common.entity.system.menu;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Menu implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String title;
    private String icon;
    private String jump;//直接跳转链接
    private String sequence;
    private Integer pid;

    /**
     * 跳转参数json格式
     */
    private String params;

    @TableField(exist = false)
    private List<Menu> list;//子菜单
    @TableField(exist = false)
    private boolean checked;//子菜单:树形组件用
}
