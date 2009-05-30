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
		return center.getServer().removeMessage(center.getLoginUser().getID(), msg);
	}

	public RemoveMessageResult(Message msg, LogicCenter center){
		super(center);
		this.msg = msg;
		if (noLoginUser())
			setError(ErrorType.NOT_LOGIN);
		else
			thread.start();
	}
}
