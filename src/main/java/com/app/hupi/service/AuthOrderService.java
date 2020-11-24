package com.app.hupi.service;

import com.app.hupi.domain.AuthOrder;

public interface AuthOrderService {

	AuthOrder addAuthOrder( String tutoringId,String name,String idcard);
	
	void  afterPayAutoOrder(String vipOrderId ,String money) ;
		
}
