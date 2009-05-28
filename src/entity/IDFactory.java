package entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * SINGLETON
 * 
 * 服务器启动时，一定要先用setUsedID把使用过的ID给填掉
 * 不填的话，出错的概率很小，但是非0概率会ID冲突。
 * 本地使用FACTORY的时候，ID就不用填了。
 * 
 * @author Administrator
 *
 */
public class IDFactory {
	static private IDFactory instance = null;
	
	Set<ID> usedID;
	
	private IDFactory(){
		usedID = new HashSet<ID>();
	}
	
	synchronized public static IDFactory getInstance()
	{
		if (instance == null)
			instance = new IDFactory();
		return instance;
	}
	
	public ID getNewUserID()
	{
		//TODO
		return null;
	}
	
	public ID getNewGroupID()
	{
		//TODO
		return null;
	}
	
	public ID getNewMessageID(){
		ID res = ID.getMessageRandID();
		while (usedID.contains(res))
			res = ID.getMessageRandID();
		usedID.add(res);
		return res;
	}
	
	public void putbackID(ID id){
		usedID.remove(id);
	}
	
	public void setUsedID(List<ID> idList){
		for(ID id: idList)
			usedID.add(id);
	}
}
