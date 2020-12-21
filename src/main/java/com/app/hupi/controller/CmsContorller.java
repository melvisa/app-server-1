package com.app.hupi.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.hupi.constant.DataResult;
import com.app.hupi.domain.Admin;
import com.app.hupi.domain.Coupon;
import com.app.hupi.domain.ServicePrice;
import com.app.hupi.domain.Vip;
import com.app.hupi.domain.Withdrawal;
import com.app.hupi.exception.KiteException;
import com.app.hupi.mapper.AdminMapper;
import com.app.hupi.mapper.CouponMapper;
import com.app.hupi.mapper.WithdrawalMapper;
import com.app.hupi.service.CommentService;
import com.app.hupi.service.CouponOrderService;
import com.app.hupi.service.CouponService;
import com.app.hupi.service.EmployerService;
import com.app.hupi.service.ServicePriceService;
import com.app.hupi.service.TutoringService;
import com.app.hupi.service.VipService;
import com.app.hupi.service.WithdrawalService;
import com.app.hupi.util.BeanUtil;
import com.app.hupi.util.JsonUtil;
import com.app.hupi.util.KiteUUID;
import com.app.hupi.util.ListUtil;
import com.app.hupi.util.StringUtil;
import com.app.hupi.util.WebUtil;
import com.app.hupi.vo.BankInfoVO;
import com.app.hupi.vo.CommentVo;
import com.app.hupi.vo.CouponAddVo;
import com.app.hupi.vo.EmployerCmsVo;
import com.app.hupi.vo.ServicePriceAddVo;
import com.app.hupi.vo.SimpleTutoring;
import com.app.hupi.vo.TutoringDetailCmsVo;
import com.app.hupi.vo.VipAddVo;
import com.app.hupi.vo.VipVO;
import com.app.hupi.vo.WithdrawalListVo;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.github.pagehelper.PageInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RequestMapping("/cms")
@Api(tags = {"管理后台接口"})
@RestController
public class CmsContorller {
	@Autowired
	private EmployerService employerService;
	@Autowired
	private WithdrawalService withdrawalService;
	@Autowired
	private WithdrawalMapper withdrawalMapper;
	@Autowired
	private AdminMapper adminMapper;
	@Autowired
	VipService  vipService;
	@Autowired
	private CouponMapper couponMapper;
	@Autowired
	private CouponService couponService;
	@Autowired
	private CouponOrderService  couponOrderService;
	@Autowired
	private CommentService  commentService;
	
	@Autowired
	private TutoringService tutoringService;
	
	@Autowired
	private ServicePriceService servicePriceService;
	
	@ApiOperation(value = "管理员模块-登录")
	@GetMapping("/admin-login")
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
	
	
	@ApiOperation(value = "VIP模块-列表")
	@GetMapping("/vip-pageInfoVip")
	public DataResult<PageInfo<VipVO>> pageInfoVip(int pageNum,int pageSize,String type) {
		PageInfo<VipVO> pageInfo=vipService.pageInfoVip(pageNum, pageSize, type);
		return DataResult.getSuccessDataResult(pageInfo);
	}
	
	@ApiOperation(value = "VIP模块-删除")
	@GetMapping("/vip-deleteVip")
	public DataResult<String> deleteVip(String vipId) {
		int i=vipService.deleteVip(vipId);
		return DataResult.getSuccessDataResult(i+"");
	}
	
	@ApiOperation(value = "VIP模块-新增")
	@PostMapping("/vip-vipAdd")
	public DataResult<String> vipAdd(@RequestBody VipAddVo  vipAddVo) {
         Vip  vi=new  Vip();
		 vi=BeanUtil.transform(vipAddVo, vi.getClass());
		 vi.setId(KiteUUID.getId());
		 vi.setIsDel(0);
		 int i = vipService.addVip(vi);
		 return DataResult.getSuccessDataResult(i+"");
	}
	
	@ApiOperation(value = "代金券模块-代金券列表")
	@GetMapping("/coupon-pageCoupon")
	public DataResult<PageInfo<Coupon>> page(int pageNum,int pageSize) {
		PageInfo<Coupon> page=couponService.page(pageNum, pageSize);
		return DataResult.getSuccessDataResult(page);
	}
	
	
	@ApiOperation(value = "代金券模块-删除")
	@GetMapping("/coupon-deleteCoupon")
	public DataResult<String> deleteCoupon(String couponId) {
		int i=couponService.deleteCoupon(couponId);
		return DataResult.getSuccessDataResult(i+"");
	}
	
	@ApiOperation(value = "代金券模块-新增")
	@GetMapping("/coupon-couponAdd")
	public DataResult<String> couponAdd(@RequestBody CouponAddVo  couponAddVo) {
		 Coupon   coupon=new   Coupon();
		 coupon=BeanUtil.transform(couponAddVo, coupon.getClass());
		 coupon.setId(KiteUUID.getId());
		 coupon.setIsDel(0);
		  couponService.addCoupon(coupon);
		 return DataResult.getSuccessDataResult("1");
	}
	
