package com.invengo.scs.service;

import com.invengo.scs.domain.QueryStudent;
import com.invengo.scs.utils.PageBean;
import com.invengo.scs.domain.Student;

public interface StudentService {

    PageBean<Student> findByCurrentPageNumber(Integer currentPageNumber, Integer pageSize);

   void deleteStudentByDataId(Integer dataId);

    Student findStudentByDataId(Integer dataId);

    boolean updateStudent(Student student);

    boolean addStudent(Student student);

    PageBean<Student> findByCondition(Integer currentPageNumber, Integer pageSize, QueryStudent queryStudent);

    boolean findStudentByStuNo(String stuNo);

    Integer findTotalPages(int pageSize);
}
