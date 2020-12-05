package com.app.hupi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.hupi.domain.CouponDetail;
import com.app.hupi.domain.Employer;
import com.app.hupi.domain.Tutoring;
import com.app.hupi.domain.Vip;
import com.app.hupi.domain.VipOrder;
import com.app.hupi.mapper.TutoringMapper;
import com.app.hupi.mapper.VipMapper;
import com.app.hupi.mapper.VipOrderMapper;
import com.app.hupi.service.CouponDetailService;
import com.app.hupi.service.EmployerService;
import com.app.hupi.service.VipOrderService;
import com.app.hupi.util.DateUtil;
import com.app.hupi.util.KiteUUID;
import com.baomidou.mybatisplus.mapper.EntityWrapper;


@Service
public class VipOrderServiceImpl implements VipOrderService {

	@Autowired
	private VipOrderMapper vipOrderMapper;
	
	@Autowired
	private VipMapper vipMapper;
	@Autowired
	private EmployerService employerService;
	@Autowired
	private TutoringMapper tutoringMapper;
	@Autowired
	private  CouponDetailService  couponDetailService;
	
	
	@Override
	public VipOrder addVipOrder(String userId, String type,String vipId) {
		VipOrder  vipOrder=new VipOrder();
		vipOrder.setCreateTime(DateUtil.getFormatedDateTime());
		vipOrder.setOrderId(KiteUUID.getId("VIP"));
		vipOrder.setStatus("0");
		Vip vip=vipMapper.selectById(vipId);
		vipOrder.setMoney(vip.getPresentPrice());
		vipOrder.setType(type);
		vipOrder.setVipId(vipId);
		vipOrder.setUserId(userId);
		vipOrderMapper.insert(vipOrder);
		return vipOrder;
	}


	private VipOrder getVipOrderByOrderId(String vipOrderId) {
		VipOrder vipOrder=new VipOrder();
		vipOrder.setOrderId(vipOrderId);
		return vipOrderMapper.selectOne(vipOrder);
	}
	
	
	private void updateByOrderId(VipOrder vipOrder) {
		EntityWrapper<VipOrder> wrapper=new EntityWrapper();
		wrapper.eq("order_id", vipOrder.getOrderId());
		vipOrderMapper.update(vipOrder, wrapper);
	}
	@Override
	@Transactional
	public void afterPayVipOrder(String vipOrderId, String money) {
		VipOrder vipOrder=getVipOrderByOrderId(vipOrderId);
		if(vipOrder.getMoney().equals(money)||true) {
			vipOrder.setStatus("1");
			vipOrder.setPayTime(DateUtil.getFormatedDateTime());
			String userId=vipOrder.getUserId();
			String type=vipOrder.getType();
			updateByOrderId(vipOrder);
			// 获取vip时长
			Vip vip=vipMapper.selectById(vipOrder.getVipId());
			Integer duration=vip.getDuration();
			String vipTime=DateUtil.getNextDay(duration,DateUtil.DATE_TIME_FORMAT);
			if("E".equals(type)) {
				Employer  employer=employerService.queryEmployerById(userId);
				employer.setVipTime(vipTime);
				employer.setLevel("1");
				employer.setVipCreateTime(DateUtil.getFormatedDateTime());
				employerService.updateEmployer(employer);
			}
			else if("T".equals(type)) {
				Tutoring tutoring=tutoringMapper.selectById(userId);
				tutoring.setLevel("1");
				tutoring.setVipTime(vipTime);
				tutoring.setVipCreateTime(DateUtil.getFormatedDateTime());
				Integer num=vip.getCouponNum();
				Integer coupon=vip.getCoupon();
				// 增加代金券额度
				if(num!=null) {
					tutoring.setCoupon(tutoring.getCoupon()+(num*coupon));
				}
				tutoringMapper.updateById(tutoring);
				// 增加代金券明细
				//代金券明细
				CouponDetail  couponDetail=new CouponDetail();
				couponDetail.setId(KiteUUID.getId());
				couponDetail.setCreateTime(DateUtil.getFormatedDateTime());
				couponDetail.setDesc("VIP充值赠送："+vipOrderId);
				couponDetail.setType("+");
				couponDetail.setAmount((num*coupon));
				couponDetail.setUserId(tutoring.getId());
				couponDetailService.addCouponDetail(couponDetail);
				// 佣金分配
			}
		}
		
	}

}
