package ui_test.UserInfoTable;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;

import entity.infoField.InfoField;
import entity.infoField.InfoFieldFactory;

public class UserInfoCellModifier implements ICellModifier
{
	private TableViewer userInfoTable;
	private UserInfoTableType userInfoTableType;
	private Shell shell;
	
	public UserInfoCellModifier(Shell shell, TableViewer userInfoTable, UserInfoTableType tableType)
	{
		this.userInfoTable = userInfoTable;
		this.userInfoTableType = tableType;
		this.shell = shell;
	}
	
	@Override
	public boolean canModify(Object element, String property)
	{
		return property == "value" && ((UserInfoTableElem)element).IsEditable();
	}

	@Override
	public Object getValue(Object element, String property)
	{
		UserInfoTableElem elem = (UserInfoTableElem)element;
		return property == "field" ? elem.GetInfoField().getName() : elem.GetInfoField().getStringValue();
	}

	@Override
	public void modify(Object element, String property, Object value)
	{
		TableItem item = (TableItem)element;
		UserInfoTableElem elem = (UserInfoTableElem)item.getData();
		//String name = elem.GetInfoField().getName();
		
		InfoField newField = InfoFieldFactory.getFactory().makeInfoField(elem.GetInfoField().getName(), (String)value);
		if(newField.getStringValue().equals(value))		
		{
			elem.SetInfoField(newField);
			userInfoTable.refresh(elem);
		}
		else
		{
			MessageDialog.openWarning(shell, "格式错误", String.format("\"%s\"字段格式有误", newField.getName()));
			//TODO:  此处有随机BUG
		}
		//item.setData(elem);
		
		//userInfoTable.update(elem, new String[] {"field","value"});
		
	}
	

}
