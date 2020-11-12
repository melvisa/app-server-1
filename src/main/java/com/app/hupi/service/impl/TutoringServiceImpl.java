package com.app.hupi.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.hupi.constant.Constant;
import com.app.hupi.domain.Demand;
import com.app.hupi.domain.Tutoring;
import com.app.hupi.domain.TutoringOrder;
import com.app.hupi.exception.KiteException;
import com.app.hupi.mapper.DemandMapper;
import com.app.hupi.mapper.TutoringMapper;
import com.app.hupi.mapper.TutoringOrderMapper;
import com.app.hupi.service.CodeService;
import com.app.hupi.service.TutoringService;
import com.app.hupi.util.BeanUtil;
import com.app.hupi.util.DateUtil;
import com.app.hupi.util.JsonUtil;
import com.app.hupi.util.KiteUUID;
import com.app.hupi.util.MapUtil;
import com.app.hupi.util.StringUtil;
import com.app.hupi.util.WebUtil;
import com.app.hupi.vo.DeliveryResumeVO;
import com.app.hupi.vo.EduExperienceVO;
import com.app.hupi.vo.TutoringAddVO;
import com.app.hupi.vo.TutoringDetailVO;
import com.app.hupi.vo.TutoringListVO;
import com.app.hupi.vo.TutoringRegisterVO;
import com.app.hupi.vo.UserVO;
import com.app.hupi.vo.WorkExperienceVO;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.github.pagehelper.PageHelper;


@Service
public class TutoringServiceImpl implements TutoringService {

	@Autowired
	private TutoringMapper tutoringMapper;
	@Autowired
	private CodeService codeService;
	@Autowired
	private DemandMapper demandMapper;
	@Autowired
	private TutoringOrderMapper tutoringOrderMapper;
	
	/**
	 * 投递简历
	 *  1、首先判断是否需要交纳意向金、如果不需要 则直接生成订单
	 *  2、如果需要、则需先进行抵扣券抵扣或者支付
	 */
	@Override
	@Transactional
	public DeliveryResumeVO deliveryResume(String tutoringId,String demandId) {
		DeliveryResumeVO  vo=new DeliveryResumeVO();
		Demand demand=demandMapper.selectById(demandId);
		if(demand==null) {
			KiteException.throwException("数据异常，查不到对应记录");
		}
		TutoringOrder tutoringOrder=new TutoringOrder();
		tutoringOrder.setId(KiteUUID.getId());
		tutoringOrder.setTutoringId(tutoringId);
		tutoringOrder.setDemandId(demandId);
		tutoringOrder.setEmployerId(demand.getEmployerId());
		tutoringOrder.setOrderId("resume"+KiteUUID.getId());
		tutoringOrder.setEarnestFlag(demand.getEarnestFlag());
		tutoringOrder.setCreateTime(DateUtil.getFormatedDateTime());
		String desc="";
		String payFlag="0";
		// 不需要诚意金
		if(demand.getEarnestFlag()=="0") {
			desc="不需要诚意金,直接生成订单";
			tutoringOrder.setStatus("待确认");
			tutoringOrder.setPayFlag("0");
			tutoringOrder.setDesc(desc);
		}
		// 需要诚意金
		else {
			// 先抵扣抵扣券 再抵扣账户余额  再进行支付
			Tutoring tutoring=tutoringMapper.selectById(tutoringId);
			int coupon= tutoring.getCoupon();
			if(coupon>=1000) {
				coupon=coupon-1000;
				tutoringMapper.updateById(tutoring);
				tutoringOrder.setStatus("待确认");
				tutoringOrder.setPayFlag("0");
				desc="需要诚意金,已进行抵扣券抵扣";
				tutoringOrder.setDesc(desc);
			}
			else {
				int totalAccount=tutoring.getTotalAccount();
				if(totalAccount>=1000) {
					totalAccount=totalAccount-1000;
					tutoringMapper.updateById(tutoring);
					tutoringOrder.setStatus("待确认");
					tutoringOrder.setPayFlag("0");
					desc="需要诚意金,已进行账户余额抵扣";
					tutoringOrder.setDesc(desc);
				}
				// 抵扣券账户余额都不够抵扣，需要进行支付
				else {
					tutoringOrder.setStatus("待支付");
					tutoringOrder.setPayFlag("1");
					desc="需要诚意金,需要支付";
					payFlag="1";
					tutoringOrder.setDesc(desc);
				}
			}
		}
		tutoringOrderMapper.insert(tutoringOrder);
		tutoringOrder=tutoringOrderMapper.selectById(tutoringOrder.getId());
		vo.setDesc(desc);
		vo.setOrderId(tutoringOrder.getOrderId());
		vo.setPayFlag(payFlag);
		return vo;
	}
	
	
	
