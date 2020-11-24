package com.app.hupi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.hupi.domain.AuthOrder;
import com.app.hupi.domain.Tutoring;
import com.app.hupi.mapper.AuthOrderMapper;
import com.app.hupi.mapper.TutoringMapper;
import com.app.hupi.service.AuthOrderService;
import com.app.hupi.util.DateUtil;
import com.app.hupi.util.KiteUUID;


@Service
public class AuthOrderServiceImpl implements AuthOrderService {

	@Autowired
	private AuthOrderMapper authOrderMapper;
	@Autowired
	private TutoringMapper tutoringMapper;
	@Override
	public AuthOrder addAuthOrder(String tutoringId, String name, String idcard) {
		// TODO Auto-generated method stub
		AuthOrder authOrder=new AuthOrder();
		authOrder.setCreateTime(DateUtil.getFormatedDateTime());
		authOrder.setId(KiteUUID.getId("AUTH"));
		authOrder.setIdcard(idcard);
		authOrder.setMoney("1");
		authOrder.setName(name);
		authOrder.setStatus("0");
		authOrderMapper.insert(authOrder);
		return authOrder;
	}
	@Override
	public void afterPayAutoOrder(String id, String money) {
		AuthOrder authOrder= authOrderMapper.selectById(id);
		if(authOrder.getMoney().equals(money)||true) {
			authOrder.setStatus("1");
			authOrder.setPayTime(DateUtil.getFormatedDateTime());
			authOrderMapper.updateById(authOrder);
			// 调实名认证  认证通过之后 更新家教信息
			Tutoring tutoring=tutoringMapper.selectById(authOrder.getTutoringId());
		}
		
	}

}
