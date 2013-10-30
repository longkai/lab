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
 * 校园信息认证用户。
 *
 * @User longkai
 * @Date 13-3-28
 * @Mail im.longkai@gmail.com
 */
@Alias("auth_user")
public class AuthorizedUser implements Serializable {

    private long		id;

	@JsonIgnore
	private boolean admin;
//    private AccountType	type	= AccountType.DEFAULT;

    @JsonProperty("authed_name")
    private String		authorizedName;

    /** 组织，单位，或者个人 */
    private String		org;
    /** 简要信息（可选） */
    private String		about;
    /** 联系方式（可选） */
    private String		contact;

    @JsonProperty("join_date")
    private Date        joinDate;

    /** 账号是否被封 */
    @JsonIgnore
    private boolean		blocked;

    //	@JsonIgnore
    private String		account;
    @JsonIgnore
    private String		password;

    @JsonIgnore
    private String		lastLoginIP;

    @JsonIgnore
    private Date        lastLoginDate;

    @JsonProperty("last_modified_date")
    private Date        lastModifiedDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	//    public AccountType getType() {
//        return type;
//    }
//
//    public void setType(AccountType type) {
//        this.type = type;
//    }

    public String getAuthorizedName() {
        return authorizedName;
    }

    public void setAuthorizedName(String authorizedName) {
        this.authorizedName = authorizedName;
    }

    public String getOrg() {
        return org;
    }

    public void setOrg(String org) {
        this.org = org;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastLoginIP() {
        return lastLoginIP;
    }

    public void setLastLoginIP(String lastLoginIP) {
        this.lastLoginIP = lastLoginIP;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((account == null) ? 0 : account.hashCode());
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
        AuthorizedUser other = (AuthorizedUser) obj;
        if (account == null) {
            if (other.account != null)
                return false;
        } else if (!account.equals(other.account))
            return false;
        if (id != other.id)
            return false;
        return true;
    }

//    public static enum AccountType {
//
//        DEFAULT, ADMIN
//
//    }
}
