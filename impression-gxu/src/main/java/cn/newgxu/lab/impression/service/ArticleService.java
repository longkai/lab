/*
 * The MIT License (MIT)
 * Copyright (c) 2013 longkai(龙凯)
 * The software shall be used for good, not evil.
 */
package cn.newgxu.lab.impression.service;

import cn.newgxu.lab.impression.entity.Article;
import cn.newgxu.lab.impression.entity.Category;

import java.util.List;

/**
 * The public API of the impression app.
 *
 * @User longkai
 * @Date 13-9-14
 * @Mail im.longkai@gmail.com
 */
public interface ArticleService {

	int LATEST = 1;
	int TOP = 2;
	int RECOMMEND = 3;

	void create(Article article);

	void update(Article article);

	void delete(long id);

	Article find(long id);

	void toggleBlock(long id);

	void toggleTop(long id);

	void toggleRecommended(long id);

	List<Article> articles(int type, int count, long offset, boolean append);

	List<Article> articles(Category category, int count, long offset, boolean append);

	List<Article> sync(long lastMillis, int count);

}
