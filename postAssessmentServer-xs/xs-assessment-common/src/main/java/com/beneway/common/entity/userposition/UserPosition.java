package com.beneway.common.entity.userposition;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zhy
 * @email 2434017367@qq.com
 * @date 2021-07-29 09:53:20
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("user_position")
public class UserPosition implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户单位类型表
     * agency_position 用户单位类型
     * position 用户类型 1普通单位  2司法局    3县政府    4县委     5市场监管局      7政府机构
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 用户单位类型
     */
    private Integer agencyPosition;
    /**
     * 用户类型
     */
    private Integer position;

}
