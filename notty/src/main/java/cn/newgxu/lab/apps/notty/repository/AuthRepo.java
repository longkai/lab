/*
 * The MIT License (MIT)
 * Copyright (c) 2013 newgxu.cn <the original author or authors>.
 * The software shall be used for good, not evil.
 */
package cn.newgxu.lab.apps.notty.repository;

import cn.newgxu.lab.apps.notty.entity.AuthorizedUser;
import cn.newgxu.lab.core.repository.CommonRepo;
import org.springframework.stereotype.Repository;

/**
 * 认证用户数据访问接口。
 *
 * @User longkai
 * @Date 13-3-28
 * @Mail im.longkai@gmail.com
 */
@Repository
public interface AuthRepo extends CommonRepo<AuthorizedUser> {

    String TABLE = "auth_users";
}
