package datacenter;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import entity.*;


//!!!!!!Singleton
public class DataCenterImp implements DataCenter {
	//��Ҫд�����ݿ�ĸ������ݵĻ���
	private List<UserInfo> userInfoWriteBuffer;
	private List<Permission> permissionWriteBuffer;
	private List<Group> groupWriteBuffer;
	private List<ID> syncIDWriteBuffer;
	private List<ID> perIDWriteBuffer;
	private Map<Group,ID> addToGroupBuffer;
	//��Ҫ�����ݿ�ɾ�������ݵĻ���
	private List<Group> groupDeleteBuffer;
	private List<ID> syncIDDeleteBuffer;
	private List<ID> perIDDeleteBuffer;
	private Map<Group,ID> deleteFromGroupBuffer;
	
	//���ݿ��û���
	private String userName="root";
	//����
	private String userPasswd="81999";
	//���ݿ���
	private String dbName="test";
	//�����ַ���
	private String url="jdbc:mysql://localhost/"+dbName+"?user="+userName+"&password="+userPasswd;
	
	private static final DataCenterImp instance=null;
	
	/**
	 * ���캯�������г�ʼ�����Ա���������������򽨱�Ȳ���
	 */
	private DataCenterImp(){
		userInfoWriteBuffer=new ArrayList<UserInfo>();
		permissionWriteBuffer=new ArrayList<Permission>();
		groupWriteBuffer=new ArrayList<Group>();
		syncIDWriteBuffer=new ArrayList<ID>();
		perIDWriteBuffer=new ArrayList<ID>();
		addToGroupBuffer=new HashMap<Group,ID>();
		groupDeleteBuffer=new ArrayList<Group>();
		syncIDDeleteBuffer=new ArrayList<ID>();
		perIDDeleteBuffer=new ArrayList<ID>();
		deleteFromGroupBuffer=new HashMap<Group,ID>();
		
		//����
		try{
	 		Class.forName("com.mysql.jdbc.Driver").newInstance();
	 		Connection connection=(Connection) DriverManager.getConnection(url);
	 	    Statement statement = (Statement) connection.createStatement();
	 	    
	 	    boolean userInfoTableExist=true;
	 	    boolean groupTableExist=true;
	 	    
///////////////����������������������������new BaseUserInfo()��ʵ���йأ�������Ҫ�޸ģ�
			Iterator<String> fieldNameIter=(new BaseUserInfo()).getKeySet().iterator();
		
		//�ж�UserInfo��Ϣ���Ƿ����
			String sql="SELECT * FROM UserInfo";
			try{	
				statement.executeQuery(sql);
			}catch(SQLException e){
				userInfoTableExist=false;
			}
	 	    
			if(userInfoTableExist==false){
		//����UserInfo��Ϣ��
				sql="CREATE TABLE UserInfo(UserID int not null";
				while(fieldNameIter.hasNext()){
					sql+=(","+fieldNameIter.next()+" varchar(50)");
				}
				
				///////////////����������������������������new CustomUserInfo()��ʵ���йأ�������Ҫ�޸ģ�
				fieldNameIter=(new CustomUserInfo()).getKeySet().iterator();
				while(fieldNameIter.hasNext()){
					sql+=(","+fieldNameIter.next()+" varchar(50)");
				}
				sql+=",WhetherSync int not null,WhetherPer int not null)";
				statement.executeUpdate(sql);	
			}
		
		//�ж�Group��Ϣ���Ƿ��Ѵ���
			sql="SELECT * FROM GroupInfo";
			try{	
				statement.executeQuery(sql);
			}catch(SQLException e){
				groupTableExist=false;
			}
	 	    
	 	//����Group��Ϣ��
			if(groupTableExist==false){
				sql="CREATE TABLE GroupInfo(GroupID int not null";
	///////////////����������������������������new Group()��ʵ���йأ�������Ҫ�޸ģ�
				fieldNameIter=(new Group()).getKeySet().iterator();
				while(fieldNameIter.hasNext()){
					sql+=(","+fieldNameIter.next()+" varchar(50)");
				}
				sql+=")";	 	    
				statement.executeUpdate(sql);
				connection.close();
			}
			
			
		//�ж�Group��Ա���Ƿ��Ѵ���
			sql="SELECT * FROM GroupMember";
			try{	
				statement.executeQuery(sql);
			}catch(SQLException e){
				groupTableExist=false;
			}
	 	    
	 	//����Group��Ա��
			if(groupTableExist==false){
				sql="CREATE TABLE GroupMember(GroupID int not null,UserID int not null)";	 	    
				statement.executeUpdate(sql);
				connection.close();
			}
		}catch(Exception ex){
		      System.out.println(ex);
		      ex.printStackTrace();
		      System.exit(0);
		}
		
	}
	
