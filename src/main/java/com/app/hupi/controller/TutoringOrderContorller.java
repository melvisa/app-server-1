package com.app.hupi.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.hupi.constant.DataResult;
import com.app.hupi.domain.Code;
import com.app.hupi.domain.Demand;
import com.app.hupi.domain.Tutoring;
import com.app.hupi.domain.TutoringOrder;
import com.app.hupi.mapper.DemandMapper;
import com.app.hupi.mapper.EmployerMapper;
import com.app.hupi.mapper.TutoringMapper;
import com.app.hupi.service.CodeService;
import com.app.hupi.service.CommentService;
import com.app.hupi.service.EmployerService;
import com.app.hupi.service.TutoringOrderService;
import com.app.hupi.service.TutoringService;
import com.app.hupi.util.UserUtil;
import com.app.hupi.util.WebUtil;
import com.app.hupi.vo.DeliveryResumeVO;
import com.app.hupi.vo.DemandListVO;
import com.app.hupi.vo.UserVO;
import com.github.pagehelper.PageInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RequestMapping("/tutoringOrder")
@Api(tags = {"家教模块"})
@RestController
public class TutoringOrderContorller {
	@Autowired
	private EmployerService  employerService;

	@Autowired
	private TutoringOrderService tutoringOrderService;
	@Autowired
	private CommentService commentService;
	@Autowired
	private TutoringMapper tutoringMapper;
	@Autowired
	private EmployerMapper employerMapper;
	@Autowired
	private DemandMapper demandMapper;
	@Autowired
	private CodeService codeService;
	@Autowired
	private TutoringService tutoringService;
	
	
//	@ApiOperation(value = "查询家教订单列表")
//	@GetMapping("/pageTutoringOrder")
//	@ApiImplicitParams({
//	        @ApiImplicitParam(name = "pageNum", value = "第几页", required = true, dataType = "int"),
//	        @ApiImplicitParam(name = "pageSize", value = "每页记录数", required = true, dataType = "int"),
//	        @ApiImplicitParam(name = "status", value = "状态", required = true, dataType = "String")
//	 })
//	public DataResult<List<Demand>> pageTutoringOrder(
//			@RequestParam(name="pageNum",required=true)int pageNum,
//			@RequestParam(name="pageSize",required=true)int pageSize,
//		   String tutoringId,String status) {
//		PageInfo<TutoringOrder>  pageInfo=tutoringOrderService.pageInfoOrderWithTutoring(pageNum, pageSize, tutoringId, status);
//		List<Demand> demandList=new ArrayList<>();
//		List<Code> codeList=codeService.listCodeByGroup("tutoring_type");
//		for(TutoringOrder t:list) {
//			Demand d=demandMapper.selectById(t.getDemandId());
//				String className=d.getClassName();
//				d.setClassName(changeClassName(className,codeList));
//				String subs=d.getSubs();
//				String[] subList=subs.split(",");
//				String str="";
//				for(String s:subList) {
//					String clasName=s.substring(0, s.lastIndexOf("_"));
//					String s2=codeService.queryCodeValueByGroupAndValue(clasName, s).split(",")[0];
//					str=str+s2;
//				}
//				d.setSubs(str);
//				demandList.add(d);
//			}
//		return DataResult.getSuccessDataResult(demandList);
//	}
//	
//	
	/**
	 * 
	 * @param demandId
	 * @return
	 *  如果需要诚意金 则需要先抵扣用户抵扣券或者先支付诚意金
	 */
	@ApiOperation(value = "投递简历")
	@PostMapping("/deliveryResume")
	public DataResult<DeliveryResumeVO> deliveryResume(	@RequestHeader("token")String token,@RequestParam String demandId) {
		Tutoring tutoring=tutoringService.queryTutoringByToken(token);
		DeliveryResumeVO deliveryResumeVO=tutoringService.deliveryResume(tutoring.getId(), demandId);
		return DataResult.getSuccessDataResult(deliveryResumeVO);
	}
	
	@ApiOperation(value = "家教查询订单列表")
	@GetMapping("/listTutoringOrder")
	@ApiImplicitParams({
	        @ApiImplicitParam(name = "pageNum", value = "第几页", required = true, dataType = "int"),
	        @ApiImplicitParam(name = "pageSize", value = "每页记录数", required = true, dataType = "int"),
	        @ApiImplicitParam(name = "status", value = "状态", required = true, dataType = "String")
	 })
	public DataResult<List<Demand>> listTutoringOrder(
			@RequestHeader("token")String token,
			@RequestParam(name="pageNum",required=true)int pageNum,
			@RequestParam(name="pageSize",required=true)int pageSize,
			@RequestParam(name="status",required=true)String status) {
		Tutoring tutoring=tutoringService.queryTutoringByToken(token);
		List<TutoringOrder> list=tutoringOrderService.listTutoringOrderWithTutoring(pageNum, pageSize, tutoring.getId(), status);
		List<Demand> demandList=new ArrayList<>();
		List<Code> codeList=codeService.listCodeByGroup("tutoring_type");
		for(TutoringOrder t:list) {
			Demand d=demandMapper.selectById(t.getDemandId());
				String className=d.getClassName();
				d.setClassName(changeClassName(className,codeList));
				String subs=d.getSubs();
				String[] subList=subs.split(",");
				String str="";
				for(String s:subList) {
					String clasName=s.substring(0, s.lastIndexOf("_"));
					String s2=codeService.queryCodeValueByGroupAndValue(clasName, s).split(",")[0];
					str=str+s2;
				}
				d.setSubs(str);
				demandList.add(d);
			}
		return DataResult.getSuccessDataResult(demandList);
	}
	
		private String changeClassName(String className,List<Code> list) {
			for(Code  code:list) {
				if(code.getValue().equals(className)) {
					return code.getName().split(",")[0];
				}
			}
			return "";
		}
			
	
}
