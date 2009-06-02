package algorithm;

import java.sql.SQLException;
import java.util.List;

import serverLogicCenter.sdataCenter.ServerDataCenter;

import entity.ID;

//TODO 实现一个RelationCube
public interface RelationCube {
	public List<ID> getSearchRes(ID from, ID to, ServerDataCenter center) throws SQLException;
}
