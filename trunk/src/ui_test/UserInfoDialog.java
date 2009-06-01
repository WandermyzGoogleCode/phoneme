package ui_test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import logiccenter.LogicCenter;
import logiccenter.LogicCenterImp;

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
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import entity.UserInfo;
import entity.infoField.BaseInfoFieldName;
import entity.infoField.CustomInfoFieldName;
import entity.infoField.InfoField;
import entity.infoField.InfoFieldName;

//import ui_test.MainWindow.ToolItemAddressCustomGroupNewSelectionListener;
import ui_test.UserInfoTable.UserInfoCellModifier;
import ui_test.UserInfoTable.UserInfoContentProvider;
import ui_test.UserInfoTable.UserInfoLabelProvider;
import ui_test.UserInfoTable.UserInfoTableElem;
import ui_test.UserInfoTable.UserInfoTableType;

public class UserInfoDialog extends Dialog
{
	//[start] Component Properties
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
				)
		{
			tabItemTools = new TabItem(tabFolder, SWT.NONE);
			tabItemTools.setText("常用操作");
	
			compositeTools = new Composite(tabFolder, SWT.NONE);
			final GridLayout gridLayout = new GridLayout();
			gridLayout.numColumns = 4;
			compositeTools.setLayout(gridLayout);
			tabItemTools.setControl(compositeTools);
	
			labelName = new Label(compositeTools, SWT.NONE);
			final GridData gd_labelName = new GridData(SWT.LEFT, SWT.CENTER, false, false, 3, 1);
			labelName.setLayoutData(gd_labelName);
			labelName.setText(user.getBaseInfo().getInfoField("Name").getStringValue());
			new Label(compositeTools, SWT.NONE);
			
			if(userInfoTableType == UserInfoTableType.Synchronization
					|| userInfoTableType == UserInfoTableType.Permission
					|| userInfoTableType == UserInfoTableType.Owner)
			{
				buttonEdit = new Button(compositeTools, SWT.NONE);
				buttonEdit.setLayoutData(new GridData());
				buttonEdit.addSelectionListener(new ButtonEditSelectionListener());
				buttonEdit.setText("编辑");
			}
	
			if(userInfoTableType == UserInfoTableType.Synchronization
					|| userInfoTableType == UserInfoTableType.Permission
					|| userInfoTableType == UserInfoTableType.Owner)
			{
				buttonPermission = new Button(compositeTools, SWT.NONE);
				buttonPermission.addSelectionListener(new ButtonPermissionSelectionListener());
				buttonPermission.setText("权限");
			}
	
			if(userInfoTableType == UserInfoTableType.Synchronization
					|| userInfoTableType == UserInfoTableType.Permission)
			{
				buttonDelete = new Button(compositeTools, SWT.NONE);
				buttonDelete.addSelectionListener(new ButtonDeleteSelectionListener());
				buttonDelete.setLayoutData(new GridData());
				buttonDelete.setText("删除");
			}
	
			if(userInfoTableType == UserInfoTableType.Group)
			{
				buttonDeleteFromGroup = new Button(compositeTools, SWT.NONE);
				buttonDeleteFromGroup.setText("从群组中删除");
			}
		}
		//[end]

		//[start]联系人信息
		tabItemInfo = new TabItem(tabFolder, SWT.NONE);
		tabItemInfo.setText("联系人信息");

		compositeInfo = new Composite(tabFolder, SWT.NONE);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		compositeInfo.setLayout(gridLayout);
		tabItemInfo.setControl(compositeInfo);

		if(userInfoTableType == UserInfoTableType.SearchForm)
		{
			labelSearchStrategy = new Label(compositeInfo, SWT.NONE);
			final GridData gd_labelSearchStrategy = new GridData();
			labelSearchStrategy.setLayoutData(gd_labelSearchStrategy);
			labelSearchStrategy.setText("搜索策略");
	
			comboSearchStrategy = new Combo(compositeInfo, SWT.NONE);
			final GridData gd_comboSearchStrategy = new GridData();
			comboSearchStrategy.setLayoutData(gd_comboSearchStrategy);
		}

		tableViewerInfo = new TableViewer(compositeInfo, SWT.BORDER);
		
		tableInfo = tableViewerInfo.getTable();
		tableInfo.setLinesVisible(true);
		tableInfo.setHeaderVisible(true);
		final GridData gd_tableInfo = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
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
		//[end]
		
		//[start] 权限
		if(userInfoTableType == UserInfoTableType.Synchronization
				|| userInfoTableType == UserInfoTableType.Permission)
		{
			tabItemPermission = new TabItem(tabFolder, SWT.NONE);
			tabItemPermission.setText("权限");
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
	
	public void OpenEditInfo()
	{
		operate = Operate.Edit;
		super.open();
	}
	
	public void OpenPermission()
	{
		operate = Operate.Permission;
		super.open();
	}
	
	public void OpenDelete()
	{
		operate = Operate.Delete;
		super.open();
	}
	
	protected void buttonPressed(int buttonId)
	{
		if (buttonId == IDialogConstants.OK_ID) {
			modifyUser();
			logicCenter.editContactInfo(user);
			
			//return;
		}
		super.buttonPressed(buttonId);
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
	//[end]
}
