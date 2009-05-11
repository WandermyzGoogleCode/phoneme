package entity;

import java.util.Map;

/**
 * 该类定义了一个用户A给另一个用户B的信息权限
 * 该权限定义了A用户的信息中的哪些字段能够被B所知道
 * @author Administrator
 *
 */
public class Permission {
	//Map<字段名字，是否有权限知道>，其中字段名对应的就是InfoField.Name
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
