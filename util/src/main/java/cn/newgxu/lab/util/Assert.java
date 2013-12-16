/*
 * The MIT License (MIT)
 * Copyright (c) 2013 newgxu.cn <the original author or authors>.
 * The software shall be used for good, not evil.
 */
package cn.newgxu.lab.util;

import java.util.Collection;

/**
 * 断言实用工具类。
 *
 * @User longkai
 * @Date 13-2-29
 * @Mail im.longkai@gmail.com
 */
public class Assert {

	/**
	 * 断言字符串不为空并且不为空串，会主动截取两边的空白符
	 *
	 * @param message
	 * @param str
	 */
	public static final void notEmpty(String message, String str) {
		notNull(message, str);
		if (str.length() == 0) {
			throw new IllegalArgumentException(message);
		}
	}

	public static final void notEmpty(String message, Object[] objects) {
		if (objects == null || objects.length == 0) {
			throw new IllegalArgumentException(message);
		}
	}

	public static final void notEmpty(String message, Collection<?> collection) {
		if (collection == null || collection.size() == 0) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * 断言字符串是否大于等于指定的长度
	 *
	 * @param message
	 * @param str
	 * @param length
	 */
	public static final void hasLength(String message, String str, int length) {
		Assert.notNull(message, str);

		if (str.length() < length) {
			throw new IllegalArgumentException(message);
		}
	}

	public static final void maxLength(String message, String str, int maxLength) {
		Assert.notNull(message, str);
		if (str.length() > maxLength) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * 断言字符串是否在指定的区间
	 *
	 * @param message
	 * @param str
	 * @param minLength
	 * @param maxLength
	 */
	public static final void between(String message, String str, int minLength, int maxLength) {
		notNull(message, str);
		if (str.length() < minLength || str.length() > maxLength) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * 断言数字必须在某个区间内，闭区间
	 *
	 * @param message
	 * @param real
	 * @param min
	 * @param max
	 */
	public static final void between(String message, int real, int min, int max) {
		if (real < min || real > max) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * 断言两个对象必须相同，调用对象底层了equals方法
	 *
	 * @param message
	 * @param o1
	 * @param o2
	 */
	public static final void equals(String message, Object o1, Object o2) {
		if (!o1.equals(o2)) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * 断言两个对象不许相同，实际上是引用是否指向同一个内存地址。o1 == o2
	 *
	 * @param message
	 * @param o1
	 * @param o2
	 */
	public static final void same(String message, Object o1, Object o2) {
		if (o1 != o2) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * 断言对象不为空
	 *
	 * @param message
	 * @param object
	 */
	public static final void notNull(String message, Object object) {
		if (object == null) {
			throw new NullPointerException(message);
		}
	}
}
