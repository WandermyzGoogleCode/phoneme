package entity.infoField;

import java.util.ArrayList;
import java.util.List;

/**
 * ��ʶ�͵�ǰ��ϵ�˵Ĺ�ϵ
 * ��Ҫ��ϵ�У�
 * �Ƿ��и��˹�ϵ�������ҵ�ͬ����ϵ�ˣ�
 * ��ͬ����ЩȺ�鵱��
 * �Ƿ��Ѿ���ɾ��(�����н��ͣ�UI������ݿ�����������ù�����ֶΣ�
 * 
 * ���ֶ�ֻ����InfoFieldFactory������չ�ϵ�Լ�ɾ�����
 * ʵ�ʹ�ϵ��LogicCenter���ṩAllContactsBox��ʱ���Զ����ɡ�
 * 
 * ע�⣬���ֶβ������û��޸ġ�
 * 
 * ����ɾ������Ϊ��˵���û�ɾ���˸���ϵ�ˣ����Ǻ͸���ϵ�˻�������
 * ͬ����ϵ�ģ�ɾ���Ժ�һֱû�е�¼���Ӷ�û�л�����·�����������ʱ����
 * ��¼ʱ�����ǻᾡ�촦����Щɾ��������������ɾ��ͬ����ϵ��
 * 
 * UI����AllContactsBox.getContacts��ʱ�����ò�����Щ��ɾ�����û���
 * 
 * ע�⣬Ⱥ���е������ǲ���ɾ���ģ��������ǹ���Ա������Ψһ��ѡ��ľ���
 * ����ʾ��ʱ���Ƿ���ʾ��Щֻ��Ⱥ���ϵ����û�и��˹�ϵ����ϵ�ˡ�
 * @author Administrator
 *
 */
public class Relation extends EmptyInfoField implements IndexedInfoField {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1699245995240789623L;
	public static final int maxLength = 200;
	List<String> groups = new ArrayList<String>();
	boolean personal = false;
	boolean removed = false;

	@Override
	public String getName() {
		return InfoFieldName.Relation.name();
	}

	@Override
	public String toString(){
		return getStringValue();
	}
	
	public Relation(){}
	
	public Relation(String value){
		if (value.equals("removed")) //$NON-NLS-1$
			removed = true;
	}
	
	@Override
	public String getStringValue() {
		if (isRemoved())
			return "removed"; //$NON-NLS-1$
		String res = ""; //$NON-NLS-1$
		if (personal)
			res += Messages.getString("Relation.Personal"); //$NON-NLS-1$
		if (!groups.isEmpty())
		{
			if (personal)
				res += Messages.getString("Relation.Partition"); //$NON-NLS-1$
			res += Messages.getString("Relation.GroupsHead"); //$NON-NLS-1$
			for(String s: groups){
				if (res.length()+4+s.length() > maxLength){
					res += "..."; //$NON-NLS-1$
					break;
				}
				res += " "+s; //$NON-NLS-1$
			}
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
	
	public boolean isRemoved(){
		return removed;
	}
	
	public void setRemoved(boolean r){
		this.removed = r;
	}
	
	public static void main(String args[]){
		Relation r = new Relation();
		r.addGroup("test"); //$NON-NLS-1$
		System.out.println(r);
		r.removeGroup("test"); //$NON-NLS-1$
		System.out.println(r);
		r.addGroup("test"); //$NON-NLS-1$
		r.addGroup("test"); //$NON-NLS-1$
		System.out.println(r);
	}

	@Override
	public int getMaxLength() {
		return 200;
	}
	
	public boolean isPersonal(){
		return personal;
	}
	
	public void setEmpty(){
		personal = false;
		groups.clear();
	}
	
	public boolean hasGroup(){
		return !groups.isEmpty();
	}
}
