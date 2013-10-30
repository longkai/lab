/*
 * The MIT License (MIT)
 * Copyright (c) 2013 newgxu.cn <the original author or authors>.
 * The software shall be used for good, not evil.
 */
package cn.newgxu.lab.apps.notty.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.inject.Inject;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @User longkai
 * @Date 13-8-1
 * @Mail im.longkai@gmail.com
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration("/spring.xml")
//@WebAppConfiguration
public class AuthControllerTest {

//    @Inject
//    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
//		this.mockMvc = MockMvcBuilders.standaloneSetup(new AuthController()).build();
//        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void testName() throws Exception {
//        this.mockMvc.perform(get("/")
//                .accept(MediaType.ALL))
//                .andExpect(status().isOk())
//        ;

    }

    @Test
    public void testCreate() throws Exception {

    }

    @Test
    public void testAuth() throws Exception {

    }

    @Test
    public void testResetPwd() throws Exception {

    }

    @Test
    public void testLogin() throws Exception {

    }

    @Test
    public void testLogout() throws Exception {

    }

//	@Test
//	public void testModify() throws Exception {
//
//	}
//
//	@Test
//	public void testModify() throws Exception {
//
//	}
//
//	@Test
//	public void testModify() throws Exception {
//
//	}

    @Test
    public void testProfile() throws Exception {

    }

    @Test
    public void testUsers() throws Exception {

    }
}