	@ApiOperation(value = "服务价格-分页查询推荐服务价格")
	@GetMapping("/servicePrice-pageServicePrice")
	public DataResult<PageInfo<ServicePrice>> pageServicePrice(int pageNum,int pageSize,String type) {
		PageInfo<ServicePrice> pageInfo=servicePriceService.pageInfo(pageNum, pageSize, type);
		return DataResult.getSuccessDataResult(pageInfo);
		
	}
	
	@ApiOperation(value = "服务价格-新增推荐服务价格")
	@PostMapping("/servicePrice-addServicePrice")
	public DataResult<ServicePrice> addServicePrice(@RequestBody ServicePriceAddVo servicePriceAddVo) {
		ServicePrice  servicePrice=new ServicePrice();
		BeanUtil.copyProperties(servicePriceAddVo, servicePrice);
		servicePrice.setId(KiteUUID.getId());
		servicePriceService.addServicePrice(servicePrice);
		return DataResult.getSuccessDataResult(servicePrice);
	}
	
	@ApiOperation(value = "服务价格-删除 返回1 删除成功")
	@GetMapping("/servicePrice-deleteServicePrice")
	public DataResult<String> deleteServicePrice(String id) {
		int i=servicePriceService.deleteServicePrice(id);
		return DataResult.getSuccessDataResult(i+"");
	}
	
	
	@ApiOperation(value = "根据状态或者家教编码查询提现列表")
	@GetMapping("/withdrawal-listWithdrawalWithStatus")
	public DataResult<PageInfo<WithdrawalListVo>>   listWithdrawalWithStatus(String  status,String tutoringId,int pageNum,int pageSize) {
    	PageInfo<Withdrawal>pageInfo=withdrawalService.pageByStatusOrTutoringId(status, tutoringId, pageNum, pageSize);
    	PageInfo<WithdrawalListVo> page = new PageInfo<WithdrawalListVo>();
    	BeanUtil.copyProperties(pageInfo, page);
    	List<WithdrawalListVo> recored=new ArrayList<>();
    	for(Withdrawal w:pageInfo.getList()) {
    		WithdrawalListVo vo=new WithdrawalListVo();
    		BeanUtil.copyProperties(w, vo);
    		String bankInfo=w.getBankInfo();
    		if(StringUtil.isNotEmpty(bankInfo)) {
    			BankInfoVO  bankInfoVO=JsonUtil.parseObject(bankInfo, BankInfoVO.class);
    			BeanUtil.copyProperties(bankInfoVO, vo);
    		}
    		recored.add(vo);
    	}
    	page.setList(recored);
    	return DataResult.getSuccessDataResult(page);
	}
	
	@ApiOperation(value = "修改提现状态")
	@GetMapping("/withdrawal-updateWithdrawalStatus")
	public DataResult<String>   updateWithdrawalStatus(String  status,String id) {
		Withdrawal  withdrawal=withdrawalMapper.selectById(id);
		int i = 0;
		if(withdrawal!=null) {
			withdrawal.setStatus(status);
			i = withdrawalMapper.updateById(withdrawal);
		}
		return DataResult.getSuccessDataResult(i+"");
	}
	// 家教模块 
	@ApiOperation(value = "教教模块-分页查询家教评论")
	@GetMapping("/tutoring-pageComment")
	public DataResult<PageInfo<CommentVo>> pageComment(int pageNum,int pageSize,String id) {
		return DataResult.getSuccessDataResult(commentService.
				pageInfo(id, pageNum, pageSize));
	}
	@ApiOperation(value = "教教模块-分页查询家教列表")
	@GetMapping("tutoring-pageTutoring")
	public DataResult<PageInfo<SimpleTutoring>> pageTutoring(int pageNum,int pageSize,String   number ,String name) {
		PageInfo<SimpleTutoring> pageInfo=tutoringService.pageInfo(pageNum, pageSize, name, number);
		return  DataResult.getSuccessDataResult(pageInfo);
	}
	
	@ApiOperation(value = "教教模块-管理后台查询家教详情")
	@GetMapping("/tutoring-queryTutoringDetailCms")
	public DataResult<TutoringDetailCmsVo> queryTutoringDetailCms(@RequestParam(name="id",required=true)String id) {
		TutoringDetailCmsVo tutoring=tutoringService.queryTutoringDetailCmsVo(id);
		return DataResult.getSuccessDataResult(tutoring);
	}
	//雇主模块
	@ApiOperation(value = "雇主模块-分页查询雇主列表")
	@GetMapping("/employer-pageInfo")
	public DataResult<PageInfo<EmployerCmsVo>> pageInfo(int pageNum,int pageSize,String name,String number) {
		PageInfo<EmployerCmsVo> pageInfo=employerService.pageInfo(pageNum, pageSize, name, number);
		return DataResult.getSuccessDataResult(pageInfo);
	}
}
