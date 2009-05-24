package ui;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import algorithm.SimpleUserInfoMatcherUsingLCST;

import com.swtdesigner.SWTResourceManager;

import entity.UserInfo;
import entity.VirtualResult.LocalSearchContactsResult;
import entity.infoField.InfoField;
import entity.infoField.InfoFieldFactory;
import logiccenter.*;

public class SearchLocalDialog extends Dialog {
	private MainWindow mainWindow;
	Object result;
	private Text name;
	private Text nick;
	private Text cellphone;
	private Text text_3;
	private Text qq;
	private Text msn;
	private Text personalStatment;
	private Combo sex;
	private Button cancel;
	private DateTime dateTime;
	private Group registInfo;
	private Shell registDia;
	private Tree tree;
	private Label labelpolcy;
	private Combo combo;
	private Button yes;
	private Button yes1;
	//private LogicCenter logicCenter;

	class SetResultTask implements Runnable{
		private List<UserInfo> users;
		@Override
		public void run() {
			setSearchResult(users);
		}
		
		public SetResultTask(List<UserInfo> users){
			this.users = users;
		}
	}
	
	class SearchObserver implements Observer{
		@Override
		public void update(Observable arg0, Object arg1) {
			LocalSearchContactsResult o = (LocalSearchContactsResult)arg0;
			Display.getDefault().syncExec(new SetResultTask(o.getContacts()));
		}
	}
	
	public SearchLocalDialog(MainWindow mainWindow, Shell arg0, int arg1){
		super(arg0, arg1);
		this.mainWindow = mainWindow;
	}
	
	public SearchLocalDialog(Shell parent, int style) {
		super(parent, style);
	}

	public SearchLocalDialog(Shell parent) {
		this(parent, 0); // your default style bits go here (not the Shell's
		// style bits)
	}

