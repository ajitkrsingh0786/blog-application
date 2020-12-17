package com.blog.blogapplication.controller;

import com.blog.blogapplication.model.Tag;
import com.blog.blogapplication.service.TagService;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

@Controller
public class TagController {

    @Autowired
    private TagService tagService;

    @RequestMapping("/createTagForm")
    public String createTagForm(Model model) {
        Tag tag = new Tag();
        model.addAttribute("tag",tag);
        return "html/createTag";
    }

    @PostMapping("/createTag")
    public String createTag(@ModelAttribute("tsg") Tag tag){
        Date date=java.util.Calendar.getInstance().getTime();
        tag.setCreatedAt(date);
        tagService.saveTag(tag);
        return "redirect:/";
    }


}
