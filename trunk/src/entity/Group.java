package entity;

import java.util.List;
import java.util.Map;
import java.util.Set;
import entity.infoField.*;

public class Group {
	private ID id;//群组的ID
	private Map<String, InfoField> fieldMap;//群组的各种字段，比如群组名等等
	private List<ID> users;//群组内所有的用户
	private ID adminUser;//群组的管理员（创建者）的ID
	
	public ID getID()
	{
		return id;
	}
	
	public ID getAdminUserID()
	{
		return adminUser;
	}
	
	public InfoField getInfoField(String name)
	{
		return fieldMap.get(name);
	}
	
	public List<ID> getUsersID()
	{
		return users;
	}
	
	public void setInfoField(String name,InfoField info){
		this.fieldMap.put(name, info);
	}
	
	public Set<String> getKeySet(){
		return fieldMap.keySet();
	}
	
	public void setID(ID id){
		this.id=id;
	}
	
	public void addToGroup(ID id){
		this.users.add(id);
	}
}
