package com.app.hupi.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.hupi.domain.Vip;
import com.app.hupi.mapper.VipMapper;
import com.app.hupi.service.VipService;
import com.app.hupi.util.BeanUtil;
import com.app.hupi.util.StringUtil;
import com.app.hupi.vo.VipVO;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.pagination.PageHelper;
import com.github.pagehelper.PageInfo;


@Service
public class VipServiceImpl implements VipService {

	@Autowired
	private VipMapper vipMapper;
	@Override
	public List<VipVO> listVip(String type) {
		EntityWrapper<Vip> wrapper=new EntityWrapper<>();
		wrapper.eq("is_del", 0).orderBy("present_price");
		if(type!=null) {
			wrapper.eq("type", type);
		}
		List<Vip> vipList=vipMapper.selectList(wrapper);
		List<VipVO>  list=new ArrayList<>();
		for(Vip  vip:vipList) {
			VipVO vipVO=new VipVO();
			BeanUtil.copyProperties(vip, vipVO);
			String desc=vip.getDetailsDesc();
			vipVO.setDetailsDesc(Arrays.asList(desc.split("//")));
			list.add(vipVO);
		}
		return list;
	}

	@Override
	public int deleteVip(String id) {
		Vip vip=vipMapper.selectById(id);
		vip.setIsDel(1);
		return vipMapper.updateById(vip);
	}

	@Override
	public VipVO updateVip(VipVO vipVO) {
		Vip vip=new Vip();
		BeanUtil.copyProperties(vipVO, vip);
		vipMapper.updateById(vip);
		vip=vipMapper.selectById(vipVO.getId());
		BeanUtil.copyProperties(vip, vipVO);
		String desc=vip.getDetailsDesc();
		vipVO.setDetailsDesc(Arrays.asList(desc.split("//")));
		return vipVO;
	}

	@Override
	public PageInfo<VipVO> pageInfoVip(int pageNum, int pageSize, String type) {
		PageHelper.startPage(pageNum, pageSize);
		EntityWrapper<Vip> wrapper=new EntityWrapper<>();
		wrapper.eq("is_del", 0).orderBy("present_price");
		if(StringUtil.isNotEmpty(type)) {
			wrapper.eq("type", type);
		}
		List<Vip> vipList=vipMapper.selectList(wrapper);
		PageInfo<Vip> info=new PageInfo<Vip>(vipList);
		PageInfo<VipVO> page=new PageInfo<VipVO>();
		BeanUtil.copyProperties(info, page);
		List<VipVO>  list=new ArrayList<>();
		for(Vip  vip:vipList) {
			VipVO vipVO=new VipVO();
			BeanUtil.copyProperties(vip, vipVO);
			String desc=vip.getDetailsDesc();
			vipVO.setDetailsDesc(Arrays.asList(desc.split("//")));
			list.add(vipVO);
		}
		page.setList(list);
		return page;
	}

	@Override
	public int addVip(Vip vip) {
		// TODO Auto-generated method stub
		return vipMapper.insert(vip);
	}

}
