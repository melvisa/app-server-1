package com.app.hupi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.hupi.domain.AuthOrder;
import com.app.hupi.mapper.AuthOrderMapper;
import com.app.hupi.service.AuthOrderService;
import com.app.hupi.util.DateUtil;
import com.app.hupi.util.KiteUUID;


@Service
public class AuthOrderServiceImpl implements AuthOrderService {

	@Autowired
	private AuthOrderMapper authOrderMapper;
	@Override
	public AuthOrder addAuthOrder(String tutoringId, String name, String idcard) {
		// TODO Auto-generated method stub
		AuthOrder authOrder=new AuthOrder();
		authOrder.setCreateTime(DateUtil.getFormatedDateTime());
		authOrder.setId(KiteUUID.getId("AUTH"));
		authOrder.setIdcard(idcard);
		authOrder.setName(name);
		authOrder.setStatus("0");
		authOrderMapper.insert(authOrder);
		return authOrder;
	}

}
