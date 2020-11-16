package com.app.hupi.service;

import java.util.List;

import com.app.hupi.domain.Withdrawal;

public interface WithdrawalService {

	Withdrawal addWithdrawal(String tutoringId,Integer money);
	
	List<Withdrawal> listWithdrawal(String tutoringId);
}
