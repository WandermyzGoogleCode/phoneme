package serverLogicCenter;

import entity.BaseUserInfo;
import entity.BoolInfo;
import entity.Group;
import entity.ID;
import entity.MyRemoteException;
import entity.Password;
import entity.Permission;
import entity.infoField.IdenticalInfoField;
import entity.message.Message;
import java.rmi.RemoteException;

import java.rmi.Remote;
import java.util.List;

import serverLogicCenter.sdataCenter.ServerDataCenter;

public interface ServerLogicCenter extends Remote{
	public List<Message> getAllMessages(ID user) throws RemoteException, MyRemoteException;
	public Message getNewMessage(ID user) throws RemoteException, MyRemoteException;	
	public BoolInfo addPerContact(ID thisUser, IdenticalInfoField targetUser, Permission permission) throws RemoteException;	
	public BoolInfo addSynContact(ID thisUser, IdenticalInfoField targetUser, int visibility) throws RemoteException;
	public BoolInfo admitApplication(ID thisUser, ID gid, ID uid, Permission p, int visibility) throws RemoteException;
	public BoolInfo admitInvitation(ID thisUser, ID gid, Permission p, int visibility) throws RemoteException;
	public BoolInfo applyJoinGroup(ID thisUser, ID gid, Permission p, int visibility) throws RemoteException;
	public Group createGroup(ID thisUser, Group g, Permission p, int visibility) throws RemoteException, MyRemoteException;
	public BoolInfo editGroup(ID thisUser, Group g) throws RemoteException;
	public BoolInfo editMyBaseInfo(BaseUserInfo baseInfo) throws RemoteException;
	public ID getUID(IdenticalInfoField identicalInfo) throws RemoteException, MyRemoteException;
	public BoolInfo inviteToGroup(ID id, IdenticalInfoField un, ID gid,
			String inviteInfo) throws RemoteException;
	public BoolInfo quitGroup(ID thisUser, ID gid, String reason)throws RemoteException;
	public BoolInfo register(BaseUserInfo b, Password pwd) throws RemoteException;
	public BoolInfo removeGroup(ID thisUser, ID gid) throws RemoteException;
	public BoolInfo removeGroupMember(ID thisUser, IdenticalInfoField un, ID gid) throws RemoteException;
	public BoolInfo removePerContact(ID thisUser, ID targetID) throws RemoteException;
	public BoolInfo removeSynContact(ID thisUser, ID targetID) throws RemoteException;
	public BoolInfo setPermission(ID thisUser, ID targetID, Permission p) throws RemoteException;
	public BoolInfo setVisibility(ID thisUser, ID targetID, int visibility) throws RemoteException;
	public BaseUserInfo login(IdenticalInfoField identicalInfo, Password pwd) throws RemoteException, MyRemoteException;
	public BoolInfo removeMessage(ID thisUser, Message msg) throws RemoteException;
	public List<BaseUserInfo> getContactsInfo(ID thisUser, List<ID> idList) throws RemoteException, MyRemoteException;
	/**
	 * admitUser�Ѿ���׼targetUser��Ϊ����Ȩ��ϵ�ˣ����
	 * targetUser��admitUser��Ϊ�Լ�ͬ����ϵ�˵�����ɹ���
	 * �ù�ϵ�ɼ���Ϊvisibility��ע�⣬��ʱ���������Ѿ���
	 * admitUser��targetUser��Ȩ�����á�
	 * @param admitUser
	 * @param targetUser
	 * @param visibility
	 * @throws RemoteException
	 */
	public void admitSynContact(ID admitUser, ID targetUser, int visibility) throws RemoteException;
	/**
	 * �ͻ��˳������ʱ������øú���������Ѿ���¼�Ļ�������������������ͷſͻ����߳�
	 * @param thisUser
	 * @return
	 * @throws RemoteException
	 */
	public BoolInfo logout(ID thisUser) throws RemoteException;
	public List<BaseUserInfo> searchUser(BaseUserInfo b) throws RemoteException, MyRemoteException;
	public List<Group> searchGroup(Group g) throws RemoteException, MyRemoteException;
	public List<BaseUserInfo> searchRelationCube(IdenticalInfoField from,
			IdenticalInfoField to) throws RemoteException, MyRemoteException;
	
	public ServerDataCenter getDataCenter();
	public List<ID> getSynRelations(ID id) throws RemoteException, MyRemoteException;
	public List<ID> getPerRelationis(ID id) throws RemoteException, MyRemoteException;
	public List<Group> getAllGroups(ID id) throws RemoteException, MyRemoteException;
}
