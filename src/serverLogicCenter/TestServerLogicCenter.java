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
import entity.IDFactory;
import entity.MyRemoteException;
import entity.Password;
import entity.Permission;
import entity.infoField.IdenticalInfoField;
import entity.message.Message;
import entity.message.MessageSender;
import entity.message.SimpleStringMessage;

public class TestServerLogicCenter extends ServerLogicCenterImp {

	@Override
	public List<Message> getAllMessages(ID user) throws MyRemoteException{
		return new ArrayList<Message>();
	}

	@Override
	public BaseUserInfo login(IdenticalInfoField identicalInfo, Password pwd) throws RemoteException ,MyRemoteException{
		BaseUserInfo thisUser = new BaseUserInfo();
		thisUser.setID(idFactory.getNewUserID());
		onlineUsers.add(thisUser.getID());
		senders.put(thisUser.getID(), new MessageSender(thisUser.getID()));

		return thisUser;
	}
	
	public static void main(String args[])
	{
		try
		{
			ServerLogicCenter obj = ServerLogicCenterImp.getInstance();
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
}
