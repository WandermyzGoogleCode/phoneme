package serverLogicCenter;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import logiccenter.VirtualResult.AddSynContactResult;

import serverdatacenter.ServerDataCenter;

import entity.BaseUserInfo;
import entity.BoolInfo;
import entity.ErrorType;
import entity.Group;
import entity.ID;
import entity.IDFactory;
import entity.MyRemoteException;
import entity.Password;
import entity.Permission;
import entity.infoField.IdenticalInfoField;
import entity.message.ApplySynContactMessage;
import entity.message.Message;
import entity.message.MessageSender;
import entity.message.SimpleStringMessage;

public class ServerLogicCenterImp implements ServerLogicCenter {
	private static ServerLogicCenterImp instance = null;
	
	protected ServerDataCenter dataCenter;
	protected Set<ID> onlineUsers;
	protected Map<ID, MessageSender> senders;
	protected IDFactory idFactory;
	
	protected ServerLogicCenterImp(){
		//TODO 各种初始化
		idFactory = IDFactory.getInstance();
		senders = new HashMap<ID, MessageSender>();
		onlineUsers = new HashSet<ID>();
	}
	
	synchronized static public ServerLogicCenter getInstance(){
		if (instance == null)
			instance = new ServerLogicCenterImp();
		return instance;
	}

	protected BaseUserInfo getUserInfo(ID id){
		List<ID> idList = new ArrayList<ID>();
		idList.add(id);
		List<BaseUserInfo> res = dataCenter.getUsersInfo(idList);
		return res.get(0);
	}
	
	@Override
	public List<Message> getAllMessages(ID user) throws MyRemoteException{
		if (!onlineUsers.contains(user))
			throw new MyRemoteException(ErrorType.NOT_ONLINE);
		return dataCenter.getMessageBuffer(user);
	}

	/**
	 * 当前只是测试，实际这应当只是一个FACADE点接口，具体由相关的子系统中的类来操作，中间可能还涉及到
	 * 很多多线程的操作。
	 */
	@Override
	public Message getNewMessage(ID user) throws MyRemoteException{
		MessageSender sender = senders.get(user);
		synchronized (sender) {
			while (sender.isAlive() && !sender.hasMessage()){
				try{
					sender.wait();
				}
				catch (Exception e) {
					System.err.println("Exception: " + e.toString());
					e.printStackTrace();
					break;
				}
			}
			if (sender.isAlive() && sender.hasMessage())
				return sender.getMessage();
		}
		return null;
	}

	@Override
	public BoolInfo addPerContact(ID thisUser, IdenticalInfoField targetUser, Permission permission)
			throws RemoteException {
		if (!onlineUsers.contains(thisUser))
			return new BoolInfo(ErrorType.NOT_ONLINE);
		ID targetID = dataCenter.searchUserID(targetUser);
		if (targetID.isNull())
			return new BoolInfo(ErrorType.TARGET_NOT_EXIST);
		dataCenter.addPerRelationship(thisUser, targetID, permission);
		return new BoolInfo();
	}
	
	@Override
	public BoolInfo addSynContact(ID thisUser, IdenticalInfoField targetUser)
			throws RemoteException {
		//TODO 已经授权的处理
		if (!onlineUsers.contains(thisUser))
			return new BoolInfo(ErrorType.NOT_ONLINE);
		ID targetID = dataCenter.searchUserID(targetUser);
		if (targetID.isNull())
			return new BoolInfo(ErrorType.TARGET_NOT_EXIST);
		Message newMessage = new ApplySynContactMessage(getUserInfo(thisUser), targetID, idFactory.getNewMessageID());
		senders.get(thisUser).addMessage(newMessage);
		return new BoolInfo();
	}

	@Override
	public BoolInfo admitApplication(ID thisUser, ID gid, ID uid) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static void main(String args[])
	{
		try
		{
			ServerLogicCenterImp obj = new ServerLogicCenterImp();
			ServerLogicCenter stub = (ServerLogicCenter) UnicastRemoteObject.exportObject(obj, 0);

		    // Bind the remote object's stub in the registry
		    Registry registry = LocateRegistry.getRegistry();
		    registry.bind("logicCenterServer", stub);

		    System.err.println("Server ready");
		}
		catch (Exception e)
		{
			System.err.println("Exception: "+e.toString());
			e.printStackTrace();
		}
	}

