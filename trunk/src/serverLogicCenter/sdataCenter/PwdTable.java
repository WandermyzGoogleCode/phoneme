package serverLogicCenter.sdataCenter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.rowset.serial.SerialBlob;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import entity.ID;
import entity.Password;

public class PwdTable {
	private Connection connection;
	
	public PwdTable(Connection connection) throws SQLException{
		this.connection = connection;
		
		//判断表是否存在，不存在则建表
		Statement statement = (Statement)connection.createStatement();
		String sql = "DESCRIBE Pwd";
		try{
			statement.execute(sql);
		}
		catch (Exception e) {
			sql = "CREATE TABLE Pwd(uid BIGINT NOT NULL, pwd TINYBLOB NOT NULL, PRIMARY KEY(uid)) CHARACTER SET gbk COLLATE gbk_bin;";
			statement.execute(sql);
		}
	}

	public Password getPwd(ID uid) throws SQLException{
		String psql = "SELECT pwd FROM Pwd WHERE uid=?";
		PreparedStatement pStatement = connection.prepareStatement(psql);
		pStatement.setLong(1, uid.getValue());
		ResultSet rows = pStatement.executeQuery();
		if (rows.next())
			return (Password)Serializer.unserialize(rows.getBlob(1));
		else
			return null;
	}

	public void setPwd(ID uid, Password pwd) throws SQLException{
		String psql = "REPLACE INTO Pwd VALUES(?, ?)";
		PreparedStatement pStatement = connection.prepareStatement(psql);
		pStatement.setLong(1, uid.getValue());
		pStatement.setBlob(2, new SerialBlob(Serializer.serialize(pwd)));
		pStatement.execute();
	}
}
