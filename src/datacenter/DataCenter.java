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
	
	//�ж�һЩ�Ƿ���ͬ����ϵ�ˣ������ͬ����ϵ�ˣ������޸�BaseUserInfo
	public ReturnType setUserInfo(UserInfo b){
		//�޸ı������ݿ��� �û�������Ϣ
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
	 * ����ͬ����ϵ�˹�ϵ�������Լ���uid�û�)
	 * @param uid ͬ����ϵ�˵�ID
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
	 * ���ӱ���Ȩ��ϵ�˹�ϵ�������Լ���uid�û�)
	 * @param uid ͬ����ϵ�˵�ID
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
	 * @param source ��ȡ�û���Ϣ��;��������ֱ�Ӵ��Լ������ݿ⣬���ߴ�Outlook��Google Synchronized�е�
	 * @return
	 */
	public ArrayList<UserInfo> getAllUserInfo(LocalSynSource source){
		ArrayList<UserInfo> r = new ArrayList<UserInfo>();
		return r;
	}
}
