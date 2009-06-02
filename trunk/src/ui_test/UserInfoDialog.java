package ui_test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import logiccenter.LogicCenter;
import logiccenter.LogicCenterImp;
import logiccenter.VirtualResult.AllGroupsBox;
import logiccenter.VirtualResult.AllPerContactsBox;
import logiccenter.VirtualResult.EditGroupResult;
import logiccenter.VirtualResult.SetPermissionResult;
import logiccenter.VirtualResult.VirtualState;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Slider;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import algorithm.LCSQUserInfoMatcher;
import algorithm.LCSTUserInfoMatcher;
import algorithm.SimpleUserInfoMatcher;
import algorithm.UserInfoMatcher;

import entity.Group;
import entity.Permission;
import entity.UserInfo;
import entity.infoField.BaseInfoFieldName;
import entity.infoField.CustomInfoFieldName;
import entity.infoField.InfoField;
import entity.infoField.InfoFieldName;
import entity.message.Message;

//import ui_test.MainWindow.ToolItemAddressCustomGroupNewSelectionListener;
import ui_test.GroupInfoDialog.EditGroupResultTask;
import ui_test.UserInfoTable.UserInfoCellModifier;
import ui_test.UserInfoTable.UserInfoContentProvider;
import ui_test.UserInfoTable.UserInfoLabelProvider;
import ui_test.UserInfoTable.UserInfoTableElem;
import ui_test.UserInfoTable.UserInfoTableType;
import ui_test.ContactPermissionComposition;

public class UserInfoDialog extends Dialog
{
	//[start] Component Properties
	private ContactPermissionComposition contactPermissionComposition;
	private Button buttonSetPerm;
	private Button buttonAddSync;
	private Button buttonInviteGroup;
	private Text textRePassword;
	private Text textPassword;
	private Label labelRePassword;
	private Label labelPassword;
	private Scale scaleSearchThreshold;
	private Label labelSearchThreshold;
	private Label labelSearchStrategy;
	private Combo comboSearchStrategy;
	private Label labelName;
	private Button buttonDeleteFromGroup;
	private Button buttonDelete;
	private Button buttonPermission;
	private Button buttonEdit;
	private Composite compositeTools;
	private Composite compositeInfo;
	private TabItem tabItemPermission;
	private TabItem tabItemInfo;
	private TabItem tabItemTools;
	private TabFolder tabFolder;
	private TableColumn tableColumnValue;
	private TableColumn tableColumnField;
	private Table tableInfo;
	private TableViewer tableViewerInfo;
	protected Object result;
	protected Shell shell;
	//[end]

	//[start] Custom Properties
	private String dialogTitle;
	private UserInfoTableType userInfoTableType;
	private UserInfo user;
	private LogicCenter logicCenter;
	//private UserInfo newUser;
	private List<UserInfoTableElem> userInfoTableElems;
	private Operate operate = Operate.Null;
	
	private UserInfoMatcher searchMatcher = null;
	private double searchThreshold = 0;
	
	private String password;
	
	private Group group = null;
	private AllPerContactsBox allPerContactsBox = null;
	private AllGroupsBox allGroupsBox = null;
	Permission permission = null;
	
	private UserInfoDialog thisDialog = this;
	
	private enum Operate
	{
		Null,
		Edit,
		Permission,
		Delete,
		DeleteFromGroup
	}
	//[end]
	
	
	/**
	 * Create the dialog
	 * @param parentShell
	 */
	public UserInfoDialog(Shell parentShell, String dialogTitle, UserInfoTableType userType, UserInfo user)
	{
		super(parentShell);
		this.dialogTitle = dialogTitle;
		this.userInfoTableType = userType;
		this.user = user;
		this.logicCenter = LogicCenterImp.getInstance();
		
		if(userType == UserInfoTableType.Synchronization 
			&& user.getInfoField(InfoFieldName.Relation).isEmpty())
		{
			this.userInfoTableType = UserInfoTableType.Local;
		}
	}

