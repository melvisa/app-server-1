package com.app.hupi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.hupi.domain.Coupon;
import com.app.hupi.mapper.CouponMapper;
import com.app.hupi.service.CouponService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.pagination.PageHelper;
import com.github.pagehelper.PageInfo;


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
		
		Coupon  Coupon=couponMapper.selectById(id);
		Coupon.setIsDel(1);
		return couponMapper.updateById(Coupon);
	}

	@Override
	public List<Coupon> listAll() {
		return couponMapper.selectList(new EntityWrapper<Coupon> ().eq("is_del", 0) );
	}

	@Override
	public PageInfo<Coupon> page(int pageNum, int pageSize) {
		
		PageHelper.startPage(pageNum, pageSize);
		List<Coupon>  list=	couponMapper.selectList(new EntityWrapper<Coupon> ().eq("is_del", 0) );
		 PageInfo<Coupon>  pageInfo= new PageInfo<Coupon>  (list);
		return pageInfo;
	}

}
