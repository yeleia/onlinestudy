package org.wingstudio.service.serviceimpl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wingstudio.dao.CategoryMapper;
import org.wingstudio.dao.StudentMapper;
import org.wingstudio.dao.VideoMapper;
import org.wingstudio.entity.Category;
import org.wingstudio.entity.Student;
import org.wingstudio.entity.Video;
import org.wingstudio.service.StudentService;

import java.util.List;

/**
 * StudentServiceImpl
 * create by chenshihang on 2018/8/8
 */
@Service
public class StudentServiceImpl  implements StudentService{

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private VideoMapper videoMapper;

    @Override
    public Student doLogin(int stuNum, String password) {

        return studentMapper.doLogin(stuNum,password);

    }

    @Override
    public List<Category> getAllCategories() {
        return categoryMapper.getAllCategories();
    }

    @Override
    public List<Video> getRecentVideos() {
        return videoMapper.getRecentVideos();
    }

    @Override
    public PageInfo<Video> getPageVideos(int pageNum, int pageSize) {

        PageHelper.startPage(pageNum,pageSize);
        List<Video> videos = videoMapper.getRecentVideos();
        PageInfo<Video> pageInfo = new PageInfo<>(videos);
        return pageInfo;

    }
}
