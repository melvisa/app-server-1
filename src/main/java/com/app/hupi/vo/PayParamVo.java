package com.app.hupi.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PayParamVo {
   @ApiModelProperty(value="支付方式传W（微信）或者A（支付宝）")
   private String payWay;
   @ApiModelProperty(value="为什么支付：0 家教订单  1 vip订单 ")
   private String payFor;
   @ApiModelProperty(value="家教订单 需要传之前生成的订单号")
   private String orderId;
}
