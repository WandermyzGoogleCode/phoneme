package ui_test;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import logiccenter.LogicCenter;
import logiccenter.LogicCenterImp;
import logiccenter.VirtualResult.AllContactsBox;
import logiccenter.VirtualResult.AllGroupsBox;
import logiccenter.VirtualResult.ApplyJoinGroupResult;
import logiccenter.VirtualResult.CreateGroupResult;
import logiccenter.VirtualResult.SearchGroupResult;
import logiccenter.VirtualResult.SetVisibilityResult;
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

import ui_test.GroupInfoTable.GroupInfoTableType;
import ui_test.GroupTableTree.GroupSearchContentProvider;
import ui_test.GroupTableTree.GroupSearchLabelProvider;
import ui_test.GroupTableTree.GroupTableTreeContentProvider;
import ui_test.GroupTableTree.GroupTableTreeLabelProvider;
import ui_test.SearchResultTable.SearchResultTableLabelProvider;
import ui_test.UserInfoTable.UserInfoTableType;
import ui_test.utility.VirtualResultObserver;

import entity.Group;
import entity.Permission;
import entity.UserInfo;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;

public class GroupComposite extends Composite
{

	private ToolItem toolEditGroup;
	private ToolItem toolNewGroup;
	private ToolBar toolBar;
	private GroupTableTreeContentProvider contentProvider;
	private GroupSearchContentProvider searchContentProvider;
	
	private GroupComposite thisComposite = this;
	
	private AllGroupsBox allGroupsBox = null;
	
	private LogicCenter logicCenter = LogicCenterImp.getInstance();
	private ToolItem toolItemVisibility;
	private TabFolder tabFolder;
	private TabItem tabItemMyGroup;
	private Tree tree;
	private TreeViewer treeViewer;
	private Button buttonSearchGroup;
	private TabItem tabItemSearchResult;
	private Tree treeSearchResult;
	private TreeViewer treeViewerSearchResult;
	private TreeColumn treeColumn_1;
	private TreeColumn treeColumn_2;
	private TreeColumn treeColumn_4;
	private TreeColumn treeColumn_5;
	private TreeColumn treeColumn_6;
	private TreeColumn treeColumn_7;
	private TreeColumn treeColumn;
	private TreeColumn treeColumn_8;
	private ToolItem toolItemApplyJoin;
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
		fd_toolBar.right = new FormAttachment(100, -445);
		fd_toolBar.top = new FormAttachment(0, 0);
		fd_toolBar.left = new FormAttachment(0, 0);
		toolBar.setLayoutData(fd_toolBar);

		toolNewGroup = new ToolItem(toolBar, SWT.PUSH);
		toolNewGroup.addSelectionListener(new ToolNewGroupSelectionListener());
		toolNewGroup.setToolTipText(Messages.getString("GroupComposite.0")); //$NON-NLS-1$
		toolNewGroup.setText(Messages.getString("GroupComposite.1")); //$NON-NLS-1$

		toolEditGroup = new ToolItem(toolBar, SWT.PUSH);
		toolEditGroup.setToolTipText(Messages.getString("GroupComposite.2")); //$NON-NLS-1$
		toolEditGroup.addSelectionListener(new ToolEditGroupSelectionListener());
		toolEditGroup.setText(Messages.getString("GroupComposite.3")); //$NON-NLS-1$
		
		toolItemVisibility = new ToolItem(toolBar, SWT.NONE);
		toolItemVisibility.addSelectionListener(new VisibilitySelectionListener());
		toolItemVisibility.setToolTipText(Messages.getString("GroupComposite.4")); //$NON-NLS-1$
		toolItemVisibility.setText(Messages.getString("GroupComposite.5")); //$NON-NLS-1$
		
		tabFolder = new TabFolder(this, SWT.NONE);
		{
			FormData formData = new FormData();
			formData.top = new FormAttachment(toolBar, 6);
			formData.bottom = new FormAttachment(100);
			formData.left = new FormAttachment(0);
			formData.right = new FormAttachment(100);
			tabFolder.setLayoutData(formData);
		}
		
