package com.invengo.scs.domain;

import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 * Created By IntelliJ IDEA
 * User:Administrator
 * Date:2018/08/30
 * Time:17:05
 */

/**
 * create table Course
 * (
 *    CourseId             int not null auto_increment,
 *    CourseName           varchar(50) not null comment '课程名称',
 *    primary key (CourseId)
 * );
 *
 * alter table Course comment '课程信息表';
 */
@Alias(value="course")
public class Course implements Serializable {
    private Integer courseId;
    private String courseName;

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseId=" + courseId +
                ", courseName='" + courseName + '\'' +
                '}';
    }
}
