package com.jason.blog.service.impl;

import com.jason.blog.domain.Comment;
import com.jason.blog.mapper.CommentMapper;
import com.jason.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Resource
    private CommentMapper commentMapper;

    //根据blogId查询parentCommentId为空及其子回复
    @Override
    public List<Comment> comments(Long blogId) {
        List<Comment> isNull = commentMapper.parentCommentIdIsNull(blogId);
        for (Comment comment : isNull) {
            List<Comment> comments = allReplay(comment);
            comment.setReplyComments(comments);
        }
        return isNull;
    }

    //临时存放子回复及其children
    private static List<Comment> replay = new ArrayList<>();

    //寻找每个parentCommentId为空的子回复
    private List<Comment> allReplay(Comment comments) {
        List<Comment> comments1 = commentMapper.replayComment(comments.getId());
        for (Comment comment : comments1)
            children(comment);
        List<Comment> temp;
        temp = replay;
        replay = new ArrayList<>();
        return temp;
    }

    //递归寻找子节点的children
    private void children(Comment comment) {
        Long parentId = comment.getParentComment().getId();
        Comment byId = commentMapper.findById(parentId);
        comment.setParentComment(byId);
        replay.add(comment);
        List<Comment> comments = commentMapper.replayComment(comment.getId());
        if (comments.size() > 0)
            for (Comment comment1 : comments) {
                Long parentId2 = comment1.getParentComment().getId();
                Comment byId2 = commentMapper.findById(parentId2);
                comment1.setParentComment(byId2);
                replay.add(comment1);
                List<Comment> comments1 = commentMapper.replayComment(comment1.getId());
                if (comments1.size() > 0)
                    for (Comment comment2 : comments1) {
                        children(comment2);
                    }
            }
    }

    @Value("${blog.comment.headPic}")
    private String headPic;

    //添加评论
    @Override
    public void insertComment(Comment comment) {
        if (comment.getParentComment().getId() == -1)
            comment.getParentComment().setId(null);
        comment.setHeadPic(headPic);
        comment.setCreateTime(new Date());
        commentMapper.insertComment(comment);
    }
}
