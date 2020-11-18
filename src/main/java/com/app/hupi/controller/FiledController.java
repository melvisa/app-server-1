package com.app.hupi.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RequestMapping("/file")
@Api(tags = { "文件上传" })
@RestController
public class FiledController {

	@PostMapping("/fileUpload")
	@ApiOperation(value = "文件上传 返回文件路径", notes = "文件上传 返回文件路径")
	public String fileUpload(@RequestParam(value = "file", required = true) MultipartFile fileImage,
			@RequestParam(value = "type", required = true) String type,HttpServletRequest req) throws IOException {
		String path = System.getProperty("catalina.home") +File.separator+"webapps";
		String secondPath = "";
		switch (type) {
		case "P":
			secondPath =  File.separator + "images"+File.separator + "product" + File.separator;
			break;
		case "C":
			secondPath = File.separator + "images"+File.separator + "carousel" + File.separator;
			break;
		default:
			secondPath = File.separator + "images"+ File.separator + "others" + File.separator;
		}
		File fileDir = new File(path + secondPath);
		// 1.判断文件夹是否存在
		if (!fileDir.exists()) {
			// 不存在的话,创建文件夹
			fileDir.mkdirs();
		}
		String tampName = fileImage.getOriginalFilename();
		int begin = tampName.lastIndexOf(".");
		int last = tampName.length();
		String fileType = tampName.substring(begin, last);
		String fileName = System.currentTimeMillis() + fileType;
		// 实现文件的上传
		path = path + secondPath + fileName;
		fileImage.transferTo(new File(path));
		// TODO  需要替换
		return path;
	}

}
