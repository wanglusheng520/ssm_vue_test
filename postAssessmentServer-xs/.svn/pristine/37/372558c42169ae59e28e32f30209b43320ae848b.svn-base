package com.beneway.common.entity.syslog;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * Description: 日志表
 *
 * @author zxc
 * @date 2022/1/25 17:28
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("sys_log")
public class SysLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 接口地址
     */
    private String requestUrl;

    /**
     * 请求类型
     */
    private String method;
    /**
     * ip
     */
    private String ip;
    /**
     * 耗时
     */
    private String duration;
    /**
     * 请求参数
     */
    private String reqPar;
    /**
     * 执行时间
     */
    private Date time;
    /**
     * 执行人id
     */
    private String userId;
    /**
     * 执行人姓名
     */
    private String userName;

    /**
     * 操作名称
     */
    private String operationName;

}
