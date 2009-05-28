package logiccenter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import logiccenter.VirtualResult.AddPerContactResult;
import logiccenter.VirtualResult.AddSynContactResult;
import logiccenter.VirtualResult.AdmitApplicationResult;
import logiccenter.VirtualResult.AdmitInvitationResult;
import logiccenter.VirtualResult.AllContactsBox;
import logiccenter.VirtualResult.ApplyJoinGroupResult;
import logiccenter.VirtualResult.CreateGroupResult;
import logiccenter.VirtualResult.EditContactInfoResult;
import logiccenter.VirtualResult.EditGroupResult;
import logiccenter.VirtualResult.EditMyBaseInfoResult;
import logiccenter.VirtualResult.ExportFileResult;
import logiccenter.VirtualResult.GetStatResultResult;
import logiccenter.VirtualResult.IgnoreMessageResult;
import logiccenter.VirtualResult.ImportFileResult;
import logiccenter.VirtualResult.InviteToGroupResult;
import logiccenter.VirtualResult.LocalSearchContactsResult;
import logiccenter.VirtualResult.LoginResult;
import logiccenter.VirtualResult.MessageBox;
import logiccenter.VirtualResult.QuitGroupResult;
import logiccenter.VirtualResult.RegisterResult;
import logiccenter.VirtualResult.RelationCubeResult;
import logiccenter.VirtualResult.RemoveContactInfoResult;
import logiccenter.VirtualResult.RemoveGroupMemberResult;
import logiccenter.VirtualResult.RemoveGroupResult;
import logiccenter.VirtualResult.RemovePerContactResult;
import logiccenter.VirtualResult.RemoveSynContactResult;
import logiccenter.VirtualResult.SearchGroupResult;
import logiccenter.VirtualResult.SearchUserResult;
import logiccenter.VirtualResult.SetPermissionResult;
import logiccenter.VirtualResult.SetVisibilityResult;
import logiccenter.VirtualResult.VirtualState;
import static java.lang.System.*;

import algorithm.Matcher;
import algorithm.SimpleUserInfoMatcher;

import datacenter.DataCenter;
import datacenter.DataCenterImp;

import serverLogicCenter.ServerLogicCenter;

import entity.BaseUserInfo;
import entity.Group;
import entity.ID;
import entity.Password;
import entity.Permission;
import entity.StatResult;
import entity.UserInfo;
import entity.infoField.Birthday;
import entity.infoField.IdenticalInfoField;
import entity.infoField.InfoFieldName;
import entity.message.Message;

public class LogicCenterImp implements LogicCenter {
	private static LogicCenterImp instance = null;
	
	private BaseUserInfo loginUser = new BaseUserInfo();//当前登录的用户
	private ServerLogicCenter server = null;
	private MessageBox messageBox = null;
	private AllContactsBox allContactsBox;
	
	private DataCenter dataCenter;
	
	@Override
	public AddPerContactResult addPerContact(IdenticalInfoField un, Permission permission) {
		return new AddPerContactResult(un, permission, this);
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
	public RemoveContactInfoResult removeContactInfo(ID id) {
		return new RemoveContactInfoResult(id, this);
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
	public LocalSearchContactsResult localSearchContacts(UserInfo info, Matcher userMatcher) {
		return new LocalSearchContactsResult(info, userMatcher, this);
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
	 * 当用户没有登录的时候，或是其他状态不合法的时候，返回null。
	 */
	@Override
	public MessageBox getMessageBox()
	{
		return messageBox;
	}
	
	private LogicCenterImp(DataCenter dataCenter)
	{
		this.dataCenter = dataCenter;
		allContactsBox = new AllContactsBox(this);
		try {
		    Registry registry = LocateRegistry.getRegistry("Localhost");//TODO 当前只是本机网络测试
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
	 * 测试用
	 * @param args
	 */
	public static void main(String args[])
	{
		LogicCenter logicCenter = new LogicCenterImp(DataCenterImp.Instance());
		/*MessageBox messageBox = logicCenter.getMessageBox();
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
		}*/
		
		UserInfo newUser1 = UserInfo.getNewLocalUser();
		EditContactInfoResult editRes = logicCenter.editContactInfo(newUser1);
		out.println(editRes.getState());
		try{
			Thread.sleep(1000);			
		}
		catch (Exception e){}
		out.println(editRes.getState());
		if (editRes.getState() == VirtualState.ERRORED)
			out.println(editRes.getError().toString());
		out.println("Bye~");
		System.exit(0);//当前MessageBox的线程不能自己消除。实现好了以后，该线程应该在退出登录的时候就自动消除。
	}

	@Override
	public StatResult calcStat() {
		StatResult res = new StatResult();
		List<UserInfo> allUsers = dataCenter.getAllUserInfo(null);
		res.setTotalCnt(allUsers.size());
		@SuppressWarnings("unchecked")
		ArrayList<UserInfo> distrib[] = new ArrayList[12];
		for(int i=0; i<12; i++)
			distrib[i] = new ArrayList<UserInfo>();
		for(UserInfo userInfo:allUsers){
			Birthday birthday = (Birthday)userInfo.getInfoField(InfoFieldName.Birthday);
			if (birthday.isEmpty())
				continue;
			distrib[birthday.getMonth()-1].add(userInfo);
		}
		res.setBirthDistrib(distrib);
		return res;
	}

	@Override
	public List<UserInfo> searchContacts(UserInfo info, Matcher matcher) {
		//TODO 有空的话，加强一下搜索的智能性
		List<UserInfo> allUsers = dataCenter.getAllUserInfo(null);
		ArrayList<UserInfo> res = new ArrayList<UserInfo>();
		for(UserInfo userInfo: allUsers)
			if (matcher.match(info, userInfo))
				res.add(userInfo);
		return res;
	}
	
	public synchronized static LogicCenter getInstance(){
		if (instance == null)
			instance = new LogicCenterImp(DataCenterImp.Instance());
		return instance;
	}

	@Override
	public AllContactsBox getAllContactsBox() {
		return allContactsBox;
	}

	@Override
	public void setLoginUser(BaseUserInfo loginUser) {
		this.loginUser = loginUser;
		if (messageBox != null)
			messageBox.close();
		messageBox = new MessageBox(loginUser.getID(), this);
	}
	
	public IgnoreMessageResult ignoreMessage(Message message) {
		return new IgnoreMessageResult(message, this);
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