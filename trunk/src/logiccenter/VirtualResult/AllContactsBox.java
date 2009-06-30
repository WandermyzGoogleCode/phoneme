package logiccenter.VirtualResult;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;

import logiccenter.BoxContent;
import logiccenter.LogicCenter;

import entity.BaseUserInfo;
import entity.CustomUserInfo;
import entity.ErrorType;
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
	private BoxContent bc;

	class GetTask implements Runnable {
		@Override
		public void run() {
			try {
				bc.setContacts(new ConcurrentHashMap<ID, UserInfo>());
				List<UserInfo> temp;
				temp = center.getDataCenter().getAllUserInfo(null);
				for (UserInfo info : temp)
					bc.getContacts().put(info.getBaseInfo().getID(), info);
				List<ID> synIDList = center.getDataCenter().getAllSynContactsID();
				Set<ID> synIDSet = (synIDList == null) ? new HashSet<ID>()
						: new HashSet<ID>(synIDList);
				for (UserInfo contact : bc.getContacts().values()) {
					Relation r = (Relation) contact
							.getInfoField(InfoFieldName.Relation);
					if (synIDSet.contains(contact.getBaseInfo().getID()))
						r.setPersonal(true);
					for (Group g : center.getAllGroupsBox().getGroups())
						if (g.getUserSet().contains(contact.getBaseInfo().getID()))
							r.addGroup(g.getInfoField(
									InfoFieldName.GroupName.name())
									.getStringValue());
				}
				setUpdateNow();
			} catch (AuthenticationException e) {
				//IMPOSSIBLE
				e.printStackTrace();
			} catch (MalformedURLException e) {
				//IMPOSSIBLE
				e.printStackTrace();
			} catch (ServiceException e) {
				//IMPOSSIBLE
				e.printStackTrace();
			} catch (IOException e) {
				//IMPOSSIBLE
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
				setError(ErrorType.SQL_ERROR);
			}
		}
	}

	public int getCnt() {
		return bc.getContacts().size();
	}

	public List<UserInfo> getContacts() {
		List<UserInfo> res = new ArrayList<UserInfo>();
		for (UserInfo info : bc.getContacts().values())
			if (!((Relation) info.getInfoField(InfoFieldName.Relation))
					.isRemoved())
				res.add(info);
		return res;
	}

	public AllContactsBox(LogicCenter center, BoxContent bc) {
		this.bc = bc;
		this.center = center;
		GetTask task = new GetTask();
		center.getExecutor().execute(task);
	}

	// MAY LEAD TO DEAD LOCK
	protected void editContactImp(UserInfo newInfo) {
		// 如果customInfo是null，那么不改变原来的customInfo
		UserInfo oldInfo = bc.getContacts().get(newInfo.getBaseInfo().getID());
		if (oldInfo != null && newInfo.getCustomInfo() == null)
			newInfo.setCustomInfo(oldInfo.getCustomInfo());
		if (newInfo.getCustomInfo() == null)
			newInfo.setCustomInfo(new CustomUserInfo());

		List<ID> synIDList = center.getDataCenter().getAllSynContactsID();
		Set<ID> synIDSet = (synIDList == null) ? new HashSet<ID>()
				: new HashSet<ID>(synIDList);
		Relation r = (Relation) newInfo.getInfoField(InfoFieldName.Relation);
		r.setEmpty();
		if (synIDSet.contains(newInfo.getBaseInfo().getID()))
			r.setPersonal(true);
		for (Group g : center.getAllGroupsBox().getGroups())
			if (g.getUserSet().contains(newInfo.getBaseInfo().getID()))
				r.addGroup(g.getInfoField(InfoFieldName.GroupName.name())
						.getStringValue());
		bc.getContacts().put(newInfo.getBaseInfo().getID(), newInfo);
	}

	/**
	 * 该方法应该是LogicCenter在修改以后调用的， GUI不用这个来修改，而是用LogicCenter的接口来修改
	 * 
	 * @param newInfo
	 */
	public void editContact(UserInfo newInfo) {
		editContactImp(newInfo);
		setUpdateNow();
	}
	
	@Override
	protected void setUpdateNow() {
		super.setUpdateNow();
		center.getAllGroupsBox().setUpdateNow();
	}

	public void removeContact(ID uid) {
		bc.getContacts().remove(uid);
		setUpdateNow();
	}

	public void updateAll() {
		GetTask task = new GetTask();
		center.getExecutor().execute(task);
	}

	public Map<ID, UserInfo> getContactsMap() {
		return bc.getContacts();
	}

	/**
	 * 写在这里是为了减少AllContactsBox更新状态的次数。 如果这个函数写在LogicCenter或者其它地方，那么每
	 * 新加一个用户，都会让AllContactBox更新一次状态。
	 * 
	 * @param g
	 */
	public void updateGroupMembers(Group g) throws RemoteException,
			MyRemoteException {
		List<ID> newIDList = new ArrayList<ID>();
		List<ID> oldIDList = new ArrayList<ID>();
		for (ID id : g.getUserSet())
			if (!bc.getContacts().containsKey(id))
				newIDList.add(id);
			else
				oldIDList.add(id);
		List<BaseUserInfo> newUsers = center.getServer().getContactsInfo(
				center.getLoginUser().getID(), newIDList);
		for (BaseUserInfo bInfo : newUsers) {
			UserInfo newInfo = new UserInfo(bInfo);
			newInfo.setCustomInfo(null);// 不改变本地字段
			editContactImp(new UserInfo(bInfo));
		}
		updateRelation(oldIDList);//setUpdateNow在updateRelation中
	}

	/**
	 * 更新idList中所有用户的关系字段
	 * 
	 * @param idList
	 */
	public void updateRelation(List<ID> idList) {
		for (ID id : idList)
			if (bc.getContacts().containsKey(id))
				editContactImp(bc.getContacts().get(id));
		setUpdateNow();
	}

	/**
	 * 获取所有要删除的联系人的信息
	 */
	public List<UserInfo> getRemovedContacts() {
		List<UserInfo> res = new ArrayList<UserInfo>();
		for (UserInfo info : bc.getContacts().values())
			if (((Relation) info.getInfoField(InfoFieldName.Relation))
					.isRemoved())
				res.add(info);
		return res;
	}

	/**
	 * 将removed的用户清空
	 */
	public void clearRemovedContacts() {
		for (ID id : bc.getContacts().keySet())
			if (((Relation) bc.getContacts().get(id).getInfoField(
					InfoFieldName.Relation)).isRemoved()) {
				bc.getContacts().remove(id);
				center.getDataCenter().removeUserInfo(id);
			}
	}
}
