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

import logiccenter.VirtualResult.*;
import static java.lang.System.*;

import algorithm.Matcher;

import datacenter.DataCenter;
import datacenter.DataCenterImp;
import datacenter.DataCenterImp_test;

import serverLogicCenter.ServerLogicCenter;
import ui.Gui;

import entity.BaseUserInfo;
import entity.Group;
import entity.ID;
import entity.Password;
import entity.Permission;
import entity.StatResult;
import entity.UserInfo;
import entity.infoField.Birthday;
import entity.infoField.IdenticalInfoField;
import entity.infoField.InfoFieldFactory;
import entity.infoField.InfoFieldName;
import entity.message.Message;

public class LogicCenterImp implements LogicCenter {
	private static LogicCenterImp instance = null;

	private BaseUserInfo loginUser = new BaseUserInfo();// 当前登录的用户
	private ServerLogicCenter server = null;
	private MessageBox messageBox = null;// 登录以后才加载
	private AllContactsBox allContactsBox;// 程序启动就加载
	private AllPerContactsBox allPerContactsBox = null;// 程序启动就加载
	private AllGroupsBox allGroupBox;// 程序启动就加载
	private Gui ui = null;

	private DataCenter dataCenter;

	@Override
	public AddPerContactResult addPerContact(IdenticalInfoField un,
			Permission permission) {
		return new AddPerContactResult(un, permission, this);
	}

	@Override
	public AddSynContactResult addSynContact(IdenticalInfoField un,
			int visibility) {
		return new AddSynContactResult(loginUser.getID(), un, visibility, this);
	}

	@Override
	public AdmitApplicationResult admitApplication(ID gID, ID uID,
			Permission p, int visibility) {
		return new AdmitApplicationResult(gID, uID, p, visibility, this);
	}

	@Override
	public AdmitInvitationResult admitInvitation(ID gid, Permission p,
			int visibility) {
		return new AdmitInvitationResult(gid, p, visibility, this);
	}

	@Override
	public ApplyJoinGroupResult applyJoinGroup(ID gid, Permission p,
			int visibility) {
		return new ApplyJoinGroupResult(gid, p, visibility, this);
	}

	@Override
	public CreateGroupResult createGroup(Group g, Permission p, int visibility) {
		return new CreateGroupResult(g, p, visibility, this);
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
	public EditMyBaseInfoResult editMyBaseInfo(BaseUserInfo baseInfo,
			Password pwd) {
		return new EditMyBaseInfoResult(baseInfo, pwd, this);
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
	public LocalSearchContactsResult localSearchContacts(UserInfo info,
			Matcher userMatcher) {
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
	public RelationCubeResult relationCube(IdenticalInfoField from,
			IdenticalInfoField to) {
		return new RelationCubeResult(from, to, this);
	}

	@Override
	public RemoveGroupResult removeGroup(Group g) {
		return new RemoveGroupResult(g, this);
	}

	@Override
	public RemoveGroupMemberResult removeGroupMember(IdenticalInfoField un,
			Group g) {
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
	public MessageBox getMessageBox() {
		return messageBox;
	}

	private LogicCenterImp(DataCenter dataCenter) {
		this.dataCenter = dataCenter;
		// TODO TEST
		// allPerContactsBox = new AllPerContactsBox(this);
		allGroupBox = new AllGroupsBox(this);
		allContactsBox = new AllContactsBox(this);// 必须放在allGroupBox后面，
		// 因为他会调用allGroupBox
		try {
			Registry registry = LocateRegistry.getRegistry("Localhost");// TODO
			// 当前只是本机网络测试
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
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			LogicCenter center = LogicCenterImp.getInstance();
			IdenticalInfoField idField = (IdenticalInfoField) InfoFieldFactory
					.getFactory().makeInfoField("QQNumber", "11");
			Password pwd = new Password("test");
			center.login(idField, pwd);

			Group g = center.getAllGroupsBox().getGroups().get(0);
			center.inviteToGroup((IdenticalInfoField) InfoFieldFactory
					.getFactory().makeInfoField("QQNumber", "11"), g, "lala");
			center.logout();
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
	}

	@Override
	public StatResult calcStat() {
		StatResult res = new StatResult();
		List<UserInfo> allUsers = dataCenter.getAllUserInfo(null);
		res.setTotalCnt(allUsers.size());
		@SuppressWarnings("unchecked")
		ArrayList<UserInfo> distrib[] = new ArrayList[12];
		for (int i = 0; i < 12; i++)
			distrib[i] = new ArrayList<UserInfo>();
		for (UserInfo userInfo : allUsers) {
			Birthday birthday = (Birthday) userInfo
					.getInfoField(InfoFieldName.Birthday);
			if (birthday.isEmpty())
				continue;
			distrib[birthday.getMonth() - 1].add(userInfo);
		}
		res.setBirthDistrib(distrib);
		return res;
	}

	@Override
	public List<UserInfo> searchContacts(UserInfo info, Matcher matcher) {
		// TODO 有空的话，加强一下搜索的智能性
		List<UserInfo> allUsers = dataCenter.getAllUserInfo(null);
		ArrayList<UserInfo> res = new ArrayList<UserInfo>();
		for (UserInfo userInfo : allUsers)
			if (matcher.match(info, userInfo))
				res.add(userInfo);
		return res;
	}

	public synchronized static LogicCenter getInstance() {
		if (instance == null)
			instance = new LogicCenterImp(DataCenterImp_test.Instance());
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
		allPerContactsBox = new AllPerContactsBox(this);
		// 刷新群组，以获取群组权限
		allGroupBox.updateAll();
	}

	@Override
	public RemoveMessageResult removeMessage(Message message) {
		return new RemoveMessageResult(message, this);
	}

	@Override
	public Gui getUI() {
		return ui;
	}

	@Override
	public void setUI(Gui ui) {
		this.ui = ui;
	}

	@Override
	public AllPerContactsBox getAllPerContatcsBox() {
		return allPerContactsBox;
	}

	@Override
	public AllGroupsBox getAllGroupsBox() {
		return allGroupBox;
	}

	@Override
	public Group getGroup(ID gid) {
		return allGroupBox.getGroupMap().get(gid);
	}

	@Override
	public RemoteSynResult removeSynchronize() {
		return new RemoteSynResult(this);
	}

	@Override
	public void logout() throws RemoteException {
		server.logout(loginUser.getID());
		loginUser = new BaseUserInfo();
		allPerContactsBox = null;
		messageBox = null;
	}

	@Override
	public void editLoginUser(BaseUserInfo baseInfo) {
		this.loginUser = baseInfo;
	}
}

class Tester implements Observer {
	@Override
	public void update(Observable o, Object arg) {
		MessageBox box = (MessageBox) o;
		System.out.println("State Changed, new State: " + box.getState());
		if (box.getUpdateTime() != null)
			System.out.println("UpdateTime: " + box.getUpdateTime() + "\n");
	}
}