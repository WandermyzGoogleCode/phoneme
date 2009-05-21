package entity;

import java.util.List;
import java.util.Map;
import java.util.Set;
import entity.infoField.*;

public class Group {
	private ID id;//Ⱥ���ID
	private Map<String, InfoField> fieldMap;//Ⱥ��ĸ����ֶΣ�����Ⱥ�����ȵ�
	private List<ID> users;//Ⱥ�������е��û�
	private ID adminUser;//Ⱥ��Ĺ���Ա�������ߣ���ID
	
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
