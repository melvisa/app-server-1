package com.app.hupi.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.hupi.constant.Constant;
import com.app.hupi.domain.Appointment;
import com.app.hupi.domain.Code;
import com.app.hupi.domain.Demand;
import com.app.hupi.domain.Tutoring;
import com.app.hupi.exception.KiteException;
import com.app.hupi.mapper.AppointmentMapper;
import com.app.hupi.mapper.DemandMapper;
import com.app.hupi.mapper.TutoringMapper;
import com.app.hupi.service.CodeService;
import com.app.hupi.service.DemandService;
import com.app.hupi.util.BeanUtil;
import com.app.hupi.util.DateUtil;
import com.app.hupi.util.DoubleUtil;
import com.app.hupi.util.KiteUUID;
import com.app.hupi.util.ListUtil;
import com.app.hupi.util.LngAndLatUtils;
import com.app.hupi.util.WebUtil;
import com.app.hupi.vo.DemandAddVO;
import com.app.hupi.vo.DemandListVO;
import com.app.hupi.vo.LngAndLatVO;
import com.app.hupi.vo.UserVO;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.github.pagehelper.PageHelper;


@Service
public class DemandServiceImpl  implements DemandService{
	@Autowired
	private DemandMapper demandMapper;
	@Autowired
	private AppointmentMapper appointmentMapper;
	@Autowired
	private TutoringMapper tutoringMapper;
	@Autowired
	private  CodeService codeService;
	
	@Override
	public Demand addDemand(String employerId,DemandAddVO demandAddVO) {
		Demand demand=new Demand();
		BeanUtil.copyProperties(demandAddVO, demand);
		demand.setId(KiteUUID.getId());
		demand.setEmployerId(employerId);
		demand.setCreateTime(DateUtil.getFormatedDateTime());
		demand.setStatus("1");
		LngAndLatVO lngAndLatVO=LngAndLatUtils.getLngAndLat(demand.getAddress());
		demand.setLat(lngAndLatVO.getLat());
		demand.setLng(lngAndLatVO.getLng());
		demandMapper.insert(demand);
		return demand;
	}
	@Override
	public List<Demand> listDemandByEmployer(String employerId,int pageNum,int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		EntityWrapper<Demand> wrapper=new EntityWrapper<Demand>();
		wrapper.eq("employer_id", employerId);
		List<Demand> list= demandMapper.selectList(wrapper);
		List<Code> codeList=codeService.listCodeByGroup("tutoring_type");
		for(Demand d:list) {
			String className=d.getClassName();
			d.setClassName(changeClassName(className,codeList));
		// tutoring_type_gaozhong_yuwen,tutoring_type_gaozhong_yingyu,tutoring_type_gaozhong_shengwu,tutoring_type_gaozhong_shuxue
			String subs=d.getSubs();
			String[] subList=subs.split(",");
			String str="";
			for(String s:subList) {
				String clasName=s.substring(0, s.lastIndexOf("_"));
				String s2=codeService.queryCodeValueByGroupAndValue(clasName, s).split(",")[0];
				str=str+s2;
			}
			d.setSubs(str);
		}
		
	   return list;
	}
	@Override
	public Demand udpateDemand(Demand demand) {
		demandMapper.updateById(demand);
		return demand;
	}
	@Override
	public List<DemandListVO> listDemandByTutoring(String tutoringId,String lng,String lat,
			int pageNum,int pageSize) {
		List<DemandListVO> demandListVO=new ArrayList<>();
		List<Demand> totalDemand=new ArrayList<>();
		// 先查询被预约的
		List<String> demandIds=new ArrayList<>();
		if(pageNum==1) {
			EntityWrapper<Appointment> wrapper=new EntityWrapper<Appointment>();
			wrapper.eq("tutoring_id", tutoringId);
			List<Appointment> appointmentList=appointmentMapper.selectList(wrapper);
			
			for(Appointment appointment:appointmentList) {
				demandIds.add(appointment.getDemandId());
			}
			if(ListUtil.isNotEmpty(demandIds)) {
				List<Demand> appointDemand=demandMapper.selectBatchIds(demandIds);
				totalDemand.addAll(appointDemand);
			}
		}
		
		Tutoring tutoring=tutoringMapper.selectById(tutoringId);
		String tutoringType=tutoring.getTutoringType();
	
		String [] types=null;
		if(tutoringType.contains(Constant.STRING_SEPARATOR)) {
			types=tutoringType.split(Constant.STRING_SEPARATOR);
		}
		else {
			types=new String[1];
			types[0]=tutoringType;
		}
		EntityWrapper<Demand> wrapper2=new EntityWrapper<Demand>();
		for(int i=0;i<types.length;i++) {
			String s=types[i];
			if(i==0) {
				wrapper2.like("subs", s);
			}
			else {
				wrapper2.or().like("subs", s);
			}
		}
		if(ListUtil.isNotEmpty(demandIds)) {
			wrapper2.notIn("id", demandIds);
		}
		PageHelper.startPage(pageNum, pageSize);
		List<Demand> demandList=demandMapper.selectList(wrapper2);
		totalDemand.addAll(demandList);
		
		demandListVO=BeanUtil.copyPropsForList(totalDemand, DemandListVO.class);
		// 距离计算
		for(DemandListVO vo:demandListVO) {
			vo.setDistance(
					getDistance(Double.parseDouble(lat),Double.parseDouble(lng)
					,Double.parseDouble(vo.getLat()),Double.parseDouble(vo.getLng())));
		}
		// className  subs转换
		List<Code> codeList=codeService.listCodeByGroup("tutoring_type");
		for(DemandListVO vo:demandListVO) {
			String className=vo.getClassName();
			vo.setClassName(changeClassName(className,codeList));
		// tutoring_type_gaozhong_yuwen,tutoring_type_gaozhong_yingyu,tutoring_type_gaozhong_shengwu,tutoring_type_gaozhong_shuxue
			String subs=vo.getSubs();
			String[] subList=subs.split(",");
			String str="";
			for(String s:subList) {
				String clasName=s.substring(0, s.lastIndexOf("_"));
				String s2=codeService.queryCodeValueByGroupAndValue(clasName, s).split(",")[0];
				str=str+s2;
			}
			vo.setSubs(str);
		}
		
		
		return demandListVO;
	}
	
	public  String getDistance(Double lat1,Double lng1,Double lat2,Double lng2) {
        double radiansAX = Math.toRadians(lng1); // A经弧度
        double radiansAY = Math.toRadians(lat1); // A纬弧度
        double radiansBX = Math.toRadians(lng2); // B经弧度
        double radiansBY = Math.toRadians(lat2); // B纬弧度
        double cos = Math.cos(radiansAY) * Math.cos(radiansBY) * Math.cos(radiansAX - radiansBX)
            + Math.sin(radiansAY) * Math.sin(radiansBY);
        double acos = Math.acos(cos); // 反余弦值
        double result= 6371393 * acos; // 最终结果
        return Math.floor((result/1000)) +"km";
	}

  public static void main(String[] args) {
	String s="tutoring_type_gaozhong_yuwen";
	System.out.println(s.lastIndexOf("_"));
	System.out.println(s.substring(s.lastIndexOf("_")+1));
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
