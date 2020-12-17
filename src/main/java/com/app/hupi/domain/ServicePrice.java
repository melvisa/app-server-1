package com.app.hupi.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("服务价格推荐")
public class ServicePrice {
	private String id;
	@ApiModelProperty(value="科目")
	private String serviceName;
	@ApiModelProperty(value="大学生服务价格")
	private String servicePrice;
	@ApiModelProperty(value="专职老师服务价格")
	private String servicePrice2;
	@ApiModelProperty(value="排序")
	private String weight;
	@ApiModelProperty(value="类型")
	private String type;
}
