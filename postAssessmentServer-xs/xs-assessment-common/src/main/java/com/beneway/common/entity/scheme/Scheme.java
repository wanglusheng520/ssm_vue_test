package com.beneway.common.entity.scheme;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.beneway.common.entity.detailtarget.DetailTarget;
import com.beneway.common.entity.schemeagencytarget.SchemeAgencyTarget;
import com.beneway.common.entity.system.agency.Agency;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Scheme {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    private String assId;

    private String assType;

    private String createUser;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private String target;

    private String periods;


}
