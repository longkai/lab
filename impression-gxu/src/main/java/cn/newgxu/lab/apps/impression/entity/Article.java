/*
 * The MIT License (MIT)
 * Copyright (c) 2013 longkai(龙凯)
 * The software shall be used for good, not evil.
 */
package cn.newgxu.lab.apps.impression.entity;

import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Date;

/**
 * The article of the app.
 *
 * @User longkai
 * @Date 13-9-14
 * @Mail im.longkai@gmail.com
 */
@Alias("article")
public class Article implements Serializable {

	private long id;
	private Category category = Category.LITERATURE;
	private Layout layout = Layout.TEXT;
	private String title;
	private String content;
	private boolean recommended;
	private boolean top;
	private boolean blocked;
//	private String gallery;
//	private String gallery_description;
	private Date added_date;
	private Date last_modified_date;
//	for some reason, the author here is not like the domain model, so just put here
	private String author_name;
	private String author_contact;
	private String author_about;

	public long getId() {

		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Layout getLayout() {
		return layout;
	}

	public void setLayout(Layout layout) {
		this.layout = layout;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean isRecommended() {
		return recommended;
	}

	public void setRecommended(boolean recommended) {
		this.recommended = recommended;
	}

	public boolean isTop() {
		return top;
	}

	public void setTop(boolean top) {
		this.top = top;
	}

	public boolean isBlocked() {
		return blocked;
	}

	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}

	public Date getAdded_date() {
		return added_date;
	}

	public void setAdded_date(Date added_date) {
		this.added_date = added_date;
	}

	public Date getLast_modified_date() {
		return last_modified_date;
	}

	public void setLast_modified_date(Date last_modified_date) {
		this.last_modified_date = last_modified_date;
	}

	public String getAuthor_name() {
		return author_name;
	}

	public void setAuthor_name(String author_name) {
		this.author_name = author_name;
	}

	public String getAuthor_contact() {
		return author_contact;
	}

	public void setAuthor_contact(String author_contact) {
		this.author_contact = author_contact;
	}

	public String getAuthor_about() {
		return author_about;
	}

	public void setAuthor_about(String author_about) {
		this.author_about = author_about;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Article article = (Article) o;

		if (id != article.id) return false;

		return true;
	}

	@Override
	public int hashCode() {
		return (int) (id ^ (id >>> 32));
	}

	@Override
	public String toString() {
		return "Article{" +
				"id=" + id +
				", category=" + category +
				", layout=" + layout +
				", title='" + title + '\'' +
				", content='" + content + '\'' +
				", recommended=" + recommended +
				", top=" + top +
				", blocked=" + blocked +
				", added_date=" + added_date +
				", last_modified_date=" + last_modified_date +
				", author_name='" + author_name + '\'' +
				", author_contact='" + author_contact + '\'' +
				", author_about='" + author_about + '\'' +
				'}';
	}
}
