package ui_test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import logiccenter.LogicCenter;
import logiccenter.LogicCenterImp;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import ui_test.ContactPermissionTable.ContactPermissionTableContentProvider;
import ui_test.ContactPermissionTable.ContactPermissionTableLabelProvider;

import entity.BaseUserInfo;
import entity.Permission;
import entity.UserInfo;
import entity.infoField.InfoField;

public class ContactPermissionComposition extends Composite
{	
	private TableColumn tableColumnValue;
	private TableColumn tableColumnField;
	private Table table;
	private CheckboxTableViewer checkboxTableViewer;
	
	private LogicCenter logicCenter = LogicCenterImp.getInstance();
	private Permission permission;
	
	/**
	 * Create the composite
	 * @param parent
	 * @param style
	 */
	public ContactPermissionComposition(Composite parent, int style, Permission permission)
	{
		super(parent, style);
		setLayout(new FillLayout());

		checkboxTableViewer = CheckboxTableViewer.newCheckList(this, SWT.BORDER);
		table = checkboxTableViewer.getTable();
		table.setLinesVisible(true);
		table.setHeaderVisible(true);

		tableColumnField = new TableColumn(table, SWT.NONE);
		tableColumnField.setWidth(100);
		tableColumnField.setText("×Ö¶Î");

		tableColumnValue = new TableColumn(table, SWT.NONE);
		tableColumnValue.setWidth(100);
		tableColumnValue.setText("Öµ");
		
		checkboxTableViewer.setContentProvider(new ContactPermissionTableContentProvider());
		checkboxTableViewer.setLabelProvider(new ContactPermissionTableLabelProvider());
		
		this.permission = permission;
		BaseUserInfo loginUser = logicCenter.getLoginUser();

		List<InfoField> fieldsList = new ArrayList<InfoField>();
		for(String key : loginUser.getKeySet())
		{
			fieldsList.add(loginUser.getInfoField(key));
		}
		checkboxTableViewer.setInput(fieldsList);

		Object input = checkboxTableViewer.getInput();
		if(input instanceof List)
		{
			List<InfoField> inputFieldsList = (List<InfoField>)input;
			for(InfoField field : inputFieldsList)
			{
				checkboxTableViewer.setChecked(field, permission.getField(field.getName()));
			}
		}
	}
	
	public void ModifyPermission()
	{
		Object input = checkboxTableViewer.getInput();
		if(input instanceof List)
		{
			List<InfoField> fieldsList = (List<InfoField>)input;
			for(InfoField field : fieldsList)
			{
				permission.setField(field.getName(), checkboxTableViewer.getChecked(field));
			}
		}
	}
	
 
	@Override
	protected void checkSubclass()
	{
		// Disable the check that prevents subclassing of SWT components
	}

}
