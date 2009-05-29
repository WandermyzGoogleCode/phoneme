package entity.infoField;

import java.util.ArrayList;
import java.util.List;

/**
 * 标识和当前联系人的关系
 * 主要关系有：
 * 是否有个人关系（他是我的同步联系人）
 * 共同在哪些群组当中
 * 
 * 该字段只能由InfoFieldFactory来构造空关系，
 * 实际关系由LogicCenter在提供AllContactsBox的时候自动生成。
 * 
 * 注意，该字段不能由用户修改
 * @author Administrator
 *
 */
public class Relation extends EmptyInfoField implements IndexedInfoField {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1699245995240789623L;
	List<String> groups = new ArrayList<String>();
	boolean personal = false;

	@Override
	public String getName() {
		return InfoFieldName.Relation.name();
	}

	@Override
	public String toString(){
		return getStringValue();
	}
	
	@Override
	public String getStringValue() {
		String res = "";
		if (personal)
			res += "Personal";
		if (!groups.isEmpty())
		{
			res += "||Groups:";
			for(String s: groups)
				res += " "+s;
		}
		return res;
	}

	/**
	 * 如果非isEmpty，那么该联系人
	 * 应该会和服务器同步，所以BaseUserInfo
	 * 应该不许用户修改
	 */
	@Override
	public boolean isEmpty() {
		return !personal && groups.isEmpty();
	}

	public void setPersonal(boolean b){
		personal = b;
	}
	
	public void addGroup(String g){
		groups.add(g);
	}
	
	public void removeGroup(String g){
		groups.remove(g);
	}
	
	public static void main(String args[]){
		Relation r = new Relation();
		r.addGroup("test");
		System.out.println(r);
		r.removeGroup("test");
		System.out.println(r);
		r.addGroup("test");
		r.addGroup("test");
		System.out.println(r);
	}
}
