package ui_test;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import logiccenter.LogicCenter;
import logiccenter.LogicCenterImp;
import logiccenter.VirtualResult.AllGroupsBox;
import logiccenter.VirtualResult.CreateGroupResult;
import logiccenter.VirtualResult.RelationCubeResult;
import logiccenter.VirtualResult.SearchUserResult;
import logiccenter.VirtualResult.VirtualState;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.TreeColumn;

import entity.BaseUserInfo;
import entity.UserInfo;

import ui_test.GroupComposite.CreateGroupResultTask;
import ui_test.SearchResultTable.SearchResultTableContentProvider;
import ui_test.SearchResultTable.SearchResultTableLabelProvider;
import ui_test.UserInfoTable.UserInfoTableType;

public class SearchComposite extends Composite
{

	private ToolItem toolItemSearch;
	private TableColumn tableColumnName;
	private Table table;
	private TableViewer tableViewer;
	private ToolItem toolItemRelationCube;
	private ToolBar toolBar;
	private TableColumn tableColumnCellphone;
	private TableColumn tableColumnEmail;
	private TableColumn tableColumnBirth;
	
	LogicCenter logicCenter = LogicCenterImp.getInstance();
	/**
	 * Create the composite
	 * @param parent
	 * @param style
	 */
	public SearchComposite(Composite parent, int style)
	{
		super(parent, style);
		setLayout(new GridLayout());

		toolBar = new ToolBar(this, SWT.NONE);

		toolItemSearch = new ToolItem(toolBar, SWT.PUSH);
		toolItemSearch.addSelectionListener(new ToolItemSearchSelectionListener());
		toolItemSearch.setText(Messages.getString("SearchComposite.newSearch")); //$NON-NLS-1$

		toolItemRelationCube = new ToolItem(toolBar, SWT.PUSH);
		toolItemRelationCube.addSelectionListener(new ToolItemRelationCubeSelectionListener());
		toolItemRelationCube.setText(Messages.getString("SearchComposite.realtionCube")); //$NON-NLS-1$

		tableViewer = new TableViewer(this, SWT.BORDER);
		tableViewer.addDoubleClickListener(new TableViewerIDoubleClickListener());
		table = tableViewer.getTable();
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		final GridData gd_table = new GridData(SWT.FILL, SWT.FILL, true, true);
		gd_table.heightHint = 233;
		table.setLayoutData(gd_table);

		tableColumnName = new TableColumn(table, SWT.NONE);
		tableColumnName.setWidth(100);
		tableColumnName.setText(Messages.getString("SearchComposite.name")); //$NON-NLS-1$

		tableColumnCellphone = new TableColumn(table, SWT.NONE);
		tableColumnCellphone.setWidth(100);
		tableColumnCellphone.setText(Messages.getString("SearchComposite.cell")); //$NON-NLS-1$

		tableColumnEmail = new TableColumn(table, SWT.NONE);
		tableColumnEmail.setWidth(100);
		tableColumnEmail.setText(Messages.getString("SearchComposite.email")); //$NON-NLS-1$

		tableColumnBirth = new TableColumn(table, SWT.NONE);
		tableColumnBirth.setWidth(100);
		tableColumnBirth.setText(Messages.getString("SearchComposite.birthday")); //$NON-NLS-1$
		//
		
		tableViewer.setContentProvider(new SearchResultTableContentProvider());
		tableViewer.setLabelProvider(new SearchResultTableLabelProvider());
	}

	@Override
	protected void checkSubclass()
	{
		// Disable the check that prevents subclassing of SWT components
	}
	
	/**
	 * 搜索用户
	 * @author Wander
	 *
	 */
	private class ToolItemSearchSelectionListener extends SelectionAdapter {
		public void widgetSelected(final SelectionEvent e)
		{
			UserInfo user = new UserInfo();
			UserInfoDialog userInfoDialog = new UserInfoDialog(getShell(), Messages.getString("SearchComposite.search"), //$NON-NLS-1$
					UserInfoTableType.SearchRemoteForm, user);
			if(userInfoDialog.OpenEditInfo() == IDialogConstants.OK_ID)
			{
				SearchUserResult result = logicCenter.searchUser(user.getBaseInfo());
				result.addObserver(new SearchUserResultObserver());
			}
		}
	}
	
