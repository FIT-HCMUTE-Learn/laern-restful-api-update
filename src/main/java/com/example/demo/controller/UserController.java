package com.example.demo.controller;

import com.example.demo.bean.Post;
import com.example.demo.bean.User;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.repository.PostRepository;
import com.example.demo.service.PostService;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @GetMapping(path = {"/jpa/users"})
    public List<User> retrieveAllUsers(){
        return userService.findAll();
    }

    @GetMapping(path = {"/jpa/user/{id}"})
    public EntityModel<User> retrieveUser(@PathVariable Integer id){
        Optional<User> users = userService.findOne(id);

        if (users.isEmpty()) {
            throw new UserNotFoundException("id-" + id);
        }

        EntityModel<User> resource = EntityModel.of(users.get());

        Link linkToAllUsers = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).retrieveAllUsers())
                .withRel("all-users");

        resource.add(linkToAllUsers);

        return resource;
    }

    @PostMapping(path = {"/jpa/user"})
    public ResponseEntity<User> createUser(@Valid @RequestBody User user){
        User savedUser = userService.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/jpa/user/{id}")
    public void deleteUser(@PathVariable Integer id) {
        Optional<User> users = userService.findOne(id);

        if (users.isEmpty()) {
            throw new UserNotFoundException("id-" + id);
        }

        userService.deleteById(id);
    }

    @GetMapping(path = {"/jpa/user/{id}/posts"})
    public List<Post> retrieveAllPosts(@PathVariable Integer id){
        Optional<User> users = userService.findOne(id);

        if (users.isEmpty()) {
            throw new UserNotFoundException("id-" + id);
        }

        return users.get().getPosts();
    }

    @PostMapping(path = {"/jpa/user/{id}/posts"})
    public ResponseEntity<User> createPost(@PathVariable Integer id, @RequestBody Post post){
        Optional<User> users = userService.findOne(id);

        if (users.isEmpty()) {
            throw new UserNotFoundException("id-" + id);
        }

        User user = users.get();

        post.setUser(user);
        postService.save(post);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(post.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }
}
