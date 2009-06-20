package ui_test.GroupTableTree;

import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import entity.Group;

public class GroupSearchContentProvider implements ITreeContentProvider {

	@Override
	public Object[] getChildren(Object arg0) {
		return new Object[0];
	}
	
	@Override
	public Object getParent(Object arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasChildren(Object arg0) {
		return false;
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
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void inputChanged(Viewer arg0, Object arg1, Object arg2) {
		// TODO Auto-generated method stub

	}

}
