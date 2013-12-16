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
 * 某个应用的一个版本。
 *
 * @User longkai
 * @Date 13-8-12
 * @Mail im.longkai@gmail.com
 */
@Alias("app_version")
public class AppVersion implements Serializable {

	private long id;
	/**
	 * 指向的应用
	 */
	private App app;
	private String version;
	private Date update_date;
	private long install_count;
	private long size;
	private String update_info;
	private String uri;
	private String extra;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public App getApp() {
		return app;
	}

	public void setApp(App app) {
		this.app = app;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Date getUpdate_date() {
		return update_date;
	}

	public void setUpdate_date(Date update_date) {
		this.update_date = update_date;
	}

	public long getInstall_count() {
		return install_count;
	}

	public void setInstall_count(long install_count) {
		this.install_count = install_count;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public String getUpdate_info() {
		return update_info;
	}

	public void setUpdate_info(String update_info) {
		this.update_info = update_info;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		AppVersion that = (AppVersion) o;

		if (id != that.id) return false;

		return true;
	}

	@Override
	public int hashCode() {
		return (int) (id ^ (id >>> 32));
	}

	@Override
	public String toString() {
		return "AppVersion{" +
				"id=" + id +
				", app=" + app +
				", version='" + version + '\'' +
				", update_date=" + update_date +
				", install_count=" + install_count +
				", size=" + size +
				", update_info='" + update_info + '\'' +
				", uri='" + uri + '\'' +
				", extra='" + extra + '\'' +
				'}';
	}
}
