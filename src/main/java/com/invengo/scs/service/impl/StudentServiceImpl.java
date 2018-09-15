package com.invengo.scs.service.impl;

import com.invengo.scs.domain.QueryStudent;
import com.invengo.scs.utils.PageBean;
import com.invengo.scs.domain.Student;
import com.invengo.scs.mapper.StudentMapper;
import com.invengo.scs.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;


import java.util.List;

/**
 * Created By IntelliJ IDEA
 * User: Barney wong
 * Date: 2018/08/30
 * Time: 21:20
 */
@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public PageBean<Student> findByCurrentPageNumber(Integer currentPageNumber, Integer pageSize) {
        int totalRecord = studentMapper.findTotalRecord();
        int startRecordIndex = (currentPageNumber-1)*pageSize;
        List<Student> students = studentMapper.findStudent(startRecordIndex,pageSize);
        System.out.println(students);
        PageBean<Student> studentPageBean = new PageBean<>(totalRecord, pageSize,currentPageNumber);
        studentPageBean.setDatas(students);
        return studentPageBean;
    }

    @Override
    public Student findStudentByDataId(Integer dataId) {
        return studentMapper.findStudentByDataId(dataId);
    }


    @Override
    public boolean updateStudent(Student student) {
        boolean isUpdate = false;
        Integer number = studentMapper.updateStudent(student);
        System.out.println(number);
        if(number != 0){
            isUpdate = true;
        }
        return isUpdate;
    }

    @Override
    public void deleteStudentByDataId(Integer dataId) {
        studentMapper.deleteStudentByDataId(dataId);
    }

    @Override
    public boolean addStudent(Student student) {
        boolean isAdd = false;
        Integer number = studentMapper.addStudent(student);
        if(number != 0){
            isAdd =true;
        }
        return isAdd;
    }

    @Override
    public boolean findStudentByStuNo(String stuNo) {
        boolean isExistence = false;
        Student student = studentMapper.findStudentByStuNo(stuNo);
        if(student!= null){
            isExistence = true;
        }
        return isExistence;
    }

    @Override
    public PageBean<Student> findByCondition(Integer currentPageNumber, Integer pageSize, QueryStudent queryStudent) {
        Integer startIndex = (currentPageNumber-1)*pageSize;
        Integer totalRecord = studentMapper.findByConditionAll(queryStudent);
        List<Student> students = studentMapper.findByCondition(startIndex,pageSize,queryStudent);
        PageBean<Student> studentPageBean = new PageBean<>(totalRecord, pageSize,currentPageNumber);
        studentPageBean.setDatas(students);
        return studentPageBean;

    }

    @Override
    public Integer findTotalPages(int pageSize) {
        Integer totalReordes = studentMapper.findTotalRecord();
        Integer totalPages  = totalReordes/pageSize;
        if((totalPages%pageSize)!=0){
            totalPages+=1;
        }
        return totalPages;
    }
}
