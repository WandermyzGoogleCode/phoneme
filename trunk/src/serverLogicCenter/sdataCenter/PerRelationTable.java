package serverLogicCenter.sdataCenter;

import java.sql.SQLException;

import java.sql.Connection;

public class PerRelationTable extends RelationTable {

	public PerRelationTable(Connection connection) throws SQLException{
		super(connection);
	}
	
	@Override
	public String getTableName() {
		return "PerRelation";
	}

}
