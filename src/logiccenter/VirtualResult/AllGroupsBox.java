package logiccenter.VirtualResult;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import entity.ErrorType;
import entity.Group;
import entity.ID;
import entity.MyRemoteException;
import entity.Permission;

import logiccenter.BoxContent;
import logiccenter.LogicCenter;

public class AllGroupsBox extends VirtualResult {
	private LogicCenter center;
	private BoxContent bc;

	class GetTask implements Runnable {
		@Override
		public void run() {
			bc.setGroups(new ConcurrentHashMap<ID, Group>());
			if (center.getDataCenter().getAllGroups() != null) {
				List<ID> idList = new ArrayList<ID>();
				for (Group g : center.getDataCenter().getAllGroups()) {
					bc.getGroups().put(g.getID(), g);
					idList.add(g.getID());
				}
				if (!center.getLoginUser().isNull()) {
					try {
						List<Permission> pList = center.getServer()
								.getPermissions(center.getLoginUser().getID(),
										idList);
						for (int i = 0; i < idList.size(); i++)
							bc.getGPermissions().put(idList.get(i),
									pList.get(i));
					} catch (RemoteException e) {
						e.printStackTrace();
						setError(ErrorType.REMOTE_ERROR);
					} catch (MyRemoteException e) {
						e.printStackTrace();
						setError(e.getErr());
					}
				}
			}
			setUpdateNow();
		}
	}

	public AllGroupsBox(LogicCenter center, BoxContent bc) {
		this.center = center;
		this.bc = bc;
		updateAll();
	}

	public List<Group> getGroups() {
		return new ArrayList<Group>(bc.getGroups().values());
	}

	// MAY LEAD TO DEADLOCK
	/**
	 * 该方法应该是LogicCenter在修改以后调用的， GUI不用这个来修改，而是用LogicCenter的接口来修改
	 * 
	 * @param newInfo
	 */
	public void editGroup(Group group) {
		bc.getGroups().put(group.getID(), group);
		try {
			center.getAllContactsBox().updateGroupMembers(group);
		} catch (MyRemoteException e) {
			setError(e.getErr());
			return;
		} catch (RemoteException e) {
			setError(ErrorType.REMOTE_ERROR);
			return;
		}
		setUpdateNow();
	}

	public void removeGroup(ID gid) {
		Group g = bc.getGroups().get(gid);
		bc.getGroups().remove(gid);
		if (g != null)
			center.getAllContactsBox().updateRelation(g.getUsersID());// 更新所有人的关系
		setUpdateNow();
	}

	public void updateAll() {
		GetTask task = new GetTask();
		center.getExecutor().execute(task);
	}

	public Map<ID, Group> getGroupMap() {
		return bc.getGroups();
	}

	public void setPermission(ID targetID, Permission p) {
		bc.getGPermissions().put(targetID, p);
		setUpdateNow();
	}

	public Permission getPermission(ID id) {
		return bc.getGPermissions().get(id);
	}
}
