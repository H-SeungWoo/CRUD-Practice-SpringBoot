package io.cheol.demo.controller;

import io.cheol.demo.domain.Post;
import io.cheol.demo.repository.PostRepository;
import jakarta.websocket.server.PathParam;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
@Controller
public class PostController {

    private final PostRepository postRepository;

    public PostController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @GetMapping("/posts")
    public String list(Model model) {
        List<Post> posts = postRepository.findAll();

        model.addAttribute("posts", posts);

        return "posts/list";
    }

    @GetMapping("posts/new")
    public String newForm(){
        return "posts/new";
    }

    @PostMapping("/posts")
    public String create(
            @RequestParam String title,
            @RequestParam String content
    ){
        Post post = postRepository.save(title, content);

        return "redirect:/posts/" + post.getId();
    }

    @GetMapping("/posts/{id}")
    public String detail(@PathVariable Long id, Model model) {

            Post post = postRepository.findById(id);

            model.addAttribute("post", post);

            return "posts/detail";
    }

    @GetMapping("/posts/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {

        Post post = postRepository.findById(id);

        model.addAttribute("post", post);

        return "posts/edit";
    }

    @PostMapping("posts/{id}/edit")
    public String update(
            @PathVariable Long id,
            @RequestParam String title,
            @RequestParam String content
    ){
        Post post = postRepository.findById(id);
        post.update(title, content);

        return "redirect:/posts/" + id;
    }

    @PostMapping("/posts/{id}/delete")
    public String delete(@PathVariable Long id) {
        Post post = postRepository.findById(id);

        postRepository.delete(post);

        return "redirect:/posts";
    }



}
