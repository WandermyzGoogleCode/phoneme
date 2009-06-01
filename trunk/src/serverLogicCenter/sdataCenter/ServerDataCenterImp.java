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
import entity.infoField.IndexedInfoField;
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
	
	private UserInfoTable userInfoTable;
	private GroupInfoTable groupInfoTable;
	private GroupMemTable groupMemTable;
	private SynRelationTable synRelationTable;
	private PerRelationTable perRelationTable;
	private VisibilityTable visibilityTable;
	private PermissionTable permissionTable;
	private MessageTable messageTable;
	private PwdTable pwdTable;
	private IDMapTable idMap;

	/**
	 * 构造函数，进行初始化类成员变量，若表不存在则建表等操作
	 */
	private ServerDataCenterImp() {
		// 建表
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection connection = (Connection) DriverManager
					.getConnection(url);
			userInfoTable = new UserInfoTable(connection);
			groupInfoTable = new GroupInfoTable(connection);
			groupMemTable = new GroupMemTable(connection);
			synRelationTable = new SynRelationTable(connection);
			perRelationTable = new PerRelationTable(connection);
			visibilityTable = new VisibilityTable(connection);
			permissionTable = new PermissionTable(connection);
			messageTable = new MessageTable(connection);
			pwdTable = new PwdTable(connection);
			idMap = new IDMapTable(connection);
		}
		catch (Exception e) {
			System.out.println(e);
			System.exit(0);
		}
	}
	
	public synchronized static ServerDataCenter getInstance(){
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
	public ReturnType addPerRelationship(ID uid1, ID uid2, Permission permission) throws SQLException{
		perRelationTable.setRelation(uid1, uid2);
		permissionTable.setPermission(uid1, uid2, permission);
		return null;
	}

	@Override
	public ReturnType addSynRelationship(ID uid1, ID uid2, int visibility) throws SQLException{
		synRelationTable.setRelation(uid1, uid2);
		visibilityTable.setVisibility(uid1, uid2, visibility);
		return null;
	}

	@Override
	public ReturnType addToGroup(Group g, ID uid, Permission permission) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Group getGroup(ID gid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Group> getGroups(ID uid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Message> getMessageBuffer(ID uid) throws SQLException {
		return messageTable.getAllMessages(uid);
	}

	@Override
	public List<ID> getPerContactID(ID uid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Permission> getPermissions(ID uid, List<ID> targetIDList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ID> getSynContactID(ID uid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BaseUserInfo> getUsersInfo(List<ID> idList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Integer> getVisibilities(ID uid, List<ID> targetIDList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isPerContact(ID id1, ID id2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ID loginGetInfo(IdenticalInfoField idField, Password pwd) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReturnType register(BaseUserInfo info, Password pwd) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReturnType removeFromGroup(Group g, ID uid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReturnType removeGroup(Group g) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BoolInfo removeMessageBuffer(ID uid, Message msg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReturnType removePerRelationship(ID uid1, ID uid2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReturnType removeSynRelationship(ID uid1, ID uid2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Group> searchGroup(Group info) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BaseUserInfo> searchUser(BaseUserInfo info) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ID searchUserID(IdenticalInfoField idInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReturnType setBaseUserInfo(ID uid, BaseUserInfo b) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReturnType setGroup(Group g) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReturnType setPermission(ID id1, ID id2, Permission permission) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReturnType setVisiblity(ID uid1, ID uid2, int visibility) {
		// TODO Auto-generated method stub
		return null;
	}

	public static void main(String args[]){
		ServerDataCenter center = ServerDataCenterImp.getInstance();
		//TODO TEST
		try {
			//TEST MESSAGE
			/*Message im = new GroupUpdatedMessage(null, "test", new ID(888));
			center.addMessageBuffer(new ID(198979), im);
			List<Message> list = center.getMessageBuffer(new ID(198979));
			Message m = list.get(0);
			System.out.println("lala");*/
			
			//TEST
			center.addPerRelationship(new ID(123), new ID(456), new Permission());
			center.addPerRelationship(new ID(123), new ID(456), new Permission());
			center.addSynRelationship(new ID(123), new ID(456), 10);
			center.addSynRelationship(new ID(123), new ID(456), 5);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
			e.printStackTrace();
		}
	}
}
