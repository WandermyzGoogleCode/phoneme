package ui_test;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import logiccenter.LogicCenter;
import logiccenter.LogicCenterImp;
import logiccenter.VirtualResult.AllContactsBox;
import logiccenter.VirtualResult.AllGroupsBox;
import logiccenter.VirtualResult.CreateGroupResult;
import logiccenter.VirtualResult.VirtualState;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
//import org.eclipse.jface.viewers.TableTreeViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
//import org.eclipse.swt.custom.TableTree;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;

import ui_test.GroupTableTree.GroupInfoTableType;
import ui_test.GroupTableTree.GroupTableTreeContentProvider;
import ui_test.GroupTableTree.GroupTableTreeLabelProvider;
import ui_test.UserInfoTable.UserInfoTableType;

import entity.Group;
import entity.Permission;
import entity.UserInfo;

public class GroupComposite extends Composite
{

	private ToolItem toolEditGroup;
	private TreeColumn treeColumnName;
	private Tree tree;
	private TreeViewer treeViewer;
	private ToolItem toolNewGroup;
	private ToolBar toolBar;
	private TreeColumn treeColumnNickName;
	private TreeColumn treeColumnRelation;
	private TreeColumn treeColumnCellphone;
	private TreeColumn treeColumnEmail;
	private TreeColumn treeColumnBirth;
	
	private GroupTableTreeContentProvider contentProvider;
	
	private GroupComposite thisComposite = this;
	
	private AllGroupsBox allGroupsBox = null;
	
	private LogicCenter logicCenter = LogicCenterImp.getInstance();
	/**
	 * Create the composite
	 * @param parent
	 * @param style
	 */
	public GroupComposite(Composite parent, int style)
	{
		super(parent, style);
		setLayout(new FormLayout());

		toolBar = new ToolBar(this, SWT.NONE);
		final FormData fd_toolBar = new FormData();
		fd_toolBar.right = new FormAttachment(100, 0);
		fd_toolBar.top = new FormAttachment(0, 0);
		fd_toolBar.left = new FormAttachment(0, 0);
		toolBar.setLayoutData(fd_toolBar);

		toolNewGroup = new ToolItem(toolBar, SWT.PUSH);
		toolNewGroup.addSelectionListener(new ToolNewGroupSelectionListener());
		toolNewGroup.setToolTipText("�½�Ⱥ��");
		toolNewGroup.setText("��");

		toolEditGroup = new ToolItem(toolBar, SWT.PUSH);
		toolEditGroup.setToolTipText("�༭�û���Ⱥ��");
		toolEditGroup.addSelectionListener(new ToolEditGroupSelectionListener());
		toolEditGroup.setText("��");

		treeViewer = new TreeViewer(this, SWT.BORDER);
		tree = treeViewer.getTree();
		tree.setHeaderVisible(true);
		final FormData fd_tree = new FormData();
		fd_tree.right = new FormAttachment(100, 0);
		fd_tree.top = new FormAttachment(toolBar, 0, SWT.BOTTOM);
		fd_tree.left = new FormAttachment(0, 0);
		fd_tree.bottom = new FormAttachment(100, -5);
		tree.setLayoutData(fd_tree);

		treeColumnName = new TreeColumn(tree, SWT.NONE);
		treeColumnName.setWidth(100);
		treeColumnName.setText("����");

		treeColumnNickName = new TreeColumn(tree, SWT.NONE);
		treeColumnNickName.setWidth(100);
		treeColumnNickName.setText("�ǳ�");

		treeColumnRelation = new TreeColumn(tree, SWT.NONE);
		treeColumnRelation.setWidth(100);
		treeColumnRelation.setText("��ϵ");

		treeColumnCellphone = new TreeColumn(tree, SWT.NONE);
		treeColumnCellphone.setWidth(100);
		treeColumnCellphone.setText("�ֻ�");

		treeColumnEmail = new TreeColumn(tree, SWT.NONE);
		treeColumnEmail.setWidth(100);
		treeColumnEmail.setText("E-mail");

		treeColumnBirth = new TreeColumn(tree, SWT.NONE);
		treeColumnBirth.setWidth(100);
		treeColumnBirth.setText("����");
		
		
		treeViewer.setLabelProvider(new GroupTableTreeLabelProvider());
		contentProvider = new GroupTableTreeContentProvider();
		treeViewer.setContentProvider(contentProvider);

		allGroupsBox = logicCenter.getAllGroupsBox();
		allGroupsBox.addObserver(new AllGroupsRefreshObserver());
	}
	
