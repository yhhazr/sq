/**
 * 
 */
package com.sz7road.web.action.online.skillTemplate;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionSupport;
import com.sz7road.web.common.util.ServiceLocator;
import com.sz7road.web.model.skillTemplate.SkillTemplate;
import com.sz7road.web.model.skillTemplate.SkillTemplateBasicJSON;
import com.sz7road.web.model.skillTemplate.SkillTemplateJSONBean;
import com.sz7road.web.service.SkillTemplateService;

/**
 * @author hokin.jim
 *
 */
public class SkillTemplateAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(SkillTemplateAction.class);
	private Integer grade;
	private String existTemplateId;
	private Integer templateId;
	private Integer job;
	private SkillTemplateJSONBean json;
	
	public String getSkillTemplate() {
		SkillTemplateService skillTemplateService=ServiceLocator.getSkillTemplateService();
		try {
				json=new SkillTemplateJSONBean();
				SkillTemplateBasicJSON next = new SkillTemplateBasicJSON();
				SkillTemplateBasicJSON current = new SkillTemplateBasicJSON();
				List<String> canLightOn=new ArrayList<String>();
				List<Integer> lighOnIds=new ArrayList<Integer>();
				List<SkillTemplate> rsList =new ArrayList<SkillTemplate>();
				//后面可升级的技能
				if(existTemplateId!=null&&templateId!=null){
					StringBuffer existParm=new StringBuffer();
					existParm.append(existTemplateId);
					existParm.append(",");
					existParm.append(templateId);
					rsList=skillTemplateService.getSkillTemplateList(job,grade,existParm.toString());
					for (SkillTemplate st : rsList) {
						if(!canLightOn.contains(st.getIcons()))//去重复
							canLightOn.add(st.getIcons());
						lighOnIds.add(st.getTemplateId());
					}
					json.setCanLighton(canLightOn);
					json.setLightonIds(lighOnIds);
				}else if(templateId!=null&&existTemplateId==null){
					StringBuffer existParm=new StringBuffer();
					existParm.append(templateId);
					rsList=skillTemplateService.getSkillTemplateList(job,grade,existParm.toString());
					for (SkillTemplate st : rsList) {
						if(!canLightOn.contains(st.getIcons()))//去重复
							canLightOn.add(st.getIcons());
						lighOnIds.add(st.getTemplateId());
					}
					json.setCanLighton(canLightOn);
					json.setLightonIds(lighOnIds);
				}else{
					rsList=skillTemplateService.getSkillTemplateListByGrade(job,grade);
					//初始
					if(rsList!=null){
						for (SkillTemplate st : rsList) {
							if(!canLightOn.contains(st.getIcons()))//去重复
								canLightOn.add(st.getIcons());
							lighOnIds.add(st.getTemplateId());
						}
						json.setCanLighton(canLightOn);
						json.setLightonIds(lighOnIds);
					}
				}
				//next
				//下个等级
				if(templateId!=null){
					SkillTemplate rs =new SkillTemplate();
					rs=skillTemplateService.getNextLevelSkillTemplateById(templateId);
					if(rs!=null){
						String decr_next=skillTemplateService.getDescriptionByParms(rs.getDescription(), rs.getParameter1(), rs.getParameter2(), rs.getParameter3());
						next.setId(rs.getTemplateId());
						next.setCooldown(rs.getCoolDown()/1000);
						next.setCost(rs.getCost());
						next.setDescription(decr_next);
						next.setNeedPlayerLevel(rs.getNeedPlayerGrade());
						next.setName(rs.getTemplateName());
						next.setLevel(rs.getGrades());	
						json.setNext(next);
					}
					//当前
					SkillTemplate rsst =new SkillTemplate();
					rsst=skillTemplateService.getlSkillTemplateById(templateId);
					if(rsst!=null){
						String decr_self=skillTemplateService.getDescriptionByParms(rsst.getDescription(), rsst.getParameter1(), rsst.getParameter2(), rsst.getParameter3());
						current.setId(rsst.getTemplateId());
						current.setCooldown(rsst.getCoolDown()/1000);
						current.setCost(rsst.getCost());
						current.setDescription(decr_self);
						current.setNeedPlayerLevel(rsst.getNeedPlayerGrade());
						current.setName(rsst.getTemplateName());
						current.setLevel(rsst.getGrades());	
						json.setCurrent(current);
					}
				}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info("getSkillTemplate Exception:"+e.toString());
		}
		return SUCCESS;
	}

	public Integer getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Integer templateId) {
		this.templateId = templateId;
	}

	public SkillTemplateJSONBean getJson() {
		return json;
	}

	public void setJson(SkillTemplateJSONBean json) {
		this.json = json;
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}


	public Integer getJob() {
		return job;
	}

	public void setJob(Integer job) {
		this.job = job;
	}

	public String getExistTemplateId() {
		return existTemplateId;
	}

	public void setExistTemplateId(String existTemplateId) {
		this.existTemplateId = existTemplateId;
	}

}
