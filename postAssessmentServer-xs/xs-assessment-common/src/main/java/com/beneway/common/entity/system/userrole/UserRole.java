package com.beneway.common.entity.system.userrole;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class UserRole implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String userId;
    private Integer roleId;
}
