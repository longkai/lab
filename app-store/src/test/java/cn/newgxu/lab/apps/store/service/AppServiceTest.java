/*
 * The MIT License (MIT)
 * Copyright (c) 2013 longkai(龙凯)
 * The software shall be used for good, not evil.
 */
package cn.newgxu.lab.apps.store.service;

import cn.newgxu.lab.apps.store.entity.App;
import cn.newgxu.lab.apps.store.entity.AppVersion;
import cn.newgxu.lab.apps.store.entity.Category;
import cn.newgxu.lab.apps.store.entity.Platform;
import cn.newgxu.lab.core.member.MemberService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @User longkai
 * @Date 13-8-13
 * @Mail im.longkai@gmail.com
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring-test.xml")
public class AppServiceTest {

    @Inject
    private AppService appService;

    @Inject
    private MemberService memberService;

    private App app;
    private AppVersion version;

    private static final int TOTAL = 4;
    private static final int VERSION_TOTAL = 6;
    private static final int MEMBER_TOTAL = 6;

    private int id;
    private int versionId;
    private int memberId;

    @Before
    public void setUp() throws Exception {
        id = (int) (Math.random() * TOTAL) + 1;
        versionId = (int) (Math.random() * VERSION_TOTAL) + 1;
        memberId = (int) (Math.random() * MEMBER_TOTAL) + 1;

        app = new App();
        app.setAuthor(memberService.find(memberId));
        app.setDescription("test description!");
        app.setCategory(Category.EMTERTAINMENT);
        app.setExtra("no extra!");
        app.setIcon("images...");
        app.setName("hello world!");
        app.setOs_requirement("android 4.3+");
        app.setOutside_link("http://lab.newgxu.cn");
        app.setPlatform(Platform.ANDROID);
        app.setRate(0);
        app.setRate_count(0);
        app.setTotal_install_count(0);

        version = new AppVersion();
        version.setApp(app);
        version.setUri("link...");
        version.setSize(10000023);
        version.setVersion("v111.buildid339");
        version.setUpdate_info("xxx bug fix");
    }

    @Test
    @DirtiesContext
    public void testCraete() throws Exception {
        appService.create(app, version);
        assertTrue(app.getId() > 0);
        assertEquals(app, version.getApp());
    }

    @Test(expected = Exception.class)
    @DirtiesContext
    public void testCreateWithin3Days() throws Exception {
        appService.create(app, version);
        app.setId(0);
        version.setId(0);
        appService.create(app, version);
    }

    @Test
    public void testFind() throws Exception {
        App a = appService.find(1);
        assertEquals(a.getPlatform().getClass(), Platform.class);
    }

    @Test
    public void testFindLatest() throws Exception {
        AppVersion latestVersion = appService.findLatestVersion(id);
        assertTrue(appService.history(id).get(0).equals(latestVersion));
    }

    @Test
    public void testMembersApps() throws Exception {
        List<App> apps = appService.apps(1);
        for (App a : apps) {
            l.debug("a:{}", a);
        }

    }

    @Test
    public void testHistroy() throws Exception {
        List<AppVersion> appVersions = appService.history(1);
        for (AppVersion v : appVersions) {
            assertEquals(v.getApp().getId(), 1);
            l.debug("v:{}", v);
        }

    }

    @Test
    public void testAppreciateApps() throws Exception {
        List<App> apps = appService.apps(AppService.APPRECIATE_TIMES, 10, 0, false);
        for (App a : apps) {
            l.debug("a:{}", a);
        }
    }

    @Test
    @DirtiesContext
    public void testUPdateApp() throws Exception {
        App a = appService.find(id);
        String newRequired = "new";
        appService.update(a.getId(), newRequired, a.getDescription(), a.getOutside_link(), a.getExtra());
        assertEquals(appService.find(a.getId()).getOs_requirement(), newRequired);
    }

    @Test
    @DirtiesContext
    public void testUpdateAppversion() throws Exception {
        AppVersion version = appService.findVersion(versionId);
        String newUpdateInfo = "new info";
        appService.update(versionId, newUpdateInfo, null);
        AppVersion _v = appService.findVersion(versionId);
        assertEquals(newUpdateInfo, _v.getUpdate_info());
    }

    @Test
    public void testFindaVersion() throws Exception {
        AppVersion version = appService.findVersion(versionId);
        assertNotNull(version);
        assertEquals(version.getId(), versionId);
        l.debug("v: {}", version);
    }

    @Test
    @DirtiesContext
    public void testUpgradle() throws Exception {
        App a = appService.find(id);
        appService.upgrade(a, version);
        assertTrue(version.getId() > 0);
    }

    @Test
    public void testPlatformapps() throws Exception {
        List<App> apps = appService.apps(Platform.ANDROID, 10, 0, false);
        for (App a : apps) {
            assertEquals(Platform.ANDROID, a.getPlatform());
        }

    }

    @Test
    public void testCategoryApps() throws Exception {
        List<App> apps = appService.apps(Category.GAME, 10, 0, false);
        for (App a : apps) {
            assertEquals(Category.GAME, a.getCategory());
        }

    }

    private static Logger l = LoggerFactory.getLogger(AppServiceTest.class);
}
