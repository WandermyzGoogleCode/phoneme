package logiccenter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import datacenter.DataCenter;

import serverLogicCenter.ServerLogicCenter;

import entity.BaseUserInfo;
import entity.Group;
import entity.ID;
import entity.IDFactory;
import entity.Password;
import entity.Permission;
import entity.ReturnType;
import entity.StatResult;
import entity.UserInfo;
import entity.VirtualResult.AddPerContactResult;
import entity.VirtualResult.AddSynContactResult;
import entity.VirtualResult.AdmitApplicationResult;
import entity.VirtualResult.AdmitInvitationResult;
import entity.VirtualResult.ApplyJoinGroupResult;
import entity.VirtualResult.CreateGroupResult;
import entity.VirtualResult.EditContactInfoResult;
import entity.VirtualResult.EditGroupResult;
import entity.VirtualResult.EditMyBaseInfoResult;
import entity.VirtualResult.ExportFileResult;
import entity.VirtualResult.GetStatResultResult;
import entity.VirtualResult.ImportFileResult;
import entity.VirtualResult.InviteToGroupResult;
import entity.VirtualResult.LocalSearchContactsResult;
import entity.VirtualResult.LoginResult;
import entity.VirtualResult.MessageBox;
import entity.VirtualResult.QuitGroupResult;
import entity.VirtualResult.RegisterResult;
import entity.VirtualResult.RelationCubeResult;
import entity.VirtualResult.RemoveGroupMemberResult;
import entity.VirtualResult.RemoveGroupResult;
import entity.VirtualResult.RemovePerContactResult;
import entity.VirtualResult.RemoveSynContactResult;
import entity.VirtualResult.SearchGroupResult;
import entity.VirtualResult.SearchUserResult;
import entity.VirtualResult.SetPermissionResult;
import entity.VirtualResult.SetVisibilityResult;
import entity.infoField.EmailAddr;
import entity.infoField.IdenticalInfoField;

public class LogicCenterImp implements LogicCenter {

	private BaseUserInfo loginUser = new BaseUserInfo();//��ǰ��¼���û�
	private ServerLogicCenter server = null;
	private MessageBox messageBox = null;
	
	private DataCenter dataCenter;
	
	@Override
	public AddPerContactResult addPerContact(IdenticalInfoField un) {
		return new AddPerContactResult(loginUser.getID(), un, this);
	}

	@Override
	public AddSynContactResult addSynContact(IdenticalInfoField un) {
		return new AddSynContactResult(loginUser.getID(), un, this);
	}

	@Override
	public AdmitApplicationResult admitApplication(ID gID, ID uID) {
		return new AdmitApplicationResult(gID, uID, this);
	}

	@Override
	public AdmitInvitationResult admitInvitation(ID gid) {
		return new AdmitInvitationResult(gid, this);
	}

	@Override
	public ApplyJoinGroupResult applyJoinGroup(ID gid) {
		return new ApplyJoinGroupResult(gid, this);
	}

	@Override
	public CreateGroupResult createGroup(Group g, Permission p) {
		return new CreateGroupResult(g, p, this);
	}

	@Override
	public EditContactInfoResult editContactInfo(UserInfo info) {
		return new EditContactInfoResult(info, this);
	}

	@Override
	public EditGroupResult editGroup(Group g) {
		return new EditGroupResult(g, this);
	}

	@Override
	public EditMyBaseInfoResult editMyBaseInfo(BaseUserInfo baseInfo) {
		return new EditMyBaseInfoResult(baseInfo, this);
	}

	@Override
	public ExportFileResult exportFile(String fileName) {
		return new ExportFileResult(fileName, this);
	}

	@Override
	public BaseUserInfo getLoginUser() {
		return loginUser;
	}

	@Override
	public GetStatResultResult getStatResult() {
		return new GetStatResultResult(this);
	}

	@Override
	public ImportFileResult importFile(String fileName) {
		return new ImportFileResult(fileName, this);
	}

	@Override
	public InviteToGroupResult inviteToGroup(IdenticalInfoField un, Group g,
			String inviteInfo) {
		return new InviteToGroupResult(un, g, inviteInfo, this);
	}

	@Override
	public LocalSearchContactsResult localSearchContacts(UserInfo info) {
		return new LocalSearchContactsResult(info, this);
	}

