package logiccenter.VirtualResult;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import logiccenter.LogicCenter;

import entity.BaseUserInfo;
import entity.CustomUserInfo;
import entity.Group;
import entity.ID;
import entity.MyRemoteException;
import entity.UserInfo;
import entity.infoField.InfoFieldName;
import entity.infoField.Relation;

/**
 * 可以获取当前所有联系人的Virtual Proxy，类似 {@link MessageBox}
 * 
 * @author Administrator
 * 
 */
public class AllContactsBox extends VirtualResult {
	private LogicCenter center;
	private Map<ID, UserInfo> contacts;

	class GetThread extends Thread {
		@Override
		public void run() {
			contacts = new HashMap<ID, UserInfo>();
			List<UserInfo> temp = center.getDataCenter().getAllUserInfo(null);
			for(UserInfo info: temp)
				contacts.put(info.getBaseInfo().getID(), info); 
			List<ID> perIDList = center.getDataCenter().getAllPerContactsID();
			Set<ID> synIDSet = (perIDList == null) ? new HashSet<ID>() : new HashSet<ID>(perIDList);
			for (UserInfo contact : contacts.values()) {
				Relation r = (Relation)contact.getInfoField(InfoFieldName.Relation);
				if (synIDSet.contains(contact.getBaseInfo().getID()))
					r.setPersonal(true);
				for (Group g : center.getAllGroupsBox().getGroups())
					if (g.getUserSet().contains(contact.getBaseInfo().getID()))
						r.addGroup(g.getInfoField(
								InfoFieldName.GroupName.name())
								.getStringValue());
			}
			setUpdateNow();
		}
	}

	public synchronized int getCnt() {
		return contacts.size();
	}

	public synchronized List<UserInfo> getContacts() {
		List<UserInfo> res = new ArrayList<UserInfo>();
		for(UserInfo info: contacts.values())
			if (!((Relation)info.getInfoField(InfoFieldName.Relation)).isRemoved())
				res.add(info);
		return res;
	}

	public AllContactsBox(LogicCenter center) {
		this.center = center;
		GetThread thread = new GetThread();
		thread.start();
	}

	protected synchronized void editContactImp(UserInfo newInfo){		
		//如果customInfo是null，那么不改变原来的customInfo
		UserInfo oldInfo = contacts.get(newInfo.getBaseInfo().getID());
		if (oldInfo != null && newInfo.getCustomInfo() == null)
			newInfo.setCustomInfo(oldInfo.getCustomInfo());
		if (newInfo.getCustomInfo() == null)
			newInfo.setCustomInfo(new CustomUserInfo());
		
		Set<ID> synIDSet = new HashSet<ID>(center.getDataCenter()
				.getAllPerContactsID());
		Relation r = (Relation)newInfo.getInfoField(InfoFieldName.Relation);
		if (synIDSet.contains(newInfo.getBaseInfo().getID()))
			r.setPersonal(true);
		for (Group g : center.getAllGroupsBox().getGroups())
			if (g.getUserSet().contains(newInfo.getBaseInfo().getID()))
				r.addGroup(g.getInfoField(InfoFieldName.GroupName.name())
						.getStringValue());
		contacts.put(newInfo.getBaseInfo().getID(), newInfo);
	}
	/**
	 * 该方法应该是LogicCenter在修改以后调用的， GUI不用这个来修改，而是用LogicCenter的接口来修改
	 * 
	 * @param newInfo
	 */
	public synchronized void editContact(UserInfo newInfo) {
		editContactImp(newInfo);
		setUpdateNow();
	}

	public synchronized void removeContact(ID uid) {
		contacts.remove(uid);
		setUpdateNow();
	}

	public synchronized void updateAll() {
		GetThread thread = new GetThread();
		thread.start();
	}
	
	public synchronized Map<ID, UserInfo> getContactsMap(){
		return contacts;
	}
	
	/**
	 * 写在这里是为了减少AllContactsBox更新状态的次数。
	 * 如果这个函数写在LogicCenter或者其它地方，那么每
	 * 新加一个用户，都会让AllContactBox更新一次状态。
	 * @param g
	 */
	public synchronized void updateGroupMembers(Group g) throws RemoteException, MyRemoteException{
		List<ID> newIDList = new ArrayList<ID>();
		for(ID id: g.getUserSet())
			if (!contacts.containsKey(id))
				newIDList.add(id);
		List<BaseUserInfo> newUsers = center.getServer().getContactsInfo(center.getLoginUser().getID(), newIDList);
		for(BaseUserInfo bInfo: newUsers){
			UserInfo newInfo = new UserInfo(bInfo);
			newInfo.setCustomInfo(null);//不改变本地字段
			editContactImp(new UserInfo(bInfo));
		}
		setUpdateNow();
	}
	
	/**
	 * 更新idList中所有用户的关系字段
	 * @param idList
	 */
	public synchronized void updateRelation(List<ID> idList){
		for(ID id: idList)
			if (contacts.containsKey(id))
				editContactImp(contacts.get(id));
		setUpdateNow();
	}
	
	/**
	 * 获取所有要删除的联系人的信息
	 */
	public synchronized List<UserInfo> getRemovedContacts(){
		List<UserInfo> res = new ArrayList<UserInfo>();
		for(UserInfo info: contacts.values())
			if (((Relation)info.getInfoField(InfoFieldName.Relation)).isRemoved())
				res.add(info);
		return res;		
	}

	/**
	 * 将removed的用户清空
	 */
	public synchronized void clearRemovedContacts() {
		for(ID id: contacts.keySet())
			if (((Relation)contacts.get(id).getInfoField(InfoFieldName.Relation)).isRemoved())
				contacts.remove(id);
	}
}
