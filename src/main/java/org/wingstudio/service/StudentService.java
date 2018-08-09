package org.wingstudio.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import org.wingstudio.entity.Category;
import org.wingstudio.entity.Student;
import org.wingstudio.entity.Video;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * StudentService
 * create by chenshihang on 2018/7/29
 */
public interface StudentService {

//    Student isOnline(HttpServletRequest request);

    Student doLogin(int stuNum, String password);


    List<Category> getAllCategories();


    List<Video> getRecentVideos();

//    List<Video> getCollections(int stuId);


    PageInfo<Video> getPageVideos(int pageNum, int pageSize);




}
