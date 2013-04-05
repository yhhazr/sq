package com.sz7road.web.action.online.user;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.json.annotations.JSON;

import com.fivestars.interfaces.bbs.client.Client;
import com.fivestars.interfaces.bbs.util.XMLHelper;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.sz7road.web.common.listener.SystemConfig;
import com.sz7road.web.common.servlet.CaptchaServlet;
import com.sz7road.web.common.util.CookieUtil;
import com.sz7road.web.common.util.DateUtil;
import com.sz7road.web.common.util.MD5Util;
import com.sz7road.web.common.util.ServiceLocator;
import com.sz7road.web.model.adinfo.RegInfo;
import com.sz7road.web.model.onlineUser.LoginInfo;
import com.sz7road.web.model.signmanage.Sign;
import com.sz7road.web.model.signmanage.SignExChangeGiftValue;
import com.sz7road.web.service.AdStatisticsService;
import com.sz7road.web.service.OnlineUserService;
import com.sz7road.web.service.SignService;

public class UserAction extends ActionSupport {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = LogManager.getLogger(UserAction.class);

    //字母开头，数字字母下划线，6-20位
    private static final String REGE_USERNAME_FORMAT = "^[a-zA-Z]{1}([a-zA-Z0-9]|[_]){5,19}$";
    //5位及以上重复字符
    private static final String REGE_USERNAME_REPEAT = "([a-z0-9A-Z_])\\1{4,}";
    //密码长度
    private static final String REGE_PWD_LENGTH = "^(.){6,20}$";
    //屏蔽字  
    private static final String[] ILLEGAL_WORDS = { "GameMaster", "GM", "shit", "bitch", "fvc", "phuc", "fuk",
            "shenqu", "fuck", "admin", "7road" };

    private String result;

    private String verifyCode;

    private String inName;//客户端传入

    private String password1;

    private String password2;

    private String isUserNameError;

    private String respMsg;

    private String bbsData;

    private String outName;//服务器传出

    private String site;

    private String lastGameZoneName;

    private int lastGameZoneId;

    private int lastGameId;

    private String state;

    private boolean showLoginVerifyFlag = false;

    /**
    * 字段描述：签到的总积分
    */
    private String totalScore = "0";

    /**
    * 字段描述：用户连续签到次数
    */
    private String continueSignCount = "0";

    /**
    * 字段描述：签到历史
    */
    private List<Integer> signHistory;

    /**
    * 字段描述：领取的礼包
    */
    private List<SignExChangeGiftValue> gifts;

    private static final int GAME_ID = Integer.parseInt(SystemConfig.getProperty("sq.game.id"));

