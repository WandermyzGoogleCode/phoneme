package ui;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import entity.VirtualResult.MessageBox;
import entity.message.Message;

public class MessageBoxDialog extends Composite {
	private Text txt0;
	private Tree tree;

	public MessageBoxDialog(Composite parent, int style) {
		super(parent, style);
		{
			Group group = new Group(this, SWT.NONE);
			group.setBounds(10, 0, 430, 52);
			{
				Label lblMessagenumber = new Label(group, SWT.NONE);
				lblMessagenumber.setBounds(25, 10, 97, 21);
				lblMessagenumber.setText("MessageNumber:");
			}
			{
				txt0 = new Text(group, SWT.BORDER | SWT.READ_ONLY);
				txt0.setText("0");
				txt0.setBounds(127, 12, 73, 19);
			}
		}
		{
			Group group = new Group(this, SWT.NONE);
			group.setBounds(10, 58, 430, 221);
			{
				tree = new Tree(group, SWT.BORDER);
				tree.setBounds(10, 10, 410, 201);
				{
					TreeItem treeItem = new TreeItem(tree, SWT.NONE);
					treeItem.setText("New TreeItem");
				}
			}
		}
	}

	public void setMessage(List<Message> m) {
		int n=m.size();
		txt0.setText(new Integer(n).toString());
		for(int i=0;i<n;i++){
			new TreeItem(tree,SWT.NONE).setText(m.get(i).toString());
		}
	}
}
