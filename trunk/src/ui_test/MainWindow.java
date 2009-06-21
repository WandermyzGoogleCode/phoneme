package ui_test;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import java.util.Map.Entry;

import logiccenter.LogicCenter;
import logiccenter.LogicCenterImp;
import logiccenter.VirtualResult.AllContactsBox;
import logiccenter.VirtualResult.AllPerContactsBox;
import logiccenter.VirtualResult.GetStatResultResult;
import logiccenter.VirtualResult.LocalSearchContactsResult;
import logiccenter.VirtualResult.LoginResult;
import logiccenter.VirtualResult.MessageBox;
import logiccenter.VirtualResult.RegisterResult;
import logiccenter.VirtualResult.RemoteSynResult;
import logiccenter.VirtualResult.RemoveSynContactResult;
import logiccenter.VirtualResult.RemoveGroupResult;
import logiccenter.VirtualResult.RemoveSynContactResult;
import logiccenter.VirtualResult.SetVisibilityResult;
import logiccenter.VirtualResult.VirtualState;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;

import ui.Gui;
import ui_test.GroupInfoDialog.RemoveGroupResultTask;
import ui_test.UserInfoTable.UserInfoTableType;
import ui_test.utility.VirtualResultObserver;

import com.ibm.icu.impl.ICUService.Factory;
import com.swtdesigner.SWTResourceManager;

import entity.BaseUserInfo;
import entity.MyError;
import entity.Password;
import entity.StatResult;
import entity.UserInfo;
import entity.infoField.InfoField;
import entity.infoField.InfoFieldFactory;
import entity.infoField.InfoFieldName;
import entity.infoField.Relation;
import entity.message.Message;
import ui_test.GroupComposite;
import ui_test.SearchComposite;
import ui_test.MessageBoxComposite;

public class MainWindow 
{

	// [start] UI Components Properties
	private MessageBoxComposite compositeMessageBox;
	private SearchComposite compositeSearch;
	private GroupComposite compositeGroup;
	private Tree treeAddressSearchResult;
	private TabItem tabItemAddressSearchResult;
	private Tree treeAddressContact;
//	private TreeItem treeAddressContactItemNoGroup;
//	private TreeItem treeAddressPermitItemNoGroup;
	private MenuItem toolItemAddressCustomGroupItems[];
	private MenuItem toolItemAddressCustomGroupNew;
	private MenuItem toolItemAddressCustomGroupNone;
	private ToolItem toolItemAddressSep2;
	private ToolItem toolItemAddressAddContact;
	private TreeColumn treeAddressPermitColumnEmail;
	private TreeColumn treeAddressPermitColumnCellphone;
	private TreeColumn treeAddressPermitColumnRelation;
	//private TreeColumn treeAddressPermitColumnCall;
	private TreeColumn treeAddressPermitColumnNickName;
	private TreeColumn treeAddressPermitColumnName;
	private Tree treeAddressPermit;
	private TreeColumn treeAddressContactColumnEmail;
	private TreeColumn treeAddressContactColumnCellphone;
	private TreeColumn treeAddressContactColumnRelation;
	//private TreeColumn treeAddressContactColumnCall;
	private TreeColumn treeAddressContactColumnNickName;
	private TreeColumn treeAddressContactColumnName;
	private TabItem tabItemAddressContact;
	private TabItem tabItemAddressPermit;
	private TabFolder tabFolderAddress;
	private Button labelAddressSearch;
	private ToolItem toolItemAddressSep1;
	private Menu toolItemAddressCustomGroupMenu;
	private ToolItem toolItemAddressCustomGroup;
	private ToolItem toolItemAddressDel;
	private ToolItem toolItemAddressPermission;
	private ToolItem toolItemAddressEdit;
//	private Combo comboInfoGender;
//	private Label labelInfoGender;
//	private ToolItem toolItemInfoDownload;
//	private ToolItem toolItemInfoReset;
//	private ToolItem toolItemInfoUpload;
//	private ToolItem toolItemInfoSave;
//	private ToolBar toolBarInfo;
//	private Text textInfoStatus;
//	private Label labelInfoStatus;
//	private Group groupInfoCustom;
//	private Text textInfoCompanyAddress;
//	private Label labelInfoCompanyAddress;
//	private Text textInfoCompanyPhone;
//	private Label labelInfoCompanyPhone;
//	private Text textInfoTitle;
//	private Label labelInfoTitle;
//	private Text textInfoCompany;
//	private Label labelInfoCompany;
//	private Group groupInfoGroup;
//	private Text textInfoFamilyAddress;
//	private Label labelInfoFamilyAddress;
//	private Text textInfoFamilyPhone;
//	private Label labelInfoFamilyPhone;
//	private Group groupInfoFamily;
	// private Button button;
	// private Composite composite_1;
//	private Text textInfoDesc;
//	private Label labelInfoDesc;
	// private Composite composite;
//	private ScrolledComposite scrolledCompositeInfo;
//	private Text textInfoMSN;
//	private Label labelInfoMSN;
//	private Text textInfoQQNumber;
//	private Label labelInfoQQNumber;
//	private Text textInfoEmail;
//	private Label labelInfoEmail;
//	private DateTime dateTimeBirthday;
//	private Label labelInfoBirthday;
//	private Text textInfoCellphone;
//	private Label labelInfoCellphone;
//	private Label labelInfoNickName;
//	private Text textInfoNickName;
//	private Text textInfoName;
//	private Label labelInfoName;
//	private Group groupInfoPersonal;
//	private Button buttonInfoChangeAvatar;
//	private Canvas canvasInfoAvatar;
	protected Shell shell;

	private Composite compositeMain;
	private StackLayout compositeMainStackLayout;
	private Composite compositeLeftList;
	private Menu mainMenu;
	private MenuItem submenuMainHelp;
	private Menu menuMainAbout;
	private MenuItem menuItemMainHelpAbout;
	private ToolBar toolBarMain;
	private Composite compositeAddress;
	private ToolBar toolBarAddress;
	private ToolItem toolItemAddressSyncRemote;
	private ToolItem toolItemAddressSyncLocal;
//	private Composite compositeInfo;
	private ToolItem toolItemMainRegister;
	private ToolItem toolItemMainLogin;
	private ToolItem toolItemMainLogout;
	private Button listButtonUserInfo;
	private Button listButtonAddressBook;
	private Button listButtonGroup;
	private Button listButtonSearch;
	private Button listButtonMessageBox;
	private MenuItem menuFile;
	private Menu filemenu;
	private MenuItem menuFileExport;
	private MenuItem menuFileImport;
	private MenuItem menustat;
	private Menu statmenu;
	private MenuItem stat;
	private StaticDialog statDialog;

	private TreeColumn treeAddressSerachResultColumnName;
	private TreeColumn treeAddressSearchResultColumnNickName;
	private TreeColumn treeAddressSearchResultColumnRelation;
	private TreeColumn treeAddressSearchResultColumnCellphone;
	private TreeColumn treeAddressSearchResultColumnEmail;
	private TreeColumn treeAddressPermitColumnBirth;
	private TreeColumn treeAddressContactColumnBirth;
	private TreeColumn treeAddressSearchResultColumnBirth;
	// [end]
 
	// [start] 自定义Properties
	//private GroupComposite groupComposite;
	
	private AllContactsBox allContactsBox;
	//private MessageBox messageBox;
	public static LogicCenter logicCenter = LogicCenterImp.getInstance();
	private Map<String, Integer> contactsCategory = new HashMap<String, Integer>();
	private AllPerContactsBox allPerContactsBox;
	private ToolItem toolItemVisibility;
	//private List<UserInfo> localSearchResult = null;
	// [end]

