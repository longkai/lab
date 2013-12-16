/*
 * The MIT License (MIT)
 * Copyright (c) 2013 newgxu.cn <the original author or authors>.
 * The software shall be used for good, not evil.
 */
package cn.newgxu.lab.notty.service;

import cn.newgxu.lab.notty.entity.AuthorizedUser;
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
public class AuthServiceTest {

	private static Logger L = LoggerFactory.getLogger(AuthServiceTest.class);

	private static int TOTAL = 8;

	@Inject
	private AuthService authService;

	private AuthorizedUser _u;

	private long id;

	@Before
	public void setUp() throws Exception {
		_u = new AuthorizedUser();
		_u.setOrg("test org");
		_u.setAuthorizedName("test name");
		_u.setContact("10086");
		_u.setAbout("test about");
		_u.setAccount("test account");
		_u.setPassword("test password");

		id = (long) (Math.random() * TOTAL) + 1;
	}

	@After
	public void tearDown() throws Exception {
//		do nothing now...
		System.err.println("done.");
	}

	@Test
	public void testCreate() throws Exception {
		assertEquals(_u.getId(), 0L);
		authService.create(_u, _u.getPassword());
		assertTrue(_u.getId() > 0L);
	}

	@Test
	public void testResetPassword() throws Exception {
		AuthorizedUser user = authService.find(id);
		if (user.isBlocked()) {
			return;
		}
		String newPwd = "_newpwd";
//		all the string params password are plain text.
		authService.resetPassword(user, newPwd, newPwd, null);
		AuthorizedUser u = authService.login(user.getAccount(), newPwd, null);
		assertNotNull(u);
	}

	@Test
	public void testUpdate() throws Exception {
		AuthorizedUser user = authService.find(id);
		String newContact = "10086";
		user.setContact(newContact);
		authService.update(user, "123456");
		assertEquals(newContact, authService.find(id).getContact());
	}

	@Test
	public void testToggleBlock() throws Exception {
		AuthorizedUser user = authService.find(id);
		if (user.isBlocked()) {
			authService.toggleBlock(user);
			assertEquals(false, authService.find(id).isBlocked());
		} else {
			authService.toggleBlock(user);
			assertEquals(true, authService.find(id).isBlocked());
		}
	}

	@Test
	@Rollback(false)
	public void testFind() throws Exception {
		AuthorizedUser user = authService.find(id);
		L.debug("u: {}", user);
		assertNotNull(user);
	}

	@Test
	public void testLogin() throws Exception {
		AuthorizedUser user = authService.find(id);
		if (user.isBlocked()) {
			return;
		}
		AuthorizedUser u = authService.login(user.getAccount(), "123456", null);
		assertNotNull(u);
	}

	@Test
	@Rollback(false)
	public void testTotal() throws Exception {
		long total = authService.total();
		assertEquals(TOTAL, total);
	}


	@Test
	@Rollback(false)
	public void testList() throws Exception {
		L.debug("id={}", id);
		List<AuthorizedUser> users = authService.users(AuthService.LATEST, (int) id);
		for (AuthorizedUser u : users) {
			L.debug("uid: {}, name: {}", u.getId(), u.getAuthorizedName());
		}

		users = authService.users(AuthService.BLOCKED, (int) id);
		for (AuthorizedUser u : users) {
			L.debug("uid: {}, name: {}", u.getId(), u.getAuthorizedName());
		}

		users = authService.users(AuthService.ALL, (int) id);
		for (AuthorizedUser u : users) {
			L.debug("uid: {}, name: {}", u.getId(), u.getAuthorizedName());
		}
	}

	@Test
	@Rollback(false)
	public void testList2() throws Exception {
		L.debug("id={}", id);
		List<AuthorizedUser> users = authService.users(AuthService.LATEST, (int) id, true, id);
		for (AuthorizedUser u : users) {
			L.debug("uid: {}, name: {}", u.getId(), u.getAuthorizedName());
		}
		users = authService.users(AuthService.LATEST, (int) id, false, id);
		for (AuthorizedUser u : users) {
			L.debug("uid: {}, name: {}", u.getId(), u.getAuthorizedName());
		}

		users = authService.users(AuthService.BLOCKED, (int) id, true, id);
		for (AuthorizedUser u : users) {
			L.debug("uid: {}, name: {}", u.getId(), u.getAuthorizedName());
		}

		users = authService.users(AuthService.BLOCKED, (int) id, false, id);
		for (AuthorizedUser u : users) {
			L.debug("uid: {}, name: {}", u.getId(), u.getAuthorizedName());
		}

		users = authService.users(AuthService.ALL, (int) id, true, id);
		for (AuthorizedUser u : users) {
			L.debug("uid: {}, name: {}", u.getId(), u.getAuthorizedName());
		}

		users = authService.users(AuthService.ALL, (int) id, false, id);
		for (AuthorizedUser u : users) {
			L.debug("uid: {}, name: {}", u.getId(), u.getAuthorizedName());
		}
	}

	@Test
	@Rollback(false)
	public void testSync() throws Exception {
		AuthorizedUser user = authService.find(id);
		L.debug("id:{}, last timestdamp:{}", id, user.getJoinDate().getTime());
		List<AuthorizedUser> users = authService.sync(user.getJoinDate().getTime(), (int) id);
		for (AuthorizedUser u : users) {
			L.debug("u:{}", u);
		}

	}
}
