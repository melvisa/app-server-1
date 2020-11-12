package com.app.hupi.domain;

import com.gitee.sunchenbin.mybatis.actable.annotation.Column;

import lombok.Data;

@Data
public class Admin {
	private String id;
	private String username;
	private String password;
	private String name;
	private String time;
	@Column(name="is_del",defaultValue="否")
	private String isDel;
}
