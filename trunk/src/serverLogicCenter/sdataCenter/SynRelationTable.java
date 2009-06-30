package serverLogicCenter.sdataCenter;

import java.sql.SQLException;

import java.sql.Connection;

public class SynRelationTable extends RelationTable {
	public SynRelationTable(Connection connection) throws SQLException{
		super(connection);
	}
	
	@Override
	public String getTableName() {
		return "SynRelation";
	}

}