	/**
	 * Create contents of the dialog
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent)
	{
		Composite container = (Composite) super.createDialogArea(parent);

		tabFolder = new TabFolder(container, SWT.NONE);
		final GridData gd_tabFolder = new GridData(SWT.FILL, SWT.FILL, true, true);
		tabFolder.setLayoutData(gd_tabFolder);

		//[start] 常用操作
		if(userInfoTableType == UserInfoTableType.Synchronization
				|| userInfoTableType == UserInfoTableType.Permission
				|| userInfoTableType == UserInfoTableType.Group
				|| userInfoTableType == UserInfoTableType.Owner
				|| userInfoTableType == UserInfoTableType.SearchResult
				|| userInfoTableType == UserInfoTableType.Local
				)
		{
			tabItemTools = new TabItem(tabFolder, SWT.NONE);
			tabItemTools.setText("常用操作");
	
			compositeTools = new Composite(tabFolder, SWT.NONE);
			final GridLayout gridLayout = new GridLayout();
			gridLayout.numColumns = 6;
			compositeTools.setLayout(gridLayout);
			tabItemTools.setControl(compositeTools);
	
			String userType = "";
			if(userInfoTableType == UserInfoTableType.Synchronization)
				userType = "同步联系人";
			else if(userInfoTableType == UserInfoTableType.Permission)
				userType = "被授权联系人";
			else if(userInfoTableType == UserInfoTableType.Group)
				userType = "群组联系人";	//TODO: 群组名
			else if(userInfoTableType == UserInfoTableType.Owner)
				userType = "所有者";
			else if(userInfoTableType == UserInfoTableType.SearchResult)
				userType = "搜索结果";
			else if(userInfoTableType == UserInfoTableType.Local)
				userType = "本地联系人";

			labelName = new Label(compositeTools, SWT.NONE);
			final GridData gd_labelName = new GridData(SWT.LEFT, SWT.CENTER, false, false, 3, 1);
			labelName.setLayoutData(gd_labelName);
			labelName.setText(user.getBaseInfo().getInfoField("Name").getStringValue() + " - " + userType);
			new Label(compositeTools, SWT.NONE);
			
			if(userInfoTableType == UserInfoTableType.Synchronization
					|| userInfoTableType == UserInfoTableType.Permission
					|| userInfoTableType == UserInfoTableType.Owner
					|| userInfoTableType == UserInfoTableType.Local)
			{
			new Label(compositeTools, SWT.NONE);
			new Label(compositeTools, SWT.NONE);
				buttonEdit = new Button(compositeTools, SWT.NONE);
				buttonEdit.setLayoutData(new GridData());
				buttonEdit.addSelectionListener(new ButtonEditSelectionListener());
				buttonEdit.setText("编辑");
			}
	
			if(userInfoTableType == UserInfoTableType.Permission
					|| userInfoTableType == UserInfoTableType.Owner)
			{
				buttonPermission = new Button(compositeTools, SWT.NONE);
				buttonPermission.addSelectionListener(new ButtonPermissionSelectionListener());
				if(userInfoTableType == UserInfoTableType.Owner)
					buttonPermission.setText("默认权限");
				else
					buttonPermission.setText("权限");
			}
	
			if(userInfoTableType == UserInfoTableType.Synchronization
					|| userInfoTableType == UserInfoTableType.Permission
					|| userInfoTableType == UserInfoTableType.Local)
			{
				buttonDelete = new Button(compositeTools, SWT.NONE);
				buttonDelete.addSelectionListener(new ButtonDeleteSelectionListener());
				buttonDelete.setLayoutData(new GridData());
				buttonDelete.setText("删除");
			}
	
			if(userInfoTableType == UserInfoTableType.Group)
			{
				//TODO: 群组管理员权限检查
				buttonDeleteFromGroup = new Button(compositeTools, SWT.NONE);
				buttonDeleteFromGroup.addSelectionListener(new ButtonDeleteFromGroupSelectionListener());
				buttonDeleteFromGroup.setText("从群组中删除");
			}

			if(userInfoTableType == UserInfoTableType.Synchronization
				|| userInfoTableType == UserInfoTableType.Permission
				|| userInfoTableType == UserInfoTableType.Group
				|| userInfoTableType == UserInfoTableType.SearchResult
				)
			{
				buttonInviteGroup = new Button(compositeTools, SWT.NONE);
				buttonInviteGroup.addSelectionListener(new ButtonInviteGroupSelectionListener());
				buttonInviteGroup.setText("邀请加入群组");
			}

			if(userInfoTableType == UserInfoTableType.SearchResult)
			{
				buttonAddSync = new Button(compositeTools, SWT.NONE);
				buttonAddSync.setText("加为同步联系人");
	
				buttonSetPerm = new Button(compositeTools, SWT.NONE);
				buttonSetPerm.setText("设为被授权联系人");
			}
			
			
			new Label(compositeTools, SWT.NONE);
			new Label(compositeTools, SWT.NONE);
			new Label(compositeTools, SWT.NONE);
			new Label(compositeTools, SWT.NONE);
			new Label(compositeTools, SWT.NONE);
		}
		//[end]

		//[start]联系人信息
		tabItemInfo = new TabItem(tabFolder, SWT.NONE);
		
		if(userInfoTableType == UserInfoTableType.Owner
				|| userInfoTableType == UserInfoTableType.Register)
		{
			tabItemInfo.setText("个人信息");
		}
		else
		{
			tabItemInfo.setText("联系人信息");
		}

		compositeInfo = new Composite(tabFolder, SWT.NONE);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 4;
		compositeInfo.setLayout(gridLayout);
		tabItemInfo.setControl(compositeInfo);

		//[start]搜索
		if(userInfoTableType == UserInfoTableType.SearchForm)
		{
			labelSearchStrategy = new Label(compositeInfo, SWT.NONE);
			final GridData gd_labelSearchStrategy = new GridData();
			labelSearchStrategy.setLayoutData(gd_labelSearchStrategy);
			labelSearchStrategy.setText("搜索策略");
	
			comboSearchStrategy = new Combo(compositeInfo, SWT.NONE);
			final GridData gd_comboSearchStrategy = new GridData();
			comboSearchStrategy.setLayoutData(gd_comboSearchStrategy);
			
			comboSearchStrategy.add("Simple", 0);
			comboSearchStrategy.add("LCSQ", 1);
			comboSearchStrategy.add("LCST", 2);
			comboSearchStrategy.select(0);

			labelSearchThreshold = new Label(compositeInfo, SWT.NONE);
			labelSearchThreshold.setText("精确度");
			scaleSearchThreshold = new Scale(compositeInfo, SWT.NONE);
			scaleSearchThreshold.setSelection(50);
		}
		//[end]

		//[start]设置密码
		if(userInfoTableType == UserInfoTableType.Register
			|| userInfoTableType == UserInfoTableType.Owner	
			)
		{
			labelPassword = new Label(compositeInfo, SWT.NONE);
			labelPassword.setText("密码");
	
			textPassword = new Text(compositeInfo, SWT.BORDER | SWT.PASSWORD);
			final GridData gd_textPassword = new GridData(SWT.FILL, SWT.CENTER, false, false);
			textPassword.setLayoutData(gd_textPassword);
			new Label(compositeInfo, SWT.NONE);
			new Label(compositeInfo, SWT.NONE);
	
			labelRePassword = new Label(compositeInfo, SWT.NONE);
			labelRePassword.setText("重复密码");
	
			textRePassword = new Text(compositeInfo, SWT.BORDER | SWT.PASSWORD);
			final GridData gd_textRePassword = new GridData(SWT.FILL, SWT.CENTER, true, false);
			textRePassword.setLayoutData(gd_textRePassword);
			new Label(compositeInfo, SWT.NONE);
			new Label(compositeInfo, SWT.NONE);
		}
		//[end]

		tableViewerInfo = new TableViewer(compositeInfo, SWT.BORDER);
		
		tableInfo = tableViewerInfo.getTable();
		tableInfo.setLinesVisible(true);
		tableInfo.setHeaderVisible(true);
		final GridData gd_tableInfo = new GridData(SWT.RIGHT, SWT.FILL, true, true, 4, 1);
		tableInfo.setLayoutData(gd_tableInfo);

		tableColumnField = new TableColumn(tableInfo, SWT.NONE);
		tableColumnField.setWidth(100);
		tableColumnField.setText("字段");

		tableColumnValue = new TableColumn(tableInfo, SWT.NONE);
		tableColumnValue.setWidth(340);
		tableColumnValue.setText("值");

		new TableColumn(tableInfo, SWT.NONE);


		tableViewerInfo.setContentProvider(new UserInfoContentProvider());
		tableViewerInfo.setLabelProvider(new UserInfoLabelProvider());
		
		generateTableElems();
		tableViewerInfo.setInput(userInfoTableElems);
		
		CellEditor[] cellEditors = new CellEditor[2];
		cellEditors[0] = null;
		cellEditors[1] = (CellEditor) new TextCellEditor(tableViewerInfo.getTable());
		
		tableViewerInfo.setColumnProperties(new String[] {"field", "value"});
		tableViewerInfo.setCellEditors(cellEditors);
		tableViewerInfo.setCellModifier(new UserInfoCellModifier(shell, tableViewerInfo, userInfoTableType));
		new Label(compositeInfo, SWT.NONE);
		new Label(compositeInfo, SWT.NONE);
		new Label(compositeInfo, SWT.NONE);
		new Label(compositeInfo, SWT.NONE);
		new Label(compositeInfo, SWT.NONE);
		//[end]
		
		//[start] 权限
		if( !logicCenter.getLoginUser().isNull() &&
			(userInfoTableType == UserInfoTableType.Permission
				|| userInfoTableType == UserInfoTableType.Owner)
			)
		{
			tabItemPermission = new TabItem(tabFolder, SWT.NONE);
			
			if(userInfoTableType == UserInfoTableType.Owner)
			{
				tabItemPermission.setText("默认权限");
			}
			else
			{
				tabItemPermission.setText("权限");
				permission = allPerContactsBox.getPermission(user.getBaseInfo().getID());
			}
			
			contactPermissionComposition = new ContactPermissionComposition(tabFolder, SWT.NONE, permission);

		}
		//[end]
		
		if(operate == Operate.Edit)
			(new ButtonEditSelectionListener()).widgetSelected(null);
		else if(operate == Operate.Permission)
			(new ButtonPermissionSelectionListener()).widgetSelected(null);
		else if(operate == Operate.Delete)
			(new ButtonDeleteSelectionListener()).widgetSelected(null);
		else if(operate == Operate.DeleteFromGroup)
			//(new ButtonEditSelectionListener()).widgetSelected(null);
		{ }
		
		return container;
	}

	/**
	 * Create contents of the button bar
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent)
	{
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
	}

	/**
	 * Return the initial size of the dialog
	 */
	@Override
	protected Point getInitialSize()
	{
		return new Point(500, 510);
	}
	
