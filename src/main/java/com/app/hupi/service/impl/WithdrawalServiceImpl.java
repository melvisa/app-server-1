package com.app.hupi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.hupi.domain.Tutoring;
import com.app.hupi.domain.Withdrawal;
import com.app.hupi.exception.KiteException;
import com.app.hupi.mapper.TutoringMapper;
import com.app.hupi.mapper.WithdrawalMapper;
import com.app.hupi.service.WithdrawalService;
import com.app.hupi.util.DateUtil;
import com.app.hupi.util.KiteUUID;
import com.baomidou.mybatisplus.mapper.EntityWrapper;

@Service
public class WithdrawalServiceImpl  implements WithdrawalService {

	@Autowired
	private WithdrawalMapper withdrawalMapper;
	@Autowired
	private TutoringMapper tutoringMapper;
	@Override
	@Transactional
	public Withdrawal addWithdrawal(String tutoringId, Integer money) {
		// 先判断是否有足够账户余额
		Tutoring tutoring=tutoringMapper.selectById(tutoringId);
		if(tutoring.getTotalAccount()<money) {
			//账户余额不足
			KiteException.throwException("账户余额不足");
		}
		tutoring.setTotalAccount(tutoring.getTotalAccount()-money);
		tutoringMapper.updateById(tutoring);
		
		Withdrawal  withdrawal=new Withdrawal();
		withdrawal.setId(KiteUUID.getId());
		withdrawal.setCreateTime(DateUtil.getFormatedDateTime());
		withdrawal.setStatus("0");
		withdrawal.setUserId(tutoringId);
		withdrawal.setMoney(money);
		withdrawalMapper.insert(withdrawal);
		return withdrawal;
	}
	@Override
	public List<Withdrawal> listWithdrawal(String tutoringId) {
		EntityWrapper<Withdrawal> wrapper=new EntityWrapper();
		wrapper.eq("user_id", tutoringId).orderBy("create_time desc");
		return withdrawalMapper.selectList(wrapper);
		
	}
}
