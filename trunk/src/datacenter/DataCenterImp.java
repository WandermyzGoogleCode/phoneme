package datacenter;

import java.io.FileReader;
import java.io.FileWriter;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import entity.*;
import entity.infoField.InfoFieldFactory;

//!!!!!!Singleton
public class DataCenterImp implements DataCenter {
	// 需要写入数据库的各种数据的缓存
	private List<UserInfo> userInfoWriteBuffer;
	private List<Permission> permissionWriteBuffer;
	private List<Group> groupWriteBuffer;
	private List<ID> syncIDWriteBuffer;
	private List<ID> perIDWriteBuffer;
	private Map<Group, ID> addToGroupBuffer;
	// 需要从数据库删除的数据的缓存
	private List<Group> groupDeleteBuffer;
	private List<ID> syncIDDeleteBuffer;
	private List<ID> perIDDeleteBuffer;
	private List<ID> userIDDeleteBuffer;
	private Map<Group, ID> deleteFromGroupBuffer;

	private boolean forceToWrite;

	// 数据库用户名
	private String userName = "root";
	// 密码
	private String userPasswd = "81999";
	// 数据库名
	private String dbName = "test";
	// 联结字符串
	private String url = "jdbc:mysql://localhost/" + dbName + "?user="
			+ userName + "&password=" + userPasswd;

	private static DataCenterImp instance = null;

