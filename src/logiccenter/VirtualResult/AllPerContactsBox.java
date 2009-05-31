package logiccenter.VirtualResult;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import logiccenter.LogicCenter;
import entity.BaseUserInfo;
import entity.ErrorType;
import entity.ID;
import entity.MyRemoteException;
import entity.UserInfo;

/**
 * 可以获取当前所有联系人的Virtual Proxy，类似 {@link AllContactsBox}
 * 
 * @author Administrator
 * 
 */
//TODO 当前所有被授权联系人在本地没有存任何信息
public class AllPerContactsBox extends VirtualResult {
	private LogicCenter center;
	private List<UserInfo> contacts;

	class GetThread extends Thread {
		@Override
		public void run() {
			List<ID> idList = center.getDataCenter().getAllPerContactsID();
			try {
				List<BaseUserInfo> temp = center.getServer().getContactsInfo(center.getLoginUser().getID(), idList);
				contacts = new ArrayList<UserInfo>();
				for(BaseUserInfo baseInfo: temp)
					contacts.add(new UserInfo(baseInfo));
			} catch (MyRemoteException e) {
				setError(e.getErr());
			} catch (RemoteException e) {
				setError(ErrorType.REMOTE_ERROR);
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

	public AllPerContactsBox(LogicCenter center) {
		this.center = center;
		if (center.getLoginUser().isNull())
			setError(ErrorType.NOT_LOGIN);
		else {
			GetThread thread = new GetThread();
			thread.start();
		}
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
		thread.start();
	}
}
