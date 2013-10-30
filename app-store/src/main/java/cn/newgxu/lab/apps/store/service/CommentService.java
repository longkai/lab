/*
 * The MIT License (MIT)
 * Copyright (c) 2013 longkai(龙凯)
 * The software shall be used for good, not evil.
 */
package cn.newgxu.lab.apps.store.service;

import cn.newgxu.lab.apps.store.entity.Comment;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @User longkai
 * @Date 13-8-14
 * @Mail im.longkai@gmail.com
 */
public interface CommentService {

    void create(Comment comment);

    void delete(long id);

    /**
     * 更新评论信息，注意，评论修改的时间也会改变
     *
     * @param id
     * @param comment
     */
    void update(long id, String comment);

    /**
     * +1，意指这个评论是被人赞同的，有用的
     * todo 添加另一张表，记录+1的用户，显示在视图上
     *
     * @param id
     */
    void plus1(long id);

    Comment find(long id);

    /**
     * 抓取某个app的评论
     *
     * @param appId
     * @param count
     * @param offset 小于等于0表示不加载更多或者更新，直接从最新的开始查找
     * @param append 是否加载更多，否则刷新一下
     * @return comments
     */
    List<Comment> comments(long appId, int count, long offset, boolean append);

    List<Comment> usefulComments(long appId, int count, long offset, boolean append);

}