	public Object open() {
		Shell parent = getParent();
		registDia = new Shell(parent, SWT.DIALOG_TRIM | SWT.MIN
				| SWT.APPLICATION_MODAL);
		registDia.setLayout(null);
		registDia.setText("\u672C\u5730\u641C\u7D22");
		{
			registInfo = new Group(registDia, SWT.NONE);
			registInfo.setFont(SWTResourceManager
					.getFont("Î¢ÈíÑÅºÚ", 8, SWT.NORMAL));
			registInfo.setLayout(null);
			registInfo.setBounds(10, 24, 574, 348);
			registInfo.setText("\u641C\u7D22\u8054\u7CFB\u4EBA");
			{
				Label label = new Label(registInfo, SWT.NONE);
				label
						.setFont(SWTResourceManager.getFont("Î¢ÈíÑÅºÚ", 8,
								SWT.NORMAL));
				label.setBounds(38, 84, 24, 17);
				label.setText("\u59D3\u540D");
			}
			{
				name = new Text(registInfo, SWT.BORDER);
				name.setBounds(86, 84, 142, 23);
			}
			{
				nick = new Text(registInfo, SWT.BORDER);
				nick.setBounds(363, 84, 142, 23);
			}
			{
				Label label = new Label(registInfo, SWT.NONE);
				label
						.setFont(SWTResourceManager.getFont("Î¢ÈíÑÅºÚ", 8,
								SWT.NORMAL));
				label.setBounds(314, 84, 24, 17);
				label.setText("\u6635\u79F0");
			}
			{
				Label label = new Label(registInfo, SWT.NONE);
				label
						.setFont(SWTResourceManager.getFont("Î¢ÈíÑÅºÚ", 8,
								SWT.NORMAL));
				label.setBounds(38, 165, 24, 17);
				label.setText("\u624B\u673A");
			}
			{
				cellphone = new Text(registInfo, SWT.BORDER);
				cellphone.setBounds(86, 162, 142, 23);
			}
			{
				Label label = new Label(registInfo, SWT.NONE);
				label
						.setFont(SWTResourceManager.getFont("Î¢ÈíÑÅºÚ", 8,
								SWT.NORMAL));
				label.setBounds(314, 124, 24, 17);
				label.setText("\u751F\u65E5");
			}
			{
				dateTime = new DateTime(registInfo, SWT.NONE);
				dateTime.setFont(SWTResourceManager.getFont("Î¢ÈíÑÅºÚ", 8,
						SWT.NORMAL));
				dateTime.setBounds(362, 122, 93, 24);
			}
			{
				Label label = new Label(registInfo, SWT.NONE);
				label.setFont(SWTResourceManager.getFont("Î¢ÈíÑÅºÚ", 8,
								SWT.NORMAL));
				label.setBounds(314, 165, 36, 17);
				label.setText("E-mail");
			}
			{
				text_3 = new Text(registInfo, SWT.BORDER);
				text_3.setBounds(363, 162, 142, 23);
			}
			{
				Label label = new Label(registInfo, SWT.NONE);
				label
						.setFont(SWTResourceManager.getFont("Î¢ÈíÑÅºÚ", 8,
								SWT.NORMAL));
				label.setBounds(38, 200, 24, 23);
				label.setText("QQ");
			}
			{
				qq = new Text(registInfo, SWT.BORDER);
				qq.setBounds(86, 200, 142, 23);
			}
			{
				Label label = new Label(registInfo, SWT.NONE);
				label
						.setFont(SWTResourceManager.getFont("Î¢ÈíÑÅºÚ", 8,
								SWT.NORMAL));
				label.setBounds(314, 200, 36, 17);
				label.setText("MSN");
			}
			{
				msn = new Text(registInfo, SWT.BORDER);
				msn.setBounds(362, 200, 142, 23);
			}
			{
				Label label = new Label(registInfo, SWT.NONE);
				label
						.setFont(SWTResourceManager.getFont("Î¢ÈíÑÅºÚ", 8,
								SWT.NORMAL));
				label.setBounds(38, 247, 48, 23);
				label.setText("\u4E2A\u4EBA\u63CF\u8FF0");
			}
			{
				personalStatment = new Text(registInfo, SWT.BORDER);
				personalStatment.setFont(SWTResourceManager.getFont("Î¢ÈíÑÅºÚ", 8,
						SWT.NORMAL));
				personalStatment.setBounds(98, 247, 427, 80);
			}
			{
				Label label = new Label(registInfo, SWT.NONE);
				label
						.setFont(SWTResourceManager.getFont("Î¢ÈíÑÅºÚ", 8,
								SWT.NORMAL));
				label.setBounds(38, 124, 24, 17);
				label.setText("\u6027\u522B");
			}
			{
				sex = new Combo(registInfo, SWT.READ_ONLY);
				sex.add("²»¹«¿ª");
				sex.add("ÄÐ");
				sex.add("Å®");
				sex.setData("²»¹«¿ª", 0);
				sex.setData("ÄÐ", 1);
				sex.setData("Å®", 2);
				sex.select(0);
				sex.setBounds(86, 121, 67, 21);
			}
			{
				labelpolcy = new Label(registInfo, SWT.NONE);
				labelpolcy.setBounds(27, 36, 48, 17);
				labelpolcy.setText("\u9009\u62E9\u7B56\u7565");
			}
			{
				combo = new Combo(registInfo, SWT.NONE);
				combo.add("²ßÂÔ1");
				combo.add("²ßÂÔ2");
				combo.add("²ßÂÔ3");
				combo.setData("²ßÂÔ1", 0);
				combo.setData("²ßÂÔ2", 1);
				combo.setData("²ßÂÔ3", 2);
				combo.select(0);
				combo.setBounds(86, 36, 89, 21);
			}
		}
		{
			yes = new Button(registDia, SWT.NONE);
			yes.addSelectionListener(new SelectionAdapter() {

				public void widgetSelected(SelectionEvent e) {
					gatherInfo();
					//!TODO combo.getSelectionIndex() ¾ÍÊÇÄãÒªµÄ²ßÂÔ
					System.out.println(combo.getSelectionIndex());
					registInfo.dispose();
					yes.dispose();
					cancel.dispose();
					System.out.println("dispose");
					Group group = new Group(registDia, SWT.NONE);
					group.setBounds(10, 58, 430, 221);
					{
						tree = new Tree(group, SWT.BORDER);
						tree.setBounds(10, 10, 410, 201);
						{
							TreeItem treeItem = new TreeItem(tree, SWT.NONE);
							treeItem.setText("New TreeItem");
						}
						yes1=new Button(registDia, SWT.NONE);
						yes1.setFont(SWTResourceManager.getFont("Î¢ÈíÑÅºÚ", 8, SWT.NORMAL));
						yes1.setBounds(404, 420, 68, 23);
						yes1.setText("È·¶¨");
						yes1.addSelectionListener(new SelectionAdapter() {

							public void widgetSelected(SelectionEvent e) {
								yes1.getParent().dispose();
							}
						});
					}
					
				}
				private void gatherInfo() {
					// TODO Auto-generated method stub
					UserInfo newUser = UserInfo.getNewLocalUser();
					InfoFieldFactory factory = InfoFieldFactory.getFactory();
					InfoField nameInfo = factory.makeInfoField("Name", name
							.getText());

					newUser.getBaseInfo().setInfoField(nameInfo.getName(),
							nameInfo);

					InfoField emailInfo = factory.makeInfoField("EmailAddress",
							text_3.getText());
					newUser.getBaseInfo().setInfoField(emailInfo.getName(),
							emailInfo);

					InfoField cell = factory.makeInfoField("CellPhone",
							cellphone.getText());
					newUser.getBaseInfo().setInfoField(cell.getName(), cell);
					
					int day = dateTime.getDay(); // Calendar.DAY_OF_MONTH
					//TODO ÎªÊ²Ã´MONTH×ÜÊÇºÍÏÔÊ¾µÄ²î1£¿
					int month = dateTime.getMonth()+1; // Calendar.MONTH
					int year = dateTime.getYear(); // Calendar.YEAR
					
					String birth=""+year+"-"+month+"-"+day;

					InfoField bir = factory.makeInfoField("BirthDay",birth);
					newUser.getBaseInfo().setInfoField(bir.getName(), bir);
					InfoField qqi=factory.makeInfoField("QQNumber", qq.getText());
					newUser.getBaseInfo().setInfoField(qqi.getName(),qqi );
					
					InfoField nicki=factory.makeInfoField("NickName", nick.getText());
					newUser.getCustomInfo().setInfoField(nicki.getName(), nicki);
					
					LocalSearchContactsResult res = mainWindow.logicCenter.localSearchContacts(newUser, new SimpleUserInfoMatcherUsingLCST());
					SearchObserver observer = new SearchObserver();
					res.addObserver(observer);
				}
				
			});
			yes.setFont(SWTResourceManager.getFont("Î¢ÈíÑÅºÚ", 8, SWT.NORMAL));
			yes.setBounds(404, 420, 68, 23);
			yes.setText("\u786E\u8BA4");
		}
		{
			cancel = new Button(registDia, SWT.NONE);
			cancel.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					cancel.getParent().dispose();
				}
			});
			cancel.setFont(SWTResourceManager.getFont("Î¢ÈíÑÅºÚ", 8, SWT.NORMAL));
			cancel.setBounds(498, 420, 68, 23);
			cancel.setText("\u53D6\u6D88");
		}
		// Your code goes here (widget creation, set result, etc).
		registDia.pack();
		registDia.open();
		Display display = parent.getDisplay();
		while (!registDia.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		return result;
	}
	public void setSearchResult(List<UserInfo> contacts){
		int n=contacts.size();
		//txt0.setText(new Integer(n).toString());
		tree.removeAll();
		for(int i=0;i<n;i++){
			new TreeItem(tree,SWT.NONE).setText(contacts.get(i).getStringValue());
		}
	}
}
