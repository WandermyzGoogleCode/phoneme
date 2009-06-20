package logiccenter.VirtualResult;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import logiccenter.LogicCenter;

import entity.BaseUserInfo;
import entity.BoolInfo;
import entity.ErrorType;
import entity.Group;
import entity.ID;
import entity.MyRemoteException;
import entity.UserInfo;

public class RemoteSynResult extends OneTimeVirtualResult {
	public RemoteSynResult(LogicCenter center) {
		super(center);
		if (noLoginUser())
			setError(ErrorType.NOT_LOGIN);
		else
			center.getExecutor().execute(task);
	}

	@Override
	protected BoolInfo getResult() throws RemoteException, MyRemoteException {
		// 更新群组信息
		List<Group> groups = center.getServer().getAllGroups(center.getLoginUser().getID());
		Set<ID> gidSet = new HashSet<ID>();
		for(Group g: groups){
			gidSet.add(g.getID());
			center.getDataCenter().setGroup(g);
			center.getAllGroupsBox().editGroup(g);
		}
		for(Group g: center.getAllGroupsBox().getGroups())
			if (!gidSet.contains(g.getID())){
				center.getDataCenter().removeGroup(g);
				center.getAllGroupsBox().removeGroup(g.getID());
			}
		
		//更新同步关系
		Set<ID> synIDSet = new HashSet<ID>(center.getServer().getSynRelations(center.getLoginUser().getID()));
		Set<ID> oldSynIDSet = new HashSet<ID>(center.getDataCenter().getAllSynContactsID());
		List<ID> changedList = new ArrayList<ID>();
		for(ID id: synIDSet)
			if (!oldSynIDSet.contains(id)){
				center.getDataCenter().addSynRelationship(id);
				changedList.add(id);
			}
		for(ID id: oldSynIDSet)
			if (!synIDSet.contains(id)){
				center.getDataCenter().removeSynRelationship(id);
				changedList.add(id);
			}
		center.getAllContactsBox().updateRelation(changedList);

		
		// 更新联系人
		Set<ID> idSet = new HashSet<ID>();
		idSet.addAll(center.getDataCenter().getAllSynContactsID());
		for(Group g: center.getAllGroupsBox().getGroups())
			for(ID id: g.getUserSet())
				idSet.add(id);
		List<BaseUserInfo> synContacts = center.getServer().getContactsInfo(
				center.getLoginUser().getID(),
				new ArrayList<ID>(idSet));
		for(BaseUserInfo info: synContacts){
			UserInfo newInfo = new UserInfo(info);
			newInfo.setCustomInfo(null);//不修改本地字段
			center.getDataCenter().setUserInfo(newInfo);
		}
		center.getAllContactsBox().updateAll();

		// TODO 由于当前被授权联系人不存在本地，所以不需要更新，以后改之

		return new BoolInfo();
	}

}
