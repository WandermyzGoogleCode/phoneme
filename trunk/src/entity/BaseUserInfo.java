package entity;
import java.util.Map;
import java.util.Set;

public class BaseUserInfo {
	ID id;
	private Map<String, InfoField> fieldMap;
	
	public ID getID()
	{
		return id;
	}
	
	public InfoField getInfoField(String name)
	{
		return fieldMap.get(name);
	}
	
	public Set<String> getKeySet(){
		return fieldMap.keySet();
	}
}
