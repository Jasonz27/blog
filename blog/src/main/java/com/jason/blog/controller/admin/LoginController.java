package com.jason.blog.controller.admin;

import com.jason.blog.domain.User;
import com.jason.blog.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class LoginController {

    private static final String REDIRECT_LOGIN = "redirect:/admin";

    @Autowired
    private LoginService loginService;

    //登录
    @GetMapping()
    public String loginPage() {
        return "admin/login";
    }

    @PostMapping("/login")
    public String checkLogin(RedirectAttributes attributes, HttpSession session, @RequestParam("username") String username, @RequestParam("password") String password) {
        User login = loginService.login(username, DigestUtils.md5DigestAsHex(password.getBytes()));
        if (login != null) {
            login.setPassword(null);
            session.setAttribute("user", login);
            return "admin/index";
        } else {
            attributes.addFlashAttribute("error", "用户名或密码错误，请重新输入！");
            return REDIRECT_LOGIN;
        }
    }

    //注销
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return REDIRECT_LOGIN;
    }
}