		tabItemMyGroup = new TabItem(tabFolder, SWT.NONE);
		tabItemMyGroup.setText(Messages.getString("GroupComposite.6")); //$NON-NLS-1$
		
		treeViewer = new TreeViewer(tabFolder, SWT.BORDER);
		tree = treeViewer.getTree();
		tree.setHeaderVisible(true);
		tabItemMyGroup.setControl(tree);
		
		treeColumn_1 = new TreeColumn(tree, SWT.NONE);
		treeColumn_1.setWidth(92);
		treeColumn_1.setText(Messages.getString("GroupComposite.7")); //$NON-NLS-1$
		
		treeColumn_4 = new TreeColumn(tree, SWT.NONE);
		treeColumn_4.setWidth(72);
		treeColumn_4.setText(Messages.getString("GroupComposite.8")); //$NON-NLS-1$
		
		treeColumn_5 = new TreeColumn(tree, SWT.NONE);
		treeColumn_5.setWidth(100);
		treeColumn_5.setText(Messages.getString("GroupComposite.9")); //$NON-NLS-1$
		
		treeColumn_7 = new TreeColumn(tree, SWT.NONE);
		treeColumn_7.setWidth(100);
		treeColumn_7.setText(Messages.getString("GroupComposite.10")); //$NON-NLS-1$
		
		treeColumn_6 = new TreeColumn(tree, SWT.NONE);
		treeColumn_6.setWidth(100);
		treeColumn_6.setText(Messages.getString("GroupComposite.11")); //$NON-NLS-1$
		
		treeColumn_2 = new TreeColumn(tree, SWT.NONE);
		treeColumn_2.setWidth(84);
		treeColumn_2.setText(Messages.getString("GroupComposite.12")); //$NON-NLS-1$
		
		tabItemSearchResult = new TabItem(tabFolder, SWT.NONE);
		tabItemSearchResult.setText(Messages.getString("GroupComposite.13")); //$NON-NLS-1$
		
		treeViewerSearchResult = new TreeViewer(tabFolder, SWT.BORDER);
		treeSearchResult = treeViewerSearchResult.getTree();
		treeSearchResult.setHeaderVisible(true);
		tabItemSearchResult.setControl(treeSearchResult);
		
		treeColumn = new TreeColumn(treeSearchResult, SWT.NONE);
		treeColumn.setWidth(100);
		treeColumn.setText(Messages.getString("GroupComposite.14")); //$NON-NLS-1$
		
		treeColumn_8 = new TreeColumn(treeSearchResult, SWT.NONE);
		treeColumn_8.setWidth(512);
		treeColumn_8.setText(Messages.getString("GroupComposite.15")); //$NON-NLS-1$
		
