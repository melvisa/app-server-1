package com.app.hupi.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AppointmentAddVO {
	@ApiModelProperty(value="家教编码",required=true)
	private String tutoringId;
	@ApiModelProperty(value="需求编码",required=true)
	private String demandId;
	
}
