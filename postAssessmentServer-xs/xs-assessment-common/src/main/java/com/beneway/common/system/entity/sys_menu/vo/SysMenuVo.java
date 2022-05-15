package com.beneway.common.system.entity.sys_menu.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.beneway.common.system.entity.sys_menu.SysMenu;
import lombok.Data;

import java.util.List;

@Data
public class SysMenuVo extends SysMenu {

    @TableField(exist = false)
    private List<SysMenuVo> children;

    @TableField(exist = false)
    private List<String> permissionList;

}
