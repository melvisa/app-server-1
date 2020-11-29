package com.app.hupi.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("新增服务价格对象")
public class ServicePriceAddVo {
	@ApiModelProperty(value="服务名称",required=true)
	private String serviceName;
	@ApiModelProperty(value="服务价格",required=true)
	private String servicePrice;
	@ApiModelProperty(value="排序")
	private String weight;
	@ApiModelProperty(value="类型",required=true)
	private String type; 
}
