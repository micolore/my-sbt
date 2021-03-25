package com.kubrick.sbt.web.common.spring;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

/**
 * @author k
 * @version 1.0.0
 * @ClassName ThreadLocalScope
 * @description:
 * @date 2021/2/27 下午11:20
 */
public class ThreadLocalScope implements Scope {

	private static final ThreadLocal THREAD_LOCAL_SCOPE = new ThreadLocal();

	@Override
	public Object get(String name, ObjectFactory<?> objectFactory) {
		Object v = THREAD_LOCAL_SCOPE.get();
		if (v != null) {
			return v;
		}
		Object object = objectFactory.getObject();
		THREAD_LOCAL_SCOPE.set(object);
		return object;
	}

	@Override
	public Object remove(String name) {
		THREAD_LOCAL_SCOPE.remove();
		return null;
	}

	@Override
	public void registerDestructionCallback(String name, Runnable callback) {

	}

	@Override
	public Object resolveContextualObject(String key) {
		return null;
	}

	@Override
	public String getConversationId() {
		return null;
	}

}
