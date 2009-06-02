package serverLogicCenter.sdataCenter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

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
		String sql = "DESCRIBE GroupInfo";
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

			sql += ", INDEX(admin";
			for (String name : emptyGroup.getKeySet())
				if (emptyGroup.getInfoField(name) instanceof IndexedInfoField) {
					sql += ", ";
					sql += name;
				}
			sql += ")";

			sql += ", PRIMARY KEY(gid)) CHARACTER SET gbk COLLATE gbk_bin;";
			statement.executeUpdate(sql);
		}

	}

	public void setGroup(Group g) throws SQLException {
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

	public Group getGroupInfo(ID gid) throws SQLException {
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

	public void removeGroup(Group g) throws SQLException{
		String psql = "DELETE FROM GroupInfo WHERE gid=?";
		PreparedStatement pStatement = connection.prepareStatement(psql);
		pStatement.setLong(1, g.getID().getValue());
		pStatement.execute();
	}

	public List<Group> searchGroup(Group info) throws SQLException{
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
}
