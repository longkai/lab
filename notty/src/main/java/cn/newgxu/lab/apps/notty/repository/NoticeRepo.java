/*
 * The MIT License (MIT)
 * Copyright (c) 2013 newgxu.cn <the original author or authors>.
 * The software shall be used for good, not evil.
 */
package cn.newgxu.lab.apps.notty.repository;

import cn.newgxu.lab.apps.notty.entity.Notice;
import cn.newgxu.lab.core.repository.CommonRepo;

/**
 * 校园信息的数据访问接口。
 *
 * @User longkai
 * @Date 13-3-28
 * @Mail im.longkai@gmail.com
 */
public interface NoticeRepo extends CommonRepo<Notice> {

    String TABLE = "notices";
}
