package ui_test.GroupTableTree;

import java.util.ArrayList;
import java.util.List;

import logiccenter.LogicCenterImp;
import logiccenter.VirtualResult.AllContactsBox;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;

import entity.Group;
import entity.ID;
import entity.UserInfo;

public class GroupTableTreeContentProvider implements ITreeContentProvider
{
	private AllContactsBox allContactsBox = null;

	public void SetAllContactsBox(AllContactsBox allContactsBox)
	{
		this.allContactsBox = allContactsBox;
	}
	
	@Override
	public Object[] getElements(Object inputElement)
	{
		if(inputElement	instanceof List)
		{
			List<Group> elems = (List<Group>)inputElement;
			return elems.toArray();
		}
		
		return new Object[0];
	}
	
	@Override
	public boolean hasChildren(Object element)
	{
		if(!(element instanceof Group)) return false;
		Group group = (Group)element;
		return group.getUsersID().size() > 0;
	}
	
	@Override
	public Object[] getChildren(Object parentElement)
	{
		if(parentElement instanceof Group)
		{
			if(allContactsBox == null) return new Object[0];
			
			Group group = (Group)parentElement;
			List<ID> userIDs = group.getUsersID();
			List<UserInfo> users = new ArrayList<UserInfo>();
			for(ID id : userIDs)
			{
				users.add(allContactsBox.getContactsMap().get(id));	
			}
			
			return users.toArray();
		}
		
		return new Object[0];
	}

	@Override
	public Object getParent(Object arg0)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void dispose()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void inputChanged(Viewer v, Object oldInput, Object newInput)
	{
		// TODO When Input Change
	}

}
