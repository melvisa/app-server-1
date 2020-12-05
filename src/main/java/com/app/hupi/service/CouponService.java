package com.app.hupi.service;

import java.util.List;

import com.app.hupi.domain.Coupon;

public interface CouponService {

	Coupon  addCoupon(Coupon Coupon);
	
	int deleteCoupon(String id);
	
	List<Coupon> listAll();
}
