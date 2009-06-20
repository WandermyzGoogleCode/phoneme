package ui_test;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import logiccenter.LogicCenter;
import logiccenter.LogicCenterImp;
import logiccenter.VirtualResult.EditGroupResult;
import logiccenter.VirtualResult.QuitGroupResult;
import logiccenter.VirtualResult.RemoveGroupResult;
import logiccenter.VirtualResult.SetPermissionResult;
import logiccenter.VirtualResult.VirtualResult;
import logiccenter.VirtualResult.VirtualState;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import ui_test.GroupInfoTable.GroupInfoCellModifier;
import ui_test.GroupInfoTable.GroupInfoTableContentProvider;
import ui_test.GroupInfoTable.GroupInfoTableLabelProvider;
import ui_test.GroupInfoTable.GroupInfoTableType;
import ui_test.UserInfoTable.UserInfoTableType;
//import entity.Group;
//import entity.UserInfo;
import entity.Permission;
import entity.infoField.EmptyGroupDescription;
import entity.infoField.EmptyGroupName;
import entity.infoField.GroupFieldName;
import entity.infoField.InfoFieldFactory;
//import entity.infoField.GroupName;
import entity.infoField.InfoField;
import ui_test.ContactPermissionComposite;

public class GroupInfoDialog extends Dialog
{

	private ContactPermissionComposite contactPermissionComposite;
	private Label labelGroupName;
	private Button buttonToolsExitGroup;
	private Button buttonToolsPermission;
	private Button buttonToolsDelete;
	private Button buttonToolsEdit;
	private Composite compositeTools;
	private TabItem tabItemPermission;
	private TabItem tabItemTools;
	private Composite compositeInfo;
	private TabItem tabItemInfo;
	private TabFolder tabFolder;
	private TableColumn tableInfoColumnValue;
	private TableColumn tableInfoColumnField;
	private Table table;
	private TableViewer tableViewerInfo;
	
	private GroupInfoTableType groupInfoTableType;
	private entity.Group group;
	private LogicCenter logicCenter = LogicCenterImp.getInstance();
	private Shell shell;
	
	private GroupInfoDialog thisDialog = this;
	private Permission permission = new Permission();
	private int visibility;
	
