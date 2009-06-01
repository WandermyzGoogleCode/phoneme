package serverLogicCenter.sdataCenter;

import java.sql.SQLException;

import com.mysql.jdbc.Connection;

public class PerRelationTable extends RelationTable {

	public PerRelationTable(Connection connection) throws SQLException{
		super(connection);
	}
	
	@Override
	public String getTableName() {
		return "PerRelation";
	}

}
