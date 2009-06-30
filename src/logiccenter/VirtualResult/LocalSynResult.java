package logiccenter.VirtualResult;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import algorithm.IdenticalUserMatcher;
import algorithm.SimilarUserMatcher;
import algorithm.utility.DisjointSet;

import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;

import datacenter.google.GoogleContactOperator;

import logiccenter.LogicCenter;

import entity.BoolInfo;
import entity.ErrorType;
import entity.GoogleSynSource;
import entity.ID;
import entity.LocalSynSource;
import entity.MyRemoteException;
import entity.UserInfo;
import entity.infoField.BaseInfoFieldName;
import entity.infoField.CustomInfoFieldName;
import entity.infoField.Extension;
import entity.infoField.InfoFieldName;

public class LocalSynResult extends OneTimeVirtualResult {
	LocalSynSource source;
	private double progress;

	public LocalSynResult(LocalSynSource source, LogicCenter center) {
		super(center);
		this.source = source;
		if (!(source instanceof GoogleSynSource))
			setError(ErrorType.SOURCE_NOT_SUPPORTED);
		center.getExecutor().submit(task);
	}

	@Override
	protected BoolInfo getResult() throws RemoteException, MyRemoteException {
		// 建议先远程同步
		boolean doRemoteSyn = center.getUI().yesOrNo(
				"建议您在本地同步前先进行远程同步，保证数据的有效性。请问要进行远程同步吗？");
		if (doRemoteSyn) {
			intermediateInfo = "正在远程同步...";
			progress = 0;
			setChanged();
			notifyObservers();
			RemoteSynResult remoteSynRes = center.remoteSynchronize();
			if (remoteSynRes.getState() == VirtualState.ERRORED) {
				return new BoolInfo("远程同步出错："
						+ remoteSynRes.getError().toString());
			}
			try {
				remoteSynRes.waitForComplete();
			} catch (InterruptedException e) {
				e.printStackTrace();
				return new BoolInfo("远程同步出错：任务被中断");
			} catch (ExecutionException e) {
				e.printStackTrace();
				return new BoolInfo("远程同步出错：" + e.toString());
			}
			intermediateInfo = "远程同步完毕";
			setChanged();
			notifyObservers();
		}

		// 开始和同步源同步
		intermediateInfo = String.format("正在和同步源\"%s\"同步...", source.getName());
		progress = 0;
		setChanged();
		notifyObservers();
		try {
			List<UserInfo> tUsers = center.getDataCenter().getAllUserInfo(
					source);// 同步源的联系人
			intermediateInfo = String.format("\t已经从同步源\"%s\"获得联系人信息，共读取到%d个联系人...", source.getName(), tUsers.size());
			progress = 0;
			setChanged();
			notifyObservers();

			List<UserInfo> myUsers = center.getAllContactsBox().getContacts();// 系统的联系人
			List<UserInfo> allUsers = new ArrayList<UserInfo>();// 所有的联系人
			allUsers.addAll(tUsers);
			allUsers.addAll(myUsers);
			DisjointSet<UserInfo> dsjSet = new DisjointSet<UserInfo>();// 联系人并查集
			dsjSet.addElements(tUsers);
			dsjSet.addElements(myUsers);
			IdenticalUserMatcher identicalMatcher = new IdenticalUserMatcher();// IdenticalInfoField为准的Matcher
			SimilarUserMatcher similarMatcher = new SimilarUserMatcher();// 模糊匹配

			intermediateInfo = String.format("\t正在识别联系人...", source.getName());
			progress = 0;
			setChanged();
			notifyObservers();
			
			int totalCnt = 2*allUsers.size()+2*allUsers.size()*(allUsers.size()-1)/2;//用来计算进度
			int progressCnt = 0, step = Math.max(totalCnt/100, 1);//当前处理计数，多少步刷新一下progress
			
			// 合并无同步关系的联系人
			//		先看绝对有把握的（IdenticalInfoField一致）
			for (int i = 0; i < allUsers.size(); i++)
				for (int j = 0; j < i; j++) {
					//刷progress
					progressCnt++;
					if (progressCnt%step == 0){
						progress = (double)progressCnt/totalCnt;
						setChanged();
						notifyObservers(progress);
					}

					UserInfo u = allUsers.get(i), v = allUsers.get(j);
					if (u.hasRelation() || v.hasRelation())//只管无同步关系的联系人
						continue;
					if (dsjSet.findSet(u) == dsjSet.findSet(v))
						continue;
					if (identicalMatcher.match(u, v))
						dsjSet.unionSet(u, v);					
				}
			//		再看模糊的
			for (int i = 0; i < allUsers.size(); i++)
				for (int j = 0; j < i; j++) {
					//刷progress
					progressCnt++;
					if (progressCnt%step == 0){
						progress = (double)progressCnt/totalCnt;
						setChanged();
						notifyObservers(progress);
					}
					
					UserInfo u = allUsers.get(i), v = allUsers.get(j);
					if (u.hasRelation() || v.hasRelation())//只管无同步关系的联系人
						continue;
					if (dsjSet.findSet(u) == dsjSet.findSet(v))
						continue;
					if (similarMatcher.match(u, v)) {
						// 很类似的联系人，问用户到底是不是同一个联系人
						if (center.getUI().yesOrNo(
								String.format(
										"联系人\"%s\"和联系人\"%s\"好像是同一个联系人，请问是吗？", 
										u.getStringValue(), 
										v.getStringValue())))
							dsjSet.unionSet(u, v);
					}
				}

			// 加上有同步关系的联系人
			Map<UserInfo, UserInfo> synUserMap = new HashMap<UserInfo, UserInfo>();//从集和代表元映射到集和中的有同步关系的联系人
			//		先看绝对有把握的（IdenticalInfoField一致）
			for(int i=0; i<allUsers.size(); i++){
				//刷progress
				progressCnt++;
				if (progressCnt%step == 0){
					progress = (double)progressCnt/totalCnt;
					setChanged();
					notifyObservers(progress);
				}

				UserInfo realU = allUsers.get(i);
				UserInfo u = dsjSet.findSet(realU);
				if (synUserMap.get(u) == null){//当前没有同步关系映射
					for(int j=0; j<myUsers.size(); j++){
						UserInfo v = myUsers.get(j);
						if (!v.hasRelation())//只找有同步关系的联系人
							continue;
						if (identicalMatcher.match(realU, v)){
							dsjSet.unionSet(u, v);
							synUserMap.put(dsjSet.findSet(u), v);
							break;
						}
					}
				}
			}
			//		再看模糊的
			for(int i=0; i<allUsers.size(); i++){
				//刷progress
				progressCnt++;
				if (progressCnt%step == 0){
					progress = (double)progressCnt/totalCnt;
					setChanged();
					notifyObservers(progress);
				}
				
				UserInfo realU = allUsers.get(i);
				UserInfo u = dsjSet.findSet(realU);
				if (synUserMap.get(u) == null){//当前没有同步关系映射
					for(int j=0; j<myUsers.size(); j++){
						UserInfo v = myUsers.get(j);
						if (!v.hasRelation())//只找有同步关系的联系人
							continue;
						if (similarMatcher.match(realU, v))
							// 很类似的联系人，问用户到底是不是同一个联系人
							if (center.getUI().yesOrNo(
									String.format(
											"联系人:\"%s\"和联系人:\"%s\"好像是同一个联系人，请问是吗？", 
											realU.getStringValue(), 
											v.getStringValue()))){
								dsjSet.unionSet(u, v);
								synUserMap.put(dsjSet.findSet(u), v);
								break;
							}
					}
				}
			}			
			int diffCnt = 0;
			for(UserInfo iu: allUsers)
				if (dsjSet.findSet(iu) == iu)
					diffCnt++; 
			
			progress = 0;
			intermediateInfo = String.format("\t共识别出%d个不同的联系人，正在合并联系人信息...", diffCnt);
			setChanged();
			notifyObservers();
			
			// 询问如何合并
			Map<UserInfo, UserInfo> sourceUserMap = new HashMap<UserInfo, UserInfo>();//从集和映射到里面某一个同步源联系人
			Map<UserInfo, UserInfo> myUserMap = new HashMap<UserInfo, UserInfo>();//从集和映射到里面某一个非同步源联系人
			Map<UserInfo, UserInfo> mUserMap = new HashMap<UserInfo, UserInfo>();//最终合并到的联系人映射
			
			totalCnt = allUsers.size();
			progressCnt = 0;
			step = Math.max(totalCnt/100, 1);
			
			//	首先获得映射
			for(UserInfo user: tUsers)
				sourceUserMap.put(dsjSet.findSet(user), user);
			for(UserInfo user: myUsers)
				myUserMap.put(dsjSet.findSet(user), user);
			for(UserInfo user: allUsers){
				//刷progress
				progressCnt++;
				if (progressCnt%step == 0){
					progress = (double)progressCnt/totalCnt;
					setChanged();
					notifyObservers(progress);
				}				
				
				UserInfo mUser = synUserMap.get(dsjSet.findSet(user));
				if (mUser == null)
					mUser = dsjSet.findSet(user);
				mUserMap.put(dsjSet.findSet(user), mUser);
				if (mUser == user)
					continue;
				if (!center.getUI().mergeUserInfo(mUser, user)){
					return new BoolInfo(ErrorType.CANCELED);
				}
			}
			intermediateInfo = String.format("\t合并完毕");
			setChanged();
			notifyObservers();
			
			progress = 0;
			intermediateInfo = String.format("\t正在修改数据...");
			setChanged();
			notifyObservers();
			Set<UserInfo> toBeRemoved = new HashSet<UserInfo>();//要删除的联系人集和代表
			Set<UserInfo> toBeAdded = new HashSet<UserInfo>();//要加入的联系人集和代表

			// 询问只有系统有的联系人
			boolean allYes = center.getUI().yesOrNo("把所有不存在于系统或同步源的联系人加入？");
			
			for(UserInfo user: allUsers){
				UserInfo u = dsjSet.findSet(user);
				UserInfo mu = mUserMap.get(u); 
				if (u == user && sourceUserMap.get(u) == null){//只有本系统才有的联系人
					if (allYes || center.getUI().yesOrNo(String.format("用户:\"%s\"不存在于同步源中，选择%s将该联系人加入同步源，选择%s将该联系人从系统中删除", 
							mu.getStringValue(), "Yes/OK", "No/Cancel"))){
						toBeAdded.add(mu);
					}
					else
						toBeRemoved.add(mu);
				}
			}

			// 询问只有同步源有的联系人
			for(UserInfo user: allUsers){
				UserInfo u = dsjSet.findSet(user);
				UserInfo mu = mUserMap.get(u);
				if (u == user && myUserMap.get(u) == null)//只有同步源才有的联系人
					if (allYes || center.getUI().yesOrNo(String.format("用户:\"%s\"不存在于系统中，选择%s将该联系人加入系统，选择%s将该联系人从同步源中删除", 
							mu.getStringValue(), "Yes/OK", "No/Cancel"))){
						toBeAdded.add(mu);
					}
					else
						toBeRemoved.add(mu);
			}

			// 更新系统
			//	添加新联系人
			progress = 0;
			intermediateInfo = String.format("\t\t正在更新系统...");
			setChanged();
			notifyObservers();
			totalCnt = tUsers.size()+myUsers.size();
			progressCnt = 0;
			step = Math.max(totalCnt/100, 1);
			
			for(UserInfo user: tUsers){
				if (toBeAdded.contains(user)){
					user.getBaseInfo().setID(ID.getLocalRandID());
					center.getDataCenter().setUserInfo(user);
				}
				//刷progress
				progressCnt++;
				if (progressCnt%step == 0){
					progress = (double)progressCnt/totalCnt;
					setChanged();
					notifyObservers(progress);
				}				
			}
			//	删除联系人，更新联系人信息
			for(UserInfo user: myUsers){
				if (toBeRemoved.contains(user) || myUserMap.get(dsjSet.findSet(user)) != user)
					center.getDataCenter().removeUserInfo(user.getBaseInfo().getID());
				else{
					UserInfo mUser = mUserMap.get(dsjSet.findSet(user));
					for(InfoFieldName key: InfoFieldName.values())
						if (user.getInfoField(key) != null && user.getInfoField(key).editable())
							user.setInfoField(mUser.getInfoField(key));
					center.getDataCenter().setUserInfo(user);
				}
				//刷progress
				progressCnt++;
				if (progressCnt%step == 0){
					progress = (double)progressCnt/totalCnt;
					setChanged();
					notifyObservers(progress);
				}				
			}
			center.getAllContactsBox().updateAll();

			// 更新同步源
			//	删除联系人，更新联系人信息
			progress = 0;
			intermediateInfo = String.format("\t\t正在更新同步源...");
			setChanged();
			notifyObservers();
			for(UserInfo user: tUsers){
				Extension ext = (Extension) user.getInfoField(InfoFieldName.Extension);
				if (toBeRemoved.contains(user) || sourceUserMap.get(dsjSet.findSet(user)) != user)
					ext.setValue("Operation", GoogleContactOperator.OperationValue.Remove.name());
				else{
					ext.setValue("Operation", GoogleContactOperator.OperationValue.Update.name());
					UserInfo mUser = mUserMap.get(dsjSet.findSet(user));
					for(InfoFieldName key: InfoFieldName.values())
						if (user.getInfoField(key) != null && user.getInfoField(key).editable())
							user.setInfoField(mUser.getInfoField(key));
				}
			}
			//	添加新联系人
			for(UserInfo user: myUsers){
				if (toBeAdded.contains(user)){
					tUsers.add(user);
					Extension ext = (Extension) user.getInfoField(InfoFieldName.Extension);
					ext.setValue("Operation", GoogleContactOperator.OperationValue.Add.name());
				}
			}
			//	执行操作
			totalCnt = tUsers.size();
			progressCnt = 0;
			step = Math.max(totalCnt/100, 1);
			for(int i=0; i<tUsers.size(); i++){//为了监视进度，并且由于网络传输好慢，所以我们就一个一个来吧
				center.getDataCenter().updateSource(tUsers.subList(i, i+1), source);
				//刷progress
				progressCnt++;
				if (progressCnt%step == 0){
					progress = (double)progressCnt/totalCnt;
					setChanged();
					notifyObservers(progress);
				}				
			}
			
			//TODO 去服务器上搜索用户
		} catch (AuthenticationException e) {
			e.printStackTrace();
			return new BoolInfo("Google用户验证失败");
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return new BoolInfo(e.toString());
		} catch (ServiceException e) {
			e.printStackTrace();
			return new BoolInfo(e.toString());
		} catch (IOException e) {
			e.printStackTrace();
			return new BoolInfo(e.toString());
		} catch (SQLException e) {
			e.printStackTrace();
			return new BoolInfo(e.toString());
		}

		intermediateInfo = "远程同步完成";
		setChanged();
		notifyObservers();

		return new BoolInfo();
	}

	/**
	 * 0到1的实数，代表进度
	 * @return
	 */
	public double getProgress(){
		return progress;
	}
}
