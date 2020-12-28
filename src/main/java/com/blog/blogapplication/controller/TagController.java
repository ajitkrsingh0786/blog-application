package com.blog.blogapplication.controller;

import com.blog.blogapplication.model.Tag;
import com.blog.blogapplication.service.declaration.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class TagController {

    @Autowired
    private TagService tagService;

    /*
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

     */
    @RequestMapping("/tags")
    public List<Tag> getAllTags() {
        return tagService.getAllTag();
    }

    @RequestMapping("/tags/{id}")
    public Tag getTag(@PathVariable int id) {
        return tagService.getTagById(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/tags")
    public void addTag(@RequestBody Tag tag) {
         tagService.saveTag(tag);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/tags")
    public void updateTag(@RequestBody Tag tag) {
         tagService.saveTag(tag);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/tags/{id}")
    public void deleteTag(@PathVariable int id) {
        tagService.deleteTagById(id);
    }
}
