package com.sz7road.web.model.onlineUser;


public class OnlineUser {
	
	private String respFlag;//平台接口连接返回状态 success  failure  invalid   error
	private int id;
	private String userName;
	private int status;	
	private String email;
	private String gender;
	private  String realName;
	private String icn;
	private long createTime;
	private int aggrRecharge;
	private String message;
	private String lastIp;
	private String lastLoginTime;
	private String lastGameId;
	private String lastGameZoneId;
	private String loginSum;
	private String site;
	private String birthday;
	private String city;
	private String career;
	private String safeLevel;
	private String mobile;
	private String messageCount;
	private String lastMessageTime;
	private String headDir;
	private int pswStrength;
	private String workPost;
	private String workEndYear;
	private String workStartYear;
	private String companyName;
	private String schoolName;
	private String startEduYear;
	private String schoolType;
	private String eduLevel;
	private String selfIntroduction;
	private String hobby;
	private String marryStatus;
	private String linkPhone;
	private String msn;
	private String qq;
	private String nickName;
	
	
	public String getRespFlag() {
		return respFlag;
	}
	public void setRespFlag(String respFlag) {
		this.respFlag = respFlag;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	public int getAggrRecharge() {
		return aggrRecharge;
	}
	public void setAggrRecharge(int aggrRecharge) {
		this.aggrRecharge = aggrRecharge;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getLastIp() {
		return lastIp;
	}
	public void setLastIp(String lastIp) {
		this.lastIp = lastIp;
	}
	
	public String getSite() {
		return site;
	}
	public void setSite(String site) {
		this.site = site;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCareer() {
		return career;
	}
	public void setCareer(String career) {
		this.career = career;
	}
	public String getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public String getLastGameId() {
		return lastGameId;
	}
	public void setLastGameId(String lastGameId) {
		this.lastGameId = lastGameId;
	}
	public String getLastGameZoneId() {
		return lastGameZoneId;
	}
	public void setLastGameZoneId(String lastGameZoneId) {
		this.lastGameZoneId = lastGameZoneId;
	}
	public String getLoginSum() {
		return loginSum;
	}
	public void setLoginSum(String loginSum) {
		this.loginSum = loginSum;
	}
	public String getSafeLevel() {
		return safeLevel;
	}
	public void setSafeLevel(String safeLevel) {
		this.safeLevel = safeLevel;
	}
	public String getIcn() {
		return icn;
	}
	public void setIcn(String icn) {
		this.icn = icn;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getMessageCount() {
		return messageCount;
	}
	public void setMessageCount(String messageCount) {
		this.messageCount = messageCount;
	}
	public String getLastMessageTime() {
		return lastMessageTime;
	}
	public void setLastMessageTime(String lastMessageTime) {
		this.lastMessageTime = lastMessageTime;
	}
	public String getHeadDir() {
		return headDir;
	}
	public void setHeadDir(String headDir) {
		this.headDir = headDir;
	}
	public int getPswStrength() {
		return pswStrength;
	}
	public void setPswStrength(int pswStrength) {
		this.pswStrength = pswStrength;
	}
	public String getWorkPost() {
		return workPost;
	}
	public void setWorkPost(String workPost) {
		this.workPost = workPost;
	}
	public String getWorkEndYear() {
		return workEndYear;
	}
	public void setWorkEndYear(String workEndYear) {
		this.workEndYear = workEndYear;
	}
	public String getWorkStartYear() {
		return workStartYear;
	}
	public void setWorkStartYear(String workStartYear) {
		this.workStartYear = workStartYear;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	public String getStartEduYear() {
		return startEduYear;
	}
	public void setStartEduYear(String startEduYear) {
		this.startEduYear = startEduYear;
	}
	public String getSchoolType() {
		return schoolType;
	}
	public void setSchoolType(String schoolType) {
		this.schoolType = schoolType;
	}
	public String getEduLevel() {
		return eduLevel;
	}
	public void setEduLevel(String eduLevel) {
		this.eduLevel = eduLevel;
	}
	public String getSelfIntroduction() {
		return selfIntroduction;
	}
	public void setSelfIntroduction(String selfIntroduction) {
		this.selfIntroduction = selfIntroduction;
	}
	public String getHobby() {
		return hobby;
	}
	public void setHobby(String hobby) {
		this.hobby = hobby;
	}
	public String getMarryStatus() {
		return marryStatus;
	}
	public void setMarryStatus(String marryStatus) {
		this.marryStatus = marryStatus;
	}
	public String getLinkPhone() {
		return linkPhone;
	}
	public void setLinkPhone(String linkPhone) {
		this.linkPhone = linkPhone;
	}
	public String getMsn() {
		return msn;
	}
	public void setMsn(String msn) {
		this.msn = msn;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
}
