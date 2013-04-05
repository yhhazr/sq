/**
 * 
 */
package com.sz7road.web.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.sz7road.web.dao.NewsDao;
import com.sz7road.web.dao.SkillTemplateDao;
import com.sz7road.web.dao.impl.NewsDaoImpl;
import com.sz7road.web.dao.impl.SkillTemplateDaoImpl;
import com.sz7road.web.model.skillTemplate.SkillTemplate;
import com.sz7road.web.service.SkillTemplateService;

/**
 * @author hokin.jim
 *
 */
public class SkillTemplateServiceImpl implements SkillTemplateService {

	private static SkillTemplateServiceImpl _this;
	
	private static SkillTemplateDao skillTemplateDao;
	
	private SkillTemplateServiceImpl() {
		skillTemplateDao = SkillTemplateDaoImpl.getInstance();
	}
	
	public synchronized static SkillTemplateServiceImpl getInstance() {
		if (_this == null)
			_this = new SkillTemplateServiceImpl();
		return _this;
	}
	@Override
	public List<SkillTemplate> getSkillTemplateList(int job,int grade,
			String preTemplateId) throws Exception {
		List<SkillTemplate> rs= new ArrayList<SkillTemplate>();
		if(grade!=0&&preTemplateId!=null&&!"".equals(preTemplateId.trim())){
			List<SkillTemplate> grs=skillTemplateDao.getSkillTemplateListByGrade(job,grade);
			for (SkillTemplate st : grs) {
				String preT=st.getPreTemplateId();
				if (preT!=null&&!"".equals(preT.trim())) {
					String[] preArray=preT.split(",");
					boolean flag=true;
					if(preArray.length>1){//前置技能为一个以上
						for (int i = 0; i < preArray.length; i++) {
							String pre = preArray[i];
							if(preTemplateId.indexOf(pre)<0){//若不满足flag为false
								flag=false;
							}
						}
						if(flag)
							rs.add(st);
					}else{//前置技能为一个
						if(preTemplateId.indexOf(preArray[0])>=0){//满足
							rs.add(st);
						}
					}
				}else{
					rs.add(st);
				}
			}
			
		}
		return rs;
	}
	@Override
	public List<SkillTemplate> getSkillTemplateListByGrade(int job,int grade) throws Exception {
		List<SkillTemplate> rs= new ArrayList<SkillTemplate>();
		if(grade!=0){
			rs=skillTemplateDao.getSkillTemplateListByGradeForInit(job,grade);
		}
		return rs;
	}
	@Override
	public String getDescriptionByParms(String description,int parm1, int parm2, int parm3) {
		// TODO 填充描述中的参数
		 if(description!=null){
			 if(description.indexOf("{Parameter1}")>=0)
				 description=description.replaceAll("\\{Parameter1\\}", String.valueOf(parm1));
			 if(description.indexOf("{Parameter2}")>=0)
				 description=description.replaceAll("\\{Parameter2\\}", String.valueOf(parm2));
			 if(description.indexOf("{Parameter3}")>=0)
				 description=description.replaceAll("\\{Parameter3\\}", String.valueOf(parm3));
		 }
		return description;
	}

	@Override
	public SkillTemplate getNextLevelSkillTemplateById(int id) throws Exception {
		// TODO Auto-generated method stub
		SkillTemplate rs=new SkillTemplate();
		SkillTemplate st=skillTemplateDao.getSkillTemplateById(id);
		int nextid=st.getNextTemplateId();
		if(nextid!=0){
			rs=skillTemplateDao.getSkillTemplateById(nextid);
		}else{
			rs=null;
		}
		return rs;
	}

	@Override
	public SkillTemplate getlSkillTemplateById(int id) throws Exception {
		// TODO Auto-generated method stub
		return skillTemplateDao.getSkillTemplateById(id);
	}
	

}