    // 登录
    public String loginSubmit() {
        result = "false";
        OnlineUserService onlineUserService = ServiceLocator.getOnlineUserService();
        String remoteIp = this.getIpAddr(ServletActionContext.getRequest());
        try {
            logger.info("====login start.. name:" + inName + " ip:" + remoteIp);
            LoginInfo logInfo = onlineUserService.servLoginAuth(inName, password1, remoteIp);
            if (logInfo != null && logInfo.getUser() != null && "success".equals(logInfo.getRespFlag())) {
                long nowTime = new Date().getTime();
                String userInfo = logInfo.getUser().getId() + "," + logInfo.getUser().getUserName() + "," + nowTime;
                String sign = CookieUtil.buildMD5Cookie(String.valueOf(logInfo.getUser().getId()), logInfo.getUser()
                        .getUserName(), String.valueOf(nowTime));
                String cookieUserInfo = userInfo + "," + sign;
                HttpServletResponse response = ServletActionContext.getResponse();
                CookieUtil.addCookie(response, SystemConfig.getProperty("cookie.name"), cookieUserInfo, 0);
                outName = logInfo.getUser().getUserName();
                if (logInfo.getGs() != null) {
                    String zoneIdStr = logInfo.getGs().getId();
                    int zoneId = ((zoneIdStr == null || "".equals(zoneIdStr)) || !zoneIdStr.matches("^[0-9]*$")) ? -1
                            : Integer.parseInt(zoneIdStr);
                    lastGameZoneId = zoneId;
                    String lastGameIdStr = logInfo.getGs().getGameId();
                    lastGameId = (lastGameIdStr == null || "".equals(lastGameIdStr)) ? -1 : Integer
                            .parseInt(lastGameIdStr);
                    lastGameZoneName = logInfo.getGs().getServerName();
                    state = logInfo.getGs().getServerStatus();
                }
                //数据库中game_server表主键id不能为0
                String bbsPassword = this.passwordMD5(password1);
                try {
                    bbsData = bbsLogin(logInfo.getUser().getUserName(), bbsPassword);
                } catch (Exception e) {
                    logger.error("bbs error.", e);
                }

                //获取用户签到的信息
                SignService signService = ServiceLocator.getSignService();
                Sign userSign = signService.getSignInfo(logInfo.getUser().getId(), GAME_ID);
                if (null != userSign) {
                    totalScore = String.valueOf(userSign.getTotalScore());
                    continueSignCount = String.valueOf(userSign.getContinueSignCount());
                    signHistory = this.LongToHistoryList(userSign.getSignHistory(), userSign.getLastModifyDate());
                }
                gifts = signService.queryReceiveGift(logInfo.getUser().getId());
                result = "true";
                logger.info("====bbs login success..");
            } else if ("failure".equals(logInfo.getRespFlag())) {
                respMsg = "用户名或密码错误。";
                ActionContext actionContext = ActionContext.getContext();
                Map session = actionContext.getSession();
                int errTimes;
                if (session.get("ShowVerify") != null) {
                    errTimes = (Integer) session.get("ShowVerify");
                } else {
                    errTimes = 0;
                }
                errTimes++;
                session.put("ShowVerify", errTimes);
                if (errTimes > 2)
                    showLoginVerifyFlag = true;
                logger.info("login " + respMsg);
                result = "false";
            } else {
                respMsg = "服务器连接超时。";
                result = "false";
            }
        } catch (Exception e) {
            logger.error("login Error:name:" + inName + " ip:" + remoteIp + " | " + e.getMessage(), e);
            respMsg = "服务器连接超时。";
            result = "false";
        }

        return SUCCESS;
    }

    // bbs登陆
    public String bbsLogin(String userName, String passWord) {
        Client e = new Client();
        logger.info("BBS login start: name:" + userName);
        String result = e.uc_user_login(userName, passWord);
        String ucsynlogin = null;
        LinkedList<String> rs = XMLHelper.uc_unserialize(result);
        if (rs.size() > 0) {
            int uid = Integer.parseInt(rs.get(0));
            String username = rs.get(1);
            String password = rs.get(2);
            String email = rs.get(3);
            if (uid > 0) {
                ucsynlogin = e.uc_user_synlogin(uid);
                logger.info("BBS -- login success !: name:" + userName);

            } else if (uid == -1) {
                logger.info("BBS -- 用户不存在,或者被删除: name:" + userName);
            } else if (uid == -2) {
                logger.info("BBS -- 密码错: name:" + userName);
            } else {
                logger.info("BBS -- 未定义: name:" + userName);
            }
        } else {
            logger.error("BBS -- Login failed! : name:" + userName + " | " + result);
        }
        return ucsynlogin;
    }

