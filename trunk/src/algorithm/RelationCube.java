package algorithm;

import java.util.List;

import serverLogicCenter.ServerLogicCenter;

import entity.BaseUserInfo;
import entity.ID;

public interface RelationCube {
	public List<BaseUserInfo> getSearchRes(ID from, ID to, ServerLogicCenter center);
}
