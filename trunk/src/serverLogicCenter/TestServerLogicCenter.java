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

import sdataCenter.ServerDataCenter;

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
		ID thisID = idFactory.getNewUserID();
		thisUser.setID(thisID);
		onlineUsers.add(thisID);
		senders.put(thisID, new MessageSender(thisID));
		return thisUser;
	}
	
	public static void main(String args[])
	{
		try
		{
			TestServerLogicCenter obj = new TestServerLogicCenter();
			ServerLogicCenter stub = (ServerLogicCenter) UnicastRemoteObject.exportObject(obj, 0);

		    // Bind the remote object's stub in the registry
		    Registry registry = LocateRegistry.getRegistry();
		    registry.bind("logicCenterServer", stub);

		    System.err.println("Server ready");

			BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
			while (true){
				System.out.println("f-fresh, now users:");
				for(ID id: obj.onlineUsers)
					System.out.println(id.getValue());
				String command = stdin.readLine();
				if (command.equals("f"))
					continue;
				Long idValue = Long.valueOf(command);
				MessageSender sender = obj.senders.get(new ID(idValue));
				if (sender == null){					
					System.out.println("Wrong ID:"+idValue);
					continue;
				}
				System.out.println("input a message:");
				String msg = stdin.readLine();
				sender.addMessage(new SimpleStringMessage(msg, obj.idFactory.getNewMessageID()));
			}
		}
		catch (Exception e)
		{
			System.err.println("Exception: "+e.toString());
			e.printStackTrace();
		}		
	}
}
