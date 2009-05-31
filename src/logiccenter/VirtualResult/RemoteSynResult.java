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
		// ���ȣ���ȡͬ����ϵ��������Ϣ
		List<BaseUserInfo> synContacts = center.getServer().getContactsInfo(
				center.getLoginUser().getID(),
				center.getDataCenter().getAllSynContactsID());
		for(BaseUserInfo info: synContacts){
			UserInfo newInfo = new UserInfo(info);
			newInfo.setCustomInfo(null);//���޸ı����ֶ�
			center.getDataCenter().setUserInfo(newInfo);
		}
		center.getAllContactsBox().updateAll();

		// TODO ���ڵ�ǰ����Ȩ��ϵ�˲����ڱ��أ����Բ���Ҫ���£��Ժ��֮

		// Ȼ�󣬸���Ⱥ����Ϣ
		List<Group> groups = center.getServer().getAllGroups(center.getLoginUser().getID());
		for(Group g: groups){
			center.getDataCenter().setGroup(g);
			center.getAllGroupsBox().editGroup(g);
		}
		
		return new BoolInfo();
	}

}
