package egovframework.eam.api.internal;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class Storage<K> {
	private Map<String, K> storage = Collections.synchronizedMap(new HashMap<String, K>());
	
	public void put(String key, K value) {
		storage.put(key, value);
	}
	
	public K get(String key) {
		return storage.get(key);
	}
}
