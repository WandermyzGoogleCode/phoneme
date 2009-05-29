package logiccenter.VirtualResult;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import logiccenter.LogicCenter;

import entity.Group;
import entity.ID;
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
	private List<UserInfo> contacts;

	class GetThread extends Thread {
		@Override
		public void run() {
			contacts = center.getDataCenter().getAllUserInfo(null);
			Set<ID> synIDSet = new HashSet<ID>(center.getDataCenter()
					.getAllPerContactsID());
			for (UserInfo contact : contacts) {
				Relation r = new Relation();
				if (synIDSet.contains(contact.getBaseInfo().getID()))
					r.setPersonal(true);
				for (Group g : center.getAllGroupsBox().getGroups())
					if (g.getUserSet().contains(contact.getBaseInfo().getID()))
						r.addGroup(g.getInfoField(
								InfoFieldName.GroupName.name())
								.getStringValue());
				contact.setInfoField(r);
			}
			setUpdateNow();
		}
	}

	public synchronized int getCnt() {
		return contacts.size();
	}

	public List<UserInfo> getContacts() {
		return contacts;
	}

	public AllContactsBox(LogicCenter center) {
		this.center = center;
		GetThread thread = new GetThread();
		thread.run();
	}

	/**
	 * 该方法应该是LogicCenter在修改以后调用的， GUI不用这个来修改，而是用LogicCenter的接口来修改
	 * 
	 * @param newInfo
	 */
	public synchronized void editContact(UserInfo newInfo) {
		Set<ID> synIDSet = new HashSet<ID>(center.getDataCenter()
				.getAllPerContactsID());
		Relation r = new Relation();
		if (synIDSet.contains(newInfo.getBaseInfo().getID()))
			r.setPersonal(true);
		for (Group g : center.getAllGroupsBox().getGroups())
			if (g.getUserSet().contains(newInfo.getBaseInfo().getID()))
				r.addGroup(g.getInfoField(InfoFieldName.GroupName.name())
						.getStringValue());
		newInfo.setInfoField(r);
		boolean found = false;
		for (UserInfo userInfo : contacts)
			if (userInfo.getBaseInfo().getID().equals(
					newInfo.getBaseInfo().getID())) {
				userInfo.setBaseInfo(newInfo.getBaseInfo());
				if (newInfo.getCustomInfo() != null)
					userInfo.setCustomInfo(newInfo.getCustomInfo());
				found = true;
				break;
			}
		if (!found)
			contacts.add(newInfo);
		setUpdateNow();
	}

	public synchronized void removeContact(ID uid) {
		for (UserInfo userInfo : contacts)
			if (userInfo.getBaseInfo().getID().equals(uid)) {
				contacts.remove(userInfo);
				break;
			}
		setUpdateNow();
	}

	public synchronized void updateAll() {
		GetThread thread = new GetThread();
		thread.run();
	}
}