		buttonSearchGroup = new Button(this, SWT.NONE);
		buttonSearchGroup.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Group g = new Group();
				GroupInfoDialog dialog = new GroupInfoDialog(getShell(), Messages.getString("GroupComposite.16"), GroupInfoTableType.Search, g); //$NON-NLS-1$
				if (dialog.open() == IDialogConstants.OK_ID){
					SearchGroupResult result = logicCenter.searchGroup(g);
					SearchResultObserver observer = new SearchResultObserver();
					result.addObserver(observer);
				}
			}
		});
		{
			FormData formData = new FormData();
			formData.top = new FormAttachment(toolBar, 0, SWT.TOP);
			
			toolItemApplyJoin = new ToolItem(toolBar, SWT.NONE);
			toolItemApplyJoin.addSelectionListener(new ApplyJoinGroupListener());
			toolItemApplyJoin.setToolTipText(Messages.getString("GroupComposite.17")); //$NON-NLS-1$
			toolItemApplyJoin.setText(Messages.getString("GroupComposite.18")); //$NON-NLS-1$
			formData.right = new FormAttachment(100, -10);
			buttonSearchGroup.setLayoutData(formData);
		}
		buttonSearchGroup.setText(Messages.getString("GroupComposite.19")); //$NON-NLS-1$
		treeViewer.setLabelProvider(new GroupTableTreeLabelProvider());
		contentProvider = new GroupTableTreeContentProvider();
		treeViewer.setContentProvider(contentProvider);
		
		treeViewerSearchResult.setLabelProvider(new GroupSearchLabelProvider());
		searchContentProvider = new GroupSearchContentProvider();
		treeViewerSearchResult.setContentProvider(searchContentProvider);

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
			treeViewer.refresh();
		}
		
	}

	@Override
	protected void checkSubclass()
	{
		// Disable the check that prevents subclassing of SWT components
	}
	
	/**
	 * 申请加入群组
	 * @author Administrator
	 *
	 */
	private class ApplyJoinGroupListener extends SelectionAdapter{
		@Override
		public void widgetSelected(SelectionEvent e) {
			if (tabFolder.getSelectionIndex() == tabFolder.indexOf(tabItemMyGroup)){
				MessageDialog.openError(getShell(), Messages.getString("GroupComposite.20"), Messages.getString("GroupComposite.21")); //$NON-NLS-1$ //$NON-NLS-2$
				return;
			}
			TreeItem currentItem = getCurrentItem();
			if (currentItem == null || !(currentItem.getData() instanceof Group)){
				MessageDialog.openError(getShell(), Messages.getString("GroupComposite.22"), Messages.getString("GroupComposite.23")); //$NON-NLS-1$ //$NON-NLS-2$
				return;
			}
			Group g = (Group)currentItem.getData();
			Permission p = new Permission();
			int visibility;
			SingleNumDialog visDialog = new SingleNumDialog(getShell());
			if (visDialog.open() != IDialogConstants.OK_ID)
				return;
			visibility = visDialog.getResult();
			GroupInfoDialog perDialog = new GroupInfoDialog(getShell(), Messages.getString("GroupComposite.24"), GroupInfoTableType.Admit, g); //$NON-NLS-1$
			perDialog.setPermission(p);
			if (perDialog.open() == IDialogConstants.OK_ID){
				ApplyJoinGroupResult result = logicCenter.applyJoinGroup(g.getID(), p, visibility);
				VirtualResultObserver observer = new VirtualResultObserver(getShell(), Messages.getString("GroupComposite.25")); //$NON-NLS-1$
				result.addObserver(observer);
			}
		}
	}
	
	/**
	 * 设置可见度
	 * @author Administrator
	 *
	 */
	private class VisibilitySelectionListener extends SelectionAdapter{
		@Override
		public void widgetSelected(SelectionEvent e) {
			if (tabFolder.getSelectionIndex() != tabFolder.indexOf(tabItemMyGroup)){
				MessageDialog.openError(getShell(), Messages.getString("GroupComposite.26"), Messages.getString("GroupComposite.27")); //$NON-NLS-1$ //$NON-NLS-2$
				return;
			}
			TreeItem currentItem = getCurrentItem();
			if (currentItem == null || !(currentItem.getData() instanceof Group)){
				MessageDialog.openError(getShell(), Messages.getString("GroupComposite.28"), Messages.getString("GroupComposite.29")); //$NON-NLS-1$ //$NON-NLS-2$
				return;
			}
			Group g = (Group)currentItem.getData();
			SingleNumDialog dialog = new SingleNumDialog(getShell());
			if (dialog.open() == IDialogConstants.OK_ID){
				SetVisibilityResult result = logicCenter.setVisibility(g.getID(), dialog.getResult());
				VirtualResultObserver observer = new VirtualResultObserver(getShell(), Messages.getString("GroupComposite.30")); //$NON-NLS-1$
				result.addObserver(observer);
			}
		}
	}
	
	/**
	 * 新建群组
	 * @author Wander
	 *
	 */
	private class ToolNewGroupSelectionListener extends SelectionAdapter {
		public void widgetSelected(final SelectionEvent e)
		{
			Group newGroup = new Group();
			GroupInfoDialog groupInfoDialog = new GroupInfoDialog(thisComposite.getShell(),Messages.getString("GroupComposite.31"), GroupInfoTableType.New, newGroup);	 //$NON-NLS-1$
			Permission permission = new Permission();
			groupInfoDialog.setPermission(permission);
			if(groupInfoDialog.open() == IDialogConstants.OK_ID)
			{
				//!TODO: 参数表怎么回事？; Observer未被调用
				CreateGroupResult result = logicCenter.createGroup(newGroup, permission, 0);
				result.addObserver(new CreateGroupResultObserver());
			}
		}
	}
	
	class CreateGroupResultObserver implements Observer
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
				MessageDialog.openInformation(getShell(), Messages.getString("GroupComposite.32"), Messages.getString("GroupComposite.33")); //$NON-NLS-1$ //$NON-NLS-2$
				treeViewer.refresh();
			}
			else if(state == VirtualState.ERRORED)
			{
				MessageDialog.openWarning(getShell(), Messages.getString("GroupComposite.34"), result.getError().toString()); //$NON-NLS-1$
			}
		}
		
	}
	
	/**
	 * 编辑/查看群组
	 * @author Wander
	 *
	 */
	private class ToolEditGroupSelectionListener extends SelectionAdapter {
		public void widgetSelected(final SelectionEvent e)
		{
			if (tabFolder.getSelectionIndex() != tabFolder.indexOf(tabItemMyGroup)){
				MessageDialog.openError(getShell(), Messages.getString("GroupComposite.35"), Messages.getString("GroupComposite.36")); //$NON-NLS-1$ //$NON-NLS-2$
				return;
			}
			TreeItem currentItem = getCurrentItem();
			if(currentItem == null) return;
			
			Object data = currentItem.getData();
			
			if(data instanceof Group)
			{	
				Group group = (Group)data;
				Permission permission = allGroupsBox.getPermission(group.getID());
				GroupInfoDialog groupInfoDialog = new GroupInfoDialog(thisComposite.getShell(), Messages.getString("GroupComposite.37"), GroupInfoTableType.Normal, group);  //$NON-NLS-1$
				groupInfoDialog.setPermission(permission);
				groupInfoDialog.open();
			}
			else if(currentItem.getData() instanceof UserInfo)
			{
				TreeItem parentItem = currentItem.getParentItem();
				Object parentData = parentItem.getData();
				if(!(parentData instanceof Group))
					return;
				
				UserInfo user = (UserInfo)data;
				UserInfoDialog userInfoDialog = new UserInfoDialog(thisComposite.getShell(), Messages.getString("GroupComposite.38"), UserInfoTableType.Group, user); //$NON-NLS-1$
				userInfoDialog.setGroup((Group)parentData);
				userInfoDialog.open();
			}
		}
	}
	
	private TreeItem getCurrentItem()
	{
		TreeItem[] currentItems = null;
		if (tabFolder.getSelectionIndex() == tabFolder.indexOf(tabItemMyGroup))
			currentItems =  treeViewer.getTree().getSelection();
		else if (tabFolder.getSelectionIndex() == tabFolder.indexOf(tabItemSearchResult))
			currentItems = treeViewerSearchResult.getTree().getSelection();
		if(currentItems != null && currentItems.length > 0)
			return currentItems[0];
		else
			return null;
	}
	
	public AllGroupsBox getAllGroupsBox()
	{
		return allGroupsBox;
	}

	/**
	 * 显示搜索结果 Observer
	 * @author Wander
	 *
	 */
	class SearchResultObserver implements Observer
	{

		@Override
		public void update(Observable o, Object arg)
		{
			SearchGroupResult result = (SearchGroupResult)o;
			if (result.getState() == VirtualState.PREPARED)
				Display.getDefault().syncExec(new SearchResultTask(result.getSearchRes()));
			else if (result.getState() == VirtualState.ERRORED)
				MessageDialog.openError(getShell(), Messages.getString("GroupComposite.39"), result.getError().toString()); //$NON-NLS-1$
		}
	}
	
	/**
	 * 显示搜索结果 Task
	 * @author Wander
	 *
	 */
	class SearchResultTask implements Runnable
	{
		private List<Group> groups;
		
		public SearchResultTask(List<Group> groups)
		{
			this.groups = groups;
		}
		
		@Override
		public void run()
		{
			treeViewerSearchResult.setInput(groups);
			treeViewerSearchResult.refresh();
			tabFolder.setSelection(tabItemSearchResult);
		}
		
	}
}
