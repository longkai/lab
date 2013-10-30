/*
 * The MIT License (MIT)
 * Copyright (c) 2013 longkai(龙凯)
 * The software shall be used for good, not evil.
 */
package cn.newgxu.lab.apps.impression.repository;

import cn.newgxu.lab.apps.impression.entity.Article;
import cn.newgxu.lab.core.repository.CommonRepo;

/**
 * Created with IntelliJ IDEA.
 *
 * @User longkai
 * @Date 13-9-14
 * @Mail im.longkai@gmail.com
 */
public interface ArticleRepo extends CommonRepo<Article> {

	String TABLE = "articles";

}
