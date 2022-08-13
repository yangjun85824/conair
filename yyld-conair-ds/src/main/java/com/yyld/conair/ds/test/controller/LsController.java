package com.yyld.conair.ds.test.controller;


import com.yyld.conair.druid.config.DataSourceContext;
import com.yyld.conair.ds.test.entity.Ls;
import com.yyld.conair.ds.test.service.LsService;
import com.yyld.conair.ds.test.service.impl.LsServiceImpl;
import com.yyld.conair.ds.users.entity.Users;
import com.yyld.conair.ds.users.service.UsersService;
import com.yyld.conair.ds.utils.CutPictureUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.yyld.conair.commons.data.utils.SpringContextUtil;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author YANG JIAN JUN
 * @since 2022-07-24
 */
@RestController
@RequestMapping("/test/ls")
public class LsController {

    @Autowired
    private LsService service;
    @Autowired
    private UsersService usersService;

    @GetMapping
    public Map<String, Object> list() {

        long starttime = System.currentTimeMillis();

        Object LS = SpringContextUtil.getBean(LsServiceImpl.class);

        DataSourceContext.setDataSource("b");
        List<Users> users = usersService.list();
        DataSourceContext.setDataSource("a");
        List<Ls> list = service.selectPage();

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("list", list);
        resultMap.put("users", users);

        System.out.println(System.currentTimeMillis() - starttime);
        return resultMap;

    }

    @RequestMapping("/createFolw")
    public void createFolw(HttpServletRequest request, HttpServletResponse response) throws Exception {

        response.setContentType("img/jpg");
        response.setCharacterEncoding("utf-8");
        try {
            OutputStream outputStream = response.getOutputStream();

            CutPictureUtil o = new CutPictureUtil("D:\\9.jpg", 100, 200, 1000, 1000);
            o.setSubpath("D:\\1.png");

            InputStream in = o.cut();

            int len = 0;
            byte[] buf = new byte[1024];
            while ((len = in.read(buf, 0, 1024)) != -1) {
                outputStream.write(buf, 0, len);
            }
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
