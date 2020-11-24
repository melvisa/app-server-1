package com.app.hupi.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("家教订单")
public class TutoringOrder {
	@ApiModelProperty("主键编码")
	private String id;
	@ApiModelProperty("家教编码")
	private String tutoringId;
	@ApiModelProperty("雇主编码")
	private String employerId;
	@ApiModelProperty("需求编码")
	private String demandId;
	@ApiModelProperty("状态")
	private String status;
	@ApiModelProperty(value="是否需要诚意金 1 是 0 否")
	private String earnestFlag;
	@ApiModelProperty("是否需要支付")
	private String payFlag;
	private String money;
	@ApiModelProperty("支付订单号")
	private String orderId;
	@ApiModelProperty("时间")
	private String createTime;
	@ApiModelProperty("不合适时间")
	private String bhsTime;
	
	@ApiModelProperty("应约并联系时间")
	private String  yyblxTime;
	
	@ApiModelProperty("合适时间")
	private String  hsTime;
	
	
	@ApiModelProperty("描述")
	private String desc;
	
	
}
