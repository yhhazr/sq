/**
 * 
 */
package com.sz7road.web.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.pureart.persistement.database.easydb.DB;
import org.pureart.persistement.database.easydb.ParamReadStH;

import com.sz7road.web.dao.SkillTemplateDao;
import com.sz7road.web.model.skillTemplate.SkillTemplate;

/**
 * 游戏技能Dao实现
 * @author hokin.jim
 *
 */
public class SkillTemplateDaoImpl implements SkillTemplateDao {
	private static final String SELECT_SKILLTEMP_BY_GRADE_AND_PRTEMP = "select * from t_s_skilltemplate where NeedPlayerGrade<=? and PreTemplateId=? and TemplateId>? and TemplateId<?"; 
	private static final String SELECT_SKILLTEMP_BY_GRADE = "select * from t_s_skilltemplate where NeedPlayerGrade<=? and TemplateId>? and TemplateId<? "; 
	private static final String SELECT_SKILLTEMP_BY_ID="select * from t_s_skilltemplate where TemplateId=?";
	private static SkillTemplateDaoImpl _instance = new SkillTemplateDaoImpl();

	private SkillTemplateDaoImpl() {}
	  
	public static SkillTemplateDaoImpl getInstance() {
		return _instance;
	}
	
	@Override
	public List<SkillTemplate> getSkillTemplateList(final int job,final int grade, final String preTemplateId)
			throws Exception {
		// TODO Auto-generated method stub
		final List<SkillTemplate> resultList=new ArrayList<SkillTemplate>();
		DB.select(SELECT_SKILLTEMP_BY_GRADE_AND_PRTEMP, new ParamReadStH() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					SkillTemplate st=new SkillTemplate();
					st.setCoolDown(rs.getInt("CoolDown"));
					st.setCost(rs.getInt("Cost"));
					st.setDescription(rs.getString("Description"));
					st.setGrades(rs.getInt("Grades"));
					st.setIcons(rs.getString("Icons"));
					st.setNeedPlayerGrade(rs.getInt("NeedPlayerGrade"));
					st.setNextTemplateId(rs.getInt("NextTemplateId"));
					st.setParameter1(rs.getInt("Parameter1"));
					st.setParameter2(rs.getInt("Parameter2"));
					st.setParameter3(rs.getInt("Parameter3"));
					st.setPreTemplateId(rs.getString("PreTemplateId"));
					st.setTemplateId(rs.getInt("TemplateId"));
					st.setTemplateName(rs.getString("TemplateName"));
					resultList.add(st);
				}
			}

