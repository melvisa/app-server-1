package com.app.hupi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.hupi.constant.DataResult;
import com.app.hupi.domain.Coupon;
import com.app.hupi.domain.CouponOrder;
import com.app.hupi.domain.Tutoring;
import com.app.hupi.mapper.CouponMapper;
import com.app.hupi.service.CouponOrderService;
import com.app.hupi.service.CouponService;
import com.app.hupi.service.TutoringService;
import com.app.hupi.util.DateUtil;
import com.app.hupi.util.KiteUUID;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RequestMapping("/coupon")
@Api(tags = {"代金券"})
@RestController
public class CouponContorller {

	
	@Autowired
	private CouponMapper couponMapper;
	@Autowired
	private CouponService couponService;
	@Autowired
	private CouponOrderService  couponOrderService;
	@Autowired
	private TutoringService tutoringService;
	
	@ApiOperation(value = "代金券列表")
	@GetMapping("/listCoupon")
	public DataResult<List<Coupon>> listCoupon() {
		List<Coupon> list=couponService.listAll();
		return DataResult.getSuccessDataResult(list);
	}
	
	@ApiOperation(value = "家教生成代金券订单")
	@GetMapping("/addCouponOrder")
	public DataResult<String> addCouponOrder(	@RequestHeader("token")String token,String couponId ) {
		Tutoring tutoring=tutoringService.queryTutoringByToken(token);
		CouponOrder  couponOrder=new CouponOrder();
		couponOrder.setCouponId(couponId);
		Coupon coupon=couponMapper.selectById(couponId);
		couponOrder.setId(KiteUUID.getId("COUPON"));
		couponOrder.setCreateTime(DateUtil.getFormatedDateTime());
		couponOrder.setMoney(coupon.getPrice()+"");
		couponOrder.setStatus("0");
		couponOrder.setUserId(tutoring.getId());
		couponOrderService.addCouponOrder(couponOrder);
		return DataResult.getSuccessDataResult(couponOrder.getId());
	}
	
	
	
	
}
