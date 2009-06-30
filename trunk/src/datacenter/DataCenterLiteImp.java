package datacenter;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import SQLite.JDBCDriver;

import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;

import datacenter.google.GoogleContactOperator;

import serverLogicCenter.sdataCenter.GroupInfoTable;
import serverLogicCenter.sdataCenter.GroupMemTable;
import serverLogicCenter.sdataCenter.PerRelationTable;
import serverLogicCenter.sdataCenter.PermissionTable;
import serverLogicCenter.sdataCenter.SynRelationTable;

import entity.GoogleSynSource;
import entity.Group;
import entity.ID;
import entity.LocalSynSource;
import entity.Permission;
import entity.ReturnType;
import entity.UserInfo;

public class DataCenterLiteImp implements DataCenter {
	private String liteUrl = "jdbc:sqlite://.//PhoneMe.dblite";

	private static DataCenterLiteImp instance = null;

	private UserInfoTable userInfoTable;
	private GroupInfoTable groupInfoTable;
	private GroupMemTable groupMemTable;
	private SynRelationTable synRelationTable;
	private PerRelationTable perRelationTable;

	@Override
	public ReturnType addPerRelationship(ID uid) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 构造函数，进行初始化类成员变量，若表不存在则建表等操作
	 */
	private DataCenterLiteImp() {
		// 建表
		try {
		   Class.forName("SQLite.JDBCDriver").newInstance();
		   java.sql.Connection connection = DriverManager.getConnection(liteUrl);
			userInfoTable = new UserInfoTable(connection);
			groupInfoTable = new GroupInfoTable(connection);
			groupMemTable = new GroupMemTable(connection);
			synRelationTable = new SynRelationTable(connection);
			perRelationTable = new PerRelationTable(connection);
		} catch (Exception e) {
			System.out.println(e);
			System.exit(0);
		}
	}

	synchronized static public DataCenterLiteImp getInstance() {
		if (instance == null)
			instance = new DataCenterLiteImp();
		return instance;
	}

	@Override
	public ReturnType addSynRelationship(ID uid) {
		try {
			synRelationTable.setRelation(ID.LOCAL_ID, uid);
		} catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ReturnType addToGroup(Group g, ID uid) {
		try {
			groupMemTable.setRelation(g.getID(), uid);
		} catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ReturnType exportFile(String fileName) {
		// TODO Auto-generated method stub
		return null;
	}

	public Group getGroup(ID gid) {
		try {
			Group res;
			res = groupInfoTable.getGroupInfo(gid);
			List<ID> members = groupMemTable.getRelations(gid);
			res.setIDSet(members);
			return res;
		} catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
		}
		return new Group();
	}

	@Override
	public List<Group> getAllGroups() {
		List<Group> res = new ArrayList<Group>();
		try {
			List<ID> gidList;
			gidList = groupInfoTable.getAllGroupID();
			for (ID id : gidList)
				res.add(getGroup(id));
		} catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public List<ID> getAllPerContactsID() {
		try {
			return perRelationTable.getRelations(ID.LOCAL_ID);
		} catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
		}
		return new ArrayList<ID>();
	}

	@Override
	public List<ID> getAllSynContactsID() {
		try {
			return synRelationTable.getRelations(ID.LOCAL_ID);
		} catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
		}
		return new ArrayList<ID>();
	}

	@Override
	public List<UserInfo> getAllUserInfo(LocalSynSource source) throws MalformedURLException, ServiceException, IOException, SQLException {
		if (source instanceof GoogleSynSource) {
			GoogleContactOperator operator = 
					((GoogleSynSource) source).getOperator();
			return operator.getAllContacts();
		}
		return userInfoTable.getAllUserInfo();
	}

	@Override
	public ReturnType importFile(String fileName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReturnType removeFromGroup(Group g, ID uid) {
		try {
			groupMemTable.removeRelation(g.getID(), uid);
		} catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ReturnType removeGroup(Group g) {
		try {
			groupInfoTable.removeGroup(g);
			for (ID id : g.getUserSet())
				groupMemTable.removeRelation(g.getID(), id);
		} catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ReturnType removePerRelationship(ID uid) {
		try {
			perRelationTable.removeRelation(ID.LOCAL_ID, uid);
		} catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ReturnType removeSynRelationship(ID uid) {
		try {
			synRelationTable.removeRelation(ID.LOCAL_ID, uid);
		} catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ReturnType removeUserInfo(ID uid) {
		try {
			userInfoTable.removeUserInfo(uid);
		} catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ReturnType setGroup(Group g) {
		try {
			groupInfoTable.setGroup(g);
			Set<ID> oldMemSet = new HashSet<ID>(groupMemTable.getRelations(g
					.getID()));
			for (ID id : g.getUserSet())
				if (!oldMemSet.contains(id))
					addToGroup(g, id);
			for (ID id : oldMemSet)
				if (!g.getUserSet().contains(id))
					removeFromGroup(g, id);
		} catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 暂未实现
	 */
	@Override
	public ReturnType setPermission(ID id, Permission p) {
		return null;
	}

	@Override
	public ReturnType setUserInfo(UserInfo b) {
		try {
			userInfoTable.setUserInfo(b.getBaseInfo().getID(), b);
		} catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void updateSource(List<UserInfo> users, LocalSynSource source) throws IOException, ServiceException {
		if (!(source instanceof GoogleSynSource))
			return;
		GoogleContactOperator operator = ((GoogleSynSource)source).getOperator();
		operator.updateAllContacts(users);
	}
}
