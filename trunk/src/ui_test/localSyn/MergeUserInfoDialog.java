package ui_test.localSyn;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ScrollBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.layout.GridLayout;

import entity.BaseUserInfo;
import entity.ID;
import entity.UserInfo;
import entity.infoField.BaseInfoFieldName;
import entity.infoField.InfoField;
import entity.infoField.InfoFieldFactory;
import entity.infoField.InfoFieldName;
import ui_test.UserInfoTable.UserInfoContentProvider;
import ui_test.UserInfoTable.UserInfoLabelProvider;
import ui_test.UserInfoTable.UserInfoTableElem;
import ui_test.UserInfoTable.UserInfoTableType;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.CheckStateChangedEvent;

public class MergeUserInfoDialog extends Dialog {
	public class VerticalBarSelectionListener implements SelectionListener {
		private Table table;
		
		public VerticalBarSelectionListener(Table table) {
			this.table = table;
		}

		@Override
		public void widgetDefaultSelected(SelectionEvent arg0) {
			table1.setTopIndex(table.getTopIndex());
			table2.setTopIndex(table.getTopIndex());
		}

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			table1.setTopIndex(table.getTopIndex());
			table2.setTopIndex(table.getTopIndex());
		}

	}

	private static final int GrayConstant = 240;
	private List<Boolean> editable;
	private Table table1;
	private Table table2;
	private UserInfo user1, user2;
	List<UserInfoTableElem> userInfoTableElems1 = new ArrayList<UserInfoTableElem>();
	List<UserInfoTableElem> userInfoTableElems2 = new ArrayList<UserInfoTableElem>();
	private CheckboxTableViewer tableViewer1;
	private CheckboxTableViewer tableViewer2;

	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public MergeUserInfoDialog(Shell parentShell, UserInfo user1, UserInfo user2) {
		super(parentShell);
		this.user1 = user1;
		this.user2 = user2;
	}

	private void generateTableElems(List<UserInfoTableElem> userInfoTableElems, UserInfo user) {
		if (user.getBaseInfo() != null) {
			for (String key : user.getBaseInfo().getKeySet()) {
				InfoField field = user.getBaseInfo().getInfoField(key);
				if (!field.editable())
					continue;
				UserInfoTableElem elem = new UserInfoTableElem(field,
						UserInfoTableType.Merge);
				if (elem.IsVisible())
					userInfoTableElems.add(elem);
			}
		}
		if (user.getCustomInfo() != null) {
			for (String key : user.getCustomInfo().getKeySet()) {
				InfoField field = user.getCustomInfo().getInfoField(key);
				if (!field.editable())
					continue;
				UserInfoTableElem elem = new UserInfoTableElem(field,
						UserInfoTableType.Merge);
				if (elem.IsVisible())
					userInfoTableElems.add(elem);
			}
		}
	}
	
	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		GridLayout gridLayout = (GridLayout) container.getLayout();
		gridLayout.numColumns = 2;
		{
			Label label = new Label(container, SWT.NONE);
			label.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
			label.setText("\u8BF7\u9009\u62E9\u8BE5\u8054\u7CFB\u4EBA\u5404\u5B57\u6BB5\u4EE5\u54EA\u4E2A\u503C\u4E3A\u51C6\uFF08\u53EA\u6709\u84DD\u8272\u53EF\u7F16\u8F91\uFF09");
		}
		{
			tableViewer1 = CheckboxTableViewer.newCheckList(container, SWT.BORDER | SWT.FULL_SELECTION);
			tableViewer1.addCheckStateListener(new ICheckStateListener() {
				public void checkStateChanged(CheckStateChangedEvent arg0) {
					for(int i=0; i<tableViewer1.getTable().getItemCount(); i++){
						Object elem = tableViewer1.getElementAt(i);
						boolean checked = tableViewer1.getChecked(elem);
						if (!editable.get(i))
							checked = true;
						tableViewer1.setChecked(elem, checked);
						tableViewer2.setChecked(tableViewer2.getElementAt(i), !checked);
					}
				}
			});
			tableViewer1.setColumnProperties(new String[] {"field", "value"});
			table1 = tableViewer1.getTable();
			table1.addSelectionListener(new VerticalBarSelectionListener(table1));
			table1.getVerticalBar().addSelectionListener(new VerticalBarSelectionListener(table1));
			table1.setLinesVisible(true);
			table1.setHeaderVisible(true);
			table1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
			{
				TableColumn tableColumn = new TableColumn(table1, SWT.NONE);
				tableColumn.setWidth(100);
				tableColumn.setText("\u5B57\u6BB5");
			}
			{
				TableColumn tableColumn = new TableColumn(table1, SWT.NONE);
				tableColumn.setWidth(120);
				tableColumn.setText("\u503C1");
			}
			tableViewer1.setLabelProvider(new UserInfoLabelProvider());
			tableViewer1.setContentProvider(new UserInfoContentProvider());
			tableViewer1.setInput(userInfoTableElems1);
		}
		{
			tableViewer2 = CheckboxTableViewer.newCheckList(container, SWT.BORDER | SWT.FULL_SELECTION);
			tableViewer2.addCheckStateListener(new ICheckStateListener() {
				public void checkStateChanged(CheckStateChangedEvent arg0) {
					for(int i=0; i<tableViewer2.getTable().getItemCount(); i++){
						Object elem = tableViewer2.getElementAt(i);
						boolean checked = tableViewer2.getChecked(elem);
						if (!editable.get(i))
							checked = false;
						tableViewer1.setChecked(tableViewer1.getElementAt(i),!checked);
						tableViewer2.setChecked(elem, checked);
					}
				}
			});
			tableViewer2.setColumnProperties(new String[] {"field", "value"});
			table2 = tableViewer2.getTable();
			table1.addSelectionListener(new VerticalBarSelectionListener(table2));
			table2.getVerticalBar().addSelectionListener(new VerticalBarSelectionListener(table2));
			table2.setLinesVisible(true);
			table2.setHeaderVisible(true);
			table2.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
			{
				TableColumn tableColumn = new TableColumn(table2, SWT.NONE);
				tableColumn.setWidth(100);
				tableColumn.setText("\u5B57\u6BB5");
			}
			{
				TableColumn tableColumn = new TableColumn(table2, SWT.NONE);
				tableColumn.setWidth(120);
				tableColumn.setText("\u503C2");
			}
			tableViewer2.setLabelProvider(new UserInfoLabelProvider());
			tableViewer2.setContentProvider(new UserInfoContentProvider());
			tableViewer2.setInput(userInfoTableElems2);
		}
		for(int i=0; i<userInfoTableElems1.size(); i++){
			UserInfoTableElem elem1 = userInfoTableElems1.get(i);
			UserInfoTableElem elem2 = userInfoTableElems2.get(i);
			if (!editable.get(i)){
//				tableViewer1.getTable().getItem(i).setBackground(new Color(Display.getDefault(), GrayConstant, GrayConstant, GrayConstant));
//				tableViewer2.getTable().getItem(i).setBackground(new Color(Display.getDefault(), GrayConstant, GrayConstant, GrayConstant));
			}
			else{
				tableViewer1.getTable().getItem(i).setForeground(new Color(Display.getDefault(), 0, 0, 255));
				tableViewer2.getTable().getItem(i).setForeground(new Color(Display.getDefault(), 0, 0, 255));
			}
			if (elem2.GetInfoField().isEmpty() || !elem1.GetInfoField().isEmpty() || 
					!editable.get(i))
				tableViewer1.setChecked(elem1, true);
			else
				tableViewer2.setChecked(elem2, true);
		}

		return container;
	}

	/**
	 * Create contents of the button bar.
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}

	@Override
	public int open() {
		boolean allOK = true;
		editable = new ArrayList<Boolean>();
		generateTableElems(userInfoTableElems1, user1);
		generateTableElems(userInfoTableElems2, user2);
		for(int i=0; i<userInfoTableElems1.size(); i++){
			UserInfoTableElem elem1 = userInfoTableElems1.get(i);
			UserInfoTableElem elem2 = userInfoTableElems2.get(i);
			if (user1.hasRelation() && (BaseInfoFieldName.contains(((UserInfoTableElem) elem1).GetInfoField().getName()))
					|| elem1.GetInfoField().getStringValue().equals(elem2.GetInfoField().getStringValue())){
				editable.add(false);
			}
			else{
				allOK = false;
				editable.add(true);
			}
		}
		if (allOK)
			return IDialogConstants.OK_ID;
		return super.open();
	}
	
	@Override
	protected void buttonPressed(int buttonId) {
		if (buttonId == IDialogConstants.OK_ID){
			for(int i=0; i<userInfoTableElems2.size(); i++)
				if (tableViewer2.getChecked(userInfoTableElems2.get(i)))
					user1.setInfoField(userInfoTableElems2.get(i).GetInfoField());
		}
		super.buttonPressed(buttonId);
	}
	
	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(561, 391);
	}
	
}
