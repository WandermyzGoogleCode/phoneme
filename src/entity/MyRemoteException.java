package entity;

public class MyRemoteException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7685970957635942577L;
	private BoolInfo boolInfo;
	
	public BoolInfo getBoolInfo(){
		return boolInfo;
	}
	
	public MyRemoteException(MyError err){
		boolInfo = new BoolInfo(err);
	}
}
