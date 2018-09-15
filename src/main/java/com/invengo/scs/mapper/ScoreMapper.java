package com.invengo.scs.mapper;

import com.invengo.scs.domain.Course;
import com.invengo.scs.domain.QueryScore;
import com.invengo.scs.domain.Score;
import com.invengo.scs.domain.Student;
import com.invengo.scs.utils.DynamicSQL;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Repository
public interface ScoreMapper {
    @Select("select * from score limit #{startIndex},#{pageSize}")
    @Results(id = "scoreMap", value = {
            @Result(property = "dataId", column = "dataId", id = true, jdbcType = JdbcType.INTEGER),
            @Result(property = "student", column = "stuNo", one = @One(select = "com.invengo.scs.mapper.ScoreMapper.findStudentByStuNo")),
            @Result(property = "course", column = "courseId", one = @One(select = "com.invengo.scs.mapper.ScoreMapper.findCourseByCourseId")),
            @Result(property = "courseScore", column = "courseScore", jdbcType = JdbcType.FLOAT)
    })
    List<Score> findScore(@Param(value = "startIndex") Integer startIndex, @Param(value = "pageSize") Integer pageSize);

    @Select("select * from student where stuNo = #{stuNo}")
    Student findStudentByStuNo(@Param(value = "stuNo") String stuNo);

    @Select("select * from course where courseId = #{courseId}")
    Course findCourseByCourseId(@Param(value = "courseId") Integer courseId);

    @Select("select count(*) from score")
    Integer findTotalRecords();

    @Select("select * from score where dataId = #{dataId}")
    @ResultMap(value = "scoreMap")
    Score findScoreByDataId(@Param(value = "dataId") Integer dataId);

    @Delete("delete from score where dataId = #{dataId}")
    void deleteScoreByDataId(@Param(value = "dataId") Integer dataId);

    @Update("update score set courseScore = #{courseScore}  where dataId = #{dataId}")
    Integer updateScoreByDataId(@Param(value = "courseScore") float courseScore, @Param(value = "dataId") Integer dataId);

    @Select("select stuNo from student")
    List<Integer> findAllStuNo();

    @Select("select stuName from student where stuNo = #{stuNo}")
    String findStuNameByStuNo(@Param(value = "stuNo") Integer stuNo);

    @Select("select courseName from course")
    List<String> findAllCourseName();

    @Select("select courseId from course where courseName = #{courseName}")
    Integer findCourseIdByCourseName(@Param(value = "courseName") String courseName);

    @SelectProvider(type = DynamicSQL.class, method = "findScoreByScore")
    Score findSoreByScore(Score score);

    @Insert("insert into score(stuNo,courseId,courseScore) values(#{student.stuNo},#{course.courseId},#{courseScore})")
    void addScore(Score score);

    @SelectProvider(type = DynamicSQL.class, method = "selectByScoreCondition")
    @ResultMap("scoreMap")
    List<Score> findScoreByCondition(QueryScore queryScore, Integer startIndex, Integer pageSize);

    @SelectProvider(type = DynamicSQL.class, method = "selectByScoreConditionAll")
    Integer findScoreByConditionAll(QueryScore queryScore);

    @Select("select * from score where stuNo = #{stuNo}")
    @ResultMap(value = "scoreMap")
    Score findScoreByStuNo(@Param(value = "stuNo") String stuNo);

    @Delete("delete from score where stuNo = #{stuNo}")
    Integer deleteScoreByStuNo(@Param(value = "stuNo") String stuNo);

    @Select("select * from score where courseId = #{courseId}")
    @ResultMap(value = "scoreMap")
    Score findScoreByCourseId(@Param(value = "courseId") Integer courseId);

    @Delete("delete from score where courseId = #{courseId}")
    Integer deleteScoreByCourseId(@Param(value = "courseId") Integer courseId);
}