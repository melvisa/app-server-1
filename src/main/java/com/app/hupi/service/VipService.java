package com.app.hupi.service;

import java.util.List;

import com.app.hupi.vo.VipVO;

public interface VipService {

	List<VipVO> listVip(String type);
	
	int deleteVip(String id);
	
	VipVO updateVip(VipVO vipVO);
	
}
