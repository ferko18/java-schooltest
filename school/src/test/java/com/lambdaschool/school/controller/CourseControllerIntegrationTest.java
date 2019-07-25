package com.lambdaschool.school.controller;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.number.OrderingComparison.lessThan;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CourseControllerIntegrationTest
{
    @Autowired
    private WebApplicationContext wac;

    @Before
    public void initialiseRestAssuredMockMvcApplicationContext()
    {
        RestAssuredMockMvc.webAppContextSetup(wac);
    }

    @Test
    public void whenMeasuredResponseTime()
    {
        RestAssuredMockMvc.given().when().get("/courses/courses").then().time(lessThan(1000L));
    }

}