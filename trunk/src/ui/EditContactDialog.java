package ui;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Button;
import com.swtdesigner.SWTResourceManager;

import entity.UserInfo;
import entity.infoField.InfoField;
import entity.infoField.InfoFieldFactory;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
public class EditContactDialog extends Dialog{
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
	private UserInfo last;
	
	public EditContactDialog (UserInfo last, Shell parent, int style) {
		super (parent, style);
		this.last = last;
	}
	public EditContactDialog (Shell parent) {
		this (new UserInfo(), parent, 0); // your default style bits go here (not the Shell's style bits)
	}
	public Object open () {
		Shell parent = getParent();
		Shell registDia = new Shell(parent, SWT.DIALOG_TRIM | SWT.MIN | SWT.APPLICATION_MODAL);
		registDia.setLayout(null);
		registDia.setText("\u7F16\u8F91");
		{
			Group registInfo = new Group(registDia, SWT.NONE);
			registInfo.setFont(SWTResourceManager.getFont("Î¢ÈíÑÅºÚ", 8, SWT.NORMAL));
			registInfo.setLayout(null);
			registInfo.setBounds(10, 24, 574, 348);
			registInfo.setText("\u7F16\u8F91\u8054\u7CFB\u4EBA\u4FE1\u606F");
			{
				Label label = new Label(registInfo, SWT.NONE);
				label.setFont(SWTResourceManager.getFont("Î¢ÈíÑÅºÚ", 8, SWT.NORMAL));
				label.setBounds(38, 34, 24, 17);
				label.setText("\u59D3\u540D");
			}
			{
				name = new Text(registInfo, SWT.BORDER);
				name.setBounds(86, 34, 142, 23);
			}
			{
				nick = new Text(registInfo, SWT.BORDER);
				nick.setBounds(363, 34, 142, 23);
			}
			{
				Label label = new Label(registInfo, SWT.NONE);
				label.setFont(SWTResourceManager.getFont("Î¢ÈíÑÅºÚ", 8, SWT.NORMAL));
				label.setBounds(314, 34, 24, 17);
				label.setText("\u6635\u79F0");
			}
			{
				Label label = new Label(registInfo, SWT.NONE);
				label.setFont(SWTResourceManager.getFont("Î¢ÈíÑÅºÚ", 8, SWT.NORMAL));
				label.setBounds(38, 115, 24, 17);
				label.setText("\u624B\u673A");
			}
			{
				cellphone = new Text(registInfo, SWT.BORDER);
				cellphone.setBounds(86, 112, 142, 23);
			}
			{
				Label label = new Label(registInfo, SWT.NONE);
				label.setFont(SWTResourceManager.getFont("Î¢ÈíÑÅºÚ", 8, SWT.NORMAL));
				label.setBounds(314, 74, 24, 17);
				label.setText("\u751F\u65E5");
			}
			{
				dateTime = new DateTime(registInfo, SWT.NONE);
				dateTime.setFont(SWTResourceManager.getFont("Î¢ÈíÑÅºÚ", 8, SWT.NORMAL));
				dateTime.setBounds(362, 72, 93, 24);
			}
			{
				Label label = new Label(registInfo, SWT.NONE);
				label.setFont(SWTResourceManager.getFont("Î¢ÈíÑÅºÚ", 8, SWT.NORMAL));
				label.setBounds(314, 115, 36, 17);
				label.setText("E-mail");
			}
			{
				text_3 = new Text(registInfo, SWT.BORDER);
				text_3.setBounds(363, 112, 142, 23);
			}
			{
				Label label = new Label(registInfo, SWT.NONE);
				label.setFont(SWTResourceManager.getFont("Î¢ÈíÑÅºÚ", 8, SWT.NORMAL));
				label.setBounds(38, 150, 24, 23);
				label.setText("QQ");
			}
			{
				qq = new Text(registInfo, SWT.BORDER);
				qq.setBounds(86, 150, 142, 23);
			}
			{
				Label label = new Label(registInfo, SWT.NONE);
				label.setFont(SWTResourceManager.getFont("Î¢ÈíÑÅºÚ", 8, SWT.NORMAL));
				label.setBounds(314, 150, 36, 17);
				label.setText("MSN");
			}
			{
				msn = new Text(registInfo, SWT.BORDER);
				msn.setBounds(362, 150, 142, 23);
			}
			{
				Label label = new Label(registInfo, SWT.NONE);
				label.setFont(SWTResourceManager.getFont("Î¢ÈíÑÅºÚ", 8, SWT.NORMAL));
				label.setBounds(38, 197, 48, 23);
				label.setText("\u4E2A\u4EBA\u63CF\u8FF0");
			}
			{
				personalStatment = new Text(registInfo, SWT.BORDER);
				personalStatment.setFont(SWTResourceManager.getFont("Î¢ÈíÑÅºÚ", 8, SWT.NORMAL));
				personalStatment.setBounds(98, 197, 427, 80);
			}
			{
				Label label = new Label(registInfo, SWT.NONE);
				label.setFont(SWTResourceManager.getFont("Î¢ÈíÑÅºÚ", 8, SWT.NORMAL));
				label.setBounds(38, 74, 24, 17);
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
				sex.setBounds(86, 71, 67, 21);
			}
		}
		{
			Button yes = new Button(registDia, SWT.NONE);
			yes.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					gatherInfo();
				}

				private void gatherInfo() {
					// TODO Auto-generated method stub
					nick.getText();
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
					int month = dateTime.getMonth(); // Calendar.MONTH
					int year = dateTime.getYear(); // Calendar.YEAR
					
					String birth=""+year+"-"+month+"-"+day;

					InfoField bir = factory.makeInfoField("BirthDay",birth);
					newUser.getBaseInfo().setInfoField(bir.getName(), bir);
					InfoField qqi=factory.makeInfoField("QQNumber", qq.getText());
					newUser.getBaseInfo().setInfoField(qqi.getName(),qqi );
					
					InfoField nicki=factory.makeInfoField("NickName", nick.getText());
					newUser.getCustomInfo().setInfoField(nicki.getName(), nicki);
					
					MainWindow.logicCenter.editContactInfo(newUser);
					
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
			if (!display.readAndDispatch()) display.sleep();
		}
		return result;
	}
}
