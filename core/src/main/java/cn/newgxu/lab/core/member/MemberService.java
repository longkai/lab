/*
 * The MIT License (MIT)
 * Copyright (c) 2013 longkai(龙凯)
 * The software shall be used for good, not evil.
 */
package cn.newgxu.lab.core.member;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @User longkai
 * @Date 13-8-13
 * @Mail im.longkai@gmail.com
 */
public interface MemberService {

    int ALL = 1;
    int VALID = 2;
    int INVALID = 3;

    void participate(Member member);

    /** 通过本地的id查找 */
    Member find(long id);

    /** 通过来自bbs等地的id来查找 */
    Member find(int from, long uid);

    void update(long id, String contact, String label, String description);

    void toggleInvalidate(long id);

    Member fromSession(HttpServletRequest request);

    /**
     * 查询成员列表，就是这一个方法，其中offset为0表示查找最新的列表。
     *
     * @param type 所有：1，有效：2，无效：3，其他之则默认为有效
     * @param count 在0-100之间，否则抛出异常
     * @param offset 偏移，用于查看更多或者加载更新等情况
     * @param append 是否加载更多？否则加载更新的（从id，也就是加入时间的顺序来排列）
     * @return 成员列表
     */
    List<Member> members(int type, int count, long offset, boolean append);

}
