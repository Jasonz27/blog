package com.jason.blog.mapper;

import com.jason.blog.domain.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {

    //根据parentCommentId为null查询
    List<Comment> parentCommentIdIsNull(Long blogId);

    //根据parentCommentId查询所有的子回复
    List<Comment> replayComment(Long parentCommentId);

    //根据id查询
    Comment findById(Long id);

    //添加评论
    void insertComment(Comment comment);
}
