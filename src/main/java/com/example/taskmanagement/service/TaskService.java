package com.example.taskmanagement.service;

import com.example.taskmanagement.model.Task;
import com.example.taskmanagement.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private CategoryService categoryService;

    public Task createTask(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("Task cannot be null");
        }
        if (task.getTitle() == null || task.getTitle().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }
        if (task.getStatus() == null || task.getStatus().isEmpty()) {
            throw new IllegalArgumentException("Status cannot be empty");
        }
        if (task.getCategory() != null && task.getCategory().getId() != null) {
            categoryService.getCategoryById(task.getCategory().getId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
        }
        return taskRepository.save(task);
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public void deleteTaskById(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new RuntimeException("Task not found");
        }
        taskRepository.deleteById(id);
    }

    public Task updateTask(Long id, Task updatedTask) {
        Optional<Task> existingTask = taskRepository.findById(id);
        if (existingTask.isPresent()) {
            Task task = existingTask.get();
            if (updatedTask.getTitle() == null || updatedTask.getTitle().isEmpty()) {
                throw new IllegalArgumentException("Title cannot be empty");
            }
            if (updatedTask.getStatus() == null || updatedTask.getStatus().isEmpty()) {
                throw new IllegalArgumentException("Status cannot be empty");
            }
            task.setTitle(updatedTask.getTitle());
            task.setStatus(updatedTask.getStatus());
            task.setCategory(updatedTask.getCategory());
            task.setDueDate(updatedTask.getDueDate());
            return taskRepository.save(task);
        } else {
            throw new RuntimeException("Task not found");
        }
    }
}