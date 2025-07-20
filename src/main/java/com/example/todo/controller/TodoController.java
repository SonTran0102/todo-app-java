package com.example.todo.controller;

import com.example.todo.model.Todo;
import com.example.todo.repository.TodoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class TodoController {
    private final TodoRepository repo;

    public TodoController(TodoRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("todos", repo.findAll());
        return "index";
    }

    @PostMapping("/add")
    public String addTodo(@RequestParam String task) {
        repo.save(new Todo(null, task));
        return "redirect:/";
    }
}

