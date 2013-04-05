/**
 * 
 */
package com.sz7road.web.model.skillTemplate;

import java.util.List;

/**
 * @author hokin.jim
 *
 */
public class SkillTemplateJSONBean {
	private SkillTemplateBasicJSON current;
	private SkillTemplateBasicJSON next;
	private List<String> canLighton;
	private List<Integer> lightonIds;
	public SkillTemplateBasicJSON getNext() {
		return next;
	}
	public void setNext(SkillTemplateBasicJSON next) {
		this.next = next;
	}
	public List<String> getCanLighton() {
		return canLighton;
	}
	public void setCanLighton(List<String> canLighton) {
		this.canLighton = canLighton;
	}
	public SkillTemplateBasicJSON getCurrent() {
		return current;
	}
	public void setCurrent(SkillTemplateBasicJSON current) {
		this.current = current;
	}
	public List<Integer> getLightonIds() {
		return lightonIds;
	}
	public void setLightonIds(List<Integer> lightonIds) {
		this.lightonIds = lightonIds;
	}
	
}
