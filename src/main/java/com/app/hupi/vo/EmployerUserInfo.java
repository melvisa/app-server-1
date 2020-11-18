package com.app.hupi.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EmployerUserInfo {

	@ApiModelProperty("需求数量")
	private int demandNum;
	@ApiModelProperty("关注数量")
	private int attentionNum;
	@ApiModelProperty("头像")
	private String headImage;
	@ApiModelProperty("名字")
	private String name;
	@ApiModelProperty("VIP标识 1 是vip   0  不是vip")
	private String vipTag;
	@ApiModelProperty("VIP标识 vip剩余天数")
	private String vipTime;
	@ApiModelProperty("客服电话")
	private String serviceNumber;
}
