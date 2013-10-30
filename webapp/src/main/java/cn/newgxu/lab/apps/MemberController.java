/*
 * The MIT License (MIT)
 * Copyright (c) 2013 longkai(龙凯)
 * The software shall be used for good, not evil.
 */
package cn.newgxu.lab.apps;

import cn.newgxu.lab.core.member.Member;
import cn.newgxu.lab.core.member.MemberService;
import cn.newgxu.lab.core.member.MemberUtils;
import cn.newgxu.lab.util.Assert;
import cn.newgxu.lab.util.ViewConstants;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

import static cn.newgxu.lab.core.member.MemberUtils.MEMEBER;
import static cn.newgxu.lab.core.member.MemberUtils.MEMEBERS;
import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @User longkai
 * @Date 13-8-13
 * @Mail im.longkai@gmail.com
 */
@Controller
@Scope("session")
public class MemberController {

//    private static final String MEMBER = "member";
//
//	private static final String SESSION_MEMBER = "session_member";

    @Inject
    private MemberService memberService;

    @RequestMapping(value = "/members/participate", method = POST)
    @ResponseBody
    public Member paticipate(@RequestParam(value = "from", defaultValue = "0") int from,
                             @RequestParam("contact") String contact,
                             @RequestParam("label") String label,
                             @RequestParam("description") String description,
                             @RequestParam("account") String account,
                             @RequestParam("password") String password) {
        JsonNode result = MemberUtils.lookat(from, account, password);
        if (result.get("status").asInt() != 1) {
            throw new SecurityException("account password not matches!");
        }
        Member member = new Member();
        member.setNick(result.get("nick").asText());
        member.setUid(result.get("_ID").asLong());
        member.setComefrom(from);
        member.setDescription(description);
        member.setLabel(label);
        member.setContact(contact);
        memberService.participate(member);
        return member;
    }

    @RequestMapping(value = "/members/login", method = GET)
    @ResponseBody
    public Object login(HttpSession session, @RequestParam("from") int from,
                        @RequestParam("account") String account, @RequestParam("password") String password) {
        Object obj = session.getAttribute(MemberUtils.SESSEION_MEMEBER);
        if (obj != null) {
            return obj;
        }

        JsonNode result = MemberUtils.lookat(from, account, password);
        if (result.get("status").asInt() != 1) {
            throw new SecurityException("account password not matches!");
        }
        Member member = memberService.find(from, result.get("_ID").asLong());
        session.setAttribute(MemberUtils.SESSEION_MEMEBER, member);
        return member;
    }

    @RequestMapping(value = "/members/logout", method = GET)
    @ResponseBody
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return ViewConstants.JSON_STATUS_OK;
    }

    @RequestMapping(value = "/members/{id}", method = GET)
    public String profile(Model model, @PathVariable("id") long id) {
        Member member = memberService.find(id);
        model.addAttribute(MEMEBER, member);
        return ViewConstants.BAD_REQUEST;
    }

    @RequestMapping(value = "/members/{id}/center", method = GET)
    public String center(HttpServletRequest request) {
        memberService.fromSession(request);
        return "member/center";
    }

    @RequestMapping(value = "/members/{id}/update", method = GET)
    @ResponseBody
    public Member update(HttpServletRequest request, @PathVariable("id") long id) {
        return checkPermission(id, request);
    }

    @RequestMapping(value = "/members/{id}/update", method = PUT)
    @ResponseBody
    public String update(HttpServletRequest request, @PathVariable("id") long id,
                         @RequestParam("contact") String contact,
                         @RequestParam("label") String label,
                         @RequestParam("description") String description) {
        Member member = checkPermission(id, request);
        memberService.update(id, contact, label, description);
        member.setLabel(label);
        member.setContact(contact);
        member.setDescription(description);
        return ViewConstants.JSON_STATUS_OK;
    }

    @RequestMapping(value = "/members", method = GET)
    public String members(Model model, @RequestParam("type") int type,
                          @RequestParam("count") int count,
                          @RequestParam(value = "offset", defaultValue = "0") long offset,
                          @RequestParam(value = "append", defaultValue = "true") boolean append) {
        List<Member> members = memberService.members(type, count, offset, append);
        model.addAttribute(MEMEBERS, members);
        return ViewConstants.BAD_REQUEST;
    }

//    private static Member fromSession(HttpSession session) {
//        Assert.notNull("session cannot be null!", session);
//        Object member = session.getAttribute(MEMEBER);
//        Assert.notNull("you need login again!", member);
//        return (Member) member;
//    }

    private Member checkPermission(long id, HttpServletRequest request) {
        Member member = memberService.fromSession(request);
        Assert.equals("you have no permission to update this member, member id = " + id, member.getId(), id);
        return member;
    }

}
