package com.app.hupi.vo;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class VipVO {
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
	private String duration;
	@ApiModelProperty("详细描述")
	private List<String> detailsDesc;
}
