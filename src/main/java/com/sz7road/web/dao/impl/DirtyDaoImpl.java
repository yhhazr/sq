package com.sz7road.web.dao.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

import com.sz7road.web.dao.DirtyDao;

public class DirtyDaoImpl implements DirtyDao{
	
	private static DirtyDaoImpl _instance = new DirtyDaoImpl();
	
	public static DirtyDaoImpl getInstance(){
		return _instance;
	}
	
	@Override
	public boolean writeDirty(String s) throws Exception {
		File file = DirtyDaoImpl.getRealPath();
		OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(file),"utf-8");
		BufferedWriter in = new BufferedWriter(out);
		//BufferedWriter in = new BufferedWriter(new FileWriter(file));
		String regex = ",|，|，|、";
		StringBuilder sb = new StringBuilder();
		String[] temp = s.trim().split(regex);
		for(String str:temp){
			sb.append(str.trim() + "-");
		}
		in.write(sb.toString());
		in.flush();
		in.close();
		return true;
	}
	@Override
	public Map<Integer,String> getDirty() throws Exception{
		Map<Integer,String> map = new HashMap<Integer,String>();
		File file = DirtyDaoImpl.getRealPath();
		InputStreamReader in = new InputStreamReader(new FileInputStream(file),"utf-8");
		BufferedReader read = new BufferedReader(in);
//		BufferedReader read = new BufferedReader(new FileReader(file));
		int i = 0;
		String str = null;
		while((str = read.readLine()) != null){
			String[] arr = str.split("-");
			for(int j = 0;j<arr.length;j++){
				map.put(i, arr[j]);
				i++;
			}
		}
		read.close();
		return map;
	}
	
	private static File getRealPath() throws Exception{
		String path = DirtyDaoImpl.class.getResource("/").getPath();
		File file = new File(path + "dirtyCode.txt");
		if(!file.exists()){
			file.createNewFile();
		}
		return file;
	}
	
}
