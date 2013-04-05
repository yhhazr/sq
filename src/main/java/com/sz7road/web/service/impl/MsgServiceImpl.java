package com.sz7road.web.service.impl;

import java.util.List;

import com.sz7road.web.dao.MsgDao;
import com.sz7road.web.dao.impl.MsgDaoImpl;
import com.sz7road.web.model.messagemanage.MsgInfo;
import com.sz7road.web.model.messagemanage.MsgInfoEx;
import com.sz7road.web.model.messagemanage.MsgOpType;
import com.sz7road.web.model.messagemanage.MsgState;
import com.sz7road.web.model.pagination.PageInfo;
import com.sz7road.web.model.pagination.PaginationResult;
import com.sz7road.web.service.MsgService;

public class MsgServiceImpl implements MsgService{

	private static MsgServiceImpl _this;
	private static MsgDao msgDao;
	static{
		msgDao = MsgDaoImpl.getInstance();
	}
	
	public synchronized static MsgServiceImpl getInstance(){
		if(_this == null){
			_this = new MsgServiceImpl();
		}
		return _this;
	}
	
	@Override
	public List<MsgOpType> getMsgOpTypeList() throws Exception {
		List<MsgOpType> msgTypeList = msgDao.getMsgOpTypeList();
		return msgTypeList;
	}

	@Override
	public List<MsgState> getMsgStateList() throws Exception {
		List<MsgState> msgStateList = msgDao.getMsgStateList();
		return msgStateList;
	}
	
	
	@Override
	public PaginationResult<MsgInfoEx> getMsgInfoList(PageInfo pageInfo,int typeId, int stateId) throws Exception {
		int total = msgDao.getMsgInfoCount(typeId, stateId);
		List<MsgInfoEx> MsgInfoList= msgDao.getMsgInfoList(pageInfo, typeId, stateId);
		PaginationResult<MsgInfoEx> pageResult = new PaginationResult<MsgInfoEx>(total,MsgInfoList);
		return pageResult;
	}

	@Override
	public MsgInfoEx getMsgInfoById(int msgId) throws Exception {
		MsgInfoEx msgInfo = new MsgInfoEx();
		msgInfo = msgDao.getMsgInfoById(msgId);
		return msgInfo;
	}

	@Override
	public boolean updateMsgInfo(MsgInfoEx info) throws Exception {
		boolean flag = msgDao.updateMsgInfo(info);
		return flag;
	}

	@Override
	public boolean deleteMsgInfo(List<Integer> idList) throws Exception {
		boolean flag = msgDao.deleteMsgInfo(idList);
		return flag;
	}

	@Override
	public boolean createNewMsg(MsgInfo info) throws Exception {
		boolean flag = msgDao.createNewMsg(info);
		return flag;
	}

	@Override
	public List<MsgInfoEx> getMsgInfoByTitleId(int titleId, int typeId)
			throws Exception {
		List<MsgInfoEx> list = msgDao.getMsgInfoByTitleId(titleId, typeId);
		return list;
	}

	

}
