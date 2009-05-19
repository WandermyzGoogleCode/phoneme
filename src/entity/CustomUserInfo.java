package entity;

import java.util.Map;
import java.util.Set;

public class CustomUserInfo {
	private Map<String, InfoField> fieldMap;
	
	public InfoField getInfoField(String name)
	{
		return fieldMap.get(name);
	}
	
	public void setInfoField(String name, InfoField info){
		fieldMap.put(name, info);
	}
	
	public Set<String> getKeySet(){
		return fieldMap.keySet();
	}
}