	private void generateTableElems()
	{
		userInfoTableElems = new ArrayList<UserInfoTableElem>();
		if(user.getBaseInfo() != null)
		{
			for(String key : user.getBaseInfo().getKeySet())
			{
				InfoField field = user.getBaseInfo().getInfoField(key);
				UserInfoTableElem elem = new UserInfoTableElem(field, userInfoTableType);
				if(elem.IsVisible())
					userInfoTableElems.add(elem);
			}
		}
		if(user.getCustomInfo() != null)
		{
			for(String key : user.getCustomInfo().getKeySet())
			{
				InfoField field = user.getCustomInfo().getInfoField(key);
				UserInfoTableElem elem = new UserInfoTableElem(field, userInfoTableType);
				if(elem.IsVisible())
					userInfoTableElems.add(elem);
			}
		}
	}
	
	private void modifyUser()
	{
		for(UserInfoTableElem elem : userInfoTableElems)
		{
			InfoField field = elem.GetInfoField();
			if (BaseInfoFieldName.contains(field.getName()))
			{
				user.getBaseInfo().setInfoField(field.getName(), field);
			}
			else if(CustomInfoFieldName.contains(field.getName()))
			{
				user.getCustomInfo().setInfoField(field.getName(), field);
			}
		}
	}	
	
