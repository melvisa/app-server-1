package com.app.hupi.domain;

import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="分组对象")
public class Code {

	@Column(name = "id",type = MySqlTypeConstant.INT,length = 11,isKey = true,isAutoIncrement = true)
	private Integer id;
	
	@ApiModelProperty(value="分组名")
	private String groupName;
	@ApiModelProperty(value="字典名称")
	private String name;
	@ApiModelProperty(value="字典值")
	private String value;
	@ApiModelProperty(value="排序:值越小 权重越大")
	private String weight;
	@ApiModelProperty(value="分组描述")
	private String groupDesc;
}
