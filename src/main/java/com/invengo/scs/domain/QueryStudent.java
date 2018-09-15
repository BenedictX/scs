package com.invengo.scs.domain;

import java.util.Date;

/**
 * Created By IntelliJ IDEA
 * User: Barney wong
 * Date: 2018/09/13
 * Time: 13:39
 */
public class QueryStudent {
    private Student student;
    private String startDate;
    private String endDate;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "QueryStudent{" +
                "student=" + student +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                '}';
    }
}
