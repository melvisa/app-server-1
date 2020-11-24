package com.app.hupi.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LoginVO {

	@ApiModelProperty(value="号码",required=true)
	private String number;
	@ApiModelProperty(value="短信验证码",required=true)
	private String code;
	@ApiModelProperty(value="手机唯一标识")
	private String unicode;
}
