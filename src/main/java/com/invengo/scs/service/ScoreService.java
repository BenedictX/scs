package com.invengo.scs.service;

import com.invengo.scs.domain.QueryScore;
import com.invengo.scs.domain.Score;
import com.invengo.scs.utils.PageBean;

import java.util.List;

public interface ScoreService  {
    PageBean<Score> findScore(Integer currentPageNumber, Integer pageSize);

    boolean deleteScoreByDataId(Integer dataId);

    Score findScoreByDataId(Integer dataId);

    boolean updateScore(Score score);

    List<Integer> findStuNo();

    String findStuNameByStuNo(Integer stuNo);

    List<String> findCourse();

    Integer findCourseIdByCourseName(String courseName);

    boolean addScore(Score score);

    Integer findScoreTotalPages(Integer pageSize);

    PageBean<Score> queryScore(QueryScore queryScore, Integer currentPageNumber, Integer pageSize);

    Score findScoreByStuNo(String stuNo);

    void deleteScoreByStuNo(String stuNo);
}
