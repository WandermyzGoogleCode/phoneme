package entity;

import java.io.Serializable;

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
	private int id;
	
	static ID getNullID()
	{
		return new ID(-1);
	}
	
	public ID(int id)
	{
		this.id = id;
	}
	
	public int getValue(){
		return id;
	}
}
