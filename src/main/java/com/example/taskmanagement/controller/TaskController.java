package com.example.taskmanagement.controller;

import com.example.taskmanagement.model.Category;
import com.example.taskmanagement.model.Task;
import com.example.taskmanagement.service.CategoryService;
import com.example.taskmanagement.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "http://localhost:8080")
public class TaskController {
    @Autowired
    TaskService taskservice;
    @Autowired
    CategoryService categoryService;

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Map<String, Object> taskData) {
        Task task = new Task();
        task.setTitle((String) taskData.get("title"));
        task.setStatus((String) taskData.get("status"));
        Object categoryIdObj = taskData.get("categoryId");
        if (categoryIdObj != null) {
            Long categoryId = Long.parseLong(categoryIdObj.toString());
            Category category = categoryService.getCategoryById(categoryId)
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            task.setCategory(category);
        }
        Task createdTask = taskservice.createTask(task);
        return ResponseEntity.ok(createdTask);
    }

    @GetMapping
    public List<Task> getAllTasks() {
        return taskservice.getAllTasks();
    }

    @DeleteMapping("/{id}")
    public void deleteTaskById(@PathVariable Long id) {
        taskservice.deleteTaskById(id);
    }

    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Long id, @RequestBody Map<String, Object> taskData) {
        Task updatedTask = new Task();
        updatedTask.setTitle((String) taskData.get("title"));
        updatedTask.setStatus((String) taskData.get("status"));
        Object categoryIdObj = taskData.get("categoryId");
        if (categoryIdObj != null) {
            Long categoryId = Long.parseLong(categoryIdObj.toString());
            Category category = categoryService.getCategoryById(categoryId)
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            updatedTask.setCategory(category);
        }
        return taskservice.updateTask(id, updatedTask);
    }
}
/*
package com.example.taskmanagement.controller;

import com.example.taskmanagement.model.Category;
import com.example.taskmanagement.model.Task;
import com.example.taskmanagement.service.CategoryService;
import com.example.taskmanagement.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "http://localhost:8080")
public class TaskController {
    @Autowired
    TaskService taskservice;
    @Autowired
    CategoryService categoryService;
    @PostMapping("/{categoryId}")
    public ResponseEntity<Task> createTask(@RequestBody Task task, @PathVariable Long categoryId)
    {
        // Implement the logic to create a task
        // You can use the categoryId to set the category for the task
        // For example:
        // Category category = categoryService.getCategoryById(categoryId);
        // task.setCategory(category);
        Category category = categoryService.getCategoryById(categoryId).orElseThrow(() -> new RuntimeException("Category not found"));
        task.setCategory(category);
        Task createdTask = taskservice.createTask(task);
        return ResponseEntity.ok(createdTask);
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
    @PutMapping("/{id}/category/{categoryId}")
    public Task updateTask(@PathVariable Long id, @PathVariable Long categoryId, @RequestBody Task updatedtask)
    {
        Category category = categoryService.getCategoryById(categoryId).orElseThrow(() -> new RuntimeException("Category not found"));
        updatedtask.setCategory(category);
        return taskservice.updateTask(id, updatedtask);
    }
}
*/
