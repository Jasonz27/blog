package com.jason.blog.controller;

import com.jason.blog.domain.Blog;
import com.jason.blog.domain.Comment;
import com.jason.blog.domain.User;
import com.jason.blog.service.BlogService;
import com.jason.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class BlogShowController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private CommentService commentService;

    //博客详情页
    @GetMapping("/blog/{id}")
    public String blogPage(@PathVariable("id") Long id, Model model) {
        Blog byId = blogService.findById(id);
        Integer views = byId.getViews();
        byId.setViews(views);
        model.addAttribute("blog", blogService.getAndConvert(id));
        return "blog";
    }

    //评论
    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable("blogId") Long blogId, Model model) {
        List<Comment> comments = commentService.comments(blogId);
        if (comments.size()==0)
            comments=null;
        model.addAttribute("comments", comments);
        bId = blogId;
        return "blog :: commentList";
    }

    private static Long bId;

    //回复
    @PostMapping("/comments")
    public String replay(Comment comment, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user!=null)
            comment.setAdminComment(true);
        commentService.insertComment(comment);
        return "redirect:/comments/" + bId;
    }
}
