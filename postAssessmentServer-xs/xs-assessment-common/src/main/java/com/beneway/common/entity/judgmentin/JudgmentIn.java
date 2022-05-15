package com.beneway.common.entity.judgmentin;

import com.alibaba.druid.proxy.jdbc.JdbcParameter;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * Description:
 *
 * @author zxc
 * @date 2022/2/22 14:46
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class JudgmentIn implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     *  研判等级
     */
    private String judgmentGrade;
    /**
     *  说明
     */
    private String judgmentNote;
    /**
     *  附件
     */
    private String judgmentFiles;
    /**
     *  研判类型 项目P/合同C/规范性文件N
     */
    private String assType;
    /**
     *  研判关联表id
     */
    private String assId;
    /**
     *  研判人
     */
    private String judgmentUser;
    /**
     *
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date judgmentTime;

}
