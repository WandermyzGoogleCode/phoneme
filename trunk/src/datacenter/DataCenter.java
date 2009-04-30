package datacenter;
import java.util.ArrayList;
import entity.*;
public class DataCenter {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	//判断一些是否是同步联系人，如果是同步联系人，则不能修改BaseUserInfo
	public ReturnType setUserInfo(UserInfo b){
		//修改本地数据库中 用户基本信息
		ReturnType r=new ReturnType();
		return r;
	}
	
	public ReturnType setPermission(Permission p){
		ReturnType r = new ReturnType();
		return r;		
	}
	
	public ReturnType setGroup(Group g){
		ReturnType r = new ReturnType();
		return r;
	}
	
	/**
	 * 增加同步联系人关系，（从自己到uid用户)
	 * @param uid 同步联系人的ID
	 * @return
	 */
	public ReturnType addSynRelationship(ID uid){
		ReturnType r = new ReturnType();
		return r;
	}
	
	public ReturnType removeSynRelationship(ID uid){
		ReturnType r = new ReturnType();
		return r;
	}

	/**
	 * 增加被授权联系人关系，（从自己到uid用户)
	 * @param uid 同步联系人的ID
	 * @return
	 */
	public ReturnType addPerRelationship(ID uid){
		ReturnType r = new ReturnType();
		return r;
	}

	public ReturnType removePerRelationship(ID uid){
		ReturnType r = new ReturnType();
		return r;
	}
	
	public ReturnType importFile(String fileName){
		ReturnType r = new ReturnType();
		return r;						
	}
	
	public ReturnType exportFile(String fileName){
		ReturnType r = new ReturnType();
		return r;				
	}
	
	/***
	 * 
	 * @param source 获取用户信息的途径。比如直接从自己的数据库，或者从Outlook，Google Synchronized中等
	 * @return
	 */
	public ArrayList<UserInfo> getAllUserInfo(LocalSynSource source){
		ArrayList<UserInfo> r = new ArrayList<UserInfo>();
		return r;
	}
}
