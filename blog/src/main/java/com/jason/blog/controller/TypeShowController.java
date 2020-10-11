package com.jason.blog.controller;

import com.jason.blog.domain.Blog;
import com.jason.blog.domain.MyPage;
import com.jason.blog.domain.Type;
import com.jason.blog.service.TagService;
import com.jason.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

@Controller
public class TypeShowController {

    private static final int size = 8;      //一页显示的数量

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @GetMapping("/types/{id}/{page}")
    public String typesPage(@PathVariable("id") Long id, @PathVariable("page") int page, Model model) {
        List<Map<Type, Long>> allBlogSizeByType = typeService.findAllBlogSizeByType(0, 10000);
        if (allBlogSizeByType.size() == 0) {
            model.addAttribute("types", allBlogSizeByType);
        } else {
            if (id == -1)
                id = allBlogSizeByType.get(0).get("tId");
            model.addAttribute("types", allBlogSizeByType);
            model.addAttribute("activeTypeId", id);
            model.addAttribute("activeTypeName", typeService.findById(id).getName());
            List<Blog> blogByTypeId = typeService.findBlogByTypeId(id, (page - 1) * size, size);
            for (Blog blog : blogByTypeId)
                blog.setTags(tagService.findAllTagByBlogId(blog.getId()));
            model.addAttribute("page", blogByTypeId);
            Long totalSize = typeService.countByTypeId(id);
            model.addAttribute("totalSize", totalSize);
            int totalPage = (int) Math.ceil(totalSize / (double) size);
            MyPage myPage = new MyPage();
            myPage.setTotalPage(totalPage);
            myPage.setCurPage(page);
            myPage.init();
            model.addAttribute("num", myPage);
        }
        return "types";
    }
}
