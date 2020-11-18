package com.app.hupi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.hupi.domain.TutoringOrder;
import com.app.hupi.mapper.TutoringOrderMapper;
import com.app.hupi.service.TutoringOrderService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.github.pagehelper.PageHelper;


@Service
public class TutoringOrderServiceImpl implements TutoringOrderService {

	@Autowired
	private TutoringOrderMapper tutoringOrderMapper;
	
	@Override
	public TutoringOrder addTutoringOrder(TutoringOrder tutoringOrder) {
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
		
		if("1".equals(status)) {
			status="待应约";
		}
		else if("2".equals(status)) {
			status="待确认";
		}
		
		if("3".equals(status)) {
			status="合适";
		}
		
		if("4".equals(status)) {
			status="不合适";
		}
		
		EntityWrapper<TutoringOrder> wrapper=new EntityWrapper<>();
		wrapper.eq("tutoring_id", tutoringId).eq("status", status);
		PageHelper.startPage(pageNum,pageSize);
		return tutoringOrderMapper.selectList(wrapper);
	}

	@Override
	public List<TutoringOrder> listTutoringOrderWithEmployer(int pageNum,int pageSize,String employerId, String status) {

		if("1".equals(status)) {
			status="待应约";
		}
		else if("2".equals(status)) {
			status="待确认";
		}
		
		if("3".equals(status)) {
			status="合适";
		}
		
		if("4".equals(status)) {
			status="不合适";
		}
		
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

	
	
	
	
}
