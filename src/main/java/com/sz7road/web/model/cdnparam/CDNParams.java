/**
 * 
 */
package com.sz7road.web.model.cdnparam;


/**
 * CDN推送的参数
 * @author xin.fang
 * @createDate 2012-12-7 上午9:48:36
 */
public class CDNParams {
	private String[] urls; // cdn的urls参数
	private String[] dirs; // cdn的dirs参数
	private CDNCallback callback; // CDN的callback参数

	public class CDNCallback {
		private String url; // 返回的url
		private String[] email; // 反馈的邮箱地址
		private boolean acptNotice; // 是否反馈，仅在email有效时有效，若url和email没有值则不反馈

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String[] getEmail() {
			return email;
		}

		public void setEmail(String[] email) {
			this.email = email;
		}

		public boolean isAcptNotice() {
			return acptNotice;
		}

		public void setAcptNotice(boolean acptNotice) {
			this.acptNotice = acptNotice;
		}
	}

	public String[] getUrls() {
		return urls;
	}

	public void setUrls(String[] urls) {
		this.urls = urls;
	}

	public String[] getDirs() {
		return dirs;
	}

	public void setDirs(String[] dirs) {
		this.dirs = dirs;
	}

	public CDNCallback getCallback() {
		return callback;
	}

	public void setCallback(CDNCallback callback) {
		this.callback = callback;
	}

}
