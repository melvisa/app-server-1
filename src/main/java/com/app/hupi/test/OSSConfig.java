package com.app.hupi.test;

public class OSSConfig {

	private  String endpoint="https://oss-cn-qingdao.aliyuncs.com";      
    private  String accessKeyId="LTAI4FnHixH9ffmT2i3D9CtL";   
    private  String accessKeySecret="g3JfvahPWon5iWw8LxercdX5A8L4ZJ";   
    private  String bucketName="taochonghui";    
    private  String picLocation="yw/image/";
	public final String getEndpoint() {
		return endpoint;
	}
	public final void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}
	public final String getAccessKeyId() {
		return accessKeyId;
	}
	public final void setAccessKeyId(String accessKeyId) {
		this.accessKeyId = accessKeyId;
	}
	public final String getAccessKeySecret() {
		return accessKeySecret;
	}
	public final void setAccessKeySecret(String accessKeySecret) {
		this.accessKeySecret = accessKeySecret;
	}
	public final String getBucketName() {
		return bucketName;
	}
	public final void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}
	public final String getPicLocation() {
		return picLocation;
	}
	public final void setPicLocation(String picLocation) {
		this.picLocation = picLocation;
	}  
    
}
