/*
 * The MIT License (MIT)
 * Copyright (c) 2013 longkai(龙凯)
 * The software shall be used for good, not evil.
 */
package cn.newgxu.lab.apps.store.service;

import cn.newgxu.lab.apps.store.entity.Comment;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 *
 * @User longkai
 * @Date 13-8-14
 * @Mail im.longkai@gmail.com
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring-test.xml")
public class CommentServiceTest {

    @Inject
    private CommentService commentService;

    @Inject
    private AppService appService;

    private static final int TOTAL = 4;
    private static final int APP_TOTAL = 4;
    private int id;
    private int appId;

    private Comment comment;

    @Before
    public void setUp() throws Exception {
        id = (int) (Math.random() * TOTAL) + 1;
        appId = (int) (Math.random() * APP_TOTAL) + 1;

        comment = new Comment();
        comment.setRate(3);
        comment.setApp(appService.find(appId));
        comment.setNick("test");
        comment.setComment("test test ...");
        comment.setUid(123);
    }

    @Test
    @DirtiesContext
    public void testCreate() throws Exception {
        commentService.create(comment);
        assertTrue(comment.getId() > 0);
    }

    @Test(expected = Exception.class)
    @DirtiesContext
    public void testDelete() throws Exception {
        Comment c = commentService.find(id);
        assertNotNull(c);
        commentService.delete(id);
        commentService.find(id);
    }

    @Test
    @DirtiesContext
    public void testUpdate() throws Exception {
        String c = ".....";
        commentService.update(id, c);
        assertEquals(commentService.find(id).getComment(), c);
    }

    @Test
    @DirtiesContext
    public void testPlus1() throws Exception {
        Comment c = commentService.find(id);
        long l = c.getUseful_count();
        commentService.plus1(c.getId());
        assertEquals(l + 1, commentService.find(id).getUseful_count());
    }

    @Test
    public void testFind() throws Exception {
        assertNotNull(commentService.find(id));
    }

    @Test
    public void testComments() throws Exception {
        List<Comment> comments = commentService.comments(1, TOTAL, 0, false);
        for (Comment c : comments) {
            assertEquals(1, c.getApp().getId());
        }
        comments = commentService.comments(1, TOTAL, 2, false);
        assertTrue(comments.get(0).getId() > 2);
    }

    @Test
    public void testUsefulComments() throws Exception {
        List<Comment> comments = commentService.usefulComments(1, TOTAL, 0, false);
        assertTrue(comments.get(0).getUseful_count() >= comments.get(1).getUseful_count());
        assertEquals(comments.get(0).getApp().getId(), 1);
    }
}
