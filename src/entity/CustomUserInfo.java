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
		List<InfoField> fields = factory.makeAllCustomEmptyField();
		for(InfoField field: fields)
			fieldMap.put(field.getName(), field);		
	}

	public void setInfoField(String name, InfoField info){
		fieldMap.put(name, info);
	}

	public String getStringValue(){
		String res = "";
		for(String key: getKeySet())
			if (getInfoField(key).getStringValue().length() > 0)
				res += "<"+key+": "+getInfoField(key).getStringValue()+">  |";
		return res;
	}
}
