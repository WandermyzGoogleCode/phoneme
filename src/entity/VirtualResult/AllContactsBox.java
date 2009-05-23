package entity.VirtualResult;

import java.util.List;

import logiccenter.LogicCenter;

import entity.ID;
import entity.UserInfo;

/**
 * 可以获取当前所有联系人的Virtual Proxy，类似
 * {@link MessageBox}
 * @author Administrator
 *
 */
public class AllContactsBox extends VirtualResult{
	private LogicCenter center;
	private List<UserInfo> contacts;
	
	class GetThread extends Thread{
		@Override
		public void run() {
			contacts = center.getDataCenter().getAllUserInfo(null);
			setUpdateNow();
		}
	}
	
	public synchronized int getCnt(){
		return contacts.size();
	}
	
	public List<UserInfo> getContacts(){
		return contacts;
	}
	
	public AllContactsBox(LogicCenter center){
		this.center = center;
		GetThread thread = new GetThread();
		thread.run();
	}
	
	public synchronized void editContact(UserInfo newInfo){
		boolean found = false;
		for(UserInfo userInfo: contacts)
			if (userInfo.getBaseInfo().getID().equals(newInfo.getBaseInfo().getID()))
			{
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

	public synchronized void removeContact(ID uid){
		for(UserInfo userInfo: contacts)
			if (userInfo.getBaseInfo().getID().equals(uid))
			{
				contacts.remove(userInfo);
				break;
			}
		setUpdateNow();
	}
	
	public synchronized void updateAll(){
		GetThread thread = new GetThread();
		thread.run();
	}
}