	private boolean verifyRegisterUser()
	{
		if(user.getInfoField(InfoFieldName.Name).isEmpty())
		{
			MessageDialog.openWarning(shell, "缺少必填项", "请输入您的名字");
			return false;
		}
		
		if(userInfoTableType == UserInfoTableType.Register 
				&& textPassword.getText().isEmpty())
		{
			MessageDialog.openWarning(shell, "密码确认", "请设置您的密码");
			return false;
		}
		
		if(! textPassword.getText().equals(textRePassword.getText()))
		{
			MessageDialog.openWarning(shell, "密码确认", "两次密码不一致");
			return false;
		}
		
		return true;
	}
	
	/**
	 * 打开对话框并直接跳转到“个人资料”选项卡
	 */
	public int OpenEditInfo()
	{
		operate = Operate.Edit;
		return super.open();
	}
	
	/**
	 * 打开对话框并直接跳转到“权限”选项卡
	 */
	public int OpenPermission()
	{
		operate = Operate.Permission;
		return super.open();
	}
	
	/**
	 * 删除指定用户
	 */
	public int OpenDelete()
	{
		operate = Operate.Delete;
		return super.open();
	}
	
	/**
	 * 返回搜索策略，默认为Simple
	 * @return
	 */
	public UserInfoMatcher getStrategy()
	{
		return searchMatcher;
	}
	
	/**
	 * 返回密码，如果用户未输入密码则为空
	 * @return
	 */
	public String getPassword()
	{
		return password;
	}
	
	/**
	 * 设置要操作的群组
	 */
	public void setGroup(Group group)
	{
		this.group = group;
	}
	
	/**
	 * 为权限设置准备
	 * @param allPerContactsBox
	 */
	public void setAllPerContactsBox(AllPerContactsBox allPerContactsBox)
	{
		this.allPerContactsBox = allPerContactsBox;
	}
	
	/**
	 * 为与群组有关的操作做准备
	 * @param allGroupsBox
	 */
	public void setAllGroupsBox(AllGroupsBox allGroupsBox)
	{
		this.allGroupsBox = allGroupsBox;
	}
	
	
	
