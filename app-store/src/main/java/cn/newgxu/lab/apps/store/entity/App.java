/*
 * The MIT License (MIT)
 * Copyright (c) 2013 longkai(龙凯)
 * The software shall be used for good, not evil.
 */
package cn.newgxu.lab.apps.store.entity;

import cn.newgxu.lab.core.member.Member;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Date;

/**
 * 应用程序。
 *
 * @User longkai
 * @Date 13-8-12
 * @Mail im.longkai@gmail.com
 */
@Alias("app")
public class App implements Serializable {

    private long id;
    private String name;
    private Member author;
    private Category category;
    private Platform platform;
    private String os_requirement;
    private String description;
    private long total_install_count;
    /** 描述的图片，可以为多张，以某个符号区分开来即可 */
    private String icon;
    /** 外链，可选 */
    private String outside_link;
    /** 总评分 */
    private long rate;
    /** 总评价次数 */
    private int rate_count;
    /** 额外信息，可选 */
    private String extra;
    /** 创建日期 */
    private Date created_date;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Member getAuthor() {
        return author;
    }

    public void setAuthor(Member author) {
        this.author = author;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public String getOs_requirement() {
        return os_requirement;
    }

    public void setOs_requirement(String os_requirement) {
        this.os_requirement = os_requirement;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getTotal_install_count() {
        return total_install_count;
    }

    public void setTotal_install_count(long total_install_count) {
        this.total_install_count = total_install_count;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getOutside_link() {
        return outside_link;
    }

    public void setOutside_link(String outside_link) {
        this.outside_link = outside_link;
    }

    public long getRate() {
        return rate;
    }

    public void setRate(long rate) {
        this.rate = rate;
    }

    public int getRate_count() {
        return rate_count;
    }

    public void setRate_count(int rate_count) {
        this.rate_count = rate_count;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public Date getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        App app = (App) o;

        if (id != app.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "App{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", author=" + author +
                ", category=" + category +
                ", platform=" + platform +
                ", os_requirement='" + os_requirement + '\'' +
                ", description='" + description + '\'' +
                ", total_install_count=" + total_install_count +
                ", icon='" + icon + '\'' +
                ", outside_link='" + outside_link + '\'' +
                ", rate=" + rate +
                ", rate_count=" + rate_count +
                ", extra='" + extra + '\'' +
                ", created_date=" + created_date +
                '}';
    }
}
