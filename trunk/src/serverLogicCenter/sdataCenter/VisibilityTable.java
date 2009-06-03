package serverLogicCenter.sdataCenter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import entity.ID;
import entity.Permission;

public class VisibilityTable {
	private Connection connection;
	
	public VisibilityTable(Connection connection) throws SQLException {
		this.connection = connection;
		
		//判断表是否存在，不存在则建表
		Statement statement = (Statement)this.connection.createStatement();
		String sql = "DESCRIBE Visibility";
		try{
			statement.execute(sql);
		}
		catch (Exception e) {
			sql = "CREATE TABLE Visibility(uid BIGINT NOT NULL, id BIGINT NOT NULL, v INT NOT NULL, INDEX(uid, id)) CHARACTER SET gbk COLLATE gbk_bin TYPE InnoDB;";
			statement.execute(sql);
		}
	}

	synchronized private boolean hasVisibility(ID uid, ID id) throws SQLException{
		String psql = "SELECT COUNT(*) FROM Visibility WHERE uid=? AND id=?";
		PreparedStatement pStatement = connection.prepareStatement(psql);
		pStatement.setLong(1, uid.getValue());
		pStatement.setLong(2, id.getValue());
		ResultSet res = pStatement.executeQuery();
		return (res.next() && res.getInt(1) > 0);
	}

	synchronized public void setVisibility(ID uid, ID id, int visibility) throws SQLException{
		String psql;
		if (hasVisibility(uid, id))
			psql = "UPDATE Visibility SET v=? WHERE uid=? AND id=?";
		else
			psql = "INSERT INTO Visibility (v, uid, id)VALUES(?, ?, ?)";
		PreparedStatement pStatement = connection.prepareStatement(psql);
		pStatement.setInt(1, visibility);
		pStatement.setLong(2, uid.getValue());
		pStatement.setLong(3, id.getValue());
		pStatement.execute();
	}

	synchronized public List<Integer> getVisibilities(ID uid, List<ID> idList) throws SQLException{
		List<Integer> res = new ArrayList<Integer>();
		if (idList == null || idList.isEmpty())
			return res;
		String psql = "SELECT id, v FROM Visibility WHERE uid=? AND (";
		for(ID id: idList){
			if (psql.charAt(psql.length()-1) != '(')
				psql += " OR ";
			psql += "id=?";
		}
		psql += ")";
		System.out.println(psql);//TODO TEST
		PreparedStatement pStatement = connection.prepareStatement(psql);
		pStatement.setLong(1, uid.getValue());
		for(int i=0; i<idList.size(); i++)
			pStatement.setLong(i+2, idList.get(i).getValue());
		ResultSet rows = pStatement.executeQuery();
		Map<ID, Integer> tempMap = new HashMap<ID, Integer>();
		while (rows.next())
			tempMap.put(new ID(rows.getLong("id")), rows.getInt("v"));
		for(int i=0; i<idList.size(); i++)
			if (tempMap.containsKey(idList.get(i)))
				res.add(tempMap.get(idList.get(i)));
			else
				res.add(0);
		return res;
	}
}
