/**
 * 
 */
package com.sz7road.web.dao;

import java.util.List;

import com.sz7road.web.model.skillTemplate.SkillTemplate;

/**
 * 游戏技能Dao接口
 * @author hokin.jim
 *
 */
public interface SkillTemplateDao {
	public List<SkillTemplate> getSkillTemplateList(int job,int grade,String preTemplateId) throws Exception;
	public List<SkillTemplate> getSkillTemplateListByGradeForInit(int job,int grade) throws Exception;
	public SkillTemplate getSkillTemplateById(int id)throws Exception;
	public List<SkillTemplate> getSkillTemplateListByGrade(int job,int grade) throws Exception;
}
