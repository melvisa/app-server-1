package com.app.hupi.service;

import java.util.List;

import com.app.hupi.domain.TutoringOrder;
import com.github.pagehelper.PageInfo;

public interface TutoringOrderService {
	
	void  afterPayTutoringOrder(String orderId,String money);

	TutoringOrder  queryTutoringOrderByOrderId(String orderId);
	
	TutoringOrder addTutoringOrder(TutoringOrder tutoringOrder);
	
	TutoringOrder updateTutoringOrder(TutoringOrder tutoringOrder);
	
	
	
	List<TutoringOrder> listTutoringOrderWithTutoring(int pageNum,int pageSize,String tutoringId,String status);
	
	List<TutoringOrder> listTutoringOrderWithEmployer(int pageNum,int pageSize,String employerId,String status);
	
	PageInfo<TutoringOrder> pageInfoOrderWithTutoring(int pageNum,int pageSize,String tutoringId,String status);
	
	
	PageInfo<TutoringOrder> pageInfoOrderWithEmployer(int pageNum,int pageSize,String employerId,String status);
	
	
	
}
