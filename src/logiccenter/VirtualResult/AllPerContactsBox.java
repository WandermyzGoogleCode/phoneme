package logiccenter.VirtualResult;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import logiccenter.BoxContent;
import logiccenter.LogicCenter;
import entity.BaseUserInfo;
import entity.ErrorType;
import entity.ID;
import entity.MyRemoteException;
import entity.Permission;
import entity.UserInfo;

/**
 * 可以获取当前所有联系人的Virtual Proxy，类似 {@link AllContactsBox}
 * 
 * @author Administrator
 * 
 */
// TODO 当前所有被授权联系人在本地没有存任何信息
public class AllPerContactsBox extends VirtualResult {
	private LogicCenter center;
	private BoxContent bc;

	class GetTask implements Runnable {
		@Override
		public void run() {
			try {
				// TODO 本地数据库现在存储有问题，所以只好从远程拿
				List<ID> idList = center.getServer().getPerRelationis(
						center.getLoginUser().getID());
				List<BaseUserInfo> temp = center.getServer().getContactsInfo(
						center.getLoginUser().getID(), idList);
				bc.setPContacts(new HashMap<ID, UserInfo>());
				for (BaseUserInfo baseInfo : temp)
					bc.getPContacts().put(baseInfo.getID(), new UserInfo(baseInfo));
				List<Permission> pList = center.getServer().getPermissions(
						center.getLoginUser().getID(), idList);
				for (int i = 0; i < idList.size(); i++)
					bc.getPPermissions().put(idList.get(i), pList.get(i));
			} catch (MyRemoteException e) {
				setError(e.getErr());
			} catch (RemoteException e) {
				setError(ErrorType.REMOTE_ERROR);
			}
			setUpdateNow();
		}
	}

	public int getCnt() {
		return bc.getPContacts().size();
	}

	public List<UserInfo> getContacts() {
		return new ArrayList<UserInfo>(bc.getPContacts().values());
	}

	public AllPerContactsBox(LogicCenter center, BoxContent bc) {
		this.center = center;
		this.bc = bc;
		if (center.getLoginUser().isNull())
			setError(ErrorType.NOT_LOGIN);
		else {
			GetTask task = new GetTask();
			center.getExecutor().execute(task);
		}
	}

	public void removeContact(ID uid) {
		bc.getPContacts().remove(uid);
		setUpdateNow();
	}

	public void updateAll() {
		GetTask task = new GetTask();
		center.getExecutor().execute(task);
	}

	public void setPermission(ID id, Permission p) {
		bc.getPPermissions().put(id, p);
		setUpdateNow();
	}

	public Permission getPermission(ID id) {
		return bc.getPPermissions().get(id);
	}

	// MAY LEAD TO DEADLOCK
	public void addContact(ID uid) {
		List<ID> idList = new ArrayList<ID>();
		idList.add(uid);
		try {
			List<BaseUserInfo> temp = center.getServer().getContactsInfo(
					center.getLoginUser().getID(), idList);
			bc.getPContacts().put(temp.get(0).getID(), new UserInfo(temp.get(0)));
			List<Permission> pList = center.getServer().getPermissions(
					center.getLoginUser().getID(), idList);
			bc.getPPermissions().put(idList.get(0), pList.get(0));
		} catch (MyRemoteException e) {
			setError(e.getErr());
		} catch (RemoteException e) {
			setError(ErrorType.REMOTE_ERROR);
		}
		setUpdateNow();
	}
}
