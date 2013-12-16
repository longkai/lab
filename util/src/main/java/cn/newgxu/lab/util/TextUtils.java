/*
 * The MIT License (MIT)
 * Copyright (c) 2013 newgxu.cn <the original author or authors>.
 * The software shall be used for good, not evil.
 */
package cn.newgxu.lab.util;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 文本，字符串相关的工具类。
 *
 * @User longkai
 * @Date 13-8-10
 * @Mail im.longkai@gmail.com
 */
public class TextUtils {

	/**
	 * ? 占位符，用于替换相应位置上的文本
	 */
	public static final Pattern QUESTION_MARK_PLACEHOLDER = Pattern.compile("\\?");
	/**
	 * :xx 占位符，用于替换对应名字上的文本
	 */
	public static final Pattern COLON_MARK_PLACEHOLDER = Pattern.compile(":\\w+");

	/**
	 * test the string is <pre>null || ''</pre>
	 *
	 * @param str
	 * @return test result
	 */
	public static final boolean isEmpty(CharSequence str) {
		return str == null ? true : str.length() == 0;
	}

	/**
	 * test the string is <pre>null || ''</pre>, 并且你可以选择是否截取字符串两边的空白
	 *
	 * @param str
	 * @param trim 是否选择截取字符串两边的空白
	 * @return test result
	 */
	public static final boolean isEmpty(String str, boolean trim) {
		return str == null ? true : trim ? str.trim().length() == 0 : str.length() == 0;
	}

	/**
	 * 给字符串两边加上单引号 <b>''</b>
	 *
	 * @param str
	 * @return 'str'
	 */
	public static final String quote(String str) {
		return new StringBuilder(str.length() + 2).append("'").append(str).append("'").toString();
	}

	/**
	 * 给字符串两边加上双引号 <b>""</b>
	 *
	 * @param str
	 * @return "str"
	 */
	public static final String doubleQuote(String str) {
		return new StringBuilder(str.length() + 2).append("\"").append(str).append("\"").toString();
	}

	/**
	 * 将给定的对象数组作为字符串连接起来。
	 *
	 * @param objs
	 * @return "objs[1]" + ... + "objs[n]"
	 */
	public static final String concat(Object... objs) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < objs.length; i++) {
			sb.append(objs[i]);
		}
		return sb.toString();
	}

	/**
	 * 转码，将给定的字符串转换为指定的编码，若原来的不知道原来的编码，将此参数设置为null，系统使用ISO8859_1来转码。
	 *
	 * @param str
	 * @param charsetFrom 当前的编码（乱码的编码）
	 * @param charsetTo   原来的编码（未乱码时的编码）
	 * @return 转码后的字符串
	 */
	public static final String encoding(String str, String charsetFrom, String charsetTo) {
		try {
			return new String(str.getBytes(charsetFrom == null ? "ISO_8859_1" : charsetFrom), charsetTo);
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException("UnsupportedEncodingException for " + str, e);
		}
	}

	/**
	 * 使用默认的 <b><pre>?</pre></b>作为占位符，依次将对应的占位符替换为相应的字符串
	 *
	 * @param string
	 * @param strings
	 * @return 替换后的字符串
	 */
	public static final String replace(String string, String... strings) {
		return replace(QUESTION_MARK_PLACEHOLDER.matcher(string), strings);
	}

	/**
	 * 使用自定义的正则表达式的 <b><pre>regex</pre></b>，依次将匹配到的文本替换为相应的字符串
	 *
	 * @param string
	 * @param regex
	 * @param strings
	 * @return 替换后的字符串
	 */
	public static final String replace(String string, String regex, String[] strings) {
		return replace(Pattern.compile(regex).matcher(string), strings);
	}

	/**
	 * 把字符串里 :name 替换掉map中对应的键为name的值
	 *
	 * @param string
	 * @param map
	 * @return 替换后的字符串
	 */
	public static final String replace(String string, Map<String, String> map) {
		return replace(COLON_MARK_PLACEHOLDER.matcher(string), map, true);
	}

	/**
	 * 通过自定义的正则表达式，将匹配到的文本作为map中对应的键，取出其值来替换掉匹配到的串。
	 *
	 * @param string
	 * @param regex
	 * @param map
	 * @return 替换后的字符串
	 */
	public static final String replace(String string, String regex, Map<String, String> map) {
		return replace(Pattern.compile(regex).matcher(string), map, false);
	}


	/**
	 * 给定文件名，返回扩展名（比如，hello.cpp.zip，返回zip）
	 *
	 * @param fileName
	 * @return 扩展名，若没有则返回null
	 */
	public static final String getFileExt(String fileName) {
		int i = fileName.lastIndexOf(".");
		return i == -1 ? null : fileName.substring(i + 1);
	}

	/**
	 * 得到对应的get方法名
	 *
	 * @param name
	 * @return getXxx
	 */
	public static final String toGetter(String name) {
		return getterOrSetter(name, true);
	}

	/**
	 * 得到对应的set方法名
	 *
	 * @param name
	 * @return setXxx
	 */
	public static final String toSetter(String name) {
		return getterOrSetter(name, false);
	}

	private static String getterOrSetter(String name, boolean toGetter) {
		StringBuilder sb = new StringBuilder(name.length() + 3).append(toGetter ? "get" : "set");
		char c = name.charAt(0);
		if ('a' <= c && c <= 'z') {
			c -= 32;
			return sb.append(c).append(name.substring(1)).toString();
		}
		return sb.append(name).toString();
	}

	private static String replace(Matcher matcher, String... strings) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; matcher.find(); i++) {
			matcher.appendReplacement(sb, strings[i]);
		}
		matcher.appendTail(sb);
		return sb.toString();
	}

	private static String replace(Matcher matcher, Map<String, String> map, boolean isDefault) {
		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			matcher.appendReplacement(sb, map.get(isDefault ? matcher.group().substring(1) : matcher.group()));
		}
		matcher.appendTail(sb);
		return sb.toString();
	}
}
