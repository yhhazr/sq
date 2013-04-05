/**
 * 
 */
package com.sz7road.web.model.skillTemplate;

/**
 * @author hokin.jim
 *
 */
public class SkillTemplateBasicJSON {
	private int id;
	private String name;
	private int level;
	private int cost;
	private int cooldown;
	private int needPlayerLevel;
	private String description;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	public int getCooldown() {
		return cooldown;
	}
	public void setCooldown(int cooldown) {
		this.cooldown = cooldown;
	}
	public int getNeedPlayerLevel() {
		return needPlayerLevel;
	}
	public void setNeedPlayerLevel(int needPlayerLevel) {
		this.needPlayerLevel = needPlayerLevel;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}



}
