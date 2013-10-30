/*
 * The MIT License (MIT)
 * Copyright (c) 2013 longkai(龙凯)
 * The software shall be used for good, not evil.
 */
package cn.newgxu.lab.apps.store.repo;

import cn.newgxu.lab.apps.store.entity.Comment;
import cn.newgxu.lab.core.repository.CommonRepo;

/**
 * Created with IntelliJ IDEA.
 *
 * @User longkai
 * @Date 13-8-14
 * @Mail im.longkai@gmail.com
 */
public interface CommentRepo extends CommonRepo<Comment> {

    String TABLE = "app_comments";

}
