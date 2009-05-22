package serverLogicCenter;

import entity.ID;
import entity.message.Message;

import java.rmi.Remote;
import java.util.List;

public interface ServerLogicCenter extends Remote {
	/**
	 * 返回用户user所有未处理的信息
	 * @param user
	 * @return
	 */
	public List<Message> getAllMessages(ID user);
	/**
	 * 返回用户user最新更新的信息
	 * @param user
	 * @return
	 */
	public Message getNewMessage(ID user);
}
