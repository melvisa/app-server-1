package com.app.hupi.service;

import com.app.hupi.domain.VipOrder;

public interface VipOrderService {

	VipOrder  addVipOrder(String userId,String type,String vipId);
	
	/**
	 * 支付成功后修改
	 * @param vipId
	 * @param money
	 */
	void  afterPayVipOrder(String vipOrderId ,String money);
}