    // 验证登陆
    public String checkLogin() {
        result = "false";
        String[] userInfo = CookieUtil.getUserInfoArrayFromCookie();
        if (userInfo != null && userInfo.length == 4) {
            String sign = CookieUtil.buildMD5Cookie(userInfo[0], userInfo[1], userInfo[2]);
            if (!sign.equals(userInfo[3])) {
                return SUCCESS;
            }
            OnlineUserService onlineUserService = ServiceLocator.getOnlineUserService();
            try {
                LoginInfo logInfo = onlineUserService.servCheckAccount(userInfo[1], "1", "true");
                if (logInfo != null && logInfo.getUser() != null && "success".equals(logInfo.getRespFlag())) {
                    result = "true";
                    if (logInfo.getGs() != null) {
                        String zoneIdStr = logInfo.getGs().getId();
                        int zoneId = ((zoneIdStr == null || "".equals(zoneIdStr)) || !zoneIdStr.matches("^[0-9]*$")) ? -1
                                : Integer.parseInt(zoneIdStr);
                        lastGameZoneId = zoneId;
                        String lastGameIdStr = logInfo.getGs().getGameId();
                        lastGameId = (lastGameIdStr == null || "".equals(lastGameIdStr)) ? -1 : Integer
                                .parseInt(lastGameIdStr);
                        lastGameZoneName = logInfo.getGs().getServerName();
                        state = logInfo.getGs().getServerStatus();
                    }
                    outName = userInfo[1];

                    //获取用户签到的信息
                    SignService signService = ServiceLocator.getSignService();
                    Sign userSign = signService.getSignInfo(logInfo.getUser().getId(), GAME_ID);
                    if (null != userSign) {
                        totalScore = String.valueOf(userSign.getTotalScore());
                        continueSignCount = String.valueOf(userSign.getContinueSignCount());
                        signHistory = this.LongToHistoryList(userSign.getSignHistory(), userSign.getLastModifyDate());
                    }
                    gifts = signService.queryReceiveGift(logInfo.getUser().getId());
                }
            } catch (Exception e) {
                logger.error("check login error. | name:" + userInfo[1], e);
            }
        }
        return SUCCESS;
    }

    // 注册提交
    public String registrationSubmit() {
        result = "false";
        if (!validateRegist()) {
            return SUCCESS;
        }
        OnlineUserService onlineUserService = ServiceLocator.getOnlineUserService();
        try {
            LoginInfo logInfo = onlineUserService.servSignUp(inName, password1, site);
            if (logInfo != null && "success".equals(logInfo.getRespFlag())) {
                HttpServletResponse response = ServletActionContext.getResponse();
                String bbsPassword = this.passwordMD5(password1);
                try {
                    bbsRegister(inName, bbsPassword, inName + "@7roadtemp.com");
                    bbsData = bbsLogin(inName, bbsPassword);
                } catch (Exception e) {
                    logger.error("bbs regist error. name:" + inName, e);
                }
                outName = inName;
                LoginInfo checkLogInfo = onlineUserService.servCheckAccount(inName, "1", "true");
                long nowTime = new Date().getTime();
                if (checkLogInfo != null && checkLogInfo.getUser() != null) {
                    String userInfo = checkLogInfo.getUser().getId() + "," + inName + "," + nowTime;
                    String sign = CookieUtil.buildMD5Cookie(String.valueOf(checkLogInfo.getUser().getId()), inName,
                            String.valueOf(nowTime));
                    String cookieUserInfo = userInfo + "," + sign;
                    CookieUtil.addCookie(response, SystemConfig.getProperty("cookie.name"), cookieUserInfo, 0);
                    if (checkLogInfo != null && "success".equals(checkLogInfo.getRespFlag())) {
                        result = "true";
                    } else {
                        respMsg = "注册成功！";
                    }
                    //注册成功后判断是否是从广告页进入，是则计数。
                    try {
                        HttpServletRequest request = ServletActionContext.getRequest();
                        String adInfo = CookieUtil.getCookieValueByName(request, "ADINFO");
                        if (adInfo != null && adInfo != "") {
                            String[] info = adInfo.split(",");
                            RegInfo regInfo = new RegInfo();
                            regInfo.setAdId(Integer.parseInt(info[0]));
                            Date now = new Date();
                            regInfo.setRegTime(now);
                            regInfo.setClickTime(now);
                            if(info.length == 3){
                            	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            	regInfo.setClickTime(dateFormat.parse(info[2]));
                            }
                            regInfo.setUseId(checkLogInfo.getUser().getId());
                            regInfo.setUserName(checkLogInfo.getUser().getUserName());
                            AdStatisticsService adStatisticsService = ServiceLocator.getAdStatisticsService();
                            adStatisticsService.regCount(regInfo, info[1]);
                        }
                    } catch (Exception e) {
                        logger.error("注册计数错误：" + e);
                    }
                } else {
                    respMsg = "服务器连接超时。";
                }
            } else if ("failure".equals(logInfo.getRespFlag())) {
                respMsg = "用户名或密码错误。";
                logger.info("regist | name:" + inName + " | " + respMsg);
            } else {
                respMsg = "服务器连接超时。";
            }
        } catch (Exception e) {
            logger.error("regist error | name:" + inName, e);
            respMsg = "服务器连接超时。";
        }
        return SUCCESS;
    }

