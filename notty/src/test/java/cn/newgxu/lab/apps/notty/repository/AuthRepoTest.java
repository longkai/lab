/*
 * The MIT License (MIT)
 * Copyright (c) 2013 longkai(龙凯)
 * The software shall be used for good, not evil.
 */
package cn.newgxu.lab.apps.notty.repository;

import cn.newgxu.lab.apps.notty.entity.AuthorizedUser;
import cn.newgxu.lab.util.SQLUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @User longkai
 * @Date 13-8-1
 * @Mail im.longkai@gmail.com
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring-test.xml")
public class AuthRepoTest {

    private static Logger l = LoggerFactory.getLogger(AuthRepo.class);

    @Inject
    private AuthRepo authRepo;

    private AuthorizedUser u;

    @Before
    public void init() {
        u = new AuthorizedUser();
        u.setPassword("123456");
        u.setAccount("longkai-idea");
        u.setAbout("idea");
        u.setAuthorizedName("idea");
        u.setContact("14795633343");
        u.setJoinDate(new Date());
        u.setBlocked(false);
        u.setLastLoginIP("127.0.0.1");
        u.setLastLoginDate(new Date());
        u.setOrg("yws");
    }

    @Test
    public void testFind() {
        AuthorizedUser user = authRepo.find(1);
        l.debug("{}", user);
    }

    @Test
//	@Rollback
    @DirtiesContext
    public void testPsersit() throws Exception {
        authRepo.insert(u);
        System.out.println(u);
    }

    @Test
//	@Rollback
    @DirtiesContext
    public void testRemove() throws Exception {
        authRepo.insert(u);
//		l.debug("{}", u);
//		authRepo.remove();
    }

    @Test
    public void testSize() throws Exception {
        long size = authRepo.count(null, null);
        l.debug("size: {}", size);
    }

    @Test
    public void testUsers() throws Exception {
//		List<AuthorizedUser> users = authRepo.users(1, 5, false);
//		Assert.assertEquals(users.size(), 10);
//		Assert.assertEquals(authRepo.users(0, 10, true).size(), 5);
//		List<AuthorizedUser> users = authRepo.users(SQLUtils.columns(new String[]{"id", "authed_name"}));
        List<AuthorizedUser> users = authRepo.query(null, null, null, null, null, "id desc", "2,1");
        l.debug("size: {}", users.size());
        for (int i = 0; i < users.size(); i++) {
            l.debug("user: {}", users.get(i));
        }
//		AuthorizedUser user = authRepo.one(SQLUtils
//				.query("info_users", null, "id=1", null, null, null, null, null));
//		l.debug("u: {}", user);
    }

    @Test
//	@Rollback
    @DirtiesContext
    public void testUpdate() throws Exception {
        authRepo.insert(u);
        l.debug("u: {}", u);
//		int i = authRepo.update(SQLUtils
//				.update("info_users", new String[]{"authed_name"}, new Object[]{"___update"}, "id=?", new Object[]{u.getId()}));
        int i = authRepo.update(SQLUtils
                .set(new String[]{"authed_name"},
                        new Object[]{"___update"}),
                "id=" + u.getId());
//		Assert.assertEquals(i, 1);
        l.debug("i:{}", i);
    }

    @Test
    public void testInit() throws Exception {
//		AuthorizedUser user = authRepo.login("ma_yun", "123456");
//		l.debug("u:{}", user);
        List<AuthorizedUser> users = authRepo.query(null, null, null, null, null, null, null);
        for (int i = 0; i < users.size(); i++) {
            l.debug("u: {}", users.get(i));
        }
    }
}
