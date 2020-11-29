package com.app.hupi.service;

import com.app.hupi.domain.Employer;
import com.app.hupi.vo.EmployerCmsVo;
import com.github.pagehelper.PageInfo;

public interface EmployerService {

	
	
	
	PageInfo<EmployerCmsVo>pageInfo(int pageNum,int pageSize,String name,String number);
	
	Employer queryEmployerByToken(String token);
	Employer queryEmployerByNumber(String number);
	
	Employer queryEmployerByUnicode(String unicode);
	
	Employer addEmployer(Employer employer);
	
	Employer queryEmployerById(String id);
	
	Employer updateEmployer(Employer employer);
	
	PageInfo<Employer>  queryPageInfo(int pageIndex,int pageSize,String searchValue);
	
}
