package entity;

import java.util.Map;

/**
 * ���ඨ����һ���û�A����һ���û�B����ϢȨ��
 * ��Ȩ�޶�����A�û�����Ϣ�е���Щ�ֶ��ܹ���B��֪��
 * @author Administrator
 *
 */
public class Permission {
	//Map<�ֶ����֣��Ƿ���Ȩ��֪��>�������ֶ�����Ӧ�ľ���InfoField.Name
	private Map<String, Boolean> fields;
	
	public void setField(String name, Boolean value)
	{
		fields.put(name, value);
	}
	
	public Boolean getField(String name)
	{
		return fields.get(name);
	}
}
