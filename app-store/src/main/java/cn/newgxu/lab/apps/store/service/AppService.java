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

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @User longkai
 * @Date 13-8-13
 * @Mail im.longkai@gmail.com
 */
public interface AppService {

    int DEFAULT = 1;
    int INSTALL_TIMES = 2;
    int APPRECIATE_TIMES = 3;

    void create(App app, AppVersion version);

    App find(long appId);

    AppVersion findVersion(long versionId);

    AppVersion findLatestVersion(long appId);

    long total();

    AppVersion download(long appId, long versionId);

    void update(long appId, String os_requirement, String description, String outside_link, String extra);

    /**
     * 给应用添加展示的图片，注意，旧的图片（如果有）将会被删除
     *
     * @param appId
     * @param iconUri
     */
    void changeIcon(long appId, String iconUri);

    /**
     * 升级
     *
     * @param app
     * @param appVersion
     */
    void upgrade(App app, AppVersion appVersion);

    /**
     * 修改版本的信息。
     *
     * @param appVersionId
     * @param updateInfo
     * @param extra
     */
    void update(long appVersionId, String updateInfo, String extra);

    /**
     * 查找某个作者的所有作品。
     *
     * @param memberId
     * @return
     */
    List<App> apps(long memberId);

    /**
     * 查找某个类别下的应用
     *
     * @param category
     * @param count
     * @param offset   小于等于0表示不加载更多或者更新，直接从最新的开始查找
     * @param append   是否加载更多，否则刷新一下
     * @return apps
     */
    List<App> apps(Category category, int count, long offset, boolean append);

    /**
     * 查找某个平台下的应用
     *
     * @param platform
     * @param count
     * @param offset   小于等于0表示不加载更多或者更新，直接从最新的开始查找
     * @param append   是否加载更多，否则刷新一下
     * @return apps
     */
    List<App> apps(Platform platform, int count, long offset, boolean append);

    /**
     * 自定义类型查找，目前的选项是1：默认（时间逆序）2：安装次数，3：好评次数，都是从高到低
     *
     * @param type
     * @param count
     * @param offset 小于等于0表示不加载更多或者更新，直接从最新的开始查找
     * @param append 是否加载更多，否则刷新一下
     * @return apps
     */
    List<App> apps(int type, int count, long offset, boolean append);

    /**
     * 某个app的历史版本
     *
     *
     * @param appId
     * @return
     */
    List<AppVersion> history(long appId);

    /**
     * 这个考虑到实际情况，没有设置分页，分类等，后面的人可以考虑一下。
     *
     * @param keywords
     * @return apps
     */
    List<App> search(String keywords);

}
