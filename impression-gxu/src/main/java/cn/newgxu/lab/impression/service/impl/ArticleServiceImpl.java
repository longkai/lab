/*
 * The MIT License (MIT)
 * Copyright (c) 2013 longkai(龙凯)
 * The software shall be used for good, not evil.
 */
package cn.newgxu.lab.impression.service.impl;

import cn.newgxu.lab.impression.entity.Article;
import cn.newgxu.lab.impression.entity.Category;
import cn.newgxu.lab.impression.repository.ArticleRepo;
import cn.newgxu.lab.impression.service.ArticleService;
import cn.newgxu.lab.util.Assert;
import cn.newgxu.lab.util.SQLUtils;
import cn.newgxu.lab.util.TextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @User longkai
 * @Date 13-9-14
 * @Mail im.longkai@gmail.com
 */
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Throwable.class)
@Service
public class ArticleServiceImpl implements ArticleService {

	@Inject
	private ArticleRepo articleRepo;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void create(Article article) {
		if (article.getId() != 0) {
			throw new IllegalArgumentException("sorry, fail to create new article -> argument error for the new article already has id!");
		}
		check(article);
		
		article.setAdded_date(new Date());
		articleRepo.insert(article);
		l.info("create new article successfully! article: {}", article);
	}

	private void check(Article article) {
		Assert.notEmpty("article' s title cannot be empty!", article.getTitle());
		Assert.notNull("article' s category cannot be null!", article.getCategory());
		Assert.notNull("article' s layout cannot be null!", article.getLayout());
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void update(Article article) {
		check(article);
		article.setLast_modified_date(new Date());
		articleRepo.update(SQLUtils.set(
				new String[]{"title", "content", "category", "layout", "last_modified_date"},
				new Object[]{article.getTitle(), article.getContent(), article.getCategory(),
						article.getLayout(), article.getLast_modified_date()}),
				"id=" + article.getId());
		l.info("update article successfully! article: {}", article);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void delete(long id) {
		articleRepo.delete(ArticleRepo.TABLE, "id=" + id);
		l.info("delete article with id: {} successfully!", id);
	}

	@Override
	public Article find(long id) {
		Article article = articleRepo.find(id);
		Assert.notNull("sorry, the article you find is not found! please try again later or contact yws!", article);
		return article;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void toggleBlock(long id) {
		Article article = find(id);
		article.setBlocked(!article.isBlocked());
		article.setLast_modified_date(new Date());
		articleRepo.update(SQLUtils.set(new String[]{"blocked", "last_modified_date"},
				new Object[]{article.isBlocked(), article.getLast_modified_date()}), "id=" + id);
		l.info("toggle article' s blocked property to {} successfully!", article.isBlocked());
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void toggleTop(long id) {
		Article article = find(id);
		article.setTop(!article.isTop());
		article.setLast_modified_date(new Date());
		articleRepo.update(SQLUtils.set(new String[]{"top", "last_modified_date"},
				new Object[]{article.isTop(), article.getLast_modified_date()}), "id=" + id);
		l.info("toggle article' s top property to {} successfully!", article.isBlocked());
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void toggleRecommended(long id) {
		Article article = find(id);
		article.setRecommended(!article.isRecommended());
		article.setLast_modified_date(new Date());
		articleRepo.update(SQLUtils.set(new String[]{"recommended", "last_modified_date"},
				new Object[]{article.isRecommended(), article.getLast_modified_date()}), "id=" + id);
		l.info("toggle article' s recommended property to {} successfully!", article.isBlocked());
	}

	@Override
	public List<Article> articles(int type, int count, long offset, boolean append) {
		check(count);
		List<Article> articles;
		String extra = "1=1 ";
		if (offset > 0) {
			extra = TextUtils.replace("AND id??", append ? "<" : ">", offset + "");
		}
		switch (type) {
			case LATEST:
				articles = articleRepo.query(null, ArticleRepo.TABLE, extra, null, null, SQLUtils.ID_DESC_ORDER_BY, "" + count);
				break;
			case RECOMMEND:
				articles = articleRepo.query(null, ArticleRepo.TABLE, SQLUtils.where("recommended=? AND " + extra, true), null, null, SQLUtils.ID_DESC_ORDER_BY, "" + count);
				break;
			case TOP:
				articles = articleRepo.query(null, ArticleRepo.TABLE, SQLUtils.where("top=? AND " + extra, true), null, null, SQLUtils.ID_DESC_ORDER_BY, "" + count);
				break;
			default:
				throw new IllegalArgumentException("sorry, not this type -> " + type);
		}
		return articles;
	}

	private void check(int count) {
		if (count < 1 || count > 50) {
			throw new IllegalArgumentException("sorry, the count of the articles' size is overflow or not valid! the valid range is 1-50!");
		}
	}

	@Override
	public List<Article> articles(Category category, int count, long offset, boolean append) {
		check(count);
		String extra = "1=1 ";
		if (offset > 0) {
			extra = TextUtils.replace("AND id??", append ? "<" : ">", offset + "");
		}
//		no need to use enum switch!
//		switch (category) {
//			case LITERATURE:
//		}
		return articleRepo.query(null, ArticleRepo.TABLE, SQLUtils.where("category=? AND " + extra, category), null, null, SQLUtils.ID_DESC_ORDER_BY, "" + count);
	}

	@Override
	public List<Article> sync(long lastMillis, int count) {
		check(count);
		Date last = new Date(lastMillis);
		String where = SQLUtils.where("added_date>? OR last_modified_date>?", last, last);
		return articleRepo.query(null, ArticleRepo.TABLE, where, null, null, SQLUtils.ID_DESC_ORDER_BY, "" + count);
	}

	private static Logger l = LoggerFactory.getLogger(ArticleServiceImpl.class);
}
