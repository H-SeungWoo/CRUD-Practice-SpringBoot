package io.cheol.demo.controller;

import io.cheol.demo.domain.Post;
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

    private final List<Post> posts = new ArrayList<>();
    private Long sequence = 1L;

    public PostController() {
        posts.add(new Post(sequence++, "첫 번째 게시글", "첫 번째 게시글 내용입니다."));
        posts.add(new Post(sequence++, "두 번째 게시글", "두 번째 게시글 내용입니다."));
        posts.add(new Post(sequence++, "세 번째 게시글", "세 번째 게시글 내용입니다."));
    }

    @GetMapping("/posts")
    public String list(Model model) {
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
        Post post = new Post(sequence++, title, content);
        posts.add(post);

        return "redirect:/posts/" + post.getId();
    }

    @GetMapping("/posts/{id}")
    public String detail(@PathVariable Long id, Model model) {

            Post post = findPostById(id);

            model.addAttribute("post", post);

            return "posts/detail";
    }

    @GetMapping("/posts/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {

        Post post = findPostById(id);

        model.addAttribute("post", post);

        return "posts/edit";
    }

    @PostMapping("posts/{id}/edit")
    public String update(
            @PathVariable Long id,
            @RequestParam String title,
            @RequestParam String content
    ){
        Post post = findPostById(id);
        post.update(title, content);

        return "redirect:/posts/" + id;
    }


    private Post findPostById(Long id) {
        Post foundPost = null;

        for (Post post : posts) {
            if (post.getId().equals(id)) {
                foundPost = post;
                break;
            }
        }

        if (foundPost == null) {
            throw new IllegalArgumentException("게시글을 찾을 수 없습니다. id=" + id);
        }

        return foundPost;
    }



}
