package entity;

import java.util.ArrayList;
import java.util.Map;

public class Group {
	private ID id;//群组的ID
	private Map<String, InfoField> fieldMap;//群组的各种字段，比如群组名等等
	private ArrayList<ID> users;//群组内所有的用户
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
	
	public ArrayList<ID> getUsersID()
	{
		return users;
	}
}
