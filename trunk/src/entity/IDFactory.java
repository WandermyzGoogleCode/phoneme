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
}
