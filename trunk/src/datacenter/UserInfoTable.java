package datacenter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import entity.CustomUserInfo;
import entity.UserInfo;
import entity.Group;
import entity.ID;
import entity.infoField.IdenticalInfoField;
import entity.infoField.IndexedInfoField;
import entity.infoField.InfoField;
import entity.infoField.InfoFieldFactory;
import entity.infoField.InfoFieldName;

public class UserInfoTable {
	private Connection connection;

	public UserInfoTable(Connection connection) throws SQLException {
		this.connection = connection;

		// ���û�б��򽨱�
		Statement statement = (Statement) this.connection.createStatement();

		boolean userInfoTableExist = true;

		// !!!���UserInfo�иĶ�����ô�����Ҫ�ؽ�
		UserInfo emptyInfo = new UserInfo();
		Iterator<String> fieldNameIter = emptyInfo.getKeySet().iterator();

		// �ж�UserInfo��Ϣ���Ƿ����
		String sql = "DESCRIBE UserInfo";
		try {
			statement.executeQuery(sql);
		} catch (SQLException e) {
			userInfoTableExist = false;
		}

		if (userInfoTableExist == false) {
			// ����UserInfo��Ϣ��
			sql = "CREATE TABLE UserInfo(uid bigint not null";
			while (fieldNameIter.hasNext()) {
				String name = fieldNameIter.next();
				sql += ("," + name + " varchar("
						+ emptyInfo.getInfoField(InfoFieldName.valueOf(name)).getMaxLength() + ") NOT NULL");
			}

			/*sql += ", UNIQUE(";
			for (String name : emptyInfo.getKeySet())
				if (emptyInfo.getInfoField(InfoFieldName.valueOf(name)) instanceof IdenticalInfoField) {
					if (sql.charAt(sql.length() - 1) != '(')
						sql += ", ";
					sql += name;
				}
			sql += ")";*/
			//������UNIQUE�ģ���Ϊ�кܶ�յ��ֶλ���ڣ����ǲ���UNIQUE�ġ�����Ƿ�UNIQUEӦ�����Լ��ĳ������жϡ�
			//�������˿��Ա���UNIQUE����Ϊ����������Ҫ��һ��UNIQUE�Ķ����ǿա�

			sql += ", INDEX(";
			for (String name : emptyInfo.getKeySet())
				if (emptyInfo.getInfoField(InfoFieldName.valueOf(name)) instanceof IndexedInfoField) {
					if (sql.charAt(sql.length() - 1) != '(')
						sql += ", ";
					sql += name;
				}
			sql += ")";

			sql += ", PRIMARY KEY(uid)) CHARACTER SET gbk COLLATE gbk_bin TYPE InnoDB;";
			statement.executeUpdate(sql);
		}
	}

	synchronized public List<UserInfo> getAllUserInfo() throws SQLException {
		String psql = "SELECT * FROM UserInfo";
		PreparedStatement pStatement = connection.prepareStatement(psql);

		ResultSet rows = pStatement.executeQuery();
		List<UserInfo> res = new ArrayList<UserInfo>();
		UserInfo emptyInfo = new UserInfo();
		while (rows.next()) {
			UserInfo now = new UserInfo();
			now.getBaseInfo().setID(new ID(rows.getLong("uid")));
			Iterator<String> fieldNameIter = emptyInfo.getKeySet().iterator();
			while (fieldNameIter.hasNext()){
				String name = fieldNameIter.next();
				now.setInfoField(InfoFieldName.valueOf(name), InfoFieldFactory.getFactory().makeInfoField(name, rows.getString(name)));
			}
			res.add(now);
		}
		return res;
	}

	synchronized public void removeUserInfo(ID uid) throws SQLException{
		String psql = "DELETE FROM UserInfo WHERE uid=?";
		PreparedStatement pStatement = connection.prepareStatement(psql);
		pStatement.setLong(1, uid.getValue());
		pStatement.execute();
	}
	
