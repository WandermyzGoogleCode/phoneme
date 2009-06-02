package serverLogicCenter.sdataCenter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
			sql = "CREATE TABLE Permission(uid BIGINT NOT NULL, id BIGINT NOT NULL, permission BLOB NOT NULL, INDEX(uid, id)) CHARACTER SET gbk COLLATE gbk_bin TYPE InnoDB;";
			statement.execute(sql);
		}
	}

	synchronized private boolean hasPermission(ID uid, ID id) throws SQLException{
		String psql = "SELECT COUNT(*) FROM Permission WHERE uid=? AND id=?";
		PreparedStatement pStatement = connection.prepareStatement(psql);
		pStatement.setLong(1, uid.getValue());
		pStatement.setLong(2, id.getValue());
		ResultSet res = pStatement.executeQuery();
		return (res.next() && res.getInt(1) > 0);
	}
	
	synchronized public void setPermission(ID uid, ID id, Permission p) throws SQLException{
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
	
	synchronized public List<Permission> getPermissions(ID uid, List<ID> idList) throws SQLException{
		List<Permission> res = new ArrayList<Permission>();
		if (idList == null || idList.isEmpty())
			return res;
		String psql = "SELECT id, permission FROM Permission WHERE uid=? AND (";
		for(ID id: idList){
			if (psql.charAt(psql.length()-1) != '(')
				psql += " OR ";
			psql += "id=?";
		}
		psql += ")";
		PreparedStatement pStatement = connection.prepareStatement(psql);
		pStatement.setLong(1, uid.getValue());
		for(int i=0; i<idList.size(); i++)
			pStatement.setLong(i+2, idList.get(i).getValue());
		ResultSet rows = pStatement.executeQuery();
		Map<ID, Permission> tempMap = new HashMap<ID, Permission>();
		while (rows.next())
			tempMap.put(new ID(rows.getLong(1)), (Permission)Serializer.unserialize(rows.getBlob(2)));
		for(int i=0; i<idList.size(); i++){
			Permission now = tempMap.get(idList.get(i));
			if (now == null)
				now = new Permission();//补空位
			res.add(now);
		}
		return res;
	}
}
