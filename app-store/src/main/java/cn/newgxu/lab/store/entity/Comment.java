/*
 * The MIT License (MIT)
 * Copyright (c) 2013 longkai(龙凯)
 * The software shall be used for good, not evil.
 */
package cn.newgxu.lab.store.entity;

import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户对app的评论以及评分。
 *
 * @User longkai
 * @Date 13-8-12
 * @Mail im.longkai@gmail.com
 */
@Alias("app_comment")
public class Comment implements Serializable {

	private long id;
	/**
	 * 来自其他地方的id
	 */
	private long uid;
	/**
	 * 来自其他地方的名字
	 */
	private String nick;
	private App app;
	private int rate;
	private int useful_count;
	private String comment;
	private Date rate_date;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public App getApp() {
		return app;
	}

	public void setApp(App app) {
		this.app = app;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	public int getUseful_count() {
		return useful_count;
	}

	public void setUseful_count(int useful_count) {
		this.useful_count = useful_count;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getRate_date() {
		return rate_date;
	}

	public void setRate_date(Date rate_date) {
		this.rate_date = rate_date;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Comment comment = (Comment) o;

		if (id != comment.id) return false;

		return true;
	}

	@Override
	public int hashCode() {
		return (int) (id ^ (id >>> 32));
	}

	@Override
	public String toString() {
		return "Comment{" +
				"id=" + id +
				", uid=" + uid +
				", nick='" + nick + '\'' +
				", app=" + app +
				", rate=" + rate +
				", useful_count=" + useful_count +
				", comment='" + comment + '\'' +
				", rate_date=" + rate_date +
				'}';
	}
}
