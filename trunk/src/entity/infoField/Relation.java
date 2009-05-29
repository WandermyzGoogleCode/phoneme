package entity.infoField;

import java.util.ArrayList;
import java.util.List;

/**
 * ��ʶ�͵�ǰ��ϵ�˵Ĺ�ϵ
 * ��Ҫ��ϵ�У�
 * �Ƿ��и��˹�ϵ�������ҵ�ͬ����ϵ�ˣ�
 * ��ͬ����ЩȺ�鵱��
 * 
 * ���ֶ�ֻ����InfoFieldFactory������չ�ϵ��
 * ʵ�ʹ�ϵ��LogicCenter���ṩAllContactsBox��ʱ���Զ����ɡ�
 * 
 * ע�⣬���ֶβ������û��޸�
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
	 * �����isEmpty����ô����ϵ��
	 * Ӧ�û�ͷ�����ͬ��������BaseUserInfo
	 * Ӧ�ò����û��޸�
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