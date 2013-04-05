package com.sz7road.web.common.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.json.JSON;
import net.sf.json.JSONObject;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.sz7road.web.common.listener.SystemConfig;
import com.sz7road.web.model.gamemanage.PlatformGameServer;
import com.sz7road.web.model.gamemanage.RoleRankInfo;
import com.sz7road.web.model.gamemanage.StopGameServerInfo;
import com.sz7road.web.model.pagination.PageInfo;
import com.sz7road.web.service.impl.GameServerServiceImpl;

/**
 * 更新接口支持多游戏
 * 新弹弹堂上线时部署
 * @author john.jiang
 *
 */
public class PlatformClient {
	private static PlatformClient _this;
	private static String USER_URL = SystemConfig
			.getProperty(AppConstants.PLATFORM_ACCESS_BASE_URL);
	private static String BACKGROUND_GAME_SERVER_URL = SystemConfig
			.getProperty(AppConstants.PLATFORM_MANAGER_BASE_URL)
			+ SystemConfig.getProperty(AppConstants.PLATFORM_SERVER_BACKGROUND);
	private static String BACKGROUND_STOP_CHECKED_GAME_SERVER_URL = SystemConfig
			.getProperty(AppConstants.PLATFORM_MANAGER_BASE_URL)
			+ SystemConfig
					.getProperty(AppConstants.PLATFORM_SERVER_BACKGROUND_STOPCHECKEDSERVER);
	private static String BACKGROUND_FIND_GAME_SERVER_URL = SystemConfig
			.getProperty(AppConstants.PLATFORM_MANAGER_BASE_URL)
			+ SystemConfig
					.getProperty(AppConstants.PLATFORM_SERVER_BACKGROUND_STOPCHECKEDSERVER);
	// private static String ONLINE_ALL_GAME_SERVER_URL =
	// SystemConfig.getProperty(AppConstants.PLATFORM_ACCESS_BASE_URL) +
	// SystemConfig.getProperty(AppConstants.PLATFORM_SERVER_ONLINE_ALLSERVER);
	private static String ONLINE_ALL_GAME_SERVER_URL = SystemConfig
			.getProperty(AppConstants.PLATFORM_MANAGER_BASE_URL)
			+ "/getEnterGameServerList";
	private static String ONLINE_LATEST_GAME_SERVER_URL = SystemConfig
			.getProperty(AppConstants.PLATFORM_ACCESS_BASE_URL)
			+ SystemConfig
					.getProperty(AppConstants.PLATFORM_SERVER_ONLINE_LATEESTSERVER);

	public synchronized static PlatformClient getInstance() {
		if (_this == null)
			_this = new PlatformClient();
		return _this;
	}

	// 登录认证接口
	public String loginAuth(String username, String password, String remoteIp)
			throws Exception {

		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("user", username));
		nvps.add(new BasicNameValuePair("code", password));
		nvps.add(new BasicNameValuePair("ip", remoteIp));
		nvps.add(new BasicNameValuePair("gameId", String.valueOf(AppConstants.GAME_ID)));
		String url = USER_URL
				+ SystemConfig
						.getProperty(AppConstants.PLATFORM_ACCESS_LOGINAUTH);

