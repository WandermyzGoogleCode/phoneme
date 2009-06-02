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
		String fieldName = infoField.getName();
		
		if(fieldName == "Relation") return false;
		
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
		else if(userInfoTableType == UserInfoTableType.SearchResult)
		{
			return false;
		}
		else if(userInfoTableType == UserInfoTableType.SearchForm)
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
		else return false;
	}
	
	public boolean IsVisible()
	{
		String fieldName = infoField.getName();
		
		if(fieldName == "Relation") return false;
		
		if(userInfoTableType == UserInfoTableType.Local)
		{
			return true;
		}
		if(userInfoTableType == UserInfoTableType.Synchronization)
		{
			//TODO: 处理权限
			return true;
		}
		else if(userInfoTableType == UserInfoTableType.Permission)
		{
			return false;
		}
		else if(userInfoTableType == UserInfoTableType.Owner)
		{
			return BaseInfoFieldName.contains(fieldName);
		}
		else if(userInfoTableType == UserInfoTableType.SearchResult)
		{
			//TODO: 处理权限
			return BaseInfoFieldName.contains(fieldName);
		}
		else if(userInfoTableType == UserInfoTableType.SearchForm)
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
		else return false;
	}
	
	public CellEditor GetCellEditor(Composite parent)
	{
		return new TextCellEditor(parent);	
	}
	
}
