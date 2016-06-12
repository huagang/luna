package com.microscene.web.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

/**
 * Copyright (C) 2015 - 2016 MICROSCENE Inc., All Rights Reserved.
 *
 * @Author: chenshangan@microscene.com
 * @Date: 2016-05-24
 */
public class TestBaseController {

    private BaseController baseController;

    @Before
    public void setUp() {
        baseController = new BaseController();
    }
    @Test
    public void testBuildModelAndView() {
        ModelAndView jspModelAndView = baseController.buildModelAndView("hello");
        Assert.assertEquals("jsp view not correctly processed", "hello.jsp", jspModelAndView.getViewName());

        ModelAndView htmlModelAndView = baseController.buildModelAndView("hello.html");
        Assert.assertEquals("html view not correctly processed", "hello.html", htmlModelAndView.getViewName());

    }
}
