package com.app.hupi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.hupi.constant.Constant;
import com.app.hupi.domain.Tutoring;
import com.app.hupi.domain.Withdrawal;
import com.app.hupi.exception.KiteException;
import com.app.hupi.mapper.TutoringMapper;
import com.app.hupi.mapper.WithdrawalMapper;
import com.app.hupi.service.WithdrawalService;
import com.app.hupi.util.DateUtil;
import com.app.hupi.util.KiteUUID;
import com.app.hupi.util.StringUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.pagination.PageHelper;
import com.github.pagehelper.PageInfo;

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
		
		// 查询银行卡信息  如果没有 则不允许提现
		String  bankInfo=tutoring.getBankInfo();
		if(StringUtil.isEmpty(bankInfo)) {
			KiteException.throwException("请先补充银行卡信息");
		}
		
		tutoring.setTotalAccount(tutoring.getTotalAccount()-money);
		tutoringMapper.updateById(tutoring);
		
		Withdrawal  withdrawal=new Withdrawal();
		withdrawal.setBankInfo(bankInfo);
		withdrawal.setId(KiteUUID.getId());
		withdrawal.setCreateTime(DateUtil.getFormatedDateTime());
		withdrawal.setStatus(Constant.WITHDRAWAL_STATUS_DSH);
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
	@Override
	public PageInfo<Withdrawal> pageByStatusOrTutoringId(String status, String tutoringId, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		EntityWrapper<Withdrawal> wrapper=new EntityWrapper<Withdrawal>();
		wrapper.eq(StringUtil.isNotEmpty(tutoringId),"user_id", tutoringId);
		wrapper.eq(StringUtil.isNotEmpty(status),"status", status);
		wrapper.orderBy("create_time desc");
		List<Withdrawal> list=withdrawalMapper.selectList(wrapper);
		PageInfo<Withdrawal> pageInfo=new PageInfo<Withdrawal>(list);
		return pageInfo;
	}
}
