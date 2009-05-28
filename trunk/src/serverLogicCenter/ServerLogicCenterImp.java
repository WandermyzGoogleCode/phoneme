package serverLogicCenter;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import entity.VirtualResult.AddSynContactResult;
import entity.infoField.IdenticalInfoField;
import entity.message.ApplySynContactMessage;
import entity.message.Message;
import entity.message.MessageSender;
import entity.message.SimpleStringMessage;

public class ServerLogicCenterImp implements ServerLogicCenter {
	private static ServerLogicCenterImp instance = null;
	
	private ServerDataCenter dataCenter;
	private Set<ID> onlineUsers;
	private Map<ID, MessageSender> senders;
	private IDFactory idFactory;
	
	private ServerLogicCenterImp(){
		//TODO ���ֳ�ʼ��
		idFactory = IDFactory.getInstance();
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
	 * ��ǰֻ�ǲ��ԣ�ʵ����Ӧ��ֻ��һ��FACADE��ӿڣ���������ص���ϵͳ�е������������м���ܻ��漰��
	 * �ܶ���̵߳Ĳ�����
	 */
	@Override
	public Message getNewMessage(ID user) throws MyRemoteException{
		// TODO ����ֻ�ǲ���
		System.out.println("Waiting for next message...");
		//Զ�̵��õ����Ƕ��̻߳��ǵ��̵߳ģ���β�synchronized������WAIT��
		String line = "";
		try
		{
			BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
			line = stdin.readLine();
		}
		catch (Exception e)
		{
			System.err.println("Exception: "+e.toString());
			e.printStackTrace();
		}
		System.out.println("New message send...");
		return new SimpleStringMessage(line, idFactory.getNewMessageID());
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
		// TODO ��ǰû�д���dataCenter���ܵĴ���
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
}