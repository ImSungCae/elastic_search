package com.example.elastic_search.task.controller;

import com.example.elastic_search.task.dto.TaskDto;
import com.example.elastic_search.task.service.TaskService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/task")
@Tag(name = "Task API")
public class TaskController {
    private final TaskService taskService;

    @GetMapping("/{title}")
    public ResponseEntity<Map<String,Object>> search(@PathVariable String title){
        return ResponseEntity.ok(taskService.searchTitle(title));
    }

    // 사용자가 가진 모든 작업 목록을 반환합니다.
    @GetMapping
    public ResponseEntity<List<TaskDto>> getTasks() {
        List<TaskDto> tasks = taskService.getTasks();
        return ResponseEntity.ok(tasks);
    }

    // 사용자가 새로운 작업을 생성합니다.
    @PostMapping
    public ResponseEntity<TaskDto> createTask(@RequestBody TaskDto taskDto) {
        TaskDto createdTask = taskService.createTask(taskDto);
        return ResponseEntity.ok(createdTask);
    }

    // 사용자가 작업을 수정합니다.
    @PutMapping("/{id}")
    public ResponseEntity<TaskDto> updateTask( @PathVariable Long id, @RequestBody TaskDto taskDto) {
        TaskDto updatedTask = taskService.updateTask(id, taskDto);
        return ResponseEntity.ok(updatedTask);
    }

    // 사용자가 작업을 삭제합니다.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask( @PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
