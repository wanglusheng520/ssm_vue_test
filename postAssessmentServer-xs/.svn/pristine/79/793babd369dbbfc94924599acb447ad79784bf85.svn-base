package com.beneway.common.entity.system.role;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.beneway.common.entity.system.login.Loginuser;
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
public class Role implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String roleName;

    private String remark;

    private String agencyPositions;

    private String userPositions;

    /**
     * 类型 U：用户 P：用户类型和单位类型 agencyPosition、userPosition才有效
     */
    private String type;

    private Boolean isWork;

    @TableField(exist = false)
    private List<Integer> menuIds;

    @TableField(exist = false)
    private List<Loginuser> userList;
}
