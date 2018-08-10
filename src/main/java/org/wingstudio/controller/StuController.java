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
import org.wingstudio.entity.Comment;
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
            //转发到to_index请求，不用重新准备主页的数据了
            modelAndView.setViewName("redirect:/to_page_index");
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


    @RequestMapping("/to_video_play")
    public ModelAndView toVideoPlay(int videoId,HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView();
        //验证是否已登录
        Object curentStu = request.getSession().getAttribute(Const.CURRENT_STU);
        if(curentStu==null){
            modelAndView.setViewName("error");
            modelAndView.addObject(Const.MSG,"需要登录才能观看视频");
            return modelAndView;
        }
        List<Category> categories = studentService.getAllCategories();


        Video video = studentService.viewOneVideo(videoId);
        //最好重新建个类来承载comment的数据，里面有评论学生的姓名信息，以及格式化好的日期
        List<Comment> comments = studentService.getOneVideoComments(videoId);
        modelAndView.addObject("categories",categories);
        modelAndView.addObject("video",video);
        modelAndView.addObject("comments",comments);
        modelAndView.setViewName("student/video_play");
        return modelAndView;
    }

    @RequestMapping("/do_comment")
    public ModelAndView doComment(String content,int videoId,HttpServletRequest request){

        System.out.println("content:"+content+"-------------");
        System.out.println("videoId:"+videoId+"-------------");

        ModelAndView modelAndView = new ModelAndView();
        //验证是否已登录
        Student curentStu = (Student) request.getSession().getAttribute(Const.CURRENT_STU);
        if(curentStu==null){
            modelAndView.setViewName("error");
            modelAndView.addObject(Const.MSG,"需要登录才能评论视频");
            return modelAndView;
        }
        studentService.doComment(curentStu.getId(),videoId,content);
        modelAndView.setViewName("redirect:/to_video_play?videoId="+videoId);
        return modelAndView;
    }

}
