package com.app.hupi.vo;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EduVo {
	@ApiModelProperty("教育经历对象")
   private List<EduExperienceVO> eduExperienceList;	
}
