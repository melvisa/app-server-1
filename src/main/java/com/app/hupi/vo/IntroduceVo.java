package com.app.hupi.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class IntroduceVo {

	@ApiModelProperty("标签,多个标签,号分割")
	private String tags;
	@ApiModelProperty("个性介绍")
	private String introduce;
}
