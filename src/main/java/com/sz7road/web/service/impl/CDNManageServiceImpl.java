package com.sz7road.web.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;

import com.sz7road.web.common.util.AppConstants;
import com.sz7road.web.common.util.HttpClientUtil;
import com.sz7road.web.model.cdnparam.CDNParams;
import com.sz7road.web.model.cdnparam.CDNReturnValue;
import com.sz7road.web.model.cdnparam.UrlState;
import com.sz7road.web.service.CDNManageService;

public class CDNManageServiceImpl implements CDNManageService {
	private Logger logger = Logger.getLogger(CDNManageServiceImpl.class);
	// 请求CDN返回的次数，若为10则停止CDN查询请求
	private int resendTimes;

	@Override
	public void cdnPost() {
		// 构造CDNParams对象
		CDNParams params = new CDNParams();
		CDNParams.CDNCallback callback = params.new CDNCallback();
		callback.setUrl(AppConstants.CDN_CALLBACK_URL);
		callback.setEmail(AppConstants.CDN_CALLBCXK_EMAIL);
		callback.setAcptNotice(AppConstants.CDN_ACPTNOTICE);

		params.setUrls(AppConstants.CDN_URLS);
		params.setDirs(AppConstants.CDN_DIRS);
		params.setCallback(callback);
		// 调用方法刷新CDN
		String result = this.cdnPost(params);
		// 调用查询接口对CDN推送的结果进行查询,等待CDN结束
		cdnFinish(result);
		// CDN 刷新结束重置resendTimes
		resendTimes = 0;
	}

	/**
	 * 对CDN推送的结果进行查询查看是否是重发、等待、成功完成状态
	 * 
	 * @author xin.fang
	 * @param result
	 * @createDate 2012-12-13 下午5:30:18
	 */
	private void cdnFinish(String result) {
		if (StringUtils.isNotBlank(result)) {
			String callbackResult = this.callback(result);
			// 根据CDN查询结果的值判断是否重新发送刷新CDN请求
			if (callbackResult.equalsIgnoreCase("resend")
					|| callbackResult.equalsIgnoreCase("wait")) {
				boolean isRun = true;
				while (isRun) {
					if (resendTimes == 10) {
						isRun = false;
					}
					try {
						Thread.sleep(60000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					String returnValue = this.callback(result);
					// 如果返回值为success即所有页面刷新成功则跳出循环
					if (returnValue.equalsIgnoreCase("success")) {
						isRun = false;
					}
					resendTimes++;
					logger.info("resendTimes: " + resendTimes);
				}
			}
		}
	}

	/**
	 * CDN返回查询结果
	 * 
	 * @author xin.fang
	 * @param result
	 * @createDate 2012-12-7 上午11:51:44
	 */
	private String callback(String result) {
		CDNReturnValue value = this.cdnReturnValue(result);
		// 判断返回的CDNReturnValue是否为空
		if (null == value) {
			logger.info("CDN return value is null");
			return "resend";
		}
		// 判断CDN返回的查询结果status的值
		if (value.getStatus().equalsIgnoreCase("UNKNOWN")) {
			return "wait";
		}
		if (value.getStatus().equalsIgnoreCase("FAIL")) {
			if (value.getSuccessRate() != 1) {
				if (resendTimes != 10) {
					this.cdnPostResend(value);
					resendTimes++;
				} else {
					logger.error("CDN resend fail");
				}
			}
		}
		//CDN执行完毕
		logger.error("CDN all finished");
		return "success";
	}

	/**
	 * 返回一个CDNReturnValue对象，cdn查询返回的值
	 * 
	 * @author xin.fang
	 * @param result
	 * @return
	 * @createDate 2012-12-10 下午2:31:07
	 */
	private CDNReturnValue cdnReturnValue(String result) {
		// 获取r_id
		String r_id = null;
		if (result != null) {
			int length = result.indexOf(":");
			if (length != -1) {
				r_id = result.substring(length + 1, result.length() - 1).trim()
						.replaceAll("\"", "");
			} else {
				logger.error("post return value is fail");
			}
		}
		// 构造查询cdn的参数
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("username", AppConstants.CDN_USERNAME));
		pairs.add(new BasicNameValuePair("password", AppConstants.CDN_PASSWORD));
		String returnValue = "";
		try {
			returnValue = HttpClientUtil.get(
					AppConstants.CDN_SELECT_URL.replace("{r_id}", r_id), pairs);
		} catch (Exception e) {
			logger.error("CDN return fail");
			e.printStackTrace();
		}
		if (StringUtils.isBlank(returnValue)) {
			logger.error("CDN return value fail");
			return null;
		}
		logger.info("CDN return success");
		logger.info(returnValue);
		JSONObject object = JSONObject.fromObject(returnValue);
		CDNReturnValue value = null;
		if (null != object) {
			Map<String, Class<?>> map = new HashMap<String, Class<?>>();
			map.put("urlStatus", UrlState.class);
			value = (CDNReturnValue) JSONObject.toBean(object,
					CDNReturnValue.class, map);
		}
		return value;
	}

	/**
	 * 如果CDN刷新失败，将刷新失败的url重新刷新一遍
	 * 
	 * @author xin.fang
	 * @createDate 2012-12-7 上午11:58:18
	 */
	private void cdnPostResend(CDNReturnValue value) {
		List<UrlState> urlStatus = value.getUrlStatus();
		List<String> urls = new ArrayList<String>();
		List<String> dirs = new ArrayList<String>();
		// 取出所有的刷新失败的url和dir
		for (UrlState urlState : urlStatus) {
			if (!urlState.getCode().equals("200")) {
				if (urlState.getUrl().endsWith("/")) {
					dirs.add(urlState.getUrl());
				} else {
					urls.add(urlState.getUrl());
				}
			}
		}
		CDNParams params = new CDNParams();
		CDNParams.CDNCallback callback = params.new CDNCallback();
		callback.setUrl(AppConstants.CDN_CALLBACK_URL);
		callback.setEmail(AppConstants.CDN_CALLBCXK_EMAIL);
		callback.setAcptNotice(true);

		params.setUrls((String[]) urls.toArray());
		params.setDirs((String[]) dirs.toArray());
		params.setCallback(callback);

		this.cdnFinish(this.cdnPost(params));
	}

	/**
	 * 发送CDNpost请求
	 * 
	 * @author xin.fang
	 * @param params
	 * @return
	 * @createDate 2012-12-7 上午11:50:13
	 */
	private String cdnPost(CDNParams params) {
		// 将CDNParams转换成json字符串
		JSONObject jsonObject = JSONObject.fromObject(params);
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("username", AppConstants.CDN_USERNAME));
		pairs.add(new BasicNameValuePair("password", AppConstants.CDN_PASSWORD));
		pairs.add(new BasicNameValuePair("task", jsonObject.toString()));
		String result = null;
		try {
			result = HttpClientUtil.post(AppConstants.CDN_POST_URL, pairs);
			logger.info("CDN transmit success result:" + result);
		} catch (Exception e) {
			logger.error("CDN transmit fail");
			e.printStackTrace();
		}
		// 判断CDN Post请求是否为空或者空字符，若是则重新请求CDN post
		if (StringUtils.isBlank(result)) {
			try {
				// 先线程睡眠10s
				Thread.sleep(10000);
				logger.info("CDN post value is null");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (resendTimes != 10) {
				result = this.cdnPost(params);
				resendTimes++;
			}
		}
		return result;
	}
}
