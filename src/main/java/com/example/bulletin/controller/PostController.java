package com.example.bulletin.controller;

import com.example.bulletin.entity.Post;
import com.example.bulletin.service.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PostController {

    @Autowired
    private PostService postService;

  
    @GetMapping("/")
    public String listPosts(Model model) {
        model.addAttribute("posts", postService.findAllPosts());
        return "list"; // list.html
    }

   
    @GetMapping("/post/{id}")
    public String viewPost(@PathVariable Long id, Model model) {
        Post post = postService.getPostAndIncreaseView(id);
        if (post == null) {
            return "redirect:/";
        }
        model.addAttribute("post", post);
        return "detail"; // detail.html
    }

  
    @GetMapping("/post/new")
    public String createForm(Model model) {
        model.addAttribute("post", new Post());
        return "form"; // form.html
    }


    @PostMapping("/post")
    public String createPost(@Valid Post post, BindingResult result) {
        if (result.hasErrors()) {
            return "form";
        }
        postService.savePost(post);
        return "redirect:/";
    }


    @GetMapping("/post/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        
        Post post = postService.getPostAndIncreaseView(id); 
      
        post = postService.getPostById(id);
        
        if (post == null) return "redirect:/";
        model.addAttribute("post", post);
        return "edit"; // edit.html
    }

  
    @PostMapping("/post/edit/{id}")
    public String updatePost(@PathVariable Long id, @RequestParam("password") String password, 
                             @Valid Post post, BindingResult result, RedirectAttributes ra) {
        if (!postService.checkPassword(id, password)) {
            ra.addFlashAttribute("error", "Password salah!");
            return "redirect:/post/edit/" + id;
        }
        postService.updatePost(id, post);
        return "redirect:/post/" + id;
    }

 
    @PostMapping("/post/delete/{id}")
    public String deletePost(@PathVariable Long id, @RequestParam("password") String password, RedirectAttributes ra) {
        if (!postService.checkPassword(id, password)) {
            ra.addFlashAttribute("error", "Password salah!");
            return "redirect:/post/" + id;
        }
        postService.softDeletePost(id);
        return "redirect:/";
    }
}