	synchronized public void setUserInfo(ID uid, UserInfo b) throws SQLException {
		boolean hasUser = false;
		String tpsql = "SELECT COUNT(*) FROM UserInfo WHERE uid=?";
		PreparedStatement tpStatement = connection.prepareStatement(tpsql);
		tpStatement.setLong(1, uid.getValue());
		ResultSet trows = tpStatement.executeQuery();
		hasUser = (trows.next() && trows.getInt(1) > 0);
		if (!hasUser && b.getCustomInfo() == null)
			b.setCustomInfo(new CustomUserInfo());
		
		if (b.getCustomInfo() != null){
			String psql = "REPLACE INTO UserInfo VALUES(?";
			UserInfo emptyInfo = new UserInfo();
			Iterator<String> fieldNameIter = emptyInfo.getKeySet().iterator();
			while (fieldNameIter.hasNext()){
				psql += ",?";
				fieldNameIter.next();
			}
			psql += ")";
			
			PreparedStatement pStatement = connection.prepareStatement(psql);
			pStatement.setLong(1, uid.getValue());
			fieldNameIter = emptyInfo.getKeySet().iterator();
			int cnt = 1;
			while (fieldNameIter.hasNext()){
				String name = fieldNameIter.next();
				pStatement.setString(++cnt, b.getInfoField(InfoFieldName.valueOf(name)).getStringValue());
			}
			
			pStatement.execute();
		}
		else{
			String psql = "UPDATE UserInfo SET uid=?"; 
			Iterator<String> fieldNameIter = b.getKeySet().iterator();
			while (fieldNameIter.hasNext()){
				psql += ","+fieldNameIter.next()+"=?";
			}
			psql += " WHERE uid=?";
			
			System.out.println(psql);//TODO TEST
			
			PreparedStatement pStatement = connection.prepareStatement(psql);
			pStatement.setLong(1, uid.getValue());
			fieldNameIter = b.getKeySet().iterator();
			int cnt = 1;
			while (fieldNameIter.hasNext()){
				String name = fieldNameIter.next();
				pStatement.setString(++cnt, b.getInfoField(InfoFieldName.valueOf(name)).getStringValue());
			}
			pStatement.setLong(++cnt, uid.getValue());

			pStatement.execute();
		}
	}

	synchronized public List<UserInfo> searchUser(UserInfo info) throws SQLException{
		String psql = "SELECT * FROM UserInfo WHERE ";
		for(String name: info.getKeySet()){
			InfoField field = info.getInfoField(InfoFieldName.valueOf(name));
			if (field.isEmpty())
				continue;
			if (psql.charAt(psql.length()-1) != ' ')
				psql += " AND ";
			psql += name+" LIKE ?";
		}
		PreparedStatement pStatement = connection.prepareStatement(psql);
		int cnt = 0;
		for(String name: info.getKeySet()){
			InfoField field = info.getInfoField(InfoFieldName.valueOf(name));
			if (field.isEmpty())
				continue;
			pStatement.setString(++cnt, "%"+field.getStringValue()+"%");
		}
		
		ResultSet rows = pStatement.executeQuery();
		List<UserInfo> res = new ArrayList<UserInfo>();
		while (rows.next()){
			UserInfo b = new UserInfo();
			b.getBaseInfo().setID(new ID(rows.getLong("uid")));
			// Ϊ�˱���KeySet��һ���ԣ����Ի�����emptyGroup����ȡKeySet
			UserInfo emptyInfo = new UserInfo();
			Iterator<String> fieldNameIter = emptyInfo.getKeySet().iterator();
			while (fieldNameIter.hasNext()) {
				String name = fieldNameIter.next();
				b.setInfoField(InfoFieldName.valueOf(name), InfoFieldFactory.getFactory()
						.makeInfoField(name, rows.getString(name)));
			}
			res.add(b);
		}
		return res;
	}
}
