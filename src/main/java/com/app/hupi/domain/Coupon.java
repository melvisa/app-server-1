package com.app.hupi.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("代金券对象")
public class Coupon {
	private String id;
	private String title;
	private Integer coupon;
	private String useDesc;
	private Integer price;
	@ApiModelProperty("未删除 1 删除")
	private int isDel;
}
