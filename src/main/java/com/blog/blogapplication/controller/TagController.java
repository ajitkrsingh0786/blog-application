package com.blog.blogapplication.controller;

import com.blog.blogapplication.model.Tag;
import com.blog.blogapplication.service.Interface.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TagController {

    @Autowired
    private TagService tagService;

    @RequestMapping("/createTagForm")
    public String createTagForm(Model model) {
        Tag tag = new Tag();
        model.addAttribute("tag", tag);
        model.addAttribute("tags", tagService.getAllTag());
        return "html/createTag";
    }

    @PostMapping("/createTag")
    public String createTag(@ModelAttribute("tag") Tag tag) {
        tagService.saveTag(tag);
        return "redirect:/";
    }

    @RequestMapping("/deleteTag/{id}")
    public String deleteTag(@PathVariable(value = "id") int id) {
        tagService.deleteTagById(id);
        return "redirect:/";
    }
}
