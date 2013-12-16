/*
 * The MIT License (MIT)
 * Copyright (c) 2013 longkai(龙凯)
 * The software shall be used for good, not evil.
 */
package cn.newgxu.lab.store.service.impl;

import cn.newgxu.lab.store.entity.Comment;
import cn.newgxu.lab.store.repo.AppRepo;
import cn.newgxu.lab.store.repo.CommentRepo;
import cn.newgxu.lab.store.service.CommentService;
import cn.newgxu.lab.util.Assert;
import cn.newgxu.lab.util.SQLUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @User longkai
 * @Date 13-8-14
 * @Mail im.longkai@gmail.com
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = Throwable.class)
public class CommentServiceImpl implements CommentService {

	private static final long COMMENT_TIMELIMITATION_MILLIS = 7 * 24 * 60 * 60 * 1000;

	@Inject
	private CommentRepo commentRepo;

	@Inject
	private AppRepo appRepo;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void create(Comment comment) {
		checkComment(comment);
		Date now = new Date();

		Date aWeekAgo = new Date(now.getTime() - COMMENT_TIMELIMITATION_MILLIS);
		long count = commentRepo.count(null, SQLUtils.where("nick=? AND uid=? AND app_id=? AND rate_date>?",
				comment.getNick(), comment.getUid(), comment.getApp().getId(), aWeekAgo));
		if (count > 0) {
			throw new RuntimeException("We' re sorry, you can only rate a app within a week at a time!");
		}

		comment.setRate_date(now);
		commentRepo.insert(comment);

		appRepo.update("rate_count=rate_count+1,rate=rate+" + comment.getRate(), "id=" + comment.getApp().getId());
		l.info("a new comment come in! nick: {}, app: {}", comment.getNick(), comment.getApp().getName());
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void delete(long id) {
		int i = commentRepo.delete(null, "id=" + id);
		l.info("comment: {} delete successfully with alter count: {}", i);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void update(long id, String comment) {
		int i = commentRepo.update(SQLUtils.set(new String[]{"comment", "rate_date"},
				new Object[]{comment, new Date()}), "id=" + id);
		l.info("comment: {} update successfully with alter count: {}", id, i);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void plus1(long id) {
		int i = commentRepo.update("useful_count=useful_count+1", "id=" + id);
		l.info("comment: {} +1 successfully with alter count: {}", id, i);
	}

	@Override
	public Comment find(long id) {
		Comment comment = commentRepo.find(id);
		Assert.notNull("We' re sorry, the app you find is not exists!", comment);
		return comment;
	}

	@Override
	public List<Comment> comments(long appId, int count, long offset, boolean append) {
		Assert.between("comments size must between 1-100!", count, 1, 100);
		String where = "C.app_id=" + appId;
		if (offset > 0) {
			where += " AND C.id" + (append ? "<" : ">") + offset;
		}
		return commentRepo.query("*", null, where, null, null, SQLUtils.ID_DESC_ORDER_BY, "" + count);
	}

	@Override
	public List<Comment> usefulComments(long appId, int count, long offset, boolean append) {
		Assert.between("comments size must between 1-100!", count, 1, 100);
		String where = "C.app_id=" + appId;
		if (offset > 0) {
			where += " AND C.id" + (append ? "<" : ">") + offset;
		}
		return commentRepo.query("*", null, where, null, null, "C.useful_count desc", "" + count);
	}

	private static void checkComment(Comment comment) {
		Assert.notEmpty("comment cannot be empty!", comment.getComment().trim());
		Assert.notEmpty("nick cannot be empty!", comment.getNick());
		Assert.between("rate must in 1-5!", comment.getRate(), 1, 5);
		Assert.notNull("app cannot be null!", comment.getApp());
	}

	private static Logger l = LoggerFactory.getLogger(CommentServiceImpl.class);
}
