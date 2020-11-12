package com.app.hupi.service;

import java.util.List;

import com.app.hupi.domain.TutoringOrder;

public interface TutoringOrderService {

	TutoringOrder addTutoringOrder(TutoringOrder tutoringOrder);
	
	TutoringOrder updateTutoringOrder(TutoringOrder tutoringOrder);
	
	List<TutoringOrder> listTutoringOrderWithTutoring(int pageNum,int pageSize,String tutoringId,String status);
	
	List<TutoringOrder> listTutoringOrderWithEmployer(int pageNum,int pageSize,String employerId,String status);
}
