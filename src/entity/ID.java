package entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

/**
 * 用来标识用户或者群组的唯一标志，只有系统可见，用户不可见
 * 该ID由系统的IDFactory自动生成，
 * @author Administrator
 *
 */
public class ID implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3866497586725147310L;
	private static final long localStart = 0;
	private static final long localRange = 10000000;
	private static final long userStart = localRange+localStart;
	private static final long userRange = 1000000000000000000L;
	private static final long groupStart = userStart+userRange;
	private static final long groupRange = 2000000000000000000L;
	private static final long messageStart = groupStart+groupRange;
	private static final long messageRange = 5000000000000000000L;
	
	public static final ID GLOBAL_ID = new ID(messageStart+messageRange);
	
	private static final Random rand = new Random(Calendar.getInstance().getTime().hashCode());
	private long id;
	int hash;
	
	static public ID getNullID()
	{
		return new ID(-1);
	}
	
	static public ID getLocalRandID(){
		return new ID(localStart+rand.nextLong()%localRange);
	}
	
	static public ID getUserRandID(){
		return new ID(userStart+rand.nextLong()%userRange);
	}
	
	static public ID getGroupRandID(){
		return new ID(groupStart+rand.nextLong()%groupRange);
	}
	
	static public ID getMessageRandID(){
		return new ID(messageStart+rand.nextLong()%messageRange);
	}
	
	public ID(long id)
	{
		this.id = id;
		this.hash = new Long(id).hashCode();
	}
	
	public long getValue(){
		return id;
	}

	public boolean isNull() {
		return (id == -1);
	}
	
	@Override
	public boolean equals(Object arg0) {
		if (arg0 instanceof ID)
			return ((ID)arg0).getValue() == getValue();
		else
			return false;
	}
	
	@Override
	public int hashCode() {
		return hash;
	}
	
	//测试
	public static void main(String args[]){
		ID id1 = new ID(1000), id2 = new ID(1000);
		System.out.println(id1.hashCode());
		System.out.println(id2.hashCode());
		System.out.println(id1.equals(id2));
		System.out.println(id1.hashCode() == id2.hashCode());
		Set<ID> test = new HashSet<ID>();
		test.add(id1);
		System.out.println(test.contains(id2));
		Map<ID, Integer> map = new HashMap<ID, Integer>();
		map.put(id1, 100);
		System.out.println(map.get(id2));
		String s1 = new String ("100"), s2 = new String("100");
		Map<String, Integer> sMap = new HashMap<String, Integer>();
		sMap.put(s1, 100);
		System.out.println(s1 == s2);
		System.out.println(sMap.get(s2));
		System.out.println(messageStart);
		System.out.println(messageRange+messageStart);
	}
}
