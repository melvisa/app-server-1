package com.app.hupi.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EmployerUserInfo {

	private int demandNum;
	private int appointmentNum;
	private String headImage;
	private String name;
	@ApiModelProperty("VIP标识")
	private String vipTag;
	@ApiModelProperty("VIP标识")
	private String vipTime;
}
