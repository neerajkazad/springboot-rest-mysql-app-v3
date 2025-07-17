package com.nk.controller;

import com.nk.binding.Course;
import com.nk.controller.CourseRestController;
import com.nk.service.CourseService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class CourseRestControllerTest {

    @Mock
    private CourseService courseService;

    @InjectMocks
    private CourseRestController courseRestController;

    @Test
    void createCourseReturnsCreatedStatusWhenCourseIsValid() {
        Course course = new Course();
        course.setCid(1);
        course.setName("Java Basics");
        Mockito.when(courseService.upsert(course)).thenReturn("Course created successfully");

        ResponseEntity<String> response = courseRestController.createCourse(course);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Course created successfully", response.getBody());
    }

    @Test
    void createCourseReturnsBadRequestWhenCourseIsNull() {
        ResponseEntity<String> response = courseRestController.createCourse(null);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid course data", response.getBody());
    }
}