package ui_test.GroupTableTree;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

import entity.Group;
import entity.UserInfo;
import entity.infoField.InfoFieldName;

public class GroupTableTreeLabelProvider implements ITableLabelProvider
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
		if(element instanceof Group)
		{
			Group group = (Group)element;
			if(columnIndex == 0)
				return group.getName();
			else
				return "";
		}
		else if(element instanceof UserInfo)
		{
			UserInfo user = (UserInfo)element;
			switch(columnIndex)
			{
			case 0:
				return user.getInfoField(InfoFieldName.Name).getStringValue();
			case 1:
				return user.getInfoField(InfoFieldName.NickName).getStringValue();
			case 2:
				return user.getInfoField(InfoFieldName.Relation).getStringValue();
			case 3:
				return user.getInfoField(InfoFieldName.Cellphone).getStringValue();
			case 4:
				return user.getInfoField(InfoFieldName.EmailAddress).getStringValue();
			case 5:
				return user.getInfoField(InfoFieldName.Birthday).getStringValue();
			}
		}
		
		return "";
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
