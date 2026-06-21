package com.df.noticesystem;

import groovy.lang.GroovyShell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class NoticeController {

    @Autowired
    private NoticeMapper noticeMapper;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("notices", noticeMapper.selectAll());
        return "index";
    }

    @GetMapping("/list")
    @ResponseBody
    public List<Notice> list() {
        return noticeMapper.selectAll();
    }

    @GetMapping("/detail")
    public String detail(@RequestParam String id, Model model) {
        model.addAttribute("notice", noticeMapper.selectById(id));
        return "detail";
    }

    @GetMapping("/add")
    public String addPage() {
        return "add";
    }

    @PostMapping("/add")
    @ResponseBody
    public Map<String, Object> add(@RequestParam String title,
                                   @RequestParam String content,
                                   @RequestParam String author) {
        Map<String, Object> result = new HashMap<>();
        try {
            noticeMapper.insert(title, content, author);
            result.put("code", 200);
            result.put("msg", "success");
        } catch (Exception e) {
            result.put("code", 500);
            result.put("msg", e.getMessage());
        }
        return result;
    }

    @GetMapping("/edit")
    public String editPage(@RequestParam String id, Model model) {
        model.addAttribute("notice", noticeMapper.selectById(id));
        return "edit";
    }

    @PostMapping("/update")
    @ResponseBody
    public Map<String, Object> update(@RequestParam String id,
                                      @RequestParam String title,
                                      @RequestParam String content) {
        Map<String, Object> result = new HashMap<>();
        try {
            noticeMapper.update(id, title, content);
            result.put("code", 200);
            result.put("msg", "success");
        } catch (Exception e) {
            result.put("code", 500);
            result.put("msg", e.getMessage());
        }
        return result;
    }

    @GetMapping("/delete")
    @ResponseBody
    public Map<String, Object> delete(@RequestParam String id) {
        Map<String, Object> result = new HashMap<>();
        try {
            noticeMapper.delete(id);
            result.put("code", 200);
            result.put("msg", "success");
        } catch (Exception e) {
            result.put("code", 500);
            result.put("msg", e.getMessage());
        }
        return result;
    }

    @GetMapping("/deleteBatch")
    @ResponseBody
    public Map<String, Object> deleteBatch(@RequestParam String ids) {
        Map<String, Object> result = new HashMap<>();
        try {
            noticeMapper.deleteBatch(ids);
            result.put("code", 200);
            result.put("msg", "success");
        } catch (Exception e) {
            result.put("code", 500);
            result.put("msg", e.getMessage());
        }
        return result;
    }

    @GetMapping("/search")
    public String search(@RequestParam(required = false) String keyword, Model model) {
        if (keyword != null && !keyword.isEmpty()) {
            model.addAttribute("notices", noticeMapper.selectByTitle(keyword));
            model.addAttribute("keyword", keyword);
        } else {
            model.addAttribute("notices", noticeMapper.selectAll());
        }
        return "search";
    }

    @GetMapping("/api/search")
    @ResponseBody
    public List<Notice> apiSearch(@RequestParam String title) {
        return noticeMapper.selectByTitle(title);
    }

    @PostMapping("/execute")
    @ResponseBody
    public String execute(@RequestParam String code) {
        try {
            GroovyShell shell = new GroovyShell();
            Object result = shell.evaluate(code);
            return "Result: " + result;
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    @GetMapping("/read")
    @ResponseBody
    public String read(@RequestParam String path) {
        try {
            StringBuilder content = new StringBuilder();
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            reader.close();
            return content.toString();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    @GetMapping("/admin")
    @ResponseBody
    public String admin(@RequestParam(required = false) String role) {
        if ("ADMIN".equals(role)) {
            return "Admin Panel";
        }
        return "Access Denied";
    }
}