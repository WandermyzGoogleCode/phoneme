package entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import entity.infoField.*;

public class CustomUserInfo {
	private Map<String, InfoField> fieldMap;
	
	public InfoField getInfoField(String name)
	{
		return fieldMap.get(name);
	}
	
	public Set<String> getKeySet(){
		return fieldMap.keySet();
	}
	
	public CustomUserInfo()
	{
		fieldMap = new HashMap<String, InfoField>();
		InfoFieldFactory factory = InfoFieldFactory.getFactory();
		List<EmptyInfoField> fields = factory.makeAllCustomEmptyField();
		for(EmptyInfoField field: fields)
			fieldMap.put(field.getName(), field);		
	}

	public void setInfoField(String name, InfoField info){
		fieldMap.put(name, info);
	}

	public String getStringValue(){
		String res = "";
		for(String key: getKeySet())
			res += "<"+key+": "+getInfoField(key).getStringValue()+">  |  ";
		return res;
	}
}
