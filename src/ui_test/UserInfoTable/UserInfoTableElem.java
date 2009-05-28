package ui_test.UserInfoTable;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.widgets.Composite;

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
		if(userInfoTableType == UserInfoTableType.Synchronization)
		{
			return true;
			//return CustomInfoFieldName.contains(fieldName);
		}
		else if(userInfoTableType == UserInfoTableType.Permission)
		{
			return true;
			//return CustomInfoFieldName.contains(fieldName);
		}
		else if(userInfoTableType == UserInfoTableType.Owner)
		{
			return BaseInfoFieldName.contains(fieldName);
		}
		else if(userInfoTableType == UserInfoTableType.Search)
		{
			return false;
		}
		else return false;
	}
	
	public boolean IsVisible()
	{
		String fieldName = infoField.getName();
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
		else if(userInfoTableType == UserInfoTableType.Search)
		{
			//TODO: 处理权限
			return BaseInfoFieldName.contains(fieldName);
		}
		else return false;
	}
	
	public CellEditor GetCellEditor(Composite parent)
	{
		return new TextCellEditor(parent);	
	}
	
}
