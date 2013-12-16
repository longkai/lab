/*
 * The MIT License (MIT)
 * Copyright (c) 2013 longkai(龙凯)
 * The software shall be used for good, not evil.
 */
package cn.newgxu.lab.impression.repository;

import cn.newgxu.lab.impression.entity.Article;
import cn.newgxu.lab.repository.CommonRepo;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 *
 * @User longkai
 * @Date 13-9-14
 * @Mail im.longkai@gmail.com
 */
@Repository
public interface ArticleRepo extends CommonRepo<Article> {

	String TABLE = "articles";

}
