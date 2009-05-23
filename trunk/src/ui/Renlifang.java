package ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

public class Renlifang extends Composite {

	public Renlifang(Composite parent, int style) {
		super(parent, style);
		{
			Group group = new Group(this, SWT.NONE);
			group.setBounds(10, 10, 399, 61);
			{
				Label lableRenlifang = new Label(group, SWT.NONE);
				lableRenlifang.setBounds(10, 10, 113, 26);
				lableRenlifang.setText("\u4EBA\u7ACB\u65B9");
			}
		}
		// TODO Auto-generated constructor stub
	}

}
