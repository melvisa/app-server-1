package com.app.hupi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.hupi.domain.CouponDetail;
import com.app.hupi.mapper.CouponDetailMapper;
import com.app.hupi.service.CouponDetailService;


@Service
public class CouponDetailServiceImpl implements CouponDetailService {

	@Autowired
	private CouponDetailMapper couponDetailMapper;
	@Override
	public CouponDetail addCouponDetail(CouponDetail couponDetail) {
		couponDetailMapper.insert(couponDetail);
		return couponDetail;
	}

}
