package serverLogicCenter.sdataCenter;

import java.sql.SQLException;
import java.util.Iterator;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import entity.BaseUserInfo;
import entity.infoField.IdenticalInfoField;
import entity.infoField.IndexedInfoField;

//TODO TO FILL UP
public class UserInfoTable {
	private Connection connection;
	
	UserInfoTable(Connection connection) throws SQLException{
		this.connection = connection;

		// 如果没有表，则建表
		Statement statement = (Statement) this.connection.createStatement();

		boolean userInfoTableExist = true;

		//!!!如果BaseUserInfo有改动，那么表格需要重建
		BaseUserInfo emptyInfo = new BaseUserInfo();
		Iterator<String> fieldNameIter = emptyInfo.getKeySet()
				.iterator();

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
				sql += ("," + name + " varchar("+emptyInfo.getInfoField(name).getMaxLength()+") NOT NULL");
			}

			sql += ", UNIQUE(";
			for(String name: emptyInfo.getKeySet())
				if (emptyInfo.getInfoField(name) instanceof IdenticalInfoField){
					if (sql.charAt(sql.length()-1) != '(')
						sql += ", ";
					sql += name;
				}
			sql += ")";

			sql += ", INDEX(";
			for(String name: emptyInfo.getKeySet())
				if (emptyInfo.getInfoField(name) instanceof IndexedInfoField){
					if (sql.charAt(sql.length()-1) != '(')
						sql += ", ";
					sql += name;
				}
			sql += ")";

			sql += ", PRIMARY KEY(uid)) CHARACTER SET gbk COLLATE gbk_bin;";
			statement.executeUpdate(sql);
		}
	}
}

