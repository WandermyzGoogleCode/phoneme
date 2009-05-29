package entity.infoField;

public enum InfoFieldName {
	Address, Birthday, Cellphone, Company, EmailAddress,
	GroupDescription, GroupName, Image, Name, NickName, Phone,
	Position, QQNumber, Remarks, Category, Website, Relation;
	
	static public InfoFieldName get(String name){
		for(InfoFieldName i: InfoFieldName.values())
			if (name.equals(i.name()))
				return i;
		return null;
	}
}
