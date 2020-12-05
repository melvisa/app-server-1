package com.app.hupi.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.hupi.constant.DataResult;
import com.app.hupi.constant.HupiConfig;
import com.app.hupi.domain.AuthOrder;
import com.app.hupi.domain.CouponDetail;
import com.app.hupi.domain.Tutoring;
import com.app.hupi.mapper.AuthOrderMapper;
import com.app.hupi.mapper.TutoringMapper;
import com.app.hupi.service.AuthOrderService;
import com.app.hupi.service.CouponDetailService;
import com.app.hupi.service.TutoringService;
import com.app.hupi.util.DateUtil;
import com.app.hupi.util.HttpClientUtil;
import com.app.hupi.util.KiteUUID;
import com.app.hupi.util.StringUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RequestMapping("/auth")
@Api(tags = {"实名认证模块"})
@RestController
public class AuthOrderContorller {

	@Autowired
	private AuthOrderService authOrderService;
	@Autowired
	private TutoringService tutoringService;
	@Autowired
	private TutoringMapper  tutoringMapper;
	@Autowired
	private AuthOrderMapper autoOrderMapper;
	@Autowired
	private CouponDetailService couponDetailService;
	
	@ApiOperation(value = "实名认证支付订单")
	@GetMapping("/authOrder")
	public DataResult<String> authOrder(@RequestHeader("token")String token,String name ,String idcard) {
		Tutoring tutoring=tutoringService.queryTutoringByToken(token);
		AuthOrder authOrder=authOrderService.addAuthOrder(tutoring.getId(), name, idcard);
		return DataResult.getSuccessDataResult(authOrder.getId());
	}
	
	
	@ApiOperation(value = "获取实名认证结果,返回1 认证成功 0 认证失败")
	@GetMapping("/authResult")
	public DataResult<String> authResult(@RequestHeader("token")String token,String  orderId) {
		// 将认证更新更新到数据库
		AuthOrder authOrder=autoOrderMapper.selectById(orderId);
		String result=auth(authOrder);
		if("1".equals(result)) {
			String tutoringId=authOrder.getTutoringId();
			Tutoring tutoring =tutoringMapper.selectById(tutoringId);
			tutoring.setName(authOrder.getName());
			tutoring.setIdCard(authOrder.getIdcard());
			String authInfo="success";
			tutoring.setAuthInfo(authInfo);
			int i=tutoringMapper.updateById(tutoring);
			
			// 实名认证成功 给推荐人10元代金券
			String  yqm=tutoring.getYqm();
			if(StringUtil.isNotEmpty(yqm)) {
				Tutoring tjr=	tutoringService.queryOneByYqm(yqm);
				tjr.setCoupon(tjr.getCoupon()+10);
				tutoringMapper.updateById(tjr);
				// 增加代金券明细
				CouponDetail  couponDetail=new CouponDetail();
				couponDetail.setId(KiteUUID.getId());
				couponDetail.setCreateTime(DateUtil.getFormatedDateTime());
				couponDetail.setDesc("推荐人："+authOrder.getName()+"实名注册");
				couponDetail.setType("+");
				couponDetail.setAmount(10);
				couponDetail.setUserId(tutoring.getId());
				couponDetailService.addCouponDetail(couponDetail);
			}
			
			return  DataResult.getSuccessDataResult(i+"");
		}
		return DataResult.getSuccessDataResult(result);
		}
		
	public String  auth (AuthOrder authOrder) {
		if(authOrder!=null) {
			String name=authOrder.getName();
			String idcard=authOrder.getIdcard();
			   String host = "https://idenauthen.market.alicloudapi.com/idenAuthentication";
			    String path = "/idenAuthentication";
			    String method = "POST";
			    String appcode = HupiConfig.AUTO_APP_CODE;
			    Map<String, String> headers = new HashMap<String, String>();
			    //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
			    headers.put("Authorization", "APPCODE " + appcode);
			    //根据API的要求，定义相对应的Content-Type
			    headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			    Map<String, String> querys = new HashMap<String, String>();
			    Map<String, String> bodys = new HashMap<String, String>();
			    bodys.put("idNo", idcard);
			    bodys.put("name", name);
			    try {
			    	
			    String  s=HttpClientUtil.getInstance().sendHttpPost(host, bodys,headers);
			    if(s.contains("\"respCode\":\"0000\"")) {
			    	System.out.println("实名认证成功");
			    	return "1";
			    }
			    } catch (Exception e) {
			    	e.printStackTrace();
			    }
			}
		return "0";
	}
	
		
}

	
	
	
	
