package egovframework.eam.admin.main.server.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

public class BeanOperator<S, T> {
	private Class<T> clazz = null;
	
	public BeanOperator(Class<T> clazz) {
		this.clazz = clazz;
	}

	public T cloneBean(S source) {
		try {
			T target = clazz.newInstance();
			
			BeanUtils.copyProperties(source, target);
			
			return target;
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	
	public List<T> cloneList(List<S> sources) {
		List<T> target = new ArrayList<T>();
		
		for (S src : sources) {
			T t = cloneBean(src);
			target.add(t);
		}
		
		return target;
	}
	
	public void copyBean(T source, S target) {
		BeanUtils.copyProperties(source, target);
	}
}
