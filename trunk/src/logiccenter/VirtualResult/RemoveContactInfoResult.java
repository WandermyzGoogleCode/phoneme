package logiccenter.VirtualResult;

import java.rmi.RemoteException;

import logiccenter.LogicCenter;
import entity.BoolInfo;
import entity.ErrorType;
import entity.ID;
import entity.UserInfo;
import entity.infoField.InfoFieldName;
import entity.infoField.Relation;

public class RemoveContactInfoResult extends OneTimeVirtualResult {
	private ID id;

	public RemoveContactInfoResult(ID id, LogicCenter center) {
		super(center);
		this.id = id;
		thread.start();
	}

	@Override
	protected BoolInfo getResult() throws RemoteException{
		if (!noLoginUser()){
			center.getServer().removeSynContact(center.getLoginUser().getID(), id);
			center.getDataCenter().removeUserInfo(id);
			center.getAllContactsBox().removeContact(id);
		}
		else{
			UserInfo info = center.getAllContactsBox().getContactsMap().get(id);
			if (info == null)
				return new BoolInfo(ErrorType.TARGET_NOT_EXIST);
			Relation r = (Relation)info.getInfoField(InfoFieldName.Relation);
			if (r.isPersonal()){
				r.setRemoved(true);
				center.getDataCenter().setUserInfo(info);
			}
			else
				center.getDataCenter().removeUserInfo(info.getBaseInfo().getID());
			center.getAllContactsBox().setUpdateNow();
		}
		return new BoolInfo();
	}

}
