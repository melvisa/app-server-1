package com.app.hupi.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("雇主注册对象")
public class EmployerAddVO {
	@ApiModelProperty(value="电话",required=true)
	private String number;
	@ApiModelProperty(value="验证码",required=true)
	private String code;
}
