package org.wingstudio.service.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wingstudio.dao.StudentMapper;
import org.wingstudio.entity.Student;
import org.wingstudio.service.StudentService;

/**
 * StudentServiceImpl
 * create by chenshihang on 2018/8/8
 */
@Service
public class StudentServiceImpl  implements StudentService{

    @Autowired
    private StudentMapper studentMapper;


    @Override
    public Student doLogin(int stuNum, String password) {

        return studentMapper.doLogin(stuNum,password);

    }
}
