package com.app.hupi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.hupi.domain.Vip;
import com.app.hupi.domain.VipOrder;
import com.app.hupi.mapper.VipMapper;
import com.app.hupi.mapper.VipOrderMapper;
import com.app.hupi.service.VipOrderService;
import com.app.hupi.util.DateUtil;
import com.app.hupi.util.KiteUUID;


@Service
public class VipOrderServiceImpl implements VipOrderService {

	@Autowired
	private VipOrderMapper vipOrderMapper;
	
	@Autowired
	private VipMapper vipMapper;
	
	
	@Override
	public VipOrder addVipOrder(String userId, String type,String vipId) {
		VipOrder  vipOrder=new VipOrder();
		vipOrder.setCreateTime(DateUtil.getFormatedDateTime());
		vipOrder.setOrderId(KiteUUID.getId("VIP"));
		vipOrder.setStatus("0");
		Vip vip=vipMapper.selectById(vipId);
		vipOrder.setMoney(vip.getPresentPrice());
		vipOrder.setType(type);
		vipOrder.setUserId(userId);
		vipOrderMapper.insert(vipOrder);
		return vipOrder;
	}

}
