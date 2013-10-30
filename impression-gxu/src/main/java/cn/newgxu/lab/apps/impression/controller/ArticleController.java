/*
 * The MIT License (MIT)
 * Copyright (c) 2013 longkai(龙凯)
 * The software shall be used for good, not evil.
 */
package cn.newgxu.lab.apps.impression.controller;

import cn.newgxu.lab.apps.impression.entity.Article;
import cn.newgxu.lab.apps.impression.entity.Category;
import cn.newgxu.lab.apps.impression.entity.Layout;
import cn.newgxu.lab.apps.impression.service.ArticleService;
import cn.newgxu.lab.apps.impression.util.Constants;
import cn.newgxu.lab.apps.impression.util.LayoutProcessor;
import cn.newgxu.lab.util.Assert;
import cn.newgxu.lab.util.ViewConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @User longkai
 * @Date 13-9-15
 * @Mail im.longkai@gmail.com
 */
@Controller
@Scope("session")
public class ArticleController {

	@Inject
	private ArticleService articleService;

	@RequestMapping(value = "/impression/entrance", method = RequestMethod.GET)
	public String login() {
		return Constants.APP + "/login";
	}

	@RequestMapping(value = "/impression/index", method = RequestMethod.GET)
	public String index(HttpServletRequest request, Model model) {
		checkLogin(request);
		model.addAttribute(Constants.CATEGORIES, Category.values());
		model.addAttribute(Constants.LAYOUTS, Layout.values());
		model.addAttribute(Constants.ARTICLES, articleService
				.articles(ArticleService.LATEST, Constants.DEFAULT_ARTICLES_COUNT, 0, false));
		return Constants.APP + "/index";
	}

	@RequestMapping(value = "/impression/login", method = RequestMethod.GET, produces = ViewConstants.MEDIA_TYPE_JSON)
	@ResponseBody
	public String login(HttpSession session,
	                    @RequestParam("account") String account,
	                    @RequestParam("password") String password) {
		if (session.getAttribute(Constants.ADMIN) == null) {
			if (account.equals("impression_admin") && password.equals("123456")) {
				l.info("admin login successfully!");
				session.setAttribute(Constants.ADMIN, account); // set whatever
			} else {
				throw new RuntimeException("sorry, account or password not matches!");
			}
		}
		return ViewConstants.JSON_STATUS_OK;
	}

	@RequestMapping(value = "/impression/logout", method = RequestMethod.GET, produces = ViewConstants.MEDIA_TYPE_JSON)
	@ResponseBody
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}
		return ViewConstants.JSON_STATUS_OK;
	}

	@RequestMapping(value = "/articles/create", method = RequestMethod.POST, produces = ViewConstants.MEDIA_TYPE_JSON)
	@ResponseBody
	public Article create(HttpServletRequest request, Article article) {
		checkLogin(request);
		try {
			String tmp = article.getContent();
			LayoutProcessor.process(article, null);
			article.setContent(tmp);
		} catch (IOException e) {
			throw new RuntimeException("error when processing article layout!", e);
		}
		articleService.create(article);
		return article;
	}

	@RequestMapping(value = "/articles/create/file", method = RequestMethod.POST, produces = ViewConstants.MEDIA_TYPE_JSON)
	@ResponseBody
	public String create(HttpServletRequest request, Article article,
	                      @RequestParam("file") MultipartFile file) {
		checkLogin(request);
//		todo: check the layout here!
		try {
			String tmp = article.getContent();
			LayoutProcessor.process(article, file);
			article.setContent(tmp);
		} catch (IOException e) {
			throw new RuntimeException("error when processing article layout!", e);
		}
		articleService.create(article);
		return ViewConstants.JSON_STATUS_OK;
	}

	@RequestMapping(value = "/articles/{id}", method = RequestMethod.GET)
	public String article(Model model, @PathVariable("id") long id) {
		model.addAttribute(Constants.ARTICLE, articleService.find(id));
		return ViewConstants.BAD_REQUEST;
	}

	@RequestMapping(value = "/articles/{id}/update", method = RequestMethod.PUT, produces = ViewConstants.MEDIA_TYPE_JSON)
	@ResponseBody
	public String update(HttpServletRequest request, @PathVariable("id") long id, Article article/*,
	                     @RequestParam("file") MultipartFile file*/) {
//		todo allow file update!
		checkLogin(request);
		article.setId(id);
//		todo -> check the article' s layout!
//		try {
//			LayoutProcessor.process(article, file);
//		} catch (IOException e) {
//			throw new RuntimeException("error when processing article layout!", e);
//		}
		articleService.update(article);
		return ViewConstants.JSON_STATUS_OK;
	}

	@RequestMapping(value = "/articles/{id}/block", method = RequestMethod.PUT, produces = ViewConstants.MEDIA_TYPE_JSON)
	@ResponseBody
	public String toggleBlock(HttpServletRequest request, @PathVariable("id") long id) {
		checkLogin(request);
		articleService.toggleBlock(id);
		return ViewConstants.JSON_STATUS_OK;
	}

	@RequestMapping(value = "/articles/{id}/top", method = RequestMethod.PUT)
	@ResponseBody
	public String toggleTop(HttpServletRequest request, @PathVariable("id") long id) {
		checkLogin(request);
		articleService.toggleTop(id);
		return ViewConstants.JSON_STATUS_OK;
	}

	@RequestMapping(value = "/articles/{id}/recommend", method = RequestMethod.PUT, produces = ViewConstants.MEDIA_TYPE_JSON)
	@ResponseBody
	public String toggleRecommended(HttpServletRequest request, @PathVariable("id") long id) {
		checkLogin(request);
		articleService.toggleRecommended(id);
		return ViewConstants.JSON_STATUS_OK;
	}

	@RequestMapping(value = "/articles", method = RequestMethod.GET)
	public String aricles(Model model, @RequestParam("type") int type, @RequestParam("count") int count,
	                      @RequestParam(value = "offset", defaultValue = "0") long offset,
	                      @RequestParam(value = "append", defaultValue = "false") boolean append) {
		List<Article> articles = articleService.articles(type, count, offset, append);
		model.addAttribute(Constants.ARTICLES, articles);
		return ViewConstants.BAD_REQUEST;
	}

	@RequestMapping(value = "/articles/category/{category}", method = RequestMethod.GET)
	public String articles(Model model, @PathVariable("category") Category category, @RequestParam("count") int count,
	                       @RequestParam(value = "offset", defaultValue = "0") long offset,
	                       @RequestParam(value = "append", defaultValue = "false") boolean append) {
		model.addAttribute(Constants.ARTICLES, articleService.articles(category, count, offset, append));
		return ViewConstants.BAD_REQUEST;
	}

	@RequestMapping(value = "/articles/sync", method = RequestMethod.GET, produces = ViewConstants.MEDIA_TYPE_JSON)
	@ResponseBody
	public List<Article> sync(@RequestParam("last_millis") long last, @RequestParam("count") int count) {
		return articleService.sync(last, count);
	}

	private static void checkLogin(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		Assert.notNull("sorry, you need to login first!", session);
	}

	private static Logger l = LoggerFactory.getLogger(ArticleController.class);

}
