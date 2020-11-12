package com.app.hupi.service;

import com.app.hupi.domain.Employer;
import com.github.pagehelper.PageInfo;

public interface EmployerService {

	Employer queryEmployerByToken(String token);
	Employer queryEmployerByNumber(String number);
	
	Employer addEmployer(Employer employer);
	
	Employer queryEmployerById(String id);
	
	Employer updateEmployer(Employer employer);
	
	PageInfo<Employer>  queryPageInfo(int pageIndex,int pageSize,String searchValue);
	
}
