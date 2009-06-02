package ui_test.GroupInfoTable;

import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import entity.infoField.InfoField;

public class GroupInfoTableContentProvider implements IStructuredContentProvider
{

	@Override
	public Object[] getElements(Object element)
	{
		if (element instanceof List)
		{
			return ((List<InfoField>) element).toArray();
		}
		else
		{
			return new Object[0];
		}
	}

	@Override
	public void dispose()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void inputChanged(Viewer arg0, Object arg1, Object arg2)
	{
		// TODO Auto-generated method stub
		
	}

}