    //bbs注册
    public void bbsRegister(String userName, String passWord, String email) {
        Client uc = new Client();

        String returns = uc.uc_user_register(userName, passWord, email);
        if (returns != null && !"".equals(returns)) {
            int uid = Integer.parseInt(returns);
            if (uid <= 0) {
                if (uid == -1) {
                    logger.info("BBS -- 用户名不合法 | name:" + userName);
                } else if (uid == -2) {
                    logger.info("BBS -- 包含要允许注册的词语 | name:" + userName);
                } else if (uid == -3) {
                    logger.info("BBS -- 用户名已经存在 | name:" + userName);
                } else if (uid == -4) {
                    logger.info("BBS -- Email 格式有误 | name:" + userName);
                } else if (uid == -5) {
                    logger.info("BBS -- Email 不允许注册 | name:" + userName);
                } else if (uid == -6) {
                    logger.info("BBS -- 该 Email 已经被注册 | name:" + userName);
                } else {
                    logger.info("BBS -- 未定义 | name:" + userName);
                }
            } else {
                logger.info("OK:" + returns);
            }
        }
    }

    // 退出登录
    public String logoutSubmit() {
        Client uc = new Client();
        // setcookie('Example_auth', '', -86400);
        // 生成同步退出的代码
        String ucsynlogout = uc.uc_user_synlogout();
        logger.info("论坛退出登录成功：" + ucsynlogout);

        if (ucsynlogout != null) {
            logger.info(ucsynlogout);
            result = "true";
            bbsData = ucsynlogout;
        }
        return SUCCESS;
    }

    //首页退出登陆
    public String indexLogout() {
        HttpServletResponse response = ServletActionContext.getResponse();
        CookieUtil.removeCookie(response, SystemConfig.getProperty("cookie.name"), 0);
        logoutSubmit();
        //		Client uc = new Client();
        //		// setcookie('Example_auth', '', -86400);
        //		// 生成同步退出的代码
        //		String ucsynlogout = uc.uc_user_synlogout();
        //		if (ucsynlogout != null) {
        //			result = "true";
        //			bbsData = ucsynlogout;
        //		}
        result = "true";
        logger.info("====官网退出登录成功.");
        return SUCCESS;
    }

    //验证用户名是否存在的方法
    public String checkUserName() {
        result = "false";
        Pattern pattern = Pattern.compile(REGE_USERNAME_REPEAT);
        Matcher matcher = pattern.matcher(inName);
        boolean containIllegalWord = false;
        String illegalWord = null;
        for (int i = 0; i < ILLEGAL_WORDS.length; i++) {
            illegalWord = ILLEGAL_WORDS[i].toLowerCase();
            if (inName.toLowerCase().indexOf(illegalWord) != -1) {
                containIllegalWord = true;
            }
        }
        if (inName == null || "".equals(inName)) {
            respMsg = "用户名不能为空";
        } else if (!(inName.matches(REGE_USERNAME_FORMAT))) {
            respMsg = "字母开头，6-20位字母、数字、下划线";
        } else if (matcher.find()) {
            respMsg = "用户名已注册";
        } else if (containIllegalWord) {
            respMsg = "用户名已注册";
        } else {
            try {
                OnlineUserService onlineUserService = ServiceLocator.getOnlineUserService();
                LoginInfo logInfo = onlineUserService.servCheckAccount(inName, "1", "false");
                if ("success".equals(logInfo.getRespFlag())) {
                    respMsg = "用户名已存在";
                } else if ("".equals(logInfo.getRespFlag())) {
                    result = "error";
                } else {
                    result = "true";
                }
            } catch (Exception e) {
                logger.error("Check userName Error:" + e.getMessage(), e);
                return INPUT;
            }
        }

        return SUCCESS;
    }

