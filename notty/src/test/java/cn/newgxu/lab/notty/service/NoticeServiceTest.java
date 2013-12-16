/*
 * The MIT License (MIT)
 * Copyright (c) 2013 newgxu.cn <the original author or authors>.
 * The software shall be used for good, not evil.
 */
package cn.newgxu.lab.notty.service;

import cn.newgxu.lab.notty.entity.Notice;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
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
public class NoticeServiceTest {

	private static Logger L = LoggerFactory.getLogger(NoticeServiceTest.class);
	private static int TOTAL = 17;
	private static int TOTAL_USER = 8;

	private int nid;
	private int uid;

	@Inject
	private NoticeService noticeService;

	@Inject
	private AuthService authService;

	private Notice _n;

	@Before
	public void setUp() throws Exception {
		nid = (int) (Math.random() * TOTAL) + 1;
		uid = (int) (Math.random() * TOTAL_USER) + 1;

		_n = new Notice();
		_n.setAuthor(authService.find(uid));
		_n.setTitle("test title");
		_n.setContent("test content");
	}

	@After
	public void tearDown() throws Exception {

	}

	@Test
	public void testCreate() throws Exception {
		noticeService.create(_n);
		assertTrue(_n.getId() > 0);
	}

	@Test(expected = RuntimeException.class)
	public void testCreate2() throws Exception {
		_n.setTitle(null);
		noticeService.create(_n);
	}

	@Test
	@Rollback(false)
	public void testDelete() throws Exception {

	}

	@Test
	public void testUpdate() throws Exception {
		Notice notice = noticeService.find(nid);
		String newTitle = "new_title";
		notice.setTitle(newTitle);
		noticeService.update(notice, notice.getAuthor().getId());
		assertEquals(noticeService.find(nid).getTitle(), newTitle);
	}

	@Test(expected = RuntimeException.class)
	public void testUpdate2() throws Exception {
		Notice notice = noticeService.find(nid);
		String newTitle = "new_title";
		notice.setTitle(newTitle);
		notice.setAuthor(authService.find(uid > 2 ? uid - 1 : uid + 1));
		noticeService.update(notice, uid);
	}

	@Test
	@Rollback(false)
	public void testFind() throws Exception {
		assertNotNull(noticeService.find(nid));
	}

	@Test
	@Ignore("does mybatis has lazy loading?") // todo check it!
	public void testView() throws Exception {
		Notice n1 = noticeService.find(nid);
		Notice n2 = noticeService.view(n1.getId());
		assertTrue(n1.getClickTimes() + 1 == n2.getClickTimes());
	}

	@Test
	@Rollback(false)
	public void testTotal() throws Exception {
		assertTrue(noticeService.total() == TOTAL);
	}

	@Test
	public void testToggleBlock() throws Exception {
		Notice notice = noticeService.find(nid);
		boolean before = notice.isBlocked();
		noticeService.toggleBlock(nid, notice.getAuthor().getId());
		assertTrue(noticeService.find(nid).isBlocked() != before);
	}

	@Test(expected = RuntimeException.class)
	public void testToggleBlock2() throws Exception {
		Notice notice = noticeService.find(nid);
		boolean before = notice.isBlocked();
		long authorId = notice.getAuthor().getId();
		noticeService.toggleBlock(nid, authorId + 1); // not the author or id is overflow!
	}

	@Test
	@Rollback(false)
	public void testNewerCount() throws Exception {
		assertTrue(noticeService.newerCount(TOTAL - 2) == 2);
	}

	private static void printNotices(List<Notice> notices) {
		for (Notice n : notices) {
			L.debug("{}", n);
		}
	}

	@Test
	@Rollback(false)
	public void testLatest1() throws Exception {
		List<Notice> latest = noticeService.latest(nid);
		assertTrue(latest.get(0).getId() == TOTAL);

		printNotices(latest);
	}

	@Test
	@Rollback(false)
	public void testLatest2() throws Exception {
		List<Notice> latest = noticeService.latest(uid, nid);
		printNotices(latest);
	}

	@Test
	@Rollback(false)
	public void testNotices() throws Exception {
		List<Notice> list = noticeService.notices(10, true, TOTAL);
		printNotices(list);

		List<Notice> list1 = noticeService.notices(10, true, uid);
		printNotices(list1);
	}

	@Test
	@Rollback(false)
	public void testSync() throws Exception {
		Notice notice = noticeService.find(nid);
		List<Notice> notices = (List<Notice>) noticeService.sync(notice.getAddDate().getTime(), TOTAL).get("notices");
		for (Notice n : notices) {
			if (n.getLastModifiedDate() != null) {
				assertTrue(n.getAddDate().after(notice.getAddDate()) || n.getLastModifiedDate().after(notice.getAddDate()));
			} else {
				assertTrue(n.getAddDate().after(notice.getAddDate()));
			}
		}
	}

	@Test
	@Rollback(false)
	public void testSearch() throws Exception {
		String keywords = "雨无声";
		List<Notice> notices = noticeService.search(keywords, 50);
		for (Notice n : notices) {
			L.debug("n:{}", n);
		}
	}
}
