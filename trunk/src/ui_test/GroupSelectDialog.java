package ui_test;

import java.util.ArrayList;
import java.util.List;

import logiccenter.LogicCenter;
import logiccenter.LogicCenterImp;
import logiccenter.VirtualResult.AllGroupsBox;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import entity.Group;
import entity.ID;

public class GroupSelectDialog extends Dialog
{

	private Label label;
	private Combo combo;

	private AllGroupsBox allGroupsBox;
	private List<Long> groupsIdInCombo = new ArrayList<Long>(); 
	private Group selectedGroup = null;
	/**
	 * Create the dialog
	 * @param parentShell
	 */
	public GroupSelectDialog(Shell parentShell, AllGroupsBox allGroupsBox)
	{
		super(parentShell);
		this.allGroupsBox = allGroupsBox;
	}

	/**
	 * Create contents of the dialog
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent)
	{
		Composite container = (Composite) super.createDialogArea(parent);

		label = new Label(container, SWT.NONE);
		label.setText("选择群组");

		combo = new Combo(container, SWT.NONE);
		combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		//
		
		for(Group group : allGroupsBox.getGroups())
		{
			groupsIdInCombo.add(group.getID().getValue());
			combo.add(group.getName(), groupsIdInCombo.size()-1);
		}
		combo.select(0);
		
		return container;
	}

	/**
	 * Create contents of the button bar
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent)
	{
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
	}

	/**
	 * Return the initial size of the dialog
	 */
	@Override
	protected Point getInitialSize()
	{
		return new Point(252, 157);
	}
	protected void buttonPressed(int buttonId)
	{
		if (buttonId == IDialogConstants.OK_ID) {
			int index = combo.getSelectionIndex();
			
			if(index == -1)
			{
				MessageDialog.openWarning(getShell(), "请选择一个群组", "请选择一个群组");
				return;
			}
			
			long id = groupsIdInCombo.get(index);
			selectedGroup = allGroupsBox.getGroupMap().get(new ID(id));
		}
		super.buttonPressed(buttonId);
	}
	
	public Group getSelectedGroup()
	{
		return selectedGroup;
	}

}