	class AllGroupsRefreshObserver implements Observer
	{
		@Override
		public void update(Observable o, Object arg)
		{
			AllGroupsBox allGroupsBox = (AllGroupsBox)o;
			List<Group> groupsList = allGroupsBox.getGroups();
			Display.getDefault().syncExec(new AllGroupsRefreshTask(groupsList));
		}
	}
	
	public void setAllContactsBox(AllContactsBox allContactsBox)
	{
		contentProvider.SetAllContactsBox(allContactsBox);
		treeViewer.refresh();
	}
		
	class AllGroupsRefreshTask implements Runnable
	{
		private List<Group> groupsList;
		
		public AllGroupsRefreshTask(List<Group> groupsList)
		{
			this.groupsList = groupsList;
		}

		@Override
		public void run()
		{
			treeViewer.setInput(groupsList);
		}
		
	}

	@Override
	protected void checkSubclass()
	{
		// Disable the check that prevents subclassing of SWT components
	}
	
	/**
	 * �½�Ⱥ��
	 * @author Wander
	 *
	 */
	private class ToolNewGroupSelectionListener extends SelectionAdapter {
		public void widgetSelected(final SelectionEvent e)
		{
			Group newGroup = new Group();
			GroupInfoDialog groupInfoDialog = new GroupInfoDialog(thisComposite.getShell(),"�½�Ⱥ��", GroupInfoTableType.New, newGroup);	
			Permission permission = new Permission();
			groupInfoDialog.setPermission(permission);
			if(groupInfoDialog.open() == IDialogConstants.OK_ID)
			{
				//!TODO: ��������ô���£�; Observerδ������
				CreateGroupResult result = logicCenter.createGroup(newGroup, permission, 0);
				result.addObserver(new CreateGrouopResultObserver());
			}
		}
	}
	
	class CreateGrouopResultObserver implements Observer
	{

		@Override
		public void update(Observable o, Object arg)
		{
			Display.getDefault().syncExec(new CreateGroupResultTask((CreateGroupResult)o));
		}
	}
	
	class CreateGroupResultTask implements Runnable
	{
		private CreateGroupResult result;
		
		public CreateGroupResultTask(CreateGroupResult result)
		{
			this.result = result;
		}

		@Override
		public void run()
		{
			VirtualState state = result.getState();
			if(state == VirtualState.PREPARED)
			{
				MessageDialog.openInformation(getShell(), "�½�Ⱥ�� �ɹ�", "�½�Ⱥ�� �ɹ�");
				treeViewer.refresh();
			}
			else if(state == VirtualState.ERRORED)
			{
				MessageDialog.openWarning(getShell(), "�½�Ⱥ��ʧ��", result.getError().toString());
			}
		}
		
	}
	
	/**
	 * �༭/�鿴Ⱥ��
	 * @author Wander
	 *
	 */
	private class ToolEditGroupSelectionListener extends SelectionAdapter {
		public void widgetSelected(final SelectionEvent e)
		{
			TreeItem currentItem = getCurrentItem();
			if(currentItem == null) return;
			
			Object data = currentItem.getData();
			
			if(data instanceof Group)
			{	
				Group group = (Group)data;
				GroupInfoDialog groupInfoDialog = new GroupInfoDialog(thisComposite.getShell(), "�༭Ⱥ��", GroupInfoTableType.Normal, group); 
				groupInfoDialog.open();
			}
			else if(currentItem.getData() instanceof UserInfo)
			{
				TreeItem parentItem = currentItem.getParentItem();
				Object parentData = parentItem.getData();
				if(!(parentData instanceof Group))
					return;
				
				UserInfo user = (UserInfo)data;
				UserInfoDialog userInfoDialog = new UserInfoDialog(thisComposite.getShell(), "�û���Ϣ", UserInfoTableType.Group, user);
				userInfoDialog.setGroup((Group)parentData);
				userInfoDialog.open();
			}
		}
	}
	
	private TreeItem getCurrentItem()
	{
		TreeItem[] currentItems =  treeViewer.getTree().getSelection();
		if(currentItems != null && currentItems.length > 0)
			return currentItems[0];
		else
			return null;
	}
	
	public AllGroupsBox getAllGroupsBox()
	{
		return allGroupsBox;
	}

}
