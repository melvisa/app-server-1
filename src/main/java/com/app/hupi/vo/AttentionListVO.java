package com.app.hupi.vo;

import com.app.hupi.domain.Attention;
import com.app.hupi.domain.Employer;
import com.app.hupi.domain.Tutoring;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel("关注列表对象")
public class AttentionListVO {
	@ApiModelProperty("家教對象")
	private Tutoring tutoring;
	@ApiModelProperty("雇主對象對象")
	private Employer employer;
	@ApiModelProperty("关注对象")
	private Attention attention;
}
