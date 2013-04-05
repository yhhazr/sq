/**
 * 
 */
package com.sz7road.web.service;

import java.util.List;

import com.sz7road.web.model.skillTemplate.SkillTemplate;

/**
 * @author hokin.jim
 *
 */
public interface SkillTemplateService {
	public List<SkillTemplate> getSkillTemplateList(int job,int grade,String preTemplateId) throws Exception;
	public List<SkillTemplate> getSkillTemplateListByGrade(int job,int grade) throws Exception;
	public SkillTemplate getlSkillTemplateById(int id)  throws Exception;
	public SkillTemplate getNextLevelSkillTemplateById(int id)  throws Exception;
	public String getDescriptionByParms(String description,int parm1,int parm2,int parm3);
}
