/**
 * Copyright  2013-3-29 第七大道-技术支持部-网站开发组
 * 自主运营平台WEB 下午5:01:06
 * 版本号： v1.0
*/

package com.sz7road.web.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.protocol.HTTP;
import org.apache.log4j.Logger;

import com.sz7road.web.common.listener.SystemConfig;

/**
 * 类描述：
 * 创建者： xin.fang
 * 项目名称： game_7road
 * 创建时间： 2013-3-29 下午5:01:06
 * 版本号： v1.0
*/
public class MoblieSendMsgUtil {
    private Logger logger = Logger.getLogger(MoblieSendMsgUtil.class);

    /**
     * 创建时间： 2013-3-29 下午5:10:04
     * 创建人：xin.fang
     * 参数： 
     * 返回值： String
     * 方法描述 :手机发送信息
    */
    public static String sendMessage(String phoneNum, String content) {
        String sn = SystemConfig.getProperty("sendMsg.sn");
        String key = SystemConfig.getProperty("sendMsg.key");
        String url = SystemConfig.getProperty("sendMsg.url");

        sn = StringUtils.isBlank(sn) ? "SDK-666-010-01745" : sn;
        key = StringUtils.isBlank(key) ? "399524" : key;
        url = StringUtils.isBlank(url) ? "http://sdk2.entinfo.cn/z_mdsmssend.aspx" : url;

        String tmp = sn + key.toUpperCase();
        String pwd = MD5Util.getMD5String(tmp).toUpperCase();
        String response = null;
        try {
            url = url + "?sn=" + sn + "&pwd=" + pwd + "&mobile=" + phoneNum + "&content="
                    + URLEncoder.encode(content, "gb2312");
            response = HttpClientUtil.post(url, new ArrayList<NameValuePair>());
        } catch (Exception e) {
            throw new IllegalArgumentException("message content invidal content");
        }
        return response;
    }
}
