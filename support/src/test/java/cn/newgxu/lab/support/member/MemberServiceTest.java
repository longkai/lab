/*
 * The MIT License (MIT)
 * Copyright (c) 2013 longkai(龙凯)
 * The software shall be used for good, not evil.
 */
package cn.newgxu.lab.support.member;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 *
 * @User longkai
 * @Date 13-8-13
 * @Mail im.longkai@gmail.com
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring-test.xml")
@Transactional
@TransactionConfiguration
public class MemberServiceTest {

	@Inject
	private MemberService memberService;

	private static final int TOTAL = 6;

	private Member member;

	private long id;
	private static final int BBS_UID = 2;

	@Before
	public void setUp() throws Exception {
		member = new Member();
		member.setNick("test");
		member.setContact("im.longkai@gmail.com");
		member.setDescription("guess what?");
		member.setLabel("google");
		member.setUid(10000);

		id = (long) (Math.random() * TOTAL) + 1;
	}

	@Test
	public void testParticipate() throws Exception {
		memberService.participate(member);
		assertTrue(member.getId() > 0);
		l.info("member: {}", member);
	}

	@Test
	@Rollback(false)
	public void testFind() throws Exception {
		Member m = memberService.find(id);
		assertEquals(id, m.getId());
	}

	@Test
	@Rollback(false)
	public void testFindByUid() throws Exception {
		Member m = memberService.find(0, BBS_UID);
		assertEquals(BBS_UID, m.getId());
	}

	@Test
	public void testUpdate() throws Exception {
		String newLable = "MM___";
		Member m = memberService.find(id);
		m.setLabel(newLable);
		memberService.update(id, m.getContact(), newLable, m.getDescription());
		m = memberService.find(id);
		assertEquals(newLable, m.getLabel());
	}

	@Test
	public void testToggleInvalidate() throws Exception {
		Member m = memberService.find(id);
		boolean before = m.isInvalid();
		memberService.toggleInvalidate(m.getId());
		m = memberService.find(id);
		assertTrue(before != m.isInvalid());
	}

	@Test
	@Rollback(false)
	public void testList() throws Exception {
		List<Member> list1 = memberService.members(MemberService.ALL, TOTAL, 0, false);
		assertEquals(TOTAL, list1.size());
		List<Member> list2 = memberService.members(MemberService.ALL, TOTAL, 5, true);
		assertTrue(list2.get(0).getId() == 4);
		List<Member> list3 = memberService.members(MemberService.INVALID, TOTAL, -1, true);
		assertEquals(1, list3.size());
		List<Member> list4 = memberService.members(MemberService.VALID, TOTAL, -1, true);
		assertEquals(TOTAL - 1, list4.size());
	}

	private static Logger l = LoggerFactory.getLogger(MemberServiceTest.class);
}
