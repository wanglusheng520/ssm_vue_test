package com.beneway.common.system.entity.sys_user.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.beneway.common.system.entity.sys_unit.SysUnit;
import com.beneway.common.system.entity.sys_user.SysUser;
import lombok.Data;

import java.util.List;

@Data
public class LoginUserInfo extends SysUser {

    @TableField(exist = false)
    private List<SysUnit> unitList;

    /**
     * 当前单位线单位列表
     */
    private List<SysUnit> currentUnitInList;

}
