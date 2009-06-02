package ui_test.GroupInfoTable;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;

import ui_test.GroupTableTree.GroupInfoTableType;

import entity.infoField.InfoField;
import entity.infoField.InfoFieldFactory;

public class GroupInfoCellModifier implements ICellModifier
{
	private TableViewer tableViewer;
	private Shell shell;
	private GroupInfoTableType groupInfoTableType;
	
	public GroupInfoCellModifier(Shell shell,  GroupInfoTableType groupInfoTableType, TableViewer tableViewer)
	{
		this.groupInfoTableType = groupInfoTableType;
		this.tableViewer = tableViewer;
		this.shell = shell;
	}

	@Override
	public boolean canModify(Object element, String property)
	{
		//TODO: Ȩ�޼��
		return property == "value";
	}

	@Override
	public Object getValue(Object element, String property)
	{
		InfoField info = (InfoField)element;
		return (property == "field") ? info.getName() : info.getStringValue();
	}

	@Override
	public void modify(Object element, String property, Object value)
	{
		TableItem item = (TableItem)element;
		InfoField field = (InfoField)item.getData();
		InfoField newField = InfoFieldFactory.getFactory().makeInfoField(field.getName(), (String)value);
		if(newField.getStringValue().equals(value))		
		{
			item.setData(newField);
			tableViewer.refresh(item.getData());
		}
		else
		{
			MessageDialog.openWarning(shell, "��ʽ����", String.format("\"%s\"�ֶθ�ʽ����", newField.getName()));
			//TODO:  �˴����������BUG
		}
		
	}

}