	/**
	 * Launch the application
	 * 
	 * @param args
	 */
	public static void main(String[] args)
	{
		try
		{
			MainWindow window = new MainWindow();
			window.open();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		System.exit(0);// 测试时期用来强制结束MessageBox线程。
	}

	/**
	 * Open the window
	 */
	public void open()
	{
		final Display display = Display.getDefault();
		createContents();

		dataInit();

		shell.open();
		shell.layout();
		while (!shell.isDisposed())
		{
			if (!display.readAndDispatch())
				display.sleep();
		}

	}

	/**
	 * Create contents of the window
	 */
	protected void createContents()
	{
		shell = new Shell();
		shell.addShellListener(new ShellShellListener());
		shell.setLayout(new FormLayout());
		shell.setSize(800, 600);
		shell.setText(Messages.getString("MainWindow.PhoneMe")); //$NON-NLS-1$

		// [start] 右侧Composite
		compositeMain = new Composite(shell, SWT.NONE);
		compositeMainStackLayout = new StackLayout();
		compositeMain.setLayout(compositeMainStackLayout);
		final FormData fd_compositeMain = new FormData();
		fd_compositeMain.bottom = new FormAttachment(100, 0);
		fd_compositeMain.right = new FormAttachment(100, 0);
		fd_compositeMain.left = new FormAttachment(0, 130);
		compositeMain.setLayoutData(fd_compositeMain);
		// [end]

		// [start] 左侧Composite
		compositeLeftList = new Composite(shell, SWT.NONE);
		compositeLeftList.setLayout(new GridLayout());
		final FormData fd_compositeLeftList = new FormData();
		fd_compositeLeftList.top = new FormAttachment(compositeMain, 0, SWT.TOP);
		fd_compositeLeftList.bottom = new FormAttachment(100, 0);
		fd_compositeLeftList.right = new FormAttachment(0, 130);
		fd_compositeLeftList.left = new FormAttachment(0, 0);
		compositeLeftList.setLayoutData(fd_compositeLeftList);
		// [end]

		// 主菜单栏
		mainMenu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(mainMenu);
		// [start] 文件菜单
		menuFile = new MenuItem(mainMenu, SWT.CASCADE);
		menuFile.setText("文件"); //$NON-NLS-1$

		filemenu = new Menu(menuFile);
		menuFile.setMenu(filemenu);

		menuFileImport = new MenuItem(filemenu, SWT.NONE);
		menuFileImport.addSelectionListener(new MenuFileImportSelectionListener());
		menuFileImport.setText("导入"); //$NON-NLS-1$

		menuFileExport = new MenuItem(filemenu, SWT.NONE);
		menuFileExport.addSelectionListener(new MenuFileExportSelectionListener());
		menuFileExport.setText("导出"); //$NON-NLS-1$
		// [end]

		// [start] “统计”菜单
		menustat = new MenuItem(mainMenu, SWT.CASCADE);
		menustat.setText("统计"); //$NON-NLS-1$

		statmenu = new Menu(menustat);
		menustat.setMenu(statmenu);

		stat = new MenuItem(statmenu, SWT.NONE);
		stat.setText("统计结果"); //$NON-NLS-1$
		stat.addSelectionListener(new SelectionAdapter()
		{

			public void widgetSelected(SelectionEvent e)
			{
				System.out.println(Messages.getString("MainWindow.statics")); //$NON-NLS-1$
				StatObserver observer = new StatObserver();
				GetStatResultResult res = logicCenter.getStatResult();
				res.addObserver(observer);
				statDialog = new StaticDialog(shell, SWT.NONE);
				statDialog.open();
			}
		});
		// [end]

		// [start] “帮助”菜单
		submenuMainHelp = new MenuItem(mainMenu, SWT.CASCADE);
		submenuMainHelp.setText(Messages.getString("MainWindow.Help")); //$NON-NLS-1$

		menuMainAbout = new Menu(submenuMainHelp);
		submenuMainHelp.setMenu(menuMainAbout);

		menuItemMainHelpAbout = new MenuItem(menuMainAbout, SWT.NONE);
		menuItemMainHelpAbout.setText(Messages.getString("MainWindow.About")); //$NON-NLS-1$
		// [end]

		// 主工具栏
		toolBarMain = new ToolBar(shell, SWT.NONE);
		fd_compositeMain.top = new FormAttachment(toolBarMain, 0, SWT.BOTTOM);

		// [start] 个人信息composite
//		scrolledCompositeInfo = new ScrolledComposite(compositeMain, SWT.V_SCROLL | SWT.H_SCROLL);
//		scrolledCompositeInfo.getVerticalBar().setThumb(0);
//		scrolledCompositeInfo.getVerticalBar().setIncrement(10);
//
//		compositeInfo = new Composite(scrolledCompositeInfo, SWT.NONE);
//		compositeInfo.setLayout(new FormLayout());
//
//		canvasInfoAvatar = new Canvas(compositeInfo, SWT.DOUBLE_BUFFERED);
//		canvasInfoAvatar.addPaintListener(new CanvasInfoAvatarPaintListener());
//		final FormData fd_canvasInfoAvatar = new FormData();
//		fd_canvasInfoAvatar.left = new FormAttachment(0, 15);
//		fd_canvasInfoAvatar.right = new FormAttachment(0, 135);
//		fd_canvasInfoAvatar.bottom = new FormAttachment(0, 160);
//		fd_canvasInfoAvatar.top = new FormAttachment(0, 40);
//		canvasInfoAvatar.setLayoutData(fd_canvasInfoAvatar);
//		canvasInfoAvatar.setData(SWTResourceManager.getImage(MainWindow.class, "/img/nullAvatar.jpg"));
//
//		buttonInfoChangeAvatar = new Button(compositeInfo, SWT.NONE);
//		buttonInfoChangeAvatar.addSelectionListener(new ButtonInfoChangeAvatarSelectionListener());
//		final FormData fd_buttonInfoChangeAvatar = new FormData();
//		fd_buttonInfoChangeAvatar.top = new FormAttachment(canvasInfoAvatar, 5, SWT.BOTTOM);
//		fd_buttonInfoChangeAvatar.right = new FormAttachment(0, 109);
//		fd_buttonInfoChangeAvatar.left = new FormAttachment(0, 40);
//		buttonInfoChangeAvatar.setLayoutData(fd_buttonInfoChangeAvatar);
//		buttonInfoChangeAvatar.setText("更换头像...");
//
//		groupInfoPersonal = new Group(compositeInfo, SWT.NONE);
//		groupInfoPersonal.setText("个人");
//		final FormData fd_groupInfoPersonal = new FormData();
//		fd_groupInfoPersonal.right = new FormAttachment(0, 585);
//		fd_groupInfoPersonal.left = new FormAttachment(0, 15);
//		fd_groupInfoPersonal.bottom = new FormAttachment(0, 490);
//		fd_groupInfoPersonal.top = new FormAttachment(0, 200);
//		groupInfoPersonal.setLayoutData(fd_groupInfoPersonal);
//
//		labelInfoName = new Label(groupInfoPersonal, SWT.NONE);
//		labelInfoName.setBounds(38, 34, 24, 17);
//		labelInfoName.setText("姓名");
//
//		textInfoName = new Text(groupInfoPersonal, SWT.BORDER);
//		textInfoName.setBounds(86, 34, 142, 23);
//
//		textInfoNickName = new Text(groupInfoPersonal, SWT.BORDER);
//		textInfoNickName.setBounds(363, 34, 142, 23);
//
//		labelInfoNickName = new Label(groupInfoPersonal, SWT.NONE);
//		labelInfoNickName.setBounds(314, 34, 24, 17);
//		labelInfoNickName.setText("昵称");
//
//		labelInfoCellphone = new Label(groupInfoPersonal, SWT.NONE);
//		labelInfoCellphone.setBounds(38, 115, 24, 17);
//		labelInfoCellphone.setText("手机");
//
//		textInfoCellphone = new Text(groupInfoPersonal, SWT.BORDER);
//		textInfoCellphone.setBounds(86, 112, 142, 23);
//		textInfoCellphone.addFocusListener(new TextInfoCellphoneFocusListener());
//
//		labelInfoBirthday = new Label(groupInfoPersonal, SWT.NONE);
//		labelInfoBirthday.setBounds(314, 74, 24, 17);
//		labelInfoBirthday.setText("生日");
//
//		dateTimeBirthday = new DateTime(groupInfoPersonal, SWT.DATE);
//		dateTimeBirthday.setBounds(362, 72, 93, 24);
//
//		labelInfoEmail = new Label(groupInfoPersonal, SWT.NONE);
//		labelInfoEmail.setBounds(314, 115, 36, 17);
//		labelInfoEmail.setText("E-mail");
//
//		textInfoEmail = new Text(groupInfoPersonal, SWT.BORDER);
//		textInfoEmail.setBounds(363, 112, 142, 23);
//		textInfoEmail.addFocusListener(new TextInfoEmailFocusListener());
//
//		labelInfoQQNumber = new Label(groupInfoPersonal, SWT.NONE);
//		labelInfoQQNumber.setBounds(38, 150, 24, 23);
//		labelInfoQQNumber.setText("QQ");
//
//		textInfoQQNumber = new Text(groupInfoPersonal, SWT.BORDER);
//		textInfoQQNumber.setBounds(86, 150, 142, 23);
//		textInfoQQNumber.addFocusListener(new TextInfoQQNumberFocusListener());
//
//		labelInfoMSN = new Label(groupInfoPersonal, SWT.NONE);
//		labelInfoMSN.setBounds(314, 150, 36, 17);
//		labelInfoMSN.addFocusListener(new LabelInfoMSNFocusListener());
//		labelInfoMSN.setText("MSN");
//
//		textInfoMSN = new Text(groupInfoPersonal, SWT.BORDER);
//		textInfoMSN.setBounds(362, 150, 142, 23);
//
//		labelInfoDesc = new Label(groupInfoPersonal, SWT.NONE);
//		labelInfoDesc.setBounds(38, 197, 48, 23);
//		labelInfoDesc.setText("个人描述");
//
//		textInfoDesc = new Text(groupInfoPersonal, SWT.BORDER);
//		textInfoDesc.setBounds(98, 197, 427, 80);
//
//		labelInfoGender = new Label(groupInfoPersonal, SWT.NONE);
//		labelInfoGender.setBounds(38, 74, 24, 17);
//		labelInfoGender.setText("性别");
//
//		comboInfoGender = new Combo(groupInfoPersonal, SWT.READ_ONLY);
//		comboInfoGender.setBounds(86, 71, 67, 25);
//		comboInfoGender.add("不公开");
//		comboInfoGender.add("男");
//		comboInfoGender.add("女");
//		comboInfoGender.setData("不公开", 0);
//		comboInfoGender.setData("男", 1);
//		comboInfoGender.setData("女", 2);
//		comboInfoGender.select(0);
//
//		groupInfoFamily = new Group(compositeInfo, SWT.NONE);
//		final FormData fd_groupInfoFamily = new FormData();
//		fd_groupInfoFamily.bottom = new FormAttachment(0, 605);
//		fd_groupInfoFamily.right = new FormAttachment(groupInfoPersonal, 0, SWT.RIGHT);
//		fd_groupInfoFamily.top = new FormAttachment(0, 510);
//		fd_groupInfoFamily.left = new FormAttachment(groupInfoPersonal, 0, SWT.LEFT);
//		groupInfoFamily.setLayoutData(fd_groupInfoFamily);
//		groupInfoFamily.setText("家庭");
//
//		labelInfoFamilyPhone = new Label(groupInfoFamily, SWT.NONE);
//		labelInfoFamilyPhone.setBounds(29, 30, 48, 17);
//		labelInfoFamilyPhone.setText("家庭电话");
//
//		textInfoFamilyPhone = new Text(groupInfoFamily, SWT.BORDER);
//		textInfoFamilyPhone.setBounds(101, 24, 142, 23);
//		textInfoFamilyPhone.addFocusListener(new TextInfoFamilyPhoneFocusListener());
//
//		labelInfoFamilyAddress = new Label(groupInfoFamily, SWT.NONE);
//		labelInfoFamilyAddress.setBounds(29, 65, 48, 17);
//		labelInfoFamilyAddress.setText("家庭住址");
//
//		textInfoFamilyAddress = new Text(groupInfoFamily, SWT.BORDER);
//		textInfoFamilyAddress.setBounds(101, 59, 282, 23);
//
//		groupInfoGroup = new Group(compositeInfo, SWT.NONE);
//		final FormData fd_groupInfoGroup = new FormData();
//		fd_groupInfoGroup.bottom = new FormAttachment(0, 765);
//		fd_groupInfoGroup.top = new FormAttachment(0, 625);
//		fd_groupInfoGroup.right = new FormAttachment(groupInfoFamily, 570, SWT.LEFT);
//		fd_groupInfoGroup.left = new FormAttachment(groupInfoFamily, 0, SWT.LEFT);
//		groupInfoGroup.setLayoutData(fd_groupInfoGroup);
//		groupInfoGroup.setText("单位");
//
//		labelInfoCompany = new Label(groupInfoGroup, SWT.NONE);
//		labelInfoCompany.setBounds(35, 32, 24, 17);
//		labelInfoCompany.setText("单位");
//
//		textInfoCompany = new Text(groupInfoGroup, SWT.BORDER);
//		textInfoCompany.setBounds(110, 32, 142, 23);
//
//		labelInfoTitle = new Label(groupInfoGroup, SWT.NONE);
//		labelInfoTitle.setBounds(311, 32, 24, 17);
//		labelInfoTitle.setText("头衔");
//
//		textInfoTitle = new Text(groupInfoGroup, SWT.BORDER);
//		textInfoTitle.setBounds(360, 32, 142, 23);
//
//		labelInfoCompanyPhone = new Label(groupInfoGroup, SWT.NONE);
//		labelInfoCompanyPhone.setBounds(35, 70, 48, 17);
//		labelInfoCompanyPhone.setText("单位电话");
//
//		textInfoCompanyPhone = new Text(groupInfoGroup, SWT.BORDER);
//		textInfoCompanyPhone.setBounds(110, 72, 142, 23);
//		textInfoCompanyPhone.addFocusListener(new TextInfoCompanyPhoneFocusListener());
//
//		labelInfoCompanyAddress = new Label(groupInfoGroup, SWT.NONE);
//		labelInfoCompanyAddress.setBounds(35, 107, 48, 17);
//		labelInfoCompanyAddress.setText("单位地址");
//
//		textInfoCompanyAddress = new Text(groupInfoGroup, SWT.BORDER);
//		textInfoCompanyAddress.setBounds(110, 109, 282, 23);
//
//		groupInfoCustom = new Group(compositeInfo, SWT.NONE);
//		final FormData fd_groupInfoCustom = new FormData();
//		fd_groupInfoCustom.right = new FormAttachment(groupInfoGroup, 0, SWT.RIGHT);
//		fd_groupInfoCustom.top = new FormAttachment(0, 800);
//		fd_groupInfoCustom.left = new FormAttachment(groupInfoPersonal, 0, SWT.LEFT);
//		groupInfoCustom.setLayoutData(fd_groupInfoCustom);
//		groupInfoCustom.setLayout(new FormLayout());
//		groupInfoCustom.setText("自定义");
//
//		labelInfoStatus = new Label(compositeInfo, SWT.NONE);
//		final FormData fd_labelInfoStatus = new FormData();
//		fd_labelInfoStatus.top = new FormAttachment(canvasInfoAvatar, 0, SWT.TOP);
//		fd_labelInfoStatus.left = new FormAttachment(0, 170);
//		labelInfoStatus.setLayoutData(fd_labelInfoStatus);
//		labelInfoStatus.setText("我的状态");
//
//		textInfoStatus = new Text(compositeInfo, SWT.BORDER);
//		final FormData fd_textInfoStatus = new FormData();
//		fd_textInfoStatus.bottom = new FormAttachment(buttonInfoChangeAvatar, 0, SWT.BOTTOM);
//		fd_textInfoStatus.right = new FormAttachment(groupInfoPersonal, 0, SWT.RIGHT);
//		fd_textInfoStatus.top = new FormAttachment(labelInfoStatus, 5, SWT.BOTTOM);
//		fd_textInfoStatus.left = new FormAttachment(labelInfoStatus, 0, SWT.LEFT);
//		textInfoStatus.setLayoutData(fd_textInfoStatus);
//
//		toolBarInfo = new ToolBar(compositeInfo, SWT.NONE);
//		final FormData fd_toolBarInfo = new FormData();
//		fd_toolBarInfo.top = new FormAttachment(0, 5);
//		fd_toolBarInfo.left = new FormAttachment(0, 5);
//		toolBarInfo.setLayoutData(fd_toolBarInfo);
//
//		toolItemInfoSave = new ToolItem(toolBarInfo, SWT.PUSH);
//		toolItemInfoSave.addSelectionListener(new ToolItemInfoSaveSelectionListener());
//		toolItemInfoSave.setToolTipText("保存对个人信息的修改");
//		toolItemInfoSave.setText("存");
//
//		toolItemInfoReset = new ToolItem(toolBarInfo, SWT.PUSH);
//		toolItemInfoReset.addSelectionListener(new ToolItemInfoResetSelectionListener());
//		toolItemInfoReset.setToolTipText("放弃所有修改");
//		toolItemInfoReset.setText("重");
//
//		toolItemInfoUpload = new ToolItem(toolBarInfo, SWT.PUSH);
//		toolItemInfoUpload.addSelectionListener(new ToolItemInfoUploadSelectionListener());
//		toolItemInfoUpload.setToolTipText("将个人信息更新到服务器");
//		toolItemInfoUpload.setText("上");
//
//		toolItemInfoDownload = new ToolItem(toolBarInfo, SWT.PUSH);
//		toolItemInfoDownload.addSelectionListener(new ToolItemInfoDownloadSelectionListener());
//		toolItemInfoDownload.setToolTipText("从服务器上下载个人信息");
//		toolItemInfoDownload.setText("下");
//
//		compositeInfo.setSize(630, 1000);
//		scrolledCompositeInfo.setContent(compositeInfo);

		// [end]

		// [start] 通讯录composite

		compositeAddress = new Composite(compositeMain, SWT.NONE);
		compositeAddress.setVisible(false);
		compositeAddress.setLayout(new FormLayout());

		toolBarAddress = new ToolBar(compositeAddress, SWT.NONE);
		final FormData fd_toolBarAddress = new FormData();
		fd_toolBarAddress.top = new FormAttachment(0, 5);
		fd_toolBarAddress.left = new FormAttachment(0, 0);
		toolBarAddress.setLayoutData(fd_toolBarAddress);

		toolItemAddressAddContact = new ToolItem(toolBarAddress, SWT.PUSH);
		toolItemAddressAddContact.addSelectionListener(new ToolItemAddressAddContactSelectionListener());
		toolItemAddressAddContact.setToolTipText(Messages.getString("MainWindow.addContactinfo")); //$NON-NLS-1$
		toolItemAddressAddContact.setText(Messages.getString("MainWindow.addcontact")); //$NON-NLS-1$

		toolItemAddressSep2 = new ToolItem(toolBarAddress, SWT.SEPARATOR);
		toolItemAddressSep2.setText(Messages.getString("MainWindow.newitem")); //$NON-NLS-1$

		toolItemAddressEdit = new ToolItem(toolBarAddress, SWT.PUSH);
		toolItemAddressEdit.addSelectionListener(new ToolItemAddressEditSelectionListener());
		toolItemAddressEdit.setEnabled(false);
		toolItemAddressEdit.setToolTipText(Messages.getString("MainWindow.editcontactoolTip")); //$NON-NLS-1$
		toolItemAddressEdit.setText(Messages.getString("MainWindow.editcontact")); //$NON-NLS-1$

		toolItemAddressPermission = new ToolItem(toolBarAddress, SWT.PUSH);
		toolItemAddressPermission.setEnabled(false);
		toolItemAddressPermission.addSelectionListener(new ToolItemAddressPermissionSelectionListener());
		toolItemAddressPermission.setToolTipText(Messages.getString("MainWindow.editpropertiytip")); //$NON-NLS-1$
		toolItemAddressPermission.setText(Messages.getString("MainWindow.editpropertiy")); //$NON-NLS-1$
		
		toolItemVisibility = new ToolItem(toolBarAddress, SWT.NONE);
		toolItemVisibility.setEnabled(false);
		toolItemVisibility.setToolTipText(Messages.getString("MainWindow.toolItem.toolTipText")); //$NON-NLS-1$
		toolItemVisibility.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				TreeItem current = getCurrentTreeItem();
				UserInfo userInfo = (UserInfo)current.getData();
				if (logicCenter.getLoginUser().isNull()){
					MessageDialog.openError(shell, Messages.getString("MainWindow.setError"), Messages.getString("MainWindow.loginfirst")); //$NON-NLS-1$ //$NON-NLS-2$
					return;					
				}
				if (!((Relation)(userInfo.getInfoField(InfoFieldName.Relation))).isPersonal()){
					MessageDialog.openError(shell, Messages.getString("MainWindow.setError"), Messages.getString("MainWindow.setVisiblerange")); //$NON-NLS-1$ //$NON-NLS-2$
					return;
				}
				SingleNumDialog dialog = new SingleNumDialog(shell);
				//TODO 显示原来的值
				if (dialog.open() == IDialogConstants.OK_ID){
					SetVisibilityResult result = logicCenter.setVisibility(userInfo.getBaseInfo().getID(), dialog.getResult());
					VirtualResultObserver observer = new VirtualResultObserver(shell, Messages.getString("MainWindow.setVisibleSucceed")); //$NON-NLS-1$
					result.addObserver(observer);
				}
			}
		});
		toolItemVisibility.setText(Messages.getString("MainWindow.toolItem.text")); //$NON-NLS-1$

		toolItemAddressDel = new ToolItem(toolBarAddress, SWT.PUSH);
		toolItemAddressDel.addSelectionListener(new ToolItemAddressDelSelectionListener());
		toolItemAddressDel.setEnabled(false);
		toolItemAddressDel.setToolTipText(Messages.getString("MainWindow.deleteContactip")); //$NON-NLS-1$
		toolItemAddressDel.setText(Messages.getString("MainWindow.delContact")); //$NON-NLS-1$

		toolItemAddressCustomGroup = new ToolItem(toolBarAddress, SWT.DROP_DOWN);
		toolItemAddressCustomGroup.setEnabled(false);
		toolItemAddressCustomGroup.setToolTipText(Messages.getString("MainWindow.15")); //$NON-NLS-1$
		toolItemAddressCustomGroup.setText(Messages.getString("MainWindow.16")); //$NON-NLS-1$

		toolItemAddressCustomGroupMenu = new Menu(toolBarAddress);
		addDropDown(toolItemAddressCustomGroup, toolItemAddressCustomGroupMenu);

		toolItemAddressSep1 = new ToolItem(toolBarAddress, SWT.SEPARATOR);
		toolItemAddressSep1.setText(Messages.getString("MainWindow.17")); //$NON-NLS-1$

		toolItemAddressSyncLocal = new ToolItem(toolBarAddress, SWT.PUSH);
		toolItemAddressSyncLocal.addSelectionListener(new ToolItemAddressSyncLocalSelectionListener());
		toolItemAddressSyncLocal.setToolTipText(Messages.getString("MainWindow.18")); //$NON-NLS-1$
		toolItemAddressSyncLocal.setText(Messages.getString("MainWindow.SyncLocal")); //$NON-NLS-1$

		toolItemAddressSyncRemote = new ToolItem(toolBarAddress, SWT.PUSH);
		toolItemAddressSyncRemote.addSelectionListener(new ToolItemAddressSyncRemoteSelectionListener());
		toolItemAddressSyncRemote.setToolTipText(Messages.getString("MainWindow.19")); //$NON-NLS-1$
		toolItemAddressSyncRemote.setText(Messages.getString(Messages.getString("MainWindow.20"))); //$NON-teAddress; //$NON-NLS-1$

		labelAddressSearch = new Button(compositeAddress, SWT.NONE);
		labelAddressSearch.addSelectionListener(new LabelAddressSearchSelectionListener());
		final FormData fd_labelAddressSearch = new FormData();
		fd_labelAddressSearch.top = new FormAttachment(toolBarAddress, 0, SWT.TOP);
		labelAddressSearch.setLayoutData(fd_labelAddressSearch);
		labelAddressSearch.setText(Messages.getString("MainWindow.labelAddressSearch.text")); //$NON-NLS-1$

		
		tabFolderAddress = new TabFolder(compositeAddress, SWT.NONE);
		fd_labelAddressSearch.right = new FormAttachment(tabFolderAddress, 0, SWT.RIGHT);
		final FormData fd_tabFolderAddress = new FormData();
		fd_tabFolderAddress.bottom = new FormAttachment(100, 0);
		fd_tabFolderAddress.right = new FormAttachment(100, 0);
		fd_tabFolderAddress.left = new FormAttachment(0, 0);
		fd_tabFolderAddress.top = new FormAttachment(toolBarAddress, 0, SWT.DEFAULT);
		tabFolderAddress.setLayoutData(fd_tabFolderAddress);

		//[start]“联系人”选项卡
		tabItemAddressContact = new TabItem(tabFolderAddress, SWT.NONE);
		tabItemAddressContact.setText(Messages.getString("MainWindow.21")); //$NON-NLS-1$

		treeAddressContact = new Tree(tabFolderAddress, SWT.BORDER);
		treeAddressContact.addMouseListener(new TreeAddressContactMouseListener());
		treeAddressContact.addSelectionListener(new TreeAddressContactSelectionListener());
		treeAddressContact.setSortColumn(null);
		treeAddressContact.setHeaderVisible(true);
		tabItemAddressContact.setControl(treeAddressContact);

		treeAddressContactColumnName = new TreeColumn(treeAddressContact, SWT.NONE);
		treeAddressContactColumnName.setWidth(100);
		treeAddressContactColumnName.setText(Messages.getString("MainWindow.22")); //$NON-NLS-1$

		treeAddressContactColumnNickName = new TreeColumn(treeAddressContact, SWT.NONE);
		treeAddressContactColumnNickName.setWidth(100);
		treeAddressContactColumnNickName.setText(Messages.getString("MainWindow.23")); //$NON-NLS-1$

		treeAddressContactColumnRelation = new TreeColumn(treeAddressContact, SWT.NONE);
		treeAddressContactColumnRelation.setWidth(100);
		treeAddressContactColumnRelation.setText(Messages.getString("MainWindow.24")); //$NON-NLS-1$

		treeAddressContactColumnCellphone = new TreeColumn(treeAddressContact, SWT.NONE);
		treeAddressContactColumnCellphone.setWidth(100);
		treeAddressContactColumnCellphone.setText(Messages.getString("MainWindow.25")); //$NON-NLS-1$

		treeAddressContactColumnEmail = new TreeColumn(treeAddressContact, SWT.NONE);
		treeAddressContactColumnEmail.setWidth(100);
		treeAddressContactColumnEmail.setText(Messages.getString("MainWindow.26")); //$NON-NLS-1$

		treeAddressContactColumnBirth = new TreeColumn(treeAddressContact, SWT.NONE);
		treeAddressContactColumnBirth.setWidth(100);
		treeAddressContactColumnBirth.setText(Messages.getString("MainWindow.27")); //$NON-NLS-1$
		//[end]
		
		//[start]“被授权联系人”选项卡
		tabItemAddressPermit = new TabItem(tabFolderAddress, SWT.NONE);
		tabItemAddressPermit.setText(Messages.getString("MainWindow.28")); //$NON-NLS-1$

		treeAddressPermit = new Tree(tabFolderAddress, SWT.BORDER);
		treeAddressPermit.addSelectionListener(new TreeAddressContactSelectionListener());
		treeAddressPermit.setHeaderVisible(true);
		tabItemAddressPermit.setControl(treeAddressPermit);

		treeAddressPermitColumnName = new TreeColumn(treeAddressPermit, SWT.NONE);
		treeAddressPermitColumnName.setWidth(100);
		treeAddressPermitColumnName.setText(Messages.getString("MainWindow.29")); //$NON-NLS-1$

		treeAddressPermitColumnNickName = new TreeColumn(treeAddressPermit, SWT.NONE);
		treeAddressPermitColumnNickName.setWidth(100);
		treeAddressPermitColumnNickName.setText(Messages.getString("MainWindow.30")); //$NON-NLS-1$

		treeAddressPermitColumnRelation = new TreeColumn(treeAddressPermit, SWT.NONE);
		treeAddressPermitColumnRelation.setWidth(100);
		treeAddressPermitColumnRelation.setText(Messages.getString("MainWindow.31")); //$NON-NLS-1$

		treeAddressPermitColumnCellphone = new TreeColumn(treeAddressPermit, SWT.NONE);
		treeAddressPermitColumnCellphone.setWidth(100);
		treeAddressPermitColumnCellphone.setText(Messages.getString("MainWindow.32")); //$NON-NLS-1$

		treeAddressPermitColumnEmail = new TreeColumn(treeAddressPermit, SWT.NONE);
		treeAddressPermitColumnEmail.setWidth(100);
		treeAddressPermitColumnEmail.setText(Messages.getString("MainWindow.33")); //$NON-NLS-1$

		treeAddressPermitColumnBirth = new TreeColumn(treeAddressPermit, SWT.NONE);
		treeAddressPermitColumnBirth.setWidth(100);
		treeAddressPermitColumnBirth.setText(Messages.getString("MainWindow.34")); //$NON-NLS-1$
		//[end]
		
		//[start]“搜索结果”选项卡
		tabItemAddressSearchResult = new TabItem(tabFolderAddress, SWT.NONE);
		tabItemAddressSearchResult.setText(Messages.getString("MainWindow.35")); //$NON-NLS-1$

		treeAddressSearchResult = new Tree(tabFolderAddress, SWT.BORDER);
		treeAddressSearchResult.setHeaderVisible(true);
		tabItemAddressSearchResult.setControl(treeAddressSearchResult);

		
		treeAddressSerachResultColumnName = new TreeColumn(treeAddressSearchResult, SWT.NONE);
		treeAddressSerachResultColumnName.setWidth(100);
		treeAddressSerachResultColumnName.setText(Messages.getString("MainWindow.36")); //$NON-NLS-1$

		treeAddressSearchResultColumnNickName = new TreeColumn(treeAddressSearchResult, SWT.NONE);
		treeAddressSearchResultColumnNickName.setWidth(100);
		treeAddressSearchResultColumnNickName.setText(Messages.getString("MainWindow.37")); //$NON-NLS-1$

		treeAddressSearchResultColumnRelation = new TreeColumn(treeAddressSearchResult, SWT.NONE);
		treeAddressSearchResultColumnRelation.setWidth(100);
		treeAddressSearchResultColumnRelation.setText(Messages.getString("MainWindow.38")); //$NON-NLS-1$

		treeAddressSearchResultColumnCellphone = new TreeColumn(treeAddressSearchResult, SWT.NONE);
		treeAddressSearchResultColumnCellphone.setWidth(100);
		treeAddressSearchResultColumnCellphone.setText(Messages.getString("MainWindow.39")); //$NON-NLS-1$

		treeAddressSearchResultColumnEmail = new TreeColumn(treeAddressSearchResult, SWT.NONE);
		treeAddressSearchResultColumnEmail.setWidth(100);
		treeAddressSearchResultColumnEmail.setText(Messages.getString("MainWindow.40")); //$NON-NLS-1$

		treeAddressSearchResultColumnBirth = new TreeColumn(treeAddressSearchResult, SWT.NONE);
		treeAddressSearchResultColumnBirth.setWidth(100);
		treeAddressSearchResultColumnBirth.setText(Messages.getString("MainWindow.41")); //$NON-NLS-1$
		
		//[end]
		
		// stackLayout.topControl = scrolledCompositeInfo;

		final FormData fd_button = new FormData();
		fd_button.right = new FormAttachment(0, 115);
		fd_button.left = new FormAttachment(0, 50);
		fd_button.bottom = new FormAttachment(0, 177);
		fd_button.top = new FormAttachment(0, 150);

		// stackLayout.topControl = scrolledCompositeInfo;
		// stackLayout.topControl = scrolledCompositeInfo;
		final FormData fd_toolBarMain = new FormData();
		fd_toolBarMain.top = new FormAttachment(0, 0);
		fd_toolBarMain.right = new FormAttachment(compositeMain, 0, SWT.RIGHT);
		fd_toolBarMain.left = new FormAttachment(compositeLeftList, 0, SWT.LEFT);
		toolBarMain.setLayoutData(fd_toolBarMain);

		// [end]

		// [start] 主工具栏按钮
		toolItemMainRegister = new ToolItem(toolBarMain, SWT.PUSH);
		toolItemMainRegister.addSelectionListener(new ToolItemMainRegisterSelectionListener());
		toolItemMainRegister.setText(Messages.getString("MainWindow.Register")); //$NON-NLS-1$

		toolItemMainLogin = new ToolItem(toolBarMain, SWT.PUSH);
		toolItemMainLogin.addSelectionListener(new ToolItemMainLoginSelectionListener());
		toolItemMainLogin.setText(Messages.getString("MainWindow.Login")); //$NON-NLS-1$

		toolItemMainLogout = new ToolItem(toolBarMain, SWT.PUSH);
		toolItemMainLogout.addSelectionListener(new ToolItemMainLogoutSelectionListener());
		toolItemMainLogout.setText(Messages.getString("MainWindow.Logout")); //$NON-NLS-1$
		// [end]

		// [start] 左侧按钮
		listButtonUserInfo = new Button(compositeLeftList, SWT.NONE);
		listButtonUserInfo.addSelectionListener(new ListButtonUserInfoSelectionListener());
		final GridData gd_listButtonUserInfo = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gd_listButtonUserInfo.heightHint = 35;
		listButtonUserInfo.setLayoutData(gd_listButtonUserInfo);
		listButtonUserInfo.setText(Messages.getString("MainWindow.UserInfo")); //$NON-NLS-1$

		listButtonAddressBook = new Button(compositeLeftList, SWT.TOGGLE);
		listButtonAddressBook.setSelection(true);
		listButtonAddressBook.addSelectionListener(new ListButtonAddressBookSelectionListener());
		final GridData gd_listButtonAddressBook = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gd_listButtonAddressBook.heightHint = 35;
		listButtonAddressBook.setLayoutData(gd_listButtonAddressBook);
		listButtonAddressBook.setText(Messages.getString("MainWindow.AddressBook")); //$NON-NLS-1$

		listButtonGroup = new Button(compositeLeftList, SWT.TOGGLE);
		listButtonGroup.addSelectionListener(new ListButtonGroupSelectionListener());
		final GridData gd_listButtonGroup = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gd_listButtonGroup.heightHint = 35;
		listButtonGroup.setLayoutData(gd_listButtonGroup);
		listButtonGroup.setText(Messages.getString("MainWindow.Group")); //$NON-NLS-1$

		listButtonSearch = new Button(compositeLeftList, SWT.TOGGLE);
		listButtonSearch.addSelectionListener(new ListButtonSearchSelectionListener());
		final GridData gd_listButtonSearch = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gd_listButtonSearch.heightHint = 35;
		listButtonSearch.setLayoutData(gd_listButtonSearch);
		listButtonSearch.setText(Messages.getString("MainWindow.RelationCube")); //$NON-NLS-1$

		final GridData gd_listButtonMessageBox = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gd_listButtonMessageBox.heightHint = 35;
		listButtonMessageBox = new Button(compositeLeftList, SWT.TOGGLE);
		listButtonMessageBox.setLayoutData(gd_listButtonMessageBox);
		listButtonMessageBox.setText(Messages.getString("MainWindow.listButtonMessageBox.text")); //$NON-NLS-1$
		listButtonMessageBox.addSelectionListener(new ListButtonMessageBoxListener());
		// [end]

		// [start] MessageBox Composite
		// (new ListButtonUserInfoSelectionListener()).widgetSelected(null);
		// MessageBox
		// [end]


		// [start] 群组Composite
		compositeGroup = new GroupComposite(compositeMain, SWT.NONE);

		compositeSearch = new SearchComposite(compositeMain, SWT.NONE);

		compositeMessageBox = new MessageBoxComposite(compositeMain, SWT.NONE);
		compositeMainStackLayout.topControl = compositeAddress;
		// [end]	
	}

	/**
	 * 在TreeItem中加入子结点
	 * 
	 * @param parentItem
	 *            父结点
	 * @param items
	 *            子结点内容
	 * @return 子节点
	 */
	private TreeItem createTreeSubItem(TreeItem parentItem, String... items)
	{
		TreeItem childItem = new TreeItem(parentItem, SWT.NONE);

		for (int i = 0; i < items.length; i++)
		{
			childItem.setText(i, items[i]);
		}
		return childItem;
	}

	/**
	 * 取得联系人树或授权联系人树的当前选中项
	 * 
	 * @return
	 */
	private TreeItem getCurrentTreeItem()
	{
		TreeItem[] current = null;
		if (tabFolderAddress.getSelection()[0] == tabItemAddressContact)
		{
			current = treeAddressContact.getSelection();
		}
		else if (tabFolderAddress.getSelection()[0] == tabItemAddressPermit)
		{
			current = treeAddressPermit.getSelection();
		}
		else if (tabFolderAddress.getSelection()[0] == tabItemAddressSearchResult)
		{
			current = treeAddressSearchResult.getSelection();
		}

		if (current != null && current.length > 0)
		{
			return current[0];
		}
		else
			return null;
		
	}
	
	/**
	 * 取得当前所在的选项卡，可能是同步联系人或被授权联系人
	 * @return
	 */
	private ContactTabType getCurrentContactTab()
	{
		if(tabFolderAddress.getSelection().length == 0) return ContactTabType.Null;
		
		if (tabFolderAddress.getSelection()[0] == tabItemAddressContact)
		{
			return ContactTabType.Synchronization;
		}
		else if(tabFolderAddress.getSelection()[0] == tabItemAddressPermit)
		{
			return ContactTabType.Permission;
		}
		else if(tabFolderAddress.getSelection()[0] == tabItemAddressSearchResult)
		{
			return ContactTabType.SearchResult;
		}
		else
		{
			return ContactTabType.Null;
		}
	}

	/**
	 * 数据初始化函数
	 */
	private void dataInit()
	{
		Gui gui = new GuiImp(shell);
		logicCenter.setUI(gui);
		
		// [start] 个人信息 初始化
		// [end]

		// [start] 同步联系人
		allContactsBox = logicCenter.getAllContactsBox();
		allContactsBox.addObserver(new ContactRefreshObserver());
		compositeGroup.setAllContactsBox(allContactsBox);
		// TODO ERRORED的处理
		// [end]

		// [start] MessageBox
		// // 正常情况下，应该登录后获取MessageBox，当前只是测试
		// MessageBoxObserver mObserver = new MessageBoxObserver();
		// messageBox = logicCenter.getMessageBox();
		// messageBox.addObserver(mObserver);
		// // ERRORED的处理
		// [end]
	}

	
	// [start] 主工具栏 事件
	/**
	 * 注册
	 */
	private class ToolItemMainRegisterSelectionListener extends SelectionAdapter {
		public void widgetSelected(final SelectionEvent e)
		{
			UserInfo newUser = new UserInfo();
			UserInfoDialog userInfoDialog = new UserInfoDialog(shell,Messages.getString("MainWindow.42"), //$NON-NLS-1$
					UserInfoTableType.Register, newUser);
			
			if(userInfoDialog.OpenEditInfo() == IDialogConstants.OK_ID)
			{
				RegisterResult result = logicCenter.register(newUser.getBaseInfo(), new Password(userInfoDialog.getPassword()));
				result.addObserver(new RegisterResultObserver());
			}
		}
	}
	
	/**
	 * 注册 Observer
	 * @author Wander
	 *
	 */
	class RegisterResultObserver implements Observer
	{

		@Override
		public void update(Observable o, Object arg)
		{
			Display.getDefault().syncExec(new RegisterResultTask((RegisterResult)o));
		}
		
	}
	
	/**
	 * 注册 Task
	 * @author Wander
	 *
	 */
	class RegisterResultTask implements Runnable
	{
		private RegisterResult result;
		
		public RegisterResultTask(RegisterResult result)
		{
			this.result = result;
		}

		@Override
		public void run()
		{
			VirtualState state = result.getState();
			if(state == VirtualState.PREPARED)
			{
				MessageDialog.openWarning(shell, Messages.getString("MainWindow.43"), Messages.getString("MainWindow.44")); //$NON-NLS-1$ //$NON-NLS-2$
			}
			else if(state == VirtualState.ERRORED)
			{
				MessageDialog.openWarning(shell, Messages.getString("MainWindow.45"), result.getError().toString()); //$NON-NLS-1$
			}
			
		}
	}
	
	/**
	 * 注销
	 * @author Wander
	 *
	 */
	private class ToolItemMainLogoutSelectionListener extends SelectionAdapter {
		public void widgetSelected(final SelectionEvent e)
		{
			if(logicCenter.getLoginUser() != null &&
					MessageDialog.openConfirm(shell, Messages.getString("MainWindow.46"), Messages.getString("MainWindow.47"))) //$NON-NLS-1$ //$NON-NLS-2$
			{
				try
				{
					logicCenter.logout();
					shell.setText(Messages.getString("MainWindow.48"));					 //$NON-NLS-1$
				} catch (RemoteException e1)
				{
					MessageDialog.openWarning(shell, Messages.getString("MainWindow.49"), Messages.getString("MainWindow.50")); //$NON-NLS-1$ //$NON-NLS-2$
					e1.printStackTrace();
				}
				
				//shell.setText("PhoneMe");
				//TODO: 这行代码好像没用
			}
		}
	}
	
	/**
	 * 登录
	 * @author Wander
	 *
	 */
	private class ToolItemMainLoginSelectionListener extends SelectionAdapter {
		public void widgetSelected(final SelectionEvent e)
		{
			LoginDialog loginDialog = new LoginDialog(shell);
			
			if(loginDialog.open() == IDialogConstants.OK_ID)
			{
				LoginResult result = logicCenter.login(loginDialog.getIdenticalField(), new Password(loginDialog.getPassword()));
				result.addObserver(new LoginResultObserver());
			}
		}
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
				MessageDialog.openWarning(shell, Messages.getString("MainWindow.51"), error.toString()); //$NON-NLS-1$
			}
			else if(state == VirtualState.PREPARED)
			{
				BaseUserInfo loginUser = logicCenter.getLoginUser();
				MessageDialog.openInformation(shell, Messages.getString("MainWindow.52"),Messages.getString("MainWindow.53")); //$NON-NLS-1$ //$NON-NLS-2$
				shell.setText(Messages.getString("MainWindow.54") + String.format(Messages.getString("MainWindow.55"), loginUser.getInfoField(Messages.getString("MainWindow.56")))); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				//TODO: 上句好像没用
				
				compositeMessageBox.GetMessage();
				
				allPerContactsBox = logicCenter.getAllPerContatcsBox();
				if(allPerContactsBox != null)
					allPerContactsBox.addObserver(new ContactPerRefreshObserver());
			}
		}
	}
	
	// [end]
	
	// [start] 左侧选择按钮 事件
	private class ListButtonUserInfoSelectionListener extends SelectionAdapter
	{
		public void widgetSelected(final SelectionEvent e)
		{
//			listButtonUserInfo.setSelection(true);
//			scrolledCompositeInfo.setVisible(true);
//			compositeMainStackLayout.topControl = scrolledCompositeInfo;
//			listButtonAddressBook.setSelection(false);
//			compositeAddress.setVisible(false);
//			listButtonRenlifang.setVisible(false);
//			compositeMessage.setVisible(false);
//			listButtonGroup.setSelection(false);
//			listButtonSearch.setSelection(false);
//			listButtonMessageBox.setSelection(false);
			
			BaseUserInfo loginBaseUserInfo = logicCenter.getLoginUser();
			if(loginBaseUserInfo.isNull())
			{
				MessageDialog.openWarning(shell, Messages.getString("MainWindow.57"), Messages.getString("MainWindow.58")); //$NON-NLS-1$ //$NON-NLS-2$
				(new ToolItemMainLoginSelectionListener()).widgetSelected(null);
			}
			else
			{
				UserInfoDialog userInfoDialog = new UserInfoDialog(shell, Messages.getString("MainWindow.59"),  //$NON-NLS-1$
						UserInfoTableType.Owner,
						new UserInfo(loginBaseUserInfo));
				if(userInfoDialog.OpenEditInfo() == IDialogConstants.OK_ID)
				{
					logicCenter.editMyBaseInfo(loginBaseUserInfo, new Password(userInfoDialog.getPassword()));
					//TODO: Add Observer
				}
			}
		
		}
	}

	private class ListButtonAddressBookSelectionListener extends SelectionAdapter
	{
		public void widgetSelected(final SelectionEvent e)
		{
			//listButtonUserInfo.setSelection(false);
			//scrolledCompositeInfo.setVisible(false);
			listButtonAddressBook.setSelection(true);
			compositeAddress.setVisible(true);
			listButtonSearch.setSelection(false);
			compositeSearch.setVisible(false);
			listButtonMessageBox.setSelection(false);
			compositeMessageBox.setVisible(false);
			
			//compositeMainStackLayout.topControl = compositeAddress;

			listButtonGroup.setSelection(false);
			compositeGroup.setVisible(false);
		}
	}

	private class ListButtonGroupSelectionListener extends SelectionAdapter
	{
		public void widgetSelected(final SelectionEvent e)
		{
			if(logicCenter.getLoginUser().isNull())//Added by SpaceFlyer
			{
				MessageDialog.openWarning(shell, Messages.getString("MainWindow.60"), Messages.getString("MainWindow.61")); //$NON-NLS-1$ //$NON-NLS-2$
				(new ToolItemMainLoginSelectionListener()).widgetSelected(null);
			}
			else
			{
				//listButtonUserInfo.setSelection(false);
				//scrolledCompositeInfo.setVisible(false);
				listButtonAddressBook.setSelection(false);
				compositeAddress.setVisible(false);
				listButtonGroup.setSelection(true);
				compositeGroup.setVisible(true);
				listButtonSearch.setSelection(false);
				listButtonMessageBox.setSelection(false);
				compositeSearch.setVisible(false);
				compositeMessageBox.setVisible(false);
			}
		}
	}

	// 选择 人立方 搜索
	private class ListButtonSearchSelectionListener extends SelectionAdapter
	{
		public void widgetSelected(final SelectionEvent e)
		{
			//listButtonUserInfo.setSelection(false);
			//scrolledCompositeInfo.setVisible(false);
			listButtonAddressBook.setSelection(false);
			compositeAddress.setVisible(false);
			listButtonGroup.setSelection(false);
			compositeGroup.setVisible(false);
			listButtonSearch.setSelection(true);
			listButtonMessageBox.setSelection(false);
			compositeSearch.setVisible(true);
			compositeMessageBox.setVisible(false);
			compositeMainStackLayout.topControl = compositeSearch;
			// System.out.println("renlifang");
		}
	}

	private class ListButtonMessageBoxListener extends SelectionAdapter
	{
		public void widgetSelected(final SelectionEvent e)
		{
			if(logicCenter.getLoginUser().isNull())//Added by SpaceFlyer
			{
				MessageDialog.openWarning(shell, Messages.getString("MainWindow.62"), Messages.getString("MainWindow.63")); //$NON-NLS-1$ //$NON-NLS-2$
				(new ToolItemMainLoginSelectionListener()).widgetSelected(null);
			}
			else
			{
				//listButtonUserInfo.setSelection(false);
				//scrolledCompositeInfo.setVisible(false);
				listButtonAddressBook.setSelection(false);
				compositeAddress.setVisible(false);
				listButtonGroup.setSelection(false);
				compositeGroup.setVisible(false);
				listButtonSearch.setSelection(false);
				listButtonMessageBox.setSelection(true);
				compositeSearch.setVisible(false);
				compositeMessageBox.setVisible(true);
				compositeMainStackLayout.topControl = compositeMessageBox;
				// System.out.println("messagebox");
			}
		}
	}

	// [end]

	// [start] 个人信息 相关事件
