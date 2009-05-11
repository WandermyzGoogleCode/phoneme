package entity;

import java.util.Map;

public class CustomUserInfo {
	private Map<String, InfoField> fieldMap;
	
	public InfoField getInfoField(String name)
	{
		return fieldMap.get(name);
	}
}
