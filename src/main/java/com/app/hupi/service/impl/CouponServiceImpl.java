package com.app.hupi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.hupi.domain.Coupon;
import com.app.hupi.mapper.CouponMapper;
import com.app.hupi.service.CouponService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;


@Service
public class CouponServiceImpl implements CouponService {

	@Autowired
	private CouponMapper couponMapper;
	@Override
	public Coupon addCoupon(Coupon Coupon) {
		couponMapper.insert(Coupon);
		return Coupon;
	}

	@Override
	public int deleteCoupon(String id) {
		return couponMapper.deleteById(id);
	}

	@Override
	public List<Coupon> listAll() {
		return couponMapper.selectList(new EntityWrapper<Coupon> () );
	}

}
