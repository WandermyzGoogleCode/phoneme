package logiccenter.VirtualResult;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entity.ErrorType;
import entity.Group;
import entity.ID;
import entity.MyRemoteException;
import entity.Permission;

import logiccenter.LogicCenter;

public class AllGroupsBox extends VirtualResult {
	private LogicCenter center;
	private Map<ID, Group> groups;
	private Map<ID, Permission> permissions = new HashMap<ID, Permission>();
	
	class GetThread extends Thread{
		@Override
		public void run() {
			groups = new HashMap<ID, Group>();
			if (center.getDataCenter().getAllGroups() != null){
				List<ID> idList = new ArrayList<ID>();
				for(Group g: center.getDataCenter().getAllGroups()){
					groups.put(g.getID(), g);
					idList.add(g.getID());
				}
				if (!center.getLoginUser().isNull()){
					try {
						List<Permission> pList = center.getServer().getPermissions(center.getLoginUser().getID(), idList);
						for(int i=0; i<idList.size(); i++)
							permissions.put(idList.get(i), pList.get(i));
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
	
	public AllGroupsBox(LogicCenter center){
		this.center = center;
		updateAll();
	}
	
	public synchronized List<Group> getGroups(){
		return new ArrayList<Group>(groups.values());
	}
	
	/**
	 * 该方法应该是LogicCenter在修改以后调用的，
	 * GUI不用这个来修改，而是用LogicCenter的接口来修改
	 * @param newInfo
	 */
	public synchronized void editGroup(Group group){
		groups.put(group.getID(), group);
		try{
			center.getAllContactsBox().updateGroupMembers(group);
		}
		catch (MyRemoteException e) {
			setError(e.getErr());
			return;
		}
		catch (RemoteException e){
			setError(ErrorType.REMOTE_ERROR);
			return;
		}
		setUpdateNow();
	}

	public synchronized void removeGroup(ID gid){
		Group g = groups.get(gid);
		groups.remove(gid);
		if (g != null)
			center.getAllContactsBox().updateRelation(g.getUsersID());//更新所有人的关系
		setUpdateNow();
	}
	
	public synchronized void updateAll(){
		GetThread thread = new GetThread();
		thread.start();
	}
	
	public synchronized Map<ID, Group> getGroupMap(){
		return groups;
	}

	public void setPermission(ID targetID, Permission p) {
		permissions.put(targetID, p);
	}
}
