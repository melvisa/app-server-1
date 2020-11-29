package com.app.hupi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.hupi.constant.Constant;
import com.app.hupi.domain.TutoringOrder;
import com.app.hupi.mapper.TutoringOrderMapper;
import com.app.hupi.service.TutoringOrderService;
import com.app.hupi.util.StringUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;


@Service
public class TutoringOrderServiceImpl implements TutoringOrderService {

	@Autowired
	private TutoringOrderMapper tutoringOrderMapper;
	
	@Override
	public TutoringOrder addTutoringOrder(TutoringOrder tutoringOrder) {
		tutoringOrder.setMoney("10");
		tutoringOrderMapper.insert(tutoringOrder);
		tutoringOrder=tutoringOrderMapper.selectById(tutoringOrder.getId());
		return tutoringOrder;
	}

	@Override
	public TutoringOrder updateTutoringOrder(TutoringOrder tutoringOrder) {
		tutoringOrderMapper.updateById(tutoringOrder);
		tutoringOrder=tutoringOrderMapper.selectById(tutoringOrder.getId());
		return tutoringOrder;
	}

	@Override
	public List<TutoringOrder> listTutoringOrderWithTutoring(int pageNum,int pageSize,String tutoringId, String status) {
		EntityWrapper<TutoringOrder> wrapper=new EntityWrapper<>();
		wrapper.eq("tutoring_id", tutoringId).eq("status", status);
		PageHelper.startPage(pageNum,pageSize);
		return tutoringOrderMapper.selectList(wrapper);
	}

	@Override
	public List<TutoringOrder> listTutoringOrderWithEmployer(int pageNum,int pageSize,String employerId, String status) {
		EntityWrapper<TutoringOrder> wrapper=new EntityWrapper<>();
		wrapper.eq("employer_id", employerId).eq("status", status);
		PageHelper.startPage(pageNum,pageSize);
		return tutoringOrderMapper.selectList(wrapper);
	}

	@Override
	public TutoringOrder queryTutoringOrderByOrderId(String orderId) {
		TutoringOrder  tutoringOrder=new TutoringOrder();
		tutoringOrder.setOrderId(orderId);
		return tutoringOrderMapper.selectOne(tutoringOrder);
	}

	
	
	@Override
	public void afterPayTutoringOrder(String orderId, String money) {
		TutoringOrder  tutoringOrder=queryTutoringOrderByOrderId(orderId);
		// 更新订单 
		tutoringOrder.setStatus(Constant.TUTORING_ORDER_STATUS_DAIYINGYUE);
		tutoringOrderMapper.updateById(tutoringOrder);
	}

	@Override
	public PageInfo<TutoringOrder> pageInfoOrderWithTutoring(int pageNum, int pageSize, String tutoringId,
			String status) {
		EntityWrapper<TutoringOrder> wrapper=new EntityWrapper<>();
		wrapper.eq("tutoring_id", tutoringId);
		if(StringUtil.isNotEmpty(status)) {
			wrapper.eq("status", status);
		}
		PageHelper.startPage(pageNum,pageSize);
		List<TutoringOrder> list= tutoringOrderMapper.selectList(wrapper);
		PageInfo<TutoringOrder> pageInfo=new PageInfo<>(list);
		return pageInfo;
	}

	@Override
	public PageInfo<TutoringOrder> pageInfoOrderWithEmployer(int pageNum, int pageSize, String employerId,
			String status) {
		EntityWrapper<TutoringOrder> wrapper=new EntityWrapper<>();
		wrapper.eq("employer_id", employerId);
		if(StringUtil.isNotEmpty(status)) {
			wrapper.eq("status", status);
		}
		PageHelper.startPage(pageNum,pageSize);
		List<TutoringOrder> list= tutoringOrderMapper.selectList(wrapper);
		PageInfo<TutoringOrder> pageInfo=new PageInfo<>(list);
		return pageInfo;
	}

	
	
	
	
}
