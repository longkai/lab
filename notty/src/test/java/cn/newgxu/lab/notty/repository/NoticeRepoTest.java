/*
 * The MIT License (MIT)
 * Copyright (c) 2013 newgxu.cn <the original author or authors>.
 * The software shall be used for good, not evil.
 */
package cn.newgxu.lab.notty.repository;

import cn.newgxu.lab.notty.entity.Notice;
import cn.newgxu.lab.util.SQLUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @User longkai
 * @Date 13-8-1
 * @Mail im.longkai@gmail.com
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring-test.xml")
@TransactionConfiguration
@Transactional
public class NoticeRepoTest {

	private static Logger l = LoggerFactory.getLogger(NoticeRepoTest.class);

	@Inject
	private NoticeRepo noticeDao;

	@Inject
	private AuthRepo authDao;

	private Notice n;

	private static int COUNT = 17;

	private int id;

	@Before
	public void setUp() throws Exception {
		n = new Notice();
		n.setTitle("test title");
		n.setAddDate(new Date());
		n.setContent("test content");
		n.setAuthor(authDao.find(1));

		id = (int) (Math.random() * COUNT) + 1;
	}

	@After
	public void tearDown() throws Exception {

	}

	@Test
	public void testPersist() throws Exception {
		noticeDao.insert(n);
		l.debug("n: {}", n);
		long id1 = n.getId();
		System.out.println(id1);
		assertTrue(id1 > 0);
	}

	@Test
	@Rollback(false)
	public void testFind() throws Exception {
		Notice n = noticeDao.find(1);
		l.debug("n:{}", n);
		l.debug("u: {}", n.getAuthor());
		l.debug("u: {}", n.getAuthor().getAuthorizedName());
		l.debug("u: {}", n.getAuthor().getContact());
		assertNotNull(n);
	}

	@Test
	public void testUpdate() throws Exception {
		noticeDao.insert(n);
		l.debug("n:{}", n);
		String newTitle = "new title";
		n.setTitle(newTitle);
		noticeDao.update(SQLUtils.set(new String[]{"title"}, new Object[]{newTitle}), "id=" + n.getId());
		assertEquals(newTitle, noticeDao.find(n.getId()).getTitle());
	}

	@Test
	public void testRemove() throws Exception {
		noticeDao.insert(n);
		l.debug("n: {}", n);
		noticeDao.delete(null, "id=" + n.getId());
		assertNull(noticeDao.find(n.getId()));
	}

	@Test
	@Rollback(false)
	public void testQuery() throws Exception {
		List<Notice> notices = noticeDao.query("*",
				MessageFormat.format("{0} N JOIN {1} U ON N.uid=U.id", NoticeRepo.TABLE, AuthRepo.TABLE),
				"N.id=" + id, null, null, null, null);
		for (int i = 0; i < notices.size(); i++) {
			Notice notice = notices.get(i);
			l.debug("n{}: {}, u: {}", i, notice, notice.getAuthor().getId());
		}
	}
}
