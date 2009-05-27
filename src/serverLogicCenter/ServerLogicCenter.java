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

public interface ServerLogicCenter extends Remote{
	/**
	 * 返回用户user所有未处理的信息
	 * @param user
	 * @return
	 */
	public List<Message> getAllMessages(ID user) throws RemoteException, MyRemoteException;
	/**
	 * 返回用户user最新更新的信息
	 * @param user
	 * @return
	 */
	public Message getNewMessage(ID user) throws RemoteException, MyRemoteException;
	
	public BoolInfo addPerContact(ID thisUser, IdenticalInfoField targetUser) throws RemoteException;
	
	public BoolInfo addSynContact(ID thisUser, IdenticalInfoField targetUser) throws RemoteException;

	public BoolInfo admitApplication(ID thisUser, ID gid, ID uid) throws RemoteException;
	public BoolInfo admitInvitation(ID thisUser, ID gid) throws RemoteException;
	public BoolInfo applyJoinGroup(ID thisUser, ID gid) throws RemoteException;
	public BoolInfo createGroup(ID thisUser, Group g, Permission p) throws RemoteException;
	public BoolInfo editGroup(ID thisUser, Group g) throws RemoteException;
	public BoolInfo editMyBaseInfo(BaseUserInfo baseInfo) throws RemoteException;
	public ID getUID(IdenticalInfoField identicalInfo) throws RemoteException;
	public BoolInfo inviteToGroup(ID id, IdenticalInfoField un, Group g,
			String inviteInfo) throws RemoteException;
	public BoolInfo quitGroup(ID thisUser, ID gid, String reason)throws RemoteException;
	public BoolInfo register(BaseUserInfo b, Password pwd) throws RemoteException;
	public BoolInfo removeGroup(ID thisUser, Group g) throws RemoteException;
	public BoolInfo removeGroupMember(ID thisUser, IdenticalInfoField un, Group g) throws RemoteException;
	public BoolInfo removePerContact(ID thisUser, ID targetID) throws RemoteException;
	public BoolInfo removeSynContact(ID thisUser, ID targetID) throws RemoteException;
	public BoolInfo setGroupPermission(ID thisUser, ID targetID, Permission p) throws RemoteException;
	public BoolInfo setVisibility(ID thisUser, ID targetID, int visibility) throws RemoteException;
}
