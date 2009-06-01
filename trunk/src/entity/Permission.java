package entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import entity.infoField.*;

/**
 * 该类定义了一个用户A给另一个用户B的信息权限
 * 该权限定义了A用户的信息中的哪些字段能够被B所知道
 * @author Administrator
 *
 */
public class Permission implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8542014263985231177L;
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
		List<InfoField> ifields = factory.makeAllBaseEmptyField();
		for(InfoField ifield: ifields)
			fields.put(ifield.getName(), false);
	}
	
	public Set<String> getKeySet(){
		return fields.keySet();
	}
	
	/**
	 * 将p中为真的授权合并过来，使得当前权限能获得更多的信息
	 * @param p
	 */
	public void union(Permission p){
		for(String key: p.getKeySet())
			if (p.getField(key).booleanValue())
				setField(key, Boolean.TRUE);
	}
	
	@Override
	public Permission clone(){
		Permission res = new Permission();
		res.union(this);
		return res;
	}
}
