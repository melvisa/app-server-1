package com.app.hupi.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ToturingUserInfo {
	@ApiModelProperty("头像")
	private String headImage;
	@ApiModelProperty("名字")
	private String name;
	@ApiModelProperty("账户余额")
	private Integer totalAccount;
	@ApiModelProperty("VIP标识")
	private String vipTag;
	@ApiModelProperty("VIP标识")
	private String vipTime;
	
}