    // 检查验证码
    public String checkVerifyCode() {
        result = "true";
        HttpServletRequest request = ServletActionContext.getRequest();
        if (request.getSession().getAttribute(CaptchaServlet.KAPTCHA_SESSION_KEY) != null) {
            String verifyImg = (String) request.getSession().getAttribute(CaptchaServlet.KAPTCHA_SESSION_KEY);
            if (verifyCode != null && !verifyCode.trim().equalsIgnoreCase(verifyImg)) {
                result = "false";
            }
        }
        return SUCCESS;
    }

    // 注册提交验证
    public boolean validateRegist() {
        boolean legalNameAndPwd = false;
        boolean legalVerifyCode = true;

        //注册时判断验证码
        HttpServletRequest request = ServletActionContext.getRequest();
        if (request.getSession().getAttribute(CaptchaServlet.KAPTCHA_SESSION_KEY) != null) {
            String verifyImg = (String) request.getSession().getAttribute(CaptchaServlet.KAPTCHA_SESSION_KEY);
            if (verifyCode != null && !verifyCode.trim().equalsIgnoreCase(verifyImg)) {
                addFieldError("verifyCode", "验证码错误");
                respMsg = "验证码错误";
                legalVerifyCode = false;
            }
        }

        //判断账号和密码
        Pattern pattern = Pattern.compile(REGE_USERNAME_REPEAT);
        Matcher matcher = pattern.matcher(inName);
        boolean containIllegalWord = false;
        String illegalWord = null;
        for (int i = 0; i < ILLEGAL_WORDS.length; i++) {
            illegalWord = ILLEGAL_WORDS[i].toLowerCase();
            if (inName.toLowerCase().indexOf(illegalWord) != -1) {
                containIllegalWord = true;
            }
        }
        if (inName == null || "".equals(inName)) {
            respMsg = "请输入用户名";
        } else if (!(inName.matches(REGE_USERNAME_FORMAT))) {
            respMsg = "字母开头，6-20位字母、数字、下划线";
        } else if (matcher.find()) {
            //5位及以上重复字符
            respMsg = "用户名已注册";
        } else if (containIllegalWord) {
            //含有屏蔽字
            respMsg = "用户名已注册";
        } else if (!password1.matches(REGE_PWD_LENGTH)) {
            respMsg = "密码长度应在6~20之间";
        } else if ("".equals(password2) || password2 == null) {
            respMsg = "请再次输入密码";
        } else if (!password1.equals(password2)) {
            respMsg = "两次密码不一致";
        } else {
            OnlineUserService onlineUserService = ServiceLocator.getOnlineUserService();
            try {
                LoginInfo logInfo = onlineUserService.servCheckAccount(inName, "1", "false");
                if ("success".equals(logInfo.getRespFlag())) {
                    respMsg = "用户名已注册";
                } else {
                    legalNameAndPwd = true;
                }
            } catch (Exception e) {
                logger.error("regist validate error", e);
            }
        }

        return legalNameAndPwd && legalVerifyCode;
    }

