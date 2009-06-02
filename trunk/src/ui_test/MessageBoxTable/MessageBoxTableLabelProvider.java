package ui_test.MessageBoxTable;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

import entity.message.Message;

public class MessageBoxTableLabelProvider implements ITableLabelProvider
{

	@Override
	public Image getColumnImage(Object arg0, int arg1)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getColumnText(Object element, int col)
	{
		if(!(element instanceof Message)) return "";
		
		Message msg = (Message)element;
		switch(col)
		{
		case 0:
			return msg.proceeded()?"已处理":"未处理";
		case 1:
			return msg.title();
		case 2:
			return msg.detail();
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
