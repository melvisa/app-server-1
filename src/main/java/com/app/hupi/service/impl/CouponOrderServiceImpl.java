package com.app.hupi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.hupi.domain.Coupon;
import com.app.hupi.domain.CouponOrder;
import com.app.hupi.domain.Tutoring;
import com.app.hupi.mapper.CouponMapper;
import com.app.hupi.mapper.CouponOrderMapper;
import com.app.hupi.mapper.TutoringMapper;
import com.app.hupi.service.CouponOrderService;
import com.app.hupi.util.DateUtil;


@Service
public class CouponOrderServiceImpl  implements CouponOrderService {

	@Autowired
	private CouponOrderMapper couponOrderMapper;
	@Autowired
	private CouponMapper couponMapper;
	@Autowired
	private TutoringMapper tutoringMapper;
	
	@Override
	public CouponOrder addCouponOrder(CouponOrder couponOrder) {
		// TODO Auto-generated method stub
		couponOrderMapper.insert(couponOrder);
		return couponOrder;
	}

	@Override
	public CouponOrder queryCouponOrderById(String id) {
		// TODO Auto-generated method stub
		return couponOrderMapper.selectById(id);
	}

	@Override
	@Transactional
	public void afterPayUpdate(String id, String money) {
		CouponOrder  couponOrder=couponOrderMapper.selectById(id);
		String tutoringId =couponOrder.getUserId();
		String  couponId=couponOrder.getCouponId();
		Coupon  coupon=	couponMapper.selectById(couponId);
		Tutoring tutoring=tutoringMapper.selectById(tutoringId);
		Integer total=tutoring.getCoupon()+coupon.getCoupon();
		// 修改用户代金券金额
		tutoring.setCoupon(total);
		tutoringMapper.updateById(tutoring);
		
		couponOrder.setStatus("1");
		couponOrder.setPayTime(DateUtil.getFormatedDateTime());
		couponOrderMapper.updateById(couponOrder);
	}

}
