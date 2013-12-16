/*
 * The MIT License (MIT)
 * Copyright (c) 2013 newgxu.cn <the original author or authors>.
 * The software shall be used for good, not evil.
 */
package cn.newgxu.lab.util;

import org.junit.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 *
 * @User longkai
 * @Date 13-8-10
 * @Mail im.longkai@gmail.com
 */
public class SQLUtilsTest {

	@Test
	public void testColumns() throws Exception {
		String columns = SQLUtils.columns("id", "name", "password");
		assertEquals("id,name,password", columns);
	}

	@Test
	public void testWhereWithArray() throws Exception {
		Date date = new Date();
		String where = SQLUtils.where("a>? AND b<?", 3, date);
		assertEquals("a>3 AND b<" + TextUtils.quote(DateTime.format(date)), where);
	}

	@Test
	public void testWhereWithMap() throws Exception {
		Date date = new Date();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", 1);
		map.put("username", "longkai");
		map.put("date", date);
		String where = SQLUtils.where("id>:id AND username = :username AND last_date < :date", map);
		assertEquals("id>1 AND username = 'longkai' AND last_date < " + TextUtils.quote(DateTime.format(date)), where);
	}

	@Test
	public void testLike() throws Exception {
		String like = SQLUtils.like("title like " + TextUtils.quote("%?%"), "title");
		assertEquals("title like '%title%'", like);
	}

	@Test
	public void testLikeWithMap() throws Exception {
		Map<String, String> m = new HashMap<String, String>();
		m.put("title", "321");
		m.put("content", "123");
		String like = SQLUtils.like("title like '%:title%' OR content like '%:content%'", m);
		assertEquals("title like '%321%' OR content like '%123%'", like);
	}

	@Test
	public void testInjectArg() throws Exception {
		assertEquals("1", SQLUtils.injectArg(1));
		assertEquals("1.1", SQLUtils.injectArg(1.1));
		assertEquals("0", SQLUtils.injectArg(false));
		assertEquals("1", SQLUtils.injectArg(true));
		assertEquals("'1'", SQLUtils.injectArg("1"));
		Date date = new Date();
		assertEquals(TextUtils.quote(DateTime.format(date)), SQLUtils.injectArg(date));
	}

	@Test
	public void testQueryWithCount() throws Exception {
		String s1 = "SELECT COUNT(1) FROM c WHERE id = 1";
		String s2 = "SELECT id,name FROM c WHERE id = 1 LIMIT 10";
		String[] strings = SQLUtils.queryWithCount("c", new String[]{"id", "name"}, "id = 1", null, null, null, null, "10");
		assertEquals(s1, strings[1]);
		assertEquals(s2, strings[0]);
	}

	@Test
	public void testQuery() throws Exception {
		String sql = "SELECT * FROM C WHERE id = 1 GROUP BY c1 ORDER BY ID DESC LIMIT 10";
		String query = SQLUtils.query("C", null, "id = ?", new Object[]{1}, "c1", null, "ID DESC", 10 + "");
		assertEquals(sql, query);
	}

	@Test
	public void testSet() throws Exception {
		String sql = "a=1,b='c'";
		String set = SQLUtils.set(new String[]{"a", "b"}, new Object[]{1, "c"});
		assertEquals(sql, set);
	}

	@Test
	public void testUpdate() throws Exception {
		String update = "UPDATE C SET id=7,name='longkai' WHERE id = 6";
		String c = SQLUtils.update("C", new String[]{"id", "name"}, new Object[]{7, "longkai"}, "id = ?", new Object[]{6});
		assertEquals(update, c);
	}

	@Test
	public void testDelete() throws Exception {
		String delete = "DELETE FROM C WHERE ID= 0";
		String s = SQLUtils.delete("C", "ID= ?", new Object[]{0});
		assertEquals(delete, s);
	}

	@Test
	public void testToSelectCount() throws Exception {
		String sql = "SELECT * FROM C WHERE id = 1 GROUP BY c1 ORDER BY ID DESC LIMIT 10";
		String count = "SELECT COUNT(1) FROM C WHERE id = 1";
		assertEquals(count, SQLUtils.toSelectCount(sql).trim());
	}

	@Test
	public void testSelectCount() throws Exception {
		String sql = "SELECT COUNT(1) FROM c WHERE id > 100";
		String c = SQLUtils.selectCount("c", "id > 100", null);
		assertEquals(sql, c);
	}
}
