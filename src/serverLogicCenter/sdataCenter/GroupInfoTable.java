package serverLogicCenter.sdataCenter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import java.sql.Connection;
import java.sql.Statement;

import entity.BaseUserInfo;
import entity.Group;
import entity.ID;
import entity.infoField.IdenticalInfoField;
import entity.infoField.IndexedInfoField;
import entity.infoField.InfoField;
import entity.infoField.InfoFieldFactory;

public class GroupInfoTable {
	private Connection connection;

	public GroupInfoTable(Connection connection) throws SQLException {
		this.connection = connection;

		// 如果没有表，则建表
		Statement statement = (Statement) this.connection.createStatement();

		boolean tableExist = true;

		// !!!如果BaseUserInfo有改动，那么表格需要重建
		Group emptyGroup = new Group();
		Iterator<String> fieldNameIter = emptyGroup.getKeySet().iterator();

		// 判断GroupInfo信息表是否存在
		String sql = "SELECT COUNT(*) FROM GroupInfo";
		try {
			statement.executeQuery(sql);
		} catch (SQLException e) {
			tableExist = false;
		}

		if (tableExist == false) {
			// 建立GroupInfo信息表
			sql = "CREATE TABLE GroupInfo(gid BIGINT NOT NULL, admin BIGINT NOT NULL";
			while (fieldNameIter.hasNext()) {
				String name = fieldNameIter.next();
				sql += ("," + name + " varchar("
						+ emptyGroup.getInfoField(name).getMaxLength() + ") NOT NULL");
			}

			String uniquePart = ", UNIQUE(";
			for (String name : emptyGroup.getKeySet())
				if (emptyGroup.getInfoField(name) instanceof IdenticalInfoField) {
					if (uniquePart.charAt(uniquePart.length() - 1) != '(')
						uniquePart += ", ";
					uniquePart += name;
				}
			uniquePart += ")";
			if (!uniquePart.equals(", UNIQUE()"))
				sql += uniquePart;

			if (connection instanceof com.mysql.jdbc.Connection){//只有mysql支持的语法
				sql += ", INDEX(admin";
				for (String name : emptyGroup.getKeySet())
					if (emptyGroup.getInfoField(name) instanceof IndexedInfoField) {
						sql += ", ";
						sql += name;
					}
				sql += ")";
			}

			sql += ", PRIMARY KEY(gid))";
			if (connection instanceof com.mysql.jdbc.Connection)//只有mysql支持的语法
				sql += " CHARACTER SET gbk COLLATE gbk_bin TYPE InnoDB;";
			statement.executeUpdate(sql);
		}

	}

	synchronized public void setGroup(Group g) throws SQLException {
		String psql = "REPLACE INTO GroupInfo VALUES(?,?";

		// 为了保持KeySet的一致性，所以还是用emptyGroup来获取KeySet
		Group emptyGroup = new Group();
		Iterator<String> fieldNameIter = emptyGroup.getKeySet().iterator();
		while (fieldNameIter.hasNext()) {
			psql += ",?";
			fieldNameIter.next();
		}
		psql += ")";

		fieldNameIter = emptyGroup.getKeySet().iterator();

		PreparedStatement pStatement = connection.prepareStatement(psql);
		pStatement.setLong(1, g.getID().getValue());
		pStatement.setLong(2, g.getAdminUserID().getValue());
		int cnt = 2;
		while (fieldNameIter.hasNext())
			pStatement.setString(++cnt, g.getInfoField(fieldNameIter.next())
					.getStringValue());
		pStatement.execute();
	}

	synchronized public Group getGroupInfo(ID gid) throws SQLException {
		Group res = new Group();
		String psql = "SELECT * FROM GroupInfo WHERE gid=?";
		PreparedStatement pStatement = connection.prepareStatement(psql);
		pStatement.setLong(1, gid.getValue());
		ResultSet rows = pStatement.executeQuery();
		if (!rows.next())
			return res;// 组不存在
		else {
			// 为了保持KeySet的一致性，所以还是用emptyGroup来获取KeySet
			Group emptyGroup = new Group();
			Iterator<String> fieldNameIter = emptyGroup.getKeySet().iterator();
			while (fieldNameIter.hasNext()) {
				String name = fieldNameIter.next();
				res.setInfoField(name, InfoFieldFactory.getFactory()
						.makeInfoField(name, rows.getString(name)));
			}
			res.setID(new ID(rows.getLong("gid")));
			res.setAdminID(new ID(rows.getLong("admin")));
		}
		return res;
	}

	synchronized public void removeGroup(Group g) throws SQLException{
		String psql = "DELETE FROM GroupInfo WHERE gid=?";
		PreparedStatement pStatement = connection.prepareStatement(psql);
		pStatement.setLong(1, g.getID().getValue());
		pStatement.execute();
	}

	synchronized public List<Group> searchGroup(Group info) throws SQLException{
		String psql = "SELECT * FROM GroupInfo WHERE ";
		for(String name: info.getKeySet()){
			InfoField field = info.getInfoField(name);
			if (field.isEmpty())
				continue;
			if (psql.charAt(psql.length()-1) != ' ')
				psql += " AND ";
			psql += name+" LIKE ?";
		}
		PreparedStatement pStatement = connection.prepareStatement(psql);
		int cnt = 0;
		for(String name: info.getKeySet()){
			InfoField field = info.getInfoField(name);
			if (field.isEmpty())
				continue;
			pStatement.setString(++cnt, "%"+field.getStringValue()+"%");
		}
		
		ResultSet rows = pStatement.executeQuery();
		List<Group> res = new ArrayList<Group>();
		while (rows.next()){
			Group g = new Group();
			g.setID(new ID(rows.getLong("gid")));
			g.setAdminID(new ID(rows.getLong("admin")));
			// 为了保持KeySet的一致性，所以还是用emptyGroup来获取KeySet
			Group emptyGroup = new Group();
			Iterator<String> fieldNameIter = emptyGroup.getKeySet().iterator();
			while (fieldNameIter.hasNext()) {
				String name = fieldNameIter.next();
				g.setInfoField(name, InfoFieldFactory.getFactory()
						.makeInfoField(name, rows.getString(name)));
			}
			res.add(g);
		}
		return res;
	}

	public List<ID> getAllGroupID() throws SQLException{
		String psql = "SELECT gid FROM GroupInfo";
		PreparedStatement pStatement = connection.prepareStatement(psql);
		ResultSet rows = pStatement.executeQuery();
		
		List<ID> res = new ArrayList<ID>();
		while (rows.next())
			res.add(new ID(rows.getLong(1)));
		return res;
	}
}
