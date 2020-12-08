package com.app.hupi.service;

import java.util.List;

import com.app.hupi.domain.Withdrawal;
import com.github.pagehelper.PageInfo;

public interface WithdrawalService {

	
	PageInfo<Withdrawal> pageByStatusOrTutoringId(String status,String tutoringId,int pageNum,int pageSize);
	
	Withdrawal addWithdrawal(String tutoringId,Integer money);
	
	List<Withdrawal> listWithdrawal(String tutoringId);
}