	@Override
	public BoolInfo admitInvitation(ID thisUser, ID gid) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BoolInfo applyJoinGroup(ID thisUser, ID gid) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BoolInfo createGroup(ID thisUser, Group g, Permission p)
			throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BoolInfo editGroup(ID thisUser, Group g) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BoolInfo editMyBaseInfo(BaseUserInfo baseInfo)
			throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ID getUID(IdenticalInfoField identicalInfo) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BoolInfo inviteToGroup(ID id, IdenticalInfoField un, Group g,
			String inviteInfo) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BoolInfo quitGroup(ID thisUser, ID gid, String reason)
			throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BoolInfo register(BaseUserInfo b, Password pwd)
			throws RemoteException {
		dataCenter.register(b, pwd);
		// TODO 当前没有处理dataCenter可能的错误
		return new BoolInfo();
	}

	@Override
	public BoolInfo removeGroup(ID thisUser, Group g) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BoolInfo removeGroupMember(ID thisUser, IdenticalInfoField un,
			Group g) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BoolInfo removePerContact(ID thisUser, ID targetID)
			throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BoolInfo removeSynContact(ID thisUser, ID targetID)
			throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BoolInfo setGroupPermission(ID thisUser, ID targetID, Permission p)
			throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BoolInfo setVisibility(ID thisUser, ID targetID, int visibility)
			throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BaseUserInfo login(IdenticalInfoField identicalInfo, Password pwd)
			throws RemoteException, MyRemoteException {
		ID thisUser = dataCenter.loginGetInfo(identicalInfo, pwd);
		if (thisUser.isNull())
			throw new MyRemoteException(ErrorType.LOGIN_FAILED);
		if (onlineUsers.contains(thisUser))
			throw new MyRemoteException(ErrorType.ALREADY_ONLINE);
		onlineUsers.add(thisUser);
		senders.put(thisUser, new MessageSender(thisUser));

		return getUserInfo(thisUser);
	}

	@Override
	public BoolInfo ignoreMessage(ID thisUser, Message msg) throws RemoteException {
		if (!onlineUsers.contains(thisUser))
			return new BoolInfo(ErrorType.NOT_ONLINE);
		dataCenter.removeMessageBuffer(thisUser, msg);
		return new BoolInfo();
	}

	protected Permission getGlobalPermission(ID thisUser){
		List<ID> idList = new ArrayList<ID>();
		idList.add(ID.GLOBAL_ID);
		List<Permission> res = dataCenter.getPermissions(thisUser, idList);
		return res.get(0);
	}
	
	/**
	 * 合成得到最终thisUser给targetUser的权限
	 * @param thisUser
	 * @param targetUser
	 * @param groups thisUser加入的所有群组
	 * @param gPs thisUser对应的对于所有群组的权限设置
	 * @param p2pPermission thisUser给targetUser设定的单独权限
	 * @return
	 */
	protected Permission getFinalPermision(ID thisUser, ID targetUser, Permission p2pPermission, List<Group> groups, List<Permission> gPs){
		Permission res = p2pPermission.clone();
		List<Group> gList2 = dataCenter.getGroups(targetUser);
		Set<ID> gidSet = new HashSet<ID>();
		for(Group g: gList2)
			gidSet.add(g.getID());
		for(int i=0; i<groups.size(); i++){
			Group g = groups.get(i);
			if (gidSet.contains(g.getID()))
				res.union(gPs.get(i));
		}
		return res;
	}
	
	/**
	 * 根据权限p来过滤info的信息
	 * @param info
	 * @param p
	 * @return
	 */
	protected BaseUserInfo filter(BaseUserInfo info, Permission p){
		BaseUserInfo res = new BaseUserInfo();
		res.setID(info.getID());
		for(String key: info.getKeySet())
			if (p.getField(key).booleanValue())
				res.setInfoField(key, info.getInfoField(key));
		return res;
	}
	
	@Override
	public List<BaseUserInfo> getContactsInfo(ID thisUser, List<ID> idList)
			throws RemoteException, MyRemoteException {
		List<BaseUserInfo> contacts = dataCenter.getUsersInfo(idList);
		List<Group> gList1 = dataCenter.getGroups(thisUser);
		List<ID> gidList = new ArrayList<ID>();
		for(Group g: gList1)
			gidList.add(g.getID());
		List<Permission> gPs = dataCenter.getPermissions(thisUser, gidList);
		List<Permission> p2pPermissions = dataCenter.getPermissions(thisUser, idList);
		
		List<Permission> finalPermissions = new ArrayList<Permission>();
		for(int i=0; i<idList.size(); i++)
			finalPermissions.add(getFinalPermision(thisUser, idList.get(i), p2pPermissions.get(i), gList1, gPs));
		
		for(int i=0; i<contacts.size(); i++)
			contacts.set(i, filter(contacts.get(i), finalPermissions.get(i)));
		return contacts;
	}
}
