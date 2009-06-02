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

import serverLogicCenter.sdataCenter.ServerDataCenter;

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
import entity.infoField.InfoFieldFactory;
import entity.infoField.InfoFieldName;
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
	
	public static void main(String args[]) {
		ServerLogicCenter center = ServerLogicCenterImp.getInstance();

		BaseUserInfo newUser = new BaseUserInfo();
		newUser.setInfoField(InfoFieldFactory.getFactory().makeInfoField(
				InfoFieldName.Name.name(), "SpaceFlyer"));
		newUser.setInfoField(InfoFieldFactory.getFactory().makeInfoField(
				InfoFieldName.Cellphone, "13888888881"));
		Password pwd = new Password("test");
		try {
//			BoolInfo rRes = center.register(newUser, pwd);
//			if (!rRes.isTrue()) {
//				System.out.println(rRes.getInfo());
//			}
			BaseUserInfo user = center.login(
					(IdenticalInfoField) (InfoFieldFactory.getFactory()
							.makeInfoField(InfoFieldName.Cellphone,
									"13888888887")), pwd);
			user.setInfoField(InfoFieldFactory.getFactory().makeInfoField(
					InfoFieldName.Cellphone, "13888888887"));
			user.setInfoField(InfoFieldFactory.getFactory().makeInfoField(
					InfoFieldName.EmailAddress, "test2@test.com"));
			BoolInfo res = center.editMyBaseInfo(user, null);
			if (!res.isTrue())
				System.out.println(res.getInfo());
			res = center.addPerContact(user.getID(),
					(IdenticalInfoField) (InfoFieldFactory.getFactory()
							.makeInfoField(InfoFieldName.EmailAddress,
									"test2@test.com")), new Permission());
			if (!res.isTrue())
				System.out.println(res.getInfo());
			res = center.addSynContact(user.getID(),
					(IdenticalInfoField) (InfoFieldFactory.getFactory()
							.makeInfoField(InfoFieldName.EmailAddress,
									"test2@test.com")), 3);
			if (!res.isTrue())
				System.out.println(res.getInfo());
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MyRemoteException e) {
			System.out.println(e.getErr());
			e.printStackTrace();
		}
	}
}
