package com.nk.service.impl;

import com.nk.binding.Course;
import com.nk.repository.CourseRepository;
import com.nk.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public String upsert(Course course) {

        courseRepository.save(course); //upsert (insert or update based on primary key)
        return "success";
    }

    @Override
    public Course getById(Integer cid) {
        Optional<Course> findById = courseRepository.findById(cid);

        if(findById.isPresent()){
            return findById.get();
        }
        return null;
    }

    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public String deleteById(Integer cid) {
        if(courseRepository.existsById(cid)){
            courseRepository.deleteById(cid);
            return "Record Deleted Successfully";
        }else {
            return "No Record Found";
        }
    }
}
