package ms.luna.biz.dao.custom.model;

import java.sql.Timestamp;

public class MsShowAppResult {
	
	private int appId;
	private String appName;
	private String appCode;
	private Timestamp registhhmmss;
	private Timestamp uphhmmss;
	private String owner;
	private int appStatus;
	private int businessId;
	private String businessName;
	
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

}
