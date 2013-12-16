/*
 * The MIT License (MIT)
 * Copyright (c) 2013 longkai(龙凯)
 * The software shall be used for good, not evil.
 */
package cn.newgxu.lab.store.repo;

import cn.newgxu.lab.store.entity.AppVersion;
import cn.newgxu.lab.support.CommonRepo;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 *
 * @User longkai
 * @Date 13-8-14
 * @Mail im.longkai@gmail.com
 */
@Repository
public interface AppVersionRepo extends CommonRepo<AppVersion> {

	String TABLE = "app_versions";

}
