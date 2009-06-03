package logiccenter.VirtualResult;

import java.rmi.RemoteException;

import logiccenter.LogicCenter;
import entity.BaseUserInfo;
import entity.BoolInfo;
import entity.ErrorType;
import entity.Password;
import entity.infoField.IdenticalInfoField;
import entity.infoField.InfoField;

public class RegisterResult extends OneTimeVirtualResult {
	private BaseUserInfo b;
	private Password pwd;

	public RegisterResult(BaseUserInfo b, Password pwd, LogicCenter center) {
		super(center);
		this.b = b;
		this.pwd = pwd;
		boolean hasIdentical = false;
		for (String is : b.getKeySet()) {
			InfoField infoField = b.getInfoField(is);
			if (infoField instanceof IdenticalInfoField && !infoField.isEmpty()) {
				hasIdentical = true;
				break;
			}
		}
		if (!hasIdentical)
			setError(ErrorType.NO_IDENTICAL_FIELD);
		else
			center.getExecutor().execute(task);
	}

	@Override
	protected BoolInfo getResult() throws RemoteException {
		return center.getServer().register(b, pwd);
	}

}
