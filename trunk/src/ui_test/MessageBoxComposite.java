package ui_test;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import logiccenter.LogicCenter;
import logiccenter.LogicCenterImp;
import logiccenter.VirtualResult.MessageBox;
import logiccenter.VirtualResult.VirtualState;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
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
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import ui_test.MessageBoxTable.MessageBoxTableContentProvider;
import ui_test.MessageBoxTable.MessageBoxTableLabelProvider;

import entity.MyRemoteException;
import entity.message.Message;

public class MessageBoxComposite extends Composite
{

	private ToolItem toolItemRemove;
	private ToolItem toolItemProceed;
	private TableColumn tableColumnDetail;
	private TableColumn tableColumnTitle;
	private TableColumn tableColumnSate;
	private Table table;
	private TableViewer tableViewer;
	private ToolBar toolBar;
	
	private LogicCenter logicCenter = LogicCenterImp.getInstance();
	private MessageBox messageBox; 
	
	/**
	 * Create the composite
	 * @param parent
	 * @param style
	 */
	public MessageBoxComposite(Composite parent, int style)
	{
		super(parent, style);
		setLayout(new GridLayout());

		toolBar = new ToolBar(this, SWT.NONE);
		toolBar.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		toolItemProceed = new ToolItem(toolBar, SWT.PUSH);
		toolItemProceed.addSelectionListener(new ToolItemProceedSelectionListener());
		toolItemProceed.setText("处理");

		toolItemRemove = new ToolItem(toolBar, SWT.PUSH);
		toolItemRemove.addSelectionListener(new ToolItemRemoveSelectionListener());
		toolItemRemove.setText("删除");

		tableViewer = new TableViewer(this, SWT.BORDER);
		tableViewer.addSelectionChangedListener(new TableViewerISelectionChangedListener());
		table = tableViewer.getTable();
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		tableColumnSate = new TableColumn(table, SWT.NONE);
		tableColumnSate.setWidth(92);
		tableColumnSate.setText("处理状态");

		tableColumnTitle = new TableColumn(table, SWT.NONE);
		tableColumnTitle.setWidth(100);
		tableColumnTitle.setText("消息标题");

		tableColumnDetail = new TableColumn(table, SWT.NONE);
		tableColumnDetail.setWidth(283);
		tableColumnDetail.setText("消息内容");
		//
		
		tableViewer.setContentProvider(new MessageBoxTableContentProvider());
		tableViewer.setLabelProvider(new MessageBoxTableLabelProvider());
		
	}
	
	class MessageBoxRefreshObserver implements Observer
	{

		@Override
		public void update(Observable o, Object arg)
		{
			Display.getDefault().syncExec(new MessageBoxRefreshTask((MessageBox)o));
		}
	}
	
	class MessageBoxRefreshTask implements Runnable
	{
		private MessageBox messageBox;

		public MessageBoxRefreshTask(MessageBox messageBox)
		{
			this.messageBox = messageBox;
		}
		
		@Override
		public void run()
		{
			VirtualState state = messageBox.getState();
			
			if(state == VirtualState.PREPARED)
			{
				List<Message> msgList = messageBox.getMessages();
				
				for(Message msg : msgList)
				{
					if(msg.autoProceed())
					{
						try
						{
							msg.proceed(logicCenter);
						} catch (Exception e)
						{
							MessageDialog.openWarning(getShell(), "自动处理失败", e.getMessage());
							e.printStackTrace();
						}
						
						MessageDialog.openInformation(getShell(), msg.title(), msg.detail());
						msg.remove(logicCenter);
					}
				}
				
				tableViewer.setInput(messageBox.getMessages());
				tableViewer.refresh();
			}
			else if(state == VirtualState.ERRORED)
			{
				MessageDialog.openWarning(getShell(), "刷新MessageBox失败", messageBox.getError().toString());
			}
		}
		
	}

	@Override
	protected void checkSubclass()
	{
		// Disable the check that prevents subclassing of SWT components
	}
	
	/**
	 * 当消息被选择时
	 * @author Wander
	 *
	 */
	private class TableViewerISelectionChangedListener implements ISelectionChangedListener {
		public void selectionChanged(final SelectionChangedEvent arg0)
		{
			if(tableViewer.getTable().getSelection().length <= 0) return;
			TableItem currentItem = tableViewer.getTable().getSelection()[0];
			Message msg = (Message)currentItem.getData();
			toolItemProceed.setText(msg.proceedName());
		}
	}
	
	/**
	 * 处理
	 * @author Wander
	 *
	 */
	private class ToolItemProceedSelectionListener extends SelectionAdapter {
		public void widgetSelected(final SelectionEvent e)
		{
			if(tableViewer.getTable().getSelection().length <= 0) return;
			TableItem currentItem = tableViewer.getTable().getSelection()[0];
			Message msg = (Message)currentItem.getData();
			try
			{
				msg.proceed(logicCenter);
			} catch (Exception e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
				MessageDialog.openWarning(getShell(), "处理失败", e1.getMessage());
			}
		}
	}
	private class ToolItemRemoveSelectionListener extends SelectionAdapter {
		public void widgetSelected(final SelectionEvent e)
		{			
			if(tableViewer.getTable().getSelection().length <= 0) return;
			TableItem currentItem = tableViewer.getTable().getSelection()[0];
			Message msg = (Message)currentItem.getData();
			
			if(MessageDialog.openConfirm(getShell(), "确认删除", "确实要删除此消息吗？"))
			{
				logicCenter.removeMessage(msg);	
				//TODO: Error处理
				
			}
		}
	}
	
	public void GetMessage()
	{
		messageBox = logicCenter.getMessageBox();
		messageBox.addObserver(new MessageBoxRefreshObserver());
	}

}
