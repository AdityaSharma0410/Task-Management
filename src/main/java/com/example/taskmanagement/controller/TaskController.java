package com.example.taskmanagement.controller;

import com.example.taskmanagement.model.Task;
import com.example.taskmanagement.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "http://localhost:8080")
public class TaskController {
    @Autowired
    TaskService taskservice;
    @PostMapping
    public Task createTask(@RequestBody Task task)
    {
        return taskservice.createTask(task);
    }
    @GetMapping
    public List<Task> getAllTasks()
    {
        return taskservice.getAllTasks();
    }
    @DeleteMapping("/{id}")
    public void deleteTaskById(@PathVariable Long id)
    {
        taskservice.deleteTaskById(id);
    }
    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Long id, @RequestBody Task updatedtask)
    {
        return taskservice.updateTask(id, updatedtask);
    }
}
