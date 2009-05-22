package serverLogicCenter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import entity.ID;
import entity.message.Message;
import entity.message.SimpleStringMessage;

public class ServerLogicCenterImp implements ServerLogicCenter {

	//TODO UNCOVER
	/*@Override
	public List<Message> getAllMessages(ID user) {
		// TODO 现在只是测试
		return new ArrayList<Message>();
	}*/

	//TODO UNCOVER
	/*@Override
	public Message getNewMessage(ID user) {
		// TODO 现在只是测试
		System.out.println("Waiting for next message...");
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
		String line = new String("IOException?");
		try
		{
			line = stdin.readLine();
		}
		catch (Exception e)
		{
			System.err.println("Exception: "+e.toString());
			e.printStackTrace();			
		}
		return new SimpleStringMessage(line);
	}*/

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
}
