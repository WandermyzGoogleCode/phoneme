package logiccenter.VirtualResult;

import java.rmi.RemoteException;

import logiccenter.LogicCenter;
import entity.BoolInfo;
import entity.ErrorType;
import entity.message.Message;

public class RemoveMessageResult extends OneTimeVirtualResult {
	private Message msg;

	@Override
	protected BoolInfo getResult() throws RemoteException{
		BoolInfo res = center.getServer().removeMessage(center.getLoginUser().getID(), msg);
		if (res.isTrue())
			center.getMessageBox().removeMessage(msg);
		return res;
	}

	public RemoveMessageResult(Message msg, LogicCenter center){
		super(center);
		this.msg = msg;
		if (noLoginUser())
			setError(ErrorType.NOT_LOGIN);
		else
			center.getExecutor().execute(task);
	}
}