	@Override
	public LoginResult login(IdenticalInfoField identicalInfo, Password pwd) {
		return new LoginResult(identicalInfo, pwd, this);
	}

	@Override
	public QuitGroupResult quitGroup(ID gid, String reason) {
		return new QuitGroupResult(gid, reason, this);
	}

	@Override
	public RegisterResult register(BaseUserInfo b, Password pwd) {
		return new RegisterResult(b, pwd, this);
	}

	@Override
	public RelationCubeResult relationCube(IdenticalInfoField from, IdenticalInfoField to) {
		return new RelationCubeResult(from, to, this);
	}

	@Override
	public RemoveGroupResult removeGroup(Group g) {
		return new RemoveGroupResult(g, this);
	}

	@Override
	public RemoveGroupMemberResult removeGroupMember(IdenticalInfoField un, Group g) {
		return new RemoveGroupMemberResult(un, g, this);
	}

	@Override
	public RemovePerContactResult removePerContact(ID targetID) {
		return new RemovePerContactResult(targetID, this);
	}

	@Override
	public RemoveSynContactResult removeSynContact(ID targetID) {
		return new RemoveSynContactResult(targetID, this);
	}

	@Override
	public SearchGroupResult searchGroup(Group g) {
		return new SearchGroupResult(g, this);
	}

	@Override
	public SearchUserResult searchUser(BaseUserInfo b) {
		return new SearchUserResult(b, this);
	}

	@Override
	public SetPermissionResult setGroupPermission(Group g, Permission p) {
		return new SetPermissionResult(g.getID(), p, this);
	}

	@Override
	public SetPermissionResult setPermission(ID uid, Permission p) {
		return new SetPermissionResult(uid, p, this);
	}

	@Override
	public SetVisibilityResult setVisibility(ID uid, int visibility) {
		return new SetVisibilityResult(uid, visibility, this);
	}

	/**
	 * ���û�û�е�¼��ʱ�򣬻�������״̬���Ϸ���ʱ�򣬷���null��
	 */
	@Override
	public MessageBox getMessageBox()
	{
		//TODO ��ǰֻ�ǲ��ԡ��õ�����Ӧ�������û���¼��ʱ����һ��MessageBox��������ÿGETһ�ξͽ���һ����
		return new MessageBox(ID.getNullID(), this);
		/* ʵ��Ӧ����������
		 * return messageBox;
		 */
	}
	
	public LogicCenterImp(DataCenter dataCenter)
	{
		this.dataCenter = dataCenter;
		try {
		    Registry registry = LocateRegistry.getRegistry("Localhost");//TODO ��ǰֻ�Ǳ����������
		    server = (ServerLogicCenter) registry.lookup("logicCenterServer");
		} catch (Exception e) {
		    System.err.println("Client exception: " + e.toString());
		    e.printStackTrace();
		}			
	}
	
	@Override
	public DataCenter getDataCenter() {
		return dataCenter;
	}
	
	@Override
	public ServerLogicCenter getServer() {
		return server;
	}
	/**
	 * ������
	 * @param args
	 */
	public static void main(String args[])
	{
		LogicCenterImp logicCenterImp = new LogicCenterImp(null);
		MessageBox messageBox = logicCenterImp.getMessageBox();
		Tester tester = new Tester();
		messageBox.addObserver(tester);
		String cmd = "";
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));		
		while (!cmd.equals("exit"))
		{
			System.out.println("Type something...('exit' to exit)");
			try
			{
				cmd = stdin.readLine();
				System.out.println("'"+cmd+"' read...\n");
			}
			catch (Exception e){}
		}
		System.exit(0);//��ǰMessageBox���̲߳����Լ�������ʵ�ֺ����Ժ󣬸��߳�Ӧ�����˳���¼��ʱ����Զ�������
	}

	@Override
	public StatResult calcStat() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserInfo> searchContacts(UserInfo info) {
		// TODO Auto-generated method stub
		return null;
	}
}

class Tester implements Observer
{
	@Override
	public void update(Observable o, Object arg) 
	{
		MessageBox box = (MessageBox)o;
		System.out.println("State Changed, new State: "+box.getState());
		if (box.getUpdateTime() != null)
			System.out.println("UpdateTime: "+box.getUpdateTime()+"\n");
	}
}