package ui_test.UserInfoTable;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.widgets.Composite;

import entity.CustomUserInfo;
import entity.infoField.BaseInfoFieldName;
import entity.infoField.CustomInfoFieldName;
import entity.infoField.InfoField;

public class UserInfoTableElem
{
	private InfoField infoField;
	private UserInfoTableType userInfoTableType;
	
	
	public UserInfoTableElem(InfoField infoField, UserInfoTableType tableType)
	{
		this.infoField = infoField;
		this.userInfoTableType = tableType;
	}
	
	
	public InfoField GetInfoField()
	{
		return infoField;
	}
	
	public void SetInfoField(InfoField infoField)
	{
		this.infoField = infoField;
	}
	
	public boolean IsEditable()
	{
		if (!infoField.editable())
			return false;
		
		String fieldName = infoField.getName();
		
		if(fieldName == Messages.getString("UserInfoTableElem.relation")) return false; //$NON-NLS-1$
		
		if(userInfoTableType == UserInfoTableType.Local)
		{
			return true;
		}
		else if(userInfoTableType == UserInfoTableType.Synchronization)
		{
			return CustomInfoFieldName.contains(fieldName);
			//return CustomInfoFieldName.contains(fieldName);
		}
		else if(userInfoTableType == UserInfoTableType.Permission)
		{
			return CustomInfoFieldName.contains(fieldName);
			//return CustomInfoFieldName.contains(fieldName);
		}
		else if(userInfoTableType == UserInfoTableType.Owner)
		{
			return BaseInfoFieldName.contains(fieldName);
		}
		else if(userInfoTableType == UserInfoTableType.SearchRemoteResult)
		{
			return false;
		}
		else if(userInfoTableType == UserInfoTableType.SearchLocalForm)
		{
			return true;
		}
		else if(userInfoTableType == UserInfoTableType.Register)
		{
			return BaseInfoFieldName.contains(fieldName);
		}
		else if(userInfoTableType == UserInfoTableType.NewLocal)
		{
			return BaseInfoFieldName.contains(fieldName);
		}
		else if(userInfoTableType == UserInfoTableType.SearchRemoteForm)
		{
			return BaseInfoFieldName.contains(fieldName);
		}
		else return false;
	}
	
	public boolean IsVisible()
	{
		String fieldName = infoField.getName();
		
		if (!infoField.visible())
			return false;
		
		if(fieldName == Messages.getString("UserInfoTableElem.relation")) return false; //$NON-NLS-1$
		
		if(userInfoTableType == UserInfoTableType.Local)
		{
			return true;
		}
		if(userInfoTableType == UserInfoTableType.Synchronization)
		{
			//不用处理权限，权限在服务器端就处理好了的……哪有到本地再处理的//TODO: 处理权限
			return true;
		}
		else if(userInfoTableType == UserInfoTableType.Permission)
		{
			return true;
		}
		else if(userInfoTableType == UserInfoTableType.Owner)
		{
			return BaseInfoFieldName.contains(fieldName);
		}
		else if(userInfoTableType == UserInfoTableType.SearchRemoteResult)
		{
			//不用处理权限，权限在服务器端就处理好了的……哪有到本地再处理的//TODO: 处理权限
			return BaseInfoFieldName.contains(fieldName);
		}
		else if(userInfoTableType == UserInfoTableType.SearchLocalForm)
		{
			return true;
		}
		else if(userInfoTableType == UserInfoTableType.Register)
		{
			return BaseInfoFieldName.contains(fieldName);
		}
		else if(userInfoTableType == UserInfoTableType.NewLocal)
		{
			return BaseInfoFieldName.contains(fieldName);
		}
		else if(userInfoTableType == UserInfoTableType.SearchRemoteForm)
		{
			return BaseInfoFieldName.contains(fieldName);
		}
		else return true;//没有特殊申明，都显示出来，比如群组联系人
	}
	
	public CellEditor GetCellEditor(Composite parent)
	{
		return new TextCellEditor(parent);	
	}
	
}
