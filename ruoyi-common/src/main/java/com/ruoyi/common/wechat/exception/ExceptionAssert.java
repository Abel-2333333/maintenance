package com.ruoyi.common.wechat.exception;

import cn.hutool.core.collection.CollUtil;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.exception.ServiceException;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Map;


/**
 * @author Abel
 * @since 1/30/2023 2:53 PM
 */
public class ExceptionAssert {
	public static void isTrue(boolean expression, String message) {
		if (!expression) {
			throw new ServiceException(message);
		}
	}
	
	public static void isNull(Object object, String name) {
		if (object != null) {
			throw new ServiceException("Argument '" + name + "' must be null!");
		}
	}
	
	public static void notNull(Object object, String name) {
		if (object == null) {
			throw new ServiceException("Argument '" + name + "' must not be null!");
		}
	}
	
	public static void notEmpty(Object[] array, String name) {
		if (ObjectUtils.isEmpty(array)) {
			throw new ServiceException("Array '" + name + "' must not be empty! ");
		}
	}
	
	public static void notEmpty(Collection<?> collection, String name) {
		if (CollectionUtils.isEmpty(collection)) {
			throw new ServiceException("Collection '" + name + "' must not be empty!");
		}
	}
	
	/**
	 * 判断集合是否为空， 为空抛出异常
	 *
	 * @param map
	 * @param name
	 */
	public static <K, V> void notEmpty(Map<K, V> map, String name) {
		if (CollUtil.isEmpty(map)) {
			throw new ServiceException("Map '" + name + "' must not be empty!");
		}
	}
	
	/**
	 * 直接抛出异常
	 */
	public static void throwException(String message) {
		throw new ServiceException(message);
	}
	
	/**
	 * 如果为true，直接抛出异常
	 */
	public static void throwException(Boolean isTrue, String message) {
		if (Boolean.TRUE.equals(isTrue)) {
			throw new ServiceException(message, HttpStatus.ERROR);
		}
	}
	
	public static void throwException(String message, Integer code) {
		throw new ServiceException(message, code);
	}
	
	public static void throwException(Boolean isTrue, Integer code, String message) {
		if (Boolean.TRUE.equals(isTrue)) {
			throwException(message, code);
		}
	}
}
