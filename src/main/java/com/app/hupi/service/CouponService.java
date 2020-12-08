package com.app.hupi.service;

import java.util.List;

import com.app.hupi.domain.Coupon;
import com.github.pagehelper.PageInfo;

public interface CouponService {

	PageInfo<Coupon> page(int pageNum,int pageSize);
	
	Coupon  addCoupon(Coupon Coupon);
	
	int deleteCoupon(String id);
	
	List<Coupon> listAll();
}
