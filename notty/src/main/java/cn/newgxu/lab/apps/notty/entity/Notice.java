/*
 * The MIT License (MIT)
 * Copyright (c) 2013 newgxu.cn <the original author or authors>.
 * The software shall be used for good, not evil.
 */
package cn.newgxu.lab.apps.notty.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Date;

/**
 * 校园信息。
 *
 * @User longkai
 * @Date 13-3-28
 * @Mail im.longkai@gmail.com
 */
@Alias("notice")
public class Notice implements Serializable {
    private long			id;

    private String			title;

    private String			content;

    @JsonProperty("click_times")
    private long			clickTimes;

    @JsonProperty("add_date")
    private Date			addDate;

    @JsonProperty("last_modified_date")
    private Date			lastModifiedDate;

    /** 是否被屏蔽 */
    @JsonIgnore
    private boolean			blocked;

    private AuthorizedUser  author;

    /** 上传文档的存放路径 */
    @JsonProperty("doc_url")
    private String			docUrl;

    @JsonProperty("doc_name")
    private String			docName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public long getClickTimes() {
        return clickTimes;
    }

    public void setClickTimes(long clickTimes) {
        this.clickTimes = clickTimes;
    }

    public Date getAddDate() {
        return addDate;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public AuthorizedUser getAuthor() {
        return author;
    }

    public void setAuthor(AuthorizedUser author) {
        this.author = author;
    }

    public String getDocUrl() {
        return docUrl;
    }

    public void setDocUrl(String docUrl) {
        this.docUrl = docUrl;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Notice other = (Notice) obj;
        if (id != other.id)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Notice{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", clickTimes=" + clickTimes +
                ", addDate=" + addDate +
                ", lastModifiedDate=" + lastModifiedDate +
                ", blocked=" + blocked +
                ", author=" + author +
                ", docUrl='" + docUrl + '\'' +
                ", docName='" + docName + '\'' +
                '}';
    }
}