		String response = HttpClientUtil.post(url, nvps);
		// String response =
		// "success={\"gs\":{\"id\":1,\"gameId\":1,\"serverNo\":1,\"serverName\":\"[毛线1服]烽火测试\",\"serverStatus\":-2,\"recommand\":true,\"createTime\":1336835410000,\"openingTime\":1338414152000},\"user\":{\"realName\":\"\",\"icn\":\"\",\"createTime\":1341904844000,\"aggrRecharge\":0,\"lastIp\":\"10.10.8.35\",\"lastLoginTime\":1342079746000,\"lastGameId\":1,\"lastGameZoneId\":1,\"loginSum\":10,\"site\":null,\"gender\":0,\"birthday\":null,\"city\":null,\"career\":null,\"safeLevel\":1,\"id\":125,\"status\":0,\"userName\":\"leo321\",\"email\":\"leo321@7road.com\"}}";
		return response;

	}

	// 账号注册接口
	public String signUp(String username, String password, String site)
			throws Exception {

		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("user", username));
		nvps.add(new BasicNameValuePair("password", password));
		nvps.add(new BasicNameValuePair("site", site));
		String url = USER_URL
				+ SystemConfig.getProperty(AppConstants.PLATFORM_ACCESS_SIGNUP);

		String response = HttpClientUtil.post(url, nvps);

		return response;

	}

	// 验证数据接口
	public String checkAccount(String value, String type, String details)
			throws Exception {

		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("value", value));
		nvps.add(new BasicNameValuePair("type", type));
		nvps.add(new BasicNameValuePair("details", details));
		nvps.add(new BasicNameValuePair("gameId", String.valueOf(AppConstants.GAME_ID)));

		String url = USER_URL
				+ SystemConfig
						.getProperty(AppConstants.PLATFORM_ACCESS_CHECKACCOUNT);

		String response = HttpClientUtil.get(url, nvps);
		// String response =
		// "success={\"gs\":{\"id\":1,\"gameId\":1,\"serverNo\":1,\"serverName\":\"[毛线1服]烽火测试\",\"serverStatus\":-2,\"recommand\":true,\"createTime\":1336835410000,\"openingTime\":1338414152000},\"user\":{\"realName\":\"\",\"icn\":\"\",\"createTime\":1341904844000,\"aggrRecharge\":0,\"lastIp\":\"10.10.8.35\",\"lastLoginTime\":1342079746000,\"lastGameId\":1,\"lastGameZoneId\":1,\"loginSum\":10,\"site\":null,\"gender\":0,\"birthday\":null,\"city\":null,\"career\":null,\"safeLevel\":1,\"id\":125,\"status\":0,\"userName\":\"leo321\",\"email\":\"leo321@7road.com\"}}";
		return response;

	}

	// 新增游戏区（批量）
	public String addGameServer(PlatformGameServer... server) throws Exception {

		return this.insertUpdateGameServer(BACKGROUND_GAME_SERVER_URL,
				AppConstants.ADD, server);
	}

	// 修改游戏区（批量）
	public String editGameServer(PlatformGameServer... server) throws Exception {

		return this.insertUpdateGameServer(BACKGROUND_GAME_SERVER_URL,
				AppConstants.UPDATE, server);
	}

	// 批量修改游戏区状态
	public String editGameServerStatusByIds(String status, String ids)
			throws Exception {
		String response = null;

		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair(AppConstants.ACTION, String
				.valueOf(AppConstants.UPDATE_STATUS)));
		nvps.add(new BasicNameValuePair(AppConstants.STATUS, status));
		nvps.add(new BasicNameValuePair(AppConstants.REQ_JSON, ids));
		nvps.add(new BasicNameValuePair("gameId", String.valueOf(AppConstants.GAME_ID)));

		response = HttpClientUtil.post(BACKGROUND_GAME_SERVER_URL, nvps);

		return response;
	}

	// 查询服务区维护信息（model:stopGameServerInfo）
	public List<StopGameServerInfo> findStopServerInfo(String ServerIdList)
			throws Exception {
		List<StopGameServerInfo> stopServerInfoList = null;
		String response = null;
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair(AppConstants.ACTION,
				"queryMaintainInfoByServerIdList"));
		nvps.add(new BasicNameValuePair("ServerIdList", ServerIdList));
		nvps.add(new BasicNameValuePair("gameId", String.valueOf(AppConstants.GAME_ID)));
		response = HttpClientUtil.post(BACKGROUND_FIND_GAME_SERVER_URL, nvps);
		// response =
		// "{\"code\":200,\"msg\":\"查询成功！\",\"object\":[{\"id\":15,\"serverId\":2,\"createTime\":1342412681000,\"startTime\":1341999000000,\"endTime\":1342432800000,\"message\":\"ok!\"}]}";
		if (response != null && (response.indexOf("object") != -1)) {
			stopServerInfoList = JsonUtil.jsonStrToGetList(response, "object",
					StopGameServerInfo.class);
		}

		return stopServerInfoList;
	}

	// 批量维护服务区（修改维护信息）
	public boolean stopGameServerByIds(StopGameServerInfo stopServerInfo,
			String serverIds) throws Exception {
		boolean isEdit = false;
		String response = null;
		if (stopServerInfo != null) {
			StringBuffer maintainJsonValueBuffer = new StringBuffer();
			maintainJsonValueBuffer.append("{\"startTime\":"
					+ stopServerInfo.getStartTime() + ",");
			maintainJsonValueBuffer.append("\"endTime\":"
					+ stopServerInfo.getEndTime() + ",");
			String message = StringUtil.escapeCodeForJson(stopServerInfo
					.getMessage());
			maintainJsonValueBuffer.append("\"message\":\"" + message + "\"}");

			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair(AppConstants.ACTION,
					AppConstants.STOP_CHECKED_SERVER));
			nvps.add(new BasicNameValuePair(AppConstants.MAINTAIN_JSON,
					maintainJsonValueBuffer.toString()));
			nvps.add(new BasicNameValuePair("gameId", String.valueOf(AppConstants.GAME_ID)));
			if ("all".equals(serverIds)) {
				nvps.add(new BasicNameValuePair("allFlag", "all"));
			} else {
				nvps.add(new BasicNameValuePair(AppConstants.SERVER_ID_ARRAY,
						serverIds));
			}
			response = HttpClientUtil.post(
					BACKGROUND_STOP_CHECKED_GAME_SERVER_URL, nvps);
			// response = "{\"code\":200,\"msg\":\"更新维护信息成功！\",\"object\":1}";
			if (response != null) {
				JSONObject jsonObj = JSONObject.fromObject(response);
				int code = jsonObj.getInt("code");
				String object = jsonObj.getString("object");
				if (code == 200 || code == 0) {
					isEdit = true;
				}
			}
		}

		return isEdit;
	}

	// 删除维护信息
	public String removeStopInfo(String ids) throws Exception {
		String response = null;
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair(AppConstants.ACTION,
				"deleteFutureMaintainInfo"));
		nvps.add(new BasicNameValuePair("ServerIdArray", ids));
		nvps.add(new BasicNameValuePair("gameId", String.valueOf(AppConstants.GAME_ID)));
		response = HttpClientUtil.post(BACKGROUND_STOP_CHECKED_GAME_SERVER_URL,
				nvps);

		return response;
	}

	// 全区开服停服（修改维护状态）
	public String editAllGameServerStatus(int all, String status)
			throws Exception {
		String response = null;

		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair(AppConstants.ACTION, String
				.valueOf(AppConstants.UPDATE_STATUS)));
		nvps.add(new BasicNameValuePair(AppConstants.All_SERVER, String
				.valueOf(all)));
		nvps.add(new BasicNameValuePair(AppConstants.STATUS, status));
		nvps.add(new BasicNameValuePair("gameId", String.valueOf(AppConstants.GAME_ID)));
		response = HttpClientUtil.post(BACKGROUND_GAME_SERVER_URL, nvps);

		return response;
	}

	// 增加 修改游戏区 基础方法
	private String insertUpdateGameServer(String url, String method,
			PlatformGameServer... server) throws Exception {
		String response = null;
		if (server.length == 0) {
			response = "empty";
		} else {
			JSON jsonArray = this.beanToJSONArray(server);
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair(AppConstants.ACTION, method));
			nvps.add(new BasicNameValuePair(AppConstants.REQ_JSON, jsonArray
					.toString()));
			nvps.add(new BasicNameValuePair("gameId", String.valueOf(AppConstants.GAME_ID)));
			response = HttpClientUtil.post(url, nvps);
		}

		return response;
	}

	// 分页查询(组合查询)
	public List<PlatformGameServer> findGameServer(PageInfo page, int sort,
			Map<String, String> queryMap, List<Integer> total) throws Exception {
		String response = null;
		List<PlatformGameServer> list = null;
		int startRow = page.getStartRow() + 1;
		int pageSize = page.getPageSize();
		int pageIndex = 1;
		if (pageSize != 0) {
			pageIndex = startRow / pageSize;
			if (pageIndex == 0 || startRow % pageSize != 0) {
				pageIndex = pageIndex + 1;
			}
		}
		// System.out.println("#####################startRow:" + startRow);
		// System.out.println("#####################pageIndex:" + pageIndex);
		// System.out.println("#####################pageSize:" + pageSize);
		String order = page.getOrder() != null ? page.getOrder() : "id";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair(AppConstants.ACTION, AppConstants.LIST));
		nvps.add(new BasicNameValuePair(AppConstants.GAME_ID_NAME, String
				.valueOf(AppConstants.GAME_ID)));
		nvps.add(new BasicNameValuePair(AppConstants.PAGE_INDEX, String
				.valueOf(pageIndex)));
		nvps.add(new BasicNameValuePair(AppConstants.PAGE_SIZE, String
				.valueOf(pageSize)));
		nvps.add(new BasicNameValuePair(AppConstants.PAGE_ORDER, order));
		nvps.add(new BasicNameValuePair(AppConstants.PAGE_SORT, String
				.valueOf(sort)));
		nvps.add(new BasicNameValuePair("gameId", String.valueOf(AppConstants.GAME_ID)));
		if (queryMap != null && queryMap.size() > 0) {
			StringBuffer queryValueBuffer = new StringBuffer();
			String queryValue = "";
			queryValueBuffer.append("{");
			Iterator iter = queryMap.entrySet().iterator();
			while (iter.hasNext()) {
				Entry entry = (Entry) iter.next();
				String mapKey = entry.getKey().toString();
				String mapValue = entry.getValue() != null ? entry.getValue()
						.toString() : "";
				queryValueBuffer.append("\"" + mapKey + "\":\"" + mapValue
						+ "\",");
			}
			queryValue = queryValueBuffer.toString();
			if (queryValue.length() > 1) {
				queryValue = queryValue.substring(0, queryValue.length() - 1)
						+ "}";
				nvps.add(new BasicNameValuePair(AppConstants.PAGE_QUERY,
						queryValue));
			}
		}

		response = HttpClientUtil.post(BACKGROUND_GAME_SERVER_URL, nvps);
		// response =
		// "{\"total\":19,\"list\":[{\"id\":6,\"serverNo\":6,\"serverName\":\"server6\",\"gameId\":1, \"serverStatus\":1,\"recommand\":true, \"openingTime\":1340011268877},{\"id\":9,\"serverNo\":9,\"serverName\":\"server9\",\"gameId\":1, \"serverStatus\":2,\"recommand\":true,\"openingTime\":1340111268877},{\"id\":6,\"serverNo\":6,\"serverName\":\"server6\",\"gameId\":1, \"serverStatus\":1,\"recommand\":true, \"openingTime\":1340011268877},{\"id\":9,\"serverNo\":9,\"serverName\":\"server9\",\"gameId\":1, \"serverStatus\":2,\"recommand\":true,\"openingTime\":1340111268877},{\"id\":6,\"serverNo\":6,\"serverName\":\"server6\",\"gameId\":1, \"serverStatus\":1,\"recommand\":true, \"openingTime\":1340011268877},{\"id\":9,\"serverNo\":9,\"serverName\":\"server9\",\"gameId\":1, \"serverStatus\":2,\"recommand\":true,\"openingTime\":1340111268877},{\"id\":6,\"serverNo\":6,\"serverName\":\"server6\",\"gameId\":1, \"serverStatus\":1,\"recommand\":true, \"openingTime\":1340011268877},{\"id\":9,\"serverNo\":9,\"serverName\":\"server9\",\"gameId\":1, \"serverStatus\":2,\"recommand\":true,\"openingTime\":1340111268877},{\"id\":6,\"serverNo\":6,\"serverName\":\"server6\",\"gameId\":1, \"serverStatus\":1,\"recommand\":true, \"openingTime\":1340011268877},{\"id\":9,\"serverNo\":9,\"serverName\":\"server9\",\"gameId\":1, \"serverStatus\":2,\"recommand\":true,\"openingTime\":1340111268877},{\"id\":6,\"serverNo\":6,\"serverName\":\"server6\",\"gameId\":1, \"serverStatus\":1,\"recommand\":true, \"openingTime\":1340011268877},{\"id\":9,\"serverNo\":9,\"serverName\":\"server9\",\"gameId\":1, \"serverStatus\":2,\"recommand\":true,\"openingTime\":1340111268877},{\"id\":6,\"serverNo\":6,\"serverName\":\"server6\",\"gameId\":1, \"serverStatus\":1,\"recommand\":true, \"openingTime\":1340011268877},{\"id\":9,\"serverNo\":9,\"serverName\":\"server9\",\"gameId\":1, \"serverStatus\":2,\"recommand\":true,\"openingTime\":1340111268877},{\"id\":6,\"serverNo\":6,\"serverName\":\"server6\",\"gameId\":1, \"serverStatus\":1,\"recommand\":true, \"openingTime\":1340011268877},{\"id\":9,\"serverNo\":9,\"serverName\":\"server9\",\"gameId\":1, \"serverStatus\":2,\"recommand\":true,\"openingTime\":1340111268877},{\"id\":6,\"serverNo\":6,\"serverName\":\"server6\",\"gameId\":1, \"serverStatus\":1,\"recommand\":true, \"openingTime\":1340011268877},{\"id\":9,\"serverNo\":9,\"serverName\":\"server9\",\"gameId\":1, \"serverStatus\":2,\"recommand\":true,\"openingTime\":1340111268877},{\"id\":6,\"serverNo\":6,\"serverName\":\"server6\",\"gameId\":1, \"serverStatus\":1,\"recommand\":true, \"openingTime\":1340011268877},{\"id\":9,\"serverNo\":9,\"serverName\":\"server9\",\"gameId\":1, \"serverStatus\":2,\"recommand\":true,\"openingTime\":1340111268877},{\"id\":6,\"serverNo\":6,\"serverName\":\"server6\",\"gameId\":1, \"serverStatus\":1,\"recommand\":true, \"openingTime\":1340011268877},{\"id\":9,\"serverNo\":9,\"serverName\":\"server9\",\"gameId\":1, \"serverStatus\":2,\"recommand\":true,\"openingTime\":1340111268877}]}";
		if (response != null && !"".equals(response)) {
			if (response.indexOf(AppConstants.TOTAL) != -1) {
				JSONObject jsonObj = JSONObject.fromObject(response);
				total.add(jsonObj.getInt("total"));
			}
			if (total.size() > 0 && total.get(0) > 0
					&& !(response.indexOf(AppConstants.GAME_LIST) == -1)) {
				list = this.jsonStrToList(response);
			}
		}

		return list;
	}

	// 生成服务器列表页面前清除缓存
	public String cleanPlatformCache() throws Exception {
		String response = null;
		String bgCleanCacheUrl = USER_URL + "/Sync";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("type", "sync"));
		nvps.add(new BasicNameValuePair("timestamp", String.valueOf(new Date()
				.getTime())));
		nvps.add(new BasicNameValuePair("gameId", String.valueOf(AppConstants.GAME_ID)));
		response = HttpClientUtil.get(bgCleanCacheUrl, nvps);
		return response;
	}

	// 查询全部服务区(前台)
	public List<PlatformGameServer> findAllGameServer() throws Exception {
		String response = null;
		List<PlatformGameServer> list = new ArrayList<PlatformGameServer>();
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair(AppConstants.ACTION, "getServerList"));
		nvps.add(new BasicNameValuePair(AppConstants.GAME_ID_NAME, String
				.valueOf(AppConstants.GAME_ID)));
		nvps.add(new BasicNameValuePair("timestamp", String.valueOf(new Date()
				.getTime())));
		nvps.add(new BasicNameValuePair("gameId", String.valueOf(AppConstants.GAME_ID)));
		response = HttpClientUtil.post(ONLINE_ALL_GAME_SERVER_URL, nvps);

		// response =
		// "[{\"id\":6,\"serverNo\":6,\"serverName\":\"server6\",\"gameId\":1, \"serverStatus\":1,\"recommand\":true, \"openingTime\":1340011268877},{\"id\":9,\"serverNo\":9,\"serverName\":\"server9\",\"gameId\":1, \"serverStatus\":2,\"recommand\":true,\"openingTime\":1340111268877},{\"id\":6,\"serverNo\":6,\"serverName\":\"server6\",\"gameId\":1, \"serverStatus\":1,\"recommand\":true, \"openingTime\":1340011268877},{\"id\":9,\"serverNo\":9,\"serverName\":\"server9\",\"gameId\":1, \"serverStatus\":2,\"recommand\":true,\"openingTime\":1340111268877},{\"id\":6,\"serverNo\":6,\"serverName\":\"server6\",\"gameId\":1, \"serverStatus\":1,\"recommand\":true, \"openingTime\":1340011268877},{\"id\":9,\"serverNo\":9,\"serverName\":\"server9\",\"gameId\":1, \"serverStatus\":2,\"recommand\":true,\"openingTime\":1340111268877},{\"id\":6,\"serverNo\":6,\"serverName\":\"server6\",\"gameId\":1, \"serverStatus\":1,\"recommand\":true, \"openingTime\":1340011268877},{\"id\":9,\"serverNo\":9,\"serverName\":\"server9\",\"gameId\":1, \"serverStatus\":2,\"recommand\":true,\"openingTime\":1340111268877},{\"id\":6,\"serverNo\":6,\"serverName\":\"server6\",\"gameId\":1, \"serverStatus\":1,\"recommand\":true, \"openingTime\":1340011268877},{\"id\":9,\"serverNo\":9,\"serverName\":\"server9\",\"gameId\":1, \"serverStatus\":2,\"recommand\":true,\"openingTime\":1340111268877},{\"id\":6,\"serverNo\":6,\"serverName\":\"server6\",\"gameId\":1, \"serverStatus\":1,\"recommand\":true, \"openingTime\":1340011268877},{\"id\":9,\"serverNo\":9,\"serverName\":\"server9\",\"gameId\":1, \"serverStatus\":2,\"recommand\":true,\"openingTime\":1340111268877},{\"id\":6,\"serverNo\":6,\"serverName\":\"server6\",\"gameId\":1, \"serverStatus\":1,\"recommand\":true, \"openingTime\":1340011268877},{\"id\":9,\"serverNo\":9,\"serverName\":\"server9\",\"gameId\":1, \"serverStatus\":2,\"recommand\":true,\"openingTime\":1340111268877},{\"id\":6,\"serverNo\":6,\"serverName\":\"server6\",\"gameId\":1, \"serverStatus\":1,\"recommand\":true, \"openingTime\":1340011268877},{\"id\":9,\"serverNo\":9,\"serverName\":\"server9\",\"gameId\":1, \"serverStatus\":2,\"recommand\":true,\"openingTime\":1340111268877},{\"id\":6,\"serverNo\":6,\"serverName\":\"server6\",\"gameId\":1, \"serverStatus\":1,\"recommand\":true, \"openingTime\":1340011268877},{\"id\":9,\"serverNo\":9,\"serverName\":\"server9\",\"gameId\":1, \"serverStatus\":2,\"recommand\":true,\"openingTime\":1340111268877},{\"id\":6,\"serverNo\":6,\"serverName\":\"server6\",\"gameId\":1, \"serverStatus\":1,\"recommand\":true, \"openingTime\":1340011268877},{\"id\":9,\"serverNo\":9,\"serverName\":\"server9\",\"gameId\":1, \"serverStatus\":2,\"recommand\":true,\"openingTime\":1340111268877},{\"id\":6,\"serverNo\":6,\"serverName\":\"server6\",\"gameId\":1, \"serverStatus\":1,\"recommand\":true, \"openingTime\":1340011268877},{\"id\":9,\"serverNo\":9,\"serverName\":\"server9\",\"gameId\":1, \"serverStatus\":2,\"recommand\":true,\"openingTime\":1340111268877},{\"id\":6,\"serverNo\":6,\"serverName\":\"server6\",\"gameId\":1, \"serverStatus\":1,\"recommand\":true, \"openingTime\":1340011268877},{\"id\":9,\"serverNo\":9,\"serverName\":\"server9\",\"gameId\":1, \"serverStatus\":2,\"recommand\":true,\"openingTime\":1340111268877},{\"id\":6,\"serverNo\":6,\"serverName\":\"server6\",\"gameId\":1, \"serverStatus\":1,\"recommand\":true, \"openingTime\":1340011268877},{\"id\":9,\"serverNo\":9,\"serverName\":\"server9\",\"gameId\":1, \"serverStatus\":2,\"recommand\":true,\"openingTime\":1340111268877},{\"id\":6,\"serverNo\":6,\"serverName\":\"server6\",\"gameId\":1, \"serverStatus\":1,\"recommand\":true, \"openingTime\":1340011268877},{\"id\":9,\"serverNo\":9,\"serverName\":\"server9\",\"gameId\":1, \"serverStatus\":2,\"recommand\":true,\"openingTime\":1340111268877},{\"id\":6,\"serverNo\":6,\"serverName\":\"server6\",\"gameId\":1, \"serverStatus\":1,\"recommand\":true, \"openingTime\":1340011268877},{\"id\":9,\"serverNo\":9,\"serverName\":\"server9\",\"gameId\":1, \"serverStatus\":2,\"recommand\":true,\"openingTime\":1340111268877},{\"id\":6,\"serverNo\":6,\"serverName\":\"server6\",\"gameId\":1, \"serverStatus\":1,\"recommand\":true, \"openingTime\":1340011268877},{\"id\":9,\"serverNo\":9,\"serverName\":\"server9\",\"gameId\":1, \"serverStatus\":2,\"recommand\":true,\"openingTime\":1340111268877},{\"id\":6,\"serverNo\":6,\"serverName\":\"server6\",\"gameId\":1, \"serverStatus\":1,\"recommand\":true, \"openingTime\":1340011268877},{\"id\":9,\"serverNo\":9,\"serverName\":\"server9\",\"gameId\":1, \"serverStatus\":2,\"recommand\":true,\"openingTime\":1340111268877},{\"id\":6,\"serverNo\":6,\"serverName\":\"server6\",\"gameId\":1, \"serverStatus\":1,\"recommand\":true, \"openingTime\":1340011268877},{\"id\":9,\"serverNo\":9,\"serverName\":\"server9\",\"gameId\":1, \"serverStatus\":2,\"recommand\":true,\"openingTime\":1340111268877},{\"id\":6,\"serverNo\":6,\"serverName\":\"server6\",\"gameId\":1, \"serverStatus\":1,\"recommand\":true, \"openingTime\":1340011268877},{\"id\":9,\"serverNo\":9,\"serverName\":\"server9\",\"gameId\":1, \"serverStatus\":2,\"recommand\":true,\"openingTime\":1340111268877},{\"id\":6,\"serverNo\":6,\"serverName\":\"server6\",\"gameId\":1, \"serverStatus\":1,\"recommand\":true, \"openingTime\":1340011268877},{\"id\":9,\"serverNo\":9,\"serverName\":\"server9\",\"gameId\":1, \"serverStatus\":2,\"recommand\":true,\"openingTime\":1340111268877},{\"id\":6,\"serverNo\":6,\"serverName\":\"server6\",\"gameId\":1, \"serverStatus\":1,\"recommand\":true, \"openingTime\":1340011268877},{\"id\":9,\"serverNo\":9,\"serverName\":\"server9\",\"gameId\":1, \"serverStatus\":2,\"recommand\":true,\"openingTime\":1340111268877},{\"id\":6,\"serverNo\":6,\"serverName\":\"server6\",\"gameId\":1, \"serverStatus\":1,\"recommand\":true, \"openingTime\":1340011268877},{\"id\":9,\"serverNo\":9,\"serverName\":\"server9\",\"gameId\":1, \"serverStatus\":2,\"recommand\":true,\"openingTime\":1340111268877},{\"id\":6,\"serverNo\":6,\"serverName\":\"server6\",\"gameId\":1, \"serverStatus\":1,\"recommand\":true, \"openingTime\":1340011268877},{\"id\":9,\"serverNo\":9,\"serverName\":\"server9\",\"gameId\":1, \"serverStatus\":2,\"recommand\":true,\"openingTime\":1340111268877},{\"id\":6,\"serverNo\":6,\"serverName\":\"server6\",\"gameId\":1, \"serverStatus\":1,\"recommand\":true, \"openingTime\":1340011268877},{\"id\":9,\"serverNo\":9,\"serverName\":\"server9\",\"gameId\":1, \"serverStatus\":2,\"recommand\":true,\"openingTime\":1340111268877},{\"id\":6,\"serverNo\":6,\"serverName\":\"server6\",\"gameId\":1, \"serverStatus\":1,\"recommand\":true, \"openingTime\":1340011268877},{\"id\":9,\"serverNo\":9,\"serverName\":\"server9\",\"gameId\":1, \"serverStatus\":2,\"recommand\":true,\"openingTime\":1340111268877},{\"id\":6,\"serverNo\":6,\"serverName\":\"server6\",\"gameId\":1, \"serverStatus\":1,\"recommand\":true, \"openingTime\":1340011268877},{\"id\":9,\"serverNo\":9,\"serverName\":\"server9\",\"gameId\":1, \"serverStatus\":2,\"recommand\":true,\"openingTime\":1340111268877},{\"id\":6,\"serverNo\":6,\"serverName\":\"server6\",\"gameId\":1, \"serverStatus\":1,\"recommand\":true, \"openingTime\":1340011268877},{\"id\":9,\"serverNo\":9,\"serverName\":\"server9\",\"gameId\":1, \"serverStatus\":2,\"recommand\":true,\"openingTime\":1340111268877},{\"id\":6,\"serverNo\":6,\"serverName\":\"server6\",\"gameId\":1, \"serverStatus\":1,\"recommand\":true, \"openingTime\":1340011268877},{\"id\":9,\"serverNo\":9,\"serverName\":\"server9\",\"gameId\":1, \"serverStatus\":2,\"recommand\":true,\"openingTime\":1340111268877},{\"id\":6,\"serverNo\":6,\"serverName\":\"server6\",\"gameId\":1, \"serverStatus\":1,\"recommand\":true, \"openingTime\":1340011268877},{\"id\":9,\"serverNo\":9,\"serverName\":\"server9\",\"gameId\":1, \"serverStatus\":2,\"recommand\":true,\"openingTime\":1340111268877},{\"id\":6,\"serverNo\":6,\"serverName\":\"server6\",\"gameId\":1, \"serverStatus\":1,\"recommand\":true, \"openingTime\":1340011268877},{\"id\":9,\"serverNo\":9,\"serverName\":\"server9\",\"gameId\":1, \"serverStatus\":2,\"recommand\":true,\"openingTime\":1340111268877},{\"id\":6,\"serverNo\":6,\"serverName\":\"server6\",\"gameId\":1, \"serverStatus\":1,\"recommand\":true, \"openingTime\":1340011268877},{\"id\":9,\"serverNo\":9,\"serverName\":\"server9\",\"gameId\":1, \"serverStatus\":2,\"recommand\":true,\"openingTime\":1340111268877},{\"id\":6,\"serverNo\":6,\"serverName\":\"server6\",\"gameId\":1, \"serverStatus\":1,\"recommand\":true, \"openingTime\":1340011268877},{\"id\":9,\"serverNo\":9,\"serverName\":\"server9\",\"gameId\":1, \"serverStatus\":2,\"recommand\":true,\"openingTime\":1340111268877},{\"id\":6,\"serverNo\":6,\"serverName\":\"server6\",\"gameId\":1, \"serverStatus\":1,\"recommand\":true, \"openingTime\":1340011268877},{\"id\":9,\"serverNo\":9,\"serverName\":\"server9\",\"gameId\":1, \"serverStatus\":2,\"recommand\":true,\"openingTime\":1340111268877},{\"id\":6,\"serverNo\":6,\"serverName\":\"server6\",\"gameId\":1, \"serverStatus\":1,\"recommand\":true, \"openingTime\":1340011268877},{\"id\":9,\"serverNo\":9,\"serverName\":\"server9\",\"gameId\":1, \"serverStatus\":2,\"recommand\":true,\"openingTime\":1340111268877},{\"id\":6,\"serverNo\":6,\"serverName\":\"server6\",\"gameId\":1, \"serverStatus\":1,\"recommand\":true, \"openingTime\":1340011268877},{\"id\":9,\"serverNo\":9,\"serverName\":\"server9\",\"gameId\":1, \"serverStatus\":2,\"recommand\":true,\"openingTime\":1340111268877},{\"id\":6,\"serverNo\":6,\"serverName\":\"server6\",\"gameId\":1, \"serverStatus\":1,\"recommand\":true, \"openingTime\":1340011268877},{\"id\":9,\"serverNo\":9,\"serverName\":\"server9\",\"gameId\":1, \"serverStatus\":2,\"recommand\":true,\"openingTime\":1340111268877},{\"id\":6,\"serverNo\":6,\"serverName\":\"server6\",\"gameId\":1, \"serverStatus\":1,\"recommand\":true, \"openingTime\":1340011268877},{\"id\":9,\"serverNo\":9,\"serverName\":\"server9\",\"gameId\":1, \"serverStatus\":2,\"recommand\":true,\"openingTime\":1340111268877},{\"id\":6,\"serverNo\":6,\"serverName\":\"server6\",\"gameId\":1, \"serverStatus\":1,\"recommand\":true, \"openingTime\":1340011268877},{\"id\":9,\"serverNo\":9,\"serverName\":\"server9\",\"gameId\":1, \"serverStatus\":2,\"recommand\":true,\"openingTime\":1340111268877},{\"id\":6,\"serverNo\":6,\"serverName\":\"server6\",\"gameId\":1, \"serverStatus\":1,\"recommand\":true, \"openingTime\":1340011268877},{\"id\":9,\"serverNo\":9,\"serverName\":\"server9\",\"gameId\":1, \"serverStatus\":2,\"recommand\":true,\"openingTime\":1340111268877},{\"id\":6,\"serverNo\":6,\"serverName\":\"server6\",\"gameId\":1, \"serverStatus\":1,\"recommand\":true, \"openingTime\":1340011268877},{\"id\":9,\"serverNo\":9,\"serverName\":\"server9\",\"gameId\":1, \"serverStatus\":2,\"recommand\":true,\"openingTime\":1340111268877},{\"id\":6,\"serverNo\":6,\"serverName\":\"server6\",\"gameId\":1, \"serverStatus\":1,\"recommand\":true, \"openingTime\":1340011268877},{\"id\":9,\"serverNo\":9,\"serverName\":\"server9\",\"gameId\":1, \"serverStatus\":2,\"recommand\":true,\"openingTime\":1340111268877},{\"id\":6,\"serverNo\":6,\"serverName\":\"server6\",\"gameId\":1, \"serverStatus\":1,\"recommand\":true, \"openingTime\":1340011268877},{\"id\":9,\"serverNo\":9,\"serverName\":\"server9\",\"gameId\":1, \"serverStatus\":2,\"recommand\":true,\"openingTime\":1340111268877},{\"id\":6,\"serverNo\":6,\"serverName\":\"server6\",\"gameId\":1, \"serverStatus\":1,\"recommand\":true, \"openingTime\":1340011268877},{\"id\":9,\"serverNo\":9,\"serverName\":\"server9\",\"gameId\":1, \"serverStatus\":2,\"recommand\":true,\"openingTime\":1340111268877},{\"id\":6,\"serverNo\":6,\"serverName\":\"server6\",\"gameId\":1, \"serverStatus\":1,\"recommand\":true, \"openingTime\":1340011268877},{\"id\":9,\"serverNo\":9,\"serverName\":\"server9\",\"gameId\":1, \"serverStatus\":2,\"recommand\":true,\"openingTime\":1340111268877},{\"id\":6,\"serverNo\":6,\"serverName\":\"server6\",\"gameId\":1, \"serverStatus\":1,\"recommand\":true, \"openingTime\":1340011268877},{\"id\":9,\"serverNo\":9,\"serverName\":\"server9\",\"gameId\":1, \"serverStatus\":2,\"recommand\":true,\"openingTime\":1340111268877},{\"id\":6,\"serverNo\":6,\"serverName\":\"server6\",\"gameId\":1, \"serverStatus\":1,\"recommand\":true, \"openingTime\":1340011268877},{\"id\":9,\"serverNo\":9,\"serverName\":\"server9\",\"gameId\":1, \"serverStatus\":2,\"recommand\":true,\"openingTime\":1340111268877},{\"id\":6,\"serverNo\":6,\"serverName\":\"server6\",\"gameId\":1, \"serverStatus\":1,\"recommand\":true, \"openingTime\":1340011268877},{\"id\":9,\"serverNo\":9,\"serverName\":\"server9\",\"gameId\":1, \"serverStatus\":2,\"recommand\":true,\"openingTime\":1340111268877},{\"id\":6,\"serverNo\":6,\"serverName\":\"server6\",\"gameId\":1, \"serverStatus\":1,\"recommand\":true, \"openingTime\":1340011268877},{\"id\":9,\"serverNo\":9,\"serverName\":\"server9\",\"gameId\":1, \"serverStatus\":2,\"recommand\":true,\"openingTime\":1340111268877},{\"id\":6,\"serverNo\":6,\"serverName\":\"server6\",\"gameId\":1, \"serverStatus\":1,\"recommand\":true, \"openingTime\":1340011268877},{\"id\":9,\"serverNo\":9,\"serverName\":\"server9\",\"gameId\":1, \"serverStatus\":2,\"recommand\":true,\"openingTime\":1340111268877},{\"id\":6,\"serverNo\":6,\"serverName\":\"server6\",\"gameId\":1, \"serverStatus\":1,\"recommand\":true, \"openingTime\":1340011268877},{\"id\":9,\"serverNo\":9,\"serverName\":\"server9\",\"gameId\":1, \"serverStatus\":2,\"recommand\":true,\"openingTime\":1340111268877},{\"id\":6,\"serverNo\":6,\"serverName\":\"server6\",\"gameId\":1, \"serverStatus\":1,\"recommand\":true, \"openingTime\":1340011268877},{\"id\":9,\"serverNo\":9,\"serverName\":\"server9\",\"gameId\":1, \"serverStatus\":2,\"recommand\":true,\"openingTime\":1340111268877},{\"id\":6,\"serverNo\":6,\"serverName\":\"server6\",\"gameId\":1, \"serverStatus\":1,\"recommand\":true, \"openingTime\":1340011268877},{\"id\":9,\"serverNo\":9,\"serverName\":\"server9\",\"gameId\":1, \"serverStatus\":2,\"recommand\":true,\"openingTime\":1340111268877},{\"id\":6,\"serverNo\":6,\"serverName\":\"server6\",\"gameId\":1, \"serverStatus\":1,\"recommand\":true, \"openingTime\":1340011268877},{\"id\":9,\"serverNo\":9,\"serverName\":\"server9\",\"gameId\":1, \"serverStatus\":2,\"recommand\":true,\"openingTime\":1340111268877},{\"id\":6,\"serverNo\":6,\"serverName\":\"server6\",\"gameId\":1, \"serverStatus\":1,\"recommand\":true, \"openingTime\":1340011268877},{\"id\":9,\"serverNo\":9,\"serverName\":\"server9\",\"gameId\":1, \"serverStatus\":2,\"recommand\":true,\"openingTime\":1340111268877},{\"id\":6,\"serverNo\":6,\"serverName\":\"server6\",\"gameId\":1, \"serverStatus\":1,\"recommand\":true, \"openingTime\":1340011268877},{\"id\":9,\"serverNo\":9,\"serverName\":\"server9\",\"gameId\":1, \"serverStatus\":2,\"recommand\":true,\"openingTime\":1340111268877},{\"id\":6,\"serverNo\":6,\"serverName\":\"server6\",\"gameId\":1, \"serverStatus\":1,\"recommand\":true, \"openingTime\":1340011268877},{\"id\":9,\"serverNo\":9,\"serverName\":\"server9\",\"gameId\":1, \"serverStatus\":2,\"recommand\":true,\"openingTime\":1340111268877},{\"id\":6,\"serverNo\":6,\"serverName\":\"server6\",\"gameId\":1, \"serverStatus\":1,\"recommand\":true, \"openingTime\":1340011268877},{\"id\":9,\"serverNo\":9,\"serverName\":\"server9\",\"gameId\":1, \"serverStatus\":2,\"recommand\":true,\"openingTime\":1340111268877},{\"id\":6,\"serverNo\":6,\"serverName\":\"server6\",\"gameId\":1, \"serverStatus\":1,\"recommand\":true, \"openingTime\":1340011268877},{\"id\":9,\"serverNo\":9,\"serverName\":\"server9\",\"gameId\":1, \"serverStatus\":2,\"recommand\":true,\"openingTime\":1340111268877},{\"id\":6,\"serverNo\":6,\"serverName\":\"server6\",\"gameId\":1, \"serverStatus\":1,\"recommand\":true, \"openingTime\":1340011268877},{\"id\":9,\"serverNo\":9,\"serverName\":\"server9\",\"gameId\":1, \"serverStatus\":2,\"recommand\":true,\"openingTime\":1340111268877},{\"id\":6,\"serverNo\":6,\"serverName\":\"server6\",\"gameId\":1, \"serverStatus\":1,\"recommand\":true, \"openingTime\":1340011268877},{\"id\":9,\"serverNo\":9,\"serverName\":\"server9\",\"gameId\":1, \"serverStatus\":2,\"recommand\":true,\"openingTime\":1340111268877},{\"id\":6,\"serverNo\":6,\"serverName\":\"server6\",\"gameId\":1, \"serverStatus\":1,\"recommand\":true, \"openingTime\":1340011268877},{\"id\":9,\"serverNo\":9,\"serverName\":\"server9\",\"gameId\":1, \"serverStatus\":2,\"recommand\":true,\"openingTime\":1340111268877},{\"id\":6,\"serverNo\":6,\"serverName\":\"server6\",\"gameId\":1, \"serverStatus\":1,\"recommand\":true, \"openingTime\":1340011268877},{\"id\":9,\"serverNo\":9,\"serverName\":\"server9\",\"gameId\":1, \"serverStatus\":2,\"recommand\":true,\"openingTime\":1340111268877},{\"id\":6,\"serverNo\":6,\"serverName\":\"server6\",\"gameId\":1, \"serverStatus\":1,\"recommand\":true, \"openingTime\":1340011268877},{\"id\":9,\"serverNo\":9,\"serverName\":\"server9\",\"gameId\":1, \"serverStatus\":2,\"recommand\":true,\"openingTime\":1340111268877},{\"id\":6,\"serverNo\":6,\"serverName\":\"server6\",\"gameId\":1, \"serverStatus\":1,\"recommand\":true, \"openingTime\":1340011268877},{\"id\":9,\"serverNo\":9,\"serverName\":\"server9\",\"gameId\":1, \"serverStatus\":2,\"recommand\":true,\"openingTime\":1340111268877},{\"id\":6,\"serverNo\":6,\"serverName\":\"server6\",\"gameId\":1, \"serverStatus\":1,\"recommand\":true, \"openingTime\":1340011268877},{\"id\":9,\"serverNo\":9,\"serverName\":\"server9\",\"gameId\":1, \"serverStatus\":2,\"recommand\":true,\"openingTime\":1340111268877},{\"id\":6,\"serverNo\":6,\"serverName\":\"server6\",\"gameId\":1, \"serverStatus\":1,\"recommand\":true, \"openingTime\":1340011268877},{\"id\":9,\"serverNo\":9,\"serverName\":\"server9\",\"gameId\":1, \"serverStatus\":2,\"recommand\":true,\"openingTime\":1340111268877},{\"id\":6,\"serverNo\":6,\"serverName\":\"server6\",\"gameId\":1, \"serverStatus\":1,\"recommand\":true, \"openingTime\":1340011268877},{\"id\":9,\"serverNo\":9,\"serverName\":\"server9\",\"gameId\":1, \"serverStatus\":2,\"recommand\":true,\"openingTime\":1340111268877},{\"id\":6,\"serverNo\":6,\"serverName\":\"server6\",\"gameId\":1, \"serverStatus\":1,\"recommand\":true, \"openingTime\":1340011268877},{\"id\":9,\"serverNo\":9,\"serverName\":\"server9\",\"gameId\":1, \"serverStatus\":2,\"recommand\":true,\"openingTime\":1340111268877},{\"id\":6,\"serverNo\":6,\"serverName\":\"server6\",\"gameId\":1, \"serverStatus\":1,\"recommand\":true, \"openingTime\":1340011268877},{\"id\":9,\"serverNo\":9,\"serverName\":\"server9\",\"gameId\":1, \"serverStatus\":2,\"recommand\":true,\"openingTime\":1340111268877},{\"id\":6,\"serverNo\":6,\"serverName\":\"server6\",\"gameId\":1, \"serverStatus\":1,\"recommand\":true, \"openingTime\":1340011268877},{\"id\":9,\"serverNo\":9,\"serverName\":\"server9\",\"gameId\":1, \"serverStatus\":2,\"recommand\":true,\"openingTime\":1340111268877},{\"id\":6,\"serverNo\":6,\"serverName\":\"server6\",\"gameId\":1, \"serverStatus\":1,\"recommand\":true, \"openingTime\":1340011268877},{\"id\":9,\"serverNo\":9,\"serverName\":\"server9\",\"gameId\":1, \"serverStatus\":2,\"recommand\":true,\"openingTime\":1340111268877},{\"id\":6,\"serverNo\":6,\"serverName\":\"server6\",\"gameId\":1, \"serverStatus\":1,\"recommand\":true, \"openingTime\":1340011268877},{\"id\":9,\"serverNo\":9,\"serverName\":\"server9\",\"gameId\":1, \"serverStatus\":2,\"recommand\":true,\"openingTime\":1340111268877},{\"id\":6,\"serverNo\":6,\"serverName\":\"server6\",\"gameId\":1, \"serverStatus\":1,\"recommand\":true, \"openingTime\":1340011268877},{\"id\":9,\"serverNo\":9,\"serverName\":\"server9\",\"gameId\":1, \"serverStatus\":2,\"recommand\":true,\"openingTime\":1340111268877},{\"id\":6,\"serverNo\":6,\"serverName\":\"server6\",\"gameId\":1, \"serverStatus\":1,\"recommand\":true, \"openingTime\":1340011268877},{\"id\":9,\"serverNo\":9,\"serverName\":\"server9\",\"gameId\":1, \"serverStatus\":2,\"recommand\":true,\"openingTime\":1340111268877},{\"id\":6,\"serverNo\":6,\"serverName\":\"server6\",\"gameId\":1, \"serverStatus\":1,\"recommand\":true, \"openingTime\":1340011268877},{\"id\":9,\"serverNo\":9,\"serverName\":\"server9\",\"gameId\":1, \"serverStatus\":2,\"recommand\":true,\"openingTime\":1340111268877},{\"id\":6,\"serverNo\":6,\"serverName\":\"server6\",\"gameId\":1, \"serverStatus\":1,\"recommand\":true, \"openingTime\":1340011268877},{\"id\":9,\"serverNo\":9,\"serverName\":\"server9\",\"gameId\":1, \"serverStatus\":2,\"recommand\":true,\"openingTime\":1340111268877},{\"id\":6,\"serverNo\":6,\"serverName\":\"server6\",\"gameId\":1, \"serverStatus\":1,\"recommand\":true, \"openingTime\":1340011268877},{\"id\":9,\"serverNo\":9,\"serverName\":\"server9\",\"gameId\":1, \"serverStatus\":2,\"recommand\":true,\"openingTime\":1340111268877},{\"id\":6,\"serverNo\":6,\"serverName\":\"server6\",\"gameId\":1, \"serverStatus\":1,\"recommand\":true, \"openingTime\":1340011268877},{\"id\":9,\"serverNo\":9,\"serverName\":\"server9\",\"gameId\":1, \"serverStatus\":2,\"recommand\":true,\"openingTime\":1340111268877},{\"id\":6,\"serverNo\":6,\"serverName\":\"server6\",\"gameId\":1, \"serverStatus\":1,\"recommand\":true, \"openingTime\":1340011268877},{\"id\":9,\"serverNo\":9,\"serverName\":\"server9\",\"gameId\":1, \"serverStatus\":2,\"recommand\":true,\"openingTime\":1340111268877},{\"id\":6,\"serverNo\":6,\"serverName\":\"server6\",\"gameId\":1, \"serverStatus\":1,\"recommand\":true, \"openingTime\":1340011268877},{\"id\":9,\"serverNo\":9,\"serverName\":\"server9\",\"gameId\":1, \"serverStatus\":2,\"recommand\":true,\"openingTime\":1340111268877},{\"id\":6,\"serverNo\":6,\"serverName\":\"server6\",\"gameId\":1, \"serverStatus\":1,\"recommand\":true, \"openingTime\":1340011268877},{\"id\":9,\"serverNo\":9,\"serverName\":\"server9\",\"gameId\":1, \"serverStatus\":2,\"recommand\":true,\"openingTime\":1340111268877},{\"id\":6,\"serverNo\":6,\"serverName\":\"server6\",\"gameId\":1, \"serverStatus\":1,\"recommand\":true, \"openingTime\":1340011268877},{\"id\":9,\"serverNo\":9,\"serverName\":\"server9\",\"gameId\":1, \"serverStatus\":2,\"recommand\":true,\"openingTime\":1340111268877},{\"id\":6,\"serverNo\":6,\"serverName\":\"server6\",\"gameId\":1, \"serverStatus\":1,\"recommand\":true, \"openingTime\":1340011268877},{\"id\":9,\"serverNo\":9,\"serverName\":\"server9\",\"gameId\":1, \"serverStatus\":2,\"recommand\":true,\"openingTime\":1340111268877},{\"id\":6,\"serverNo\":6,\"serverName\":\"server6\",\"gameId\":1, \"serverStatus\":1,\"recommand\":true, \"openingTime\":1340011268877},{\"id\":9,\"serverNo\":9,\"serverName\":\"server9\",\"gameId\":1, \"serverStatus\":2,\"recommand\":true,\"openingTime\":1340111268877},{\"id\":6,\"serverNo\":6,\"serverName\":\"server6\",\"gameId\":1, \"serverStatus\":1,\"recommand\":true, \"openingTime\":1340011268877},{\"id\":9,\"serverNo\":9,\"serverName\":\"server9\",\"gameId\":1, \"serverStatus\":2,\"recommand\":true,\"openingTime\":1340111268877},{\"id\":6,\"serverNo\":6,\"serverName\":\"server6\",\"gameId\":1, \"serverStatus\":1,\"recommand\":true, \"openingTime\":1340011268877},{\"id\":9,\"serverNo\":9,\"serverName\":\"server9\",\"gameId\":1, \"serverStatus\":2,\"recommand\":true,\"openingTime\":1340111268877},{\"id\":6,\"serverNo\":6,\"serverName\":\"server6\",\"gameId\":1, \"serverStatus\":1,\"recommand\":true, \"openingTime\":1340011268877},{\"id\":9,\"serverNo\":9,\"serverName\":\"server9\",\"gameId\":1, \"serverStatus\":2,\"recommand\":true,\"openingTime\":1340111268877},{\"id\":6,\"serverNo\":6,\"serverName\":\"server6\",\"gameId\":1, \"serverStatus\":1,\"recommand\":true, \"openingTime\":1340011268877},{\"id\":9,\"serverNo\":9,\"serverName\":\"server9\",\"gameId\":1, \"serverStatus\":2,\"recommand\":true,\"openingTime\":1340111268877}]";
		if (response != null) {
			list = JsonUtil.jsonStrToList(response, PlatformGameServer.class);
		}

		return list;
	}

	// 查询最新推荐服务区（前台）
	public PlatformGameServer findLastRecommendServer() throws Exception {
		String response = null;
		PlatformGameServer server = null;
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("gameId", String.valueOf(AppConstants.GAME_ID)));
		response = HttpClientUtil.post(ONLINE_LATEST_GAME_SERVER_URL, nvps);
		if (response != null) {
			server = JsonUtil.jsonStrToBean(response, PlatformGameServer.class);
		}
		return server;
	}

	// 验证登陆游戏（前台）
	public String checkLoginGame(String gameUrl) throws Exception {
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		
		String response = HttpClientUtil.post(gameUrl + "&status=1", nvps);

		return response;
	}

	// 取得玩家排行列表url
	public List<RoleRankInfo> findRoleRankList() throws Exception {
		List<RoleRankInfo> roleRankList = null;

		String url = SystemConfig
				.getProperty(AppConstants.PLATFORM_MANAGER_BASE_URL)
				+ "/getRoleRankUrl";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair(AppConstants.ACTION, "GameRankInfo"));
		nvps.add(new BasicNameValuePair("gameId", String.valueOf(1)));
		String response = HttpClientUtil.post(url, nvps);
		if (response != null && !"".equals(response)) {
			int total = 0;
			if (response.indexOf(AppConstants.TOTAL) != -1) {
				JSONObject jsonObj = JSONObject.fromObject(response);
				total = jsonObj.getInt("total");
			}
			if (total > 0 && !(response.indexOf("data") == -1)) {
				roleRankList = JsonUtil.jsonStrToGetList(response, "data",
						RoleRankInfo.class);
			}
		}

		return roleRankList;
	}

	// 测试充值
	public String testRecharge(String sign, String userId, int gameId,
			int zoneId, String amount) throws Exception {
		String url = SystemConfig
				.getProperty(AppConstants.PLATFORM_ACCESS_BASE_URL)
				+ "/RechargeTest";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("sign", sign));
		nvps.add(new BasicNameValuePair("u", userId));
		nvps.add(new BasicNameValuePair("_g", String.valueOf(gameId)));
		nvps.add(new BasicNameValuePair("_z", String.valueOf(zoneId)));
		nvps.add(new BasicNameValuePair("amount", amount));
		String response = HttpClientUtil.post(url, nvps);

		return response;
	}

	// 后台进入游戏测试
	public String testEnterGame(String sign) throws Exception {
		String url = SystemConfig
				.getProperty(AppConstants.PLATFORM_ACCESS_BASE_URL)
				+ "/PlayGame2";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("sign", sign));
		String response = HttpClientUtil.post(url, nvps);

		return response;
	}
	
	/**
	 * 查询是否已领取新手卡激活码
	 * 	已领取:返回已领的激活码
	 * 	未领取：返回false
	 * @param userId
	 * @param site
	 * @return
	 * @throws Exception
	 */
	public String queryNaviceCardCDKey(String userId, String site) throws Exception {
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		String activityId = SystemConfig.getProperty("activeCode.id_01");
		String sign = MD5Util.getMD5String(activityId + "7road");
		nvps.add(new BasicNameValuePair("activityId", activityId));
		nvps.add(new BasicNameValuePair("sign", sign));
		nvps.add(new BasicNameValuePair("userId", userId));
		nvps.add(new BasicNameValuePair("site", site));
		
		String url = SystemConfig.getProperty("activeCode.url1");
		
		return HttpClientUtil.post(url, nvps);
	}
	
	/**
	 * 获得新手礼包激活码
	 * @param userId
	 * @param site
	 * @return
	 * @throws Exception
	 */
	public String queryNaviceCardSingleCDKey(String userId, String site) throws Exception {
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		String activityId = SystemConfig.getProperty("activeCode.id_01");
		String sign = MD5Util.getMD5String(activityId + "7road");
		nvps.add(new BasicNameValuePair("activityId", activityId));
		nvps.add(new BasicNameValuePair("sign", sign));
		nvps.add(new BasicNameValuePair("userId", userId));
		nvps.add(new BasicNameValuePair("site", site));
		nvps.add(new BasicNameValuePair("giftBagType", SystemConfig.getProperty("activeCode.giftBagType")));
		nvps.add(new BasicNameValuePair("useScope", SystemConfig.getProperty("activeCode.useScope")));
		
		String url = SystemConfig.getProperty("activeCode.url2");
		
		return HttpClientUtil.post(url, nvps);
	}

	// 处理对象格式 to get list
	public List<PlatformGameServer> jsonStrToList(String str) {
		List<PlatformGameServer> list = JsonUtil.jsonStrToGetList(str,
				AppConstants.GAME_LIST, PlatformGameServer.class);
		GameServerServiceImpl gsServ = GameServerServiceImpl.getInstance();
		for (PlatformGameServer server : list) {
			if (server != null && server.getServerStatus() != null) {
				String openingTimeStamp = server.getOpeningTime();
				if (openingTimeStamp != null) {
					String openingTimeStampFormat = DateUtil.format(new Date(
							Long.parseLong(openingTimeStamp)));
					server.setOpeningTime(openingTimeStampFormat);
				}
			}
		}
		return list;
	}

	// 处理对象格式 to bean
	public PlatformGameServer jsonStrToBean(String jsonString) {
		PlatformGameServer server = JsonUtil.jsonStrToBean(jsonString,
				PlatformGameServer.class);
		server = this.formatTimeInBean(server);

		return server;
	}

	// 处理对象时间格式
	public PlatformGameServer formatTimeInBean(PlatformGameServer server) {
		String openingTimeStamp = server.getOpeningTime();
		if (openingTimeStamp != null) {
			String openingTimeStampFormat = DateUtil.format(new Date(Long
					.parseLong(openingTimeStamp)));
			server.setOpeningTime(openingTimeStampFormat);
		}

		return server;
	}

	// 处理对象格式 to jsonArray
	public JSON beanToJSONArray(PlatformGameServer... server) {
		for (PlatformGameServer gameServer : server) {
			String openingTimeFormat = gameServer.getOpeningTime();
			if (openingTimeFormat != null) {
				String openingTimeStamp = String.valueOf(DateUtil.parse(
						openingTimeFormat).getTime());
				gameServer.setOpeningTime(openingTimeStamp);
			}
		}
		JSON jsonArray = JsonUtil.beanToJSONArray(server);

		return jsonArray;
	}

	public PlatformGameServer findGameServerById(int gameId, int serverId)
			throws Exception {
		List<PlatformGameServer> list = this.findAllGameServer();
		if (null != list && list.size() != 0) {
			for (PlatformGameServer server : list) {
				if (server.getServerNo().equals(String.valueOf(serverId))
						&& server.getGameId().equals(String.valueOf(gameId))) {
					return server;
				}
			}
		}
		return null;
	}

}
