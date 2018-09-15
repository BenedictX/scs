package com.invengo.scs.mapper;

import com.invengo.scs.domain.Course;
import com.invengo.scs.utils.DynamicSQL;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseMapper {
    @Select("select * from course limit #{startRecordIndex} ,#{pageSize}")
    @Results(id="courseMap",value={
            @Result(property="courseId",column = "courseId",id=true,jdbcType = JdbcType.INTEGER),
            @Result(property="courseName",column ="courseName",jdbcType = JdbcType.VARCHAR)}
    )
    List<Course> findCourse(@Param(value="startRecordIndex") int startRecordIndex, @Param(value="pageSize") int pageSize);

    @Select("select count(*) from course")
    Integer findTotalRecords()
            ;
    @Select("select * from course where courseName = #{courseName}" )
    @ResultMap("courseMap")
    Course findCourseByName(@Param(value="courseName")String courseName);

    @Insert("insert into course(courseName)values(#{courseName})")
    Integer addCourse(@Param(value="courseName")String courseName);

    @Select("select * from course where courseId = #{courseId}")
    Course findCourseByCourseId(@Param(value="courseId")Integer courseId);

    @Delete("delete from course where courseId = #{courseId}")
    Integer deleteCourseByCourseId(@Param(value="courseId") Integer courseId);

    @SelectProvider(type=DynamicSQL.class,method = "selectByCourseCondition")
    @ResultMap("courseMap")
    List<Course> findCourseByCondition(Integer startIndex, Integer pageSize,Course course);

    @SelectProvider(type=DynamicSQL.class,method = "selectByCourseConditionAll")
    Integer findCourseByConditionAll(Course course);

    @Update("update course set courseName = #{courseName} where courseId = #{courseId}")
    Integer updateCourseByCourseId(Course course);
}
