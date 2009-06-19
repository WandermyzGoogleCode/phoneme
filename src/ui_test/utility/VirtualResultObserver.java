package ui_test.utility;

import java.util.Observable;
import java.util.Observer;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import logiccenter.VirtualResult.VirtualResult;
import logiccenter.VirtualResult.VirtualState;

public class VirtualResultObserver implements Observer
{
	private Shell shell;
	private String succMsg;
	
	public VirtualResultObserver(Shell shell, String succMsg){
		this.shell = shell;
		this.succMsg = succMsg;
	}
	
	@Override
	public void update(Observable o, Object arg)
	{
		Display.getDefault().syncExec(new EditGroupResultTask((VirtualResult)o, shell, succMsg));
	}
	
}

class EditGroupResultTask implements Runnable
{
	private VirtualResult result;
	private Shell shell;
	private String succMsg;
	
	public EditGroupResultTask(VirtualResult result, Shell shell, String succMsg)
	{
		this.shell = shell;
		this.succMsg = succMsg;
		this.result = result;
	}

	@Override
	public void run()
	{
		VirtualState state = result.getState();
		if(state == VirtualState.PREPARED)
		{
			MessageDialog.openInformation(shell, "³É¹¦", succMsg);
		}
		else if(state == VirtualState.ERRORED)
		{
			MessageDialog.openWarning(shell, "Ê§°Ü", result.getError().toString());
		}
		
	}
}