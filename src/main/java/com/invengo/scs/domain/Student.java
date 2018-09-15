package com.invengo.scs.domain;

/**
 * Created By IntelliJ IDEA
 * User:Administrator
 * Date:2018/08/30
 * Time:16:58
 */

import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * create table Student
 * (
 *    DataId               int not null auto_increment,
 *    StuNo                varchar(30) not null comment '学号',
 *    StuName              varchar(30) not null comment '姓名',
 *    Sex                  int not null comment '性别:1-男;2-女',
 *    Age                  int not null comment '年龄',
 *    CreateDate           datetime not null comment '创建时间',
 *    primary key (DataId)
 * );
 *
 * alter table Student comment '学生信息表';
 */
@Alias(value="student")
public class Student implements Serializable {
    private Integer dataId;
    private String stuNo;
    private String stuName;
    private Integer sex;
    private Integer age;
    private Date createDate;

    public Integer getDataId() {
        return dataId;
    }

    public void setDataId(Integer dataId) {
        this.dataId = dataId;
    }

    public String getStuNo() {
        return stuNo;
    }

    public void setStuNo(String stuNo) {
        this.stuNo = stuNo;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "Student{" +
                "dataId=" + dataId +
                ", stuNo='" + stuNo + '\'' +
                ", stuName='" + stuName + '\'' +
                ", sex=" + sex +
                ", age=" + age +
                ", createDate=" + createDate+
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(dataId, student.dataId) &&
                Objects.equals(stuNo, student.stuNo) &&
                Objects.equals(stuName, student.stuName) &&
                Objects.equals(sex, student.sex) &&
                Objects.equals(age, student.age) &&
                Objects.equals(createDate, student.createDate);
    }

    @Override
    public int hashCode() {

        return Objects.hash(dataId, stuNo, stuName, sex, age, createDate);
    }
}
