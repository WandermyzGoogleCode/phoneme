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
import java.util.Set;

import serverdatacenter.ServerDataCenter;

import entity.BaseUserInfo;
import entity.BoolInfo;
import entity.ErrorType;
import entity.Group;
import entity.ID;
import entity.MyRemoteException;
import entity.Password;
import entity.Permission;
import entity.infoField.IdenticalInfoField;
import entity.message.Message;
import entity.message.SimpleStringMessage;

public class TestServerLogicCenter extends ServerLogicCenterImp {

	@Override
	public List<Message> getAllMessages(ID user) throws MyRemoteException{
		return new ArrayList<Message>();
	}

	/**
	 * 当前只是测试，实际这应当只是一个FACADE点接口，具体由相关的子系统中的类来操作，中间可能还涉及到
	 * 很多多线程的操作。
	 */
	@Override
	public Message getNewMessage(ID user) throws MyRemoteException{
		// TODO 现在只是测试
		System.out.println("Waiting for next message...");
		//远程调用到底是多线程还是单线程的？如何不synchronized而进行WAIT？
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
		return new SimpleStringMessage(line);
	}

	@Override
	public BoolInfo addPerContact(ID thisUser, IdenticalInfoField targetUser, Permission permission)
			throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public BoolInfo addSynContact(ID thisUser, IdenticalInfoField targetUser)
			throws RemoteException {
		// TODO Auto-generated method stub
		return null;
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
		return new BoolInfo();
	}

	@Override
	public BoolInfo removeGroup(ID thisUser, Group g) throws RemoteException {
		return new BoolInfo();
	}

	@Override
	public BoolInfo removeGroupMember(ID thisUser, IdenticalInfoField un,
			Group g) throws RemoteException {
		return new BoolInfo();
	}

	@Override
	public BoolInfo removePerContact(ID thisUser, ID targetID)
			throws RemoteException {
		return new BoolInfo();
	}

	@Override
	public BoolInfo removeSynContact(ID thisUser, ID targetID)
			throws RemoteException {
		return new BoolInfo();
	}

	@Override
	public BoolInfo setGroupPermission(ID thisUser, ID targetID, Permission p)
			throws RemoteException {
		return new BoolInfo();
	}

	@Override
	public BoolInfo setVisibility(ID thisUser, ID targetID, int visibility)
			throws RemoteException {
		return new BoolInfo();
	}
}
