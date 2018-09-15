package com.invengo.scs.utils;

import com.invengo.scs.domain.*;
import org.apache.ibatis.jdbc.SQL;
import org.thymeleaf.util.StringUtils;

import java.util.Date;

/**
 * Created By IntelliJ IDEA
 * User: Barney wong
 * Date: 2018/09/04
 * Time: 14:57
 */
public class DynamicSQL {
    public String selectByStudentCondition(Integer startIndex, Integer pageSize, QueryStudent queryStudent) {
        if (queryStudent == null) {
            return "select count(*) from student limit " + startIndex + "," + pageSize;
        }
        StringBuffer whereClause = new StringBuffer("select * from student where 1=1 ");

        String stuNo = queryStudent.getStudent().getStuNo();
        if (!StringUtils.isEmpty(stuNo)) {
            whereClause.append(" and stuNo like '%").append(stuNo).append("%'");
        }
        String stuName = queryStudent.getStudent().getStuName();
        if (!StringUtils.isEmpty(stuName)) {
            whereClause.append(" and stuName like '%").append(stuName).append("%'");
        }
        Integer sex = queryStudent.getStudent().getSex();
        if (sex == 1) {
            whereClause.append(" and sex = ").append(sex);
        }
        if (sex == 2) {
            whereClause.append(" and sex = ").append(sex);
        }
        Integer age = queryStudent.getStudent().getAge();
        if (age != null) {
            whereClause.append(" and age = ").append(age);
        }
        String startDate = queryStudent.getStartDate();
        String endDate = queryStudent.getEndDate();
        if (!StringUtils.isEmpty(startDate)) {
            if (!StringUtils.isEmpty(endDate)) {
                if (startDate.equals(endDate) ) {
                    whereClause.append(" and createDate = ' ").append(startDate+" '");
                } else {
                    whereClause.append(" and createDate >= ' ").append(startDate+" '").append(" and createDate <= ' ").append(endDate+" '");
                }
            } else {
                whereClause.append(" and createDate >= ' ").append(startDate+" '");
            }
        } else {
            if (!StringUtils.isEmpty(endDate)) {
                whereClause.append(" and createDate < ' ").append(endDate+" '");
            }
        }
        whereClause.append(" limit ").append(startIndex).append(",").append(pageSize);
        return whereClause.toString();
    }

    public String selectByStudentConditionAll(QueryStudent queryStudent) {
        if (queryStudent == null) {
            return "select count(*) from student";
        }
        StringBuffer whereClause = new StringBuffer(" 1=1 ");

        String stuNo = queryStudent.getStudent().getStuNo();
        if (!StringUtils.isEmpty(stuNo)) {
            whereClause.append(" and stuNo like '%").append(stuNo).append("%'");
        }
        String stuName = queryStudent.getStudent().getStuName();
        if (!StringUtils.isEmpty(stuName)) {
            whereClause.append(" and stuName like '%").append(stuName).append("%'");
        }
        Integer sex = queryStudent.getStudent().getSex();
        if (sex == 1) {
            whereClause.append(" and sex = ").append(sex);
        }
        if (sex == 2) {
            whereClause.append(" and sex = ").append(sex);
        }
        Integer age = queryStudent.getStudent().getAge();
        if (age != null) {
            whereClause.append(" and age = ").append(age);
        }
        String startDate = queryStudent.getStartDate();
        String endDate = queryStudent.getEndDate();
        if (!StringUtils.isEmpty(startDate)) {
            if (!StringUtils.isEmpty(endDate)) {
                if (startDate.equals(endDate) ) {
                    whereClause.append(" and createDate =' ").append(startDate+" '");
                } else {
                    whereClause.append(" and createDate >= ' ").append(startDate+" '").append(" and createDate <= ' ").append(endDate+" '");
                }
            } else {
                whereClause.append(" and createDate >= ' ").append(startDate+" '");
            }
        } else {
            if (!StringUtils.isEmpty(endDate)) {
                whereClause.append(" and createDate < ' ").append(endDate+" '");
            }
        }
        return new SQL() {
            {
                SELECT("count(*)");
                FROM("student");
                WHERE(whereClause.toString());
            }
        }.toString();
    }

