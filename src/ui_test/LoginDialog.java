package ui_test;

import java.util.Observable;
import java.util.Observer;

import logiccenter.LogicCenter;
import logiccenter.LogicCenterImp;
import logiccenter.VirtualResult.LoginResult;
import logiccenter.VirtualResult.VirtualState;

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
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import ui_test.MainWindow.LocalSearchResultTask;

import entity.MyError;
import entity.Password;
import entity.infoField.IdenticalInfoField;
import entity.infoField.IdenticalInfoFieldName;
import entity.infoField.InfoFieldFactory;

public class LoginDialog extends Dialog
{

	private Text textPassword;
	private Label labelPassword;
	private Text textIdent;
	private Label label;
	private Combo comboIdentType;
	private Label labelIdentifier;
	
	private IdenticalInfoField ident;
	private String password;
	
	private Shell parentShell;
	private LoginDialog thisDialog = this;	//给内部类调用
	
	private LogicCenter logicCenter = LogicCenterImp.getInstance();
		
	/**
	 * Create the dialog
	 * @param parentShell
	 */
	public LoginDialog(Shell parentShell)
	{
		super(parentShell);
		this.parentShell = parentShell;
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

		labelIdentifier = new Label(container, SWT.NONE);
		labelIdentifier.setText("标识类型");

		comboIdentType = new Combo(container, SWT.NONE);
		final GridData gd_comboIdentType = new GridData(SWT.FILL, SWT.CENTER, false, false);
		comboIdentType.setLayoutData(gd_comboIdentType);
		
		for(IdenticalInfoFieldName fieldName : IdenticalInfoFieldName.values())
		{
			comboIdentType.add(fieldName.name());
		}
		comboIdentType.select(0);

		label = new Label(container, SWT.NONE);
		label.setText("身份标识");

		textIdent = new Text(container, SWT.BORDER);
		final GridData gd_textIdent = new GridData(SWT.FILL, SWT.CENTER, false, false);
		textIdent.setLayoutData(gd_textIdent);

		labelPassword = new Label(container, SWT.NONE);
		labelPassword.setText("密码");

		textPassword = new Text(container, SWT.BORDER | SWT.PASSWORD);
		final GridData gd_textPassword = new GridData(SWT.FILL, SWT.CENTER, false, false);
		textPassword.setLayoutData(gd_textPassword);
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
		return new Point(500, 375);
	}
	protected void buttonPressed(int buttonId)
	{
		if (buttonId == IDialogConstants.OK_ID)
		{
			 //!TODO: Identical Field怎么新建？？
			ident = null;
			password = textPassword.getText();
			
			LoginResult result = logicCenter.login(ident, new Password(password));
			result.addObserver(new LoginResultObserver());
			
			//!TODO	 调试！！
			
			return;
		}
		super.buttonPressed(buttonId);
	}
	
	class LoginResultObserver implements Observer
	{

		@Override
		public void update(Observable o, Object arg)
		{
			// TODO Auto-generated method stub
			Display.getDefault().syncExec(new LoginResultTask((LoginResult)o));
		}
		
	}
	
	class LoginResultTask implements Runnable
	{
		private LoginResult loginResult;

		public LoginResultTask(LoginResult loginResult)
		{
			this.loginResult = loginResult;
		}
		@Override
		public void run()
		{
			VirtualState state = loginResult.getState();
			if(state == VirtualState.ERRORED)
			{
				MyError error = loginResult.getError();
				MessageDialog.openWarning(parentShell, "登录失败", error.toString());
			}
			else if(state == VirtualState.PREPARED)
			{
				thisDialog.close();
			}
		}
		
	}

}
