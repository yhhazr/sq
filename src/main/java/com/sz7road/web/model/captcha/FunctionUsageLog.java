package com.sz7road.web.model.captcha;

import java.util.Date;

public class FunctionUsageLog {

	private String identifierId;

	private String functionCode;

	private Date usageTime;

	/**
	 * @return the functionCode
	 */
	public String getFunctionCode() {
		return functionCode;
	}

	/**
	 * @param functionCode the functionCode to set
	 */
	public void setFunctionCode(String functionCode) {
		this.functionCode = functionCode;
	}

	/**
	 * @return the identifierId
	 */
	public String getIdentifierId() {
		return identifierId;
	}

	/**
	 * @param identifierId the identifierId to set
	 */
	public void setIdentifierId(String identifierId) {
		this.identifierId = identifierId;
	}

	/**
	 * @return the usageTime
	 */
	public Date getUsageTime() {
		return usageTime;
	}

	/**
	 * @param usageTime the usageTime to set
	 */
	public void setUsageTime(Date usageTime) {
		this.usageTime = usageTime;
	}
}
