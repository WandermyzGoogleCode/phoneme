package entity.infoField;

import java.util.ArrayList;
import java.util.List;

/**
 * 标识和当前联系人的关系
 * 主要关系有：
 * 是否有个人关系（他是我的同步联系人）
 * 共同在哪些群组当中
 * 是否已经被删除(下面有解释，UI组和数据库组基本都不用管这个字段）
 * 
 * 该字段只能由InfoFieldFactory来构造空关系以及删除与否。
 * 实际关系由LogicCenter在提供AllContactsBox的时候自动生成。
 * 
 * 注意，该字段不能由用户修改。
 * 
 * 关于删除，是为了说明用户删除了该联系人，但是和该联系人还保留有
 * 同步关系的（删除以后一直没有登录，从而没有机会更新服务器）。此时，当
 * 登录时，我们会尽快处理这些删除，到服务器上删除同步关系。
 * 
 * UI在拿AllContactsBox.getContacts的时候是拿不到这些被删除的用户的
 * 
 * 注意，群组中的人你是不能删除的（除非你是管理员），你唯一能选择的就是
 * 在显示的时候，是否显示那些只有群组关系，而没有个人关系的联系人。
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
		if (value.equals("removed"))
			removed = true;
	}
	
	@Override
	public String getStringValue() {
		if (isRemoved())
			return "removed";
		String res = "";
		if (personal)
			res += "Personal";
		if (!groups.isEmpty())
		{
			res += "||Groups:";
			for(String s: groups){
				if (res.length()+4+s.length() > maxLength){
					res += "...";
					break;
				}
				res += " "+s;
			}
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
	
	public boolean isRemoved(){
		return removed;
	}
	
	public void setRemoved(boolean r){
		this.removed = r;
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

	@Override
	public int getMaxLength() {
		return 200;
	}
	
	public boolean isPersonal(){
		return personal;
	}
}