//	/**
//	 * 绘制头像
//	 */
//	private class CanvasInfoAvatarPaintListener implements PaintListener
//	{
//		public void paintControl(final PaintEvent e)
//		{
//			Image image = (Image) canvasInfoAvatar.getData();
//			if (image != null)
//				e.gc.drawImage(image, 0, 0, image.getBounds().width, image.getBounds().height, 0, 0, canvasInfoAvatar
//						.getBounds().width, canvasInfoAvatar.getBounds().height);
//		}
//	}
//
//	/**
//	 * 更换新头像
//	 * 
//	 * @author Wander
//	 * 
//	 */
//	private class ButtonInfoChangeAvatarSelectionListener extends SelectionAdapter
//	{
//		public void widgetSelected(final SelectionEvent e)
//		{
//			FileDialog dlg = new FileDialog(shell, SWT.OPEN);
//			String fileName = dlg.open();
//			if (fileName != null)
//			{
//				Image image = new Image(null, fileName);
//				canvasInfoAvatar.setData(image);
//				canvasInfoAvatar.redraw();
//				// 检查图片格式，保存用户头像到数据库
//			}
//		}
//	}
//
//	/**
//	 * 检查手机号合法性
//	 * 
//	 * @author Wander
//	 * 
//	 */
//	private class TextInfoCellphoneFocusListener extends FocusAdapter
//	{
//		public void focusLost(final FocusEvent e)
//		{
//			// 检查手机号合法性
//		}
//	}
//
//	/**
//	 * 检查E-mail合法性
//	 * 
//	 * @author Wander
//	 * 
//	 */
//	private class TextInfoEmailFocusListener extends FocusAdapter
//	{
//		public void focusLost(final FocusEvent e)
//		{
//			// 检查E-mail合法性
//		}
//	}
//
//	/**
//	 * 检查QQ号合法性
//	 * 
//	 * @author Wander
//	 * 
//	 */
//	private class TextInfoQQNumberFocusListener extends FocusAdapter
//	{
//		public void focusLost(final FocusEvent e)
//		{
//			// 检查QQ号合法性
//		}
//	}
//
//	/**
//	 * 检查家庭电话合法性
//	 * 
//	 * @author Wander
//	 * 
//	 */
//	private class TextInfoFamilyPhoneFocusListener extends FocusAdapter
//	{
//		public void focusLost(final FocusEvent e)
//		{
//			// 检查家庭电话合法性
//		}
//	}
//
//	/**
//	 * 检查单位电话合法性
//	 * 
//	 * @author Wander
//	 * 
//	 */
//	private class TextInfoCompanyPhoneFocusListener extends FocusAdapter
//	{
//		public void focusLost(final FocusEvent e)
//		{
//			// 检查单位电话合法性
//		}
//	}
//
//	/**
//	 * 检查MSN合法性
//	 * 
//	 * @author Wander
//	 * 
//	 */
//	private class LabelInfoMSNFocusListener extends FocusAdapter
//	{
//		public void focusLost(final FocusEvent e)
//		{
//			// 检查MSN合法性
//		}
//	}
//
//	/**
//	 * 放弃对个人信息的修改
//	 * 
//	 * @author Wander
//	 * 
//	 */
//	private class ToolItemInfoResetSelectionListener extends SelectionAdapter
//	{
//		public void widgetSelected(final SelectionEvent e)
//		{
//			if (MessageDialog.openConfirm(shell, "确认重置", "确实要放弃所有修改吗？"))
//			{
//				// 放弃所有修改
//			}
//		}
//	}
//
//	/**
//	 * 保存对个人信息的修改
//	 * 
//	 * @author Wander
//	 * 
//	 */
//	private class ToolItemInfoSaveSelectionListener extends SelectionAdapter
//	{
//		public void widgetSelected(final SelectionEvent e)
//		{
//			// 将个人信息保存在本地数据库
//		}
//	}
//
//	/**
//	 * 将个人信息上传到服务器
//	 * 
//	 * @author Wander
//	 * 
//	 */
//	private class ToolItemInfoUploadSelectionListener extends SelectionAdapter
//	{
//		public void widgetSelected(final SelectionEvent e)
//		{
//			// 将个人信息上传到服务器
//		}
//	}
//
//	/**
//	 * 从服务器上下载个人信息
//	 * 
//	 * @author Wander
//	 * 
//	 */
//	private class ToolItemInfoDownloadSelectionListener extends SelectionAdapter
//	{
//		public void widgetSelected(final SelectionEvent e)
//		{
//			if (false || // true = 本地的个人信息最后修改时间比服务器的新
//			MessageDialog.openConfirm(shell, "确认下载", "本地的个人信息比服务器的新，确实要用服务器上的数据覆盖本地数据吗？"))
//			{
//				// 从服务器上下载个人信息
//			}
//		}
//	}

	// [end]

	// [start] 通讯录 相关事件
	private enum ContactTabType
	{
		Null,
		Synchronization,
		Permission,
		SearchResult
	}
	
	/**
	 * 联系人或分组被选择
	 */
	private class TreeAddressContactSelectionListener extends SelectionAdapter
	{
		public void widgetSelected(final SelectionEvent e)
		{
			TreeItem current = (TreeItem) e.item;

			if (current.getParentItem() == null) // 选择了分组
			{
				toolItemAddressPermission.setEnabled(false);
				toolItemAddressCustomGroup.setEnabled(false);
				toolItemVisibility.setEnabled(false);

				toolItemAddressPermission.setToolTipText(Messages.getString("MainWindow.64")); //$NON-NLS-1$
				toolItemAddressCustomGroup.setToolTipText(Messages.getString("MainWindow.65")); //$NON-NLS-1$
				
				toolItemAddressEdit.setToolTipText(String.format(Messages.getString("MainWindow.66"), current.getText(0))); //$NON-NLS-1$
				toolItemAddressDel.setToolTipText(String.format(Messages.getString("MainWindow.67"), current.getText(0))); //$NON-NLS-1$
			} else
			// 选择了联系人
			{
				toolItemAddressPermission.setEnabled(getCurrentContactTab() == ContactTabType.Permission);//Modified by SpaceFlyer
				toolItemAddressCustomGroup.setEnabled(true);
				toolItemVisibility.setEnabled(getCurrentContactTab() != ContactTabType.Permission);

				toolItemAddressEdit.setToolTipText(String.format(Messages.getString("MainWindow.68"), current.getText(0))); //$NON-NLS-1$
				toolItemAddressDel.setToolTipText(String.format(Messages.getString("MainWindow.69"), current.getText(0))); //$NON-NLS-1$
				toolItemAddressPermission.setToolTipText(String.format(Messages.getString("MainWindow.70"), current.getText(0))); //$NON-NLS-1$
				toolItemAddressCustomGroup.setToolTipText(String.format(Messages.getString("MainWindow.71"), current.getText(0))); //$NON-NLS-1$

				toolItemAddressCustomGroupNone.setSelection(false);
				for (MenuItem item : toolItemAddressCustomGroupItems)
				{
					item.setSelection(false);
				}

				String cate = ((UserInfo)current.getData()).getCustomInfo().getInfoField(Messages.getString("MainWindow.72")).getStringValue(); //$NON-NLS-1$
				if(cate != null && !cate.isEmpty() && contactsCategory.containsKey(cate))
				{
					System.out.println(contactsCategory.keySet());//TODO TEST BUG（一登录就选择某CATE的联系人，会RE，因为contactsCategory没有任何key）
					int cateKey = (int)contactsCategory.get(cate);
					toolItemAddressCustomGroupItems[cateKey].setSelection(true);
				}
				else
				{
				 toolItemAddressCustomGroupNone.setSelection(true);
				}
			}

			toolItemAddressEdit.setEnabled(true);
			toolItemAddressDel.setEnabled(true);

		}
	}

	/**
	 * 添加联系人
	 * 
	 * @author Wander
	 * 
	 */
	private class ToolItemAddressAddContactSelectionListener extends SelectionAdapter
	{
		public void widgetSelected(final SelectionEvent e)
		{	
			if(getCurrentContactTab() == ContactTabType.Synchronization)
			{
				UserInfo newUser = UserInfo.getNewLocalUser();
				UserInfoDialog userInfoDialog = new UserInfoDialog(shell, Messages.getString("MainWindow.73"), UserInfoTableType.NewLocal, newUser); //$NON-NLS-1$
				userInfoDialog.OpenEditInfo();
				
			}
		}
	}

	/**
	 * 编辑联系人或分组
	 * 
	 * @author Wander
	 * 
	 */
	private class ToolItemAddressEditSelectionListener extends SelectionAdapter
	{
		public void widgetSelected(final SelectionEvent e)
		{
			TreeItem current = getCurrentTreeItem();

			if (current != null)
			{
				if (current.getParentItem() == null)	//编辑分组
				{
					if (!(current.getData() instanceof Integer) || !current.getData().equals(-1))  //不是“未分组”
					{
						InfoFieldFactory factory = InfoFieldFactory.getFactory();
						EditCategoryDialog editGroupDialog = new EditCategoryDialog(shell);
						editGroupDialog.SetCategoryName(current.getText());
						if (editGroupDialog.open() == 0)
						{
							for (TreeItem item : current.getItems())
							{
								UserInfo user = (UserInfo) item.getData();
								InfoField field = factory.makeInfoField(Messages.getString("MainWindow.74"), editGroupDialog.GetCategoryName()); //$NON-NLS-1$
								user.getCustomInfo().setInfoField(Messages.getString("MainWindow.75"), field); //$NON-NLS-1$

								logicCenter.editContactInfo(user);
							}
						}
					}
				}
				else	//编辑联系人
				{
					UserInfoDialog userInfoDialog = new UserInfoDialog(shell, current.getText(), 
							(getCurrentContactTab() == ContactTabType.Permission) ? 
									UserInfoTableType.Permission : UserInfoTableType.Synchronization,
							(UserInfo)current.getData());
					if(getCurrentContactTab() == ContactTabType.Permission)
						userInfoDialog.setAllPerContactsBox(allPerContactsBox);
					//userInfoDialog.setAllGroupsBox(compositeGroup.getAllGroupsBox());
					userInfoDialog.OpenEditInfo();
					
//					// Debug:
//					int ind = current.getParentItem().indexOf(current);
//					EditContactDialog editContac = new EditContactDialog(allContactsBox.getContacts().get(ind), shell,
//							SWT.None);
//					editContac.open();

					// MessageDialog.openInformation(shell, "编辑联系人", current
					// .getText());
				}
			}
		}
	}

	/**
	 * 编辑联系人权限
	 * 
	 * @author Wander
	 * 
	 */
	private class ToolItemAddressPermissionSelectionListener extends SelectionAdapter
	{
		public void widgetSelected(final SelectionEvent e)
		{
			TreeItem current = getCurrentTreeItem();

			if (current != null && current.getParentItem() != null && getCurrentContactTab() == ContactTabType.Permission)
			{
				UserInfoDialog userInfoDialog = new UserInfoDialog(shell, current.getText(), 
						UserInfoTableType.Permission,
						(UserInfo)current.getData());
				userInfoDialog.setAllPerContactsBox(allPerContactsBox);
				userInfoDialog.OpenPermission();
			}
		}
	}

	/**
	 * 删除联系人或分组
	 * 
	 * @author Wander
	 * 
	 */
	private class ToolItemAddressDelSelectionListener extends SelectionAdapter
	{
		public void widgetSelected(final SelectionEvent e)
		{
			TreeItem current = getCurrentTreeItem();

			if (current != null)
			{
				if (current.getParentItem() == null)	//删除分组
				{
					if(!(current.getData() instanceof Integer) || !current.getData().equals(-1))	//不是“未分组”
					{
						if (MessageDialog.openConfirm(shell, Messages.getString("MainWindow.76"), String.format(Messages.getString("MainWindow.77"), current.getText()))) //$NON-NLS-1$ //$NON-NLS-2$
						{
							for (TreeItem item : current.getItems())
							{
								UserInfo user = (UserInfo) item.getData();
								user.getCustomInfo().setInfoField(Messages.getString("MainWindow.78"), //$NON-NLS-1$
										InfoFieldFactory.getFactory().makeInfoField(Messages.getString("MainWindow.79"), null)); //$NON-NLS-1$
								logicCenter.editContactInfo(user);
							}
						}
					}
				}
				else
				{
					//TODO: 删除联系人
					UserInfoDialog userInfoDialog = new UserInfoDialog(shell, current.getText(), 
							(getCurrentContactTab() == ContactTabType.Permission) ?
									UserInfoTableType.Permission : UserInfoTableType.Synchronization,
							(UserInfo)current.getData());
					if (getCurrentContactTab() == ContactTabType.Permission)
						userInfoDialog.setAllPerContactsBox(allPerContactsBox);//W同学，你的BUG！
					//userInfoDialog.setAllGroupsBox(compositeGroup.getAllGroupsBox());
					userInfoDialog.open();
				}
			}
		}
	}

	/**
	 * 为联系人设置自定义分组
	 * 
	 * @author Wander
	 * 
	 */
	private class ToolItemAddressCustomGroupSelectionListener extends SelectionAdapter
	{
		public void widgetSelected(final SelectionEvent e)
		{
			TreeItem current = getCurrentTreeItem();
			if (current == null || current.getParentItem() == null)
				return;

			MenuItem currentMenuItem = (MenuItem)e.getSource();
			if(!currentMenuItem.getSelection()) return;
			
			UserInfo user = (UserInfo)current.getData();
			InfoFieldFactory factory = InfoFieldFactory.getFactory();
			
			if (toolItemAddressCustomGroupNone.getSelection())
			{
				InfoField cate = factory.makeInfoField(Messages.getString("MainWindow.80"), null); //$NON-NLS-1$
				user.getCustomInfo().setInfoField(Messages.getString("MainWindow.81"), cate); //$NON-NLS-1$
				//MessageDialog.openInformation(shell, "设置分组", "id = 0");
			} 
			else
			{
//				for (MenuItem item : toolItemAddressCustomGroupItems)
//				{
//					if (item.getSelection())
//					{
						InfoField cate = factory.makeInfoField(Messages.getString("MainWindow.82"), currentMenuItem.getText()); //$NON-NLS-1$
						user.getCustomInfo().setInfoField(Messages.getString("MainWindow.83"), cate); //$NON-NLS-1$
						//MessageDialog.openInformation(shell, "设置分组", "id = " + ((Integer) item.getData()).toString());
//					}
//				}
			}
			
			logicCenter.editContactInfo(user);
		}
	}

	/**
	 * 新建分组
	 * 
	 * @author Wander
	 * 
	 */
	private class ToolItemAddressCustomGroupNewSelectionListener extends SelectionAdapter
	{
		public void widgetSelected(final SelectionEvent e)
		{
				TreeItem current = getCurrentTreeItem();
				
				EditCategoryDialog editGroupDialog = new EditCategoryDialog(shell);
				if(editGroupDialog.open() == 0)
				{
					InfoFieldFactory factory = InfoFieldFactory.getFactory();
					UserInfo user = (UserInfo)current.getData();
					user.getCustomInfo().setInfoField(Messages.getString("MainWindow.84"), //$NON-NLS-1$
							factory.makeInfoField(Messages.getString("MainWindow.85"), editGroupDialog.GetCategoryName()) //$NON-NLS-1$
							);
					
					logicCenter.editContactInfo(user);
				}
			
		}
	}

	/**
	 * 本地同步
	 * 
	 * @author Wander
	 * 
	 */
	private class ToolItemAddressSyncLocalSelectionListener extends SelectionAdapter
	{
		public void widgetSelected(final SelectionEvent e)
		{
			LocalSynDialog dialog = new LocalSynDialog(shell, logicCenter.localSynchronize());
			dialog.open();
		}
	}

	/**
	 * 远程同步
	 * 
	 * @author Wander
	 * 
	 */
	private class ToolItemAddressSyncRemoteSelectionListener extends SelectionAdapter
	{
		public void widgetSelected(final SelectionEvent e)
		{
			RemoteSynResult result = logicCenter.remoteSynchronize();
			VirtualResultObserver observer = new VirtualResultObserver(shell, Messages.getString("MainWindow.86")); //$NON-NLS-1$
			result.addObserver(observer);
		}
	}

	/**
	 * 在本地搜索联系人
	 * 
	 * @author Wander
	 * 
	 */
	private class LabelAddressSearchSelectionListener extends SelectionAdapter {
		public void widgetSelected(final SelectionEvent e)
		{
			UserInfo user = new UserInfo();
			UserInfoDialog userInfoDialog = new UserInfoDialog(shell, Messages.getString("MainWindow.87"), UserInfoTableType.SearchLocalForm, user); //$NON-NLS-1$
			if(userInfoDialog.OpenEditInfo() == IDialogConstants.OK_ID)
			{
				LocalSearchContactsResult result = logicCenter.localSearchContacts(user, userInfoDialog.getStrategy());
				result.addObserver(new LocalSearchResultObserver());
				//TODO: 如果从搜索结果中更新联系人，则搜索结果的显示不会自动更新
			}
		}
	}
	
	/**
	 * 显示本地搜索结果 Observer
	 * @author Wander
	 *
	 */
	class LocalSearchResultObserver implements Observer
	{

		@Override
		public void update(Observable o, Object arg)
		{
			LocalSearchContactsResult result = (LocalSearchContactsResult)o;
			Display.getDefault().syncExec(new LocalSearchResultTask(result.getContacts()));
		}
	}
	
	/**
	 * 显示本地搜索结果 Task
	 * @author Wander
	 *
	 */
	class LocalSearchResultTask implements Runnable
	{
		private List<UserInfo> users;
		
		public LocalSearchResultTask(List<UserInfo> users)
		{
			this.users = users;
		}
		
		@Override
		public void run()
		{
			//localSearchResult = users;
			(new ContactRefreshTask(users, ContactTabType.SearchResult)).run();
			tabFolderAddress.setSelection(tabItemAddressSearchResult);
		}
		
	}
	
	/**
	 * 刷新联系人 Observer
	 * @author Wander
	 *
	 */
	class ContactRefreshObserver implements Observer
	{
		@Override
		public void update(Observable o, Object arg)
		{
			AllContactsBox allContactsBox = (AllContactsBox) o;
			Display.getDefault().syncExec(new ContactRefreshTask(allContactsBox.getContacts()));
		}
	}
	
	/**
	 * 刷新被授权联系人 Observer
	 * @author Wander
	 *
	 */
	class ContactPerRefreshObserver implements Observer
	{
		@Override
		public void update(Observable o, Object arg)
		{
			AllPerContactsBox allPerContactsBox = (AllPerContactsBox) o;
			Display.getDefault().syncExec(new ContactRefreshTask(allPerContactsBox.getContacts(), ContactTabType.Permission));
		}
	}
	
	
	/**
	 * 刷新通讯录 Task
	 * @author Wander
	 *
	 */
	class ContactRefreshTask implements Runnable
	{
		private List<UserInfo> users;
		private ContactTabType tabType;

		@Override
		public void run()
		{
			int n = users.size();

			// tabItemAddressContact.
			// treeAddressContactItemNoGroup.clearAll(true);
			/*
			 * treeAddressContact.dispose(); treeAddressContact = new
			 * Tree(tabFolderAddress, SWT.BORDER); treeAddressContact
			 * .addSelectionListener(new TreeAddressContactSelectionListener());
			 * treeAddressContact.setSortColumn(null);
			 * treeAddressContact.setHeaderVisible(true);
			 * tabItemAddressContact.setControl(treeAddressContact);
			 */
			Tree currentTree;	
			
			if(tabType == ContactTabType.Synchronization)
			{
				currentTree = treeAddressContact;
			}
			else if(tabType == ContactTabType.Permission)
			{
				currentTree = treeAddressPermit;
			}
			else if(tabType == ContactTabType.SearchResult)
			{
				currentTree = treeAddressSearchResult;
			}
			else
			{
				currentTree = treeAddressContact;
			}
			
			currentTree.removeAll();
			contactsCategory.clear();
			
			//if(n <= 0) return;
			
			TreeItem noGroupItem = new TreeItem(currentTree, SWT.NONE);
			noGroupItem.setText(Messages.getString("MainWindow.88")); //$NON-NLS-1$
			noGroupItem.setData(new Integer(-1));

			List<TreeItem> cateList = new ArrayList<TreeItem>();
			// TreeItem item1 = new TreeItem(treeAddressContact, SWT.NONE);
			// item1.setText("sql");
			for (int i = 0; i < n; i++)
			{							
				String name = users.get(i).getBaseInfo().getInfoField(Messages.getString("MainWindow.89")).getStringValue(); //$NON-NLS-1$
				String nick = users.get(i).getCustomInfo().getInfoField(Messages.getString("MainWindow.90")).getStringValue(); //$NON-NLS-1$
				String relation = users.get(i).getInfoField(InfoFieldName.Relation).getStringValue();
				String cell = users.get(i).getBaseInfo().getInfoField(Messages.getString("MainWindow.91")).getStringValue(); //$NON-NLS-1$
				String email = users.get(i).getBaseInfo().getInfoField(Messages.getString("MainWindow.92")).getStringValue(); //$NON-NLS-1$
				String tag = users.get(i).getCustomInfo().getInfoField(Messages.getString("MainWindow.93")).getStringValue(); //$NON-NLS-1$
				String bir = users.get(i).getBaseInfo().getInfoField(Messages.getString("MainWindow.94")).getStringValue(); //$NON-NLS-1$
				
				TreeItem parentItem;
				if (tag == null || tag.isEmpty())
				{
					parentItem = noGroupItem;
				}
				else if (contactsCategory.containsKey(tag))
				{
					parentItem = cateList.get((int) contactsCategory.get(tag));
				}
				else
				{
					parentItem = new TreeItem(currentTree, SWT.NONE);
					cateList.add(parentItem);
					parentItem.setText(tag);
					contactsCategory.put(tag, cateList.size()-1);
				}
				TreeItem current = createTreeSubItem(parentItem, name, nick, relation, cell, email, bir);
				current.setData(users.get(i));
			}
			
			for(TreeItem item : currentTree.getItems())
			{
				item.setExpanded(true);
			}

			
			// [start] 自定义分组
			for(MenuItem item : toolItemAddressCustomGroupMenu.getItems())
			{
				item.dispose();
			}
			
			toolItemAddressCustomGroupNone = new MenuItem(toolItemAddressCustomGroupMenu, SWT.RADIO);
			toolItemAddressCustomGroupNone.addSelectionListener(new ToolItemAddressCustomGroupSelectionListener());
			toolItemAddressCustomGroupNone.setText(Messages.getString("MainWindow.95")); //$NON-NLS-1$

			toolItemAddressCustomGroupItems = new MenuItem[contactsCategory.size()]; // 1: 分组总数
			for(String cate : contactsCategory.keySet())
			{
				int key = contactsCategory.get(cate);
				toolItemAddressCustomGroupItems[key] = new MenuItem(toolItemAddressCustomGroupMenu, SWT.RADIO);
				toolItemAddressCustomGroupItems[key].setText(cate);
				toolItemAddressCustomGroupItems[key].addSelectionListener(new ToolItemAddressCustomGroupSelectionListener());
			}

			new MenuItem(toolItemAddressCustomGroupMenu, SWT.SEPARATOR);
			toolItemAddressCustomGroupNew = new MenuItem(toolItemAddressCustomGroupMenu, SWT.NONE);
			toolItemAddressCustomGroupNew.addSelectionListener(new ToolItemAddressCustomGroupNewSelectionListener());
			toolItemAddressCustomGroupNew.setText(Messages.getString("MainWindow.96")); //$NON-NLS-1$
			// [end]
		}

		public ContactRefreshTask(List<UserInfo> users)
		{
			this.users = users;
			this.tabType = ContactTabType.Synchronization;
		}
		
		public ContactRefreshTask(List<UserInfo> users, ContactTabType tabType)
		{
			this.users = users;
			this.tabType = tabType;
		}
	}

	private static void addDropDown(final ToolItem item, final Menu menu)
	{
		item.addListener(SWT.Selection, new Listener()
		{
			public void handleEvent(Event event)
			{
				if (event.detail == SWT.ARROW)
				{
					Rectangle rect = item.getBounds();
					Point pt = new Point(rect.x, rect.y + rect.height);
					pt = item.getParent().toDisplay(pt);
					menu.setLocation(pt.x, pt.y);
					menu.setVisible(true);
				}
			}
		});
	}
	
	private class TreeAddressContactMouseListener extends MouseAdapter {
		public void mouseDoubleClick(final MouseEvent e)
		{
			TreeItem current = getCurrentTreeItem();

			if (current != null)
			{
				if (current.getParentItem() != null)
				{
					UserInfoDialog userInfoDialog = new UserInfoDialog(shell, current.getText(), 
							(tabFolderAddress.getSelection()[0] == tabItemAddressContact) ? 
									UserInfoTableType.Synchronization : UserInfoTableType.Permission,
							(UserInfo)current.getData());
					userInfoDialog.open();
				}
			}
		}
	}

	// [end]

	// [start] “文件”菜单 相关事件
	/***
	 * 通讯录导入
	 * 
	 * @author Wander
	 */
	private class MenuFileImportSelectionListener extends SelectionAdapter
	{
		public void widgetSelected(final SelectionEvent e)
		{
			FileDialog dialog = new FileDialog(shell);
			dialog.setText(Messages.getString("MainWindow.97")); //$NON-NLS-1$
			// dialog.setMessage("请选择一个文件");
			dialog.setFilterPath(Messages.getString("MainWindow.98")); //$NON-NLS-1$
			dialog.setFilterExtensions(new String[] { Messages.getString("MainWindow.99") }); //$NON-NLS-1$
			String dir = dialog.open();
			if (dir != null)
			{
				logicCenter.importFile(dir);
				System.out.println(Messages.getString("MainWindow.100") + dir); //$NON-NLS-1$
			}
		}
	}

	/***
	 * 通讯录导出
	 * 
	 * @author Wander
	 * 
	 */
	private class MenuFileExportSelectionListener extends SelectionAdapter
	{
		public void widgetSelected(final SelectionEvent e)
		{
			FileDialog dialog = new FileDialog(shell, SWT.SAVE);
			dialog.setText(Messages.getString("MainWindow.101")); //$NON-NLS-1$
			// dialog.setMessage("请选择一个目录");
			dialog.setFilterExtensions(new String[] { Messages.getString("MainWindow.102") }); //$NON-NLS-1$
			dialog.setFilterPath(Messages.getString("MainWindow.103")); //$NON-NLS-1$
			String dir = dialog.open();
			if (dir != null)
			{
				logicCenter.exportFile(dir + Messages.getString("MainWindow.104")); //$NON-NLS-1$
				System.out.println(Messages.getString("MainWindow.105") + dir); //$NON-NLS-1$
			}
		}
	}

	// [end]
	
	/**
	 * 退出时执行
	 */
	private class ShellShellListener extends ShellAdapter {
		public void shellClosed(final ShellEvent e)
		{
			try
			{
				logicCenter.logout();
			} catch (RemoteException e1)
			{
				e1.printStackTrace();
			}
		}
	}

	// [start] Observer相关事件

	class DisplayStatTask implements Runnable
	{
		StatResult result;

		@Override
		public void run()
		{
			System.out.println(Messages.getString("MainWindow.106")); //$NON-NLS-1$
			statDialog.setStatics(result);
		}

		public DisplayStatTask(StatResult result)
		{
			this.result = result;
		}
	}

	void displayStat(StatResult result)
	{
		Display.getDefault().syncExec(new DisplayStatTask(result));
	}

	class StatObserver implements Observer
	{
		@Override
		public void update(Observable o, Object arg1)
		{
			GetStatResultResult ro = (GetStatResultResult) o;
			displayStat(ro.getStatResult());
		}
	}
	
	//[end]
}
