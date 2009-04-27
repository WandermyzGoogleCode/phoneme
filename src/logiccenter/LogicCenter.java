package logiccenter;
import entity.*;
public class LogicCenter {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	public ReturnType register(EmailAddr e,Password pwd,BaseUserInfo b){
		//email,password,BaseUserInfo
		ReturnType r=new ReturnType();
		return r;
	}
	public ReturnType login(EmailAddr e,Password pwd){
		
		ReturnType r=new ReturnType();
		return r;
	}
	public ReturnType editMyBaseInfo(){
		//修改自己的基本信息，维护在自己网络上的信息,用于网络更新的推送
		ReturnType r=new ReturnType();
		return r;
	}
	//!!
	public ReturnType searchUserName(BaseUserInfo b){
		ReturnType r=new ReturnType();
		return r;
	}
	public ReturnType addSynContact(UserName un){
		ReturnType r=new ReturnType();
		return r;
	}
}
