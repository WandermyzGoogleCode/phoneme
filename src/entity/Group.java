package entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import entity.infoField.*;
import java.util.ArrayList;

public class Group implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4975730456812134661L;
	private ID id;// Ⱥ���ID
	private Map<String, InfoField> fieldMap;// Ⱥ��ĸ����ֶΣ�����Ⱥ�����ȵ�
	private Set<ID> users;// Ⱥ�������е��û�
	private ID adminUser;// Ⱥ��Ĺ���Ա�������ߣ���ID

	public ID getID() {
		return id;
	}

	public ID getAdminUserID() {
		return adminUser;
	}

	public InfoField getInfoField(String name) {
		return fieldMap.get(name);
	}

	public List<ID> getUsersID() {
		return new ArrayList<ID>(users);
	}

	public Set<ID> getUserSet() {
		return users;
	}

	public Set<String> getKeySet() {
		return fieldMap.keySet();
	}

	public Group() {
		id = adminUser = ID.getNullID();
		users = new HashSet<ID>();
		fieldMap = new HashMap<String, InfoField>();
		InfoFieldFactory factory = InfoFieldFactory.getFactory();
		List<EmptyInfoField> fields = factory.makeAllGroupEmptyField();
		for (EmptyInfoField field : fields)
			fieldMap.put(field.getName(), field);
	}

	public void setID(ID id) {
		this.id = id;
	}

	public void addToGroup(ID id) {
		this.users.add(id);
	}

	public void setInfoField(String name, InfoField info) {
		this.fieldMap.put(name, info);
	}
}
