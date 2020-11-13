package com.app.hupi.vo;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class WorkVo {

	@ApiModelProperty("工作经历")
	private List<WorkExperienceVO> list;
}
