/*
 * The MIT License (MIT)
 * Copyright (c) 2013 newgxu.cn <the original author or authors>.
 * The software shall be used for good, not evil.
 */
package cn.newgxu.lab.support.member;

import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>参与实验室的成员，在实验室上具有特权（校园信息除外）</p>
 * <p>注意，成员必须是雨无声bbs（或者雨无声其他服务）的用户，so，他们的验证需要远程ajax操作</p>
 *
 * @User longkai
 * @Date 13-8-12
 * @Mail im.longkai@gmail.com
 */
@Alias("member")
public class Member implements Serializable {

	/**
	 * 内部使用
	 */
	private long id;
	/**
	 * 用户来自的那个地方的id，比如bbs上的user id
	 */
	private long uid;
	/**
	 * 来自哪里，比如说bbs，获取雨无声其他服务，默认0，来自bbs
	 */
	private int comefrom;
	/**
	 * 显示昵称
	 */
	private String nick;
	/**
	 * 个人标签
	 */
	private String label;
	/**
	 * 是否有效，默认有效，暂未使用
	 */
	private boolean invalid;
	/**
	 * 联系方式
	 */
	private String contact;
	/**
	 * 自我介绍
	 */
	private String description;
	/**
	 * 参加时间
	 */
	private Date participate_date;
	private Date last_modified_date;

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

	public int getComefrom() {
		return comefrom;
	}

	public void setComefrom(int comefrom) {
		this.comefrom = comefrom;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public boolean isInvalid() {
		return invalid;
	}

	public void setInvalid(boolean invalid) {
		this.invalid = invalid;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getParticipate_date() {
		return participate_date;
	}

	public void setParticipate_date(Date participate_date) {
		this.participate_date = participate_date;
	}

	public Date getLast_modified_date() {
		return last_modified_date;
	}

	public void setLast_modified_date(Date last_modified_date) {
		this.last_modified_date = last_modified_date;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Member member = (Member) o;

		if (id != member.id) return false;

		return true;
	}

	@Override
	public int hashCode() {
		return (int) (id ^ (id >>> 32));
	}

	@Override
	public String toString() {
		return "Member{" +
				"id=" + id +
				", uid=" + uid +
				", comefrom=" + comefrom +
				", nick='" + nick + '\'' +
				", label='" + label + '\'' +
				", invalid=" + invalid +
				", contact='" + contact + '\'' +
				", description='" + description + '\'' +
				", participate_date=" + participate_date +
				", last_modified_date=" + last_modified_date +
				'}';
	}
}
