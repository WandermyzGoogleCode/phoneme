package ui_test;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class EditGroupDialog extends Dialog
{

	private Text textCategoryName;
	private Label labelCategoryName;
	
	private String categoryName;
	public String GetCategoryName() {return categoryName;}
	public void SetCategoryName(String text)
	{
		categoryName = text;
	}
	/**
	 * Create the dialog
	 * @param parentShell
	 */
	public EditGroupDialog(Shell parentShell)
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

		labelCategoryName = new Label(container, SWT.NONE);
		labelCategoryName.setText("自定义分组名称");

		textCategoryName = new Text(container, SWT.BORDER);
		final GridData gd_textCategoryName = new GridData(SWT.LEFT, SWT.CENTER, true, false);
		gd_textCategoryName.widthHint = 239;
		textCategoryName.setLayoutData(gd_textCategoryName);
		
		if(categoryName != null) 
			textCategoryName.setText(categoryName);
		//
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
		
		return new Point(372, 119);
	}
	
	protected void buttonPressed(int buttonId)
	{
		if (buttonId == IDialogConstants.OK_ID) {
			categoryName = textCategoryName.getText();
			//return;
		}
		super.buttonPressed(buttonId);
	}

}
