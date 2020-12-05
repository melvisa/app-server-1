package com.app.hupi.service;

import com.app.hupi.domain.CouponOrder;

public interface CouponOrderService {
	
	CouponOrder  addCouponOrder(CouponOrder couponOrder);
	
	CouponOrder queryCouponOrderById(String  id);
	
	void  afterPayUpdate(String id,String money);
}