	/**
	 * 构造函数，进行初始化类成员变量，若表不存在则建表等操作
	 */
	private DataCenterImp() {
		userInfoWriteBuffer = new ArrayList<UserInfo>();
		permissionWriteBuffer = new ArrayList<Permission>();
		groupWriteBuffer = new ArrayList<Group>();
		syncIDWriteBuffer = new ArrayList<ID>();
		perIDWriteBuffer = new ArrayList<ID>();
		addToGroupBuffer = new HashMap<Group, ID>();
		groupDeleteBuffer = new ArrayList<Group>();
		syncIDDeleteBuffer = new ArrayList<ID>();
		perIDDeleteBuffer = new ArrayList<ID>();
		userIDDeleteBuffer = new ArrayList<ID>();
		deleteFromGroupBuffer = new HashMap<Group, ID>();

		// 建表
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection connection = (Connection) DriverManager
					.getConnection(url);
			Statement statement = (Statement) connection.createStatement();

			boolean userInfoTableExist = true;
			boolean groupTableExist = true;
			boolean groupMemTableExist = true;
			boolean permissionTableExist = true;

			// /////////////！！！！！！！！！！！！（跟new BaseUserInfo()的实现有关，可能需要修改）
			Iterator<String> fieldNameIter = (new BaseUserInfo()).getKeySet()
					.iterator();

			// 判断UserInfo信息表是否存在
			String sql = "SELECT * FROM UserInfo";
			try {
				statement.executeQuery(sql);
			} catch (SQLException e) {
				userInfoTableExist = false;
			}

			if (userInfoTableExist == false) {
				// 建立UserInfo信息表
				//TODO CRITICAL 当前的USERID已经变成了LONG
				//TODO CRITICAL 请用字段的getMaxLength来设定字符串长度
				sql = "CREATE TABLE UserInfo(UserID int not null";
				while (fieldNameIter.hasNext()) {
					sql += ("," + fieldNameIter.next() + " varchar(50)");
				}

				// /////////////！！！！！！！！！！！！（跟new CustomUserInfo()的实现有关，可能需要修改）
				fieldNameIter = (new CustomUserInfo()).getKeySet().iterator();
				while (fieldNameIter.hasNext()) {
					sql += ("," + fieldNameIter.next() + " varchar(50)");
				}
				sql += ",WhetherSync int not null DEFAULT 0,WhetherPer int not null DEFAULT 0)";
				statement.executeUpdate(sql);
			}

			// 判断Group信息表是否已存在
			sql = "SELECT * FROM GroupInfo";
			try {
				statement.executeQuery(sql);
			} catch (SQLException e) {
				groupTableExist = false;
			}

			// 建立Group信息表
			//TODO CRITICAL 当前的GROUPID已经变成了LONG
			
			if (groupTableExist == false) {
				sql = "CREATE TABLE GroupInfo(GroupID int not null";
				// /////////////！！！！！！！！！！！！（跟new Group()的实现有关，可能需要修改）
				fieldNameIter = (new Group()).getKeySet().iterator();
				while (fieldNameIter.hasNext()) {
					sql += ("," + fieldNameIter.next() + " varchar(50)");
				}
				sql += ")";
				statement.executeUpdate(sql);
			}

			// 判断Group成员表是否已存在
			sql = "SELECT * FROM GroupMember";
			try {
				statement.executeQuery(sql);
			} catch (SQLException e) {
				groupMemTableExist = false;
			}

			// 建立Group成员表
			//TODO CRITICAL 当前的ID已经变成了LONG
			if (groupMemTableExist == false) {
				sql = "CREATE TABLE GroupMember(GroupID int not null,UserID int not null)";
				statement.executeUpdate(sql);
			}

			// 判断Permission表是否存在
			sql = "SELECT * FROM Permission";
			try {
				statement.executeQuery(sql);
			} catch (SQLException e) {
				permissionTableExist = false;
			}
			// 建立Permission表
			//TODO CRITICAL 当前的ID已经变成了LONG
			if (permissionTableExist == false) {
				sql = "CREATE TABLE Permission(UserID int not null";
				fieldNameIter = (new Permission()).getKeySet().iterator();
				while (fieldNameIter.hasNext()) {
					sql += ("," + fieldNameIter.next() + " int not null");
				}
				sql += ")";
				statement.executeUpdate(sql);
			}
			connection.close();
		} catch (Exception ex) {
			System.out.println(ex);
			ex.printStackTrace();
			System.exit(0);
		}

	}

	/**
	 * 返回此类的唯一实例
	 * 
	 * @return
	 */
	public static synchronized DataCenterImp Instance() {
		if (instance == null) {
			instance = new DataCenterImp();
		}
		return instance;
	}

	@Override
	public ReturnType addPerRelationship(ID uid) {
		perIDWriteBuffer.add(uid);
		try {
			if (perIDWriteBuffer.size() > 0) {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				Connection connection = (Connection) DriverManager
						.getConnection(url);
				connection.setAutoCommit(false);
				String sql = "UPDATE UserInfo WhetherPer:=? where UserID=?";
				PreparedStatement pstatement = (PreparedStatement) connection
						.prepareStatement(sql);
				for (int i = 0; i < perIDWriteBuffer.size(); i++) {
					pstatement.setInt(1, 1);
					pstatement.setLong(2, perIDWriteBuffer.get(i).getValue());
					pstatement.addBatch();
				}
				pstatement.executeBatch();
				connection.commit();
				connection.close();
				pstatement.clearBatch();
				perIDWriteBuffer.clear();
			}
		} catch (Exception ex) {
			System.out.println(ex);
			ex.printStackTrace();
			System.exit(0);
		}
		return null;
	}

	@Override
	public ReturnType addSynRelationship(ID uid) {
		syncIDWriteBuffer.add(uid);
		try {
			if (syncIDWriteBuffer.size() > 0) {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				Connection connection = (Connection) DriverManager
						.getConnection(url);
				connection.setAutoCommit(false);
				String sql = "UPDATE UserInfo WhetherSync:=? where UserID=?";
				PreparedStatement pstatement = (PreparedStatement) connection
						.prepareStatement(sql);
				for (int i = 0; i < syncIDWriteBuffer.size(); i++) {
					pstatement.setInt(1, 1);
					pstatement.setLong(2, syncIDWriteBuffer.get(i).getValue());
					pstatement.addBatch();
				}
				pstatement.executeBatch();
				connection.commit();
				connection.close();
				pstatement.clearBatch();
				syncIDWriteBuffer.clear();
			}
		} catch (Exception ex) {
			System.out.println(ex);
			ex.printStackTrace();
			System.exit(0);
		}
		return null;
	}

	@Override
	public ReturnType addToGroup(Group g, ID uid) {
		addToGroupBuffer.put(g, uid);
		if (addToGroupBuffer.size() > 0) {
			try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				Connection connection = (Connection) DriverManager
						.getConnection(url);
				connection.setAutoCommit(false);
				String sql = "INSERT INTO GroupMember (GroupID,UserID) VALUES (?,?)";
				PreparedStatement pstatement = (PreparedStatement) connection
						.prepareStatement(sql);
				Iterator<Group> groupIter = addToGroupBuffer.keySet()
						.iterator();
				for (int i = 0; i < addToGroupBuffer.size(); i++) {
					Group temp = groupIter.next();
					pstatement.setLong(1, temp.getID().getValue());
					pstatement
							.setLong(2, addToGroupBuffer.get(temp).getValue());
					pstatement.addBatch();
				}
				pstatement.executeBatch();
				connection.commit();
				connection.close();
				pstatement.clearBatch();
				addToGroupBuffer.clear();
			} catch (Exception ex) {
				System.out.println(ex);
				ex.printStackTrace();
				System.exit(0);
			}
		}
		return null;
	}

	@Override
	public ReturnType exportFile(String fileName) {
		// TODO 上面的fileName需要改为路径名，一下所有需要路径的地方加上路径名
		try {
			CSVWriter writer;
			Iterator<String> iter;
			String toWrite[];
			// 写UserInfo
			writer = new CSVWriter(new FileWriter(fileName), '#');
			ArrayList<UserInfo> allUserInfo = (ArrayList<UserInfo>) getAllUserInfo(null);
			int countBase = 1;// 已经有一个userID需要写入
			int countCustom = 0;
			iter = new BaseUserInfo().getKeySet().iterator();
			ArrayList<String> tempBase = new ArrayList<String>();
			ArrayList<String> tempCustom = new ArrayList<String>();
			while (iter.hasNext()) {
				tempBase.add(iter.next());
				countBase++;
			}
			iter = new CustomUserInfo().getKeySet().iterator();
			while (iter.hasNext()) {
				tempCustom.add(iter.next());
				countCustom++;
			}
			toWrite = new String[countBase + countCustom];
			toWrite[0] = "UserID";
			for (int i = 1; i < countBase; i++) {
				toWrite[i] = tempBase.get(i - 1);
			}
			for (int i = 0; i < countCustom; i++) {
				toWrite[countBase + i] = tempCustom.get(i);
			}
			writer.writeNext(toWrite);
			for (int i = 0; i < allUserInfo.size(); i++) {
				toWrite[0] = ""
						+ allUserInfo.get(i).getBaseInfo().getID().getValue();
				for (int j = 1; j < countBase; j++) {
					toWrite[j] = allUserInfo.get(i).getBaseInfo().getInfoField(
							tempBase.get(j - 1)).getStringValue();
				}
				for (int j = 0; j < countCustom; j++) {
					toWrite[countBase + j] = allUserInfo.get(i).getCustomInfo()
							.getInfoField(tempCustom.get(j)).getStringValue();
				}
				writer.writeNext(toWrite);
			}
			writer.close();

			// 以下信息存于服务器，不需要导出

			// 写GroupInfo
			/*
			 * writer=new CSVWriter(new
			 * FileWriter(dirName+"\\GroupInfo.csv"),'#'); ArrayList<Group>
			 * allGroup=(ArrayList<Group>)getAllGroup(); iter=new
			 * Group().getKeySet().iterator(); int count=1; ArrayList<String>
			 * tempGroup=new ArrayList<String>(); while(iter.hasNext()){
			 * tempGroup.add(iter.next()); count++; } toWrite=new String[count];
			 * toWrite[0]="GroupID"; for(int i=1;i<count;i++){
			 * toWrite[i]=tempGroup.get(i-1); } writer.writeNext(toWrite);
			 * for(int i=0;i<allGroup.size();i++){
			 * toWrite[0]=""+allGroup.get(i).getID().getValue(); for(int
			 * j=1;j<count;j++){
			 * toWrite[j]=allGroup.get(i).getInfoField(tempGroup
			 * .get(j-1)).getStringValue(); } writer.writeNext(toWrite); }
			 * writer.close();
			 * 
			 * //写GroupMember writer=new CSVWriter(new
			 * FileWriter(dirName+"\\GroupMember.csv"),'#'); toWrite=new
			 * String[2]; toWrite[0]="GroupID"; toWrite[1]="UserID";
			 * writer.writeNext(toWrite); for(int i=0;i<allGroup.size();i++){
			 * for(int j=0;j<allGroup.get(i).getUsersID().size();i++){
			 * toWrite[0]=""+allGroup.get(i).getID().getValue();
			 * toWrite[1]=""+allGroup.get(i).getUsersID().get(j).getValue();
			 * writer.writeNext(toWrite); } } writer.close();
			 * 
			 * //写Permission writer=new CSVWriter(new
			 * FileWriter(dirName+"\\Permission.csv"),'#');
			 * HashMap<ID,Permission>
			 * allPermission=(HashMap<ID,Permission>)getAllPermission();
			 * iter=new Permission().getKeySet().iterator(); ArrayList<String>
			 * tempPer=new ArrayList<String>(); count=1; while(iter.hasNext()){
			 * tempPer.add(iter.next()); count++; } toWrite=new String[count];
			 * toWrite[0]="UserID"; for(int i=1;i<count;i++){
			 * toWrite[i]=tempPer.get(i-1); } writer.writeNext(toWrite);
			 * Iterator<ID> iterID=allPermission.keySet().iterator();
			 * while(iterID.hasNext()){ ID id=iterID.next();
			 * toWrite[0]=""+id.getValue(); for(int i=1;i<count;i++){
			 * if(allPermission.get(id).getField(tempPer.get(i-1))==true)
			 * toWrite[i]=""+1; else toWrite[i]=""+0; }
			 * writer.writeNext(toWrite); } writer.close();
			 */

		} catch (Exception ex) {
			System.out.println(ex);
			ex.printStackTrace();
			System.exit(0);
		}
		return null;
	}

	@Override
	public List<UserInfo> getAllUserInfo(LocalSynSource source) {
		List<UserInfo> result = new ArrayList<UserInfo>();
		List<BaseUserInfo> baseResult = new ArrayList<BaseUserInfo>();
		List<CustomUserInfo> customResult = new ArrayList<CustomUserInfo>();
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection connection = (Connection) DriverManager
					.getConnection(url);
			connection.setAutoCommit(false);
			Statement statement = (Statement) connection.createStatement();
			String sql = "SELECT UserID FROM UserInfo";
			ResultSet allUserID = statement.executeQuery(sql);
			BaseUserInfo baseUserInfo;
			CustomUserInfo customUserInfo;
			while (allUserID.next()) {
				baseUserInfo = new BaseUserInfo();
				baseUserInfo.setID(new ID(allUserID.getInt(1)));
				baseResult.add(baseUserInfo);
				customUserInfo = new CustomUserInfo();
				customResult.add(customUserInfo);
			}
			allUserID.close();
			for (int i = 0; i < baseResult.size(); i++) {
				Iterator<String> iter = new BaseUserInfo().getKeySet()
						.iterator();
				while (iter.hasNext()) {
					String temp = iter.next();
					sql = "SELECT " + temp + " FROM UserInfo WHERE UserID=?";
					PreparedStatement pstatement = (PreparedStatement) connection
							.prepareStatement(sql);
					pstatement.setLong(1, baseResult.get(i).getID().getValue());
					ResultSet rs = pstatement.executeQuery();

					if (rs.next())
						baseResult.get(i).setInfoField(
								temp,
								InfoFieldFactory.getFactory().makeInfoField(
										temp, rs.getString(1)));
					rs.close();
				}
				iter = new CustomUserInfo().getKeySet().iterator();
				while (iter.hasNext()) {
					String temp = iter.next();
					sql = "SELECT " + temp + " FROM UserInfo WHERE UserID=?";
					PreparedStatement pstatement = (PreparedStatement) connection
							.prepareStatement(sql);
					pstatement.setLong(1, baseResult.get(i).getID().getValue());
					ResultSet rs = pstatement.executeQuery();
					if (rs.next())
						customResult.get(i).setInfoField(
								temp,
								InfoFieldFactory.getFactory().makeInfoField(
										temp, rs.getString(1)));
					rs.close();
				}
				UserInfo userInfo = new UserInfo();
				userInfo.setBaseInfo(baseResult.get(i));
				userInfo.setCustomInfo(customResult.get(i));
				result.add(userInfo);
			}
			connection.close();

		} catch (Exception ex) {
			System.out.println(ex);
			ex.printStackTrace();
			System.exit(0);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ReturnType importFile(String fileName) {
		// TODO Auto-generated method stub
		try {
			UserInfo userInfo = new UserInfo();
			BaseUserInfo baseUserInfo = new BaseUserInfo();
			CustomUserInfo customUserInfo = new CustomUserInfo();
			CSVReader reader = new CSVReader(new FileReader(fileName), '#');
			int countBase = 1;// 第一个是UserID
			int countCustom = 0;
			Iterator<String> iter = baseUserInfo.getKeySet().iterator();
			while (iter.hasNext()) {
				iter.next();
				countBase++;
			}
			iter = customUserInfo.getKeySet().iterator();
			while (iter.hasNext()) {
				iter.next();
				countCustom++;
			}
			ArrayList<String[]> field = (ArrayList<String[]>) reader.readAll();
			String fieldName[] = field.get(0);
			for (int i = 1; i < field.size(); i++) {
				baseUserInfo.setID(new ID(Integer.parseInt(field.get(i)[0])));
				for (int j = 1; j < countBase; j++) {
					baseUserInfo.setInfoField(fieldName[j], InfoFieldFactory
							.getFactory().makeInfoField(fieldName[j],
									field.get(i)[j]));
				}
				for (int j = countBase; j < countBase + countCustom; j++) {
					customUserInfo.setInfoField(fieldName[j], InfoFieldFactory
							.getFactory().makeInfoField(fieldName[j],
									field.get(i)[j]));
				}
				userInfo.setBaseInfo(baseUserInfo);
				userInfo.setCustomInfo(customUserInfo);
				this.setUserInfo(userInfo);
			}

		} catch (Exception ex) {
			System.out.println(ex);
			ex.printStackTrace();
			System.exit(0);
		}
		return null;
	}

	@Override
	public ReturnType removeFromGroup(Group g, ID uid) {
		deleteFromGroupBuffer.put(g, uid);
		if (deleteFromGroupBuffer.size() > 0) {
			try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				Connection connection = (Connection) DriverManager
						.getConnection(url);
				connection.setAutoCommit(false);
				String sql = "DELETE FROM GroupMember WHERE GroupID=? AND UserID=?";
				PreparedStatement pstatement = (PreparedStatement) connection
						.prepareStatement(sql);
				Iterator<Group> groupIter = deleteFromGroupBuffer.keySet()
						.iterator();
				for (int i = 0; i < deleteFromGroupBuffer.size(); i++) {
					Group temp = groupIter.next();
					pstatement.setLong(1, temp.getID().getValue());
					pstatement.setLong(2, deleteFromGroupBuffer.get(temp)
							.getValue());
					pstatement.addBatch();
				}
				pstatement.executeBatch();
				connection.commit();
				connection.close();
				pstatement.clearBatch();
				deleteFromGroupBuffer.clear();
			} catch (Exception ex) {
				System.out.println(ex);
				ex.printStackTrace();
				System.exit(0);
			}
		}
		return null;
	}

	@Override
	public ReturnType removeGroup(Group g) {
		groupDeleteBuffer.add(g);
		try {
			if (groupDeleteBuffer.size() > 0) {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				Connection connection = (Connection) DriverManager
						.getConnection(url);
				connection.setAutoCommit(false);
				String sql1 = "DELETE FROM GroupInfo WHERE GroupID=?";
				String sql2 = "DELETE FROM GroupMember WHERE GroupID=?";
				PreparedStatement pstatement1 = (PreparedStatement) connection
						.prepareStatement(sql1);
				PreparedStatement pstatement2 = (PreparedStatement) connection
						.prepareStatement(sql2);
				for (int i = 0; i < groupDeleteBuffer.size(); i++) {
					pstatement1.setLong(1, groupDeleteBuffer.get(i).getID()
							.getValue());
					pstatement2.setLong(1, groupDeleteBuffer.get(i).getID()
							.getValue());
					pstatement1.addBatch();
					pstatement2.addBatch();
				}
				pstatement1.executeBatch();
				pstatement2.executeBatch();
				connection.commit();
				connection.close();
				pstatement1.clearBatch();
				pstatement2.clearBatch();
				groupDeleteBuffer.clear();
			}
		} catch (Exception ex) {
			System.out.println(ex);
			ex.printStackTrace();
			System.exit(0);
		}
		return null;
	}

	@Override
	public ReturnType removePerRelationship(ID uid) {
		perIDDeleteBuffer.add(uid);
		try {
			if (perIDDeleteBuffer.size() > 0) {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				Connection connection = (Connection) DriverManager
						.getConnection(url);
				connection.setAutoCommit(false);
				String sql = "UPDATE UserInfo WhetherPer:=? where UserID=?";
				PreparedStatement pstatement = (PreparedStatement) connection
						.prepareStatement(sql);
				for (int i = 0; i < perIDDeleteBuffer.size(); i++) {
					pstatement.setInt(1, 0);
					pstatement.setLong(2, perIDDeleteBuffer.get(i).getValue());
					pstatement.addBatch();
				}
				pstatement.executeBatch();
				connection.commit();
				connection.close();
				pstatement.clearBatch();
				perIDDeleteBuffer.clear();
			}
		} catch (Exception ex) {
			System.out.println(ex);
			ex.printStackTrace();
			System.exit(0);
		}
		return null;
	}

	@Override
	public ReturnType removeSynRelationship(ID uid) {
		syncIDDeleteBuffer.add(uid);
		try {
			if (syncIDDeleteBuffer.size() > 0) {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				Connection connection = (Connection) DriverManager
						.getConnection(url);
				connection.setAutoCommit(false);
				String sql = "UPDATE UserInfo WhetherSync:=? where UserID=?";
				PreparedStatement pstatement = (PreparedStatement) connection
						.prepareStatement(sql);
				for (int i = 0; i < syncIDDeleteBuffer.size(); i++) {
					pstatement.setInt(1, 0);
					pstatement.setLong(2, syncIDDeleteBuffer.get(i).getValue());
					pstatement.addBatch();
				}
				pstatement.executeBatch();
				connection.commit();
				connection.close();
				pstatement.clearBatch();
				syncIDDeleteBuffer.clear();
			}
		} catch (Exception ex) {
			System.out.println(ex);
			ex.printStackTrace();
			System.exit(0);
		}
		return null;
	}

	//TODO CRITICAL 注意群组中的成员也会更新
	@Override
	public ReturnType setGroup(Group g) {
		groupWriteBuffer.add(g);
		try {
			if (groupWriteBuffer.size() > 0) {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				Connection connection = (Connection) DriverManager
						.getConnection(url);
				connection.setAutoCommit(false);

				String sql1 = "UPDATE GroupInfo set ";
				String sql2 = "INSERT INTO GroupInfo (GroupID,";
				Iterator<String> fieldNameIter = g.getKeySet().iterator();
				String temp = fieldNameIter.next();
				sql1 += (temp + ":=?");
				sql2 += temp;
				int count = 0;
				while (fieldNameIter.hasNext()) {
					temp = fieldNameIter.next();
					sql1 += ("," + temp + ":=?");
					sql2 += ("," + temp);
					count++;
				}
				sql1 += " WHERE GroupID=?";
				sql2 += ") VALUES (?,?";// 第一个是GroupID，从第二个开始是字段内容
				for (int i = 0; i < count; i++)
					sql2 += ",?";
				sql2 += ")";

				PreparedStatement pstatement1 = (PreparedStatement) connection
						.prepareStatement(sql1);
				PreparedStatement pstatement2 = (PreparedStatement) connection
						.prepareStatement(sql2);
				for (int i = 0; i < groupWriteBuffer.size(); i++) {
					boolean groupExist = true;
					try {
						String tempSQL = "SELECT count(*) FROM GroupInfo WHERE GroupID="
								+ groupWriteBuffer.get(i).getID().getValue();
						Statement statement = (Statement) connection
								.createStatement();
						ResultSet rs = statement.executeQuery(tempSQL);
						if (rs.next()) {
							if (rs.getInt(1) == 0)
								groupExist = false;
						}
					} catch (SQLException e) {
						groupExist = false;
					}
					if (groupExist) {
						int keyNum = 1;
						fieldNameIter = groupWriteBuffer.get(i).getKeySet()
								.iterator();
						while (fieldNameIter.hasNext()) {
							pstatement1.setString(keyNum, groupWriteBuffer.get(
									i).getInfoField(fieldNameIter.next())
									.getStringValue());
							keyNum++;
						}
						pstatement1.setLong(keyNum, groupWriteBuffer.get(i)
								.getID().getValue());
						pstatement1.addBatch();
					} else {
						fieldNameIter = groupWriteBuffer.get(i).getKeySet()
								.iterator();
						pstatement1.setLong(1, groupWriteBuffer.get(i).getID()
								.getValue());
						int keyNum = 2;
						while (fieldNameIter.hasNext()) {
							pstatement2.setString(keyNum, groupWriteBuffer.get(
									i).getInfoField(fieldNameIter.next())
									.getStringValue());
							keyNum++;
						}
						pstatement2.addBatch();
					}
				}
				pstatement1.executeBatch();
				pstatement2.executeBatch();
				connection.commit();
				connection.close();
				pstatement1.clearBatch();
				pstatement2.clearBatch();
				groupWriteBuffer.clear();
			}
		} catch (Exception ex) {
			System.out.println(ex);
			ex.printStackTrace();
			System.exit(0);
		}
		return null;
	}

	@Override
	public ReturnType setPermission(ID uid, Permission p) {
		permissionWriteBuffer.add(p);
		try {
			if (permissionWriteBuffer.size() > 0) {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				Connection connection = (Connection) DriverManager
						.getConnection(url);
				connection.setAutoCommit(false);

				String sql1 = "UPDATAE Permission set";
				String sql2 = "INSERT INTO Permission (UserID,";
				Iterator<String> fieldNameIter = p.getKeySet().iterator();
				int count = 0;
				while (fieldNameIter.hasNext()) {
					String temp = fieldNameIter.next();
					sql1 += ("," + temp + ":=?");
					sql2 += ("," + temp);
					count++;
				}
				sql1 += " WHERE UserID=?";
				sql2 += ") VALUES (?";// 第一个?代表UserID，从第二个开始是字段内容
				for (int i = 0; i < count; i++)
					sql2 += ",?";
				sql2 += ")";

				PreparedStatement pstatement1 = (PreparedStatement) connection
						.prepareStatement(sql1);
				PreparedStatement pstatement2 = (PreparedStatement) connection
						.prepareStatement(sql2);
				for (int i = 0; i < permissionWriteBuffer.size(); i++) {
					boolean userExist = true;
					try {
						String tempSQL = "SELECT count(*) FROM Permission WHERE UserID="
								+ uid.getValue();
						Statement statement = (Statement) connection
								.createStatement();
						ResultSet rs = statement.executeQuery(tempSQL);
						if (rs.next()) {
							if (rs.getInt(1) == 0)
								userExist = false;
						}
					} catch (SQLException e) {
						userExist = false;
					}
					if (userExist) {
						int keyNum = 1;
						fieldNameIter = permissionWriteBuffer.get(i)
								.getKeySet().iterator();
						while (fieldNameIter.hasNext()) {
							if (permissionWriteBuffer.get(i).getField(
									fieldNameIter.next()) == true)
								pstatement1.setInt(keyNum, 1);
							else
								pstatement1.setInt(keyNum, 0);
							keyNum++;
						}
						pstatement1.setLong(keyNum, uid.getValue());
						pstatement1.addBatch();
					} else {
						fieldNameIter = permissionWriteBuffer.get(i)
								.getKeySet().iterator();
						pstatement2.setLong(1, uid.getValue());
						int keyNum = 2;
						while (fieldNameIter.hasNext()) {
							if (permissionWriteBuffer.get(i).getField(
									fieldNameIter.next()) == true)
								pstatement2.setInt(keyNum, 1);
							else
								pstatement2.setInt(keyNum, 0);
							keyNum++;
						}
						pstatement2.addBatch();
					}
				}
				pstatement1.executeBatch();
				pstatement2.executeBatch();
				connection.commit();
				connection.close();
				pstatement1.clearBatch();
				pstatement2.clearBatch();
				userInfoWriteBuffer.clear();
			}
		} catch (Exception ex) {
			System.out.println(ex);
			ex.printStackTrace();
			System.exit(0);
		}
		return null;
	}

	@Override
	public ReturnType setUserInfo(UserInfo b) {
		// TODO 传入的空值即为不需要更改的
		userInfoWriteBuffer.add(b);
		try {
			// TODO userInfoWriteBuffer.size()的限制
			if (userInfoWriteBuffer.size() > 0) {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				Connection connection = (Connection) DriverManager
						.getConnection(url);
				connection.setAutoCommit(false);

				String sql1 = "UPDATE UserInfo set ";
				String sqlCustomNull = "UPDATE UserInfo set ";
				String sql2 = "INSERT INTO UserInfo (UserID,";
				Iterator<String> fieldNameIter = UserInfo.getNewLocalUser()
						.getBaseInfo().getKeySet().iterator();
				String temp = fieldNameIter.next();
				sql1 += (temp + ":=?");
				sqlCustomNull += (temp + ":=?");// 处理当传入的CustomUserInfo为空的时候用
				sql2 += temp;
				int count = 0;
				while (fieldNameIter.hasNext()) {
					temp = fieldNameIter.next();
					sql1 += ("," + temp + ":=?");
					sqlCustomNull += ("," + temp + ":=?");
					sql2 += ("," + temp);
					count++;
				}
				fieldNameIter = UserInfo.getNewLocalUser().getCustomInfo()
						.getKeySet().iterator();
				while (fieldNameIter.hasNext()) {
					temp = fieldNameIter.next();
					sql1 += ("," + temp + ":=?");
					sql2 += ("," + temp);
					count++;
				}
				sql1 += " WHERE UserID=?";
				sqlCustomNull += " WHERE UserID=?";
				sql2 += ") VALUES (?,?";// 第一个?代表UserID，从第二个开始是字段内容
				for (int i = 0; i < count; i++)
					sql2 += ",?";
				sql2 += ")";

				PreparedStatement pstatement1 = (PreparedStatement) connection
						.prepareStatement(sql1);
				// 处理当传入的CustomUserInfo为空的时候用
				PreparedStatement pstatementCustomNull = (PreparedStatement) connection
						.prepareStatement(sqlCustomNull);
				PreparedStatement pstatement2 = (PreparedStatement) connection
						.prepareStatement(sql2);
				for (int i = 0; i < userInfoWriteBuffer.size(); i++) {
					boolean userExist = true;
					try {
						String tempSQL = "SELECT count(*) FROM UserInfo WHERE UserID="
								+ userInfoWriteBuffer.get(i).getBaseInfo()
										.getID().getValue();
						Statement statement = (Statement) connection
								.createStatement();
						ResultSet rs = statement.executeQuery(tempSQL);
						if (rs.next()) {
							if (rs.getInt(1) == 0)
								userExist = false;
						}
					} catch (SQLException e) {
						userExist = false;
					}
					if (userExist) {
						int keyNum = 1;
						fieldNameIter = userInfoWriteBuffer.get(i)
								.getBaseInfo().getKeySet().iterator();
						while (fieldNameIter.hasNext()) {
							String fieldName = fieldNameIter.next();
							pstatement1.setString(keyNum, userInfoWriteBuffer
									.get(i).getBaseInfo().getInfoField(
											fieldName).getStringValue());
							pstatementCustomNull.setString(keyNum,
									userInfoWriteBuffer.get(i).getBaseInfo()
											.getInfoField(fieldName)
											.getStringValue());
							keyNum++;
						}
						// 如果传入的CustomUserInfo不为空
						if (userInfoWriteBuffer.get(i).getCustomInfo() != null) {
							fieldNameIter = userInfoWriteBuffer.get(i)
									.getCustomInfo().getKeySet().iterator();
							while (fieldNameIter.hasNext()) {
								pstatement1.setString(keyNum,
										userInfoWriteBuffer.get(i)
												.getCustomInfo().getInfoField(
														fieldNameIter.next())
												.getStringValue());
								keyNum++;
							}
							pstatement1.setLong(keyNum, userInfoWriteBuffer
									.get(i).getBaseInfo().getID().getValue());
							pstatement1.addBatch();
						} else {// 如果传入的CustomUserInfo为空
							pstatementCustomNull.setLong(keyNum,
									userInfoWriteBuffer.get(i).getBaseInfo()
											.getID().getValue());
							pstatementCustomNull.addBatch();
						}

					} else {
						fieldNameIter = userInfoWriteBuffer.get(i)
								.getBaseInfo().getKeySet().iterator();
						pstatement2.setLong(1, userInfoWriteBuffer.get(i)
								.getBaseInfo().getID().getValue());
						int keyNum = 2;
						while (fieldNameIter.hasNext()) {
							pstatement2.setString(keyNum, userInfoWriteBuffer
									.get(i).getBaseInfo().getInfoField(
											fieldNameIter.next())
									.getStringValue());
							keyNum++;
						}
						fieldNameIter = userInfoWriteBuffer.get(i)
								.getCustomInfo().getKeySet().iterator();
						while (fieldNameIter.hasNext()) {
							pstatement2.setString(keyNum, userInfoWriteBuffer
									.get(i).getCustomInfo().getInfoField(
											fieldNameIter.next())
									.getStringValue());
							keyNum++;
						}
						pstatement2.addBatch();
					}
				}
				pstatement1.executeBatch();
				pstatementCustomNull.executeBatch();
				pstatement2.executeBatch();
				connection.commit();
				connection.close();
				pstatement1.clearBatch();
				pstatementCustomNull.clearBatch();
				pstatement2.clearBatch();
				userInfoWriteBuffer.clear();
			}
		} catch (Exception ex) {
			System.out.println(ex);
			ex.printStackTrace();
			System.exit(0);
		}
		return null;
	}

	public ReturnType removeUserInfo(ID uid) {
		userIDDeleteBuffer.add(uid);
		if (userIDDeleteBuffer.size() > 0) {
			try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				Connection connection = (Connection) DriverManager
						.getConnection(url);
				connection.setAutoCommit(false);
				String sql = "DELETE FROM UserInfo WHERE UserID=?";
				PreparedStatement pstatement = (PreparedStatement) connection
						.prepareStatement(sql);
				for (int i = 0; i < userIDDeleteBuffer.size(); i++) {
					pstatement.setLong(1, uid.getValue());
					pstatement.addBatch();
				}
				pstatement.executeBatch();
				connection.commit();
				connection.close();
				pstatement.clearBatch();
				userIDDeleteBuffer.clear();
			} catch (Exception ex) {
				System.out.println(ex);
				ex.printStackTrace();
				System.exit(0);
			}
		}
		return null;
	}

	private List<Group> getAllGroup() {
		// TODO
		List<Group> result = new ArrayList<Group>();
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection connection = (Connection) DriverManager
					.getConnection(url);
			connection.setAutoCommit(false);
			Statement statement = (Statement) connection.createStatement();
			String sql = "SELECT GroupID FROM GroupInfo";
			ResultSet allGroupID = statement.executeQuery(sql);
			// 一个表项代表一个group字段的所有用户的值
			Map<String, ResultSet> groupFieldList = new HashMap<String, ResultSet>();
			String sql1 = "SELECT ? FROM GroupInfo";
			String sql2 = "SELECT UserID FROM GroupMember WHERE GroupID=?";
			PreparedStatement pstatement1 = (PreparedStatement) connection
					.prepareStatement(sql1);
			PreparedStatement pstatement2 = (PreparedStatement) connection
					.prepareStatement(sql2);
			Iterator<String> iter = new Group().getKeySet().iterator();
			while (iter.hasNext()) {
				String temp = iter.next();
				pstatement1.setString(1, temp);
				groupFieldList.put(temp, pstatement1.executeQuery());
			}
			while (allGroupID.next()) {
				int groupID = allGroupID.getInt(1);
				Group group = new Group();
				group.setID(new ID(groupID));
				pstatement2.setInt(1, groupID);
				ResultSet idRs = pstatement2.executeQuery();
				while (idRs.next()) {
					group.addToGroup(new ID(idRs.getInt(1)));
				}
				Iterator<String> listIter = groupFieldList.keySet().iterator();
				while (listIter.hasNext()) {
					// 设置group的各个字段
					String temp = listIter.next();
					groupFieldList.get(temp).next();// 此处可能有问题
					group.setInfoField(temp, InfoFieldFactory.getFactory()
							.makeInfoField(temp,
									groupFieldList.get(temp).getString(1)));
				}
				result.add(group);
			}

		} catch (Exception ex) {
			System.out.println(ex);
			ex.printStackTrace();
			System.exit(0);
		}
		return result;
	}

	private Map<ID, Permission> getAllPermission() {
		Map<ID, Permission> result = new HashMap<ID, Permission>();
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection connection = (Connection) DriverManager
					.getConnection(url);
			connection.setAutoCommit(false);
			Statement statement = (Statement) connection.createStatement();
			String sql = "SELECT UserID FROM Permission";
			ResultSet allUserID = statement.executeQuery(sql);
			Iterator<String> iter = new Permission().getKeySet().iterator();
			String sql1 = "SELECT ? FROM Permission WHERE UserID=?";
			PreparedStatement pstatement = (PreparedStatement) connection
					.prepareStatement(sql1);
			while (allUserID.next()) {
				ID id = new ID(allUserID.getInt(1));
				Permission per = new Permission();
				pstatement.setLong(2, id.getValue());
				while (iter.hasNext()) {
					String temp = iter.next();
					pstatement.setString(1, temp);
					ResultSet rs = pstatement.executeQuery();
					rs.next();
					if (rs.getInt(1) == 1)
						per.setField(temp, true);
					else
						per.setField(temp, false);
				}
				result.put(id, per);
			}

		} catch (Exception ex) {
			System.out.println(ex);
			ex.printStackTrace();
			System.exit(0);
		}
		return result;
	}

	@Override
	public ReturnType setVisibility(ID uid, int visibility) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ID> getAllPerContactsID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ID> getAllSynContactsID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Group> getAllGroups() {
		// TODO Auto-generated method stub
		return null;
	}

}
