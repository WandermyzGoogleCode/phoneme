package serverLogicCenter;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import algorithm.BFSRelationCube;
import algorithm.Checker;
import algorithm.GroupChecker;
import algorithm.RelationCube;
import algorithm.SearchGroupChecker;
import algorithm.SearchUserChecker;
import algorithm.UserChecker;

import logiccenter.VirtualResult.AddSynContactResult;

import serverLogicCenter.sdataCenter.ServerDataCenter;
import serverLogicCenter.sdataCenter.ServerDataCenterImp;

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
import entity.infoField.IdenticalInfoFieldName;
import entity.infoField.InfoFieldFactory;
import entity.infoField.InfoFieldName;
import entity.message.*;

public class ServerLogicCenterImp implements ServerLogicCenter {
	private static ServerLogicCenterImp instance = null;

	protected ServerDataCenter dataCenter;
	protected Set<ID> onlineUsers;
	protected Map<ID, MessageSender> senders;
	protected IDFactory idFactory;
	protected RelationCube relationCube;

	protected Checker groupChecker, userChecker, searchGroupChecker,
			searchUserChecker;

	protected ServerLogicCenterImp() {
		idFactory = IDFactory.getInstance();
		dataCenter = ServerDataCenterImp.getInstance();
		senders = new ConcurrentHashMap<ID, MessageSender>();
		onlineUsers = new HashSet<ID>();
		groupChecker = new GroupChecker(dataCenter);
		userChecker = new UserChecker(dataCenter);
		searchGroupChecker = new SearchGroupChecker();
		searchUserChecker = new SearchUserChecker();
		relationCube = new BFSRelationCube();
	}

	synchronized static public ServerLogicCenter getInstance() {
		if (instance == null)
			instance = new ServerLogicCenterImp();
		return instance;
	}

	protected BaseUserInfo getUserInfo(ID id) throws SQLException {
		List<ID> idList = new ArrayList<ID>();
		idList.add(id);
		List<BaseUserInfo> res = dataCenter.getUsersInfo(idList);
		return res.get(0);
	}

