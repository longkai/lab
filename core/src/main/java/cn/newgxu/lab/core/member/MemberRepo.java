/*
 * The MIT License (MIT)
 * Copyright (c) 2013 longkai(龙凯)
 * The software shall be used for good, not evil.
 */
package cn.newgxu.lab.core.member;

import cn.newgxu.lab.core.repository.CommonRepo;
import org.springframework.stereotype.Repository;

/**
 * 实验室成员的数据仓库，
 *
 * @User longkai
 * @Date 13-8-13
 * @Mail im.longkai@gmail.com
 */
@Repository
public interface MemberRepo extends CommonRepo<Member> {

    String TABLE = "members";

}
