package ui_test.MessageBoxTable;

import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import entity.message.Message;

public class MessageBoxTableContentProvider implements IStructuredContentProvider
{

	@Override
	public Object[] getElements(Object element)
	{
		if(element instanceof List)
		{
			List<Message> msgList = (List<Message>)element;
			if(msgList.size() > 0)
				return msgList.toArray();
			else
				return new Object[0];
		}
		return null;
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
