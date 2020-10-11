package com.jason.blog.controller.admin;

import com.jason.blog.domain.*;
import com.jason.blog.service.BlogService;
import com.jason.blog.service.TagService;
import com.jason.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/admin/blog")
public class BlogController {

    private static final int size = 6;      //一页显示的数量
    private static final String INPUT = "admin/blog-input";
    private static final String REDIRECT_BLOG = "redirect:/admin/blog/1";

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    //博客管理
    @GetMapping("/{page}")
    public String blogPage(@PathVariable("page") int curPage, Model model) {
        Long count = blogService.countAll();
        int totalPage = (int) Math.ceil(count / (double) size);     //总页数
        MyPage myPage = new MyPage();
        myPage.setCurPage(curPage);
        myPage.setTotalPage(totalPage);
        myPage.init();
        model.addAttribute("num", myPage);
        model.addAttribute("page", blogService.listAll((myPage.getCurPage() - 1) * size, size));
        model.addAttribute("type", typeService.listAll(0, 10000));
        model.addAttribute("flag", "show");
        return "admin/blog";
    }

    //搜索
    @PostMapping("/search")
    public String searchBlog(Model model, BlogQuery blogQuery) {
        Long count = blogService.count(blogQuery);
        int totalPage = (int) Math.ceil(count / (double) size);     //总页数
        MyPage myPage = new MyPage();
        myPage.setTotalPage(totalPage);
        myPage.setCurPage(blogQuery.getPage());
        myPage.init();
        model.addAttribute("num", myPage);
        model.addAttribute("flag", "search");
        model.addAttribute("page", blogService.pageForTTR(blogQuery, (blogQuery.getPage() - 1) * size, size));
        return "admin/blog :: blogList";
    }

    //新增
    @GetMapping("/input")
    public String inputPage(Model model) {
        model.addAttribute("types", typeService.listAll(0, 10000));
        model.addAttribute("tags", tagService.listAll(0, 10000));
        return INPUT;
    }

    @PostMapping("/save")
    public String saveBlog(Blog blog, @RequestParam("typeId") Long typeId, HttpSession session,
                           RedirectAttributes attributes) {
        if (blogService.findByTitle(blog.getTitle())) {
            attributes.addFlashAttribute("error", "有标题一样的博客！");
            return "redirect:/admin/blog/input";
        } else {
            blogService.saveBlog(blog, typeId, (User) session.getAttribute("user"));
            tagService.saveTags(blog.getTagIds());
            tagService.saveBlogIdAndTagId(blog.getTitle(), blog.getTagIds());
            attributes.addFlashAttribute("msg", "添加成功！");
            return REDIRECT_BLOG;
        }
    }

    //修改
    @GetMapping("/update/{id}")
    public String updatePage(@PathVariable("id") Long id, Model model) {
        Blog byId = blogService.findById(id);
        List<Tag> allTagByBlogId = tagService.findAllTagByBlogId(id);
        byId.setTags(allTagByBlogId);
        byId.initIds();
        model.addAttribute("tags", tagService.listAll(0, 10000));
        model.addAttribute("blog", byId);
        model.addAttribute("types", typeService.listAll(0, 10000));
        return "admin/blog-update";
    }

    @PostMapping("/update")
    public String updateBlog(Blog blog, @RequestParam("typeId") Long typeId, RedirectAttributes attributes) {
        blogService.updateBlog(blog, typeId);
        List<Tag> allTagByBlogId = tagService.findAllTagByBlogId(blog.getId());
        tagService.compare(allTagByBlogId,blog.getTagIds(),blog.getId());
        attributes.addFlashAttribute("msg", "修改成功！");
        return REDIRECT_BLOG;
    }

    //删除
    @GetMapping("/delete/{id}")
    public String deleteBlog(@PathVariable("id") Long id, RedirectAttributes attributes) {
        blogService.deleteBlog(id);
        tagService.deleteByBlogId(id);
        attributes.addFlashAttribute("msg", "删除成功！");
        return REDIRECT_BLOG;
    }
}
