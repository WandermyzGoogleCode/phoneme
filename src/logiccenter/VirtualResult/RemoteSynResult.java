package logiccenter.VirtualResult;

import java.rmi.RemoteException;
import java.util.List;

import logiccenter.LogicCenter;

import entity.BaseUserInfo;
import entity.BoolInfo;
import entity.ErrorType;
import entity.Group;
import entity.MyRemoteException;
import entity.UserInfo;

public class RemoteSynResult extends OneTimeVirtualResult {
	public RemoteSynResult(LogicCenter center) {
		super(center);
		if (noLoginUser())
			setError(ErrorType.NOT_LOGIN);
		else
			thread.start();
	}

	@Override
	protected BoolInfo getResult() throws RemoteException, MyRemoteException {
		// 首先，获取同步联系人最新信息
		List<BaseUserInfo> synContacts = center.getServer().getContactsInfo(
				center.getLoginUser().getID(),
				center.getDataCenter().getAllSynContactsID());
		for(BaseUserInfo info: synContacts){
			UserInfo newInfo = new UserInfo(info);
			newInfo.setCustomInfo(null);//不修改本地字段
			center.getDataCenter().setUserInfo(newInfo);
		}
		center.getAllContactsBox().updateAll();

		// TODO 由于当前被授权联系人不存在本地，所以不需要更新，以后改之

		// 然后，更新群组信息
		List<Group> groups = center.getServer().getAllGroups(center.getLoginUser().getID());
		for(Group g: groups){
			center.getDataCenter().setGroup(g);
			center.getAllGroupsBox().editGroup(g);
		}
		
		return new BoolInfo();
	}

}
