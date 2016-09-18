package ms.luna.biz.dao.custom.model;

import java.sql.Timestamp;

public class MsShowAppResult {
	
	private int appId;
	private String appName;
	private String appCode;
	private int type;
	private String appAddr;
	private Timestamp registhhmmss;
	private Timestamp uphhmmss;
	private String owner;
	private int appStatus;
	private int businessId;
	private String businessName;

	// 根据类别获取微景展接口 添加属性
	private String note;
	private String picThumb;
	private String categoryId; // 类别id
	private String categoryName; // 类别名称
	private Timestamp publishTime;
	public int getAppId() {
		return appId;
	}

	public void setAppId(int appId) {
		this.appId = appId;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getAppCode() {
		return appCode;
	}
	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}

	public String getAppAddr() {
		return appAddr;
	}

	public void setAppAddr(String appAddr) {
		this.appAddr = appAddr;
	}

	public Timestamp getRegisthhmmss() {
		return registhhmmss;
	}

	public void setRegisthhmmss(Timestamp registhhmmss) {
		this.registhhmmss = registhhmmss;
	}

	public Timestamp getUphhmmss() {
		return uphhmmss;
	}

	public void setUphhmmss(Timestamp uphhmmss) {
		this.uphhmmss = uphhmmss;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public int getAppStatus() {
		return appStatus;
	}
	public void setAppStatus(int appStatus) {
		this.appStatus = appStatus;
	}
	public int getBusinessId() {
		return businessId;
	}
	public void setBusinessId(int businessId) {
		this.businessId = businessId;
	}
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public Timestamp getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Timestamp publishTime) {
		this.publishTime = publishTime;
	}
	public String getPicThumb() {
		return picThumb;
	}

	public void setPicThumb(String picThumb) {
		this.picThumb = picThumb;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
}
