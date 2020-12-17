package com.app.hupi.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.hupi.constant.DataResult;
import com.app.hupi.domain.Employer;
import com.app.hupi.domain.Tutoring;
import com.app.hupi.exception.KiteException;
import com.app.hupi.mapper.TutoringOrderMapper;
import com.app.hupi.service.CodeService;
import com.app.hupi.service.CommentService;
import com.app.hupi.service.EmployerService;
import com.app.hupi.service.TutoringOrderService;
import com.app.hupi.service.TutoringService;
import com.app.hupi.util.BeanUtil;
import com.app.hupi.util.CodeUtil;
import com.app.hupi.util.DateUtil;
import com.app.hupi.util.JsonUtil;
import com.app.hupi.util.StringUtil;
import com.app.hupi.vo.BankInfoVO;
import com.app.hupi.vo.CommentVo;
import com.app.hupi.vo.EduVo;
import com.app.hupi.vo.IntroduceVo;
import com.app.hupi.vo.ScheduleObjVo;
import com.app.hupi.vo.SimpleTutoring;
import com.app.hupi.vo.SimpleUserVo;
import com.app.hupi.vo.ToturingUserInfo;
import com.app.hupi.vo.TutoringAddVO;
import com.app.hupi.vo.TutoringBaseInfo;
import com.app.hupi.vo.TutoringDetailCmsVo;
import com.app.hupi.vo.TutoringDetailVO;
import com.app.hupi.vo.TutoringListVO;
import com.app.hupi.vo.TutoringRegisterVO;
import com.app.hupi.vo.WorkVo;
import com.github.pagehelper.PageInfo;

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
	private CodeService codeService;
	@Autowired
	private EmployerService employerService;
	@Autowired
	private TutoringOrderService tutoringOrderService;
	@Autowired
	private TutoringOrderMapper tutoringOrderMapper;


	
	
	@ApiOperation(value = "银行卡绑定")
	@PostMapping("/bindBank")
	public DataResult<BankInfoVO> bindBank(@RequestHeader("token")String token,@RequestBody BankInfoVO bankInfoVO) {
		Tutoring tutoring=tutoringService.queryTutoringByToken(token);
		tutoring.setBankInfo(JsonUtil.toJson(bankInfoVO));
		tutoringService.updateTutoring(tutoring);
		tutoring=tutoringService.queryTutoringByNumber(tutoring.getNumber());
		return DataResult.getSuccessDataResult(JsonUtil.parseObject(tutoring.getBankInfo(), BankInfoVO.class));
	}
	
	@ApiOperation(value = "银行卡信息")
	@GetMapping("/bankInfo")
	public DataResult<BankInfoVO> bankInfo(@RequestHeader("token")String token) {
		Tutoring tutoring=tutoringService.queryTutoringByToken(token);
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
	        @ApiImplicitParam(name = "pageNum", value = "第几页", required = true, dataType = "int"),
	        @ApiImplicitParam(name = "pageSize", value = "每页记录数", required = true, dataType = "int")
	 })
	public DataResult<List<TutoringListVO>> listTutoringList(
			@RequestHeader("token")String token,
			@RequestParam(name="tutoringType",required=true)String tutoringType,
			@RequestParam(name="pageNum",required=true)int pageNum,
			@RequestParam(name="pageSize",required=true)int pageSize) {
		Employer employer=employerService.queryEmployerByToken(token);
		List<TutoringListVO> list=tutoringService.listTutoringList(employer.getId(),tutoringType, "", "", pageNum, pageSize);
		return DataResult.getSuccessDataResult(list);
	}
	
	@ApiOperation(value = "条件查询家教列表")
	@GetMapping("/listTutoringListByParams")
	 @ApiImplicitParams({
		    @ApiImplicitParam(name = "classType", value = "家教类型",required = false, dataType = "String"),
		    @ApiImplicitParam(name = "tutoringType", value = "家教类型", required = false, dataType = "String"),
		    @ApiImplicitParam(name = "tutoringIdentity", value = "家教类型",required = false,  dataType = "String"),
		    @ApiImplicitParam(name = "sex", value = "性别", required = false, dataType = "String"),
	        @ApiImplicitParam(name = "pageNum", value = "第几页", required = true, dataType = "int"),
	        @ApiImplicitParam(name = "pageSize", value = "每页记录数", required = true, dataType = "int")
	 })
	public DataResult<List<TutoringListVO>> listTutoringListByParams(
			@RequestParam(name="classType",required = false)String classType,
			@RequestParam(name="tutoringType",required = false)String tutoringType,
			@RequestParam(name="tutoringIdentity",required = false)String tutoringIdentity,
			@RequestParam(name="sex",required = false)String sex,
			@RequestParam(name="pageNum",required=true)int pageNum,
			@RequestParam(name="pageSize",required=true)int pageSize) {
		
		Map<String,String>params=new HashMap<String,String>();
		params.put("classType", classType);
		params.put("tutoringType", tutoringType);
		params.put("tutoringIdentity", tutoringIdentity);
		params.put("sex", sex);
		List<TutoringListVO> list=tutoringService.listTutoringListByParams(pageNum, pageSize, params);
		return DataResult.getSuccessDataResult(list);
	}
	
	// 判断是否可以代金券购买
	@ApiOperation(value = "判断是否可以代金券购买,返回1 满足代金券购买条件 2 不满足")
	@GetMapping("/checkCoupon")
	public DataResult<String> checkCoupon(@RequestHeader("token")String token,@RequestParam(name="id",required=true)String id) {
		Tutoring tutoring=tutoringService.queryTutoringByToken(token);
		Integer coupon=tutoring.getCoupon();
		if(coupon>10) {
			return DataResult.getSuccessDataResult("1"); 
		}
		return DataResult.getSuccessDataResult("0");
	}
	
	
	
	
	@ApiOperation(value = "家教详情")
	@GetMapping("/queryTutoringDetail")
	public DataResult<TutoringDetailVO> queryTutoringDetail(@RequestHeader("token")String token,@RequestParam(name="id",required=true)String id) {
		Employer employer=employerService.queryEmployerByToken(token);
		TutoringDetailVO tutoring=tutoringService.queryTutoringDetail(id, employer.getId());
		return DataResult.getSuccessDataResult(tutoring);
	}
	
	@ApiOperation(value = "新人资料是否提现完成,返回1 完成 0 未完成")
	@GetMapping("baseInfoCheck")
	public DataResult<Integer> baseInfoCheck(@RequestHeader("token")String token) {
		 Tutoring tutoring=tutoringService.queryTutoringByToken(token);
		 String name=tutoring.getName();
		 String workExperience=tutoring.getWorkExperience();
		 String eduExperience=tutoring.getEduExperience();
		 String introduce=tutoring.getIntroduce();
		 String headImage=tutoring.getHeadImage();
		 if(StringUtil.isEmpty(name)||StringUtil.isEmpty(workExperience)||StringUtil.isEmpty(eduExperience)
				 ||StringUtil.isEmpty(introduce)||StringUtil.isEmpty(headImage)) {
			 return DataResult.getSuccessDataResult(0);
		 }
		return  DataResult.getSuccessDataResult(1);
		
	}
	
	@ApiOperation(value = "查询基本资料")
	@GetMapping("queryBaseInfo")
	public DataResult<Tutoring> queryBaseInfo(@RequestHeader("token")String token) {
		Tutoring tutoring=tutoringService.queryTutoringByToken(token);
		return  DataResult.getSuccessDataResult(tutoring);
	}
	
	@ApiOperation(value = "查询我的邀请码")
	@GetMapping("queryYqm")
	public DataResult<String> queryYqm(@RequestHeader("token")String token) {
		Tutoring tutoring=tutoringService.queryTutoringByToken(token);
		return  DataResult.getSuccessDataResult(tutoring.getYqmSelf());
	}
	
	@ApiOperation(value = "家教个人中心信息")
	@GetMapping("queryToturingUserInfo")
	public DataResult<ToturingUserInfo> queryToturingUserInfo(@RequestHeader("token")String token) {
		Tutoring tutoring=tutoringService.queryTutoringByToken(token);
		ToturingUserInfo info=new ToturingUserInfo();
		info.setHeadImage(tutoring.getHeadImage());
		info.setName(tutoring.getName());
		info.setTotalAccount(tutoring.getTotalAccount());
		info.setVipTag(tutoring.getLevel());
		if("1".equals(tutoring.getLevel())) {
			//计算剩余天数
			long subDay=DateUtil.getBetweenDays(DateUtil.getFormatedDate(), 
					tutoring.getVipTime(), DateUtil.DATE_FORMAT);
			info.setVipTime(subDay+"");
		}
		String serviceNumber=codeService.queryCodeValueByGroupAndValue("serviceNumber", "serviceNumber");
		info.setServiceNumber(serviceNumber);
		return  DataResult.getSuccessDataResult(info);
	}


	@ApiOperation(value = "查询我的下线")
	@GetMapping("subUserByYqm")
	public DataResult<List<SimpleUserVo>> subUser(String yqm) {
		List<SimpleUserVo> list=tutoringService.queryByYqm(yqm);
		return  DataResult.getSuccessDataResult(list);
	}
	
	
	@ApiOperation(value = "查询我的相册")
	@GetMapping("album")
	public DataResult<List<String>> album(@RequestHeader("token")String token) {
		Tutoring tutoring=tutoringService.queryTutoringByToken(token);
		String album=tutoring.getAlbum();
		if(album!=null) {
			return  DataResult.getSuccessDataResult(Arrays.asList(album.split(",")));
		}
		else {
			return DataResult.getSuccessDataResult(new ArrayList<String>());
		}
	}
	
	
	@ApiOperation(value = "更新我的相册,多个相册之间英文逗号分隔")
	@PostMapping("updateAlbum")
	public DataResult<List<String>> updateAlbum(@RequestHeader("token")String token
			,String album) {
		Tutoring tutoring=tutoringService.queryTutoringByToken(token);
		tutoring.setAlbum(album);
		tutoringService.updateTutoring(tutoring);
		return  DataResult.getSuccessDataResult(Arrays.asList(album.split(",")));
	}
	
	@ApiOperation(value = "查询我的下线")
	@GetMapping("subUserByToken")
	public DataResult<List<SimpleUserVo>> subUserByToken(@RequestHeader("token")String token) {
		Tutoring tutoring=tutoringService.queryTutoringByToken(token);
		List<SimpleUserVo> list=tutoringService.queryByYqm(tutoring.getYqmSelf());
		return  DataResult.getSuccessDataResult(list);
	}
	
	
	@ApiOperation(value = "查询我的课程安排")
	@GetMapping("querySchedule")
	public DataResult<String> querySchedule(@RequestHeader("token")String token) {
		Tutoring tutoring=tutoringService.queryTutoringByToken(token);
		String str=tutoring.getSchedule();
//		List<ScheduleVo> list=JsonUtil.parseArray(str, ScheduleVo.class );
		return  DataResult.getSuccessDataResult(str);
	}
	
	
	@ApiOperation(value = "更新课程安排")
	@PostMapping("updateSchedule")
	public DataResult<String> updateSchedule(@RequestHeader("token")String token,
			@RequestBody ScheduleObjVo scheduleObjVo) {
		Tutoring tutoring=tutoringService.queryTutoringByToken(token);
		String json=JsonUtil.toJson(scheduleObjVo);
		tutoring.setSchedule(json);
		tutoringService.updateTutoring(tutoring);
		return querySchedule(token);
	}
	
	
	@ApiOperation(value = "基本资料更新")
	@PostMapping("updateBaseInfo")
	public DataResult<Tutoring> updateBaseInfo(@RequestHeader("token")String token,@RequestBody TutoringBaseInfo tutoringBaseInfo) {
		Tutoring tutoring=tutoringService.queryTutoringByToken(token);
		BeanUtil.copyProperties(tutoringBaseInfo, tutoring);
		tutoringService.updateTutoring(tutoring);
		return  DataResult.getSuccessDataResult(tutoring);
		
   }
	@ApiOperation(value = "个性介绍更新")
	@PostMapping("updateIntroduce")
	public DataResult<Tutoring> updateIntroduce(@RequestHeader("token")String token,@RequestBody IntroduceVo introduceVo) {
		Tutoring tutoring=tutoringService.queryTutoringByToken(token);
		tutoring.setIntroduce(introduceVo.getIntroduce());
		tutoring.setTags(introduceVo.getTags());
		tutoringService.updateTutoring(tutoring);
		return  DataResult.getSuccessDataResult(tutoring);
		
   }
	@ApiOperation(value = "头像更新")
	@PostMapping("updateHeadImage")
	public DataResult<Tutoring> updateHeadImage(@RequestHeader("token")String token,String  headImage) {
		Tutoring tutoring=tutoringService.queryTutoringByToken(token);
		tutoring.setHeadImage(headImage);
		tutoringService.updateTutoring(tutoring);
		return  DataResult.getSuccessDataResult(tutoring);
		
   }
	
	@ApiOperation(value = "教育经验更新")
	@PostMapping("updateEdu")
	public DataResult<Tutoring> updateEdu(@RequestHeader("token")String token,
			@RequestBody  EduVo eduVo) {
		Tutoring tutoring=tutoringService.queryTutoringByToken(token);
		eduVo.getEduExperienceList();
		tutoring.setEduExperience(JsonUtil.toJson(eduVo.getEduExperienceList()));
		tutoringService.updateTutoring(tutoring);
		return  DataResult.getSuccessDataResult(tutoring);
		
   }
	
	@ApiOperation(value = "工作经历更新")
	@PostMapping("updateWork")
	public DataResult<Tutoring> updateWork(@RequestHeader("token")String token,
			@RequestBody  WorkVo workVo) {
		Tutoring tutoring=tutoringService.queryTutoringByToken(token);
		tutoring.setWorkExperience(JsonUtil.toJson(workVo.getList()));
		tutoringService.updateTutoring(tutoring);
		return  DataResult.getSuccessDataResult(tutoring);
		
   }
	
	
}
