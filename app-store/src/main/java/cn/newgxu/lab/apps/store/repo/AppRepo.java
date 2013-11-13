/*
 * The MIT License (MIT)
 * Copyright (c) 2013 longkai(龙凯)
 * The software shall be used for good, not evil.
 */
package cn.newgxu.lab.apps.store.repo;

import cn.newgxu.lab.apps.store.entity.App;
import cn.newgxu.lab.core.repository.CommonRepo;
import org.springframework.stereotype.Repository;

/**
 * app数据访问接口。
 *
 * @User longkai
 * @Date 13-8-13
 * @Mail im.longkai@gmail.com
 */
@Repository
public interface AppRepo extends CommonRepo<App> {

    String TABLE = "apps";
}
