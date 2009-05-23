package ui;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import com.swtdesigner.SWTResourceManager;

import entity.StatResult;
import entity.UserInfo;

public class StaticDialog extends Dialog {
	Object result;
	private Button cancel;
	private Text totalNumber;
	private Text text1;
	private Text text3;
	private Text text5;
	private Text text7;
	private Text text9;
	private Text text11;
	private Text text12;
	private Text text10;
	private Text text8;
	private Text text2;
	private Text text4;
	private Text text6;
	//private LogicCenter logicCenter;

	public StaticDialog(Shell parent, int style) {
		super(parent, style);
	}

	public StaticDialog(Shell parent) {
		this(parent, 0); // your default style bits go here (not the Shell's
		// style bits)
	}

	public Object open() {
		Shell parent = getParent();
		Shell registDia = new Shell(parent, SWT.DIALOG_TRIM | SWT.MIN
				| SWT.APPLICATION_MODAL);
		registDia.setLayout(null);
		registDia.setText("\u7EDF\u8BA1");
		{
			cancel = new Button(registDia, SWT.NONE);
			cancel.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					cancel.getParent().dispose();
				}
			});
			cancel.setFont(SWTResourceManager.getFont("微软雅黑", 8, SWT.NORMAL));
			cancel.setBounds(498, 420, 68, 23);
			cancel.setText("\u8FD4\u56DE");
		}
		// Your code goes here (widget creation, set result, etc).
		registDia.pack();
		{
			Group group = new Group(registDia, SWT.NONE);
			group.setBounds(10, 10, 574, 404);
			{
				Label labelTotal = new Label(group, SWT.NONE);
				labelTotal.setBounds(38, 30, 43, 21);
				labelTotal.setText("\u603B\u4EBA\u6570");
			}
			{
				totalNumber = new Text(group, SWT.BORDER);
				totalNumber.setBounds(92, 30, 73, 19);
			}
			{
				Label lbl1 = new Label(group, SWT.NONE);
				lbl1.setBounds(32, 73, 60, 13);
				lbl1.setText("1\u6708\u4EFD\u751F\u65E5");
			}
			{
				text1 = new Text(group, SWT.BORDER);
				text1.setBounds(92, 67, 73, 19);
			}
			{
				text3 = new Text(group, SWT.BORDER);
				text3.setBounds(92, 104, 73, 19);
			}
			{
				text5 = new Text(group, SWT.BORDER);
				text5.setBounds(92, 143, 73, 19);
			}
			{
				text7 = new Text(group, SWT.BORDER);
				text7.setBounds(92, 179, 73, 19);
			}
			{
				text9 = new Text(group, SWT.BORDER);
				text9.setBounds(92, 214, 73, 19);
			}
			{
				text11 = new Text(group, SWT.BORDER);
				text11.setBounds(92, 248, 73, 19);
			}
			{
				text12 = new Text(group, SWT.BORDER);
				text12.setBounds(271, 248, 73, 19);
			}
			{
				text10 = new Text(group, SWT.BORDER);
				text10.setBounds(271, 214, 73, 19);
			}
			{
				text8 = new Text(group, SWT.BORDER);
				text8.setBounds(271, 179, 73, 19);
			}
			{
				text2 = new Text(group, SWT.BORDER);
				text2.setBounds(271, 67, 73, 19);
			}
			{
				text4 = new Text(group, SWT.BORDER);
				text4.setBounds(271, 104, 73, 19);
			}
			{
				text6 = new Text(group, SWT.BORDER);
				text6.setBounds(271, 143, 73, 19);
			}
			{
				Label lbl2月份生日 = new Label(group, SWT.NONE);
				lbl2月份生日.setBounds(205, 73, 60, 13);
				lbl2月份生日.setText("2\u6708\u4EFD\u751F\u65E5");
			}
			{
				Label lbl3月份生日 = new Label(group, SWT.NONE);
				lbl3月份生日.setBounds(205, 104, 60, 13);
				lbl3月份生日.setText("4\u6708\u4EFD\u751F\u65E5");
			}
			{
				Label lbl6月份生日 = new Label(group, SWT.NONE);
				lbl6月份生日.setBounds(205, 143, 60, 13);
				lbl6月份生日.setText("6\u6708\u4EFD\u751F\u65E5");
			}
			{
				Label lbl8月份生日 = new Label(group, SWT.NONE);
				lbl8月份生日.setBounds(205, 179, 60, 13);
				lbl8月份生日.setText("8\u6708\u4EFD\u751F\u65E5");
			}
			{
				Label lbl110月份生日 = new Label(group, SWT.NONE);
				lbl110月份生日.setBounds(205, 214, 60, 13);
				lbl110月份生日.setText("10\u6708\u4EFD\u751F\u65E5");
			}
			{
				Label lbl12月份生日 = new Label(group, SWT.NONE);
				lbl12月份生日.setBounds(205, 248, 60, 13);
				lbl12月份生日.setText("12\u6708\u4EFD\u751F\u65E5");
			}
			{
				Label lbl3月份生日_1 = new Label(group, SWT.NONE);
				lbl3月份生日_1.setBounds(32, 104, 60, 13);
				lbl3月份生日_1.setText("3\u6708\u4EFD\u751F\u65E5");
			}
			{
				Label lbl7月份生日 = new Label(group, SWT.NONE);
				lbl7月份生日.setBounds(32, 179, 60, 13);
				lbl7月份生日.setText("7\u6708\u4EFD\u751F\u65E5");
			}
			{
				Label lbl5月份生日 = new Label(group, SWT.NONE);
				lbl5月份生日.setBounds(32, 143, 60, 13);
				lbl5月份生日.setText("5\u6708\u4EFD\u751F\u65E5");
			}
			{
				Label lbl9月份生日 = new Label(group, SWT.NONE);
				lbl9月份生日.setBounds(32, 214, 60, 13);
				lbl9月份生日.setText("9\u6708\u4EFD\u751F\u65E5");
			}
			{
				Label lbl11月份生日 = new Label(group, SWT.NONE);
				lbl11月份生日.setBounds(32, 248, 60, 13);
				lbl11月份生日.setText("11\u6708\u4EFD\u751F\u65E5");
			}
		}
		
		registDia.open();
		Display display = parent.getDisplay();
		while (!registDia.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		return result;
	}
	public void setStatics(StatResult result){
		System.out.println("设置统计");
		totalNumber.setText(new Integer(result.getTotalCnt()).toString());
		List<UserInfo>[] a=result.getBirthDistrib();
		text1.setText(new Integer(a[0].size()).toString());
		text2.setText(new Integer(a[1].size()).toString());
		text3.setText(new Integer(a[2].size()).toString());
		text4.setText(new Integer(a[3].size()).toString());
		text5.setText(new Integer(a[4].size()).toString());
		text6.setText(new Integer(a[5].size()).toString());
		text7.setText(new Integer(a[6].size()).toString());
		text8.setText(new Integer(a[7].size()).toString());
		text9.setText(new Integer(a[8].size()).toString());
		text10.setText(new Integer(a[9].size()).toString());
		text11.setText(new Integer(a[10].size()).toString());
		text12.setText(new Integer(a[11].size()).toString());
		
	}
}
