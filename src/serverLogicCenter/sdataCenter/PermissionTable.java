package serverLogicCenter.sdataCenter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.rowset.serial.SerialBlob;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import entity.ID;
import entity.Permission;

public class PermissionTable {
	private Connection connection;
	
	public PermissionTable(Connection connection) throws SQLException{
		this.connection = connection;
		
		//!!!如果表的设置有变动，必须删除表，然后等待该过程重新建立
		//检查表格是否存在，不存在则建立表格
		Statement statement = (Statement)connection.createStatement();
		String sql = "DESCRIBE Permission";
		try{
			statement.execute(sql);
		}
		catch (Exception e) {
			sql = "CREATE TABLE Permission(uid BIGINT NOT NULL, id BIGINT NOT NULL, permission BLOB NOT NULL, INDEX(uid, id)) CHARACTER SET gbk COLLATE gbk_bin;";
			statement.execute(sql);
		}
	}

	private boolean hasPermission(ID uid, ID id) throws SQLException{
		String psql = "SELECT COUNT(*) FROM Permission WHERE uid=? AND id=?";
		PreparedStatement pStatement = connection.prepareStatement(psql);
		pStatement.setLong(1, uid.getValue());
		pStatement.setLong(2, id.getValue());
		ResultSet res = pStatement.executeQuery();
		return (res.next() && res.getInt(1) > 0);
	}
	
	public void setPermission(ID uid, ID id, Permission p) throws SQLException{
		String psql;
		if (hasPermission(uid, id))
			psql = "UPDATE Permission SET permission=? WHERE uid=? AND id=?";
		else
			psql = "INSERT INTO Permission (permission, uid, id) VALUES(?, ?, ?)";
		PreparedStatement pStatement = connection.prepareStatement(psql);
		pStatement.setBlob(1, new SerialBlob(Serializer.serialize(p)));
		pStatement.setLong(2, uid.getValue());
		pStatement.setLong(3, id.getValue());
		pStatement.execute();
	}
}