			@Override
			public void setParams(PreparedStatement stmt) throws SQLException {
				stmt.setInt(1, grade);
				stmt.setString(2, preTemplateId);
				if(job==1){
					stmt.setInt(3, 10000);
					stmt.setInt(4, 19999);
				}else if(job==2){
					stmt.setInt(3, 20000);
					stmt.setInt(4, 29999);
				}else if(job==3){
					stmt.setInt(3, 30000);
					stmt.setInt(4, 39999);
				}
				
			}
		});
		return resultList;
		
	}

	@Override
	public List<SkillTemplate> getSkillTemplateListByGrade(final int job,final int grade)
			throws Exception {
		// TODO Auto-generated method stub
				final List<SkillTemplate> resultList=new ArrayList<SkillTemplate>();
				DB.select(SELECT_SKILLTEMP_BY_GRADE, new ParamReadStH() {
					@Override
					public void handleRead(ResultSet rs) throws SQLException {
						while (rs.next()) {
							SkillTemplate st=new SkillTemplate();
							st.setCoolDown(rs.getInt("CoolDown"));
							st.setCost(rs.getInt("Cost"));
							st.setDescription(rs.getString("Description"));
							st.setGrades(rs.getInt("Grades"));
							st.setIcons(rs.getString("Icons"));
							st.setNeedPlayerGrade(rs.getInt("NeedPlayerGrade"));
							st.setNextTemplateId(rs.getInt("NextTemplateId"));
							st.setParameter1(rs.getInt("Parameter1"));
							st.setParameter2(rs.getInt("Parameter2"));
							st.setParameter3(rs.getInt("Parameter3"));
							st.setPreTemplateId(rs.getString("PreTemplateId"));
							st.setTemplateId(rs.getInt("TemplateId"));
							st.setTemplateName(rs.getString("TemplateName"));
						//	if(rs.getString("PreTemplateId")==null||"".equals(rs.getString("PreTemplateId").trim()))
							resultList.add(st);
						}
					}

					@Override
					public void setParams(PreparedStatement stmt) throws SQLException {
						stmt.setInt(1, grade);
						if(job==1){
							stmt.setInt(2, 10000);
							stmt.setInt(3, 19999);
						}else if(job==2){
							stmt.setInt(2, 20000);
							stmt.setInt(3, 29999);
						}else if(job==3){
							stmt.setInt(2, 30000);
							stmt.setInt(3, 39999);
						}
						
					}
				});
				return resultList;
	}

	@Override
	public SkillTemplate getSkillTemplateById(final int id) throws Exception {
		final List<SkillTemplate> resultList=new ArrayList<SkillTemplate>();
		DB.select(SELECT_SKILLTEMP_BY_ID, new ParamReadStH() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					SkillTemplate st=new SkillTemplate();
					st.setCoolDown(rs.getInt("CoolDown"));
					st.setCost(rs.getInt("Cost"));
					st.setDescription(rs.getString("Description"));
					st.setGrades(rs.getInt("Grades"));
					st.setIcons(rs.getString("Icons"));
					st.setNeedPlayerGrade(rs.getInt("NeedPlayerGrade"));
					st.setNextTemplateId(rs.getInt("NextTemplateId"));
					st.setParameter1(rs.getInt("Parameter1"));
					st.setParameter2(rs.getInt("Parameter2"));
					st.setParameter3(rs.getInt("Parameter3"));
					st.setPreTemplateId(rs.getString("PreTemplateId"));
					st.setTemplateId(rs.getInt("TemplateId"));
					st.setTemplateName(rs.getString("TemplateName"));
					resultList.add(st);
				}
			}

			@Override
			public void setParams(PreparedStatement stmt) throws SQLException {
				stmt.setInt(1, id);
			}
		});
		return resultList.get(0);
	}

	@Override
	public List<SkillTemplate> getSkillTemplateListByGradeForInit(final int job,
			final int grade) throws Exception {
		// TODO Auto-generated method stub
		final List<SkillTemplate> resultList=new ArrayList<SkillTemplate>();
		DB.select(SELECT_SKILLTEMP_BY_GRADE, new ParamReadStH() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					SkillTemplate st=new SkillTemplate();
					st.setCoolDown(rs.getInt("CoolDown"));
					st.setCost(rs.getInt("Cost"));
					st.setDescription(rs.getString("Description"));
					st.setGrades(rs.getInt("Grades"));
					st.setIcons(rs.getString("Icons"));
					st.setNeedPlayerGrade(rs.getInt("NeedPlayerGrade"));
					st.setNextTemplateId(rs.getInt("NextTemplateId"));
					st.setParameter1(rs.getInt("Parameter1"));
					st.setParameter2(rs.getInt("Parameter2"));
					st.setParameter3(rs.getInt("Parameter3"));
					st.setPreTemplateId(rs.getString("PreTemplateId"));
					st.setTemplateId(rs.getInt("TemplateId"));
					st.setTemplateName(rs.getString("TemplateName"));
					if(rs.getString("PreTemplateId")==null||"".equals(rs.getString("PreTemplateId").trim()))
					resultList.add(st);
				}
			}

			@Override
			public void setParams(PreparedStatement stmt) throws SQLException {
				stmt.setInt(1, grade);
				if(job==1){
					stmt.setInt(2, 10000);
					stmt.setInt(3, 19999);
				}else if(job==2){
					stmt.setInt(2, 20000);
					stmt.setInt(3, 29999);
				}else if(job==3){
					stmt.setInt(2, 30000);
					stmt.setInt(3, 39999);
				}
				
			}
		});
		return resultList;
	}
	


}
