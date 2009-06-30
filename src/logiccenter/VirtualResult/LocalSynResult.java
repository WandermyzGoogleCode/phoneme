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
		// ������Զ��ͬ��
		boolean doRemoteSyn = center.getUI().yesOrNo(
				"�������ڱ���ͬ��ǰ�Ƚ���Զ��ͬ������֤���ݵ���Ч�ԡ�����Ҫ����Զ��ͬ����");
		if (doRemoteSyn) {
			intermediateInfo = "����Զ��ͬ��...";
			progress = 0;
			setChanged();
			notifyObservers();
			RemoteSynResult remoteSynRes = center.remoteSynchronize();
			if (remoteSynRes.getState() == VirtualState.ERRORED) {
				return new BoolInfo("Զ��ͬ������"
						+ remoteSynRes.getError().toString());
			}
			try {
				remoteSynRes.waitForComplete();
			} catch (InterruptedException e) {
				e.printStackTrace();
				return new BoolInfo("Զ��ͬ�����������ж�");
			} catch (ExecutionException e) {
				e.printStackTrace();
				return new BoolInfo("Զ��ͬ������" + e.toString());
			}
			intermediateInfo = "Զ��ͬ�����";
			setChanged();
			notifyObservers();
		}

		// ��ʼ��ͬ��Դͬ��
		intermediateInfo = String.format("���ں�ͬ��Դ\"%s\"ͬ��...", source.getName());
		progress = 0;
		setChanged();
		notifyObservers();
		try {
			List<UserInfo> tUsers = center.getDataCenter().getAllUserInfo(
					source);// ͬ��Դ����ϵ��
			intermediateInfo = String.format("\t�Ѿ���ͬ��Դ\"%s\"�����ϵ����Ϣ������ȡ��%d����ϵ��...", source.getName(), tUsers.size());
			progress = 0;
			setChanged();
			notifyObservers();

			List<UserInfo> myUsers = center.getAllContactsBox().getContacts();// ϵͳ����ϵ��
			List<UserInfo> allUsers = new ArrayList<UserInfo>();// ���е���ϵ��
			allUsers.addAll(tUsers);
			allUsers.addAll(myUsers);
			DisjointSet<UserInfo> dsjSet = new DisjointSet<UserInfo>();// ��ϵ�˲��鼯
			dsjSet.addElements(tUsers);
			dsjSet.addElements(myUsers);
			IdenticalUserMatcher identicalMatcher = new IdenticalUserMatcher();// IdenticalInfoFieldΪ׼��Matcher
			SimilarUserMatcher similarMatcher = new SimilarUserMatcher();// ģ��ƥ��

			intermediateInfo = String.format("\t����ʶ����ϵ��...", source.getName());
			progress = 0;
			setChanged();
			notifyObservers();
			
			int totalCnt = 2*allUsers.size()+2*allUsers.size()*(allUsers.size()-1)/2;//�����������
			int progressCnt = 0, step = Math.max(totalCnt/100, 1);//��ǰ������������ٲ�ˢ��һ��progress
			
			// �ϲ���ͬ����ϵ����ϵ��
			//		�ȿ������а��յģ�IdenticalInfoFieldһ�£�
			for (int i = 0; i < allUsers.size(); i++)
				for (int j = 0; j < i; j++) {
					//ˢprogress
					progressCnt++;
					if (progressCnt%step == 0){
						progress = (double)progressCnt/totalCnt;
						setChanged();
						notifyObservers(progress);
					}

					UserInfo u = allUsers.get(i), v = allUsers.get(j);
					if (u.hasRelation() || v.hasRelation())//ֻ����ͬ����ϵ����ϵ��
						continue;
					if (dsjSet.findSet(u) == dsjSet.findSet(v))
						continue;
					if (identicalMatcher.match(u, v))
						dsjSet.unionSet(u, v);					
				}
			//		�ٿ�ģ����
			for (int i = 0; i < allUsers.size(); i++)
				for (int j = 0; j < i; j++) {
					//ˢprogress
					progressCnt++;
					if (progressCnt%step == 0){
						progress = (double)progressCnt/totalCnt;
						setChanged();
						notifyObservers(progress);
					}
					
					UserInfo u = allUsers.get(i), v = allUsers.get(j);
					if (u.hasRelation() || v.hasRelation())//ֻ����ͬ����ϵ����ϵ��
						continue;
					if (dsjSet.findSet(u) == dsjSet.findSet(v))
						continue;
					if (similarMatcher.match(u, v)) {
						// �����Ƶ���ϵ�ˣ����û������ǲ���ͬһ����ϵ��
						if (center.getUI().yesOrNo(
								String.format(
										"��ϵ��\"%s\"����ϵ��\"%s\"������ͬһ����ϵ�ˣ���������", 
										u.getStringValue(), 
										v.getStringValue())))
							dsjSet.unionSet(u, v);
					}
				}

			// ������ͬ����ϵ����ϵ��
			Map<UserInfo, UserInfo> synUserMap = new HashMap<UserInfo, UserInfo>();//�Ӽ��ʹ���Ԫӳ�䵽�����е���ͬ����ϵ����ϵ��
			//		�ȿ������а��յģ�IdenticalInfoFieldһ�£�
			for(int i=0; i<allUsers.size(); i++){
				//ˢprogress
				progressCnt++;
				if (progressCnt%step == 0){
					progress = (double)progressCnt/totalCnt;
					setChanged();
					notifyObservers(progress);
				}

				UserInfo realU = allUsers.get(i);
				UserInfo u = dsjSet.findSet(realU);
				if (synUserMap.get(u) == null){//��ǰû��ͬ����ϵӳ��
					for(int j=0; j<myUsers.size(); j++){
						UserInfo v = myUsers.get(j);
						if (!v.hasRelation())//ֻ����ͬ����ϵ����ϵ��
							continue;
						if (identicalMatcher.match(realU, v)){
							dsjSet.unionSet(u, v);
							synUserMap.put(dsjSet.findSet(u), v);
							break;
						}
					}
				}
			}
			//		�ٿ�ģ����
			for(int i=0; i<allUsers.size(); i++){
				//ˢprogress
				progressCnt++;
				if (progressCnt%step == 0){
					progress = (double)progressCnt/totalCnt;
					setChanged();
					notifyObservers(progress);
				}
				
				UserInfo realU = allUsers.get(i);
				UserInfo u = dsjSet.findSet(realU);
				if (synUserMap.get(u) == null){//��ǰû��ͬ����ϵӳ��
					for(int j=0; j<myUsers.size(); j++){
						UserInfo v = myUsers.get(j);
						if (!v.hasRelation())//ֻ����ͬ����ϵ����ϵ��
							continue;
						if (similarMatcher.match(realU, v))
							// �����Ƶ���ϵ�ˣ����û������ǲ���ͬһ����ϵ��
							if (center.getUI().yesOrNo(
									String.format(
											"��ϵ��:\"%s\"����ϵ��:\"%s\"������ͬһ����ϵ�ˣ���������", 
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
			intermediateInfo = String.format("\t��ʶ���%d����ͬ����ϵ�ˣ����ںϲ���ϵ����Ϣ...", diffCnt);
			setChanged();
			notifyObservers();
			
			// ѯ����κϲ�
			Map<UserInfo, UserInfo> sourceUserMap = new HashMap<UserInfo, UserInfo>();//�Ӽ���ӳ�䵽����ĳһ��ͬ��Դ��ϵ��
			Map<UserInfo, UserInfo> myUserMap = new HashMap<UserInfo, UserInfo>();//�Ӽ���ӳ�䵽����ĳһ����ͬ��Դ��ϵ��
			Map<UserInfo, UserInfo> mUserMap = new HashMap<UserInfo, UserInfo>();//���պϲ�������ϵ��ӳ��
			
			totalCnt = allUsers.size();
			progressCnt = 0;
			step = Math.max(totalCnt/100, 1);
			
			//	���Ȼ��ӳ��
			for(UserInfo user: tUsers)
				sourceUserMap.put(dsjSet.findSet(user), user);
			for(UserInfo user: myUsers)
				myUserMap.put(dsjSet.findSet(user), user);
			for(UserInfo user: allUsers){
				//ˢprogress
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
			intermediateInfo = String.format("\t�ϲ����");
			setChanged();
			notifyObservers();
			
			progress = 0;
			intermediateInfo = String.format("\t�����޸�����...");
			setChanged();
			notifyObservers();
			Set<UserInfo> toBeRemoved = new HashSet<UserInfo>();//Ҫɾ������ϵ�˼��ʹ���
			Set<UserInfo> toBeAdded = new HashSet<UserInfo>();//Ҫ�������ϵ�˼��ʹ���

			// ѯ��ֻ��ϵͳ�е���ϵ��
			boolean allYes = center.getUI().yesOrNo("�����в�������ϵͳ��ͬ��Դ����ϵ�˼��룿");
			
			for(UserInfo user: allUsers){
				UserInfo u = dsjSet.findSet(user);
				UserInfo mu = mUserMap.get(u); 
				if (u == user && sourceUserMap.get(u) == null){//ֻ�б�ϵͳ���е���ϵ��
					if (allYes || center.getUI().yesOrNo(String.format("�û�:\"%s\"��������ͬ��Դ�У�ѡ��%s������ϵ�˼���ͬ��Դ��ѡ��%s������ϵ�˴�ϵͳ��ɾ��", 
							mu.getStringValue(), "Yes/OK", "No/Cancel"))){
						toBeAdded.add(mu);
					}
					else
						toBeRemoved.add(mu);
				}
			}

			// ѯ��ֻ��ͬ��Դ�е���ϵ��
			for(UserInfo user: allUsers){
				UserInfo u = dsjSet.findSet(user);
				UserInfo mu = mUserMap.get(u);
				if (u == user && myUserMap.get(u) == null)//ֻ��ͬ��Դ���е���ϵ��
					if (allYes || center.getUI().yesOrNo(String.format("�û�:\"%s\"��������ϵͳ�У�ѡ��%s������ϵ�˼���ϵͳ��ѡ��%s������ϵ�˴�ͬ��Դ��ɾ��", 
							mu.getStringValue(), "Yes/OK", "No/Cancel"))){
						toBeAdded.add(mu);
					}
					else
						toBeRemoved.add(mu);
			}

			// ����ϵͳ
			//	�������ϵ��
			progress = 0;
			intermediateInfo = String.format("\t\t���ڸ���ϵͳ...");
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
				//ˢprogress
				progressCnt++;
				if (progressCnt%step == 0){
					progress = (double)progressCnt/totalCnt;
					setChanged();
					notifyObservers(progress);
				}				
			}
			//	ɾ����ϵ�ˣ�������ϵ����Ϣ
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
				//ˢprogress
				progressCnt++;
				if (progressCnt%step == 0){
					progress = (double)progressCnt/totalCnt;
					setChanged();
					notifyObservers(progress);
				}				
			}
			center.getAllContactsBox().updateAll();

			// ����ͬ��Դ
			//	ɾ����ϵ�ˣ�������ϵ����Ϣ
			progress = 0;
			intermediateInfo = String.format("\t\t���ڸ���ͬ��Դ...");
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
			//	�������ϵ��
			for(UserInfo user: myUsers){
				if (toBeAdded.contains(user)){
					tUsers.add(user);
					Extension ext = (Extension) user.getInfoField(InfoFieldName.Extension);
					ext.setValue("Operation", GoogleContactOperator.OperationValue.Add.name());
				}
			}
			//	ִ�в���
			totalCnt = tUsers.size();
			progressCnt = 0;
			step = Math.max(totalCnt/100, 1);
			for(int i=0; i<tUsers.size(); i++){//Ϊ�˼��ӽ��ȣ������������紫��������������Ǿ�һ��һ������
				center.getDataCenter().updateSource(tUsers.subList(i, i+1), source);
				//ˢprogress
				progressCnt++;
				if (progressCnt%step == 0){
					progress = (double)progressCnt/totalCnt;
					setChanged();
					notifyObservers(progress);
				}				
			}
			
			//TODO ȥ�������������û�
		} catch (AuthenticationException e) {
			e.printStackTrace();
			return new BoolInfo("Google�û���֤ʧ��");
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

		intermediateInfo = "Զ��ͬ�����";
		setChanged();
		notifyObservers();

		return new BoolInfo();
	}

	/**
	 * 0��1��ʵ�����������
	 * @return
	 */
	public double getProgress(){
		return progress;
	}
}
