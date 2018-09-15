package com.invengo.scs.controller;

import com.invengo.scs.domain.QueryStudent;
import com.invengo.scs.domain.Score;
import com.invengo.scs.service.ScoreService;
import com.invengo.scs.utils.PageBean;
import com.invengo.scs.domain.Student;
import com.invengo.scs.service.StudentService;
import com.invengo.scs.utils.PageConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created By IntelliJ IDEA
 * User: Barney wong
 * Date: 2018/08/30
 * Time: 21:18
 */
@Controller
public class StudentController {

    //    private Student student;
    private int pageSize = PageConstants.PAGE_SIZE;
    private Integer currentPageNumber = 1;

    @Autowired
    private StudentService studentService;
    @Autowired
    private ScoreService scoreService;

    @RequestMapping("index")
    public String defaultView() {
        return "index";
    }

    @RequestMapping("pageSizeChange")
    public @ResponseBody
    Map<String, Object> pageSizeChange(@RequestParam Integer pageSize) {
        Map<String, Object> result = new HashMap<>();
        this.pageSize = pageSize;
        result.put("pageSizeChange", "OK");
        return result;
    }

    @RequestMapping("findStudent/{currentPageNumber}")
    public String findStudent(@PathVariable Integer currentPageNumber, Model model) {
        this.currentPageNumber = currentPageNumber;
        PageBean<Student> pageBean = studentService.findByCurrentPageNumber(this.currentPageNumber, pageSize);
        String url = "/findStudentAsync/";
        pageBean.setUrl(url);
        System.out.println(pageBean);
        model.addAttribute("pageBean", pageBean);
        return "student/list";
    }

    @RequestMapping("findStudentAsync/{currentPageNumber}/{pageSize}")
    @ResponseBody
    public Map<String, Object> findStudentAsync(@PathVariable Integer currentPageNumber, @PathVariable Integer pageSize, Model model) {
        this.currentPageNumber = currentPageNumber;
        Map<String, Object> result = new HashMap<>();
        PageBean<Student> pageBean = studentService.findByCurrentPageNumber(this.currentPageNumber, pageSize);
        String url = "/findStudentAsync/";
        pageBean.setUrl(url);
        model.addAttribute("pageBean", pageBean);
        result.put("pageBeanAsync", pageBean);
        return result;
    }

    @RequestMapping("findStudentByDataId/{currentPageNumber}/{dataId}")
    public String findStudentByDataId(@PathVariable Integer currentPageNumber, @PathVariable Integer dataId, Model model) {
        this.currentPageNumber = currentPageNumber;
        Student student = studentService.findStudentByDataId(dataId);
        model.addAttribute("student", student);
        model.addAttribute("currentPageNumber", currentPageNumber);
        return "student/detail";
    }

    @RequestMapping("findStudentByStuNo")
    @ResponseBody
    public Map<String, Object> findStudentByStuNo(@RequestParam String stuNo) {
        Map<String, Object> result = new HashMap<>();
        boolean isExistence = studentService.findStudentByStuNo(stuNo);
        if (isExistence) {
            result.put("isExistence", "501");
        } else {
            result.put("isExistence", "502");
        }

        return result;
    }

    @RequestMapping("updateStudent/{currentPageNumber}")
    public @ResponseBody
    Map<String, Object> updateStudent(@PathVariable Integer currentPageNumber, @RequestBody Student student) throws IOException {
        this.currentPageNumber = currentPageNumber;
        Map<String, Object> result = new HashMap<>();
        boolean isUpdate = studentService.updateStudent(student);
        if (isUpdate) {
            result.put("isUpdate", "301");
            result.put("currentPageNumber", currentPageNumber);
        } else {
            result.put("isUpdate", "302");
        }
        return result;
    }


    @RequestMapping("deleteStudentByDataId/{dataId}")
    @ResponseBody
    public Map<String, Object> deleteStudentByDataId(@PathVariable Integer dataId) {
        System.out.println(dataId);
        boolean isDelete = false;
        Student student = studentService.findStudentByDataId(dataId);
        Map<String, Object> resultMap = new HashMap<>();
        if (student != null) {
            studentService.deleteStudentByDataId(dataId);
            Student deleteStudent = studentService.findStudentByDataId(dataId);
            if (deleteStudent == null) {
                scoreService.deleteScoreByStuNo(student.getStuNo());
                Integer totalPages = studentService.findTotalPages(pageSize);
                resultMap.put("totalPages", totalPages);
                isDelete = true;
            }
        }
        if (isDelete) {
            resultMap.put("isDelete", "1");
        }else {
            resultMap.put("isDelete", "2");
        }
        return resultMap;
    }


    @RequestMapping("addStudent")
    public @ResponseBody
    Map<String, Object> addStudent(@RequestBody Student student) {
        Map<String, Object> result = new HashMap<>();

        student.setCreateDate(new Date());
        boolean isAdd = studentService.addStudent(student);
        if (isAdd) {
            result.put("isAdd", "101");
        } else {
            result.put("isAdd", "102");
        }
        Integer totalPages = studentService.findTotalPages(pageSize);
        result.put("totalPages", totalPages);
        return result;
    }

    @RequestMapping("queryStudent/{currentPageNumber}/{pageSize}")
    public @ResponseBody
    Map<String, Object> queryStudent(@PathVariable Integer currentPageNumber, @PathVariable Integer pageSize, @RequestBody QueryStudent queryStudent) {
        ;
        this.currentPageNumber = currentPageNumber;
        System.out.println(queryStudent);
        Map<String, Object> result = new HashMap<>();
        PageBean<Student> pageBeanAsync = studentService.findByCondition(currentPageNumber, pageSize, queryStudent);
        System.out.println(pageBeanAsync);
        String url = "/queryStudent/";
        pageBeanAsync.setUrl(url);
        pageBeanAsync.setCurrentPageNumber(currentPageNumber);
        result.put("pageBeanAsync", pageBeanAsync);
        return result;
    }
//    public Student getStudent() {
//        return student;
//    }
//
//    public void setStudent(Student student) {
//        this.student = student;
//    }
}