	/**
	 * ���ش����Ψһʵ��
	 * @return
	 */
	public static synchronized DataCenterImp Instance(){
		return instance;
	}
	

	@Override
	public ReturnType addPerRelationship(ID uid) {
		perIDWriteBuffer.add(uid);
		try{
			if(perIDWriteBuffer.size()==1000){
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				Connection connection=(Connection) DriverManager.getConnection(url);
				connection.setAutoCommit(false);   
				String sql="UPDATE UserInfo WhetherPer:=? where UserID=?";
				PreparedStatement pstatement=(PreparedStatement) connection.prepareStatement(sql);
				for(int i=0;i<perIDWriteBuffer.size();i++){
					pstatement.setInt(1, 1);
					pstatement.setInt(2, perIDWriteBuffer.get(i).getValue());
					pstatement.addBatch();
				}
				pstatement.executeBatch();
		 		connection.commit();
		 		connection.close();
		 		pstatement.clearBatch();
		 		perIDWriteBuffer.clear();
			}
		}catch(Exception ex){
			System.out.println(ex);
			ex.printStackTrace();
			System.exit(0);
		}
		return null;
	}

	@Override
	public ReturnType addSynRelationship(ID uid) {
		syncIDWriteBuffer.add(uid);
		try{
			if(syncIDWriteBuffer.size()==1000){
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				Connection connection=(Connection) DriverManager.getConnection(url);
				connection.setAutoCommit(false);   
				String sql="UPDATE UserInfo WhetherSync:=? where UserID=?";
				PreparedStatement pstatement=(PreparedStatement) connection.prepareStatement(sql);
				for(int i=0;i<syncIDWriteBuffer.size();i++){
					pstatement.setInt(1, 1);
					pstatement.setInt(2, syncIDWriteBuffer.get(i).getValue());
					pstatement.addBatch();
				}
				pstatement.executeBatch();
		 		connection.commit();
		 		connection.close();
		 		pstatement.clearBatch();
		 		syncIDWriteBuffer.clear();
			}
		}catch(Exception ex){
			System.out.println(ex);
			ex.printStackTrace();
			System.exit(0);
		}
		return null;
	}

	@Override
	public ReturnType addToGroup(Group g, ID uid) {
		addToGroupBuffer.put(g, uid);
		if(addToGroupBuffer.size()==1000){
			try{
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				Connection connection=(Connection) DriverManager.getConnection(url);
				connection.setAutoCommit(false);   
				String sql="INSERT INTO GroupMember (GroupID,UserID) VALUES (?,?)";
				PreparedStatement pstatement=(PreparedStatement) connection.prepareStatement(sql);
				Iterator<Group> groupIter=addToGroupBuffer.keySet().iterator();
				for(int i=0;i<addToGroupBuffer.size();i++){
					Group temp=groupIter.next();
					pstatement.setInt(1, temp.getID().getValue());
					pstatement.setInt(2, addToGroupBuffer.get(temp).getValue());
					pstatement.addBatch();
				}
				pstatement.executeBatch();
				connection.commit();
				connection.close();
				pstatement.clearBatch();
				addToGroupBuffer.clear();
			}catch(Exception ex){
				System.out.println(ex);
				ex.printStackTrace();
				System.exit(0);
			}
		}
		return null;
	}

