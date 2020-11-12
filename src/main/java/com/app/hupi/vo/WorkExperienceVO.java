package com.app.hupi.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
@ApiModel("工作经验")
@Data
public class WorkExperienceVO {
	  @ApiModelProperty(value="标题",required=true)
	  private String title;
	  @ApiModelProperty(value="开始时间",required=true)
	  private String startTime;
	  @ApiModelProperty(value="结束时间",required=true)
	  private String endTime;
	  @ApiModelProperty(value="内容",required=true)
	  private String content;
}
