package com.webtest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("/code")
public class DynCodeController {

    @RequestMapping("/run")
    public String run(String codeStr,String params){



        return null;
    }

}
