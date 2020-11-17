package com.app.hupi.service;

import com.app.hupi.domain.VipOrder;

public interface VipOrderService {

	VipOrder  addVipOrder(String userId,String type,String vipId);
}
