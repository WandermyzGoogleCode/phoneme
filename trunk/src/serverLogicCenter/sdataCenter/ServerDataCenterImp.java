package serverLogicCenter.sdataCenter;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import datacenter.DataCenterImp;

import entity.BaseUserInfo;
import entity.BoolInfo;
import entity.CustomUserInfo;
import entity.Group;
import entity.ID;
import entity.Password;
import entity.Permission;
import entity.ReturnType;
import entity.UserInfo;
import entity.infoField.IdenticalInfoField;
import entity.infoField.IdenticalInfoFieldName;
import entity.infoField.IndexedInfoField;
import entity.infoField.InfoField;
import entity.infoField.InfoFieldFactory;
import entity.infoField.InfoFieldName;
import entity.message.GroupUpdatedMessage;
import entity.message.Message;

public class ServerDataCenterImp implements ServerDataCenter {
	// 数据库用户名
	private String userName = "root";
	// 密码
	private String userPasswd = "81999";
	// 数据库名
	private String dbName = "PhoneMeServer";
	// 联结字符串
	private String url = "jdbc:mysql://localhost/" + dbName + "?user="
			+ userName + "&password=" + userPasswd;

	private static ServerDataCenterImp instance = null;

	private BaseUserInfoTable userInfoTable;
	private GroupInfoTable groupInfoTable;
	private GroupMemTable groupMemTable;
	private SynRelationTable synRelationTable;
	private PerRelationTable perRelationTable;
	private VisibilityTable visibilityTable;
	private PermissionTable permissionTable;
	private MessageTable messageTable;
	private PwdTable pwdTable;
	private IDMapTable idMapTable;

