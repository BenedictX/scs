package com.invengo.scs.service.impl;

import com.invengo.scs.domain.QueryScore;
import com.invengo.scs.domain.Score;
import com.invengo.scs.mapper.ScoreMapper;
import com.invengo.scs.service.ScoreService;
import com.invengo.scs.utils.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created By IntelliJ IDEA
 * User: Barney wong
 * Date: 2018/09/11
 * Time: 17:18
 */
@Service
public class ScoreServiceImpl implements ScoreService {
    @Autowired
    private ScoreMapper scoreMapper;

    @Override
    public PageBean<Score> findScore(Integer currentPageNumber, Integer pageSize) {
        Integer startIndex = (currentPageNumber - 1) * pageSize;
        Integer totalRecords = scoreMapper.findTotalRecords();
        List<Score> scores = scoreMapper.findScore(startIndex, pageSize);
        System.out.println(scores);
        PageBean<Score> pageBean = new PageBean(totalRecords, pageSize, currentPageNumber);
        pageBean.setDatas(scores);
        return pageBean;
    }

    @Override
    public boolean deleteScoreByDataId(Integer dataId) {
        Score score = scoreMapper.findScoreByDataId(dataId);
        if (score != null) {
            scoreMapper.deleteScoreByDataId(dataId);
            score = scoreMapper.findScoreByDataId(dataId);
            if (score == null) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Score findScoreByDataId(Integer dataId) {
        return scoreMapper.findScoreByDataId(dataId);
    }

    @Override
    public boolean updateScore(Score score) {
        boolean isUpdate = false;
        Score existenceScore = scoreMapper.findScoreByDataId(score.getDataId());
        if (existenceScore != null) {
            Integer row = scoreMapper.updateScoreByDataId(score.getCourseScore(), score.getDataId());
            if (row != 0) {
                isUpdate = true;
            }
        }
        return isUpdate;
    }

    @Override
    public List<Integer> findStuNo() {
        return scoreMapper.findAllStuNo();
    }

    @Override
    public String findStuNameByStuNo(Integer stuNo) {
        return scoreMapper.findStuNameByStuNo(stuNo);
    }

    @Override
    public List<String> findCourse() {
        return scoreMapper.findAllCourseName();
    }

    @Override
    public Integer findCourseIdByCourseName(String courseName) {
        return scoreMapper.findCourseIdByCourseName(courseName);
    }

    @Override
    public boolean addScore(Score score) {
        boolean isAdd = false;
        Score oldScore = scoreMapper.findSoreByScore(score);
        System.out.println(oldScore);
        if (oldScore != null) {
            return isAdd;
        }else{
            scoreMapper.addScore(score);
            Score newScore = scoreMapper.findSoreByScore(score);
            if (newScore != null) {
                isAdd = true;
            }
        }
        return isAdd;
    }

    @Override
    public Integer findScoreTotalPages(Integer pageSize) {
        Integer totalRecord = scoreMapper.findTotalRecords();
        Integer totalPages = totalRecord/pageSize;
        if((totalRecord%pageSize)!=0){
            totalPages+=1;
        }
        return totalPages;
    }

    @Override
    public PageBean<Score> queryScore(QueryScore queryScore, Integer currentPageNumber, Integer pageSize) {
        Integer startIndex = (currentPageNumber-1)*pageSize;
        List<Score> scores = new ArrayList<>();
        Integer totalRecord = 0 ;
        if(queryScore == null){
            scores = scoreMapper.findScore(startIndex,pageSize);
            totalRecord = scoreMapper.findTotalRecords();
        }else{
            scores = scoreMapper.findScoreByCondition(queryScore,startIndex,pageSize);
            totalRecord =scoreMapper.findScoreByConditionAll(queryScore);
        }
        PageBean<Score> pageBean = new PageBean<>(totalRecord,pageSize,currentPageNumber);
        pageBean.setDatas(scores);
        return pageBean;
    }

    @Override
    public Score findScoreByStuNo(String stuNo) {
        return scoreMapper.findScoreByStuNo(stuNo);
    }

    @Override
    public void deleteScoreByStuNo(String stuNo) { ;
       Score score = scoreMapper.findScoreByStuNo(stuNo);
       if(score != null) {
           Integer row = scoreMapper.deleteScoreByStuNo(stuNo);
       }
    }
}