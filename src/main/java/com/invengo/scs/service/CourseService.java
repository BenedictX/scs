package com.invengo.scs.service;

import com.invengo.scs.domain.Course;
import com.invengo.scs.utils.PageBean;


public interface CourseService {
    PageBean<Course> findCourse(Integer currentPageNumber, Integer pageSize);

    boolean addCourse(String courseName);

    boolean deleteCourseByCourseId(Integer courseId);

    Course findCourseByCourseId(Integer courseId);

    boolean findCourseByName(String courseName);

    Integer findCourseTotalPages(Integer pageSize);

    PageBean<Course> findCourseByCondition(Course course, Integer pageSize, Integer currentPageNumber);

    boolean updateCourseByCourseId(Course course);
}
