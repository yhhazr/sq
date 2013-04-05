/**
 * 
 */
package com.sz7road.web.model.cdnparam;

/**
 * @author xin.fang
 * @createDate 2012-12-10 下午4:56:53
 */
public class UrlState {
	private String url;
	private String code;

	public UrlState() {
	}

	public UrlState(String url, String code) {
		this.url = url;
		this.code = code;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
