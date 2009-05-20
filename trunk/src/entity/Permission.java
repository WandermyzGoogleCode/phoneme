package entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entity.infoField.*;

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
	
	/**
	 * 如果fields中没有name这个key，那么返回一个false的Boolean
	 * @param name
	 * @return
	 */
	public Boolean getField(String name)
	{
		return (fields.get(name) == null) ? false : fields.get(name);
	}
	
	/**
	 * 创建一个空的Permission，对所有字段都不允许访问
	 */
	public Permission()
	{
		fields = new HashMap<String, Boolean>();
		InfoFieldFactory factory = InfoFieldFactory.getFactory();
		List<EmptyInfoField> ifields = factory.makeAllBaseEmptyField();
		for(EmptyInfoField ifield: ifields)
			fields.put(ifield.getName(), false);
	}
}
