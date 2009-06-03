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
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import entity.Group;
import entity.ID;

public class GroupSelectDialog extends Dialog
{

	private Text textMessage;
	private Label labelMessage;
	private Label labelSelect;
	private Combo combo;

	private List<Long> groupsIdInCombo = new ArrayList<Long>(); 
	private Group selectedGroup = null;
	private String message = null;
	private AllGroupsBox allGroupsBox;
	private LogicCenter logicCenter = LogicCenterImp.getInstance();
	/**
	 * Create the dialog
	 * @param parentShell
	 */
	public GroupSelectDialog(Shell parentShell)
	{
		super(parentShell);
	}

	/**
	 * Create contents of the dialog
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent)
	{
		Composite container = (Composite) super.createDialogArea(parent);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		container.setLayout(gridLayout);

		labelSelect = new Label(container, SWT.NONE);
		labelSelect.setText("选择群组");

		combo = new Combo(container, SWT.NONE);
		combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		//
		
		allGroupsBox = logicCenter.getAllGroupsBox();
		
		for(Group group : allGroupsBox.getGroups())
		{
			groupsIdInCombo.add(group.getID().getValue());
			combo.add(group.getName(), groupsIdInCombo.size()-1);
		}
		combo.select(0);

		labelMessage = new Label(container, SWT.NONE);
		labelMessage.setText("消息");

		textMessage = new Text(container, SWT.BORDER);
		final GridData gd_textMessage = new GridData(SWT.FILL, SWT.CENTER, true, false);
		textMessage.setLayoutData(gd_textMessage);
	
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
			message = textMessage.getText();
		}
		super.buttonPressed(buttonId);
	}
	
	public Group getSelectedGroup()
	{
		return selectedGroup;
	}
	
	public String getMessage()
	{
		return message;
	}

}
