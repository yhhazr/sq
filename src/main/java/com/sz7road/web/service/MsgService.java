package com.sz7road.web.service;

import java.util.List;

import com.sz7road.web.model.messagemanage.MsgInfo;
import com.sz7road.web.model.messagemanage.MsgInfoEx;
import com.sz7road.web.model.messagemanage.MsgOpType;
import com.sz7road.web.model.messagemanage.MsgState;
import com.sz7road.web.model.pagination.PageInfo;
import com.sz7road.web.model.pagination.PaginationResult;

public interface MsgService {

	public List<MsgOpType> getMsgOpTypeList() throws Exception;
	
	public List<MsgState> getMsgStateList() throws Exception;
	
	public PaginationResult<MsgInfoEx> getMsgInfoList(PageInfo pageInfo,int typeId,int stateId) throws Exception;
	
	public MsgInfoEx getMsgInfoById(int msgId) throws Exception;
	
	public boolean updateMsgInfo(MsgInfoEx info) throws Exception;
	
	public boolean deleteMsgInfo(List<Integer> idList) throws Exception; 
	
	public boolean createNewMsg(MsgInfo info) throws Exception;
	
	public List<MsgInfoEx> getMsgInfoByTitleId(int titleId, int typeId) throws Exception;
}
