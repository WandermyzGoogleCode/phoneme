package serverLogicCenter;

import java.io.BufferedReader;
import java.io.DataInputStream;
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

	@Override
	public List<Message> getAllMessages(ID user){
		// TODO 现在只是测试
		return new ArrayList<Message>();
	}

	/**
	 * 当前只是测试，实际这应当只是一个FACADE点接口，具体由相关的子系统中的类来操作，中间可能还涉及到
	 * 很多多线程的操作。
	 */
	@Override
	public Message getNewMessage(ID user) {
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
