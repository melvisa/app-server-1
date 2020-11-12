package com.app.hupi.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("教育经验")
public class EduExperienceVO {
	  @ApiModelProperty(value="学校",required=true)
	  private String school;
	  @ApiModelProperty(value="专业",required=true)
	  private String major;
	  @ApiModelProperty(value="开始时间",required=true)
	  private String startTime;
	  @ApiModelProperty(value="结束时间",required=true)
	  private String endTime;
	  @ApiModelProperty(value="在校经历",required=true)
	  private String experience;
}
