package com.jason.blog.controller;

import com.jason.blog.domain.MyPage;
import com.jason.blog.service.BlogService;
import com.jason.blog.service.TagService;
import com.jason.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class IndexController {

    private static final int size = 8;      //一页显示的数量

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    //主页
    @GetMapping()
    public String indexPage(Model model) {
        Long totalSize = blogService.countPublish();
        model.addAttribute("totalSize", totalSize);
        int totalPage = (int) Math.ceil(totalSize / (double) size);
        MyPage myPage = new MyPage();
        myPage.setTotalPage(totalPage);
        myPage.setCurPage(1);
        myPage.init();
        model.addAttribute("num", myPage);
        model.addAttribute("page", blogService.listAllByPublish(0, size));
        model.addAttribute("types", typeService.findAllBlogSizeByType(0, size));
        model.addAttribute("tags", tagService.findAllBlogSizeByTag(0, size));
        model.addAttribute("newTop", blogService.newTop(size));
        return "index";
    }

    @GetMapping("/{page}")
    public String index(@PathVariable("page") int page, Model model) {
        Long totalSize = blogService.countPublish();
        model.addAttribute("totalSize", totalSize);
        int totalPage = (int) Math.ceil(totalSize / (double) size);
        MyPage myPage = new MyPage();
        myPage.setTotalPage(totalPage);
        myPage.setCurPage(page);
        myPage.init();
        model.addAttribute("num", myPage);
        model.addAttribute("page", blogService.listAllByPublish((page - 1) * size, size));
        model.addAttribute("types", typeService.findAllBlogSizeByType(0, size));
        model.addAttribute("tags", tagService.findAllBlogSizeByTag(0, size));
        model.addAttribute("newTop", blogService.newTop(size));
        return "index";
    }

    //全局search
    @PostMapping("/search/{page}")
    public String search(@RequestParam("query") String query, Model model, @PathVariable("page") int page) {
        System.out.println(query);
        model.addAttribute("query", query);
        Long totalSize = blogService.searchCount(query);
        model.addAttribute("totalSize", totalSize);
        int totalPage = (int) Math.ceil(totalSize / (double) size);
        MyPage myPage = new MyPage();
        myPage.setCurPage(page);
        myPage.setTotalPage(totalPage);
        myPage.init();
        model.addAttribute("num", myPage);
        model.addAttribute("page", blogService.searchBlog(query, (page - 1) * size, size));
        return "search";
    }

    //归档
    @GetMapping("/archives")
    public String archivesPage(Model model) {
        model.addAttribute("totalBlog",blogService.countPublish());
        model.addAttribute("archiveMap",blogService.archives());
        return "archives";
    }

    //关于我
    @GetMapping("/about")
    public String aboutPage() {
        return "about";
    }

    //footer
    @GetMapping("/footer/newBlog")
    public String footer(Model model) {
        model.addAttribute("newBlogs", blogService.newTop(4));
        return "_fragments :: newBlogs";
    }
}
