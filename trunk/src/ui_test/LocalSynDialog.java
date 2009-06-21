package ui_test;

import java.util.Observable;
import java.util.Observer;

import logiccenter.VirtualResult.LocalSynResult;
import logiccenter.VirtualResult.VirtualState;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.widgets.Text;

public class LocalSynDialog extends Dialog {
	private Label labelNowState;
	private Text textOutput;
	private LocalSynResult synResult;

	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public LocalSynDialog(Shell parentShell, LocalSynResult synResult) {
		super(parentShell);
		this.synResult = synResult;
		SynObserver observer = new SynObserver();
		this.synResult.addObserver(observer);
	}

	class SynObserver implements Observer{
		@Override
		public void update(Observable arg0, Object arg1) {
			Display.getDefault().syncExec(new SynObserverTask((LocalSynResult)arg0));
		}
	}
	
	class SynObserverTask implements Runnable{
		private LocalSynResult result;
		
		public SynObserverTask(LocalSynResult result) {
			this.result = result;
		}

		@Override
		public void run() {
			if (result.getState() == VirtualState.LOADING){
				labelNowState.setText(result.getIntermediateInfo());
				textOutput.append(result.getIntermediateInfo()+"\n");
			}
			else if (result.getState() == VirtualState.PREPARED){
				labelNowState.setText("同步完成");
				textOutput.append("同步完成\n");				
			}
			else if (result.getState() == VirtualState.ERRORED){
				labelNowState.setText("同步出错");
				textOutput.append(String.format("同步出错%s\n", result.getError().toString()));
				MessageDialog.openError(getShell(), "同步出错", result.getError().toString());
			}
		}
	}
	
	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		container.setLayout(new FormLayout());
		{
			labelNowState = new Label(container, SWT.NONE);
			{
				FormData formData = new FormData();
				formData.top = new FormAttachment(0, 10);
				formData.left = new FormAttachment(0, 10);
				labelNowState.setLayoutData(formData);
			}
			labelNowState.setText("\u5F53\u524D\u72B6\u6001");
		}
		{
			textOutput = new Text(container, SWT.BORDER);
			{
				FormData formData = new FormData();
				formData.bottom = new FormAttachment(100, -10);
				formData.right = new FormAttachment(100, -10);
				formData.top = new FormAttachment(labelNowState, 6);
				formData.left = new FormAttachment(0, 10);
				textOutput.setLayoutData(formData);
			}
		}

		return container;
	}

	/**
	 * Create contents of the button bar.
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(450, 300);
	}

}
