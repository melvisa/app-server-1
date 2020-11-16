package com.app.hupi.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ScheduleVo {
	@ApiModelProperty(value="星期： 0 代表星期天 、1代表 星期1.......",required=true)
	private int week;
	@ApiModelProperty(value="上午是否有课标识 0 没有 1 有",required=true)
	private int morningTag;
	@ApiModelProperty(value="下午是否有课标识 0 没有 1 有",required=true)
	private int afternoonTag;
	@ApiModelProperty(value="晚上是否有课标识 0 没有 1 有",required=true)
	private int nightTag;

}
