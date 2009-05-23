package ui;

import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import logiccenter.LogicCenter;
import logiccenter.LogicCenterImp;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
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

import com.swtdesigner.SWTResourceManager;

import entity.UserInfo;
import entity.VirtualResult.AllContactsBox;
import entity.VirtualResult.MessageBox;
import entity.VirtualResult.VirtualState;
import entity.message.Message;

public class MainWindow {
	public static LogicCenter logicCenter=LogicCenterImp.getInstance();
	private TreeItem treeAddressContactItemNoGroup;
	private TreeItem treeAddressPermitItemNoGroup;
	private MenuItem toolItemAddressCustomGroupItems[];
	private MenuItem toolItemAddressCustomGroupNew;
	private MenuItem toolItemAddressCustomGroupNone;
	private ToolItem toolItemAddressSep2;
	private ToolItem toolItemAddressAddGroup;
	private ToolItem toolItemAddressAddContact;
	private TreeColumn treeAddressPermitColumnEmail;
	private TreeColumn treeAddressPermitColumnCellPhone;
	private TreeColumn treeAddressPermitColumnRelation;
	private TreeColumn treeAddressPermitColumnCall;
	private TreeColumn treeAddressPermitColumnNickName;
	private TreeColumn treeAddressPermitColumnName;
	private Tree treeAddressPermit;
	private TreeColumn treeAddressContactColumnEmail;
	private TreeColumn treeAddressContactColumnCellPhone;
	private TreeColumn treeAddressContactColumnRelation;
	private TreeColumn treeAddressContactColumnCall;
	private TreeColumn treeAddressContactColumnNickName;
	private TreeColumn treeAddressContactColumnName;
	private Tree treeAddressContact;
	private TabItem tabItemAddressContact;
	private TabItem tabItemAddressPermit;
	private TabFolder tabFolderAddress;
	private Label labelAddressSearch;
	private MenuItem toolItemAddressGroupByNone;
	private MenuItem toolItemAddressGroupByInitial;
	private MenuItem toolItemAddressGroupByRelation;
	private MenuItem toolItemAddressGroupByGroup;
	private MenuItem toolItemAddressGroupByCustomGroup;
	private ToolItem toolItemAddressSep1;
	private Menu toolItemAddressCustomGroupMenu;
	private ToolItem toolItemAddressCustomGroup;
	private Menu toolItemAddressGroupByMenu;
	private ToolItem toolItemAddressGroupBy;
	private ToolItem toolItemAddressDel;
	private ToolItem toolItemAddressPermission;
	private ToolItem toolItemAddressEdit;
	private Combo comboInfoGender;
	private Label labelInfoGender;
	private ToolItem toolItemInfoDownload;
	private ToolItem toolItemInfoReset;
	private ToolItem toolItemInfoUpload;
	private ToolItem toolItemInfoSave;
	private ToolBar toolBarInfo;
	private Text textInfoStatus;
	private Label labelInfoStatus;
	private Group groupInfoCustom;
	private Text textInfoCompanyAddress;
	private Label labelInfoCompanyAddress;
	private Text textInfoCompanyPhone;
	private Label labelInfoCompanyPhone;
	private Text textInfoTitle;
	private Label labelInfoTitle;
	private Text textInfoCompany;
	private Label labelInfoCompany;
	private Group groupInfoGroup;
	private Text textInfoFamilyAddress;
	private Label labelInfoFamilyAddress;
	private Text textInfoFamilyPhone;
	private Label labelInfoFamilyPhone;
	private Group groupInfoFamily;
	// private Button button;
	// private Composite composite_1;
	private Text textInfoDesc;
	private Label labelInfoDesc;
	// private Composite composite;
	private ScrolledComposite scrolledCompositeInfo;
	private Text textInfoMSN;
	private Label labelInfoMSN;
	private Text textInfoQQNumber;
	private Label labelInfoQQNumber;
	private Text textInfoEmail;
	private Label labelInfoEmail;
	private DateTime dateTimeBirthday;
	private Label labelInfoBirthday;
	private Text textInfoCellPhone;
	private Label labelInfoCellPhone;
	private Label labelInfoNickName;
	private Text textInfoNickName;
	private Text textInfoName;
	private Label labelInfoName;
	private Group groupInfoPersonal;
	private Button buttonInfoChangeAvatar;
	private Canvas canvasInfoAvatar;
	protected Shell shell;

	private Text textAddressSearch;
	private Composite compositeMain;
	private StackLayout compositeMainStackLayout;
	private Composite compositeLeftList;
	private Menu menuMain;
	private MenuItem submenuMainHelp;
	private Menu menuMainAbout;
	private MenuItem menuItemMainHelpAbout;
	private ToolBar toolBarMain;
	private Composite compositeAddress;
	private ToolBar toolBarAddress;
	private ToolItem toolItemAddressSyncRemote;
	private ToolItem toolItemAddressSyncLocal;
	private Composite compositeInfo;
	private ToolItem toolItemMainRegister;
	private ToolItem toolItemMainLogin;
	private ToolItem toolItemMainLogout;
	private Button listButtonUserInfo;
	private Button listButtonAddressBook;
	private Button listButtonGroup;
	private Button listButtonSearch;
	private Composite renlifang;
	private Button listButtonMessageBox;
	private MessageBoxDialog messageCompsite;