	/**
	 * 构造函数，进行初始化类成员变量，若表不存在则建表等操作
	 */
	private ServerDataCenterImp() {
		// 建表
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection connection = (Connection) DriverManager
					.getConnection(url);
			userInfoTable = new BaseUserInfoTable(connection);
			groupInfoTable = new GroupInfoTable(connection);
			groupMemTable = new GroupMemTable(connection);
			synRelationTable = new SynRelationTable(connection);
			perRelationTable = new PerRelationTable(connection);
			visibilityTable = new VisibilityTable(connection);
			permissionTable = new PermissionTable(connection);
			messageTable = new MessageTable(connection);
			pwdTable = new PwdTable(connection);
			idMapTable = new IDMapTable(connection);
		} catch (Exception e) {
			System.out.println(e);
			System.exit(0);
		}
	}

	public synchronized static ServerDataCenter getInstance() {
		if (instance == null)
			instance = new ServerDataCenterImp();
		return instance;
	}

	@Override
	public ReturnType addMessageBuffer(ID uid, Message msg) throws SQLException {
		messageTable.addMessage(msg.getID(), uid, msg);
		return null;
	}

	@Override
	public ReturnType addPerRelationship(ID uid1, ID uid2, Permission permission)
			throws SQLException {
		perRelationTable.setRelation(uid1, uid2);
		permissionTable.setPermission(uid1, uid2, permission);
		return null;
	}

	@Override
	public ReturnType addSynRelationship(ID uid1, ID uid2, int visibility)
			throws SQLException {
		synRelationTable.setRelation(uid1, uid2);
		visibilityTable.setVisibility(uid1, uid2, visibility);
		return null;
	}

	@Override
	public ReturnType addToGroup(Group g, ID uid, Permission permission)
			throws SQLException {
		groupMemTable.setRelation(g.getID(), uid);
		permissionTable.setPermission(uid, g.getID(), permission);
		return null;
	}

	@Override
	public Group getGroup(ID gid) throws SQLException {
		Group res = groupInfoTable.getGroupInfo(gid);
		List<ID> members = groupMemTable.getRelations(gid);
		res.setIDSet(members);
		return res;
	}

	@Override
	public List<Group> getGroups(ID uid) throws SQLException {
		List<Group> res = new ArrayList<Group>();
		List<ID> gidList = groupMemTable.getBackRelations(uid);
		for (ID id : gidList){
			Group g = getGroup(id);
			if (g.getID().isNull()){
				groupMemTable.removeRelation(id, uid);//群组不存在，删除该关系
				continue;
			}
			res.add(getGroup(id));
		}
		return res;
	}

	@Override
	public List<Message> getMessageBuffer(ID uid) throws SQLException {
		return messageTable.getAllMessages(uid);
	}

	@Override
	public List<ID> getPerContactID(ID uid) throws SQLException {
		return perRelationTable.getRelations(uid);
	}

	@Override
	public List<Permission> getPermissions(ID uid, List<ID> targetIDList)
			throws SQLException {
		return permissionTable.getPermissions(uid, targetIDList);
	}

	@Override
	public List<ID> getSynContactID(ID uid) throws SQLException {
		return synRelationTable.getRelations(uid);
	}

	@Override
	public List<BaseUserInfo> getUsersInfo(List<ID> idList) throws SQLException {
		return userInfoTable.getUsersInfo(idList);
	}

	@Override
	public List<Integer> getVisibilities(ID uid, List<ID> targetIDList)
			throws SQLException {
		return visibilityTable.getVisibilities(uid, targetIDList);
	}

	@Override
	public boolean isPerContact(ID id1, ID id2) throws SQLException {
		return perRelationTable.hasRelation(id1, id2);
	}

	@Override
	public ID loginGetInfo(IdenticalInfoField idField, Password pwd)
			throws SQLException {
		ID res = idMapTable.getID(idField);
		Password realPwd = pwdTable.getPwd(res);
		if (realPwd == null || !realPwd.equals(pwd))
			return ID.getNullID();
		else
			return res;
	}

	@Override
	public ReturnType register(BaseUserInfo info, Password pwd)
			throws SQLException {
		setBaseUserInfo(info.getID(), info);
		pwdTable.setPwd(info.getID(), pwd);
		return null;
	}

	@Override
	public ReturnType removeFromGroup(Group g, ID uid) throws SQLException {
		groupMemTable.removeRelation(g.getID(), uid);
		return null;
	}

	@Override
	public ReturnType removeGroup(Group g) throws SQLException {
		groupInfoTable.removeGroup(g);
		return null;
	}

	@Override
	public void removeMessageBuffer(ID uid, Message msg) throws SQLException {
		messageTable.removeMsg(uid, msg);
	}

	@Override
	public ReturnType removePerRelationship(ID uid1, ID uid2)
			throws SQLException {
		perRelationTable.removeRelation(uid1, uid2);
		permissionTable.removePermission(uid1, uid2);
		return null;
	}

	@Override
	public ReturnType removeSynRelationship(ID uid1, ID uid2)
			throws SQLException {
		synRelationTable.removeRelation(uid1, uid2);
		return null;
	}

	@Override
	public List<Group> searchGroup(Group info) throws SQLException {
		List<Group> groups = groupInfoTable.searchGroup(info);
		for (Group g : groups)
			g.setIDSet(groupMemTable.getRelations(g.getID()));
		return groups;
	}

	@Override
	public List<BaseUserInfo> searchUser(BaseUserInfo info) throws SQLException {
		return userInfoTable.searchUser(info);
	}

	@Override
	public ID searchUserID(IdenticalInfoField idInfo) throws SQLException {
		return idMapTable.getID(idInfo);
	}

	/**
	 * 方便获取单个用户
	 * 
	 * @param uid
	 * @return
	 * @throws SQLException
	 */
	public BaseUserInfo getUserInfo(ID uid) throws SQLException {
		List<ID> idList = new ArrayList<ID>();
		idList.add(uid);
		List<BaseUserInfo> res = getUsersInfo(idList);
		if (res.size() > 0)
			return res.get(0);
		else
			return new BaseUserInfo();
	}

	@Override
	public ReturnType setBaseUserInfo(ID uid, BaseUserInfo b)
			throws SQLException {
		// 把原来的IdenticalInfoField到ID的映射删除
		BaseUserInfo oldB = getUserInfo(uid);
		if (oldB != null)
			for (IdenticalInfoFieldName name : IdenticalInfoFieldName.values()) {
				InfoField field = oldB.getInfoField(name.name());
				if (field != null && !field.isEmpty())
					idMapTable.removeIDField((IdenticalInfoField) field);
			}

		userInfoTable.setUserInfo(uid, b);

		// 建立新的映射
		for (IdenticalInfoFieldName name : IdenticalInfoFieldName.values()) {
			InfoField field = b.getInfoField(name.name());
			if (!field.isEmpty())
				idMapTable.setID((IdenticalInfoField) field, uid);
		}
		return null;
	}

	@Override
	public ReturnType setGroup(Group g) throws SQLException {
		groupInfoTable.setGroup(g);
		return null;
	}

	@Override
	public ReturnType setPermission(ID id1, ID id2, Permission permission)
			throws SQLException {
		permissionTable.setPermission(id1, id2, permission);
		return null;
	}

	@Override
	public ReturnType setVisiblity(ID uid1, ID uid2, int visibility)
			throws SQLException {
		visibilityTable.setVisibility(uid1, uid2, visibility);
		return null;
	}

	public static void main(String args[]) {
		ServerDataCenter center = ServerDataCenterImp.getInstance();
		try {
			// TEST MESSAGE
			/*
			 * Message im = new GroupUpdatedMessage(null, "test", new ID(888));
			 * center.addMessageBuffer(new ID(198979), im); List<Message> list =
			 * center.getMessageBuffer(new ID(198979)); Message m = list.get(0);
			 * System.out.println("lala"); center.removeMessageBuffer(new
			 * ID(198979), im);
			 */

			// TEST RELATION
			/*
			 * center.addPerRelationship(new ID(123), new ID(456), new
			 * Permission()); center.addPerRelationship(new ID(123), new
			 * ID(456), new Permission()); center.addSynRelationship(new
			 * ID(123), new ID(456), 10); center.addSynRelationship(new ID(123),
			 * new ID(456), 5);
			 */

			// TEST GROUP
			/*
			 * Group g = new Group();
			 * g.setInfoField(InfoFieldFactory.getFactory().makeInfoField(
			 * InfoFieldName.GroupName, "test")); center.setGroup(g); for (int i
			 * = 0; i < 3; i++) center.addToGroup(g, new ID(i), new
			 * Permission()); Group rg = center.getGroup(ID.getNullID());
			 * System.out.println(rg.getName());
			 * System.out.println(rg.getUsersID()); Group sg = new Group();
			 * sg.setInfoField(InfoFieldFactory.getFactory().makeInfoField(
			 * InfoFieldName.GroupName, "es")); List<Group> gList =
			 * center.searchGroup(sg); rg = gList.get(0);
			 * System.out.println(rg.getName());
			 * System.out.println(rg.getUsersID());
			 */

			// TEST PERMISSIONS
			/*
			 * center.addPerRelationship(new ID(123), new ID(457), new
			 * Permission()); center.addPerRelationship(new ID(123), new
			 * ID(458), new Permission()); center.addPerRelationship(new
			 * ID(123), new ID(459), new Permission()); List<ID> idList = new
			 * ArrayList<ID>(); idList.add(new ID(457)); idList.add(new
			 * ID(458)); idList.add(new ID(459)); List<Permission> pList =
			 * center.getPermissions(new ID(123), idList);
			 * System.out.println(pList.size());
			 */

			// TEST USERINFO
			/*
			 * BaseUserInfo user = new BaseUserInfo();
			 * user.setInfoField(InfoFieldFactory.getFactory().makeInfoField(
			 * InfoFieldName.Name.name(), "TestUser"));
			 * center.setBaseUserInfo(user.getID(), user); List<ID> idList = new
			 * ArrayList<ID>(); idList.add(ID.getNullID()); BaseUserInfo su =
			 * new BaseUserInfo();
			 * su.setInfoField(InfoFieldFactory.getFactory().makeInfoField(
			 * InfoFieldName.Name.name(), "stU")); List<BaseUserInfo> ru =
			 * center.searchUser(su); System.out.println(ru.get(0).getName());
			 */

			// TEST VISIBILITY
			/*
			 * List<ID> idList = new ArrayList<ID>(); idList.add(new ID(456));
			 * List<Integer> vs = center.getVisibilities(new ID(123), idList);
			 * System.out.println(vs.get(0));
			 */

			// TEST REGISTER LOGIN
			BaseUserInfo user = new BaseUserInfo();
			user.setInfoField(InfoFieldFactory.getFactory().makeInfoField(
					InfoFieldName.Name.name(), "TestUser"));
			user.setInfoField(InfoFieldFactory.getFactory().makeInfoField(
					InfoFieldName.Cellphone, "13888888888"));
			user.setID(new ID(198979));
			Password pwd = new Password("lala");
			center.register(user, pwd);
			ID id = center.loginGetInfo((IdenticalInfoField) InfoFieldFactory
					.getFactory().makeInfoField(InfoFieldName.Cellphone,
							"13888888888"), pwd);
			ID id2 = center.loginGetInfo((IdenticalInfoField) InfoFieldFactory
					.getFactory().makeInfoField(InfoFieldName.Cellphone,
							"13888888888"), new Password("test"));
			System.out.println(id);
			System.out.println(id2);

			// TEST REMOVE RELATION
			// center.removeFromGroup(new Group(), new ID(0));
			// TEST REMOVE GROUP
			// center.removeGroup(new Group());
		} catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
		}
	}

	@Override
	public void setPwd(ID uid, Password pwd) throws SQLException {
		pwdTable.setPwd(uid, pwd);
	}
}
