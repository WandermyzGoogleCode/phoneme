package entity;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.List;
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
	
	public void setID(ID id){
		this.id=id;
	}
	
	public void setInfoField(String name,InfoField info){
		fieldMap.put(name, info);
	}
	
	public Set<String> getKeySet(){
		return fieldMap.keySet();
	}
	
	/**
	 * ֱ�ӹ���һ��ȫ�յģ�id������BaseUserInfo
	 * @param id ����Ǵ����ݿ������������ֱ�Ӵ���-1����ʱ����set
	 */
	public BaseUserInfo(int id)
	{
		this.id = new ID(id);
		fieldMap = new HashMap<String, InfoField>();
		InfoFieldFactory factory = InfoFieldFactory.getFactory();
		List<EmptyInfoField> fields = factory.makeAllBaseEmptyField();
		for(EmptyInfoField field: fields)
			fieldMap.put(field.getName(), field);
	}
}
