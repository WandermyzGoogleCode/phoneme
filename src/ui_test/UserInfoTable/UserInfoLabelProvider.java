package ui_test.UserInfoTable;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

public class UserInfoLabelProvider implements ITableLabelProvider
{

	@Override
	public Image getColumnImage(Object element, int col)
	{
		// TODO ÏÔÊ¾Í·Ïñ
		return null;
	}

	@Override
	public String getColumnText(Object element, int col)
	{
		UserInfoTableElem elem = (UserInfoTableElem)element;
		if(col == 0)
			return elem.GetInfoField().getName();
		else if(col == 1)
			return elem.GetInfoField().getStringValue();
		
		return null;
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