	@Override
	public Tutoring addTutoring(TutoringAddVO tutoringAddVO) {
		UserVO userVO=(UserVO) WebUtil.getSession().getAttribute("user");
		if(userVO==null) {
			KiteException.throwException("登录信息失效，请重新登录");
		}
		String number=userVO.getNumber();
		Tutoring t=queryTutoringByNumber(number);
		BeanUtil.copyProperties(tutoringAddVO, t);
		t.setEduExperience(JsonUtil.toJson(tutoringAddVO.getEduExperience()));
		t.setWorkExperience(JsonUtil.toJson(tutoringAddVO.getWorkExperienceList()));
		tutoringMapper.updateById(t);
		return t;
	}
	@Override
	public Tutoring queryTutoringByNumber(String number) {
		Tutoring tutoring=new Tutoring();
		tutoring.setNumber(number);
		tutoring=tutoringMapper.selectOne(tutoring);
		return tutoring;
	}
	@Override
	public Tutoring register(TutoringRegisterVO tutoringRegisterVO) {
		Tutoring  t = queryTutoringByNumber(tutoringRegisterVO.getNumber());
		if(t!=null) {
			KiteException.throwException("号码已注册、请直接登录");
		}
	    t=new Tutoring();
		BeanUtil.copyProperties(tutoringRegisterVO, t);
		t.setId(KiteUUID.getId());
		t.setCreateTime(DateUtil.getFormatedDateTime());
		t.setYqmSelf(KiteUUID.randomString(6));
		tutoringMapper.insert(t);
		return t;
	}
	@Override
	public List<TutoringListVO> listTutoringList(String employerId, String tutoringType,String lng, String lat, int pageIndex,
			int pageSize) {
		PageHelper.startPage(pageIndex, pageSize);
		String sqlSelect="id, name ,head_image as headImage ,"
				+ "tutoring_type as tutoringType , auth_info  as authInfo,"
				+ "tutoring_identity as tutoringIdentity,"
				+ "teache_time as teacheTime,sex ,age,lng,lat,cert_flag as certFlag,cast((st_distance(point(lng,lat),point("+lng+","+lat+"))*111195/1000 )AS CHAR) as distance ";
		EntityWrapper<Tutoring> wrapper=new EntityWrapper<Tutoring>();
		wrapper.setSqlSelect(sqlSelect);
		wrapper.like("tutoring_type", tutoringType);
		wrapper.orderBy("distance");
		List<TutoringListVO> result=new ArrayList<>();
		List<Map<String, Object>> list=tutoringMapper.selectMaps(wrapper);
		for(Map<String, Object> map:list) {
			TutoringListVO vo=MapUtil.mapToBean(map, TutoringListVO.class);
			List<String>subs=changeSubjectList(MapUtil.getString(map, "tutoringType"));
			vo.setSubjectList(subs);
			if(vo.getAuthInfo()==null) {
				vo.setAuthInfo("未实名认证");
			}
			else {
				vo.setAuthInfo("已实名认证");
			}
			vo.setTutoringIdentity(codeService.queryCodeValueByGroupAndValue("tutoring_identity", vo.getTutoringIdentity()));
			result.add(vo);
		}
		return result;
	}
	


	private List<String> changeSubjectList(String tutoringType){
		List<String> list=new ArrayList<>();
		if(StringUtil.isEmpty(tutoringType)) {
			return list;
		}
		String[] temp= {};
		if(tutoringType.contains(Constant.STRING_SEPARATOR)) {
			temp=tutoringType.split(Constant.STRING_SEPARATOR);
		}
		else {
			temp=new String[1];
			temp[0]=tutoringType;
		}
		for(String s:temp) {
			s=s.replace("tutoring_type_", "");
			String[] ss=s.split("_");
			String tutoring_type=codeService.queryCodeValueByGroupAndValue("tutoring_type", "tutoring_type_"+ss[0]);
			String sub=codeService.queryCodeValueByGroupAndValue("tutoring_type_"+ss[0], "tutoring_type_"+ss[0]+"_"+ss[1]);
			list.add(tutoring_type+sub);
		}
		return list;
	}
	@Override
	public TutoringDetailVO queryTutoringDetail(String id) {
		Tutoring tutoring=tutoringMapper.selectById(id);
		if(tutoring==null) {
			return null;
		}
		TutoringDetailVO vo=new TutoringDetailVO();
		BeanUtil.copyProperties(tutoring, vo);
		// 工作经历
		List<WorkExperienceVO> workExperienceList=JsonUtil.parseArray(tutoring.getWorkExperience(), WorkExperienceVO.class);
		// 教育经历
		EduExperienceVO eduExperience=JsonUtil.parseObject(tutoring.getEduExperience(), EduExperienceVO.class);
		vo.setWorkExperienceList(workExperienceList);
		vo.setEduExperience(eduExperience);
		// 证书
		if(tutoring.getAuthInfo()==null) {
			vo.setAuthInfo("未实名认证");
		}
		else {
			vo.setAuthInfo("已实名认证");
		}
		vo.setTutoringIdentity(codeService.queryCodeValueByGroupAndValue("tutoring_identity", tutoring.getTutoringIdentity()));
		return vo;
	}
	@Override
	public Tutoring updateTutoring(Tutoring tutoring) {
		tutoringMapper.updateById(tutoring);
		return tutoring;
	}



	@Override
	public Tutoring queryTutoringByToken(String token) {
		// TODO Auto-generated method stub
		Tutoring tutoring=new Tutoring();
		tutoring.setToken(token);
		tutoring =tutoringMapper.selectOne(tutoring);
		if(tutoring!=null) {
			String tokenTime=tutoring.getTokenTime();
			// 如果当前时间和tokenTime相差6小时、则token失效
			Date minDate=DateUtil.parseStrToDate(tokenTime, DateUtil.DATE_TIME_FORMAT);
			Date maxDate=new Date();
			long subTime=maxDate.getTime()-minDate.getTime();
			if(subTime>6*60*3600*1000) {
				KiteException.throwException("登录已失效。请重新登录");
			}
			//如果没有失效  则更新tokenTime
			tutoring.setTokenTime(DateUtil.getFormatedDateTime());
			tutoringMapper.updateById(tutoring);
		}
		return tutoring;
	}
	
	
}
