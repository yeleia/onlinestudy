package org.wingstudio.controller;

import com.github.pagehelper.PageInfo;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.wingstudio.Common.Const;
import org.wingstudio.entity.Category;
import org.wingstudio.entity.Student;
import org.wingstudio.entity.Video;
import org.wingstudio.service.StudentService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
            modelAndView.setViewName("student/index");
        }
        return modelAndView;
    }

    @RequestMapping("/to_login")
    public String toLogin(){
        return "student/stu_login";
    }

    @RequestMapping("/to_index")
    public ModelAndView toIndex(HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView();
        Object curentStu = request.getSession().getAttribute(Const.CURRENT_STU);
        if(curentStu==null){
            request.getSession().setAttribute(Const.CURRENT_GUEST,"游客：9527");
        }
        modelAndView.setViewName("student/index");

        List<Category> categories = studentService.getAllCategories();

        List<Video> videos = studentService.getRecentVideos();
        modelAndView.addObject("categories",categories);
        modelAndView.addObject("videos",videos);
        return modelAndView;
    }


    @RequestMapping("/to_page_index")
    public ModelAndView toIndex2(HttpServletRequest request, @RequestParam(value = "pageNum",defaultValue = "1") int pageNum){
        ModelAndView modelAndView = new ModelAndView();
        Object curentStu = request.getSession().getAttribute(Const.CURRENT_STU);
        if(curentStu==null){
            request.getSession().setAttribute(Const.CURRENT_GUEST,"游客：9527");
        }
        modelAndView.setViewName("student/page_index");

        List<Category> categories = studentService.getAllCategories();



        PageInfo<Video> pageInfo = studentService.getPageVideos(pageNum,2);

        System.out.println(pageInfo.getTotal()+"---------total----");
        System.out.println(pageInfo.getList().get(0).getTitle()+"---------title-----");

        modelAndView.addObject("categories",categories);
        modelAndView.addObject("pageInfo",pageInfo);
        return modelAndView;
    }




}
