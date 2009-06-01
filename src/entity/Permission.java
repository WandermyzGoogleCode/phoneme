package entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import entity.infoField.*;

/**
 * ���ඨ����һ���û�A����һ���û�B����ϢȨ��
 * ��Ȩ�޶�����A�û�����Ϣ�е���Щ�ֶ��ܹ���B��֪��
 * @author Administrator
 *
 */
public class Permission implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8542014263985231177L;
	//Map<�ֶ����֣��Ƿ���Ȩ��֪��>�������ֶ�����Ӧ�ľ���InfoField.Name
	private Map<String, Boolean> fields;
	
	public void setField(String name, Boolean value)
	{
		fields.put(name, value);
	}
	
	/**
	 * ���fields��û��name���key����ô����һ��false��Boolean
	 * @param name
	 * @return
	 */
	public Boolean getField(String name)
	{
		return (fields.get(name) == null) ? false : fields.get(name);
	}
	
	/**
	 * ����һ���յ�Permission���������ֶζ����������
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
	 * ��p��Ϊ�����Ȩ�ϲ�������ʹ�õ�ǰȨ���ܻ�ø������Ϣ
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
