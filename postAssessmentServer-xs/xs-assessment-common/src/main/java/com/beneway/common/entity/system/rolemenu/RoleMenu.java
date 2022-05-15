package com.beneway.common.entity.system.rolemenu;

import com.baomidou.mybatisplus.annotation.IdType;
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
public class RoleMenu implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer roleId;
    private Integer menuId;
}