	class SearchUserResultObserver implements Observer
	{

		@Override
		public void update(Observable o, Object arg)
		{
			Display.getDefault().syncExec(new SearchUserResultTask((SearchUserResult)o));
		}
	}
	
	class SearchUserResultTask implements Runnable
	{
		private SearchUserResult result;
		
		public SearchUserResultTask(SearchUserResult result)
		{
			this.result = result;
		}

		@Override
		public void run()
		{
			VirtualState state = result.getState();
			if(state == VirtualState.PREPARED)
			{
				List<BaseUserInfo> usersList = result.getSearchRes();
				if(usersList.size() > 0)
				{
					tableViewer.setInput(usersList);
					tableViewer.refresh();
				}
				else
				{
					MessageDialog.openInformation(getShell(), Messages.getString("SearchComposite.notFoundfuhe"), Messages.getString("SearchComposite.notFoundfuhe")); //$NON-NLS-1$ //$NON-NLS-2$
				}
			}
			else if(state == VirtualState.ERRORED)
			{
				MessageDialog.openWarning(getShell(), Messages.getString("SearchComposite.searchFailed"), result.getError().toString()); //$NON-NLS-1$
			}
		}
		
	}
	
	/**
	 * 搜索人立方
	 * @author Wander
	 *
	 */
	private class ToolItemRelationCubeSelectionListener extends SelectionAdapter {
		public void widgetSelected(final SelectionEvent e)
		{
			if(tableViewer.getTable().getSelection().length <= 0) return;
			
			TableItem item = tableViewer.getTable().getSelection()[0];
			if(!(item.getData() instanceof BaseUserInfo)) return;
			
			BaseUserInfo user = (BaseUserInfo)item.getData();
			
			BaseUserInfo user2nd = new BaseUserInfo();
			UserInfoDialog userInfoDialog = new UserInfoDialog(getShell(), Messages.getString("SearchComposite.search"), //$NON-NLS-1$
					UserInfoTableType.SearchRemoteForm, new UserInfo(user2nd));
			if(userInfoDialog.OpenEditInfo() == IDialogConstants.OK_ID)
			{
				RelationCubeResult result = logicCenter.relationCube(user.getIdenticalField(), user2nd.getIdenticalField());
				result.addObserver(new RelationCubeResultObserver());
			}
		}
	}
	
	class RelationCubeResultObserver implements Observer
	{

		@Override
		public void update(Observable o, Object arg)
		{
			Display.getDefault().syncExec(new RelationCubeResultTask((RelationCubeResult)o));
		}
	}
	
	class RelationCubeResultTask implements Runnable
	{
		private RelationCubeResult result;
		
		public RelationCubeResultTask(RelationCubeResult result)
		{
			this.result = result;
		}

		@Override
		public void run()
		{
			VirtualState state = result.getState();
			if(state == VirtualState.PREPARED)
			{
				List<BaseUserInfo> usersList = result.getSearchRes();
				if(usersList.size() > 0)
				{
					tableViewer.setInput(usersList);
					tableViewer.refresh();
				}
				else
				{
					MessageDialog.openInformation(getShell(), Messages.getString("SearchComposite.notFoundfuhe"), Messages.getString("SearchComposite.notFoundfuhe")); //$NON-NLS-1$ //$NON-NLS-2$
				}
			}
			else if(state == VirtualState.ERRORED)
			{
				MessageDialog.openWarning(getShell(), Messages.getString("SearchComposite.searchFailed"), result.getError().toString()); //$NON-NLS-1$
			}
		}
		
	}
	
	/**
	 * 显示用户信息
	 * @author Wander
	 *
	 */
	private class TableViewerIDoubleClickListener implements IDoubleClickListener {
		public void doubleClick(final DoubleClickEvent arg0)
		{
			if(tableViewer.getTable().getSelection().length <= 0) return;
			
			TableItem currentItem = tableViewer.getTable().getSelection()[0];
			
			UserInfoDialog userInfoDialog = new UserInfoDialog(getShell(), Messages.getString("SearchComposite.userInfo"), //$NON-NLS-1$
					UserInfoTableType.SearchRemoteResult,
					new UserInfo((BaseUserInfo)currentItem.getData()));
			userInfoDialog.open();
		}
	}

}
