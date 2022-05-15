package com.beneway.common.entity.userlogin;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2021-06-05 17:32:55
 */
@Data
@TableName("userlogin_log")
public class UserloginLog implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId(type = IdType.AUTO)
	private Integer id;
	/**
	 * 用户名
	 */
	private String userId;

	@TableField(exist = false)
	private String username;
	/**
	 * ip地址
	 */
	private String ipAddress;
	/**
	 * 登陆时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date loginTime;

	/**
	 * 登录类型 Z:账号密码登录 Q:扫码登录 P:手机端登录
	 */
	private String type;

}
