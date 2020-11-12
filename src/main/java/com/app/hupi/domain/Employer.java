package com.app.hupi.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("雇主对象")
public class Employer {

	private String id;
	
	@ApiModelProperty("电话")
	private String number;
	
	private String name;
	@ApiModelProperty("注册时间")
	private String createTime;
	
	private String token;
	
	private String tokenTime;
}
