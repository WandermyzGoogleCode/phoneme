package logiccenter;
import entity.*;
import java.util.ArrayList;

public class LogicCenter {
	private BaseUserInfo loginUser;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	/**
	 * Return the login user, null if not login yet
	 * 
	 * @return the login user
	 */
	public BaseUserInfo getLoginUser(){
		BaseUserInfo r = new BaseUserInfo();
		return r;
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
		//�޸��Լ��Ļ�����Ϣ��ά�����Լ������ϵ���Ϣ,����������µ�����
		ReturnType r=new ReturnType();
		return r;
	}
	//!!
	public ReturnType searchGroup(Group g){
		ReturnType r=new ReturnType();
		return r;		
	}
	
	public ReturnType searchUser(BaseUserInfo b){
		ReturnType r=new ReturnType();
		return r;
	}
	
	public ReturnType addSynContact(UserName un){
		ReturnType r=new ReturnType();
		return r;
	}
	
	public ReturnType removeSynContact(UserName un){
		ReturnType r = new ReturnType();
		return r;
	}
	
	public ReturnType removePerContact(UserName un){
		ReturnType r = new ReturnType();
		return r;
	}
	
	public ReturnType createGroup(Group g, Permission p){
		ReturnType r = new ReturnType();
		return r;
	}
	
	public ReturnType removeGroup(Group g){
		ReturnType r = new ReturnType();
		return r;		
	}
	
	public ReturnType editGroup(Group g){
		ReturnType r = new ReturnType();
		return r;
	}
	
	public ReturnType inviteToGroup(UserName un, String inviteInfo){
		ReturnType r = new ReturnType();
		return r;		
	}
	
	public ReturnType removeGroupMember(UserName un, Group g){
		ReturnType r = new ReturnType();
		return r;		
	}
	
	public ReturnType applyJoinGroup(ID gID){
		ReturnType r = new ReturnType();
		return r;		
	}
	
	public ReturnType quitGroup(ID gID, String reason){
		ReturnType r = new ReturnType();
		return r;
	}
	
	public ReturnType admitInvitation(ID gID){
		ReturnType r = new ReturnType();
		return r;
	}
	
	public ReturnType admitApplication(ID gID, ID uID){
		ReturnType r = new ReturnType();
		return r;
	}
	
	public ReturnType setPermission(Permission p){
		ReturnType r = new ReturnType();
		return r;
	}

	//�ж�һЩ�Ƿ���ͬ����ϵ�ˣ������ͬ����ϵ�ˣ������޸�BaseUserInfo
	//ͬ������
	public ReturnType editContactInfo(UserInfo cInfo){
		ReturnType r = new ReturnType();
		return r;				
	}
	
	public ArrayList<UserInfo> localSearchContacts(UserInfo uInfo){
		ArrayList<UserInfo> r = new ArrayList<UserInfo>();
		return r;
	}
	
	/**
	 * ����ͳ����Ϣ
	 * 
	 * @return
	 */
	public StatResult getStatResult(){
		StatResult r = new StatResult();
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
	
	public ReturnType relationCube(UserName a, UserName b){
		ReturnType r=new ReturnType();
		return r;
	}
}
