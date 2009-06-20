package entity;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import entity.infoField.*;

public class BaseUserInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -784064690264146991L;
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

	/**
	 * 返回所有infoField的name的set
	 * @return
	 */
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
		List<InfoField> fields = factory.makeAllBaseEmptyField();
		for(InfoField field: fields)
			fieldMap.put(field.getName(), field);
	}
	public void setID(ID id){
		this.id=id;
	}
	public void setInfoField(String name,InfoField info){
		fieldMap.put(name, info);
	}
	
	/**
	 * 方便setInfoField
	 * @param info
	 */
	public void setInfoField(InfoField info){
		setInfoField(info.getName(), info);
	}
	
	public boolean isNull(){
		return (id.isNull());
	}
	
	public String getStringValue(){
		String res = ""; //$NON-NLS-1$
		for(String key: getKeySet())
			if (getInfoField(key).getStringValue().length() > 0)
				res += String.format(Messages.getString("KeyValueFormat"), key, getInfoField(key).getStringValue()); //$NON-NLS-1$
		return res;
	}
	
	/**
	 * 方便获得名字
	 * @return
	 */
	public String getName(){
		return getInfoField(InfoFieldName.Name.name()).getStringValue();
	}
	
	/**
	 * 方便获取一个非空的IdenticalInfoField，不存在则返回null
	 * @return
	 */
	public IdenticalInfoField getIdenticalField(){
		for(IdenticalInfoFieldName name: IdenticalInfoFieldName.values()){
			InfoField field = getInfoField(name.name());
			if (!field.isEmpty())
				return (IdenticalInfoField)field;
		}
		return null;
	}
	
	@Override
	public String toString() {
		return getStringValue();
	}
}