    public String selectByCourseCondition(Integer startIndex, Integer pageSize, Course course) {
        if (course == null) {
            return "select * from course limit " + startIndex + "," + pageSize;
        }
        StringBuffer sb = new StringBuffer("select * from course where 1=1 ");
        Integer courseId = course.getCourseId();
        if (courseId != null) {
            sb.append("and courseId like '%").append(courseId).append("%' ");
        }
        String courseName = course.getCourseName();
        if (courseName != null) {
            sb.append("and courseName like '%").append(courseName).append("%' ");
        }
        sb.append(" limit ").append(startIndex).append(",").append(pageSize);

        return sb.toString();
    }

    public String selectByCourseConditionAll(Course course) {
        if (course == null) {
            return "select count(*) from course ";
        }
        StringBuffer sb = new StringBuffer("select count(*) from course where 1=1 ");
        Integer courseId = course.getCourseId();
        if (courseId != null) {
            sb.append("and courseId like '%").append(courseId).append("%' ");
        }
        String courseName = course.getCourseName();
        if (courseName != null) {
            sb.append("and courseName like '%").append(courseName).append("%' ");
        }
        return sb.toString();
    }

    public String selectByScoreCondition(QueryScore queryScore, Integer startIndex, Integer pageSize) {
        StringBuffer sb = new StringBuffer("select * " +
                "from score " +
                "left join student as s on score.stuNo = s.stuNo " +
                "left join course as c on score.courseId = c.courseId " +
                "where 1=1 ");

        String stuNo = queryScore.getScore().getStudent().getStuNo();
        if (!StringUtils.isEmpty(stuNo)) {
            sb.append(" and score.stuNo like '%").append(stuNo).append("%'");
        }
        Integer courseId = queryScore.getScore().getCourse().getCourseId();
        if (courseId != null) {
            sb.append(" and score.courseId like '%").append(courseId).append("%'");
        }
        String courseName = queryScore.getScore().getCourse().getCourseName();
        if (!StringUtils.isEmpty(courseName)) {
            sb.append("and c.courseName like '%").append(courseName).append("%' ");
        }
        Integer min = queryScore.getMin();
        Integer max = queryScore.getMax();
        if (min != null) {
            if (max != null) {
                sb.append(" and courseScore between ").append(min).append(" and ").append(max);
            } else {
                sb.append(" and courseScore > ").append(min);
            }
        } else {
            if (max != null) {
                sb.append(" and courseScore < ").append(max);
            }
        }
        sb.append(" limit ").append(startIndex).append(",").append(pageSize);

        return sb.toString();
    }

    public String selectByScoreConditionAll(QueryScore queryScore) {
        StringBuffer sb = new StringBuffer("select count(*) " +
                "from score " +
                "left join student as s on score.stuNo = s.stuNo " +
                "left join course as c on score.courseId = c.courseId " +
                "where 1=1 ");

        String stuNo = queryScore.getScore().getStudent().getStuNo();
        if (!StringUtils.isEmpty(stuNo)) {
            sb.append(" and score.stuNo like '%").append(stuNo).append("%'");
        }
        Integer courseId = queryScore.getScore().getCourse().getCourseId();
        if (courseId != null) {
            sb.append(" and score.courseId like '%").append(courseId).append("%'");
        }
        String courseName = queryScore.getScore().getCourse().getCourseName();
        if (!StringUtils.isEmpty(courseName)) {
            sb.append("and c.courseName like '%").append(courseName).append("%' ");
        }
        Integer min = queryScore.getMin();
        Integer max = queryScore.getMax();
        if (min != null) {
            if (max != null) {
                sb.append(" and courseScore between ").append(min).append(" and ").append(max);
            } else {
                sb.append(" and courseScore > ").append(min);
            }
        } else {
            if (max != null) {
                sb.append(" and courseScore < ").append(max);
            }
        }
        return sb.toString();
    }
    public String findScoreByScore(Score score){
        StringBuffer sb = new StringBuffer("select * from score where 1=1 ");
        String stuNo = score.getStudent().getStuNo();
        sb.append(" and stuNo = ").append(stuNo);
        Integer courseId = score.getCourse().getCourseId();
        sb.append(" and courseId = ").append(courseId);
        float courseScore = score.getCourseScore();
        sb.append(" and courseScore = ").append(courseScore);
        return sb.toString();

    }
}