	/**
	 * Create the dialog
	 * @param parentShell
	 */
	public GroupInfoDialog(Shell parentShell, String dialogTitle, GroupInfoTableType groupInfoTableType, entity.Group group)
	{
		super(parentShell);
		this.shell = parentShell;
		this.groupInfoTableType = groupInfoTableType;
		this.group = group;
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
		final GridData gd_tabFolder = new GridData(SWT.CENTER, SWT.TOP, true, true);
		gd_tabFolder.widthHint = 462;
		gd_tabFolder.heightHint = 189;
		tabFolder.setLayoutData(gd_tabFolder);

		//[start] 常用工具
		if(groupInfoTableType == GroupInfoTableType.Normal)
		{
			tabItemTools = new TabItem(tabFolder, SWT.NONE);
			tabItemTools.setText("常用工具");
	
			compositeTools = new Composite(tabFolder, SWT.NONE);
			final GridLayout gridLayout = new GridLayout();
			gridLayout.numColumns = 4;
			compositeTools.setLayout(gridLayout);
			tabItemTools.setControl(compositeTools);

			labelGroupName = new Label(compositeTools, SWT.NONE);
			final GridData gd_labelGroupName = new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1);
			labelGroupName.setLayoutData(gd_labelGroupName);
			labelGroupName.setText(group.getName());
			new Label(compositeTools, SWT.NONE);
			new Label(compositeTools, SWT.NONE);

			buttonToolsExitGroup = new Button(compositeTools, SWT.NONE);
			buttonToolsExitGroup.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false));
			buttonToolsExitGroup.addSelectionListener(new ButtonToolsExitGroupSelectionListener());
			buttonToolsExitGroup.setText("退出群组");
	
			buttonToolsEdit = new Button(compositeTools, SWT.NONE);
			buttonToolsEdit.addSelectionListener(new ButtonToolsEditSelectionListener());
			buttonToolsEdit.setText("编辑");
	
			buttonToolsDelete = new Button(compositeTools, SWT.NONE);
			buttonToolsDelete.addSelectionListener(new ButtonToolsDeleteSelectionListener());
			buttonToolsDelete.setText("删除");
	
			buttonToolsPermission = new Button(compositeTools, SWT.NONE);
			buttonToolsPermission.addSelectionListener(new ButtonToolsPermissionSelectionListener());
			final GridData gd_buttonToolsPermission = new GridData();
			buttonToolsPermission.setLayoutData(gd_buttonToolsPermission);
			buttonToolsPermission.setText("权限");
		}
		//[end]
		
		//[start] 群组信息
		if (groupInfoTableType != GroupInfoTableType.Admit) {
			tabItemInfo = new TabItem(tabFolder, SWT.NONE);
			tabItemInfo.setText("群组信息");

			compositeInfo = new Composite(tabFolder, SWT.NONE);
			compositeInfo.setLayout(new GridLayout());
			tabItemInfo.setControl(compositeInfo);

			tableViewerInfo = new TableViewer(compositeInfo, SWT.BORDER);
			table = tableViewerInfo.getTable();
			table.setLinesVisible(true);
			table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
			table.setHeaderVisible(true);

			tableInfoColumnField = new TableColumn(table, SWT.NONE);
			tableInfoColumnField.setWidth(100);
			tableInfoColumnField.setText("字段");

			tableInfoColumnValue = new TableColumn(table, SWT.NONE);
			tableInfoColumnValue.setWidth(335);
			tableInfoColumnValue.setText("值");

			tableViewerInfo
					.setColumnProperties(new String[] { "field", "value" });
			tableViewerInfo
					.setContentProvider(new GroupInfoTableContentProvider());
			tableViewerInfo.setLabelProvider(new GroupInfoTableLabelProvider());

			CellEditor[] cellEditors = new CellEditor[2];
			cellEditors[0] = null;
			cellEditors[1] = (CellEditor) new TextCellEditor(tableViewerInfo
					.getTable());

			tableViewerInfo.setCellEditors(cellEditors);
			tableViewerInfo.setCellModifier(new GroupInfoCellModifier(shell,
					groupInfoTableType, tableViewerInfo));

			List<InfoField> fieldsList = new ArrayList<InfoField>();
			for (GroupFieldName fieldName : GroupFieldName.values()) {
				fieldsList.add(group.getInfoField(fieldName.name()));
			}
			tableViewerInfo.setInput(fieldsList);
		}
		//[end]
		
		//[start] 权限
		if (groupInfoTableType != GroupInfoTableType.Search){
			tabItemPermission = new TabItem(tabFolder, SWT.NONE);
			tabItemPermission.setText("权限");
	
			contactPermissionComposite = new ContactPermissionComposite(tabFolder, SWT.NONE, permission);
			tabItemPermission.setControl(contactPermissionComposite);
		}
		//[end]
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
		return new Point(500, 375);
	}
	
	private void modifyGroup()
	{
		for(TableItem item : tableViewerInfo.getTable().getItems())
		{
			InfoField field = (InfoField)item.getData();
			group.setInfoField(field.getName(), field);
		}
	}
	
	protected void buttonPressed(int buttonId)
	{
		if (buttonId == IDialogConstants.OK_ID) 
		{		
			if(groupInfoTableType == GroupInfoTableType.Normal)
			{
				if (tabFolder.getSelectionIndex() == tabFolder.indexOf(tabItemPermission))
				{
					contactPermissionComposite.ModifyPermission();
					SetPermissionResult result = logicCenter.setPermission(group.getID(), permission);
					result.addObserver(new EditGroupResultObserver());
				}
				else if (tabFolder.getSelectionIndex() == tabFolder.indexOf(tabItemInfo))
				{
					modifyGroup();
					EditGroupResult result = logicCenter.editGroup(group);
					result.addObserver(new EditGroupResultObserver());
				}				
				
				//TODO: Error
				return;
			}
			else if(groupInfoTableType == GroupInfoTableType.New)
			{
				modifyGroup();
				contactPermissionComposite.ModifyPermission();
			}
			else if (groupInfoTableType == GroupInfoTableType.Search)
				modifyGroup();				
			else if(groupInfoTableType == GroupInfoTableType.Admit)
			{
				contactPermissionComposite.ModifyPermission();
			}
		}
		super.buttonPressed(buttonId);
	}
	
	public int GetVisibility()
	{
		return visibility;
	}
	
	/**
	 * 编辑群组 Observer
	 * @author Wander
	 *
	 */
	class EditGroupResultObserver implements Observer
	{

		@Override
		public void update(Observable o, Object arg)
		{
			Display.getDefault().syncExec(new EditGroupResultTask((VirtualResult)o));
		}
		
	}
	
	/**
	 * 编辑群组 Task
	 * @author Wander
	 *
	 */
	class EditGroupResultTask implements Runnable
	{
		private VirtualResult result;
		
		public EditGroupResultTask(VirtualResult result)
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
				MessageDialog.openWarning(shell, "编辑群组失败", result.getError().toString());
			}
			
		}
	}
	
	/**
	 * 编辑群组
	 * @author Wander
	 *
	 */
	private class ButtonToolsEditSelectionListener extends SelectionAdapter {
		public void widgetSelected(final SelectionEvent e)
		{
			tabFolder.setSelection(tabItemInfo);
		}
	}
	
	/**
	 * 编辑群组权限
	 * @author Wander
	 *
	 */
	private class ButtonToolsPermissionSelectionListener extends SelectionAdapter {
		public void widgetSelected(final SelectionEvent e)
		{
			tabFolder.setSelection(tabItemPermission);
		}
	}
	
	
	/**
	 * 删除群组
	 * @author Wander
	 *
	 */
	private class ButtonToolsDeleteSelectionListener extends SelectionAdapter {
		public void widgetSelected(final SelectionEvent e)
		{
			if(MessageDialog.openConfirm(shell, "确认删除", 
					String.format("确认要删除群组\"%s\"吗？",group.getName())
				))
			{
				RemoveGroupResult result = logicCenter.removeGroup(group);
				result.addObserver(new RemoveGroupResultObserver());
			}
		}
	}
	

	/**
	 * 删除群组 Observer
	 * @author Wander
	 *
	 */
	class RemoveGroupResultObserver implements Observer
	{

		@Override
		public void update(Observable o, Object arg)
		{
			Display.getDefault().syncExec(new RemoveGroupResultTask((RemoveGroupResult)o));
		}
		
	}
	
	/**
	 * 删除群组 Task
	 * @author Wander
	 *
	 */
	class RemoveGroupResultTask implements Runnable
	{
		private RemoveGroupResult result;
		
		public RemoveGroupResultTask(RemoveGroupResult result)
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
				MessageDialog.openWarning(shell, "删除群组失败", result.getError().toString());
			}
			
		}
	}
	
	/**
	 * 退出群组
	 * @author Wander
	 *
	 */
	private class ButtonToolsExitGroupSelectionListener extends SelectionAdapter {
		public void widgetSelected(final SelectionEvent e)
		{
			InputDialog confirmDialog = new InputDialog(shell, String.format("确认要退出群组\"%s\"吗？",group.getName()), "为什么要退出：", "未说明具体原因", null);
			if (confirmDialog.open() == InputDialog.OK)
			{
				QuitGroupResult res = logicCenter.quitGroup(group.getID(), confirmDialog.getValue());
				res.addObserver(new QuitGroupResultObserver());
			}
		}
	}
	
	/**
	 * 退出群组 Observer
	 *
	 */
	class QuitGroupResultObserver implements Observer
	{

		@Override
		public void update(Observable o, Object arg)
		{
			Display.getDefault().syncExec(new QuitGroupResultTask((QuitGroupResult)o));
		}
		
	}
	
	/**
	 * 退出群组 Task
	 *
	 */
	class QuitGroupResultTask implements Runnable
	{
		private QuitGroupResult result;
		
		public QuitGroupResultTask(QuitGroupResult result)
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
				MessageDialog.openWarning(shell, "退出群组失败", result.getError().toString());
			}
			
		}
	}
	
	public void setPermission(Permission permission)
	{
		this.permission = permission;
	}

}
