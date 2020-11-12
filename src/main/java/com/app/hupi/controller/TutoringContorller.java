package com.app.hupi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.hupi.constant.DataResult;
import com.app.hupi.domain.Tutoring;
import com.app.hupi.exception.KiteException;
import com.app.hupi.mapper.TutoringOrderMapper;
import com.app.hupi.service.TutoringOrderService;
import com.app.hupi.service.TutoringService;
import com.app.hupi.util.BeanUtil;
import com.app.hupi.util.CodeUtil;
import com.app.hupi.util.JsonUtil;
import com.app.hupi.util.WebUtil;
import com.app.hupi.vo.BankInfoVO;
import com.app.hupi.vo.TutoringAddVO;
import com.app.hupi.vo.TutoringDetailVO;
import com.app.hupi.vo.TutoringListVO;
import com.app.hupi.vo.TutoringRegisterVO;
import com.app.hupi.vo.UserVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RequestMapping("/tutoring")
@Api(tags = {"家教模块"})
@RestController
public class TutoringContorller {

	@Autowired
	private TutoringService tutoringService;
	@Autowired
	private TutoringOrderService tutoringOrderService;
	@Autowired
	private TutoringOrderMapper tutoringOrderMapper;
	
	@ApiOperation(value = "银行卡绑定")
	@PostMapping("/bindBank")
	public DataResult<BankInfoVO> bindBank(@RequestBody BankInfoVO bankInfoVO) {
		UserVO userVO=(UserVO) WebUtil.getSession().getAttribute("user");
		Tutoring tutoring=tutoringService.queryTutoringByNumber(userVO.getNumber());
		tutoring.setBankInfo(JsonUtil.toJson(bankInfoVO));
		tutoringService.updateTutoring(tutoring);
		tutoring=tutoringService.queryTutoringByNumber(userVO.getNumber());
		return DataResult.getSuccessDataResult(JsonUtil.parseObject(tutoring.getBankInfo(), BankInfoVO.class));
	}
	
	@ApiOperation(value = "银行卡信息")
	@GetMapping("/bankInfo")
	public DataResult<BankInfoVO> bankInfo() {
		UserVO userVO=(UserVO) WebUtil.getSession().getAttribute("user");
		Tutoring tutoring=tutoringService.queryTutoringByNumber(userVO.getNumber());
		return DataResult.getSuccessDataResult(JsonUtil.parseObject(tutoring.getBankInfo(), BankInfoVO.class));
	}
	
	
	@ApiOperation(value = "家教注册")
	@PostMapping("/register")
	public DataResult<Tutoring> register(@RequestBody TutoringRegisterVO tutoringRegisterVO) {
	   if(CodeUtil.checkFail(tutoringRegisterVO.getCode(), tutoringRegisterVO.getNumber())) {
	    	 KiteException.throwException("验证码错误");
	    }
		Tutoring tutoring=tutoringService.register(tutoringRegisterVO);
		return DataResult.getSuccessDataResult(tutoring);
	}
	
	@ApiOperation(value = "家教资料提交")
	@PostMapping("/addTutoring")
	public DataResult<Tutoring> addTutoring(@RequestBody TutoringAddVO tutoringAddVO) {
		Tutoring tutoring=tutoringService.addTutoring(tutoringAddVO);
		return DataResult.getSuccessDataResult(tutoring);
	}
	
	@ApiOperation(value = "雇主查看家教列表")
	@GetMapping("/listTutoringList")
	 @ApiImplicitParams({
		    @ApiImplicitParam(name = "tutoringType", value = "家教类型", required = true, dataType = "String"),
	        @ApiImplicitParam(name = "lng", value = "经度", required = true, dataType = "String"),
	        @ApiImplicitParam(name = "lat", value = "纬度", required = true, dataType = "String"),
	        @ApiImplicitParam(name = "pageNum", value = "第几页", required = true, dataType = "int"),
	        @ApiImplicitParam(name = "pageSize", value = "每页记录数", required = true, dataType = "int")
	 })
	public DataResult<List<TutoringListVO>> listTutoringList(
			@RequestParam(name="tutoringType",required=true)String tutoringType,
			@RequestParam(name="lng",required=true)String lng,
			@RequestParam(name="lat",required=true)String lat,
			@RequestParam(name="pageNum",required=true)int pageNum,
			@RequestParam(name="pageSize",required=true)int pageSize) {
		UserVO userVO=(UserVO) WebUtil.getSession().getAttribute("user");
		List<TutoringListVO> list=tutoringService.listTutoringList(userVO.getId(),tutoringType, lng, lat, pageNum, pageSize);
		return DataResult.getSuccessDataResult(list);
	}
	@ApiOperation(value = "家教详情")
	@GetMapping("/queryTutoringDetail")
	public DataResult<TutoringDetailVO> queryTutoringDetail(@RequestParam(name="id",required=true)String id) {
		TutoringDetailVO tutoring=tutoringService.queryTutoringDetail(id);
		return DataResult.getSuccessDataResult(tutoring);
	}
	
	@ApiOperation(value = "资料查询")
	@GetMapping("baseInfo")
	public DataResult<Tutoring> baseInfo(@RequestHeader("token")String token) {
		Tutoring tutoring=tutoringService.queryTutoringByToken(token);
		return  DataResult.getSuccessDataResult(tutoring);
		
	}
	
	@ApiOperation(value = "资料更新")
	@PostMapping("updateInfo")
	public DataResult<Tutoring> updateInfo(@RequestHeader("token")String token,@RequestBody TutoringAddVO tutoringAddVO) {
		Tutoring tutoring=tutoringService.queryTutoringByToken(token);
		BeanUtil.copyProperties(tutoringAddVO, tutoring);
		tutoring.setEduExperience(JsonUtil.toJson(tutoringAddVO.getEduExperience()));
		tutoring.setWorkExperience(JsonUtil.toJson(tutoringAddVO.getWorkExperienceList()));
		tutoringService.updateTutoring(tutoring);
		return  DataResult.getSuccessDataResult(tutoring);
		
	}
	
	
	
	
}
