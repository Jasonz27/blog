package com.jason.blog.controller.admin;

import com.jason.blog.domain.MyPage;
import com.jason.blog.domain.Tag;
import com.jason.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/tag")
public class TagController {

    private static final int size = 6;      //一页显示的数量
    private static final String REDIRECT_TAG = "redirect:/admin/tag/1";
    private static final String INPUT = "admin/tag-input";
    private static final String UPDATE = "admin/tag-update";

    @Autowired
    private TagService tagService;

    //显示标签
    @GetMapping("/{page}")
    public String tagPage(@PathVariable("page") int page, Model model) {
        Long aLong = tagService.countAll();
        int totalPage = (int) Math.ceil(aLong / (double) size);
        MyPage myPage = new MyPage();
        myPage.setCurPage(page);
        myPage.setTotalPage(totalPage);
        myPage.init();
        model.addAttribute("page", tagService.listAll((myPage.getCurPage() - 1) * size, size));
        model.addAttribute("num", myPage);
        model.addAttribute("size", aLong);
        return "admin/tag";
    }

    //新增
    @GetMapping("/input")
    public String inputPage() {
        return INPUT;
    }

    @PostMapping("/save")
    public String saveTag(@RequestParam("name") String name, RedirectAttributes attributes, Model model) {
        boolean saveTag = tagService.saveTag(name);
        if (saveTag) {
            attributes.addFlashAttribute("msg", "添加成功！");
            return REDIRECT_TAG;
        } else {
            model.addAttribute("error", "添加失败，已存在！");
            return INPUT;
        }
    }

    //修改
    @GetMapping("/update/{id}")
    public String updatePage(@PathVariable("id") Long id, Model model) {
        Tag byId = tagService.findById(id);
        model.addAttribute("tag", byId);
        return UPDATE;
    }

    @PostMapping("/update")
    public String updateTag(Tag tag, RedirectAttributes attributes, Model model) {
        boolean updateTag = tagService.updateTag(tag);
        if (updateTag) {
            attributes.addFlashAttribute("msg", "修改成功！");
            return REDIRECT_TAG;
        } else {
            model.addAttribute("error", "修改失败，已存在！");
            return UPDATE;
        }
    }

    //根据id删除
    @GetMapping("/delete/{id}")
    public String deleteTag(@PathVariable("id") Long id, RedirectAttributes attributes) {
        tagService.deleteById(id);
        attributes.addFlashAttribute("msg", "删除成功！");
        return REDIRECT_TAG;
    }
}
