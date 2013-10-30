/*
 * The MIT License (MIT)
 * Copyright (c) 2013 longkai(龙凯)
 * The software shall be used for good, not evil.
 */
package cn.newgxu.lab.apps.store.repo;

import cn.newgxu.lab.apps.store.entity.AppVersion;
import cn.newgxu.lab.core.repository.CommonRepo;

/**
 * Created with IntelliJ IDEA.
 *
 * @User longkai
 * @Date 13-8-14
 * @Mail im.longkai@gmail.com
 */
public interface AppVersionRepo extends CommonRepo<AppVersion> {

    String TABLE = "app_versions";

}
