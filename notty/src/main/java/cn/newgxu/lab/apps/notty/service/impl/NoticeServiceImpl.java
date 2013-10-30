/*
 * The MIT License (MIT)
 * Copyright (c) 2013 newgxu.cn <the original author or authors>.
 * The software shall be used for good, not evil.
 */
package cn.newgxu.lab.apps.notty.service.impl;

import cn.newgxu.lab.apps.notty.Notty;
import cn.newgxu.lab.apps.notty.entity.AuthorizedUser;
import cn.newgxu.lab.apps.notty.entity.Notice;
import cn.newgxu.lab.apps.notty.repository.AuthRepo;
import cn.newgxu.lab.apps.notty.repository.NoticeRepo;
import cn.newgxu.lab.apps.notty.service.NoticeService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cn.newgxu.lab.apps.notty.Notty.*;

/**
 * 校园信息接口的实现。
 *
 * @User longkai
 * @Date 13-8-1
 * @Mail im.longkai@gmail.com
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Throwable.class)
public class NoticeServiceImpl implements NoticeService {

    private static Logger l = LoggerFactory.getLogger(NoticeServiceImpl.class);

    private static final String DEFAULT_FROM_CLAUSE =
            TextUtils.replace("? as N JOIN ? as U ON N.uid=U.id", NoticeRepo.TABLE, AuthRepo.TABLE);

    private static final String DEFAULT_ORDER_BY = "N.id DESC";

    @Inject
    private NoticeRepo noticeRepo;

	@Inject
	AuthRepo authRepo;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void create(Notice notice) {
        validate(notice);

        notice.setAddDate(new Date());
        noticeRepo.insert(notice);
        l.info("auth_user：{} post a new notice: {} successfully!",
                notice.getAuthor().getAuthorizedName(), notice.getTitle());
    }

    @Override
    public void delete(Notice notice) {
        throw new UnsupportedOperationException(NOT_SUPPORT);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void update(Notice notice, long uid) {
        validate(notice);
        assertBelong(notice, uid);

        notice.setLastModifiedDate(new Date());
        noticeRepo.update(SQLUtils
                .set(new String[]{"last_modified_date", "title", "content", "doc_name", "doc_url"},
                        new Object[]{notice.getLastModifiedDate(), notice.getTitle(), notice.getContent(),
                                notice.getDocName(), notice.getDocUrl()}),
                "id=" + notice.getId());
        l.info("notice：{} modified successfully！", notice.getTitle());
    }

    @Override
    public Notice find(long pk) {
        Notice n = noticeRepo.find(pk);
        Assert.notNull(NOT_FOUND, n);
        return n;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Notice view(long pk) {
        Notice notice = find(pk);
        int i = noticeRepo.update("click_times=click_times+1", "id=" + pk);
        if (i > 0)
            notice.setClickTimes(notice.getClickTimes() + 1);
        return notice;
    }

    @Override
    public long total() {
        return noticeRepo.count(null, null);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Notice toggleBlock(long nid, long uid) {
        Notice notice = find(nid);

        assertBelong(notice, uid);

        notice.setBlocked(!notice.isBlocked());

        noticeRepo.update(SQLUtils
                .set(new String[]{"blocked"}, new Boolean[]{notice.isBlocked()}),
                "id=" + notice.getId());

        l.info("notice: {} block? -> {} successfully!", notice.getTitle(), notice.isBlocked());
        return notice;
    }

    @Override
    public long newerCount(long pk) {
        return noticeRepo.count(null, "id>" + pk);
    }

    @Override
    public List<Notice> latest(int count) {
        checkRange(count);
        return noticeRepo.query(null, DEFAULT_FROM_CLAUSE, "N.blocked=0", null, null, DEFAULT_ORDER_BY, "" + count);
    }

    @Override
    public List<Notice> latest(long uid, int count) {
        checkRange(count);
        return noticeRepo.query(null, DEFAULT_FROM_CLAUSE, "U.id=" + uid, null, null, DEFAULT_ORDER_BY, "" + count);
    }

    @Override
    public List<Notice> notices(long offset, boolean append, int count) {
        checkRange(count);
        String where = TextUtils.replace("N.id??", append ? "<" : ">", offset+"");
        return noticeRepo.query(null, DEFAULT_FROM_CLAUSE, "N.blocked=0 AND " + where,
                null, null, DEFAULT_ORDER_BY, "" + count);
    }

    @Override
    public List<Notice> notices(long uid, long offset, boolean append, int count) {
        checkRange(count);
        String where = TextUtils.replace("N.id??", append ? "<" : ">", offset+"");
        return noticeRepo.query(null, DEFAULT_FROM_CLAUSE,
                "N.uid=" + uid + " AND " + where, null, null, DEFAULT_ORDER_BY, "" + count);
    }

    @Override
    public Map<String, Object> sync(long lastTimestamp, int count) {
        checkRange(count);
	    Map<String, Object> map = new HashMap<String, Object>();
	    String where = "N.last_modified_date>? OR N.add_date>?";
        Date last = new Date(lastTimestamp);
	    List<Notice> notices = noticeRepo.query(null, DEFAULT_FROM_CLAUSE, SQLUtils.where(where, last, last),
			    null, null, DEFAULT_ORDER_BY, "" + count);
	    map.put(Notty.NOTICES, notices);
	    where = "(last_modified_date>? OR join_date>?) AND blocked=0";
	    List<AuthorizedUser> users = authRepo.query(null, AuthRepo.TABLE, SQLUtils.where(where, last, last),
			    null, null, SQLUtils.ID_DESC_ORDER_BY, "" + count);
	    map.put(Notty.AUTH_USERS, users);
	    return map;
    }

    @Override
    public List<Notice> search(String keywords, int count) {
        checkRange(count);
        String where = SQLUtils.like("N.title like '%?%' OR N.content like '%?%'", keywords, keywords);
        return noticeRepo.
                query(SQLUtils.columns("N.id", "N.title"), null, where, null, null, DEFAULT_ORDER_BY, "" + count);
    }

    private void checkRange(int count) {
        if (count < 1 || count > MAX_NOTICES_COUNT) {
            throw new IllegalArgumentException(NOTICES_ARG_ERROR);
        }
    }

    /** 简单地验证信息是否合法 */
    private void validate(Notice info) {
        Assert.notNull(USER_NOT_NULL, info.getAuthor());
        Assert.notEmpty(TITLE_NOT_NULL, info.getTitle());
        Assert.notEmpty(CONTENT_NOT_NULL, info.getContent());
//		文件名和路径绝对不能是空串
        if (TextUtils.isEmpty(info.getDocName(), true)) {
//				throw new IllegalArgumentException(getMessage(FILE_NAME_NOT_NULL));
            info.setDocName(null);
        }
        if (TextUtils.isEmpty(info.getDocUrl(), true)) {
//				throw new IllegalArgumentException(getMessage(FILE_URL_NOT_NULL));
            info.setDocUrl(null);
        }
    }

    /**
     * 1，检查数据库是否存在该信息；2，检查信息的发布者是否是同一人
     *
     * @param notice
     * @param uid
     */
    private void assertBelong(Notice notice, long uid) {
        if (notice.getAuthor().getId() != uid) {
            throw new SecurityException(NO_PERMISSION);
        }
    }
}
