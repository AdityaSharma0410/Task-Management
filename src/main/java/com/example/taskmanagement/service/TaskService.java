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
    TaskRepository taskrepository;
    // Add methods to interact with the TaskRepository here
    public Task createTask(Task task) {
        return taskrepository.save(task);
    }
    public List<Task> getAllTasks() {
        return taskrepository.findAll();
    }
    public void deleteTaskById(Long id) {
        Task task = taskrepository.findById(id).orElse(null);
        if (task != null) {
            taskrepository.delete(task);
        }
    }
    public Task updateTask(Long id, Task updatedtask)
    {
        Optional<Task> existingTask = taskrepository.findById(id);
        if(existingTask.isPresent())
        {
            Task task = existingTask.get();
            task.setTitle(updatedtask.getTitle());
            task.setStatus(updatedtask.getStatus());
            return taskrepository.save(task);
        }
        else
        {
            return null;
        }

    }
}
