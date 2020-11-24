package com.app.hupi.domain;

import com.gitee.sunchenbin.mybatis.actable.annotation.Column;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("雇主对象")
public class Employer {

	private String id;
	
	@ApiModelProperty("电话")
	private String number;
	@Column(name="name",defaultValue="亲爱的雇主")
	private String name;
	@ApiModelProperty("注册时间")
	private String createTime;
	@Column(name="head_image",defaultValue="0")
	private String headImage;
	
	@ApiModelProperty("0 普通雇主  1 vip雇主")
	@Column(name="level",defaultValue="0")
	private String level;
	@ApiModelProperty("vip到期时间")
	private String vipTime;
	@ApiModelProperty("vip开通时间")
	private String vipCreateTime;

	private String token;
	
	private String tokenTime;
	
	private String unicode;
}