	protected BaseUserInfo getUserInfo(ID targetID, ID getterID) {
		List<ID> idList = new ArrayList<ID>();
		idList.add(targetID);
		try {
			return getContactsInfo(getterID, idList).get(0);
		} catch (RemoteException e) {
			// 应该不会出错
			e.printStackTrace();
		} catch (MyRemoteException e) {
			// 应该不会出错
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Message> getAllMessages(ID user) throws MyRemoteException {
		if (!onlineUsers.contains(user))
			throw new MyRemoteException(ErrorType.NOT_ONLINE);
		try {
			return dataCenter.getMessageBuffer(user);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new MyRemoteException(ErrorType.SQL_ERROR);
		}
	}

	/**
	 * 当前只是测试，实际这应当只是一个FACADE点接口，具体由相关的子系统中的类来操作，中间可能还涉及到 很多多线程的操作。
	 */
	@Override
	public Message getNewMessage(ID user) throws MyRemoteException {
		if (!onlineUsers.contains(user))
			throw new MyRemoteException(ErrorType.NOT_ONLINE);
		MessageSender sender = senders.get(user);
		synchronized (sender) {
			while (sender.isAlive() && !sender.hasMessage()) {
				try {
					sender.wait();
				} catch (Exception e) {
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

	/**
	 * 给targetUser发msg，如果targetUser，直接发过去。 无论如何，数据库都留一份。
	 * 
	 * @param targetUser
	 * @param msg
	 * @throws SQLException
	 */
	protected void pushMessage(ID targetUser, Message msg) throws SQLException {
		dataCenter.addMessageBuffer(targetUser, msg);
		if (onlineUsers.contains(targetUser))
			senders.get(targetUser).addMessage(msg);
	}

	@Override
	public BoolInfo addPerContact(ID thisUser, IdenticalInfoField targetUser,
			Permission permission) throws RemoteException {
		if (!onlineUsers.contains(thisUser))
			return new BoolInfo(ErrorType.NOT_ONLINE);
		if (targetUser == null || permission == null)
			return new BoolInfo(ErrorType.ILLEGAL_NULL);
		ID targetID;
		try {
			targetID = dataCenter.searchUserID(targetUser);
			if (targetID == null || targetID.isNull())
				return new BoolInfo(ErrorType.TARGET_NOT_EXIST);
			dataCenter.addPerRelationship(thisUser, targetID, permission);
		} catch (SQLException e) {
			e.printStackTrace();
			return new BoolInfo(ErrorType.SQL_ERROR);
		}
		return new BoolInfo();
	}

	@Override
	public BoolInfo addSynContact(ID thisUser, IdenticalInfoField targetUser,
			int visibility) throws RemoteException {
		if (!onlineUsers.contains(thisUser))
			return new BoolInfo(ErrorType.NOT_ONLINE);
		if (targetUser == null)
			return new BoolInfo(ErrorType.ILLEGAL_NULL);
		ID targetID;
		try {
			targetID = dataCenter.searchUserID(targetUser);
			if (targetID == null || targetID.isNull())
				return new BoolInfo(ErrorType.TARGET_NOT_EXIST);
			if (dataCenter.isPerContact(thisUser, targetID)) {
				admitSynContact(targetID, thisUser, visibility);
				return new BoolInfo();
			}
			Message newMessage = new ApplySynContactMessage(
					getUserInfo(thisUser), targetID, visibility, idFactory
							.getNewMessageID());
			pushMessage(thisUser, newMessage);
		} catch (SQLException e) {
			e.printStackTrace();
			return new BoolInfo(ErrorType.SQL_ERROR);
		}
		return new BoolInfo();
	}

	@Override
	public BoolInfo admitApplication(ID thisUser, ID gid, ID uid, Permission p,
			int visibility) {
		if (!onlineUsers.contains(thisUser))
			return new BoolInfo(ErrorType.NOT_ONLINE);
		if (gid == null || uid == null || p == null)
			return new BoolInfo(ErrorType.ILLEGAL_NULL);
		Group g;
		try {
			g = dataCenter.getGroup(gid);
			if (g == null)
				return new BoolInfo(ErrorType.TARGET_NOT_EXIST);
			if (!g.getAdminUserID().equals(thisUser))
				return new BoolInfo(ErrorType.NOT_ADMIN);
			dataCenter.addToGroup(g, uid, p);
			dataCenter.setVisiblity(thisUser, gid, visibility);
			g.addToGroup(thisUser);
			pushMessage(thisUser, new GroupAppAdmitMessage(uid, g, p,
					visibility, idFactory.getNewMessageID()));
			String detail = "群组有新用户加入：" + getUserInfo(uid).getStringValue();
			for (ID id : g.getUserSet())
				if (!id.equals(uid))
					pushMessage(id, new GroupUpdatedMessage(g, detail,
							idFactory.getNewMessageID()));
		} catch (SQLException e) {
			e.printStackTrace();
			return new BoolInfo(ErrorType.SQL_ERROR);
		}
		return new BoolInfo();
	}

	@Override
	public BoolInfo admitInvitation(ID thisUser, ID gid, Permission p,
			int visibility) throws RemoteException {
		if (!onlineUsers.contains(thisUser))
			return new BoolInfo(ErrorType.NOT_ONLINE);
		if (gid == null || p == null)
			return new BoolInfo(ErrorType.ILLEGAL_NULL);
		Group g;
		try {
			g = dataCenter.getGroup(gid);
			if (g == null)
				return new BoolInfo(ErrorType.TARGET_NOT_EXIST);
			dataCenter.addToGroup(g, thisUser, p);
			dataCenter.setVisiblity(thisUser, gid, visibility);
			g.addToGroup(thisUser);
			pushMessage(thisUser, new GroupUpdatedMessage(g, "您已经加入该群组",
					idFactory.getNewMessageID()));
			String detail = "群组有新用户加入："
					+ getUserInfo(thisUser).getStringValue();
			for (ID id : g.getUserSet())
				if (!id.equals(thisUser))
					pushMessage(id, new GroupUpdatedMessage(g, detail,
							idFactory.getNewMessageID()));
		} catch (SQLException e) {
			e.printStackTrace();
			return new BoolInfo(ErrorType.SQL_ERROR);
		}
		return null;
	}

	@Override
	public BoolInfo applyJoinGroup(ID thisUser, ID gid, Permission p,
			int visibility) throws RemoteException {
		if (!onlineUsers.contains(thisUser))
			return new BoolInfo(ErrorType.NOT_ONLINE);
		if (gid == null || p == null)
			return new BoolInfo(ErrorType.ILLEGAL_NULL);
		Group g;
		try {
			g = dataCenter.getGroup(gid);
			if (g == null)
				return new BoolInfo(ErrorType.TARGET_NOT_EXIST);

			Permission tempP = getFinalPermission(thisUser, g.getAdminUserID());
			tempP.union(p);
			BaseUserInfo userInfo = getUserInfo(thisUser);
			userInfo = filter(userInfo, tempP);
			pushMessage(g.getAdminUserID(), new ApplyJoinGroupMessage(userInfo,
					g, p, visibility, g.getAdminUserID(), idFactory
							.getNewMessageID()));
		} catch (SQLException e) {
			e.printStackTrace();
			return new BoolInfo(ErrorType.SQL_ERROR);
		}
		return new BoolInfo();
	}

	@Override
	public Group createGroup(ID thisUser, Group g, Permission p, int visibility)
			throws RemoteException, MyRemoteException {
		if (!onlineUsers.contains(thisUser))
			throw new MyRemoteException(ErrorType.NOT_ONLINE);
		if (g == null || p == null)
			throw new MyRemoteException(ErrorType.ILLEGAL_NULL);
		if (!groupChecker.check(g))
			throw new MyRemoteException(ErrorType.ILLEGAL_NEW_INSTANCE);
		g.setID(idFactory.getNewGroupID());
		g.setAdminID(thisUser);
		try {
			dataCenter.setGroup(g);
			dataCenter.setPermission(thisUser, g.getID(), p);
			dataCenter.setVisiblity(thisUser, g.getID(), visibility);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new MyRemoteException(ErrorType.SQL_ERROR);
		}
		return g;
	}

	@Override
	public BoolInfo editGroup(ID thisUser, Group g) throws RemoteException {
		if (!onlineUsers.contains(thisUser))
			return new BoolInfo(ErrorType.NOT_ONLINE);
		if (g == null)
			return new BoolInfo(ErrorType.ILLEGAL_NULL);
		if (g.getID() == null || g.getID().isNull() || !groupChecker.check(g))
			return new BoolInfo(ErrorType.ILLEGAL_NEW_INSTANCE);
		try {
			dataCenter.setGroup(g);
			for (ID id : g.getUserSet())
				if (id != g.getAdminUserID())
					pushMessage(id, new GroupUpdatedMessage(g, "管理员修改了群组信息",
							idFactory.getNewMessageID()));
		} catch (SQLException e) {
			e.printStackTrace();
			return new BoolInfo(ErrorType.SQL_ERROR);
		}
		return new BoolInfo();
	}

	@Override
	public BoolInfo editMyBaseInfo(BaseUserInfo baseInfo, Password pwd)
			throws RemoteException {
		try {
			if (baseInfo == null)
				return new BoolInfo(ErrorType.ILLEGAL_NULL);
			if (!onlineUsers.contains(baseInfo.getID()))
				return new BoolInfo(ErrorType.NOT_ONLINE);
			if (!userChecker.check(baseInfo))
				return new BoolInfo(ErrorType.ILLEGAL_NEW_INSTANCE);
			dataCenter.setBaseUserInfo(baseInfo.getID(), baseInfo);

			Set<ID> idSet = new HashSet<ID>();

			if (pwd != null && !pwd.isNull())
				dataCenter.setPwd(baseInfo.getID(), pwd);

			// 通知被授权联系人
			List<ID> perIDList = dataCenter.getPerContactID(baseInfo.getID());
			for (ID id : perIDList)
				idSet.add(id);

			// 通知同群组的联系人
			List<Group> groups = dataCenter.getGroups(baseInfo.getID());
			for (Group g : groups)
				idSet.addAll(g.getUserSet());

			// 发送消息
			idSet.remove(baseInfo.getID());
			for (ID id : idSet)
				pushMessage(id, new ContactUpdatedMessage(baseInfo, idFactory
						.getNewMessageID()));
		} catch (SQLException e) {
			return new BoolInfo(ErrorType.SQL_ERROR);
		}
		return new BoolInfo();
	}

	@Override
	public ID getUID(IdenticalInfoField identicalInfo) throws RemoteException,
			MyRemoteException {
		if (identicalInfo == null)
			throw new MyRemoteException(ErrorType.ILLEGAL_NULL);
		try {
			return dataCenter.searchUserID(identicalInfo);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new MyRemoteException(ErrorType.SQL_ERROR);
		}
	}

	@Override
	public BoolInfo inviteToGroup(ID thisUser, IdenticalInfoField un, ID gid,
			String inviteInfo) throws RemoteException {
		if (!onlineUsers.contains(thisUser))
			return new BoolInfo(ErrorType.NOT_ONLINE);
		if (un == null || gid == null || inviteInfo == null)
			return new BoolInfo(ErrorType.ILLEGAL_NULL);
		Group g;
		try {
			g = dataCenter.getGroup(gid);
			if (g == null)
				return new BoolInfo(ErrorType.TARGET_NOT_EXIST);
			if (g.getAdminUserID() != thisUser)
				return new BoolInfo(ErrorType.NOT_ADMIN);
			ID targetID = dataCenter.searchUserID(un);
			if (targetID == null || targetID.isNull())
				return new BoolInfo(ErrorType.TARGET_NOT_EXIST);
			pushMessage(targetID, new InviteToGroupMessage(targetID, g,
					inviteInfo, idFactory.getNewMessageID()));
		} catch (SQLException e) {
			e.printStackTrace();
			return new BoolInfo(ErrorType.SQL_ERROR);
		}
		return new BoolInfo();
	}

	@Override
	public BoolInfo quitGroup(ID thisUser, ID gid, String reason)
			throws RemoteException {
		if (!onlineUsers.contains(thisUser))
			return new BoolInfo(ErrorType.NOT_ONLINE);
		if (gid == null || reason == null)
			return new BoolInfo(ErrorType.ILLEGAL_NULL);
		Group g;
		try {
			g = dataCenter.getGroup(gid);
			if (g == null)
				return new BoolInfo(ErrorType.TARGET_NOT_EXIST);
			if (g.getUserSet().contains(thisUser)) {
				g.removeFromGroup(thisUser);
				dataCenter.removeFromGroup(g, thisUser);
				String detail = "用户：" + getUserInfo(thisUser).getName()
						+ "退出了群组";
				for (ID id : g.getUserSet())
					pushMessage(id, new GroupUpdatedMessage(g, detail,
							idFactory.getNewMessageID()));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return new BoolInfo(ErrorType.SQL_ERROR);
		}
		return new BoolInfo();
	}

	@Override
	public BoolInfo register(BaseUserInfo b, Password pwd)
			throws RemoteException {
		if (b == null || pwd == null || pwd.isNull())
			return new BoolInfo(ErrorType.ILLEGAL_NULL);
		if (!userChecker.check(b))
			return new BoolInfo(ErrorType.ILLEGAL_NEW_INSTANCE);
		try {
			b.setID(idFactory.getNewUserID());
			dataCenter.register(b, pwd);
			// 加入默认的Global Permission
			dataCenter.setPermission(b.getID(), ID.GLOBAL_ID, Permission
					.getDefaultGlobalPermission());
		} catch (SQLException e) {
			e.printStackTrace();
			return new BoolInfo(ErrorType.SQL_ERROR);
		}
		return new BoolInfo();
	}

	@Override
	public BoolInfo removeGroup(ID thisUser, ID gid) throws RemoteException {
		if (!onlineUsers.contains(thisUser))
			return new BoolInfo(ErrorType.NOT_ONLINE);
		if (gid == null)
			return new BoolInfo(ErrorType.ILLEGAL_NULL);
		Group g;
		try {
			g = dataCenter.getGroup(gid);
			if (!g.getAdminUserID().equals(thisUser))
				return new BoolInfo(ErrorType.NOT_ADMIN);
			dataCenter.removeGroup(g);
			String detail = "管理员删除了群组";
			for (ID id : g.getUserSet())
				if (!id.equals(thisUser))
					pushMessage(id, new GroupRemovedMessage(g, detail,
							idFactory.getNewMessageID()));
		} catch (SQLException e) {
			e.printStackTrace();
			return new BoolInfo(ErrorType.SQL_ERROR);
		}
		return new BoolInfo();
	}

	@Override
	public BoolInfo removeGroupMember(ID thisUser, IdenticalInfoField un, ID gid)
			throws RemoteException {
		if (!onlineUsers.contains(thisUser))
			return new BoolInfo(ErrorType.NOT_ONLINE);
		if (gid == null || un == null)
			return new BoolInfo(ErrorType.ILLEGAL_NULL);
		Group g;
		try {
			g = dataCenter.getGroup(gid);
			if (!g.getAdminUserID().equals(thisUser))
				return new BoolInfo(ErrorType.NOT_ADMIN);
			ID targetUser = dataCenter.searchUserID(un);
			if (targetUser == null || targetUser.isNull())
				return new BoolInfo(ErrorType.TARGET_NOT_EXIST);
			g.removeFromGroup(targetUser);
			dataCenter.removeFromGroup(g, targetUser);
			String detail = "用户：" + getUserInfo(targetUser).getName() + "被删除";
			for (ID id : g.getUserSet())
				pushMessage(id, new GroupUpdatedMessage(g, detail, idFactory
						.getNewMessageID()));
			pushMessage(targetUser, new GroupRemovedMessage(g, "管理员将您从群组中删除",
					idFactory.getNewMessageID()));
		} catch (SQLException e) {
			e.printStackTrace();
			return new BoolInfo(ErrorType.SQL_ERROR);
		}
		return new BoolInfo();
	}

	@Override
	public BoolInfo removePerContact(ID thisUser, ID targetID)
			throws RemoteException {
		if (!onlineUsers.contains(thisUser))
			return new BoolInfo(ErrorType.NOT_ONLINE);
		if (targetID == null || targetID.isNull())
			return new BoolInfo(ErrorType.ILLEGAL_NULL);
		try {
			dataCenter.removePerRelationship(thisUser, targetID);
			pushMessage(targetID, new SynRelationLostMessage(thisUser,
					getUserInfo(thisUser).getName(), idFactory
							.getNewMessageID()));
		} catch (SQLException e) {
			e.printStackTrace();
			return new BoolInfo(ErrorType.SQL_ERROR);
		}
		return new BoolInfo();
	}

	@Override
	public BoolInfo removeSynContact(ID thisUser, ID targetID)
			throws RemoteException {
		if (!onlineUsers.contains(thisUser))
			return new BoolInfo(ErrorType.NOT_ONLINE);
		if (targetID == null || targetID.isNull())
			return new BoolInfo(ErrorType.ILLEGAL_NULL);
		try {
			dataCenter.removeSynRelationship(thisUser, targetID);
		} catch (SQLException e) {
			e.printStackTrace();
			return new BoolInfo(ErrorType.SQL_ERROR);
		}
		return new BoolInfo();
	}

	@Override
	public BoolInfo setPermission(ID thisUser, ID targetID, Permission p)
			throws RemoteException {
		if (!onlineUsers.contains(thisUser))
			return new BoolInfo(ErrorType.NOT_ONLINE);
		if (targetID == null || targetID.isNull() || p == null)
			return new BoolInfo(ErrorType.ILLEGAL_NULL);
		try {
			dataCenter.setPermission(thisUser, targetID, p);
		} catch (SQLException e) {
			e.printStackTrace();
			return new BoolInfo(ErrorType.SQL_ERROR);
		}
		return new BoolInfo();
	}

	@Override
	public BoolInfo setVisibility(ID thisUser, ID targetID, int visibility)
			throws RemoteException {
		if (!onlineUsers.contains(thisUser))
			return new BoolInfo(ErrorType.NOT_ONLINE);
		if (targetID == null || targetID.isNull())
			return new BoolInfo(ErrorType.ILLEGAL_NULL);
		try {
			dataCenter.setVisiblity(thisUser, targetID, visibility);
		} catch (SQLException e) {
			e.printStackTrace();
			return new BoolInfo(ErrorType.SQL_ERROR);
		}
		return new BoolInfo();
	}

	@Override
	public BaseUserInfo login(IdenticalInfoField identicalInfo, Password pwd)
			throws RemoteException, MyRemoteException {
		ID thisUser;
		try {
			thisUser = dataCenter.loginGetInfo(identicalInfo, pwd);
			if (thisUser.isNull())
				throw new MyRemoteException(ErrorType.LOGIN_FAILED);
			if (onlineUsers.contains(thisUser))
				throw new MyRemoteException(ErrorType.ALREADY_ONLINE);
			onlineUsers.add(thisUser);
			senders.put(thisUser, new MessageSender(thisUser));

			return getUserInfo(thisUser);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new MyRemoteException(ErrorType.SQL_ERROR);
		}
	}

	@Override
	public BoolInfo removeMessage(ID thisUser, Message msg)
			throws RemoteException {
		if (!onlineUsers.contains(thisUser))
			return new BoolInfo(ErrorType.NOT_ONLINE);
		try {
			dataCenter.removeMessageBuffer(thisUser, msg);
		} catch (SQLException e) {
			e.printStackTrace();
			return new BoolInfo(ErrorType.SQL_ERROR);
		}
		idFactory.putbackID(msg.getID());
		return new BoolInfo();
	}

	protected Permission getGlobalPermission(ID thisUser) throws SQLException {
		List<ID> idList = new ArrayList<ID>();
		idList.add(ID.GLOBAL_ID);
		List<Permission> res = dataCenter.getPermissions(thisUser, idList);
		return res.get(0);
	}

	/**
	 * 合成得到最终thisUser给targetUser的权限
	 * 
	 * @param thisUser
	 * @param targetUser
	 * @param groups
	 *            thisUser加入的所有群组
	 * @param gPs
	 *            thisUser对应的对于所有群组的权限设置
	 * @param p2pPermission
	 *            thisUser给targetUser设定的单独权限
	 * @return
	 * @throws SQLException
	 */
	protected Permission getFinalPermision(ID thisUser, ID targetUser,
			Permission p2pPermission, List<Group> groups, List<Permission> gPs)
			throws SQLException {
		Permission res = (p2pPermission == null) ? new Permission()
				: p2pPermission.clone();
		List<Group> gList2 = dataCenter.getGroups(targetUser);
		Set<ID> gidSet = new HashSet<ID>();
		for (Group g : gList2)
			gidSet.add(g.getID());
		for (int i = 0; i < groups.size(); i++) {
			Group g = groups.get(i);
			if (gidSet.contains(g.getID()))
				res.union(gPs.get(i));
		}
		res.union(getGlobalPermission(thisUser));
		return res;
	}

	/**
	 * 根据权限p来过滤info的信息
	 * 
	 * @param info
	 * @param p
	 * @return
	 */
	protected BaseUserInfo filter(BaseUserInfo info, Permission p) {
		BaseUserInfo res = new BaseUserInfo();
		res.setID(info.getID());
		for (String key : info.getKeySet())
			if (p.getField(key).booleanValue())
				res.setInfoField(key, info.getInfoField(key));
		return res;
	}

	/**
	 * 获取个人对个人的权限设置
	 * 
	 * @param thisUser
	 * @param targetUser
	 * @return
	 * @throws SQLException
	 */
	protected Permission getPermission(ID thisUser, ID targetUser)
			throws SQLException {
		List<ID> idList = new ArrayList<ID>();
		idList.add(targetUser);
		return dataCenter.getPermissions(thisUser, idList).get(0);
	}

	/**
	 * 获取thisUser给targetUser合并后（全局，群组，个人）的权限
	 * 
	 * @param thisUser
	 * @param targetUser
	 * @return
	 * @throws SQLException
	 */
	protected Permission getFinalPermission(ID thisUser, ID targetUser)
			throws SQLException {
		List<Group> gList1 = dataCenter.getGroups(thisUser);
		List<ID> gidList = new ArrayList<ID>();
		for (Group g : gList1)
			gidList.add(g.getID());
		List<Permission> gPs = dataCenter.getPermissions(thisUser, gidList);
		Permission p2pPermission = getPermission(thisUser, targetUser);
		return getFinalPermision(thisUser, targetUser, p2pPermission, gList1,
				gPs);
	}

	@Override
	public List<BaseUserInfo> getContactsInfo(ID thisUser, List<ID> idList)
			throws RemoteException, MyRemoteException {
		List<BaseUserInfo> contacts;
		try {
			contacts = dataCenter.getUsersInfo(idList);
			List<Permission> finalPermissions = new ArrayList<Permission>();

			if (!onlineUsers.contains(thisUser)) {
				List<Group> gList1 = dataCenter.getGroups(thisUser);
				List<ID> gidList = new ArrayList<ID>();
				for (Group g : gList1)
					gidList.add(g.getID());
				List<Permission> gPs = dataCenter.getPermissions(thisUser,
						gidList);
				List<Permission> p2pPermissions = dataCenter.getPermissions(
						thisUser, idList);
				for (int i = 0; i < idList.size(); i++)
					finalPermissions.add(getFinalPermision(thisUser, idList
							.get(i), p2pPermissions.get(i), gList1, gPs));
			} else
				for (int i = 0; i < idList.size(); i++)
					finalPermissions.add(getGlobalPermission(idList.get(i)));

			for (int i = 0; i < contacts.size(); i++)
				contacts.set(i,
						filter(contacts.get(i), finalPermissions.get(i)));
		} catch (SQLException e) {
			e.printStackTrace();
			throw new MyRemoteException(ErrorType.SQL_ERROR);
		}
		return contacts;
	}

	@Override
	public void admitSynContact(ID admitUser, ID targetUser, int visibility)
			throws RemoteException {
		senders.get(targetUser).addMessage(
				new AdmitSynContactMessage(getUserInfo(admitUser, targetUser),
						targetUser, visibility, idFactory.getNewMessageID()));
	}

	@Override
	public BoolInfo logout(ID thisUser) throws RemoteException {
		if (!onlineUsers.contains(thisUser))
			return new BoolInfo(ErrorType.NOT_ONLINE);
		onlineUsers.remove(thisUser);
		senders.get(thisUser).close();
		senders.remove(thisUser);
		return new BoolInfo();
	}

	@Override
	public List<Group> searchGroup(Group g) throws RemoteException,
			MyRemoteException {
		if (g == null)
			throw new MyRemoteException(ErrorType.ILLEGAL_NULL);
		if (!searchGroupChecker.check(g))
			throw new MyRemoteException(ErrorType.ILLEGAL_SEARCH);
		try {
			return dataCenter.searchGroup(g);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new MyRemoteException(ErrorType.SQL_ERROR);
		}
	}

	@Override
	public List<BaseUserInfo> searchRelationCube(IdenticalInfoField from,
			IdenticalInfoField to) throws RemoteException, MyRemoteException {
		if (from == null || to == null)
			throw new MyRemoteException(ErrorType.ILLEGAL_NULL);
		ID fromID, toID;
		try {
			fromID = dataCenter.searchUserID(from);
			toID = dataCenter.searchUserID(to);
			if (fromID == null || fromID.isNull() || toID == null
					|| toID.isNull())
				throw new MyRemoteException(ErrorType.TARGET_NOT_EXIST);
			List<ID> idRes = relationCube.getSearchRes(fromID, toID, dataCenter);
			List<BaseUserInfo> res = dataCenter.getUsersInfo(idRes);
			for (int i = 0; i < res.size(); i++)
				res.set(i,
						filter(res.get(i), getGlobalPermission(idRes.get(i))));
			return res;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new MyRemoteException(ErrorType.SQL_ERROR);
		}
	}

	@Override
	public List<BaseUserInfo> searchUser(BaseUserInfo b)
			throws RemoteException, MyRemoteException {
		if (b == null)
			throw new MyRemoteException(ErrorType.ILLEGAL_NULL);
		if (!searchGroupChecker.check(b))
			throw new MyRemoteException(ErrorType.ILLEGAL_SEARCH);
		List<BaseUserInfo> res;
		try {
			res = dataCenter.searchUser(b);
			for (int i = 0; i < res.size(); i++)
				res.set(i, filter(res.get(i), getGlobalPermission(res.get(i)
						.getID())));
		} catch (SQLException e) {
			e.printStackTrace();
			throw new MyRemoteException(ErrorType.SQL_ERROR);
		}
		return res;
	}

	@Override
	public List<ID> getPerRelationis(ID id) throws RemoteException,
			MyRemoteException {
		if (!onlineUsers.contains(id))
			throw new MyRemoteException(ErrorType.NOT_ONLINE);
		try {
			return dataCenter.getPerContactID(id);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new MyRemoteException(ErrorType.SQL_ERROR);
		}
	}

	@Override
	public List<ID> getSynRelations(ID id) throws RemoteException,
			MyRemoteException {
		if (!onlineUsers.contains(id))
			throw new MyRemoteException(ErrorType.NOT_ONLINE);
		try {
			return dataCenter.getSynContactID(id);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new MyRemoteException(ErrorType.SQL_ERROR);
		}
	}

	@Override
	public List<Group> getAllGroups(ID id) throws RemoteException,
			MyRemoteException {
		if (!onlineUsers.contains(id))
			throw new MyRemoteException(ErrorType.NOT_ONLINE);
		try {
			return dataCenter.getGroups(id);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new MyRemoteException(ErrorType.SQL_ERROR);
		}
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

	@Override
	public List<Permission> getPermissions(ID uid, List<ID> idList)
			throws RemoteException, MyRemoteException {
		if (!onlineUsers.contains(uid))
			throw new MyRemoteException(ErrorType.NOT_ONLINE);
		try {
			return dataCenter.getPermissions(uid, idList);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new MyRemoteException(ErrorType.SQL_ERROR);
		}
	}
}
