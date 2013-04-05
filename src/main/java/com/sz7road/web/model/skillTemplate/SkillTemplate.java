/**
 * 
 */
package com.sz7road.web.model.skillTemplate;

/**
 * 游戏技能实体
 * @author hokin.jim
 *
 */
public class SkillTemplate implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = 1L;
	private Integer templateId;
	private String templateName;
	private String icons;
	private Integer grades;
	private String preTemplateId;
	private Integer nextTemplateId;
	private Integer needPlayerGrade;
	private Integer cost;
	private Integer parameter1;
	private Integer parameter2;
	private Integer parameter3;
	private Integer coolDown;
	private String description;

	// Constructors

	/** default constructor */
	public SkillTemplate() {
	}


	/** full constructor */
	public SkillTemplate(Integer templateId, String templateName,
			String icons, Integer grades, String preTemplateId,
			Integer nextTemplateId, Integer needPlayerGrade, Integer cost,
			Integer parameter1, Integer parameter2, Integer parameter3,
			Integer coolDown, String description) {
		this.templateId = templateId;
		this.templateName = templateName;
		this.icons = icons;
		this.grades = grades;
		this.preTemplateId = preTemplateId;
		this.nextTemplateId = nextTemplateId;
		this.needPlayerGrade = needPlayerGrade;
		this.cost = cost;
		this.parameter1 = parameter1;
		this.parameter2 = parameter2;
		this.parameter3 = parameter3;
		this.coolDown = coolDown;
		this.description = description;
	}

	// Property accessors

	public Integer getTemplateId() {
		return this.templateId;
	}

	public void setTemplateId(Integer templateId) {
		this.templateId = templateId;
	}

	public String getTemplateName() {
		return this.templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getIcons() {
		return this.icons;
	}

	public void setIcons(String icons) {
		this.icons = icons;
	}

	public Integer getGrades() {
		return this.grades;
	}

	public void setGrades(Integer grades) {
		this.grades = grades;
	}

	public String getPreTemplateId() {
		return this.preTemplateId;
	}

	public void setPreTemplateId(String preTemplateId) {
		this.preTemplateId = preTemplateId;
	}

	public Integer getNextTemplateId() {
		return this.nextTemplateId;
	}

	public void setNextTemplateId(Integer nextTemplateId) {
		this.nextTemplateId = nextTemplateId;
	}

	public Integer getNeedPlayerGrade() {
		return this.needPlayerGrade;
	}

	public void setNeedPlayerGrade(Integer needPlayerGrade) {
		this.needPlayerGrade = needPlayerGrade;
	}

	public Integer getCost() {
		return this.cost;
	}

	public void setCost(Integer cost) {
		this.cost = cost;
	}

	public Integer getParameter1() {
		return this.parameter1;
	}

	public void setParameter1(Integer parameter1) {
		this.parameter1 = parameter1;
	}

	public Integer getParameter2() {
		return this.parameter2;
	}

	public void setParameter2(Integer parameter2) {
		this.parameter2 = parameter2;
	}

	public Integer getParameter3() {
		return this.parameter3;
	}

	public void setParameter3(Integer parameter3) {
		this.parameter3 = parameter3;
	}

	public Integer getCoolDown() {
		return this.coolDown;
	}

	public void setCoolDown(Integer coolDown) {
		this.coolDown = coolDown;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}