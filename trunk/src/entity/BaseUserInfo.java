package entity;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import entity.infoField.*;

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
	
	/**
	 * 直接构造一个全空的，id为NullID的BaseUserInfo
	 * @param id 如果是从数据库里面读，可以直接传入-1，到时候再set
	 */
	public BaseUserInfo()
	{
		this.id = ID.getNullID();
		fieldMap = new HashMap<String, InfoField>();
		InfoFieldFactory factory = InfoFieldFactory.getFactory();
		List<EmptyInfoField> fields = factory.makeAllBaseEmptyField();
		for(EmptyInfoField field: fields)
			fieldMap.put(field.getName(), field);
	}
	public void setID(ID id){
		this.id=id;
	}
	public void setInfoField(String name,InfoField info){
		fieldMap.put(name, info);
	}
	
	public boolean isNull(){
		return (id.isNull());
	}
	
	public String getStringValue(){
		String res = "";
		for(String key: getKeySet())
			if (getInfoField(key).getStringValue().length() > 0)
				res += "<"+key+": "+getInfoField(key).getStringValue()+">  |";
		return res;
	}
}
