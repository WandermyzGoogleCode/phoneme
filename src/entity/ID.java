package entity;

/**
 * ������ʶ�û�����Ⱥ���Ψһ��־��ֻ��ϵͳ�ɼ����û����ɼ�
 * ��ID��ϵͳ��IDFactory�Զ����ɣ�
 * @author Administrator
 *
 */
public class ID {
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
