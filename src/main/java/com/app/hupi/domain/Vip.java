package com.app.hupi.domain;

import com.gitee.sunchenbin.mybatis.actable.annotation.Column;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class Vip {
	private String id;
	@ApiModelProperty("标题")
	private String title;
	@ApiModelProperty("折扣描述")
	private String discountDesc;
	@ApiModelProperty("现价")
	private String presentPrice;
	@ApiModelProperty("原价")
	private String originalPrice;
	@ApiModelProperty("简单描述")
	private String desc;
	@ApiModelProperty("时长")
	private Integer duration;
	@ApiModelProperty("详细描述")
	private String detailsDesc;
	@ApiModelProperty("类型  E 雇主vip  T  家教vip")
	private String type;
	@ApiModelProperty("优惠券数量")
	private Integer couponNum;
	@ApiModelProperty("赠送优惠券额度")
	private Integer coupon;
	@ApiModelProperty("未删除 1 删除")
	private int isDel;
}
