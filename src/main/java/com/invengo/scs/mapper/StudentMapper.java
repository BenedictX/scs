package com.invengo.scs.mapper;


import com.invengo.scs.domain.QueryStudent;
import com.invengo.scs.domain.Student;
import com.invengo.scs.utils.DynamicSQL;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Repository
public interface StudentMapper {

    @Select("select * from student")
    @Results(id="studentMap",value={
            @Result(property="dataId",column = "dataId",id=true,jdbcType = JdbcType.INTEGER),
            @Result(property="stuNo",column ="stuNo",jdbcType = JdbcType.VARCHAR),
            @Result(property ="stuName",column = "stuName",jdbcType = JdbcType.VARCHAR),
            @Result(property ="sex",column = "sex",jdbcType = JdbcType.INTEGER),
            @Result(property ="age",column = "age",jdbcType =JdbcType.INTEGER),
            @Result(property = "createDate", column = "createDate", jdbcType = JdbcType.DATETIMEOFFSET)}
    )
    List<Student> findAll();

    @Select("SELECT COUNT(*) FROM student")
    int findTotalRecord();

    @Select("select * from student limit #{startRecordIndex},#{pageSize}")
    @ResultMap("studentMap")
    List<Student> findStudent(@Param(value="startRecordIndex") int startRecordIndex, @Param(value="pageSize") int pageSize);

    @Delete("DELETE FROM student WHERE dataId = #{dataId}")
    void deleteStudentByDataId(@Param(value="dataId") Integer dataId);

    @Select("select * from student where dataId = #{dataId}")
    @ResultMap("studentMap")
    Student findStudentByDataId(@Param(value = "dataId") Integer dataId);

    @Update("update student set stuNo = #{stuNo},stuName=#{stuName},sex=#{sex},age=#{age} where dataId = #{dataId}")
    Integer updateStudent(Student student);

    @Insert("insert into student(stuNo,stuName,sex,age,createDate) values(#{stuNo},#{stuName},#{sex},#{age},#{createDate,jdbcType=DATE})")
    Integer addStudent(Student student);

    @Select("select * from student where stuNo = #{stuNo}")
    Student findStudentByStuNo(@Param(value="stuNo")String stuNo);

    @SelectProvider(type=DynamicSQL.class,method = "selectByStudentCondition")
    @ResultMap("studentMap")
    List<Student> findByCondition(Integer startIndex, Integer pageSize, QueryStudent queryStudent);

    @SelectProvider(type=DynamicSQL.class,method = "selectByStudentConditionAll")
    Integer findByConditionAll(QueryStudent queryStudent);
}

