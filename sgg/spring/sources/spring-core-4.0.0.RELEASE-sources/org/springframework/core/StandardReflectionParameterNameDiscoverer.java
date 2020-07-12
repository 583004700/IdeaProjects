package org.springframework.core;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class StandardReflectionParameterNameDiscoverer implements ParameterNameDiscoverer {
	/**
	 * 获取方法的参数名数组，在jdk 1.8以上上支持
	 * @param method
	 * @return
	 */
	@Override
	public String[] getParameterNames(Method method) {
		Parameter[] parameters = method.getParameters();
		String[] parameterNames = new String[parameters.length];
		for (int i = 0; i < parameters.length; i++) {
			Parameter param = parameters[i];
			if (!param.isNamePresent()) {
				return null;
			}
			parameterNames[i] = param.getName();
		}
		return parameterNames;
	}

	/**
	 * 获取构造方法参数名数组 需要jdk 1.8以上支持
	 * @param ctor
	 * @return
	 */
	@Override
	public String[] getParameterNames(Constructor<?> ctor) {
		Parameter[] parameters = ctor.getParameters();
		String[] parameterNames = new String[parameters.length];
		for (int i = 0; i < parameters.length; i++) {
			Parameter param = parameters[i];
			if (!param.isNamePresent()) {
				return null;
			}
			parameterNames[i] = param.getName();
		}
		return parameterNames;
	}

}
