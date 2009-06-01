package serverLogicCenter.sdataCenter;

import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class IDMapTable {
	private Connection connection;
	
	public IDMapTable(Connection connection) throws SQLException{
		this.connection = connection;
		
		//判断表是否存在，不存在则建表
		Statement statement = (Statement)connection.createStatement();
		String sql = "DESCRIBE idMap";
		try{
			statement.execute(sql);
		}
		catch (Exception e) {
			sql = "CREATE TABLE idMap(idField VARCHAR(120) NOT NULL, uid BIGINT NOT NULL, PRIMARY KEY(idField)) CHARACTER SET gbk COLLATE gbk_bin;";
			statement.execute(sql);
		}
	}

}
