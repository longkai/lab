package cn.newgxu.lab.apps.impression.service;

import cn.newgxu.lab.apps.impression.entity.Article;
import cn.newgxu.lab.apps.impression.entity.Category;
import cn.newgxu.lab.apps.impression.entity.Layout;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 *
 * @User longkai
 * @Date 13-9-14
 * @Mail im.longkai@gmail.com
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring-test.xml")
public class ArticleServiceTest {

	@Inject
	private ArticleService articleService;

	private Article article;

	private static final int TOTAL = 5;
	private int id;

	@Before
	public void setUp() throws Exception {
		article = new Article();
		article.setAuthor_about("author about!");
		article.setAuthor_name("aurhor name!");
		article.setCategory(Category.LITERATURE);
		article.setContent("{\"text\":\"test text!\"}");
		article.setTitle("title");
		article.setLayout(Layout.TEXT);
		article.setAuthor_contact("110");

		id = (int) (Math.random() * TOTAL) + 1;
	}

	@Test
	@DirtiesContext
	public void testCreate() throws Exception {
		articleService.create(article);
		assertTrue(article.getId() > 0);
	}

	@Test
	@DirtiesContext
	public void testUpdate() throws Exception {
		Article a = articleService.find(id);
		String s = "xxx";
		a.setTitle(s);
		articleService.update(a);
		assertEquals(s, articleService.find(id).getTitle());
	}

	@Test(expected = RuntimeException.class)
	@DirtiesContext
	public void testDelete() throws Exception {
		articleService.create(article);
		articleService.delete(article.getId());
		articleService.find(article.getId());
	}

	@Test
	public void testFind() throws Exception {

	}

	@Test
	@DirtiesContext
	public void testToggleBlock() throws Exception {
		Article a = articleService.find(id);
		articleService.toggleBlock(id);
		assertTrue(articleService.find(id).isBlocked() != a.isBlocked());

	}

	@Test
	@DirtiesContext
	public void testToggleTop() throws Exception {
		Article a = articleService.find(id);
		articleService.toggleTop(id);
		assertTrue(articleService.find(id).isTop() != a.isTop());
	}

	@Test
	@DirtiesContext
	public void testToggleRecommended() throws Exception {
		Article a = articleService.find(id);
		articleService.toggleRecommended(id);
		assertTrue(articleService.find(id).isRecommended() != a.isRecommended());
	}

	@Test
	public void testArticlesWithType() throws Exception {
		int type = (int) (Math.random() * 3) + 1;
		List<Article> articles = articleService.articles(type, TOTAL, 0, false);
		switch (type) {
			case ArticleService.LATEST:
				for (int i = articles.size() - 1; i > 0; i--) {
					articles.get(i).getAdded_date().after(articles.get(i - 1).getAdded_date());
				}
				break;
			case ArticleService.RECOMMEND:
				for (Article a : articles) {
					assertTrue(a.isRecommended());
				}
				break;
			case ArticleService.TOP:
				for (Article a : articles) {
					assertTrue(a.isTop());
				}
				break;
			default:
				break;
		}
	}

	@Test
	public void testArticlesWithCategory() throws Exception {
		Category[] categories = Category.values();
		int random = (int) (Math.random() * categories.length);
		Category category = categories[random];
		List<Article> articles = articleService.articles(category, TOTAL, 0, false);
		for (Article a : articles) {
			assertEquals(a.getCategory(), category);
		}
	}

	@Test
	public void testSync() throws Exception {
		Article a = articleService.find(id);
		long lastSyncMillis = a.getAdded_date().getTime();
		List<Article> articles = articleService.sync(lastSyncMillis, TOTAL);
		for (Article _a : articles) {
			assertTrue(_a.getAdded_date().getTime() > lastSyncMillis
					|| (_a.getLast_modified_date() != null ? _a.getLast_modified_date().getTime() > lastSyncMillis : true));
		}
	}
}
