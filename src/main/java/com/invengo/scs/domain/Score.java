package com.invengo.scs.domain;

/**
 * Created By IntelliJ IDEA
 * User:Administrator
 * Date:2018/08/30
 * Time:17:07
 */

import org.apache.ibatis.type.Alias;

/**
 * create table Score
 * (
 *    DataId               int not null auto_increment,
 *    StuNo                varchar(30) not null comment '学号',
 *    CourseId             int not null comment '课程编号',
 *    CourseScore          float not null comment '成绩',
 *    primary key (DataId)
 * );
 */
@Alias(value="score")
public class Score {
    private Integer dataId;
    private Student student;
    private Course course;
    private float courseScore;

    public Integer getDataId() {
        return dataId;
    }

    public void setDataId(Integer dataId) {
        this.dataId = dataId;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public float getCourseScore() {
        return courseScore;
    }

    public void setCourseScore(float courseScore) {
        this.courseScore = courseScore;
    }

    @Override
    public String toString() {
        return "Score{" +
                "dataId=" + dataId +
                ", student=" + student +
                ", course=" + course +
                ", courseScore=" + courseScore +
                '}';
    }
}
