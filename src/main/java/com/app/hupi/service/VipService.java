package com.app.hupi.service;

import java.util.List;

import com.app.hupi.domain.Vip;
import com.app.hupi.vo.VipVO;
import com.github.pagehelper.PageInfo;

public interface VipService {

	
	int addVip(Vip  vip);
	
	PageInfo<VipVO>pageInfoVip(int pageNum,int pageSize,String type);
	
	List<VipVO> listVip(String type);
	
	int deleteVip(String id);
	
	VipVO updateVip(VipVO vipVO);
	
}
