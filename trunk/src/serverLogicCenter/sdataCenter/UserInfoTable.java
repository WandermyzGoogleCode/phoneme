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

public class UserInfoTable {
	private Connection connection;

	UserInfoTable(Connection connection) throws SQLException {
		this.connection = connection;

		// 如果没有表，则建表
		Statement statement = (Statement) this.connection.createStatement();

		boolean userInfoTableExist = true;

		// !!!如果BaseUserInfo有改动，那么表格需要重建
		BaseUserInfo emptyInfo = new BaseUserInfo();
		Iterator<String> fieldNameIter = emptyInfo.getKeySet().iterator();

		// 判断UserInfo信息表是否存在
		String sql = "DESCRIBE UserInfo";
		try {
			statement.executeQuery(sql);
		} catch (SQLException e) {
			userInfoTableExist = false;
		}

		if (userInfoTableExist == false) {
			// 建立UserInfo信息表
			sql = "CREATE TABLE UserInfo(uid bigint not null";
			while (fieldNameIter.hasNext()) {
				String name = fieldNameIter.next();
				sql += ("," + name + " varchar("
						+ emptyInfo.getInfoField(name).getMaxLength() + ") NOT NULL");
			}

			sql += ", UNIQUE(";
			for (String name : emptyInfo.getKeySet())
				if (emptyInfo.getInfoField(name) instanceof IdenticalInfoField) {
					if (sql.charAt(sql.length() - 1) != '(')
						sql += ", ";
					sql += name;
				}
			sql += ")";

			sql += ", INDEX(";
			for (String name : emptyInfo.getKeySet())
				if (emptyInfo.getInfoField(name) instanceof IndexedInfoField) {
					if (sql.charAt(sql.length() - 1) != '(')
						sql += ", ";
					sql += name;
				}
			sql += ")";

			sql += ", PRIMARY KEY(uid)) CHARACTER SET gbk COLLATE gbk_bin TYPE InnoDB;";
			statement.executeUpdate(sql);
		}
	}

	public List<BaseUserInfo> getUsersInfo(List<ID> idList) throws SQLException {
		if (idList.isEmpty())
			return new ArrayList<BaseUserInfo>();
		String psql = "SELECT * FROM UserInfo WHERE ";
		for (ID id : idList) {
			if (psql.charAt(psql.length() - 1) != ' ')
				psql += " OR ";
			psql += "uid=?";
		}
		PreparedStatement pStatement = connection.prepareStatement(psql);
		for(int i=0; i<idList.size(); i++)
			pStatement.setLong(i+1, idList.get(i).getValue());

		ResultSet rows = pStatement.executeQuery();
		List<BaseUserInfo> res = new ArrayList<BaseUserInfo>();
		BaseUserInfo emptyInfo = new BaseUserInfo();
		while (rows.next()) {
			BaseUserInfo now = new BaseUserInfo();
			now.setID(new ID(rows.getLong("uid")));
			Iterator<String> fieldNameIter = emptyInfo.getKeySet().iterator();
			while (fieldNameIter.hasNext()){
				String name = fieldNameIter.next();
				now.setInfoField(name, InfoFieldFactory.getFactory().makeInfoField(name, rows.getString(name)));
			}
			res.add(now);
		}
		return res;
	}

	public void setUserInfo(ID uid, BaseUserInfo b) throws SQLException {
		String psql = "REPLACE INTO UserInfo VALUES(?";
		BaseUserInfo emptyInfo = new BaseUserInfo();
		Iterator<String> fieldNameIter = emptyInfo.getKeySet().iterator();
		while (fieldNameIter.hasNext()){
			psql += ",?";
			fieldNameIter.next();
		}
		psql += ")";
		
		PreparedStatement pStatement = connection.prepareStatement(psql);
		pStatement.setLong(1, uid.getValue());
		fieldNameIter = emptyInfo.getKeySet().iterator();
		int cnt = 1;
		while (fieldNameIter.hasNext()){
			String name = fieldNameIter.next();
			pStatement.setString(++cnt, b.getInfoField(name).getStringValue());
		}
		
		pStatement.execute();
	}

	public List<BaseUserInfo> searchUser(BaseUserInfo info) throws SQLException{
		String psql = "SELECT * FROM UserInfo WHERE ";
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
		List<BaseUserInfo> res = new ArrayList<BaseUserInfo>();
		while (rows.next()){
			BaseUserInfo b = new BaseUserInfo();
			b.setID(new ID(rows.getLong("uid")));
			// 为了保持KeySet的一致性，所以还是用emptyGroup来获取KeySet
			BaseUserInfo emptyInfo = new BaseUserInfo();
			Iterator<String> fieldNameIter = emptyInfo.getKeySet().iterator();
			while (fieldNameIter.hasNext()) {
				String name = fieldNameIter.next();
				b.setInfoField(name, InfoFieldFactory.getFactory()
						.makeInfoField(name, rows.getString(name)));
			}
			res.add(b);
		}
		return res;
	}
}
