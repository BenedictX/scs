package com.invengo.scs.service.impl;

import com.invengo.scs.domain.Course;
import com.invengo.scs.domain.Score;
import com.invengo.scs.mapper.CourseMapper;
import com.invengo.scs.mapper.ScoreMapper;
import com.invengo.scs.service.CourseService;
import com.invengo.scs.utils.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created By IntelliJ IDEA
 * User: Barney wong
 * Date: 2018/09/10
 * Time: 15:35
 */

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private ScoreMapper scoreMapper;

    @Override
    public PageBean<Course> findCourse(Integer currentPageNumber, Integer pageSize) {
        Integer totalRecord = courseMapper.findTotalRecords();
        Integer startIndex = (currentPageNumber - 1) * pageSize;
        List<Course> courses = courseMapper.findCourse(startIndex, pageSize);
        PageBean<Course> pageBean = new PageBean<>(totalRecord, pageSize, currentPageNumber);
        pageBean.setDatas(courses);
        return pageBean;
    }

    @Override
    public boolean addCourse(String courseName) {
        boolean isAdd = false;
        Course course = courseMapper.findCourseByName(courseName);
        if (course == null) {
            Integer row = courseMapper.addCourse(courseName);
            if (row != 0) {
                course = courseMapper.findCourseByName(courseName);
                if (course != null) {
                    isAdd = true;
                }
            }
        }
        return isAdd;
    }

    @Override
    public Course findCourseByCourseId(Integer courseId) {
        Course course = courseMapper.findCourseByCourseId(courseId);
        return course;
    }

    @Override
    public boolean deleteCourseByCourseId(Integer courseId) {
        boolean isDelete = false;
        Course course = courseMapper.findCourseByCourseId(courseId);
        if (course != null) {
            Integer row = courseMapper.deleteCourseByCourseId(courseId);
            if (row != 0) {
                isDelete= true;
                Score score = scoreMapper.findScoreByCourseId(courseId);
                if (score != null) {
                    scoreMapper.deleteScoreByCourseId(courseId);
                }
            }

        }
        return isDelete;

    }

    @Override
    public boolean findCourseByName(String courseName) {
        boolean isExistence = false;
        Course course = courseMapper.findCourseByName(courseName);
        if (course != null) {
            isExistence = true;
        }
        return isExistence;
    }

    @Override
    public Integer findCourseTotalPages(Integer pageSize) {
        Integer totalRecord = courseMapper.findTotalRecords();
        Integer totalPages = totalRecord / pageSize;
        if ((totalRecord % pageSize) != 0) {
            totalPages += 1;
        }
        return totalPages;
    }

    @Override
    public PageBean<Course> findCourseByCondition(Course course, Integer pageSize, Integer currentPageNumber) {
        Integer startIndex = (currentPageNumber - 1) * pageSize;
        List<Course> courses = courseMapper.findCourseByCondition(startIndex, pageSize, course);
        Integer totalRecord = courseMapper.findCourseByConditionAll(course);
        PageBean<Course> pageBean = new PageBean<>(totalRecord, pageSize, currentPageNumber);
        pageBean.setDatas(courses);
        return pageBean;
    }

    @Override
    public boolean updateCourseByCourseId(Course course) {
        boolean isUpdate = false;
        Integer row = courseMapper.updateCourseByCourseId(course);
        if (row != 0) {
            isUpdate = true;
        }
        return isUpdate;
    }
}
