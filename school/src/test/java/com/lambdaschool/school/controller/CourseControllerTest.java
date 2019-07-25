package com.lambdaschool.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lambdaschool.school.model.Course;
import com.lambdaschool.school.model.Instructor;
import com.lambdaschool.school.service.CourseService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@WebMvcTest(value = CourseController.class, secure=false)
public class CourseControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseService courseService;

    public List<Course> courseList;

    @Before
    public void setUp()
    {
        courseList = new ArrayList<>();

        var i1 = new Instructor("Sally");
        var i2 = new Instructor("Lucy");
        var i3 = new Instructor("Charlie");

        var c1 = new Course("Data Science", i1);
        var c2 = new Course("JavaScript", i1);
        var c3 = new Course("Node.js", i1);
        var c4 = new Course("Java Back End", i2);
        var c5 = new Course("Mobile IOS", i2);
        var c6 = new Course("Mobile Android", i3);

        courseList.addAll(Arrays.asList(c1, c2, c3, c4, c5, c6));
    }

    @Test
    public void listAllCourses() throws Exception
    {
        String apiUrl = "/courses/courses";

        // 1. when findall is called , return courselist
        Mockito.when(courseService.findAll()).thenReturn(new ArrayList<>(courseList));

        // 2. lets build a httprequest to send to our mock mvc
        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl).accept(MediaType.APPLICATION_JSON);

        // 3. lets make our mock request and get the result
        MvcResult r = mockMvc.perform(rb).andReturn();

        // 4. get response data as a string
        String tr = r.getResponse().getContentAsString();

        // 5. create an object to map over out list and turn it into a json string
        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(courseList);

        assertEquals("Rest API Returns List", er, tr);
    }
}

