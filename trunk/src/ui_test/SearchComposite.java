package ui_test;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

public class SearchComposite extends Composite
{

	private ToolItem toolItemRelationCube;
	private ToolBar toolBar;
	/**
	 * Create the composite
	 * @param parent
	 * @param style
	 */
	public SearchComposite(Composite parent, int style)
	{
		super(parent, style);
		setLayout(new GridLayout());

		toolBar = new ToolBar(this, SWT.NONE);

		toolItemRelationCube = new ToolItem(toolBar, SWT.PUSH);
		toolItemRelationCube.setText("ÈËÁ¢·½ËÑË÷");
		//
	}

	@Override
	protected void checkSubclass()
	{
		// Disable the check that prevents subclassing of SWT components
	}

}
