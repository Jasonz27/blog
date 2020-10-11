package com.jason.blog.controller.admin;

import com.jason.blog.domain.MyPage;
import com.jason.blog.domain.Type;
import com.jason.blog.service.BlogService;
import com.jason.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/type")
public class TypeController {

    private static final int size = 6;      //一页显示的数量

    private static final String REDIRECT_TYPE = "redirect:/admin/type/1";
    private static final String INPUT = "admin/type-input";
    private static final String UPDATE = "admin/type-update";

    @Autowired
    private TypeService typeService;

    @Autowired
    private BlogService blogService;

    //显示分类
    @GetMapping("/{page}")
    public String typePage(@PathVariable("page") int page, Model model) {
        Long aLong = typeService.countAll();
        int totalPage = (int) Math.ceil(aLong / (double) size);
        MyPage myPage = new MyPage();
        myPage.setCurPage(page);
        myPage.setTotalPage(totalPage);
        myPage.init();
        model.addAttribute("size", aLong);
        model.addAttribute("page", typeService.listAll((page - 1) * size, size));
        model.addAttribute("num", myPage);
        return "admin/type";
    }

    //新增
    @GetMapping("/input")
    public String inputPage() {
        return INPUT;
    }

    @PostMapping("/save")
    public String saveType(@RequestParam("name") String name, RedirectAttributes attributes, Model model) {
        boolean saveType = typeService.saveType(name);
        if (saveType) {
            attributes.addFlashAttribute("msg", "添加成功！");
            return REDIRECT_TYPE;
        } else {
            model.addAttribute("error", "添加失败，已存在！");
            return INPUT;
        }
    }

    //修改
    @GetMapping("/update/{id}")
    public String updatePage(@PathVariable("id") Long id, Model model) {
        Type byId = typeService.findById(id);
        model.addAttribute("type", byId);
        return UPDATE;
    }

    @PostMapping("/update")
    public String updateType(Type type, Model model, RedirectAttributes attributes) {
        boolean updateType = typeService.updateType(type);
        if (updateType) {
            attributes.addFlashAttribute("msg", "修改成功！");
            return REDIRECT_TYPE;
        } else {
            model.addAttribute("error", "修改失败，已存在！");
            return UPDATE;
        }
    }

    //根据id删除
    @GetMapping("/delete/{id}")
    public String deleteType(@PathVariable("id") Long id, RedirectAttributes attributes) {
        boolean byTypeId = blogService.findByTypeId(id);
        if (!byTypeId) {
            typeService.deleteById(id);
            attributes.addFlashAttribute("msg", "删除成功！");
            return REDIRECT_TYPE;
        } else {
            attributes.addFlashAttribute("error", "删除失败，有相关博客！");
            return REDIRECT_TYPE;
        }
    }
}
