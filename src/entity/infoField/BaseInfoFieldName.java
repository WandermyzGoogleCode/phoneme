package entity.infoField;

public enum BaseInfoFieldName {
	Address, Birthday, Cellphone, Company, EmailAddress,
	Image, Name, Phone,
	Position, QQNumber, Website;
	
	static public boolean contains(String name){
		for(BaseInfoFieldName i: values())
			if (i.name().equals(name))
				return true;
		return false;
	}
}
