package ui;
import entity.*;
import java.util.ArrayList;
import entity.ResulType;
public class Gui {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public void pushResult(ResulType type,Object o){
		//
	}

	/**
	 * 对于userList中的每一个用户，都问同一个问题askInfo，将回答的结果返回。
	 * 
	 * @param userList
	 * @return
	 */
	public ArrayList<Boolean> checkUserList(ArrayList<UserInfo> userList, String askInfo){
		ArrayList<Boolean> r = new ArrayList<Boolean>();
		return r;
	}
	
	public boolean yseOrNo(String askInfo){
		return true;
	}
	
	public UserInfo selMatchedUser(UserInfo a, ArrayList<UserInfo> list){
		UserInfo r = new UserInfo();
		return r;
	}
}
