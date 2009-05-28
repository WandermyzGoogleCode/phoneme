package logiccenter.VirtualResult;

import java.util.List;

import logiccenter.LogicCenter;
import logiccenter.VirtualResult.AllContactsBox.GetThread;
import entity.BaseUserInfo;
import entity.ID;
import entity.UserInfo;

/**
 * 可以获取当前所有联系人的Virtual Proxy，类似
 * {@link AllContactsBox}
 * @author Administrator
 *
 */
public class AllPerContactsBox extends VirtualResult {
	private LogicCenter center;
	private List<BaseUserInfo> contacts;
	
	class GetThread extends Thread{
		@Override
		public void run() {
			//TODO NEXT
		}
	}
	
	public synchronized int getCnt(){
		return contacts.size();
	}
	
	public List<BaseUserInfo> getContacts(){
		return contacts;
	}
	
	public AllPerContactsBox(LogicCenter center){
		//TODO NEXT
		this.center = center;
		GetThread thread = new GetThread();
		thread.run();
	}
	
	public synchronized void removeContact(ID uid){
		for(BaseUserInfo userInfo: contacts)
			if (userInfo.getID().equals(uid))
			{
				contacts.remove(userInfo);
				break;
			}
		setUpdateNow();
	}
	
	public synchronized void updateAll(){
		//TODO
		GetThread thread = new GetThread();
		thread.run();
	}
}
