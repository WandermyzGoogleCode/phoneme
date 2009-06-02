package serverLogicCenter.sdataCenter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import entity.ID;
import entity.infoField.IdenticalInfoField;

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
			sql = "CREATE TABLE idMap(idField VARCHAR(120) NOT NULL, uid BIGINT NOT NULL, PRIMARY KEY(idField), INDEX(uid)) CHARACTER SET gbk COLLATE gbk_bin TYPE InnoDB;";
			statement.execute(sql);
		}
	}

	public ID getID(IdenticalInfoField idField) throws SQLException{
		String psql = "SELECT uid FROM idMap WHERE idField=?";
		PreparedStatement pStatement = connection.prepareStatement(psql);
		pStatement.setString(1, idField.toIDString());
		ResultSet rows = pStatement.executeQuery();
		if (!rows.next())
			return ID.getNullID();
		else
			return new ID(rows.getLong(1));
	}
	
	public void setID(IdenticalInfoField idField, ID uid) throws SQLException{
		String psql = "REPLACE INTO idMap VALUES(?, ?)";
		PreparedStatement pStatement = connection.prepareStatement(psql);
		pStatement.setString(1, idField.toIDString());
		pStatement.setLong(2, uid.getValue());
		pStatement.execute();
	}

	public void removeIDField(IdenticalInfoField idField) throws SQLException{
		String psql = "DELETE FROM idMap WHERE idField=?";
		PreparedStatement pStatement = connection.prepareStatement(psql);
		pStatement.setString(1, idField.toIDString());
		pStatement.execute();
	}
}
