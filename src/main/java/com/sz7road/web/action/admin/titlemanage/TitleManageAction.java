package com.sz7road.web.action.admin.titlemanage;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Iterator;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.opensymphony.xwork2.ActionSupport;

public class TitleManageAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	private String[] items = new String[29];
	private String editSuccess;
	public String queryTitleList() throws Exception {
		String result = INPUT;
		SAXReader reader = new SAXReader();
		Document document = reader.read(new File("e:\\Title.xml"));
		Element root = document.getRootElement();
		Iterator iter = root.elementIterator("items");
		int i = 0;
		while (iter.hasNext()) {
			Element item = (Element) iter.next();
			Attribute atr = item.attribute("url");
			items[i] = atr.getValue();
			i++;
			Iterator it = item.elementIterator("item");
			while(it.hasNext()){
				Element e = (Element) it.next();
				Attribute atr1 = e.attribute("url");
				items[i] = atr1.getValue();
				i++;
			}
			
		}
		result = SUCCESS;
		return result;
	}
	public String editTltleSubmit() throws Exception{
	
		String result = INPUT;
		SAXReader reader = new SAXReader();
		Document document = reader.read(new File("e:\\Title.xml"));
		Element root = document.getRootElement();
		Iterator iter = root.elementIterator("items");
		int i = 0;
		while (iter.hasNext()) {
			Element item = (Element) iter.next();
			Attribute atr = item.attribute("url");
			atr.setValue(items[i]);
			i++;
			Iterator it = item.elementIterator("item");
			while(it.hasNext()){
				Element e = (Element) it.next();
				Attribute atr1 = e.attribute("url");
				atr1.setValue(items[i]);
				i++;
			}
			
		}	
		FileOutputStream out = new FileOutputStream("e:\\Title.xml");
		XMLWriter writer = new XMLWriter(out);
		writer.write(document);
		writer.close();
		editSuccess = SUCCESS;
		result = SUCCESS;
		return result;
	}
	public String[] getItems() {
		return items;
	}

	public void setItems(String[] items) {
		this.items = items;
	}
	public String getEditSuccess() {
		return editSuccess;
	}
	public void setEditSuccess(String editSuccess) {
		this.editSuccess = editSuccess;
	}
	
	
}
