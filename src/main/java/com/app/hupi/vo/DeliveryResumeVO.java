package com.app.hupi.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DeliveryResumeVO {
	@ApiModelProperty("是否需要支付 1需要支付  0 不需要支付")
	private String payFlag;
	@ApiModelProperty("支付时的订单号")
	private String orderId;
	@ApiModelProperty("返回描述")
	private String desc;
	
}
