package ui_test.ContactPermissionTable;

import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import entity.infoField.InfoField;

public class ContactPermissionTableContentProvider implements IStructuredContentProvider
{

	@Override
	public Object[] getElements(Object inputElements)
	{
		if(inputElements instanceof List)
		{
			List<InfoField> fieldsList = (List<InfoField>)inputElements;
			if(fieldsList.size() > 0)
				return fieldsList.toArray();
			else
				return new Object[0];
		}
		return new Object[0];
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
