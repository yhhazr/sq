/**
 * 
 */
package com.sz7road.web.model.cdnparam;

import java.util.List;

/**
 * CDN返回的参数
 * 
 * @author xin.fang
 * @createDate 2012-12-7 上午11:22:16
 */
public class CDNReturnValue {
	// 成功率，1为100%
	private double successRate;
	// 刷新所耗费的总时间，单位是秒
	private int totalTime;
	// 刷新请求id
	private String r_id;
	// 返回的状态UNKNOW 处理中、SUCCESS 成功、 FAIL 失败
	private String status;
	// 用户名
	private String username;
	// 请求创建时间
	private String createdTime;
	// 刷新完成时间
	private String finishedTime;
	// 每个url的状态 200 刷新成功,400无效的URL，该URL不属于当前用户,503该URL刷新失败
	// 同一个url的code同时为200和503，表示该url在某些设备上刷新失败
	private List<UrlState> urlStatus;

	public double getSuccessRate() {
		return successRate;
	}

	public void setSuccessRate(double successRate) {
		this.successRate = successRate;
	}

	public int getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(int totalTime) {
		this.totalTime = totalTime;
	}

	public String getR_id() {
		return r_id;
	}

	public void setR_id(String r_id) {
		this.r_id = r_id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}

	public String getFinishedTime() {
		return finishedTime;
	}

	public void setFinishedTime(String finishedTime) {
		this.finishedTime = finishedTime;
	}

	public List<UrlState> getUrlStatus() {
		return urlStatus;
	}

	public void setUrlStatus(List<UrlState> urlStatus) {
		this.urlStatus = urlStatus;
	}

}
