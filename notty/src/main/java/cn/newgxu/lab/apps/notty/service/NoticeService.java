/*
 * The MIT License (MIT)
 * Copyright (c) 2013 newgxu.cn <the original author or authors>.
 * The software shall be used for good, not evil.
 */
package cn.newgxu.lab.apps.notty.service;

import cn.newgxu.lab.apps.notty.entity.Notice;

import java.util.List;
import java.util.Map;

/**
 * 校园信息的服务接口。
 *
 * @User longkai
 * @Date 13-3-28
 * @Mail im.longkai@gmail.com
 */
public interface NoticeService {

    void create(Notice notice);

    void delete(Notice notice);

    void update(Notice notice, long uid);

    Notice find(long pk);

    /** 区别于find，这个会增加1的点击率 */
    Notice view(long pk);

    long total();

    /**
     * 屏蔽屏蔽或者解蔽信息。
     *
     * @param nid 欲屏蔽或者解蔽的信息对象
     * @param uid
     */
    Notice toggleBlock(long nid, long uid);

    /**
     * 是否有更新，判断是否有比传过来的参数更大的主键
     *
     * @param pk 客户端上最新的主键
     * @return number of the lastest notices
     */
    long newerCount(long pk);

    List<Notice> latest(int count);

    List<Notice> latest(long uid, int count);

    List<Notice> notices(long offset, boolean append, int count);

    List<Notice> notices(long uid, long offset, boolean append, int count);

    Map<String, Object> sync(long lastTimestamp, int count);

    // only return title and id!
    List<Notice> search(String keywords, int count);
}
