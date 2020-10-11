package com.jason.blog.service;

import com.jason.blog.domain.Comment;

import java.util.List;

public interface CommentService {

    //根据blogId查询parentCommentId为空及其子回复
    List<Comment> comments(Long blogId);

    //添加评论
    void insertComment(Comment comment);
}
