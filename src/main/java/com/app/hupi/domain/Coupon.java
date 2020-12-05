package com.app.hupi.domain;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("代金券对象")
public class Coupon {
	private String id;
	private String title;
	private Integer coupon;
	private String useDesc;
	private Integer price;
}
