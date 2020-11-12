package com.app.hupi.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("服务价格推荐")
public class ServicePrice {
	private String id;
	@ApiModelProperty(value="服务名称",required=true)
	private String serviceName;
	@ApiModelProperty(value="服务价格",required=true)
	private String servicePrice;
	@ApiModelProperty(value="排序")
	private String weight;
	@ApiModelProperty(value="类型",required=true)
	private String type;
}
