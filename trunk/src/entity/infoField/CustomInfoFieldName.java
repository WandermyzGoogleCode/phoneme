package entity.infoField;

public enum CustomInfoFieldName {
	NickName, Remarks, Category, Relation, Extension;
	
	static public boolean contains(String name){
		for(CustomInfoFieldName i: values())
			if (i.name().equals(name))
				return true;
		return false;
	}
}
