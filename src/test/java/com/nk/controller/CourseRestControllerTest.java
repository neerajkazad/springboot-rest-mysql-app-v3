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

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Test
    void getCourseReturnsCourseWhenIdIsValid() {
        Course course = new Course();
        course.setCid(1);
        course.setName("Java Basics");
        Mockito.when(courseService.getById(1)).thenReturn(course);

        ResponseEntity<Course> response = courseRestController.getCourse(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(course, response.getBody());
    }

    @Test
    void getCourseReturnsNullWhenCourseNotFound() {
        Mockito.when(courseService.getById(99)).thenReturn(null);

        ResponseEntity<Course> response = courseRestController.getCourse(99);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(null, response.getBody());
    }

    @Test
    void getAllCoursesReturnsListOfCoursesWhenCoursesExist() {
        List<Course> courses = List.of(
                new Course(1, "Java Basics"),
                new Course(2, "Spring Boot")
        );
        Mockito.when(courseService.getAllCourses()).thenReturn(courses);

        ResponseEntity<List<Course>> response = courseRestController.getAllCourses();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(courses, response.getBody());
    }

    @Test
    void getAllCoursesReturnsEmptyListWhenNoCoursesExist() {
        Mockito.when(courseService.getAllCourses()).thenReturn(List.of());

        ResponseEntity<List<Course>> response = courseRestController.getAllCourses();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    void updateCourseReturnsOkStatusWhenCourseIsValid() {
        Course course = new Course();
        course.setCid(1);
        course.setName("Advanced Java");
        Mockito.when(courseService.upsert(course)).thenReturn("Course updated successfully");

        ResponseEntity<String> response = courseRestController.updateCourse(course);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Course updated successfully", response.getBody());
    }

    @Test
    void updateCourseReturnsBadRequestWhenCourseIsNull() {
        ResponseEntity<String> response = courseRestController.updateCourse(null);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid course data", response.getBody());
    }

    @Test
    void deleteCourseReturnsOkStatusWhenCourseExists() {
        Mockito.when(courseService.deleteById(1)).thenReturn("Course deleted successfully");

        ResponseEntity<String> response = courseRestController.deleteCourse(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Course deleted successfully", response.getBody());
    }

    @Test
    void deleteCourseReturnsOkStatusWhenCourseDoesNotExist() {
        Mockito.when(courseService.deleteById(99)).thenReturn("Course not found");

        ResponseEntity<String> response = courseRestController.deleteCourse(99);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Course not found", response.getBody());
    }
}