/*
 * The MIT License (MIT)
 * Copyright (c) 2013 longkai(龙凯)
 * The software shall be used for good, not evil.
 */
package cn.newgxu.lab.apps.store.service.impl;

import cn.newgxu.lab.apps.store.entity.App;
import cn.newgxu.lab.apps.store.entity.AppVersion;
import cn.newgxu.lab.apps.store.entity.Category;
import cn.newgxu.lab.apps.store.entity.Platform;
import cn.newgxu.lab.apps.store.repo.AppRepo;
import cn.newgxu.lab.apps.store.repo.AppVersionRepo;
import cn.newgxu.lab.apps.store.service.AppService;
import cn.newgxu.lab.util.Assert;
import cn.newgxu.lab.util.SQLUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

import static cn.newgxu.lab.util.SQLUtils.ID_DESC_ORDER_BY;
import static cn.newgxu.lab.util.SQLUtils.set;

/**
 * Created with IntelliJ IDEA.
 *
 * @User longkai
 * @Date 13-8-13
 * @Mail im.longkai@gmail.com
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Throwable.class)
public class AppServiceImpl implements AppService {

    private static final int DEFAULT_SEARCH_COUNT = 100;
    private static final long NEW_APP_LIMIT_TIME_MILLIS = 3 * 24 * 60 * 60 * 1000L;

    @Inject
    private AppRepo appRepo;

    @Inject
    private AppVersionRepo appVersionRepo;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void create(App app, AppVersion version) {
        checkAppMetaData(app);

        checkVersionMetaData(version);

        // in order to avoid evil action, we do the below..
        // in 3 days, can only create 1 new app
        Date now = new Date();
        Date threeDayAgo = new Date(now.getTime() - NEW_APP_LIMIT_TIME_MILLIS);

        long count = appRepo.count(null, SQLUtils.where("uid=? AND created_date>?",
                app.getAuthor().getId(), threeDayAgo));
        if (count > 0) {
            throw new RuntimeException("We' re sorry, you have created a new app within the last 3 days, please wait!");
        }

        app.setCreated_date(now);
        appRepo.insert(app);
        version.setApp(app);
        version.setUpdate_date(now);
        appVersionRepo.insert(version);

        l.info("a new app come in! name:{}, version: {}", app.getName(), version.getVersion());
    }

    @Override
    public App find(long id) {
        App app = appRepo.find(id);
        Assert.notNull("The app you find is not exists!", app);
        return app;
    }

    @Override
    public AppVersion findVersion(long versionId) {
        AppVersion version = appVersionRepo.find(versionId);
        Assert.notNull("we' re sorry, the version you find is not exists!", version);
        return version;
    }

    @Override
    public AppVersion findLatestVersion(long appId) {
        return appVersionRepo.findOne(null, null,
                SQLUtils.where("V.id = (SELECT max(id) FROM " + AppVersionRepo.TABLE + " WHERE app_id=" + appId + ")"));
    }

    @Override
    public long total() {
        return appRepo.count(null, null);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public AppVersion download(long appId, long versionId) {
        appVersionRepo.update("install_count=install_count+1", "id=" + versionId);
        appRepo.update("total_install_count=total_install_count+1", "id=" + appId);
        l.info("app: {} download++", appId);
        return appVersionRepo.find(versionId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void update(long appId, String os_requirement, String description, String outside_link, String extra) {
        int i = appRepo.update(set(new String[]{"os_requirement", "description", "outside_link", "extra"},
                new Object[]{os_requirement, description, outside_link, extra}), "id=" + appId);
        l.info("update app id={} information successfully! alter count: {}", appId, i);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void changeIcon(long appId, String iconUri) {
        int i = appRepo.update(set(new String[]{"icon"}, new String[]{iconUri}), "id=" + appId);
        l.info("update app id={} icon successfully! alter count: {}", appId, i);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void upgrade(App app, AppVersion appVersion) {
        checkAppMetaData(app);
        checkVersionMetaData(appVersion);

        appVersion.setUpdate_date(new Date());
        appVersion.setApp(app);
        appVersionRepo.insert(appVersion);

        l.info("app: {} upgrade succefully! appversion: {}", app.getName(), appVersion);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void update(long appVersionId, String updateInfo, String extra) {
        int i = appVersionRepo.update(SQLUtils.set(new String[]{"update_info", "extra"},
                new Object[]{updateInfo, extra}), "id=" + appVersionId);
        l.info("update appversion info id={} successfully! alter count: {}", appVersionId, i);
    }

    @Override
    public List<App> apps(long memberId) {
        return appRepo.query("*", null, "uid=" + memberId, null, null, ID_DESC_ORDER_BY, null);
    }

    @Override
    public List<App> apps(Category category, int count, long offset, boolean append) {
        String where = SQLUtils.where("category=?", category);
        if (offset > 0) {
            where += " AND id" + (append ? "<" : ">") + offset;
        }
        return appRepo.query("*", null, where, null, null, ID_DESC_ORDER_BY, "" + count);
    }

    @Override
    public List<App> apps(Platform platform, int count, long offset, boolean append) {
        String where = SQLUtils.where("platform=?", platform);
        if (offset > 0) {
            where += " AND id" + (append ? "<" : ">") + offset;
        }
        return appRepo.query("*", null, where, null, null, ID_DESC_ORDER_BY, "" + count);
    }

    @Override
    public List<App> apps(int type, int count, long offset, boolean append) {
        Assert.between("apps count must be 1-100 at a time!", count, 1, 100);
        List<App> apps;
        switch (type) {
            case DEFAULT:
                apps = appRepo.query("*", null, null, null, null, ID_DESC_ORDER_BY, "" + count);
                break;
            case INSTALL_TIMES:
                apps = appRepo.query("*", null, null, null, null, "total_install_count DESC", "" + count);
                break;
            case APPRECIATE_TIMES:
                apps = appRepo.query("*", null, "A.id in (SELECT app_id FROM app_comments ORDER BY useful_count DESC)",
                        null, null, ID_DESC_ORDER_BY, "" + count);
                break;
            default:
                throw new IllegalArgumentException("not support the type[" + type + "]");
        }
        return apps;
    }

    @Override
    public List<AppVersion> history(long appId) {
//这里会有一个问题，就是级联的
        return appVersionRepo.query("*", null, "V.app_id=" + appId, null, null, ID_DESC_ORDER_BY, null);
    }

    @Override
    public List<App> search(String keywords) {
        return  appRepo.query("*", null, SQLUtils.like("A.name lime '%?%' OR A.decription like '%?%'", keywords, keywords),
                null, null, ID_DESC_ORDER_BY, "" + DEFAULT_SEARCH_COUNT);
    }

    private static void checkVersionMetaData(AppVersion version) {
        Assert.notEmpty("update info cannot be empty!", version.getUpdate_info());
        Assert.notEmpty("version number cannot be empty!", version.getVersion());
    }

    private static void checkAppMetaData(App app) {
        Assert.notEmpty("description cannot be empty!", app.getDescription());
        Assert.notEmpty("app name cannot be empty!", app.getName());
        Assert.notEmpty("os requirement cannot be empty!", app.getOs_requirement());
        Assert.notNull("author cannot be null!", app.getAuthor());
        Assert.notNull("category cannot be null!", app.getCategory());
        Assert.notNull("platform cannot be null!", app.getPlatform());
    }
    private static Logger l = LoggerFactory.getLogger(AppServiceImpl.class);

}