	protected void buttonPressed(int buttonId)
	{
		if (buttonId == IDialogConstants.OK_ID) {
			if(userInfoTableType == UserInfoTableType.Synchronization
					|| userInfoTableType == UserInfoTableType.Local)
			{
				modifyUser();
				logicCenter.editContactInfo(user);
			}
			else if (userInfoTableType == UserInfoTableType.SearchForm)
			{
				modifyUser();
				searchThreshold = (double)scaleSearchThreshold.getSelection() / (double)scaleSearchThreshold.getMaximum();
				switch(comboSearchStrategy.getSelectionIndex())
				{
				case 0:
					searchMatcher = new SimpleUserInfoMatcher(searchThreshold);
					break;
				case 1:
					searchMatcher = new LCSQUserInfoMatcher(searchThreshold);
					break;
				case 2:
					searchMatcher = new LCSTUserInfoMatcher(searchThreshold); 
					break;
				default:
					searchMatcher = new SimpleUserInfoMatcher(searchThreshold);
					break;
				}
			}
			else if (userInfoTableType == UserInfoTableType.Register
					|| userInfoTableType == UserInfoTableType.Owner)
			{
				modifyUser();
				
				if(!verifyRegisterUser())
					return;
				else
					password = textPassword.getText();
			}
			else if(userInfoTableType == UserInfoTableType.Permission)
			{
				contactPermissionComposition.ModifyPermission();
				SetPermissionResult result = logicCenter.setPermission(user.getBaseInfo().getID(), permission);
				result.addObserver(new SetPermissionResultObserver());
				return;
			}
		}
		super.buttonPressed(buttonId);
	}
	
	/**
	 * 编辑权限 Observer
	 * @author Wander
	 *
	 */
	class SetPermissionResultObserver implements Observer
	{

		@Override
		public void update(Observable o, Object arg)
		{
			Display.getCurrent().syncExec(new SetPermissionResultTask((SetPermissionResult)o));
		}
		
	}
	
	/**
	 * 编辑权限 Task
	 * @author Wander
	 *
	 */
	class SetPermissionResultTask implements Runnable
	{
		private SetPermissionResult result;
		
		public SetPermissionResultTask(SetPermissionResult result)
		{
			this.result = result;
		}

		@Override
		public void run()
		{
			VirtualState state = result.getState();
			if(state == VirtualState.PREPARED)
			{
				thisDialog.close();
			}
			else if(state == VirtualState.ERRORED)
			{
				MessageDialog.openWarning(shell, "编辑权限失败", result.getError().toString());
			}
			
		}
	}
	
	//[start] 常用操作 事件
	private class ButtonEditSelectionListener extends SelectionAdapter {
		public void widgetSelected(final SelectionEvent e)
		{
			tabFolder.setSelection(tabItemInfo);
		}
	}
	private class ButtonPermissionSelectionListener extends SelectionAdapter {
		public void widgetSelected(final SelectionEvent e)
		{
			tabFolder.setSelection(tabItemPermission);
		}
	}
	private class ButtonDeleteSelectionListener extends SelectionAdapter {
		public void widgetSelected(final SelectionEvent e)
		{
			//TODO: 如果通过operate直接调用，会出Exception
			if (MessageDialog.openConfirm(shell, "确认删除", String.format("你确实要删除联系人\"%s\"吗？", 
					user.getBaseInfo().getInfoField("Name").getStringValue())))
			{
				logicCenter.removeContactInfo(user.getBaseInfo().getID());
				buttonPressed(IDialogConstants.CANCEL_ID);
			}
			else
			{
				if(operate == Operate.Delete)
					buttonPressed(IDialogConstants.CANCEL_ID);
			}
		}
	}
	
	/**
	 * 从群组中删除用户
	 * @author Wander
	 *
	 */
	private class ButtonDeleteFromGroupSelectionListener extends SelectionAdapter {
		public void widgetSelected(final SelectionEvent e)
		{
			//TODO: 从群组中删除用户
		}
	}
	
	/**
	 * 邀请加入群组
	 * @author Wander
	 *
	 */
	private class ButtonInviteGroupSelectionListener extends SelectionAdapter {
		public void widgetSelected(final SelectionEvent e)
		{
			GroupSelectDialog groupSelectDialog = new GroupSelectDialog(getShell(), allGroupsBox);
			if(groupSelectDialog.open() == IDialogConstants.OK_ID)
			{
				Group group = groupSelectDialog.getSelectedGroup();
				//logicCenter.inviteToGroup(un, g, inviteInfo);
			}
		}
	}
	//[end]
}
