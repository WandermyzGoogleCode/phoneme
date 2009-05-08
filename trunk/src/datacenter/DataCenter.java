package datacenter;
import java.util.ArrayList;
import entity.*;
public interface DataCenter {
	//�ж�һЩ�Ƿ���ͬ����ϵ�ˣ������ͬ����ϵ�ˣ������޸�BaseUserInfo
	public ReturnType setUserInfo(UserInfo b);	
	public ReturnType setPermission(Permission p);
	
	/**
	 * �������޸�Ⱥ��g
	 * @param g
	 * @return
	 */
	public ReturnType setGroup(Group g);
	
	public ReturnType removeGroup(Group g);
	
	/**
	 * ����ͬ����ϵ�˹�ϵ�������Լ���uid�û�)
	 * @param uid ͬ����ϵ�˵�ID
	 * @return
	 */
	public ReturnType addSynRelationship(ID uid);
	
	public ReturnType removeSynRelationship(ID uid);

	/**
	 * ���ӱ���Ȩ��ϵ�˹�ϵ�������Լ���uid�û�)(add permitted relationship)
	 * @param uid ����Ȩ��ϵ�˵�ID
	 * @return
	 */
	public ReturnType addPerRelationship(ID uid);

	public ReturnType removePerRelationship(ID uid);
	
	public ReturnType importFile(String fileName);
	
	public ReturnType exportFile(String fileName);

	/**
	 * ���û�uid���뵽Ⱥ��g��
	 * @param g
	 * @param uid
	 * @return
	 */
	public ReturnType addToGroup(Group g, ID uid);
	
	/**
	 * ���û�uid��Ⱥ��g��ɾ��
	 * @param g
	 * @param uid
	 * @return
	 */
	public ReturnType removeFromGroup(Group g, ID uid);

	/***
	 * 
	 * @param source ��ȡ�û���Ϣ��;��������ֱ�Ӵ��Լ������ݿ⣬���ߴ�Outlook��Google Synchronized�е�
	 * @return
	 */
	public ArrayList<UserInfo> getAllUserInfo(LocalSynSource source);
}
