package com.app.hupi.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.hupi.constant.DataResult;
import com.app.hupi.domain.Admin;
import com.app.hupi.exception.KiteException;
import com.app.hupi.mapper.AdminMapper;
import com.app.hupi.util.DateUtil;
import com.app.hupi.util.KiteUUID;
import com.app.hupi.util.ListUtil;
import com.app.hupi.util.WebUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RequestMapping("/admin")
@Api(tags = {"后台管理员模块"})
@RestController
public class AdminContorller {

	@Autowired
	private AdminMapper adminMapper;
	
//	@ApiOperation(value = "管理员列表")
//	@GetMapping("/listAdmin")
	public DataResult<PageInfo<Admin>> listWithdrawal(int pageIndex,int pageSize) {
		//如果不是超级管理员 不允许查看
		List<Admin> list =new ArrayList<>();
		String adminName=(String) WebUtil.getSession().getAttribute("admin");
		if(adminName==null) {
			KiteException.throwException("请重新登录");
		}
		if(!"admin".equals(adminName)) {
			return DataResult.getSuccessDataResult(new PageInfo<>(list));
		}
		PageHelper.startPage(pageIndex, pageSize,true);
		EntityWrapper<Admin> wrapper=new EntityWrapper<Admin>();
		wrapper.eq("is_del", "否").orderBy("time desc");
		list=adminMapper.selectList(wrapper);
		PageInfo<Admin> pageInfo=new PageInfo<>(list);
		return DataResult.getSuccessDataResult(pageInfo);
	}
	
	@ApiOperation(value = "管理员登录")
	@GetMapping("/login")
	public DataResult<Admin> login(String username,String password) {
		EntityWrapper<Admin> wrapper=new EntityWrapper<Admin>();
		wrapper.eq("username", username).eq("password", password);
		List<Admin> list=adminMapper.selectList(wrapper);
		// 登录成功
		if(ListUtil.isEmpty(list)) {
			KiteException.throwException("账户或者密码错误");
		}
		WebUtil.getSession().setAttribute("admin", username);
		return DataResult.getSuccessDataResult(list.get(0));
	}
	
	
	@ApiOperation(value = "管理员账号新增")
	@GetMapping(value="/addAdmin")
	public DataResult<Admin> addAdmin(String username,String password) {
		Admin admin=new Admin();
		admin.setUsername(username);
		Admin temp=adminMapper.selectOne(admin);
		if(temp!=null) {
			KiteException.throwException("账号已存在");
		}
		admin.setId(KiteUUID.getId());
		admin.setPassword(password);
		admin.setTime(DateUtil.getFormatedDateTime());
		adminMapper.insert(admin);
		return DataResult.getSuccessDataResult(admin);
	}
	
	@ApiOperation(value = "管理员账号删除")
	@GetMapping("/deleteAdmin")
	public DataResult<Admin> deleteAdmin(String id) {
		Admin admin=adminMapper.selectById(id);
		if(admin!=null) {
			admin.setIsDel("是");
			adminMapper.updateById(admin);
		}
		return DataResult.getSuccessDataResult(admin);
	}
	
}
