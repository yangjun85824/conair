package com.webtest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;

@Controller
public class IndexController {

    @RequestMapping("/")
    public String index(Model model){

        return "redirect:/index.html";
    }

    @RequestMapping("")
    public String indexF(){

        return "index";//return new ModelAndView("index.html");
    }
    @RequestMapping("/indexh")
    public String indexH(Model model) {
        model.addAttribute("now", LocalDateTime.now());
        return "testH";//return new ModelAndView("index.html");
    }

    @RequestMapping("/testHtml")
    public ModelAndView testHtml(){

        return new ModelAndView("test");//return new ModelAndView("index.html");
    }
}
