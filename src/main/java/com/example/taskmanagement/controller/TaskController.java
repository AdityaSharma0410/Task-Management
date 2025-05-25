package com.example.taskmanagement.controller;

import com.example.taskmanagement.model.Category;
import com.example.taskmanagement.model.Task;
import com.example.taskmanagement.service.CategoryService;
import com.example.taskmanagement.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "http://localhost:8080")
public class TaskController {
    @Autowired
    private TaskService taskService;
    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<?> createTask(@RequestBody Map<String, Object> taskData) {
        try {
            Task task = new Task();
            task.setTitle((String) taskData.get("title"));
            if (task.getTitle() == null || task.getTitle().isEmpty()) {
                return ResponseEntity.badRequest().body("Title is required");
            }
            task.setStatus((String) taskData.get("status"));
            if (task.getStatus() == null || task.getStatus().isEmpty()) {
                return ResponseEntity.badRequest().body("Status is required");
            }
            Object categoryIdObj = taskData.get("categoryId");
            if (categoryIdObj != null) {
                try {
                    Long categoryId = Long.parseLong(categoryIdObj.toString());
                    Category category = categoryService.getCategoryById(categoryId)
                            .orElseThrow(() -> new RuntimeException("Category not found"));
                    task.setCategory(category);
                } catch (NumberFormatException e) {
                    return ResponseEntity.badRequest().body("Invalid categoryId");
                }
            }
            Object dueDateObj = taskData.get("dueDate");
            if (dueDateObj != null && !dueDateObj.toString().isEmpty()) {
                try {
                    task.setDueDate(LocalDate.parse(dueDateObj.toString()));
                } catch (Exception e) {
                    return ResponseEntity.badRequest().body("Invalid dueDate format. Use YYYY-MM-DD");
                }
            }
            Task createdTask = taskService.createTask(task);
            return ResponseEntity.ok(createdTask);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating task");
        }
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTaskById(@PathVariable Long id) {
        try {
            taskService.deleteTaskById(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTask(@PathVariable Long id, @RequestBody Map<String, Object> taskData) {
        try {
            Task task = new Task();
            task.setTitle((String) taskData.get("title"));
            if (task.getTitle() == null || task.getTitle().isEmpty()) {
                return ResponseEntity.badRequest().body("Title is required");
            }
            task.setStatus((String) taskData.get("status"));
            if (task.getStatus() == null || task.getStatus().isEmpty()) {
                return ResponseEntity.badRequest().body("Status is required");
            }
            Object categoryIdObj = taskData.get("categoryId");
            if (categoryIdObj != null) {
                try {
                    Long categoryId = Long.parseLong(categoryIdObj.toString());
                    Category category = categoryService.getCategoryById(categoryId)
                            .orElseThrow(() -> new RuntimeException("Category not found"));
                    task.setCategory(category);
                } catch (NumberFormatException e) {
                    return ResponseEntity.badRequest().body("Invalid categoryId");
                }
            }
            Object dueDateObj = taskData.get("dueDate");
            if (dueDateObj != null && !dueDateObj.toString().isEmpty()) {
                try {
                    task.setDueDate(LocalDate.parse(dueDateObj.toString()));
                } catch (Exception e) {
                    return ResponseEntity.badRequest().body("Invalid dueDate format. Use YYYY-MM-DD");
                }
            }
            Task updatedTask = taskService.updateTask(id, task);
            if (updatedTask == null) {
                return ResponseEntity.badRequest().body("Task not found");
            }
            return ResponseEntity.ok(updatedTask);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating task");
        }
    }
}