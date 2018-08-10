package org.wingstudio.service.serviceimpl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wingstudio.dao.CategoryMapper;
import org.wingstudio.dao.CommentMapper;
import org.wingstudio.dao.StudentMapper;
import org.wingstudio.dao.VideoMapper;
import org.wingstudio.entity.Category;
import org.wingstudio.entity.Comment;
import org.wingstudio.entity.Student;
import org.wingstudio.entity.Video;
import org.wingstudio.service.StudentService;

import java.util.Date;
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

    @Autowired
    private CommentMapper commentMapper;

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



    @Override
    public Video viewOneVideo(int videoId) {
        Video video = videoMapper.selectByPrimaryKey(videoId);
        int oldViewCount = video.getViewAmount();
        video.setViewAmount(oldViewCount+1);
        videoMapper.updateByPrimaryKey(video);

        return video;
    }

    @Override
    public List<Comment> getOneVideoComments(int videoId) {
        return commentMapper.getOneVideoComments(videoId);
    }

    @Override
    public void doComment(int stuId, int videoId, String content) {
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setCreateTime(new Date());
        comment.setStuId(stuId);
        comment.setVideoId(videoId);
        comment.setParentId(0);
        commentMapper.insert(comment);
    }
}
