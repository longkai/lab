/*
 * The MIT License (MIT)
 * Copyright (c) 2013 longkai(龙凯)
 * The software shall be used for good, not evil.
 */
package cn.newgxu.lab.core.member;

import cn.newgxu.lab.util.Assert;
import cn.newgxu.lab.util.SQLUtils;
import cn.newgxu.lab.util.TextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @User longkai
 * @Date 13-8-13
 * @Mail im.longkai@gmail.com
 */
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Throwable.class)
public class MemberServiceImpl implements MemberService {

    private static Logger l = LoggerFactory.getLogger(MemberServiceImpl.class);
    private static String DEFAULT_ORDER_BY = "id DESC";

    @Inject
    private MemberRepo memberRepo;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void participate(Member member) {
        Assert.notEmpty("nick cannot be empty!", member.getNick().trim());
        Assert.notEmpty("contact cannot be empty!", member.getContact().trim());
        Assert.notEmpty("description cannot be empty!", member.getDescription());
        // check whether account exists or not.
        String where = SQLUtils.where("uid=? OR nick=?", member.getUid(), member.getNick());
        long count = memberRepo.count(null, where);
        if (count > 0) {
            throw new RuntimeException("We' re sorry, the account has been participated! Pldase check again.");
        }
        member.setParticipate_date(new Date());
        memberRepo.insert(member);
        l.info("a new member come in! id:{}, nick:{}, from:{}", member.getId(), member.getNick(), member.getComefrom());
    }

    @Override
    public Member find(long id) {
        Member member = memberRepo.find(id);
        Assert.notNull("We' re sorry, the one you find is not exists!", member);
        return member;
    }

    @Override
    public Member find(int from, long uid) {
        Member member = memberRepo.findOne(null, null, SQLUtils.where("comefrom=? AND uid=?", from, uid));
        Assert.notNull("We' re sorry, the one you find is not exists! maybe you need to register first!", member);
        return member;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void update(long id, String contact, String label, String description) {
        int i = memberRepo.update(SQLUtils.set(new String[]{"contact", "label", "description"},
                new Object[]{contact, label, description}),
                "id=" + id);
        l.info("member: {} update successfully, alter count: {}", id, i);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void toggleInvalidate(long id) {
        Member member = this.find(id);
        String update = "invalid=" + (member.isInvalid() ? "0" : "1");
        int i = memberRepo.update(update, "id=" + id);
        l.info("memeber: {} invalidate? {} successfully with alter count: {}", member.getNick(), !member.isInvalid(), i);
    }

    @Override
    public Member fromSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Assert.notNull("We' re sorry, you need to login first!", session);
        Member member = (Member) session.getAttribute(MemberUtils.SESSEION_MEMEBER);
        Assert.notNull("We' re sorry, you need to login first!", member);
        if (member.isInvalid()) {
            throw new IllegalStateException("We' re sorry, you account is invalid, please contact Yws.");
        }
        return member;
    }

    @Override
    public List<Member> members(int type, int count, long offset, boolean append) {
        Assert.between("member list count must in 1-100!", count, 1, 100);
        List<Member> members;
        String where = offset > 0 ? ("id" + (append ? "<" : ">") + offset) : null;
        switch (type) {
            case ALL:
                members = memberRepo.query(null, null, where, null, null, DEFAULT_ORDER_BY, "" + count);
                break;
            default:
                type = VALID;
            case VALID:
            case INVALID:
                where = "invalid=" + (type == VALID ? "0" : "1") + (TextUtils.isEmpty(where) ? "" : " AND " + where);
                members = memberRepo.query(null, null, where, null, null, DEFAULT_ORDER_BY, "" + count);
                break;
        }
        return members;
    }
}
