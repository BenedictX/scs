package com.invengo.scs.controller;

import com.invengo.scs.domain.Course;
import com.invengo.scs.mapper.ScoreMapper;
import com.invengo.scs.service.CourseService;
import com.invengo.scs.service.ScoreService;
import com.invengo.scs.utils.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created By IntelliJ IDEA
 * User: Barney wong
 * Date: 2018/09/10
 * Time: 15:35
 */

@Controller
public class CourseController {
    private Integer pageSize = 2;
    private Integer currentPageNumber = 1;

    @Autowired
    private CourseService courseService;
    @Autowired
    private ScoreService scoreService;

    @RequestMapping("coursePageSizeChange")
    public @ResponseBody
    Map<String, Object> pageSizeChange(@RequestParam Integer pageSize) {
        Map<String, Object> result = new HashMap<>();
        this.pageSize = pageSize;
        result.put("pageSizeChange", "OK");
        return result;
    }

    @RequestMapping("/findCourseIndex")
    public String findCourseIndex() {
        this.pageSize = 2;
        return "Course/course";
    }

    @RequestMapping("findCourse/{currentPageNumber}")
    public @ResponseBody
    Map<String, Object> findCourse(@PathVariable Integer currentPageNumber) {
        this.currentPageNumber = currentPageNumber;
        Map<String, Object> result = new HashMap<>();
        PageBean<Course> pageBean = courseService.findCourse(currentPageNumber, pageSize);
        String url = "/findCourse/";
        pageBean.setUrl(url);
        result.put("pageBean", pageBean);
        return result;
    }

    @RequestMapping("findCourseByCourseId/{currentPageNumber}/{courseId}")
    public String findCourseByCourseId(@PathVariable Integer currentPageNumber, @PathVariable Integer courseId, Model model) {
        Course course = courseService.findCourseByCourseId(courseId);
        if (course != null) {
            model.addAttribute("course", course);
            model.addAttribute("currentPageNumber", currentPageNumber);
        }
        return "Course/courseDetail";

    }

    @RequestMapping("findCourseByName")
    public @ResponseBody
    Map<String, Object> findCourseByName(@RequestParam String courseName) {
        System.out.println(courseName);
        Map<String, Object> result = new HashMap<>();
        boolean isExistence = courseService.findCourseByName(courseName);
        if (isExistence) {
            result.put("isExistence", "501");
        } else {
            result.put("isExistence", "502");
        }
        return result;
    }

    @RequestMapping("addCourse")
    public @ResponseBody
    Map<String, Object> addCourse(@RequestParam String courseName) {
        Map<String, Object> result = new HashMap<>();
        boolean isAdd = courseService.addCourse(courseName);
        if (isAdd) {
            result.put("isAdd", "101");
            Integer totalPages = courseService.findCourseTotalPages(pageSize);
            result.put("totalPages", totalPages);
        } else {
            result.put("isAdd", "102");
        }
        result.put("url", "/findCourse/");
        return result;
    }

    @RequestMapping("deleteCourseByCourseId/{courseId}")
    public @ResponseBody
    Map<String, Object> deleteCourseByCourseId(@PathVariable Integer courseId) {
        Map<String, Object> result = new HashMap<>();
        Course course = courseService.findCourseByCourseId(courseId);
        if (course != null) {
           boolean isDelete =  courseService.deleteCourseByCourseId(courseId);
            if (isDelete) {
                result.put("isDelete", "1");
                result.put("url", "/findCourse/");
            }else {
                result.put("isDelete", "2");
        }
        }
        return result;
    }

    @RequestMapping("queryCourse/{currentPageNumber}")
    public @ResponseBody
    Map<String, Object> queryCourse(@PathVariable Integer currentPageNumber, @RequestBody Course course) {
        System.out.println(course);
        Map<String, Object> result = new HashMap<>();
        PageBean<Course> pageBean = courseService.findCourseByCondition(course, pageSize, currentPageNumber);
        String url = "/queryCourse/";
        pageBean.setUrl(url);
        result.put("pageBean", pageBean);
        return result;
    }

    @RequestMapping("updateCourse")
    public @ResponseBody
    Map<String, Object> updateCourse(@RequestBody Course course) {
        Map<String, Object> result = new HashMap<>();
        boolean isUpdate = courseService.updateCourseByCourseId(course);
        if (isUpdate) {
            result.put("isUpdate", "301");
        } else {
            result.put("isUpdate", "302");
        }
        return result;
    }
}
