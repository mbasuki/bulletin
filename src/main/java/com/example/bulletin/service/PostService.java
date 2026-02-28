package com.example.bulletin.service;

import com.example.bulletin.entity.Post;
import com.example.bulletin.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public List<Post> findAllPosts() {
        return postRepository.findAll(); 
    }

    public Post savePost(Post post) {
        return postRepository.save(post);
    }

    @Transactional
    public Post getPostAndIncreaseView(Long id) {
        Optional<Post> postOptional = postRepository.findById(id);
        if (postOptional.isPresent()) {
            Post post = postOptional.get();
            post.setViews(post.getViews() + 1);
            return postRepository.save(post);
        }
        return null;
    }

    public boolean checkPassword(Long id, String password) {
        return postRepository.findById(id)
                .map(post -> post.getPassword().equals(password))
                .orElse(false);
    }

    @Transactional
    public boolean updatePost(Long id, Post updatedPost) {
        return postRepository.findById(id).map(post -> {
            post.setTitle(updatedPost.getTitle());
            post.setAuthor(updatedPost.getAuthor());
            post.setContent(updatedPost.getContent());
            // updated_at otomatis terisi oleh @PreUpdate
            postRepository.save(post);
            return true;
        }).orElse(false);
    }

    @Transactional
    public boolean softDeletePost(Long id) {
        return postRepository.findById(id).map(post -> {
            post.setDeleted(true);
            postRepository.save(post);
            return true;
        }).orElse(false);
    }
    
    public Post getPostById(Long id) {
        return postRepository.findById(id).orElse(null);
    }
}