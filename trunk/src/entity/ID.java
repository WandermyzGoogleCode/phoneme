package entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Random;

/**
 * ������ʶ�û�����Ⱥ���Ψһ��־��ֻ��ϵͳ�ɼ����û����ɼ�
 * ��ID��ϵͳ��IDFactory�Զ����ɣ�
 * @author Administrator
 *
 */
public class ID implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3866497586725147310L;
	//TODO ����û�����10�ڵĻ���ID�͵�ȫ�����ĳ�LONG�ˣ�����ҲҪ���ˡ�
	private static final int localThreshold = 2000000000;
	private static final int localRange = 10000000;
	private static final Random rand = new Random(Calendar.getInstance().getTime().hashCode());
	private int id;
	
	static public ID getNullID()
	{
		return new ID(-1);
	}
	
	static public ID getLocalRandID(){
		return new ID(localThreshold+rand.nextInt(localRange));
	}
	
	public ID(int id)
	{
		this.id = id;
	}
	
	public int getValue(){
		return id;
	}

	public boolean isNull() {
		return (id == -1);
	}
}
