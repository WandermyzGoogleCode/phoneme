package ui_test;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import logiccenter.LogicCenter;
import logiccenter.LogicCenterImp;
import logiccenter.VirtualResult.AddPerContactResult;
import logiccenter.VirtualResult.AddSynContactResult;
import logiccenter.VirtualResult.AdmitInvitationResult;
import logiccenter.VirtualResult.SetPermissionResult;
import logiccenter.VirtualResult.VirtualState;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.hyperlink.URLHyperlink;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.widgets.Hyperlink;

import ui_test.GroupInfoTable.GroupInfoTableType;
import ui_test.UserInfoTable.UserInfoTableType;
import ui_test.localSyn.MergeUserInfoDialog;

import entity.BaseUserInfo;
import entity.Group;
import entity.Permission;
import entity.UserInfo;
import entity.infoField.InfoFieldName;

public class GuiImp implements ui.Gui
{
	private Shell shell;
	private LogicCenter logicCenter = LogicCenterImp.getInstance();
	
	GuiImp(Shell shell)
	{
		this.shell = shell;
	}
	
	@Override
	public void addPerContact(BaseUserInfo targetUser)
	{			
		UserInfoDialog userInfoDialog = new UserInfoDialog(shell, "设置权限", UserInfoTableType.Permission, new UserInfo(targetUser));
		Permission permission = new Permission();
		userInfoDialog.setPermission(permission);

		if (userInfoDialog.OpenPermission() == IDialogConstants.OK_ID)
		{
			AddPerContactResult result = logicCenter.addPerContact(targetUser.getIdenticalField(), permission);
			result.addObserver(new AddPerContactResultObserver());
		}
	}
	
	class AddPerContactResultObserver implements Observer
	{

		@Override
		public void update(Observable o, Object arg)
		{
			Display.getDefault().syncExec(new AddPerContactResultTask((AddPerContactResult)o));
		}
	}
	
	class AddPerContactResultTask implements Runnable
	{
		private AddPerContactResult result;
		
		public AddPerContactResultTask(AddPerContactResult result)
		{
			this.result = result;
		}

		@Override
		public void run()
		{
			VirtualState state = result.getState();
			if(state == VirtualState.PREPARED)
			{
				MessageDialog.openInformation(shell, "操作成功", "操作成功");
			}
			else if(state == VirtualState.ERRORED)
			{
				MessageDialog.openWarning(shell, "操作失败", result.getError().toString());
			}
		}
		
	}

	@Override
	public void addSynContact(BaseUserInfo targetUser)
	{
		SingleNumDialog visibDialog = new SingleNumDialog(shell);
		if(visibDialog.open() == IDialogConstants.OK_ID)
		{
			AddSynContactResult result = logicCenter.addSynContact(targetUser.getIdenticalField(), visibDialog.getResult());
			result.addObserver(new AddSynContactResultObserver());
		}
		
	}
	
	class AddSynContactResultObserver implements Observer
	{

		@Override
		public void update(Observable o, Object arg)
		{
			Display.getDefault().syncExec(new AddSynContactResultTask((AddSynContactResult)o));
		}
	}
	
	class AddSynContactResultTask implements Runnable
	{
		private AddSynContactResult result;
		
		public AddSynContactResultTask(AddSynContactResult result)
		{
			this.result = result;
		}

		@Override
		public void run()
		{
			VirtualState state = result.getState();
			if(state == VirtualState.PREPARED)
			{
				MessageDialog.openInformation(shell, "操作成功", "请求已发送，请等待对方验证");
			}
			else if(state == VirtualState.ERRORED)
			{
				MessageDialog.openWarning(shell, "操作失败", result.getError().toString());
			}
			else if(state == VirtualState.LOADING)
			{
				MessageDialog.openWarning(shell, "Loading...", "Loading...");
			}
		}
		
	}

	@Override
	public void admitInvitation(Group g)
	{
		GroupInfoDialog groupInfoDialog = new GroupInfoDialog(shell, "群组设置", 
				GroupInfoTableType.Admit, g);
		Permission permission = new Permission();
		groupInfoDialog.setPermission(permission);
		
		if(groupInfoDialog.open() == IDialogConstants.OK_ID)
		{
			AdmitInvitationResult result = logicCenter.admitInvitation(g.getID(), permission, groupInfoDialog.GetVisibility());
			result.addObserver(new AdmitInvitationResultObserver());
		}
		
	}
	
	class AdmitInvitationResultObserver implements Observer
	{

		@Override
		public void update(Observable o, Object arg)
		{
			Display.getDefault().syncExec(new AdmitInvitationResultTask((AdmitInvitationResult)o));
		}
	}
	
	class AdmitInvitationResultTask implements Runnable
	{
		private AdmitInvitationResult result;
		
		public AdmitInvitationResultTask(AdmitInvitationResult result)
		{
			this.result = result;
		}

		@Override
		public void run()
		{
			VirtualState state = result.getState();
			if(state == VirtualState.PREPARED)
			{
				MessageDialog.openInformation(shell, "操作成功", "你已经加入群组");
			}
			else if(state == VirtualState.ERRORED)
			{
				MessageDialog.openWarning(shell, "操作失败", result.getError().toString());
			}
		}
		
	}

	@Override
	public List<Boolean> checkUserList(List<UserInfo> userList, String askInfo)
	{
		// TODO Auto-generated method stub
		return null;
	}

	class MergeUserInfoTask implements Runnable{
		private UserInfo user1, user2;
		private boolean res;
		
		public MergeUserInfoTask(UserInfo user1, UserInfo user2) {
			this.user1 = user1;
			this.user2 = user2;
		}
		
		@Override
		public void run() {
			MergeUserInfoDialog dialog = new MergeUserInfoDialog(shell, user1, user2);	
			res = (dialog.open() == IDialogConstants.OK_ID);
		}
		
		public boolean getRes(){
			return res;
		}
	}
	
	@Override
	public boolean mergeUserInfo(UserInfo a, UserInfo b)
	{
		MergeUserInfoTask task = new MergeUserInfoTask(a, b);
		Display.getDefault().syncExec(task);
		return task.getRes();
	}

	@Override
	public UserInfo selMatchedUser(UserInfo a, List<UserInfo> list)
	{
		// TODO Auto-generated method stub
		return null;
	}

	class YesOrNoTask implements Runnable{
		private String askInfo;
		private boolean res;
		
		public YesOrNoTask(String askInfo) {
			this.askInfo = askInfo;
		}
		
		@Override
		public void run() {
			res = MessageDialog.openQuestion(shell, "提问", askInfo);
		}
		
		public boolean getRes(){
			return res;
		}
	}
	
	@Override
	public boolean yesOrNo(String askInfo)
	{
		YesOrNoTask task = new YesOrNoTask(askInfo);
		Display.getDefault().syncExec(task);
		return task.getRes();
	}

	@Override
	public void showError(String info) {
		MessageDialog.openError(shell, "错误", info);
	}

}
