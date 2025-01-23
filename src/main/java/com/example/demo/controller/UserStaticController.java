package com.example.demo.controller;

import com.example.demo.bean.User;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.service.UserStaticService;
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

@RestController
public class UserStaticController {

    @Autowired
    private UserStaticService userService;

    @GetMapping(path = {"/users"})
    public List<User> retrieveAllUsers(){
        return userService.findAll();
    }

    @GetMapping(path = {"/user/{id}"})
    public EntityModel<User> retrieveUser(@PathVariable int id){
        User user = userService.findById(id);

        if (user == null) {
            throw new UserNotFoundException("id-" + id);
        }

        EntityModel<User> resource = EntityModel.of(user);

        Link linkToAllUsers = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).retrieveAllUsers())
                .withRel("all-users");

        resource.add(linkToAllUsers);

        return resource;
    }

    @PostMapping(path = {"/user"})
    public ResponseEntity<User> createUser(@Valid @RequestBody User user){
        User savedUser = userService.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/user/{id}")
    public void deleteUser(@PathVariable int id) {
        User user = userService.deleteById(id);

        if (user == null) {
            throw new UserNotFoundException("id-" + id);
        }
    }
}