	@Override
	public ReturnType exportFile(String fileName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserInfo> getAllUserInfo(LocalSynSource source) {
		// TODO Auto-generated method stub
		List<UserInfo> result=new ArrayList<UserInfo>();
		UserInfo userInfo=new UserInfo();
		BaseUserInfo baseUserInfo=new BaseUserInfo();
		CustomUserInfo customUserInfo=new CustomUserInfo();
		InfoFieldFactory infoFieldFactory=new InfoFieldFactory();
		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection connection=(Connection) DriverManager.getConnection(url);
			connection.setAutoCommit(false);
			Statement statement=(Statement)connection.createStatement();
			String sql="SELECT UserID FROM UserInfo";
			ResultSet allUserID=statement.executeQuery(sql);
			//һ���������һ��BaseUserInfo�ֶε������û���ֵ
			ArrayList<ResultSet> baseFieldList=new ArrayList<ResultSet>();
			ArrayList<ResultSet> customFieldList= new ArrayList<ResultSet>();
			sql="SELECT ? FROM UserInfo";
			PreparedStatement pstatement=(PreparedStatement)connection.prepareStatement(sql);
			Iterator<String> iter=baseUserInfo.getKeySet().iterator();
			while(iter.hasNext()){
				pstatement.setString(1, iter.next());
				baseFieldList.add(pstatement.executeQuery());
			}
			iter=customUserInfo.getKeySet().iterator();
			while(iter.hasNext()){
				pstatement.setString(1, iter.next());
				customFieldList.add(pstatement.executeQuery());
			}
		}catch(Exception ex){
			System.out.println(ex);
			ex.printStackTrace();
			System.exit(0);
		}
		return null;
	}

