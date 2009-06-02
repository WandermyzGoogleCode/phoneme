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
import entity.ID;
import entity.MyRemoteException;
import entity.Password;
import entity.UserInfo;
import entity.infoField.IdenticalInfoField;

public class LoginResult extends OneTimeVirtualResult {
	private IdenticalInfoField identicalInfo;
	private Password pwd;

	public LoginResult(IdenticalInfoField identicalInfo, Password pwd,
			LogicCenter center) {
		super(center);
		this.identicalInfo = identicalInfo;
		this.pwd = pwd;
		if (!noLoginUser())
			setError(ErrorType.ALREADY_ONLINE);
		else
			thread.start();
	}

	@Override
	protected BoolInfo getResult() throws RemoteException, MyRemoteException {
		BaseUserInfo loginUser = center.getServer().login(identicalInfo, pwd);

		//需要删除的同步关系
		for(UserInfo user: center.getAllContactsBox().getRemovedContacts())
			center.getServer().removeSynContact(loginUser.getID(), user.getBaseInfo().getID());
		center.getAllContactsBox().clearRemovedContacts();
		
		//更新同步关系
		Set<ID> synIDSet = new HashSet<ID>(center.getServer().getSynRelations(loginUser.getID()));
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
		
		//更新被授权关系
		Set<ID> perIDSet = new HashSet<ID>(center.getServer().getPerRelationis(loginUser.getID()));
		Set<ID> oldPerIDSet = new HashSet<ID>(center.getDataCenter().getAllPerContactsID());
		for(ID id: perIDSet)
			if (!oldPerIDSet.contains(id))
				center.getDataCenter().addPerRelationship(id);
		for(ID id: oldPerIDSet)
			if (!perIDSet.contains(id))
				center.getDataCenter().removePerRelationship(id);
		
		center.setLoginUser(loginUser);		
		return new BoolInfo();
	}
}
