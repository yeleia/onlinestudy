<%--
  Created by IntelliJ IDEA.
  User: 24834
  Date: 2018/7/29
  Time: 18:23
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<html>
<head>
    <meta charset="UTF-8">
    <title>视频播放</title>
    <link rel="stylesheet" href="/static/css/bootstrap.css">
    <style>
        body{
            background: #eaf2fa;
        }
        .video{
            padding-left: 20px;
        }
        .video img{
            width: 20px;
            height: 20px;
        }
        .iniline-block{
            display: inline-block;
        }
        .video_play{

        }
        .video_comment{

            background: #e2f0fd;
            margin-bottom: 10px;
        }
        .comment_date{
            text-align: right;
        }

    </style>
</head>
<body>
<script src="/static/js/jquery-3.3.1.js"></script>
<script src="/static/js/bootstrap.js"></script>

<div class="container">

    <nav class="navbar navbar-expand-lg navbar-light ">
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item active">
                    <a class="nav-link" href="#">最新视频 <span class="sr-only">(current)</span></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">热门推荐</a>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        分类
                    </a>
                    <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                        <c:forEach items="${categories}" var="category">
                            <a class="dropdown-item" href="#">${category.name}</a>
                            <div class="dropdown-divider"></div>
                        </c:forEach>
                    </div>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/to_stu_info">${current_stu.name}</a>
                </li>
            </ul>

            <form class="form-inline my-2 my-lg-0">
                <input class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search">
                <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
            </form>
        </div>
    </nav>


    <div class="video">
        <h3>${video.title}</h3>
        <hr>
        <p>类别：${video.categoryId}&nbsp;&nbsp;发布时间：${video.createTime}&nbsp;&nbsp; by：admin</p>
        <div class="iniline-block">
            <div class="iniline-block">
                <img  src="/static/img/video.svg"><span>&nbsp;&nbsp;${video.viewAmount}&nbsp;&nbsp;</span>
            </div>
            <div class="iniline-block">
                <a href="#comment">
                    <img src="/static/img/pinglun.svg">
                </a>
                <span>&nbsp;&nbsp;${video.commentAmount}&nbsp;&nbsp;</span>
            </div>
            <div class="iniline-block">
                <a href="#">
                    <img  src="/static/img/shoucang.svg">
                </a>
                <span>&nbsp;&nbsp;收藏：${video.collectAmount}&nbsp;&nbsp;</span>
            </div>
            <div class="iniline-block">
                <a href="#">
                    <img  src="/static/img/like.svg">
                </a>
                <span>&nbsp;&nbsp;喜欢：${video.likeAmount}&nbsp;&nbsp;</span>
            </div>
        </div>
        <div class="video_play">
            <video  width="100%" height="436" controls="controls">
                <source src="${video.path}" type="video/mp4">
                Your browser does not support the video tag.
            </video>
        </div>

        <hr>
        <div class="video_introduction">
            <p>视频简介：${video.introduction}</p>

        </div>
        <hr>

        <c:forEach items="${comments}" var="comment">
            <div class="video_comment">
                <div>
                    <a href="#">${comment.stuId}：</a>
                    <span>${comment.content}</span>
                </div>
                <div class="comment_date">
                    <span>${comment.createTime}</span>
                    <a href="#">回复</a>
                </div>
            </div>
        </c:forEach>

        <div id="comment" class="input-group mb-3">
            <form action="/do_comment">
                <input type="text" class="form-control" name="content" placeholder="发表评论" aria-label="Recipient's username" aria-describedby="button-addon2">
                <input type="text" name="videoId" value="${video.id}" hidden>
                <div class="input-group-append">
                    <button  class="btn btn-outline-secondary" type="submit" id="button-addon2">发表评论</button>
                </div>

            </form>
            <%--<input id="" type="text" class="form-control" placeholder="发表评论" aria-label="Recipient's username" aria-describedby="button-addon2">--%>
            <%--<div class="input-group-append">--%>
                <%--<button class="btn btn-outline-secondary" type="button" id="button-addon2" onclick="commont()">发表评论</button>--%>
            <%--</div>--%>
        </div>




    </div>
</div>
</body>
</html>