    public String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip != null && ip.indexOf(",") != -1) {
            ip = ip.split(",")[0];
        }
        return ip;
    }

    //论坛密码MD5加密
    private String passwordMD5(String password) {
        String pwdMD5 = MD5Util.getMD5String(password);
        String pwdMD52 = MD5Util.getMD5String(pwdMD5 + "SZ@7road");
        String pwd = pwdMD52.substring(0, 15);
        return pwd;
    }

    /**
        * 将签到历史时间转换成以日期中的天表示并存入列表中，
        * 例如：昨天是18号，如果昨天签到则将18存入列表中，如果没签到则不存 
        * 创建时间： 2013-1-18 下午3:10:54
        * 创建人：xin.fang
        * 参数：
        * 返回值： String
        * 方法描述 :
        */
    private List<Integer> LongToHistoryList(long history, long dateTime) {
        List<Integer> listStr = new ArrayList<Integer>();
        Date date = new Date(dateTime);
        Calendar newCalendar = Calendar.getInstance();
        newCalendar.setTime(new Date());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        //如果获取的date的年和月不等于当前时间的年和月，就直接返回空的listStr
        if (newCalendar.get(Calendar.YEAR) != calendar.get(Calendar.YEAR)
                || newCalendar.get(Calendar.MONTH) != calendar.get(Calendar.MONTH)) {
            return listStr;
        }

        String dateStr = DateUtil.format(date, "dd");
        int time = 1;
        if (StringUtils.isNotBlank(dateStr) && StringUtils.isNumeric(dateStr)) {
            time = Integer.parseInt(dateStr);
        }
        char[] binaryStr = Long.toBinaryString(history).toCharArray();
        int preTime = 1;
        for (int i = binaryStr.length - 1; i >= 0; i--) {
            if (binaryStr[i] == '1') {
                if (time >= 1) {
                    listStr.add(time);
                } else {
                    listStr.add(Integer.valueOf("5" + preTime));
                }
            }
            time--;
            if (time < 1) {
                preTime++;
            }
        }
        return listStr;
    }

    @JSON(serialize = false)
    public String getIsUserNameError() {
        return isUserNameError;
    }

    @JSON(serialize = false)
    public String getInName() {
        return inName;
    }

    public void setInName(String inName) {
        this.inName = inName;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }

    public String getOutName() {
        return outName;
    }

    public void setOutName(String outName) {
        this.outName = outName;
    }

    @JSON(serialize = false)
    public String getPassword1() {
        return password1;
    }

    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    @JSON(serialize = false)
    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @JSON(serialize = false)
    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getBbsData() {
        return bbsData;
    }

    public void setBbsData(String bbsData) {
        this.bbsData = bbsData;
    }

    @Override
    public void validate() {
        //		logger.info("enter validate");
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getLastGameZoneName() {
        return lastGameZoneName;
    }

    public void setLastGameZoneName(String lastGameZoneName) {
        this.lastGameZoneName = lastGameZoneName;
    }

    public int getLastGameZoneId() {
        return lastGameZoneId;
    }

    public void setLastGameZoneId(int lastGameZoneId) {
        this.lastGameZoneId = lastGameZoneId;
    }

    public int getLastGameId() {
        return lastGameId;
    }

    public void setLastGameId(int lastGameId) {
        this.lastGameId = lastGameId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public boolean isShowLoginVerifyFlag() {
        return showLoginVerifyFlag;
    }

    public void setShowLoginVerifyFlag(boolean showLoginVerifyFlag) {
        this.showLoginVerifyFlag = showLoginVerifyFlag;
    }

    public String getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(String totalScore) {
        this.totalScore = totalScore;
    }

    public String getContinueSignCount() {
        return continueSignCount;
    }

    public void setContinueSignCount(String continueSignCount) {
        this.continueSignCount = continueSignCount;
    }

    public List<Integer> getSignHistory() {
        return signHistory;
    }

    public void setSignHistory(List<Integer> signHistory) {
        this.signHistory = signHistory;
    }

    public List<SignExChangeGiftValue> getGifts() {
        return gifts;
    }

    public void setGifts(List<SignExChangeGiftValue> gifts) {
        this.gifts = gifts;
    }

}