	@Override
	public ReturnType importFile(String fileName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReturnType removeFromGroup(Group g, ID uid) {
		deleteFromGroupBuffer.put(g, uid);
		if(deleteFromGroupBuffer.size()==1000){
			try{
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				Connection connection=(Connection) DriverManager.getConnection(url);
				connection.setAutoCommit(false);   
				String sql="DELETE FROM GroupMember WHERE GroupID=? AND UserID=?";
				PreparedStatement pstatement=(PreparedStatement) connection.prepareStatement(sql);
				Iterator<Group> groupIter=deleteFromGroupBuffer.keySet().iterator();
				for(int i=0;i<deleteFromGroupBuffer.size();i++){
					Group temp=groupIter.next();
					pstatement.setInt(1, temp.getID().getValue());
					pstatement.setInt(2, deleteFromGroupBuffer.get(temp).getValue());
					pstatement.addBatch();
				}
				pstatement.executeBatch();
				connection.commit();
				connection.close();
				pstatement.clearBatch();
				deleteFromGroupBuffer.clear();
			}catch(Exception ex){
				System.out.println(ex);
				ex.printStackTrace();
				System.exit(0);
			}
		}
		return null;
	}

	@Override
	public ReturnType removeGroup(Group g) {
		groupDeleteBuffer.add(g);
		try{
			if(groupDeleteBuffer.size()==1000){
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				Connection connection=(Connection) DriverManager.getConnection(url);
				connection.setAutoCommit(false);   
				String sql1="DELETE FROM GroupInfo WHERE GroupID=?";
				String sql2="DELETE FROM GroupMember WHERE GroupID=?";
				PreparedStatement pstatement1=(PreparedStatement) connection.prepareStatement(sql1);
				PreparedStatement pstatement2=(PreparedStatement) connection.prepareStatement(sql2);
				for(int i=0;i<groupDeleteBuffer.size();i++){
					pstatement1.setInt(1, groupDeleteBuffer.get(i).getID().getValue());
					pstatement2.setInt(1, groupDeleteBuffer.get(i).getID().getValue());
					pstatement1.addBatch();
					pstatement2.addBatch();
				}
				pstatement1.executeBatch();
				pstatement2.executeBatch();
		 		connection.commit();
		 		connection.close();
		 		pstatement1.clearBatch();
		 		pstatement2.clearBatch();
		 		groupDeleteBuffer.clear();
			}
		}catch(Exception ex){
			System.out.println(ex);
			ex.printStackTrace();
			System.exit(0);
		}
		return null;
	}

	@Override
	public ReturnType removePerRelationship(ID uid) {
		perIDDeleteBuffer.add(uid);
		try{
			if(perIDDeleteBuffer.size()==1000){
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				Connection connection=(Connection) DriverManager.getConnection(url);
				connection.setAutoCommit(false);   
				String sql="UPDATE UserInfo WhetherPer:=? where UserID=?";
				PreparedStatement pstatement=(PreparedStatement) connection.prepareStatement(sql);
				for(int i=0;i<perIDDeleteBuffer.size();i++){
					pstatement.setInt(1, 0);
					pstatement.setInt(2, perIDDeleteBuffer.get(i).getValue());
					pstatement.addBatch();
				}
				pstatement.executeBatch();
		 		connection.commit();
		 		connection.close();
		 		pstatement.clearBatch();
		 		perIDDeleteBuffer.clear();
			}
		}catch(Exception ex){
			System.out.println(ex);
			ex.printStackTrace();
			System.exit(0);
		}
		return null;
	}

	@Override
	public ReturnType removeSynRelationship(ID uid) {
		syncIDDeleteBuffer.add(uid);
		try{
			if(syncIDDeleteBuffer.size()==1000){
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				Connection connection=(Connection) DriverManager.getConnection(url);
				connection.setAutoCommit(false);   
				String sql="UPDATE UserInfo WhetherSync:=? where UserID=?";
				PreparedStatement pstatement=(PreparedStatement) connection.prepareStatement(sql);
				for(int i=0;i<syncIDDeleteBuffer.size();i++){
					pstatement.setInt(1, 0);
					pstatement.setInt(2, syncIDDeleteBuffer.get(i).getValue());
					pstatement.addBatch();
				}
				pstatement.executeBatch();
		 		connection.commit();
		 		connection.close();
		 		pstatement.clearBatch();
		 		syncIDDeleteBuffer.clear();
			}
		}catch(Exception ex){
			System.out.println(ex);
			ex.printStackTrace();
			System.exit(0);
		}
		return null;
	}

	@Override
	public ReturnType setGroup(Group g) {
		groupWriteBuffer.add(g);
		try{
			if(groupWriteBuffer.size()==1000){
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				Connection connection=(Connection) DriverManager.getConnection(url);
				connection.setAutoCommit(false);   
				
				String sql1="UPDATE GroupInfo set ";
				String sql2="INSERT INTO GroupInfo (GroupID,";
				Iterator<String> fieldNameIter=g.getKeySet().iterator();
				String temp=fieldNameIter.next();
				sql1+=(temp+":=?");
				sql2+=temp;
				int count=0;
				while(fieldNameIter.hasNext()){
					temp=fieldNameIter.next();
					sql1+=(","+temp+":=?");
					sql2+=(","+temp);
					count++;
				}
				sql1+=" WHERE GroupID=?";
				sql2+=") VALUES (?,?";//��һ����GroupID���ӵڶ�����ʼ���ֶ�����
				for(int i=0;i<count;i++)
					sql2+=",?";
				sql2+=")";
				
				PreparedStatement pstatement1=(PreparedStatement) connection.prepareStatement(sql1);
				PreparedStatement pstatement2=(PreparedStatement) connection.prepareStatement(sql2);
				for(int i=0;i<groupWriteBuffer.size();i++){
					boolean groupExist=true;
					try{
						String tempSQL="SELECT count(*) FROM GroupInfo WHERE GroupID="+groupWriteBuffer.get(i).getID().getValue();
						Statement statement = (Statement) connection.createStatement();
						ResultSet rs=statement.executeQuery(tempSQL);
						if(rs.next()){
							if(rs.getInt(1)==0)
								groupExist=false;
						}
					}catch(SQLException e){
						groupExist=false;
					}
					if(groupExist){
						int keyNum=1;
						fieldNameIter=groupWriteBuffer.get(i).getKeySet().iterator();
						while(fieldNameIter.hasNext()){
							pstatement1.setString(keyNum, groupWriteBuffer.get(i).getInfoField(fieldNameIter.next()).getStringValue());	
							keyNum++;
						}
						pstatement1.setInt(keyNum, groupWriteBuffer.get(i).getID().getValue());
						pstatement1.addBatch();
					}else{
						fieldNameIter=groupWriteBuffer.get(i).getKeySet().iterator();
						pstatement1.setInt(1, groupWriteBuffer.get(i).getID().getValue());
						int keyNum=2;
						while(fieldNameIter.hasNext()){
							pstatement2.setString(keyNum, groupWriteBuffer.get(i).getInfoField(fieldNameIter.next()).getStringValue());
							keyNum++;
						}
						pstatement2.addBatch();
					}
				}
				pstatement1.executeBatch();
				pstatement2.executeBatch();
		 		connection.commit();
		 		connection.close();
		 		pstatement1.clearBatch();
		 		pstatement2.clearBatch();
		 		groupWriteBuffer.clear();
			}
		}catch(Exception ex){
			System.out.println(ex);
			ex.printStackTrace();
			System.exit(0);
		}
		return null;
	}

	@Override
	public ReturnType setPermission(ID uid, Permission p) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReturnType setUserInfo(UserInfo b) {
		userInfoWriteBuffer.add(b);
		try{
			if(userInfoWriteBuffer.size()==1000){
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				Connection connection=(Connection) DriverManager.getConnection(url);
				connection.setAutoCommit(false);   
				
				String sql1="UPDATE UserInfo set ";
				String sql2="INSERT INTO UserInfo (UserID,";
				Iterator<String> fieldNameIter=b.getBaseInfo().getKeySet().iterator();
				String temp=fieldNameIter.next();
				sql1+=(temp+":=?");
				sql2+=temp;
				int count=0;
				while(fieldNameIter.hasNext()){
					temp=fieldNameIter.next();
					sql1+=(","+temp+":=?");
					sql2+=(","+temp);
					count++;
				}
				fieldNameIter=b.getCustomInfo().getKeySet().iterator();
				while(fieldNameIter.hasNext()){
					temp=fieldNameIter.next();
					sql1+=(","+temp+":=?");
					sql2+=(","+temp);
					count++;
				}
				sql1+=" WHERE UserID=?";
				sql2+=") VALUES (?,?";//��һ��?����UserID���ӵڶ�����ʼ���ֶ�����
				for(int i=0;i<count;i++)
					sql2+=",?";
				sql2+=")";
				
				PreparedStatement pstatement1=(PreparedStatement) connection.prepareStatement(sql1);
				PreparedStatement pstatement2=(PreparedStatement) connection.prepareStatement(sql2);
				for(int i=0;i<userInfoWriteBuffer.size();i++){
					boolean userExist=true;
					try{
						String tempSQL="SELECT count(*) FROM GroupInfo WHERE GroupID="+userInfoWriteBuffer.get(i).getBaseInfo().getID().getValue();
						Statement statement = (Statement) connection.createStatement();
						ResultSet rs=statement.executeQuery(tempSQL);
						if(rs.next()){
							if(rs.getInt(1)==0)
								userExist=false;
						}
					}catch(SQLException e){
						userExist=false;
					}
					if(userExist){
						int keyNum=1;
						fieldNameIter=userInfoWriteBuffer.get(i).getBaseInfo().getKeySet().iterator();
						while(fieldNameIter.hasNext()){
							pstatement1.setString(keyNum, userInfoWriteBuffer.get(i).getBaseInfo().getInfoField(fieldNameIter.next()).getStringValue());	
							keyNum++;
						}
						fieldNameIter=userInfoWriteBuffer.get(i).getCustomInfo().getKeySet().iterator();
						while(fieldNameIter.hasNext()){
							pstatement1.setString(keyNum, userInfoWriteBuffer.get(i).getCustomInfo().getInfoField(fieldNameIter.next()).getStringValue());	
							keyNum++;
						}
						pstatement1.setInt(keyNum, userInfoWriteBuffer.get(i).getBaseInfo().getID().getValue());
						pstatement1.addBatch();
					}else{
						fieldNameIter=userInfoWriteBuffer.get(i).getBaseInfo().getKeySet().iterator();
						pstatement2.setInt(1, userInfoWriteBuffer.get(i).getBaseInfo().getID().getValue());
						int keyNum=2;
						while(fieldNameIter.hasNext()){
							pstatement2.setString(keyNum, userInfoWriteBuffer.get(i).getBaseInfo().getInfoField(fieldNameIter.next()).getStringValue());
							keyNum++;
						}
						fieldNameIter=userInfoWriteBuffer.get(i).getCustomInfo().getKeySet().iterator();
						while(fieldNameIter.hasNext()){
							pstatement2.setString(keyNum, userInfoWriteBuffer.get(i).getCustomInfo().getInfoField(fieldNameIter.next()).getStringValue());
							keyNum++;
						}
						pstatement2.addBatch();
					}
				}
				pstatement1.executeBatch();
				pstatement2.executeBatch();
		 		connection.commit();
		 		connection.close();
		 		pstatement1.clearBatch();
		 		pstatement2.clearBatch();
		 		userInfoWriteBuffer.clear();
			}
		}catch(Exception ex){
			System.out.println(ex);
			ex.printStackTrace();
			System.exit(0);
		}
		return null;
	}
	//TODO ò��ȱ��RemoveUserInfo����
	//////////////!!!!!!!!!

}
