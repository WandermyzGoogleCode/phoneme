package entity;

import java.util.ArrayList;
import java.util.Map;

public class Group {
	private ID id;//Ⱥ���ID
	private Map<String, InfoField> fieldMap;//Ⱥ��ĸ����ֶΣ�����Ⱥ�����ȵ�
	private ArrayList<ID> users;//Ⱥ�������е��û�
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
	
	public ArrayList<ID> getUsersID()
	{
		return users;
	}
}
