package ui_test;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import entity.UserInfo;
import entity.infoField.BaseInfoFieldName;
import entity.infoField.CustomInfoFieldName;
import entity.infoField.InfoField;
import entity.infoField.InfoFieldName;

import ui_test.UserInfoTable.UserInfoCellModifier;
import ui_test.UserInfoTable.UserInfoContentProvider;
import ui_test.UserInfoTable.UserInfoLabelProvider;
import ui_test.UserInfoTable.UserInfoTableElem;
import ui_test.UserInfoTable.UserInfoTableType;

public class UserInfoDialog extends Dialog
{
	private TableColumn tableColumnValue;
	private TableColumn tableColumnField;
	private Table table;
	private TableViewer tableViewer;
	protected Object result;
	protected Shell shell;

	private String dialogTitle;
	private UserInfoTableType userInfoTableType;
	private UserInfo user;
	
	private UserInfo newUser;
	private List<UserInfoTableElem> userInfoTableElems;
	
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
	}

	/**
	 * Create contents of the dialog
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent)
	{
		Composite container = (Composite) super.createDialogArea(parent);

		tableViewer = new TableViewer(container, SWT.BORDER);
		
		table = tableViewer.getTable();
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		tableColumnField = new TableColumn(table, SWT.NONE);
		tableColumnField.setWidth(100);
		tableColumnField.setText("×Ö¶Î");

		tableColumnValue = new TableColumn(table, SWT.NONE);
		tableColumnValue.setWidth(354);
		tableColumnValue.setText("Öµ");

		new TableColumn(table, SWT.NONE);
		
		tableViewer.setContentProvider(new UserInfoContentProvider());
		tableViewer.setLabelProvider(new UserInfoLabelProvider());
		
		generateTableElems();
		tableViewer.setInput(userInfoTableElems);
		
		CellEditor[] cellEditors = new CellEditor[2];
		cellEditors[0] = null;
		cellEditors[1] = (CellEditor) new TextCellEditor(tableViewer.getTable());
		
		tableViewer.setColumnProperties(new String[] {"field", "value"});
		tableViewer.setCellEditors(cellEditors);
		tableViewer.setCellModifier(new UserInfoCellModifier(shell, tableViewer, userInfoTableType));
		
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
		for(String key : user.getBaseInfo().getKeySet())
		{
			InfoField field = user.getBaseInfo().getInfoField(key);
			UserInfoTableElem elem = new UserInfoTableElem(field, userInfoTableType);
			if(elem.IsVisible())
				userInfoTableElems.add(elem);
		}
		for(String key : user.getCustomInfo().getKeySet())
		{
			InfoField field = user.getCustomInfo().getInfoField(key);
			UserInfoTableElem elem = new UserInfoTableElem(field, userInfoTableType);
			if(elem.IsVisible())
				userInfoTableElems.add(elem);
		}
	}
	
	public void modifyUser()
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

}
