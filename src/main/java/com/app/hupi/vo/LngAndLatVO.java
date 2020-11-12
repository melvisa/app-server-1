package com.app.hupi.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class LngAndLatVO {

	@ApiModelProperty("经度")
	private String lng;
	@ApiModelProperty("纬度")
	private String lat;
}
