package entity;

/**
 * SINGLETON
 * @author Administrator
 *
 */
public class IDFactory {
	static private IDFactory instance = null;
	
	private IDFactory(){
	}
	
	public static IDFactory getInstance()
	{
		//TODO synchronized处理多线程
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
}
