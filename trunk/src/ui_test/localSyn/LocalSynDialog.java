package ui_test.localSyn;

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
import org.eclipse.swt.widgets.ProgressBar;

public class LocalSynDialog extends Dialog {
	public class RefreshProgressTask implements Runnable {
		double progress;
		
		public RefreshProgressTask(Double progress) {
			this.progress = progress;
		}

		@Override
		public void run() {
			progressBar.setSelection((int)(progressBar.getMaximum()*progress));
		}

	}

	private Label labelNowState;
	private Text textOutput;
	private LocalSynResult synResult;
	private FormData formData_1;
	private ProgressBar progressBar;

	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public LocalSynDialog(Shell parentShell, LocalSynResult synResult) {
		super(parentShell);
		setShellStyle(SWT.CLOSE | SWT.MAX | SWT.RESIZE);
		this.synResult = synResult;
		SynObserver observer = new SynObserver();
		this.synResult.addObserver(observer);
	}

	class SynObserver implements Observer{
		@Override
		public void update(Observable arg0, Object arg1) {
			if (arg1 != null && (arg1 instanceof Double))
				Display.getDefault().syncExec(new RefreshProgressTask((Double)arg1));
			else
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
				labelNowState.setText(result.getIntermediateInfo().trim());
				textOutput.append(result.getIntermediateInfo()+"\n");
				progressBar.setSelection((int)(progressBar.getMaximum()*result.getProgress()));
			}
			else if (result.getState() == VirtualState.PREPARED){
				labelNowState.setText("同步完成");
				textOutput.append("同步完成\n");	
				progressBar.setSelection(progressBar.getMaximum());
			}
			else if (result.getState() == VirtualState.ERRORED){
				labelNowState.setText("同步出错");
				textOutput.append(String.format("同步出错：%s\n", result.getError().toString()));
				MessageDialog.openError(getShell(), "同步出错", result.getError().toString());
				progressBar.setSelection(progressBar.getMinimum());
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
				formData_1 = new FormData();
				formData_1.left = new FormAttachment(0, 10);
				formData_1.top = new FormAttachment(0, 10);
				labelNowState.setLayoutData(formData_1);
			}
			labelNowState.setText("\u5F53\u524D\u72B6\u6001");
		}
		{
			textOutput = new Text(container, SWT.BORDER | SWT.MULTI);
			formData_1.right = new FormAttachment(textOutput, 0, SWT.RIGHT);
			textOutput.setEditable(false);
			{
				FormData formData = new FormData();
				formData.bottom = new FormAttachment(100, -31);
				formData.top = new FormAttachment(labelNowState, 6);
				formData.right = new FormAttachment(100, -10);
				formData.left = new FormAttachment(0, 10);
				textOutput.setLayoutData(formData);
			}
		}
		{
			progressBar = new ProgressBar(container, SWT.NONE);
			{
				FormData formData = new FormData();
				formData.right = new FormAttachment(labelNowState, 0, SWT.RIGHT);
				formData.top = new FormAttachment(textOutput, 6);
				formData.left = new FormAttachment(0, 10);
				progressBar.setLayoutData(formData);
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
