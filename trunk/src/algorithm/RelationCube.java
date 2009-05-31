package algorithm;

import java.util.List;

import serverLogicCenter.ServerLogicCenter;

import entity.ID;

//TODO 实现一个RelationCube
public interface RelationCube {
	public List<ID> getSearchRes(ID from, ID to, ServerLogicCenter center);
}
