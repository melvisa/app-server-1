package com.app.hupi.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SimpleUserVo {

	private String id;
	@ApiModelProperty("头像")
	private String headImage;
	@ApiModelProperty("名字")
	private String name;
	@ApiModelProperty("时间")
	private String time;
	@ApiModelProperty("邀请码")
	private String yqm;
	
}
