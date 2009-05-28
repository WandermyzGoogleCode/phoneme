package entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * SINGLETON
 * 
 * ����������ʱ��һ��Ҫ����setUsedID��ʹ�ù���ID�����
 * ����Ļ�������ĸ��ʺ�С�����Ƿ�0���ʻ�ID��ͻ��
 * ����ʹ��FACTORY��ʱ��ID�Ͳ������ˡ�
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
