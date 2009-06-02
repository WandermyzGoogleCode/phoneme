package serverLogicCenter.sdataCenter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.text.TabableView;

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
			sql = "CREATE TABLE "+getTableName()+"(id1 BIGINT NOT NULL, id2 BIGINT NOT NULL, INDEX(id1, id2)) CHARACTER SET gbk COLLATE gbk_bin TYPE InnoDB;";
			statement.execute(sql);
		}
	}
	
	synchronized public boolean hasRelation(ID id1, ID id2) throws SQLException{
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
	
	synchronized public List<ID> getRelations(ID id1) throws SQLException{
		String psql = "SELECT id2 FROM "+getTableName()+" WHERE id1=?";
		PreparedStatement pStatement = connection.prepareStatement(psql);
		pStatement.setLong(1, id1.getValue());
		ResultSet rows = pStatement.executeQuery();
		List<ID> res = new ArrayList<ID>();
		while (rows.next()){
			res.add(new ID(rows.getLong(1)));
		}
		return res;
	}
	
	synchronized public List<ID> getBackRelations(ID id2) throws SQLException{
		String psql = "SELECT id1 FROM "+getTableName()+" WHERE id2=?";
		PreparedStatement pStatement = connection.prepareStatement(psql);
		pStatement.setLong(1, id2.getValue());
		ResultSet rows = pStatement.executeQuery();
		List<ID> res = new ArrayList<ID>();
		while (rows.next()){
			res.add(new ID(rows.getLong(1)));
		}
		return res;		
	}	
	
	synchronized public void removeRelation(ID id1, ID id2) throws SQLException{
		String psql = "DELETE FROM "+getTableName()+" WHERE id1=? AND id2=?";
		PreparedStatement pStatement = connection.prepareStatement(psql);
		pStatement.setLong(1, id1.getValue());
		pStatement.setLong(2, id2.getValue());
		pStatement.execute();
	}
}
