package com.app.hupi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.hupi.constant.DataResult;
import com.app.hupi.domain.Employer;
import com.app.hupi.domain.Tutoring;
import com.app.hupi.exception.KiteException;
import com.app.hupi.service.EmployerService;
import com.app.hupi.service.TutoringService;
import com.app.hupi.util.CodeUtil;
import com.app.hupi.util.DateUtil;
import com.app.hupi.util.KiteUUID;
import com.app.hupi.util.StringUtil;
import com.app.hupi.util.WebUtil;
import com.app.hupi.vo.LoginVO;
import com.app.hupi.vo.UserInfoVO;
import com.app.hupi.vo.UserVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RequestMapping("/user")
@Api(tags = {"用户登录"})
@RestController
public class UserContorller {
	
	@Autowired
	private TutoringService tutoringService;
	@Autowired
	private EmployerService employerService;
	
	@ApiOperation(value = "用户登录")
	@GetMapping("/login")
	public DataResult<UserInfoVO> login( LoginVO LoginVO) {
		UserInfoVO  userInfoVO=new UserInfoVO();
	     if(CodeUtil.checkFail(LoginVO.getCode(), LoginVO.getNumber())) {
	    	 KiteException.throwException("验证码错误");
	     }
	     Tutoring tutoring=tutoringService.queryTutoringByNumber(LoginVO.getNumber());
	     UserVO userVO=new UserVO();
	     if(tutoring!=null) {
	    	 userVO.setId(tutoring.getId());
	    	 userVO.setNumber(tutoring.getNumber());
	    	 userVO.setStatus("T");
	    	 WebUtil.getSession().setAttribute("user", userVO);
	    	 userInfoVO.setUserType("T");
	    	 userInfoVO.setTutoring(tutoring);
	    	 // 生成token 并保存数据库
	    	 String token=KiteUUID.getId();
	    	 String tokenTime=DateUtil.getFormatedDateTime();
	    	 tutoring.setToken(token);
	    	 tutoring.setTokenTime(tokenTime);
	    	 tutoringService.updateTutoring(tutoring);
	    	 return DataResult.getSuccessDataResult(userInfoVO);
	     }
	     Employer employer=employerService.queryEmployerByNumber(LoginVO.getNumber());
	     if(employer==null) {
	    	 KiteException.throwException("号码未注册,请先进行注册");
	     }
	     // 生成token 并保存数据库
	     String token=KiteUUID.getId();
    	 String tokenTime=DateUtil.getFormatedDateTime();
    	 employer.setToken(token);
    	 employer.setTokenTime(tokenTime);
    	 employerService.updateEmployer(employer);
	     userVO.setNumber(employer.getNumber());
    	 userVO.setStatus("E");
    	 userVO.setId(employer.getId());
    	 userInfoVO.setUserType("E");
    	 userInfoVO.setEmployer(employer);
	     WebUtil.getSession().setAttribute("user", userVO);
	     return DataResult.getSuccessDataResult(userInfoVO);
	}
	
	
	
	
	
}
