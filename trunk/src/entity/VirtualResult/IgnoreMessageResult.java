package entity.VirtualResult;

import logiccenter.LogicCenter;
import entity.BoolInfo;
import entity.ErrorType;
import entity.message.Message;

public class IgnoreMessageResult extends OneTimeVirtualResult {
	private Message msg;

	@Override
	protected BoolInfo getResult() throws Exception {
		return center.getServer().ignoreMessage(center.getLoginUser().getID(), msg);
	}

	public IgnoreMessageResult(Message msg, LogicCenter center){
		super(center);
		this.msg = msg;
		if (noLoginUser())
			setError(ErrorType.NOT_LOGIN);
		else
			thread.start();
	}
}
