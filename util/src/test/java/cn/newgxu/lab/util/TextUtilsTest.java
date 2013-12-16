/*
 * The MIT License (MIT)
 * Copyright (c) 2013 newgxu.cn <the original author or authors>.
 * The software shall be used for good, not evil.
 */
package cn.newgxu.lab.util;

import org.junit.Ignore;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 *
 * @User longkai
 * @Date 13-8-10
 * @Mail im.longkai@gmail.com
 */
public class TextUtilsTest {

	private static final String emptyStr = "";

	@Test
	public void testIsEmpty() throws Exception {
		assertTrue(TextUtils.isEmpty(emptyStr));
		assertTrue(TextUtils.isEmpty(null));
		assertTrue(!TextUtils.isEmpty("null"));
		assertTrue(!TextUtils.isEmpty(" "));
	}

	@Test
	public void testIsEmptyWithTrim() throws Exception {
		assertTrue(TextUtils.isEmpty(" ", true));
		assertTrue(!TextUtils.isEmpty(" ", false));
	}

	@Test
	public void testQuote() throws Exception {
		String s1 = "hello";
		String s2 = "'hello'";
		assertEquals(s2, TextUtils.quote(s1));
		assertEquals("''", TextUtils.quote(emptyStr));
	}

	@Test
	public void testDoubleQuote() throws Exception {
		String s1 = "hello";
		String s2 = "\"hello\"";
		assertEquals(s2, TextUtils.doubleQuote(s1));
		assertEquals("\"\"", TextUtils.doubleQuote(emptyStr));
	}

	@Test
	public void testConcat() throws Exception {
		String s = "I love this game!";
		assertEquals(s, TextUtils.concat("I ", "love ", "this ", "game!"));
	}

	@Test
	public void testEncoding() throws Exception {
//      这是编码为gbk在iso8859-1上的显示
		String s1 = "Áú¿­";
		assertEquals("龙凯", TextUtils.encoding(s1, null, "GBK"));
	}

	@Test
	public void testReplace() throws Exception {
		String sql = "insert into user (id, name, password) values (?, ?, ?)";
		String result = "insert into user (id, name, password) values (null, hello, world)";
		String replace = TextUtils.replace(sql, "null", "hello", "world");
		assertEquals(replace, replace);
	}

	@Test
	public void testReplaceWithCustomRegex() throws Exception {
		String sql = "insert into user (id, name, password) values ($1, $2, $3)";
		String result = "insert into user (id, name, password) values (null, hello, world)";
		String replace = TextUtils.replace(sql, "\\$\\d", new String[]{"null", "hello", "world"});
		assertEquals(replace, replace);
	}

	@Test
	public void testReplaceWithMap() throws Exception {
		String sql = "insert into user (id, name, password) values (:id, :name, :password)";
		String result = "insert into user (id, name, password) values (null, hello, world)";
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", "null");
		map.put("name", "hello");
		map.put("password", "world");
		assertEquals(result, TextUtils.replace(sql, map));
	}

	@Test
	@Ignore("due to the difference between OS.")
	public void testGetFileExt() throws Exception {
		String f = "C:/docs/hello.doc";
		assertEquals(".doc", TextUtils.getFileExt(f));
		f = "/etc/files/c.c.c.cpp";
		assertEquals(".cpp", TextUtils.getFileExt(f));
	}

	@Test
	public void testToGetter() throws Exception {
		String s = "name";
		assertEquals("getName", TextUtils.toGetter(s));
		s = "_hei";
		assertEquals("get_hei", TextUtils.toGetter(s));
	}

	@Test
	public void testToSetter() throws Exception {
		String s = "name";
		assertEquals("setName", TextUtils.toSetter(s));
		s = "_hei";
		assertEquals("set_hei", TextUtils.toSetter(s));
	}
}