	/**
	 * Launch the application
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MainWindow window = new MainWindow();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window
	 */
	public void open() {
		final Display display = Display.getDefault();
		createContents();

		dataInit();

		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}

	}

	/**
	 * Create contents of the window
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setLayout(new FormLayout());
		shell.setSize(800, 600);
		shell.setText(Messages.getString("MainWindow.PhoneMe")); //$NON-NLS-1$

		// �Ҳ�Composite
		compositeMain = new Composite(shell, SWT.NONE);
		compositeMainStackLayout = new StackLayout();
		compositeMain.setLayout(compositeMainStackLayout);
		final FormData fd_compositeMain = new FormData();
		fd_compositeMain.bottom = new FormAttachment(100, 0);
		fd_compositeMain.right = new FormAttachment(100, 0);
		fd_compositeMain.left = new FormAttachment(0, 130);
		compositeMain.setLayoutData(fd_compositeMain);

		// ���Composite
		compositeLeftList = new Composite(shell, SWT.NONE);
		compositeLeftList.setLayout(new GridLayout());
		final FormData fd_compositeLeftList = new FormData();
		fd_compositeLeftList.top = new FormAttachment(compositeMain, 0, SWT.TOP);
		fd_compositeLeftList.bottom = new FormAttachment(100, 0);
		fd_compositeLeftList.right = new FormAttachment(0, 130);
		fd_compositeLeftList.left = new FormAttachment(0, 0);
		compositeLeftList.setLayoutData(fd_compositeLeftList);

		// ���˵���
		menuMain = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menuMain);

		// ���������˵�
		submenuMainHelp = new MenuItem(menuMain, SWT.CASCADE);
		submenuMainHelp.setText(Messages.getString("MainWindow.Help")); //$NON-NLS-1$

		menuMainAbout = new Menu(submenuMainHelp);
		submenuMainHelp.setMenu(menuMainAbout);

		menuItemMainHelpAbout = new MenuItem(menuMainAbout, SWT.NONE);
		menuItemMainHelpAbout.setText(Messages.getString("MainWindow.About")); //$NON-NLS-1$

		// ��������
		toolBarMain = new ToolBar(shell, SWT.NONE);
		fd_compositeMain.top = new FormAttachment(toolBarMain, 0, SWT.BOTTOM);

		// ������Ϣcomposite
		scrolledCompositeInfo = new ScrolledComposite(compositeMain,
				SWT.V_SCROLL | SWT.H_SCROLL);
		scrolledCompositeInfo.getVerticalBar().setThumb(0);
		scrolledCompositeInfo.getVerticalBar().setIncrement(10);

		compositeInfo = new Composite(scrolledCompositeInfo, SWT.NONE);
		compositeInfo.setLayout(new FormLayout());

		canvasInfoAvatar = new Canvas(compositeInfo, SWT.DOUBLE_BUFFERED);
		canvasInfoAvatar.addPaintListener(new CanvasInfoAvatarPaintListener());
		final FormData fd_canvasInfoAvatar = new FormData();
		fd_canvasInfoAvatar.left = new FormAttachment(0, 15);
		fd_canvasInfoAvatar.right = new FormAttachment(0, 135);
		fd_canvasInfoAvatar.bottom = new FormAttachment(0, 160);
		fd_canvasInfoAvatar.top = new FormAttachment(0, 40);
		canvasInfoAvatar.setLayoutData(fd_canvasInfoAvatar);
		canvasInfoAvatar.setData(SWTResourceManager.getImage(MainWindow.class,
				"/img/nullAvatar.jpg"));

		buttonInfoChangeAvatar = new Button(compositeInfo, SWT.NONE);
		buttonInfoChangeAvatar
				.addSelectionListener(new ButtonInfoChangeAvatarSelectionListener());
		final FormData fd_buttonInfoChangeAvatar = new FormData();
		fd_buttonInfoChangeAvatar.top = new FormAttachment(canvasInfoAvatar, 5,
				SWT.BOTTOM);
		fd_buttonInfoChangeAvatar.right = new FormAttachment(0, 109);
		fd_buttonInfoChangeAvatar.left = new FormAttachment(0, 40);
		buttonInfoChangeAvatar.setLayoutData(fd_buttonInfoChangeAvatar);
		buttonInfoChangeAvatar.setText("����ͷ��...");

		groupInfoPersonal = new Group(compositeInfo, SWT.NONE);
		groupInfoPersonal.setText("����");
		final FormData fd_groupInfoPersonal = new FormData();
		fd_groupInfoPersonal.right = new FormAttachment(0, 585);
		fd_groupInfoPersonal.left = new FormAttachment(0, 15);
		fd_groupInfoPersonal.bottom = new FormAttachment(0, 490);
		fd_groupInfoPersonal.top = new FormAttachment(0, 200);
		groupInfoPersonal.setLayoutData(fd_groupInfoPersonal);

		labelInfoName = new Label(groupInfoPersonal, SWT.NONE);
		labelInfoName.setBounds(38, 34, 24, 17);
		labelInfoName.setText("����");

		textInfoName = new Text(groupInfoPersonal, SWT.BORDER);
		textInfoName.setBounds(86, 34, 142, 23);

		textInfoNickName = new Text(groupInfoPersonal, SWT.BORDER);
		textInfoNickName.setBounds(363, 34, 142, 23);

		labelInfoNickName = new Label(groupInfoPersonal, SWT.NONE);
		labelInfoNickName.setBounds(314, 34, 24, 17);
		labelInfoNickName.setText("�ǳ�");

		labelInfoCellPhone = new Label(groupInfoPersonal, SWT.NONE);
		labelInfoCellPhone.setBounds(38, 115, 24, 17);
		labelInfoCellPhone.setText("�ֻ�");

		textInfoCellPhone = new Text(groupInfoPersonal, SWT.BORDER);
		textInfoCellPhone.setBounds(86, 112, 142, 23);
		textInfoCellPhone
				.addFocusListener(new TextInfoCellPhoneFocusListener());

		labelInfoBirthday = new Label(groupInfoPersonal, SWT.NONE);
		labelInfoBirthday.setBounds(314, 74, 24, 17);
		labelInfoBirthday.setText("����");

		dateTimeBirthday = new DateTime(groupInfoPersonal, SWT.DATE);
		dateTimeBirthday.setBounds(362, 72, 93, 24);

		labelInfoEmail = new Label(groupInfoPersonal, SWT.NONE);
		labelInfoEmail.setBounds(314, 115, 36, 17);
		labelInfoEmail.setText("E-mail");

		textInfoEmail = new Text(groupInfoPersonal, SWT.BORDER);
		textInfoEmail.setBounds(363, 112, 142, 23);
		textInfoEmail.addFocusListener(new TextInfoEmailFocusListener());

		labelInfoQQNumber = new Label(groupInfoPersonal, SWT.NONE);
		labelInfoQQNumber.setBounds(38, 150, 24, 23);
		labelInfoQQNumber.setText("QQ");

		textInfoQQNumber = new Text(groupInfoPersonal, SWT.BORDER);
		textInfoQQNumber.setBounds(86, 150, 142, 23);
		textInfoQQNumber.addFocusListener(new TextInfoQQNumberFocusListener());

		labelInfoMSN = new Label(groupInfoPersonal, SWT.NONE);
		labelInfoMSN.setBounds(314, 150, 36, 17);
		labelInfoMSN.addFocusListener(new LabelInfoMSNFocusListener());
		labelInfoMSN.setText("MSN");

		textInfoMSN = new Text(groupInfoPersonal, SWT.BORDER);
		textInfoMSN.setBounds(362, 150, 142, 23);

		labelInfoDesc = new Label(groupInfoPersonal, SWT.NONE);
		labelInfoDesc.setBounds(38, 197, 48, 23);
		labelInfoDesc.setText("��������");

		textInfoDesc = new Text(groupInfoPersonal, SWT.BORDER);
		textInfoDesc.setBounds(98, 197, 427, 80);

		labelInfoGender = new Label(groupInfoPersonal, SWT.NONE);
		labelInfoGender.setBounds(38, 74, 24, 17);
		labelInfoGender.setText("�Ա�");

		comboInfoGender = new Combo(groupInfoPersonal, SWT.READ_ONLY);
		comboInfoGender.setBounds(86, 71, 67, 25);
		comboInfoGender.add("������");
		comboInfoGender.add("��");
		comboInfoGender.add("Ů");
		comboInfoGender.setData("������", 0);
		comboInfoGender.setData("��", 1);
		comboInfoGender.setData("Ů", 2);
		comboInfoGender.select(0);

		groupInfoFamily = new Group(compositeInfo, SWT.NONE);
		final FormData fd_groupInfoFamily = new FormData();
		fd_groupInfoFamily.bottom = new FormAttachment(0, 605);
		fd_groupInfoFamily.right = new FormAttachment(groupInfoPersonal, 0,
				SWT.RIGHT);
		fd_groupInfoFamily.top = new FormAttachment(0, 510);
		fd_groupInfoFamily.left = new FormAttachment(groupInfoPersonal, 0,
				SWT.LEFT);
		groupInfoFamily.setLayoutData(fd_groupInfoFamily);
		groupInfoFamily.setText("��ͥ");

		labelInfoFamilyPhone = new Label(groupInfoFamily, SWT.NONE);
		labelInfoFamilyPhone.setBounds(29, 30, 48, 17);
		labelInfoFamilyPhone.setText("��ͥ�绰");

		textInfoFamilyPhone = new Text(groupInfoFamily, SWT.BORDER);
		textInfoFamilyPhone.setBounds(101, 24, 142, 23);
		textInfoFamilyPhone
				.addFocusListener(new TextInfoFamilyPhoneFocusListener());

		labelInfoFamilyAddress = new Label(groupInfoFamily, SWT.NONE);
		labelInfoFamilyAddress.setBounds(29, 65, 48, 17);
		labelInfoFamilyAddress.setText("��ͥסַ");

		textInfoFamilyAddress = new Text(groupInfoFamily, SWT.BORDER);
		textInfoFamilyAddress.setBounds(101, 59, 282, 23);

		groupInfoGroup = new Group(compositeInfo, SWT.NONE);
		final FormData fd_groupInfoGroup = new FormData();
		fd_groupInfoGroup.bottom = new FormAttachment(0, 765);
		fd_groupInfoGroup.top = new FormAttachment(0, 625);
		fd_groupInfoGroup.right = new FormAttachment(groupInfoFamily, 570,
				SWT.LEFT);
		fd_groupInfoGroup.left = new FormAttachment(groupInfoFamily, 0,
				SWT.LEFT);
		groupInfoGroup.setLayoutData(fd_groupInfoGroup);
		groupInfoGroup.setText("��λ");

		labelInfoCompany = new Label(groupInfoGroup, SWT.NONE);
		labelInfoCompany.setBounds(35, 32, 24, 17);
		labelInfoCompany.setText("��λ");

		textInfoCompany = new Text(groupInfoGroup, SWT.BORDER);
		textInfoCompany.setBounds(110, 32, 142, 23);

		labelInfoTitle = new Label(groupInfoGroup, SWT.NONE);
		labelInfoTitle.setBounds(311, 32, 24, 17);
		labelInfoTitle.setText("ͷ��");

		textInfoTitle = new Text(groupInfoGroup, SWT.BORDER);
		textInfoTitle.setBounds(360, 32, 142, 23);

		labelInfoCompanyPhone = new Label(groupInfoGroup, SWT.NONE);
		labelInfoCompanyPhone.setBounds(35, 70, 48, 17);
		labelInfoCompanyPhone.setText("��λ�绰");

		textInfoCompanyPhone = new Text(groupInfoGroup, SWT.BORDER);
		textInfoCompanyPhone.setBounds(110, 72, 142, 23);
		textInfoCompanyPhone
				.addFocusListener(new TextInfoCompanyPhoneFocusListener());

		labelInfoCompanyAddress = new Label(groupInfoGroup, SWT.NONE);
		labelInfoCompanyAddress.setBounds(35, 107, 48, 17);
		labelInfoCompanyAddress.setText("��λ��ַ");

		textInfoCompanyAddress = new Text(groupInfoGroup, SWT.BORDER);
		textInfoCompanyAddress.setBounds(110, 109, 282, 23);

		groupInfoCustom = new Group(compositeInfo, SWT.NONE);
		final FormData fd_groupInfoCustom = new FormData();
		fd_groupInfoCustom.right = new FormAttachment(groupInfoGroup, 0,
				SWT.RIGHT);
		fd_groupInfoCustom.top = new FormAttachment(0, 800);
		fd_groupInfoCustom.left = new FormAttachment(groupInfoPersonal, 0,
				SWT.LEFT);
		groupInfoCustom.setLayoutData(fd_groupInfoCustom);
		groupInfoCustom.setLayout(new FormLayout());
		groupInfoCustom.setText("�Զ���");

		labelInfoStatus = new Label(compositeInfo, SWT.NONE);
		final FormData fd_labelInfoStatus = new FormData();
		fd_labelInfoStatus.top = new FormAttachment(canvasInfoAvatar, 0,
				SWT.TOP);
		fd_labelInfoStatus.left = new FormAttachment(0, 170);
		labelInfoStatus.setLayoutData(fd_labelInfoStatus);
		labelInfoStatus.setText("�ҵ�״̬");

		textInfoStatus = new Text(compositeInfo, SWT.BORDER);
		final FormData fd_textInfoStatus = new FormData();
		fd_textInfoStatus.bottom = new FormAttachment(buttonInfoChangeAvatar,
				0, SWT.BOTTOM);
		fd_textInfoStatus.right = new FormAttachment(groupInfoPersonal, 0,
				SWT.RIGHT);
		fd_textInfoStatus.top = new FormAttachment(labelInfoStatus, 5,
				SWT.BOTTOM);
		fd_textInfoStatus.left = new FormAttachment(labelInfoStatus, 0,
				SWT.LEFT);
		textInfoStatus.setLayoutData(fd_textInfoStatus);

		toolBarInfo = new ToolBar(compositeInfo, SWT.NONE);
		final FormData fd_toolBarInfo = new FormData();
		fd_toolBarInfo.top = new FormAttachment(0, 5);
		fd_toolBarInfo.left = new FormAttachment(0, 5);
		toolBarInfo.setLayoutData(fd_toolBarInfo);

		toolItemInfoSave = new ToolItem(toolBarInfo, SWT.PUSH);
		toolItemInfoSave
				.addSelectionListener(new ToolItemInfoSaveSelectionListener());
		toolItemInfoSave.setToolTipText("����Ը�����Ϣ���޸�");
		toolItemInfoSave.setText("��");

		toolItemInfoReset = new ToolItem(toolBarInfo, SWT.PUSH);
		toolItemInfoReset
				.addSelectionListener(new ToolItemInfoResetSelectionListener());
		toolItemInfoReset.setToolTipText("���������޸�");
		toolItemInfoReset.setText("��");

		toolItemInfoUpload = new ToolItem(toolBarInfo, SWT.PUSH);
		toolItemInfoUpload
				.addSelectionListener(new ToolItemInfoUploadSelectionListener());
		toolItemInfoUpload.setToolTipText("��������Ϣ���µ�������");
		toolItemInfoUpload.setText("��");

		toolItemInfoDownload = new ToolItem(toolBarInfo, SWT.PUSH);
		toolItemInfoDownload
				.addSelectionListener(new ToolItemInfoDownloadSelectionListener());
		toolItemInfoDownload.setToolTipText("�ӷ����������ظ�����Ϣ");
		toolItemInfoDownload.setText("��");

		compositeInfo.setSize(630, 1000);
		scrolledCompositeInfo.setContent(compositeInfo);

		// ͨѶ¼composit

		compositeAddress = new Composite(compositeMain, SWT.NONE);
		compositeAddress.setVisible(false);
		compositeAddress.setLayout(new FormLayout());

		toolBarAddress = new ToolBar(compositeAddress, SWT.NONE);
		final FormData fd_toolBarAddress = new FormData();
		fd_toolBarAddress.top = new FormAttachment(0, 5);
		fd_toolBarAddress.left = new FormAttachment(0, 0);
		toolBarAddress.setLayoutData(fd_toolBarAddress);

		toolItemAddressAddContact = new ToolItem(toolBarAddress, SWT.PUSH);
		toolItemAddressAddContact
				.addSelectionListener(new ToolItemAddressAddContactSelectionListener());
		toolItemAddressAddContact.setToolTipText("�����ϵ��");
		toolItemAddressAddContact.setText("����");

		toolItemAddressAddGroup = new ToolItem(toolBarAddress, SWT.PUSH);
		toolItemAddressAddGroup
				.addSelectionListener(new ToolItemAddressAddGroupSelectionListener());
		toolItemAddressAddGroup.setToolTipText("����Զ������");
		toolItemAddressAddGroup.setText("����");

		toolItemAddressSep2 = new ToolItem(toolBarAddress, SWT.SEPARATOR);
		toolItemAddressSep2.setText("New item");

		toolItemAddressEdit = new ToolItem(toolBarAddress, SWT.PUSH);
		toolItemAddressEdit
				.addSelectionListener(new ToolItemAddressEditSelectionListener());
		toolItemAddressEdit.setEnabled(false);
		toolItemAddressEdit.setToolTipText("�鿴�ͱ༭��ǰ��ϵ��");
		toolItemAddressEdit.setText("��");

		toolItemAddressPermission = new ToolItem(toolBarAddress, SWT.PUSH);
		toolItemAddressPermission
				.addSelectionListener(new ToolItemAddressPermissionSelectionListener());
		toolItemAddressPermission.setEnabled(false);
		toolItemAddressPermission.setToolTipText("�༭��ǰ��ϵ�˵�Ȩ��");
		toolItemAddressPermission.setText("Ȩ");

		toolItemAddressDel = new ToolItem(toolBarAddress, SWT.PUSH);
		toolItemAddressDel
				.addSelectionListener(new ToolItemAddressDelSelectionListener());
		toolItemAddressDel.setEnabled(false);
		toolItemAddressDel.setToolTipText("ɾ����ǰ��ϵ��");
		toolItemAddressDel.setText("ɾ");

		toolItemAddressCustomGroup = new ToolItem(toolBarAddress, SWT.DROP_DOWN);
		toolItemAddressCustomGroup.setEnabled(false);
		toolItemAddressCustomGroup.setToolTipText("ָ����ǰ��ϵ�˵ķ���");
		toolItemAddressCustomGroup.setText("����");

		toolItemAddressCustomGroupMenu = new Menu(toolBarAddress);
		addDropDown(toolItemAddressCustomGroup, toolItemAddressCustomGroupMenu);

		toolItemAddressSep1 = new ToolItem(toolBarAddress, SWT.SEPARATOR);
		toolItemAddressSep1.setText("New item");

		toolItemAddressSyncLocal = new ToolItem(toolBarAddress, SWT.PUSH);
		toolItemAddressSyncLocal
				.addSelectionListener(new ToolItemAddressSyncLocalSelectionListener());
		toolItemAddressSyncLocal.setToolTipText("��Outlook��������б���ͬ��");
		toolItemAddressSyncLocal.setText(Messages
				.getString("MainWindow.SyncLocal")); //$NON-NLS-1$

		toolItemAddressSyncRemote = new ToolItem(toolBarAddress, SWT.PUSH);
		toolItemAddressSyncRemote
				.addSelectionListener(new ToolItemAddressSyncRemoteSelectionListener());
		toolItemAddressSyncRemote.setToolTipText("�����������Զ��ͬ��");
		toolItemAddressSyncRemote.setText(Messages
				.getString("MainWindow.SyncRemote")); //$NON-NLS-1$

		toolItemAddressGroupBy = new ToolItem(toolBarAddress, SWT.DROP_DOWN);
		toolItemAddressGroupBy.setToolTipText("ѡ����ϵ�˵ķ���");
		toolItemAddressGroupBy.setText("������ʾ");

		toolItemAddressGroupByMenu = new Menu(toolBarAddress);
		addDropDown(toolItemAddressGroupBy, toolItemAddressGroupByMenu);

		toolItemAddressGroupByNone = new MenuItem(toolItemAddressGroupByMenu,
				SWT.RADIO);
		toolItemAddressGroupByNone.setText("��������ʾ");

		toolItemAddressGroupByCustomGroup = new MenuItem(
				toolItemAddressGroupByMenu, SWT.RADIO);
		toolItemAddressGroupByCustomGroup.setText("������");

		toolItemAddressGroupByGroup = new MenuItem(toolItemAddressGroupByMenu,
				SWT.RADIO);
		toolItemAddressGroupByGroup.setText("��Ⱥ��");

		toolItemAddressGroupByRelation = new MenuItem(
				toolItemAddressGroupByMenu, SWT.RADIO);
		toolItemAddressGroupByRelation.setText("����ϵ");

		toolItemAddressGroupByInitial = new MenuItem(
				toolItemAddressGroupByMenu, SWT.RADIO);
		toolItemAddressGroupByInitial.setText("����������ĸ");

		textAddressSearch = new Text(compositeAddress, SWT.BORDER);
		textAddressSearch
				.addModifyListener(new TextAddressSearchModifyListener());
		textAddressSearch.setToolTipText(Messages
				.getString("MainWindow.SearchContacts")); //$NON-NLS-1$
		final FormData fd_textAddressSearch = new FormData();
		fd_textAddressSearch.left = new FormAttachment(100, -118);
		fd_textAddressSearch.right = new FormAttachment(100, -9);
		fd_textAddressSearch.bottom = new FormAttachment(0, 33);
		fd_textAddressSearch.top = new FormAttachment(0, 10);
		textAddressSearch.setLayoutData(fd_textAddressSearch);
		compositeMainStackLayout.topControl = compositeAddress;

		labelAddressSearch = new Label(compositeAddress, SWT.NONE);
		final FormData fd_labelAddressSearch = new FormData();
		fd_labelAddressSearch.right = new FormAttachment(textAddressSearch,
				-16, SWT.LEFT);
		fd_labelAddressSearch.top = new FormAttachment(textAddressSearch, 0,
				SWT.TOP);
		labelAddressSearch.setLayoutData(fd_labelAddressSearch);
		labelAddressSearch.setText("����");

		tabFolderAddress = new TabFolder(compositeAddress, SWT.NONE);
		final FormData fd_tabFolderAddress = new FormData();
		fd_tabFolderAddress.bottom = new FormAttachment(100, 0);
		fd_tabFolderAddress.right = new FormAttachment(100, 0);
		fd_tabFolderAddress.left = new FormAttachment(0, 0);
		fd_tabFolderAddress.top = new FormAttachment(toolBarAddress, 0,
				SWT.DEFAULT);
		tabFolderAddress.setLayoutData(fd_tabFolderAddress);

		tabItemAddressContact = new TabItem(tabFolderAddress, SWT.NONE);
		tabItemAddressContact.setText("��ϵ��");

		treeAddressContact = new Tree(tabFolderAddress, SWT.BORDER);
		treeAddressContact
				.addSelectionListener(new TreeAddressContactSelectionListener());
		treeAddressContact.setSortColumn(null);
		treeAddressContact.setHeaderVisible(true);
		tabItemAddressContact.setControl(treeAddressContact);

		treeAddressContactColumnName = new TreeColumn(treeAddressContact,
				SWT.NONE);
		treeAddressContactColumnName.setWidth(100);
		treeAddressContactColumnName.setText("����");

		treeAddressContactColumnNickName = new TreeColumn(treeAddressContact,
				SWT.NONE);
		treeAddressContactColumnNickName.setWidth(100);
		treeAddressContactColumnNickName.setText("�ǳ�");

		treeAddressContactColumnCall = new TreeColumn(treeAddressContact,
				SWT.NONE);
		treeAddressContactColumnCall.setWidth(100);
		treeAddressContactColumnCall.setText("�ƺ�");

		treeAddressContactColumnRelation = new TreeColumn(treeAddressContact,
				SWT.NONE);
		treeAddressContactColumnRelation.setWidth(100);
		treeAddressContactColumnRelation.setText("��ϵ");

		treeAddressContactColumnCellPhone = new TreeColumn(treeAddressContact,
				SWT.NONE);
		treeAddressContactColumnCellPhone.setWidth(100);
		treeAddressContactColumnCellPhone.setText("�ֻ�");

		treeAddressContactColumnEmail = new TreeColumn(treeAddressContact,
				SWT.NONE);
		treeAddressContactColumnEmail.setWidth(100);
		treeAddressContactColumnEmail.setText("E-mail");

		treeAddressPermitColumnEmail = new TreeColumn(treeAddressContact,
				SWT.NONE);
		treeAddressPermitColumnEmail.setWidth(100);
		treeAddressPermitColumnEmail.setText("����");
		tabItemAddressPermit = new TabItem(tabFolderAddress, SWT.NONE);
		tabItemAddressPermit.setText("����Ȩ��ϵ��");

		treeAddressPermit = new Tree(tabFolderAddress, SWT.BORDER);
		treeAddressPermit
				.addSelectionListener(new TreeAddressContactSelectionListener());
		treeAddressPermit.setHeaderVisible(true);
		tabItemAddressPermit.setControl(treeAddressPermit);

		treeAddressPermitColumnName = new TreeColumn(treeAddressPermit,
				SWT.NONE);
		treeAddressPermitColumnName.setWidth(100);
		treeAddressPermitColumnName.setText("����");

		treeAddressPermitColumnNickName = new TreeColumn(treeAddressPermit,
				SWT.NONE);
		treeAddressPermitColumnNickName.setWidth(100);
		treeAddressPermitColumnNickName.setText("�ǳ�");

		treeAddressPermitColumnCall = new TreeColumn(treeAddressPermit,
				SWT.NONE);
		treeAddressPermitColumnCall.setWidth(100);
		treeAddressPermitColumnCall.setText("�ƺ�");

		treeAddressPermitColumnRelation = new TreeColumn(treeAddressPermit,
				SWT.NONE);
		treeAddressPermitColumnRelation.setWidth(100);
		treeAddressPermitColumnRelation.setText("��ϵ");

		treeAddressPermitColumnCellPhone = new TreeColumn(treeAddressPermit,
				SWT.NONE);
		treeAddressPermitColumnCellPhone.setWidth(100);
		treeAddressPermitColumnCellPhone.setText("�ֻ�");

		treeAddressPermitColumnEmail = new TreeColumn(treeAddressPermit,
				SWT.NONE);
		treeAddressPermitColumnEmail.setWidth(100);
		treeAddressPermitColumnEmail.setText("E-mail");
		
		treeAddressPermitColumnEmail = new TreeColumn(treeAddressPermit,
				SWT.NONE);
		treeAddressPermitColumnEmail.setWidth(100);
		treeAddressPermitColumnEmail.setText("����");
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

		// ����������ť
		toolItemMainRegister = new ToolItem(toolBarMain, SWT.PUSH);
		toolItemMainRegister.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				// MessageDialog.openInformation(shell, "hello", "regist");
				// Shell regist=new Shell();
				// regist.open();
				// Dialog a=new Dialog(shell);
				RegistDialog r = new RegistDialog(shell, SWT.None);
				r.open();
			}
		});
		toolItemMainRegister.setText(Messages.getString("MainWindow.Register")); //$NON-NLS-1$

		toolItemMainLogin = new ToolItem(toolBarMain, SWT.PUSH);
		toolItemMainLogin.setText(Messages.getString("MainWindow.Login")); //$NON-NLS-1$

		toolItemMainLogout = new ToolItem(toolBarMain, SWT.PUSH);
		toolItemMainLogout.setText(Messages.getString("MainWindow.Logout")); //$NON-NLS-1$

		// ��ఴť
		listButtonUserInfo = new Button(compositeLeftList, SWT.TOGGLE);
		listButtonUserInfo.setSelection(true);
		listButtonUserInfo
				.addSelectionListener(new ListButtonUserInfoSelectionListener());
		final GridData gd_listButtonUserInfo = new GridData(SWT.FILL,
				SWT.CENTER, true, false);
		gd_listButtonUserInfo.heightHint = 35;
		listButtonUserInfo.setLayoutData(gd_listButtonUserInfo);
		listButtonUserInfo.setText(Messages.getString("MainWindow.UserInfo")); //$NON-NLS-1$

		listButtonAddressBook = new Button(compositeLeftList, SWT.TOGGLE);
		listButtonAddressBook
				.addSelectionListener(new ListButtonAddressBookSelectionListener());
		final GridData gd_listButtonAddressBook = new GridData(SWT.FILL,
				SWT.CENTER, true, false);
		gd_listButtonAddressBook.heightHint = 35;
		listButtonAddressBook.setLayoutData(gd_listButtonAddressBook);
		listButtonAddressBook.setText(Messages
				.getString("MainWindow.AddressBook")); //$NON-NLS-1$

		listButtonGroup = new Button(compositeLeftList, SWT.TOGGLE);
		listButtonGroup
				.addSelectionListener(new ListButtonGroupSelectionListener());
		final GridData gd_listButtonGroup = new GridData(SWT.FILL, SWT.CENTER,
				true, false);
		gd_listButtonGroup.heightHint = 35;
		listButtonGroup.setLayoutData(gd_listButtonGroup);
		listButtonGroup.setText(Messages.getString("MainWindow.Group")); //$NON-NLS-1$

		listButtonSearch = new Button(compositeLeftList, SWT.TOGGLE);
		listButtonSearch
				.addSelectionListener(new ListButtonSearchSelectionListener());
		final GridData gd_listButtonSearch = new GridData(SWT.FILL, SWT.CENTER,
				true, false);
		gd_listButtonSearch.heightHint = 35;
		listButtonSearch.setLayoutData(gd_listButtonSearch);
		listButtonSearch.setText(Messages.getString("MainWindow.RelationCube")); //$NON-NLS-1$

		final GridData gd_listButtonMessageBox = new GridData(SWT.FILL,
				SWT.CENTER, true, false);
		gd_listButtonMessageBox.heightHint = 35;
		listButtonMessageBox = new Button(compositeLeftList, SWT.TOGGLE);
		listButtonMessageBox.setLayoutData(gd_listButtonMessageBox);
		listButtonMessageBox.setText(Messages
				.getString("MainWindow.listButtonMessageBox.text")); //$NON-NLS-1$
		listButtonMessageBox
				.addSelectionListener(new ListButtonMessageBoxListener());

		// ��ʼ��ָ��
		// (new ListButtonUserInfoSelectionListener()).widgetSelected(null);
		// MessageBox
		messageCompsite = new MessageBoxDialog(compositeMain, SWT.NONE);

		// ����������
		// !TODO
		renlifang = new Renlifang(compositeMain, SWT.NONE);
		listButtonUserInfo.setSelection(true);
		scrolledCompositeInfo.setVisible(true);
		compositeMainStackLayout.topControl = scrolledCompositeInfo;
		listButtonAddressBook.setSelection(false);
		compositeAddress.setVisible(false);
		renlifang.setVisible(false);
		messageCompsite.setVisible(false);
		listButtonGroup.setSelection(false);
		listButtonSearch.setSelection(false);
		listButtonMessageBox.setSelection(false);
		
		//SpaceFlyer: ��ص��߼����ִ����￪ʼ
		RefreshObserver observer = new RefreshObserver();
		AllContactsBox box = logicCenter.getAllContactsBox();
		box.addObserver(observer);
		if (box.getState() == VirtualState.PREPARED)
			refreshContacts(box.getContacts());
		//TODO ERRORED�Ĵ���
		
		//TODO ��������£�Ӧ�õ�¼���ȡMessageBox����ǰֻ�ǲ���
		MessageBoxObserver mObserver = new MessageBoxObserver();
		MessageBox mBox = logicCenter.getMessageBox();
		mBox.addObserver(mObserver);
		if (box.getState() == VirtualState.PREPARED)
			refreshMessageBox(mBox.getMessages());
		//TODO ERRORED�Ĵ���
	}

	/**
	 * ��TreeItem�м����ӽ��
	 * 
	 * @param parentItem
	 *            �����
	 * @param items
	 *            �ӽ������
	 * @return �ӽڵ�
	 */
	private TreeItem createTreeSubItem(TreeItem parentItem, String... items) {
		TreeItem childItem = new TreeItem(parentItem, SWT.NONE);

		for (int i = 0; i < items.length; i++) {
			childItem.setText(i, items[i]);
		}
		return childItem;
	}

	/**
	 * ȡ����ϵ��������Ȩ��ϵ�����ĵ�ǰѡ����
	 * 
	 * @return
	 */
	private TreeItem getCurrentTreeItem() {
		TreeItem current = null;
		if (tabFolderAddress.getSelection()[0] == tabItemAddressContact) {
			current = treeAddressContact.getSelection()[0];
		} else if (tabFolderAddress.getSelection()[0] == tabItemAddressPermit) {
			current = treeAddressPermit.getSelection()[0];
		}

		return current;
	}

	/**
	 * ���ݳ�ʼ������
	 */
	private void dataInit() {
		// TODO ���Ƹ�����Ϣ���Զ����ֶεĿؼ�
		// TODO �����ݿ��ж�ȡ������Ϣ��������ؿؼ�������DataBinding��

		treeAddressContactItemNoGroup = new TreeItem(treeAddressContact,
				SWT.NONE);
		treeAddressContactItemNoGroup.setText("δ����");

		// TODO �����ݿ��ж�ȡͬ����ϵ�ˣ�������ؿؼ�������DataBinding��
		// Sample Code:
	
		TreeItem item1 = new TreeItem(treeAddressContact, SWT.NONE);
		item1.setText("March");
		createTreeSubItem(item1, "�����", "SpaceFlyer", "�ϴ�", "��ѧͬѧ",
				"13800000000", "Space@Flyer.com");
		createTreeSubItem(item1, "����", "Wander", "", "", "13900000000",
				"Wander@Wander.com");
		item1.setExpanded(true);
		// Sample Code End.
		treeAddressContactItemNoGroup.setExpanded(true);

		treeAddressPermitItemNoGroup = new TreeItem(treeAddressPermit, SWT.NONE);
		treeAddressPermitItemNoGroup.setText("δ����");

		// TODO �����ݿ��ж�ȡ����Ȩ��ϵ�ˣ�������ؿؼ�������DataBinding��
		// Sample Code:
		TreeItem item2 = new TreeItem(treeAddressPermit, SWT.NONE);
		item2.setText("ҵ��");
		createTreeSubItem(item2, "������", "", "����", "��ʦ", "13600000000",
				"xzx@tsinghua.edu.cn");
		item2.setExpanded(true);
		// Sample Code End.
		treeAddressPermitItemNoGroup.setExpanded(true);

		// ���������뱣��
		toolItemAddressCustomGroupNone = new MenuItem(
				toolItemAddressCustomGroupMenu, SWT.RADIO);
		toolItemAddressCustomGroupNone
				.addSelectionListener(new ToolItemAddressCustomGroupSelectionListener());
		toolItemAddressCustomGroupNone.setText("δ����");

		// TODO: �����ݿ��ж�ȡ��ǰ���еķ��飬������ؿؼ�
		// Sample Code:
		toolItemAddressCustomGroupItems = new MenuItem[1]; // 1: ��������
		// ���ﻻ��ѭ��
		toolItemAddressCustomGroupItems[0] = new MenuItem(
				toolItemAddressCustomGroupMenu, SWT.RADIO);
		toolItemAddressCustomGroupItems[0].setText("March");
		toolItemAddressCustomGroupItems[0].setData(123); // 123: �Զ������id��
		// Sample Code End.

		new MenuItem(toolItemAddressCustomGroupMenu, SWT.SEPARATOR);
		toolItemAddressCustomGroupNew = new MenuItem(
				toolItemAddressCustomGroupMenu, SWT.NONE);
		toolItemAddressCustomGroupNew
				.addSelectionListener(new ToolItemAddressCustomGroupNewSelectionListener());
		toolItemAddressCustomGroupNew.setText("�½�����...");
	}

	// ���ѡ��ť �¼�
	// ��һ����ť �û���Ϣ
	private class ListButtonUserInfoSelectionListener extends SelectionAdapter {
		public void widgetSelected(final SelectionEvent e) {
			listButtonUserInfo.setSelection(true);
			scrolledCompositeInfo.setVisible(true);
			compositeMainStackLayout.topControl = scrolledCompositeInfo;
			listButtonAddressBook.setSelection(false);
			compositeAddress.setVisible(false);
			renlifang.setVisible(false);
			messageCompsite.setVisible(false);
			listButtonGroup.setSelection(false);
			listButtonSearch.setSelection(false);
			listButtonMessageBox.setSelection(false);
		}
	}

	private class ListButtonAddressBookSelectionListener extends
			SelectionAdapter {
		public void widgetSelected(final SelectionEvent e) {
			listButtonUserInfo.setSelection(false);
			scrolledCompositeInfo.setVisible(false);
			listButtonAddressBook.setSelection(true);
			compositeAddress.setVisible(true);
			renlifang.setVisible(false);
			messageCompsite.setVisible(false);
			compositeMainStackLayout.topControl = compositeAddress;
			listButtonGroup.setSelection(false);
			listButtonSearch.setSelection(false);
			listButtonMessageBox.setSelection(false);

		}
	}

	private class ListButtonGroupSelectionListener extends SelectionAdapter {
		public void widgetSelected(final SelectionEvent e) {
			listButtonUserInfo.setSelection(false);
			scrolledCompositeInfo.setVisible(false);
			listButtonAddressBook.setSelection(false);
			compositeAddress.setVisible(false);
			listButtonGroup.setSelection(true);
			listButtonSearch.setSelection(false);
			listButtonMessageBox.setSelection(false);
			renlifang.setVisible(false);
			messageCompsite.setVisible(false);
		}
	}

	// ѡ�� ������ ����
	private class ListButtonSearchSelectionListener extends SelectionAdapter {
		public void widgetSelected(final SelectionEvent e) {
			listButtonUserInfo.setSelection(false);
			scrolledCompositeInfo.setVisible(false);
			listButtonAddressBook.setSelection(false);
			compositeAddress.setVisible(false);
			listButtonGroup.setSelection(false);
			listButtonSearch.setSelection(true);
			listButtonMessageBox.setSelection(false);
			renlifang.setVisible(true);
			messageCompsite.setVisible(false);
			compositeMainStackLayout.topControl = renlifang;
			System.out.println("renlifang");
		}
	}

	private class ListButtonMessageBoxListener extends SelectionAdapter {
		public void widgetSelected(final SelectionEvent e) {
			listButtonUserInfo.setSelection(false);
			scrolledCompositeInfo.setVisible(false);
			listButtonAddressBook.setSelection(false);
			compositeAddress.setVisible(false);
			listButtonGroup.setSelection(false);
			listButtonSearch.setSelection(false);
			listButtonMessageBox.setSelection(true);
			renlifang.setVisible(false);
			messageCompsite.setVisible(true);
			compositeMainStackLayout.topControl = messageCompsite;
			System.out.println("messagebox");
		}
	}

	// ������Ϣ ����¼�
	/**
	 * ����ͷ��
	 */
	private class CanvasInfoAvatarPaintListener implements PaintListener {
		public void paintControl(final PaintEvent e) {
			Image image = (Image) canvasInfoAvatar.getData();
			if (image != null)
				e.gc.drawImage(image, 0, 0, image.getBounds().width, image
						.getBounds().height, 0, 0,
						canvasInfoAvatar.getBounds().width, canvasInfoAvatar
								.getBounds().height);
		}
	}

	/**
	 * ������ͷ��
	 * 
	 * @author Wander
	 * 
	 */
	private class ButtonInfoChangeAvatarSelectionListener extends
			SelectionAdapter {
		public void widgetSelected(final SelectionEvent e) {
			FileDialog dlg = new FileDialog(shell, SWT.OPEN);
			String fileName = dlg.open();
			if (fileName != null) {
				Image image = new Image(null, fileName);
				canvasInfoAvatar.setData(image);
				canvasInfoAvatar.redraw();
				// TODO: ���ͼƬ��ʽ�������û�ͷ�����ݿ�
			}
		}
	}

	/**
	 * ����ֻ��źϷ���
	 * 
	 * @author Wander
	 * 
	 */
	private class TextInfoCellPhoneFocusListener extends FocusAdapter {
		public void focusLost(final FocusEvent e) {
			// TODO: ����ֻ��źϷ���
		}
	}

	/**
	 * ���E-mail�Ϸ���
	 * 
	 * @author Wander
	 * 
	 */
	private class TextInfoEmailFocusListener extends FocusAdapter {
		public void focusLost(final FocusEvent e) {
			// TODO: ���E-mail�Ϸ���
		}
	}

	/**
	 * ���QQ�źϷ���
	 * 
	 * @author Wander
	 * 
	 */
	private class TextInfoQQNumberFocusListener extends FocusAdapter {
		public void focusLost(final FocusEvent e) {
			// TODO: ���QQ�źϷ���
		}
	}

	/**
	 * ����ͥ�绰�Ϸ���
	 * 
	 * @author Wander
	 * 
	 */
	private class TextInfoFamilyPhoneFocusListener extends FocusAdapter {
		public void focusLost(final FocusEvent e) {
			// TODO: ����ͥ�绰�Ϸ���
		}
	}

	/**
	 * ��鵥λ�绰�Ϸ���
	 * 
	 * @author Wander
	 * 
	 */
	private class TextInfoCompanyPhoneFocusListener extends FocusAdapter {
		public void focusLost(final FocusEvent e) {
			// TODO: ��鵥λ�绰�Ϸ���
		}
	}

	/**
	 * ���MSN�Ϸ���
	 * 
	 * @author Wander
	 * 
	 */
	private class LabelInfoMSNFocusListener extends FocusAdapter {
		public void focusLost(final FocusEvent e) {
			// TODO: ���MSN�Ϸ���
		}
	}

	/**
	 * �����Ը�����Ϣ���޸�
	 * 
	 * @author Wander
	 * 
	 */
	private class ToolItemInfoResetSelectionListener extends SelectionAdapter {
		public void widgetSelected(final SelectionEvent e) {
			if (MessageDialog.openConfirm(shell, "ȷ������", "ȷʵҪ���������޸���")) {
				// TODO: ���������޸�
			}
		}
	}

	/**
	 * ����Ը�����Ϣ���޸�
	 * 
	 * @author Wander
	 * 
	 */
	private class ToolItemInfoSaveSelectionListener extends SelectionAdapter {
		public void widgetSelected(final SelectionEvent e) {
			// TODO: ��������Ϣ�����ڱ������ݿ�
		}
	}

	/**
	 * ��������Ϣ�ϴ���������
	 * 
	 * @author Wander
	 * 
	 */
	private class ToolItemInfoUploadSelectionListener extends SelectionAdapter {
		public void widgetSelected(final SelectionEvent e) {
			// TODO: ��������Ϣ�ϴ���������
		}
	}

	/**
	 * �ӷ����������ظ�����Ϣ
	 * 
	 * @author Wander
	 * 
	 */
	private class ToolItemInfoDownloadSelectionListener extends
			SelectionAdapter {
		public void widgetSelected(final SelectionEvent e) {
			if (false || // true = ���صĸ�����Ϣ����޸�ʱ��ȷ���������
			MessageDialog.openConfirm(shell, "ȷ������",
					"���صĸ�����Ϣ�ȷ��������£�ȷʵҪ�÷������ϵ����ݸ��Ǳ���������")) {
				// TODO: �ӷ����������ظ�����Ϣ
			}
		}
	}

	// ͨѶ¼ ����¼�
	/**
	 * ��ϵ�˻���鱻ѡ��
	 */
	private class TreeAddressContactSelectionListener extends SelectionAdapter {
		public void widgetSelected(final SelectionEvent e) {
			TreeItem current = (TreeItem) e.item;

			if (current.getParentItem() == null) // ѡ���˷���
			{
				toolItemAddressPermission.setEnabled(false);
				toolItemAddressCustomGroup.setEnabled(false);

				toolItemAddressPermission.setToolTipText("");
				toolItemAddressCustomGroup.setToolTipText("");
				toolItemAddressEdit.setToolTipText(String.format("����������\"%s\"",
						current.getText(0)));
				toolItemAddressDel.setToolTipText(String.format("ɾ������\"%s\"",
						current.getText(0)));
			} else // ѡ������ϵ��
			{
				toolItemAddressPermission.setEnabled(true);
				toolItemAddressCustomGroup.setEnabled(true);

				toolItemAddressEdit.setToolTipText(String.format("�༭��ϵ��\"%s\"",
						current.getText(0)));
				toolItemAddressDel.setToolTipText(String.format("ɾ����ϵ��\"%s\"",
						current.getText(0)));
				toolItemAddressPermission.setToolTipText(String.format(
						"Ϊ\"%s\"����Ȩ��", current.getText(0)));
				toolItemAddressCustomGroup.setToolTipText(String.format(
						"Ϊ\"%s\"�����Զ������", current.getText(0)));

				toolItemAddressCustomGroupNone.setSelection(false);
				for (MenuItem item : toolItemAddressCustomGroupItems) {
					item.setSelection(false);
				}

				// TODO: ��ȡ��ǰ��ϵ�������Զ�����飬�ڡ����顰��ť�������˵���ѡ����Ӧ����
				// Sample Code:
				toolItemAddressCustomGroupItems[0].setSelection(true);
				// δ����Sample Code:
				// toolItemAddressCustomGroupNone.setSelection(true);
			}

			toolItemAddressEdit.setEnabled(true);
			toolItemAddressDel.setEnabled(true);

		}
	}

	/**
	 * �����ϵ��
	 * 
	 * @author Wander
	 * 
	 */
	private class ToolItemAddressAddContactSelectionListener extends
			SelectionAdapter {
		public void widgetSelected(final SelectionEvent e) {
			// TODO: �����ϵ��
			AddContactDialog addContac = new AddContactDialog(shell, SWT.None);
			addContac.open();
		}
	}

	/**
	 * ��ӷ���
	 * 
	 * @author Wander
	 * 
	 */
	private class ToolItemAddressAddGroupSelectionListener extends
			SelectionAdapter {
		public void widgetSelected(final SelectionEvent e) {
			// TODO: ��ӷ���
		}
	}

	/**
	 * �༭��ϵ�˻����
	 * 
	 * @author Wander
	 * 
	 */
	private class ToolItemAddressEditSelectionListener extends SelectionAdapter {
		public void widgetSelected(final SelectionEvent e) {
			TreeItem current = getCurrentTreeItem();

			if (current != null) {
				if (current.getParentItem() == null) {
					// TODO: ����������
					// Debug:
					MessageDialog.openInformation(shell, "����������", current
							.getText());
				} else {
					// TODO: �༭��ϵ��
					// Debug:
					EditContactDialog editContac = new EditContactDialog(shell,
							SWT.None);
					editContac.open();

					// MessageDialog.openInformation(shell, "�༭��ϵ��", current
					// .getText());
				}
			}
		}
	}

	/**
	 * �༭��ϵ��Ȩ��
	 * 
	 * @author Wander
	 * 
	 */
	private class ToolItemAddressPermissionSelectionListener extends
			SelectionAdapter {
		public void widgetSelected(final SelectionEvent e) {
			TreeItem current = getCurrentTreeItem();

			if (current != null && current.getParentItem() != null) {
				// TODO: �༭��ϵ��Ȩ��
				// Debug:
				MessageDialog.openInformation(shell, "�༭��ϵ��Ȩ��", current
						.getText());
			}
		}
	}

	/**
	 * ɾ����ϵ�˻����
	 * 
	 * @author Wander
	 * 
	 */
	private class ToolItemAddressDelSelectionListener extends SelectionAdapter {
		public void widgetSelected(final SelectionEvent e) {
			TreeItem current = getCurrentTreeItem();

			if (current != null) {
				if (current.getParentItem() == null) {
					// TODO: ɾ������
					// Debug:
					MessageDialog.openInformation(shell, "ɾ������", current
							.getText());
				} else {
					// TODO: ɾ����ϵ��
					// Debug:
					MessageDialog.openInformation(shell, "ɾ����ϵ��", current
							.getText());
				}
			}
		}
	}

	/**
	 * Ϊ��ϵ�������Զ������
	 * 
	 * @author Wander
	 * 
	 */
	private class ToolItemAddressCustomGroupSelectionListener extends
			SelectionAdapter {
		public void widgetSelected(final SelectionEvent e) {
			TreeItem current = getCurrentTreeItem();
			if (current == null || current.getParentItem() == null)
				return;

			if (toolItemAddressCustomGroupNone.getSelection()) {
				// TODO: ���õ�ǰ��ϵ��Ϊ��δ���顰
				// Debug:
				MessageDialog.openInformation(shell, "���÷���", "id = 0");
			} else {
				for (MenuItem item : toolItemAddressCustomGroupItems) {
					if (item.getSelection()) {
						// TODO: ���õ�ǰ��ϵ�˵ķ���
						// Debug:
						MessageDialog.openInformation(shell, "���÷���", "id = "
								+ ((Integer) item.getData()).toString());
					}
				}
			}
		}
	}

	/**
	 * �½�����
	 * 
	 * @author Wander
	 * 
	 */
	private class ToolItemAddressCustomGroupNewSelectionListener extends
			SelectionAdapter {
		public void widgetSelected(final SelectionEvent e) {
			// TODO: �½�����
		}
	}

	/**
	 * ����ͬ��
	 * 
	 * @author Wander
	 * 
	 */
	private class ToolItemAddressSyncLocalSelectionListener extends
			SelectionAdapter {
		public void widgetSelected(final SelectionEvent e) {
			// TODO: ����ͬ��
		}
	}

	/**
	 * Զ��ͬ��
	 * 
	 * @author Wander
	 * 
	 */
	private class ToolItemAddressSyncRemoteSelectionListener extends
			SelectionAdapter {
		public void widgetSelected(final SelectionEvent e) {
			// TODO: Զ��ͬ��
		}
	}

	/**
	 * �ڱ���������ϵ��
	 * 
	 * @author Wander
	 * 
	 */
	private class TextAddressSearchModifyListener implements ModifyListener {
		public void modifyText(final ModifyEvent e) {
			// TODO: �ڱ���������ϵ�ˣ���ÿ�����ƴ�����������ֿ⣩
		}
	}

	private static void addDropDown(final ToolItem item, final Menu menu) {
		item.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (event.detail == SWT.ARROW) {
					Rectangle rect = item.getBounds();
					Point pt = new Point(rect.x, rect.y + rect.height);
					pt = item.getParent().toDisplay(pt);
					menu.setLocation(pt.x, pt.y);
					menu.setVisible(true);
				}
			}
		});
	}
	
	class RefreshContactTask implements Runnable{
		private List<UserInfo> users;
		@Override
		public void run() {
			// TODO Auto-generated method stub
			int n=users.size();
			TreeItem item1 = new TreeItem(treeAddressContact, SWT.NONE);
			item1.setText("sql");
			for(int i=0;i<n;i++){
				String name=users.get(i).getBaseInfo().getInfoField("Name").getStringValue();
				String nick=users.get(i).getCustomInfo().getInfoField("NickName").getStringValue();
				String cell=users.get(i).getBaseInfo().getInfoField("CellPhone").getStringValue();
				String email=users.get(i).getBaseInfo().getInfoField("EmailAddress").getStringValue();
				String tag=users.get(i).getCustomInfo().getInfoField("Category").getStringValue();
				createTreeSubItem(item1, name, nick, "", tag, cell, email);
				item1.setExpanded(true);				
			}
		}
		
		public RefreshContactTask(List<UserInfo> users){
			this.users = users;
		}
	}
	
	void refreshContacts(List<UserInfo> users){
		Display.getDefault().syncExec(new RefreshContactTask(users));
	}
	
	void refreshMessageBox(List<Message> messages)
	{
		//TODO LIJING
	}
	
	class RefreshObserver implements Observer
	{
		@Override
		public void update(Observable o, Object arg) 
		{
			AllContactsBox allContactsBox = (AllContactsBox)o;
			refreshContacts(allContactsBox.getContacts());
		}
	}
	
	class MessageBoxObserver implements Observer{
		Date lastUpdateTime = null;
		@Override
		public void update(Observable o, Object arg1) {
			MessageBox box = (MessageBox)o;
			refreshMessageBox(box.getMessages());
		}
	}
}
