package com.app.hupi.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class VipAddVo {
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
	@ApiModelProperty("详细描述 格式：1、简历投递不受名额限制；//2、系统智能匹配合适订单，自动投递；//3、新订单的短信服务通知；")
	private String detailsDesc;
	@ApiModelProperty("类型  E 雇主vip  T  家教vip")
	private String type;
	@ApiModelProperty("优惠券数量")
	private Integer couponNum;
	@ApiModelProperty("赠送优惠券额度")
	private Integer coupon;

}
