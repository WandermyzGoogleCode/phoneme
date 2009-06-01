package serverLogicCenter.sdataCenter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import entity.ID;

public abstract class RelationTable {
	private Connection connection;
	
	//子类说明自己的表名
	abstract public String getTableName();
	
	public RelationTable(Connection connection) throws SQLException{
		this.connection = connection;
		
		//如果表不存在，建立表
		Statement statement = (Statement)connection.createStatement();
		String sql = "DESCRIBE "+getTableName();
		try{
			statement.execute(sql);
		}
		catch (Exception e) {
			sql = "CREATE TABLE "+getTableName()+"(id1 BIGINT NOT NULL, id2 BIGINT NOT NULL, INDEX(id1, id2)) CHARACTER SET gbk COLLATE gbk_bin;";
			statement.execute(sql);
		}
	}
	
	public boolean hasRelation(ID id1, ID id2) throws SQLException{
		String psql = "SELECT * FROM "+getTableName()+" WHERE id1=? AND id2=?";
		PreparedStatement pStatement = connection.prepareStatement(psql);
		pStatement.setLong(1, id1.getValue());
		pStatement.setLong(2, id2.getValue());
		ResultSet rows = pStatement.executeQuery();
		return rows.next();
	}
	
	public void setRelation(ID id1, ID id2) throws SQLException{
		if (hasRelation(id1, id2))
			return;
		String psql = "INSERT INTO "+getTableName()+" VALUES(?, ?)";
		PreparedStatement pStatement = connection.prepareStatement(psql);
		pStatement.setLong(1, id1.getValue());
		pStatement.setLong(2, id2.getValue());
		pStatement.execute();
	}
}
