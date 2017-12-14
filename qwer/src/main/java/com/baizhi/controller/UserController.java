package com.baizhi.controller;

import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/queryAll")
    @ResponseBody
    public List<User> queryAll(){
        return userService.queryAll();
    }


    @RequestMapping("/generateHtml")
    @ResponseBody
    public Map generateHtml(HttpServletRequest req) throws Exception{
        ServletContext sc = req.getSession().getServletContext();
        List<User> list = userService.queryAll();
        Configuration cfg = new Configuration(new Version(2, 3, 0));

        cfg.setDirectoryForTemplateLoading(new File(sc.getRealPath("/templates/")));
        Template template = cfg.getTemplate("article.ftl","utf-8");
        SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd");
        String savePath = sdf.format(new Date());
        savePath = sc.getRealPath("/front/"+savePath+"/");
        // 生成各个文章详情的静态页面
        for (User data : list) {
            File f = new File(savePath);
            if(!f.exists()){
                f.mkdirs();
            }
            File fh = new File(savePath +"/"+data.getId()+".html");
            Writer out =new OutputStreamWriter(new FileOutputStream(fh), Charset.forName("UTF-8"));
            template.process(data, out);
            out.flush();
            out.close();
        }

        // 生成普通用户浏览首页的 静态页面。
        Map<String,Object> map = new HashMap<String,Object>();
        String t = sdf.format(new Date());
        map.put("currentDate", t);
        map.put("articleList", list);
        Template indexTemplate = cfg.getTemplate("index.ftl","utf-8");
        File indexFile = new File(sc.getRealPath("/front/index.html"));
        Writer indexOut =new OutputStreamWriter(new FileOutputStream(indexFile), Charset.forName("UTF-8"));
        indexTemplate.process(map, indexOut);
        indexOut.flush();
        indexOut.close();

        Map<String,String> map2 = new HashMap<String,String>();
        map2.put("msg", "ok");
        return  map2;
    }

}
