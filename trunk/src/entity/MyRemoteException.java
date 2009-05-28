package entity;

public class MyRemoteException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7685970957635942577L;
	private MyError err;
	
	public MyError getErr(){
		return err;
	}
	
	public MyRemoteException(MyError err){
		this.err = err;
	}
}
