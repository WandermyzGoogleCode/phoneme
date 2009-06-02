package serverLogicCenter.sdataCenter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import entity.ID;
import entity.message.Message;

public class MessageTable {
	private Connection connection;

	public MessageTable(Connection connection) throws SQLException {
		this.connection = connection;

		// !!!如果表的设置有变动，必须删除表，然后等待该过程重新建立
		// 检查是否有表格，没有则建表
		Statement statement = (Statement) connection.createStatement();
		String sql = "DESCRIBE Message";
		try {
			statement.execute(sql);
		} catch (Exception e) {
			sql = "CREATE TABLE Message(mid BIGINT NOT NULL, uid BIGINT NOT NULL, msg BLOB NOT NULL, PRIMARY KEY(mid), INDEX(uid)) CHARACTER SET gbk COLLATE gbk_bin;";
			statement.execute(sql);
		}
	}

	public void addMessage(ID mid, ID uid, Message msg) throws SQLException {
		String psql = "REPLACE INTO Message VALUES(?, ?, ?)";
		PreparedStatement pStatement = connection.prepareStatement(psql);
		pStatement.setLong(1, mid.getValue());
		pStatement.setLong(2, uid.getValue());
		pStatement.setBlob(3, new SerialBlob(Serializer.serialize(msg)));
		pStatement.execute();
	}

	public List<Message> getAllMessages(ID uid) throws SQLException{
		String psql = "SELECT msg FROM Message WHERE uid=?";
		PreparedStatement pStatement = connection.prepareStatement(psql);
		pStatement.setLong(1, uid.getValue());
		List<Message> res = new ArrayList<Message>();
		ResultSet resultSet = pStatement.executeQuery();
		while (resultSet.next()){
			res.add((Message)Serializer.unserialize(resultSet.getBlob(1)));
		}
		return res;
	}

	public void removeMsg(ID uid, Message msg) throws SQLException{
		String psql = "DELETE FROM Message WHERE mid=?";
		PreparedStatement pStatement = connection.prepareStatement(psql);
		pStatement.setLong(1, msg.getID().getValue());
		pStatement.execute();
	}
}