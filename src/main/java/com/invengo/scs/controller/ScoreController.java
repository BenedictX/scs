package com.invengo.scs.controller;

import com.invengo.scs.domain.QueryScore;
import com.invengo.scs.domain.Score;
import com.invengo.scs.service.ScoreService;
import com.invengo.scs.utils.PageBean;
import com.invengo.scs.utils.PageConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created By IntelliJ IDEA
 * User: Barney wong
 * Date: 2018/09/11
 * Time: 17:17
 */
@Controller
public class ScoreController {
    private Integer pageSize =  PageConstants.PAGE_SIZE;
    private Integer currentPageNumber = 1;

    @Autowired
    private ScoreService scoreService;
    @RequestMapping("scorePageSizeChange")
    public @ResponseBody
    Map<String, Object> pageSizeChange(@RequestParam Integer pageSize) {
        Map<String, Object> result = new HashMap<>();
        this.pageSize = pageSize;
        result.put("pageSizeChange", "OK");
        return result;
    }
    @RequestMapping("findScoreIndex")
    public String findScoreIndex() {
        return "score/scoreList";
    }

    @RequestMapping("findScore/{currentPageNumber}")
    public @ResponseBody
    Map<String, Object> findScore(@PathVariable Integer currentPageNumber) {
        Map<String, Object> result = new HashMap<>();
        PageBean<Score> pageBean = scoreService.findScore(currentPageNumber, pageSize);
        String url = "/findScore";
        pageBean.setUrl(url);
        result.put("pageBean", pageBean);
        return result;
    }

    @RequestMapping("findScoreByDataId/{currentPageNumber}/{dataId}")
    public String findScoreByDataId(@PathVariable Integer currentPageNumber, @PathVariable Integer dataId, Model model) {
        Score score = scoreService.findScoreByDataId(dataId);
        if (score != null) {
            model.addAttribute("score", score);
            model.addAttribute("currentPageNumber", currentPageNumber);
        }
        System.out.println(score);
        return "score/scoreDetail";
    }

    @RequestMapping("deleteScoreByDataId/{currentPageNumber}")
    public @ResponseBody
    Map<String, Object> deleteScoreByDataId(@PathVariable Integer currentPageNumber, @RequestParam Integer dataId) {
        Map<String, Object> result = new HashMap<>();
        boolean isDelete = false;
        isDelete = scoreService.deleteScoreByDataId(dataId);
        if (isDelete) {
            Integer totalPages = scoreService.findScoreTotalPages(pageSize);

            result.put("isDelete", "201");
        } else {
            result.put("isDelete", "202");
        }
        return result;
    }

    @RequestMapping("updateScore")
    public @ResponseBody
    Map<String, Object> updateScore(@RequestBody Score score) {
        Map<String, Object> result = new HashMap<>();
        boolean isUpdate = false;
        isUpdate = scoreService.updateScore(score);
        if (isUpdate) {
            result.put("isUpdate", "301");
        } else {
            result.put("isUpdate", "302");
        }
        return result;
    }

    @RequestMapping("findStuNo")
    public @ResponseBody
    Map<String, Object> findStuNo() {
        Map<String, Object> result = new HashMap<>();
        List<Integer> stuNos = scoreService.findStuNo();
        result.put("stuNos", stuNos);
        return result;
    }

    @RequestMapping("findStuNameByStuNo/{stuNo}")
    public @ResponseBody
    Map<String, Object> findStuNameByStuNo(@PathVariable Integer stuNo) {
        Map<String, Object> result = new HashMap<>();
        String stuName = scoreService.findStuNameByStuNo(stuNo);
        result.put("stuName", stuName);
        return result;
    }

    @RequestMapping("findCourse")
    public @ResponseBody
    Map<String, Object> findCourse() {
        Map<String, Object> result = new HashMap<>();
        List<String> courses = scoreService.findCourse();
        result.put("courses", courses);
        return result;
    }

    @RequestMapping("findCourseIdByCourseName/{courseName}")
    public @ResponseBody
    Map<String, Object> findCourse(@PathVariable String courseName) {
        Map<String, Object> result = new HashMap<>();
        Integer courseId = scoreService.findCourseIdByCourseName(courseName);
        result.put("courseId", courseId);
        return result;
    }

    @RequestMapping("addScore")
    public @ResponseBody
    Map<String, Object> addScore(@RequestBody Score score) {
        Map<String, Object> result = new HashMap<>();
        boolean isAdd = false;
        isAdd = scoreService.addScore(score);
        if (isAdd) {
            Integer totalPages = scoreService.findScoreTotalPages(pageSize);
            System.out.println(totalPages);
            result.put("isAdd", "101");
            result.put("totalPages",totalPages);
        } else {
            result.put("isAdd", "102");
        }
        return result;
    }
    @RequestMapping("queryScore/{currentPageNumber}")
    public @ResponseBody
    Map<String,Object> queryScore(@PathVariable Integer currentPageNumber,@RequestBody QueryScore queryScore){
        System.out.println(queryScore);
        Map<String, Object> result = new HashMap<>();
        PageBean<Score> pageBean = scoreService.queryScore(queryScore,currentPageNumber,pageSize);
        String url = "/queryScore/";
        pageBean.setUrl(url);
        result.put("pageBean", pageBean);
        return result;
    }
}
