package algorithm;

import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import serverLogicCenter.sdataCenter.ServerDataCenter;
import entity.Group;
import entity.ID;

public class BFSRelationCube implements RelationCube {
	private static final int maxDist = 5;//当前只给搜总举例小于等于5的 

	@Override
	public List<ID> getSearchRes(ID from, ID to,
			ServerDataCenter center) throws SQLException {
		Set<ID> footmark = new HashSet<ID>();
		Queue<ID> queue = new ArrayDeque<ID>();
		Map<ID, Integer> dist = new HashMap<ID, Integer>();
		Map<ID, ID> prev = new HashMap<ID, ID>();
		
		footmark.add(from);
		dist.put(from, 0);
		queue.add(from);
		while (!queue.isEmpty()){
			ID now = queue.poll();
			int nowDist = dist.get(now); 
			if (nowDist >= maxDist)
				break;
			
			
			//同步联系人
			List<ID> synContacts = center.getSynContactID(now);
			List<Integer> visibilities = center.getVisibilities(now, synContacts); 
			for(int i=0; i<synContacts.size(); i++){
				if (visibilities.get(i) < nowDist)
					continue;
				ID next = synContacts.get(i);
				if (footmark.contains(next))
					continue;
				footmark.add(next);
				dist.put(next, nowDist+1);
				prev.put(next, now);
			}
			
			//群组
			List<Group> groups = center.getGroups(now);
			List<ID> idList = new ArrayList<ID>();
			for(Group g: groups)
				idList.add(g.getID());
			visibilities = center.getVisibilities(now, idList);
			for(int i=0; i<groups.size(); i++){
				if (visibilities.get(i) < nowDist)
					continue;
				for(ID next: groups.get(i).getUserSet()){
					if (footmark.contains(next))
						continue;
					footmark.add(next);
					dist.put(next, nowDist+1);
					prev.put(next, now);
				}
			}
		}
		
		
		List<ID> idRes = new ArrayList<ID>();
		if (!dist.containsKey(to))
			return idRes;
		for(ID id=to; !id.equals(from); id = prev.get(id))
			idRes.add(id);
		idRes.add(from);
		
		//reverse
		for(int i=0; i<idRes.size()/2; i++){
			ID t = idRes.get(i);
			idRes.set(i, idRes.get(idRes.size()-i-1));
			idRes.set(idRes.size()-i-1, t);
		}
		
		return idRes;
	}
	
	public static void main(String args[]){
		Integer a = 1, b = 1;
		Integer c = new Integer(1);
		System.out.println(a+b == 2);
	}
}
