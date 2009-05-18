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
import com.mysql.jdbc.Statement;

import entity.*;


//!!!!!!Singleton
public class DataCenterImp implements DataCenter {
	//需要写入数据库的各种数据的缓存
	private List<UserInfo> userInfoWriteBuffer;
	private List<Permission> permissionWriteBuffer;
	private List<Group> groupWriteBuffer;
	private List<ID> syncIDWriteBuffer;
	private List<ID> perIDWriteBuffer;
	private Map<Group,ID> addToGroupBuffer;
	//需要从数据库删除的数据的缓存
	private List<Group> groupDeleteBuffer;
	private List<ID> syncIDDeleteBuffer;
	private List<ID> perIDDeleteBuffer;
	private Map<Group,ID> deleteFromGroupBuffer;
	
	//数据库用户名
	private String userName="root";
	//密码
	private String userPasswd="81999";
	//数据库名
	private String dbName="test";
	//联结字符串
	private String url="jdbc:mysql://localhost/"+dbName+"?user="+userName+"&password="+userPasswd;
	
	private static final DataCenterImp instance=null;
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
		
		//建表
		try{
	 		Class.forName("com.mysql.jdbc.Driver").newInstance();
	 		Connection connection=(Connection) DriverManager.getConnection(url);
	 	    Statement statement = (Statement) connection.createStatement();
	 	    
	 	    boolean userInfoTableExist=true;
	 	    boolean groupTableExist=true;
	 	    
			Iterator<String> fieldNameIter=(new BaseUserInfo()).getKeySet().iterator();
		
		//判断UserInfo信息表是否存在
			String sql="SELECT * FROM UserInfo";
			try{	
				statement.executeQuery(sql);
			}catch(SQLException e){
				userInfoTableExist=false;
			}
	 	    
			if(userInfoTableExist==false){
		//建立UserInfo信息表
				sql="CREATE TABLE UserInfo(UserID int not null";
				while(fieldNameIter.hasNext()){
					sql+=(","+fieldNameIter.next()+" varchar(50)");
				}
				fieldNameIter=(new CustomUserInfo()).getKeySet().iterator();
				while(fieldNameIter.hasNext()){
					sql+=(","+fieldNameIter.next()+" varchar(50)");
				}
				sql+=",WhetherSyc int not null,WhetherPer int not null)";
				statement.executeUpdate(sql);	
			}
		
		//判断Group信息表是否已存在
			sql="SELECT * FROM GroupInfo";
			try{	
				statement.executeQuery(sql);
			}catch(SQLException e){
				groupTableExist=false;
			}
	 	    
	 	//建立Group信息表
			if(groupTableExist==false){
				sql="CREATE TABLE GroupInfo(GroupID int not null,UserID int not null";
				fieldNameIter=(new Group()).getKeySet().iterator();
				while(fieldNameIter.hasNext()){
					sql+=(","+fieldNameIter.next()+" varchar(50)");
				}
				sql+=")";	 	    
				statement.executeUpdate(sql);
				connection.close();
			}
		}catch(Exception ex){
		      System.out.println(ex);
		      ex.printStackTrace();
		      System.exit(0);
		}
		
	}
	public static synchronized DataCenterImp Instance(){
		return instance;
	}
	
	
	

	@Override
	public ReturnType addPerRelationship(ID uid) {
		// TODO Auto-generated method stub
		
		return null;
	}

	@Override
	public ReturnType addSynRelationship(ID uid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReturnType addToGroup(Group g, ID uid) {
		// TODO Auto-generated method stub
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
		return null;
	}

	@Override
	public ReturnType importFile(String fileName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReturnType removeFromGroup(Group g, ID uid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReturnType removeGroup(Group g) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReturnType removePerRelationship(ID uid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReturnType removeSynRelationship(ID uid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReturnType setGroup(Group g) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReturnType setPermission(ID uid, Permission p) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReturnType setUserInfo(UserInfo b) {
		// TODO Auto-generated method stub
		return null;
	}

}
