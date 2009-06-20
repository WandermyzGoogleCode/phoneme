package ui_test.SearchResultTable;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

import entity.BaseUserInfo;
import entity.UserInfo;
import entity.infoField.InfoFieldName;

public class SearchResultTableLabelProvider implements ITableLabelProvider
{

	@Override
	public Image getColumnImage(Object arg0, int arg1)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex)
	{
		if(element instanceof BaseUserInfo)
		{
			BaseUserInfo user = (BaseUserInfo)element;
			switch(columnIndex)
			{
			case 0:
				return user.getInfoField(Messages.getString("SearchResultTableLabelProvider.name")).getStringValue(); //$NON-NLS-1$
			case 1:
				return user.getInfoField(Messages.getString("SearchResultTableLabelProvider.cell")).getStringValue(); //$NON-NLS-1$
			case 2:
				return user.getInfoField(Messages.getString("SearchResultTableLabelProvider.email")).getStringValue(); //$NON-NLS-1$
			case 3:
				return user.getInfoField(Messages.getString("SearchResultTableLabelProvider.birthday")).getStringValue(); //$NON-NLS-1$
			}
		}
		return ""; //$NON-NLS-1$
	}

	@Override
	public void addListener(ILabelProviderListener arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isLabelProperty(Object arg0, String arg1)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener arg0)
	{
		// TODO Auto-generated method stub
		
	}

}
