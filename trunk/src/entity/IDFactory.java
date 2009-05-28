package entity;

import java.util.HashSet;
import java.util.Set;

/**
 * SINGLETON
 * @author Administrator
 *
 */
public class IDFactory {
	static private IDFactory instance = null;
	
	Set<Long> usedMessageID, usedUserID, usedGroupID;
	
	private IDFactory(){
		usedMessageID = new HashSet<Long>();
		usedUserID = new HashSet<Long>();
		usedGroupID = new HashSet<Long>();
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
		while (usedMessageID.contains(new Long(res.getValue())))
			res = ID.getMessageRandID();
		usedMessageID.add(new Long(res.getValue()));
		return res;
	}
	
	public void putbackMessageID(ID id){
		usedMessageID.remove(new Long(id.getValue()));
	}
}
