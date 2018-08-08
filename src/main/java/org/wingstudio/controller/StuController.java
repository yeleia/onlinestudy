package org.wingstudio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.wingstudio.Common.Const;
import org.wingstudio.entity.Student;
import org.wingstudio.service.StudentService;

import javax.servlet.http.HttpServletRequest;

/**
 * StuController
 * create by chenshihang on 2018/8/8
 */
@Controller
public class StuController {

    @Autowired
    private StudentService studentService;


    @RequestMapping("/do_login")
    public ModelAndView doLogin(String stuNum, String password, HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView();
        int studentNum;
        try{
            studentNum = Integer.parseInt(stuNum);
        }catch (Exception e){
            modelAndView.addObject(Const.MSG,"学号或者密码错误");
            modelAndView.setViewName("error");
            return modelAndView;
        }
        Student student = studentService.doLogin(studentNum,password);
        if(student==null){
            modelAndView.addObject(Const.MSG,"学号或者密码错误");
            modelAndView.setViewName("error");
        }else {
            student.setPassword("");
            request.getSession().setAttribute(Const.CURRENT_STU,student);
            modelAndView.setViewName("index");
        }
        return modelAndView;
    }

    @RequestMapping("/to_login")
    public String toLogin(){
        return "login";
    }
}
