package entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entity.infoField.*;

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
		List<EmptyInfoField> ifields = factory.makeAllBaseEmptyField();
		for(EmptyInfoField ifield: ifields)
			fields.put(ifield.getName(), false);
	}
}
