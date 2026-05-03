package io.cheol.demo.repository;

import io.cheol.demo.domain.Post;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PostRepository {

    private final List<Post> posts = new ArrayList<>();
    private Long sequence = 1L;

    public PostRepository() {
        posts.add(new Post(sequence++, "첫 번째 게시글", "첫 번째 게시글 내용입니다."));
        posts.add(new Post(sequence++, "두 번째 게시글", "두 번째 게시글 내용입니다."));
        posts.add(new Post(sequence++, "세 번째 게시글", "세 번째 게시글 내용입니다."));
    }

    public List<Post> findAll() {
        return posts;
    }
    public Post findById(Long id) {
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

    public Post save(String title, String content) {
        Post post = new Post(sequence++, title, content);
        posts.add(post);

        return post;
    }

    public void delete(Post post) {
        posts.remove(post);
    }
}
