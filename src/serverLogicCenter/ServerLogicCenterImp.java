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
		// TODO ����ֻ�ǲ���
		return new ArrayList<Message>();
	}

	/**
	 * ��ǰֻ�ǲ��ԣ�ʵ����Ӧ��ֻ��һ��FACADE��ӿڣ���������ص���ϵͳ�е������������м���ܻ��漰��
	 * �ܶ���̵߳Ĳ�����
	 */
	@Override
	public Message getNewMessage(ID user) {
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
