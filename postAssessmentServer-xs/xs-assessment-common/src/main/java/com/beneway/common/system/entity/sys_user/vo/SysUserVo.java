package com.beneway.common.system.entity.sys_user.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.beneway.common.system.entity.sys_role.SysRole;
import com.beneway.common.system.entity.sys_tag.SysTag;
import com.beneway.common.system.entity.sys_unit.SysUnit;
import com.beneway.common.system.entity.sys_user.SysUser;
import lombok.Data;

import java.util.List;

/**
 * Create by zhy on 2022/2/28 17:41
 */
@Data
public class SysUserVo extends SysUser {

    @TableField(exist = false)
    private List<SysUnit> unitList;

    @TableField(exist = false)
    private List<SysRole> roleList;

    @TableField(exist = false)
    private List<SysTag> tagList;

}
