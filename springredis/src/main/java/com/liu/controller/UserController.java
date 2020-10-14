package com.liu.controller;

import com.liu.service.UserService;
import com.liu.test.User;
import org.apache.ibatis.cache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/findAll")
    public String  findAll(Model model){
        System.out.println("方法被调用....");
        List<User> all = userService.findAll();
        model.addAttribute("users",all);
        return "showAll";
    }

    @GetMapping("/findById")
    public String  findById(Model model, HttpServletRequest request){
        String id = request.getParameter("id");
        System.out.println("方法被调用....");
        User user = userService.findById(id);
        model.addAttribute("users",user);
        return "showAll";
    }

    @GetMapping("/deleteById")
    public String  deleteById( HttpServletRequest request){
        String id = request.getParameter("id");
        System.out.println("delete方法被调用....");
        userService.delete(id);
        return "redirect:/user/findAll" ;
    }


}
