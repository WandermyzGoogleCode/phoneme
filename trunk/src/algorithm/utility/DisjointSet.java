package algorithm.utility;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * ²¢²é¼¯
 * @author Administrator
 *
 * @param <T>
 */
public class DisjointSet<T> {
	private Map<T, T> parent = new HashMap<T, T>();
	
	public T findSet(T u){
		if (parent.get(u) != u)
			parent.put(u, findSet(parent.get(u)));
		return parent.get(u);
	}
	
	public void unionSet(T u, T v){
		parent.put(parent.get(u), parent.get(v));
	}
	
	public void addElements(Collection<T> elems){
		for(T e: elems)
			parent.put(e, e);
	}
}
