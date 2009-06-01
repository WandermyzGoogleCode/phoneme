package serverLogicCenter.sdataCenter;

import java.sql.SQLException;
import java.util.Iterator;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import entity.BaseUserInfo;
import entity.Group;
import entity.infoField.IdenticalInfoField;
import entity.infoField.IndexedInfoField;

public class GroupInfoTable {
	private Connection connection;
	
	public GroupInfoTable(Connection connection) throws SQLException{
		this.connection = connection;
		
		// 如果没有表，则建表
		Statement statement = (Statement) this.connection.createStatement();

		boolean tableExist = true;

		//!!!如果BaseUserInfo有改动，那么表格需要重建
		Group emptyGroup = new Group();
		Iterator<String> fieldNameIter = emptyGroup.getKeySet()
				.iterator();

		// 判断GroupInfo信息表是否存在
		String sql = "DESCRIBE GroupInfo";
		try {
			statement.executeQuery(sql);
		} catch (SQLException e) {
			tableExist = false;
		}

		if (tableExist == false) {
			// 建立GroupInfo信息表
			sql = "CREATE TABLE GroupInfo(gid bigint not null";
			while (fieldNameIter.hasNext()) {
				String name = fieldNameIter.next();
				sql += ("," + name + " varchar("+emptyGroup.getInfoField(name).getMaxLength()+") NOT NULL");
			}

			String uniquePart = ", UNIQUE("; 
			for(String name: emptyGroup.getKeySet())
				if (emptyGroup.getInfoField(name) instanceof IdenticalInfoField){
					if (uniquePart.charAt(uniquePart.length()-1) != '(')
						uniquePart += ", ";
					uniquePart += name;
				}
			uniquePart += ")";
			if (!uniquePart.equals(", UNIQUE()"))
				sql += uniquePart;

			sql += ", INDEX(";
			for(String name: emptyGroup.getKeySet())
				if (emptyGroup.getInfoField(name) instanceof IndexedInfoField){
					if (sql.charAt(sql.length()-1) != '(')
						sql += ", ";
					sql += name;
				}
			sql += ")";

			sql += ", PRIMARY KEY(gid)) CHARACTER SET gbk COLLATE gbk_bin;";
			statement.executeUpdate(sql);
		}
		
	}
}
