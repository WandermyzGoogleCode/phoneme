package logiccenter.VirtualResult;

import java.rmi.RemoteException;
import java.util.List;

import logiccenter.LogicCenter;
import logiccenter.VirtualResult.AllContactsBox.GetThread;
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
public class AllPerContactsBox extends VirtualResult {
	private LogicCenter center;
	private List<BaseUserInfo> contacts;

	class GetThread extends Thread {
		@Override
		public void run() {
			List<ID> idList = center.getDataCenter().getAllPerContactsID();
			try {
				contacts = center.getServer().getContactsInfo(center.getLoginUser().getID(), idList);
			} catch (MyRemoteException e) {
				setError(e.getErr());
			} catch (RemoteException e) {
				// TODO 写入具体的错误信息
				setError(ErrorType.REMOTE_ERROR);
			}
			setUpdateNow();
		}
	}

	public synchronized int getCnt() {
		return contacts.size();
	}

	public List<BaseUserInfo> getContacts() {
		return contacts;
	}

	public AllPerContactsBox(LogicCenter center) {
		this.center = center;
		if (center.getLoginUser().isNull())
			setError(ErrorType.NOT_LOGIN);
		else {
			GetThread thread = new GetThread();
			thread.run();
		}
	}

	public synchronized void removeContact(ID uid) {
		for (BaseUserInfo userInfo : contacts)
			if (userInfo.getID().equals(uid)) {
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
