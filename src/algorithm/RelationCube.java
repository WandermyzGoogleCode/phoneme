package algorithm;

import java.util.List;

import serverLogicCenter.ServerLogicCenter;

import entity.ID;

//TODO ʵ��һ��RelationCube
public interface RelationCube {
	public List<ID> getSearchRes(ID from, ID to, ServerLogicCenter center);
}
