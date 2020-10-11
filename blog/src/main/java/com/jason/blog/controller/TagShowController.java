package com.jason.blog.controller;

import com.jason.blog.domain.Blog;
import com.jason.blog.domain.MyPage;
import com.jason.blog.domain.Tag;
import com.jason.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

@Controller
public class TagShowController {

    private static final int size = 8;        //一页显示的数量

    @Autowired
    private TagService tagService;

    @GetMapping("/tags/{id}/{page}")
    public String tagPage(@PathVariable("id") Long tagId, @PathVariable("page") int page, Model model) {
        List<Map<Tag, Long>> allBlogSizeByTag = tagService.findAllBlogSizeByTag(0, 10000);
        if (allBlogSizeByTag.size() == 0) {
            model.addAttribute("tags", allBlogSizeByTag);
        } else {
            if (tagId == -1)
                tagId = allBlogSizeByTag.get(0).get("gId");
            model.addAttribute("tags", allBlogSizeByTag);
            Long totalSize = tagService.countByTagId(tagId);
            model.addAttribute("totalSize", totalSize);
            int totalPage = (int) Math.ceil(totalSize / (double) size);
            model.addAttribute("activeTagId", tagId);
            model.addAttribute("activeTagName", tagService.findById(tagId).getName());
            MyPage myPage = new MyPage();
            myPage.setCurPage(page);
            myPage.setTotalPage(totalPage);
            myPage.init();
            model.addAttribute("num", myPage);
            model.addAttribute("page", tagService.findAllBlogByTagId(tagId, (page - 1) * size, size));
        }
        return "tags";
    }
}
