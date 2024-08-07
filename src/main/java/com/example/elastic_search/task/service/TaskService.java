package com.example.elastic_search.task.service;

import com.example.elastic_search.task.document.TaskDocument;
import com.example.elastic_search.task.dto.TaskDto;
import com.example.elastic_search.task.entity.Task;
import com.example.elastic_search.task.repository.entity.TaskRepository;
import com.example.elastic_search.task.repository.es.TaskSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskSearchRepository taskSearchRepository;

    @Transactional(readOnly = true)
    public Map<String, Object> searchTitle(String title){
        // elasticsearch 검색
        SearchHits<TaskDocument> searchHits = taskSearchRepository.findByTitle(title);

        // 리턴할 결과 Map 객체
        Map<String,Object> result = new HashMap<>();

        // 결과 개수
        result.put("count", searchHits.getTotalHits());

        // 결과 컨텐츠
        List<TaskDto> taskDtoList = new ArrayList<>();
        for(SearchHit<TaskDocument> hit : searchHits){
            TaskDocument taskDocument = hit.getContent(); // 검색 결과에서 TaskDocument 가져오기
            TaskDto taskDto = TaskDocument.toEntity(taskDocument); // TaskDocument를 TaskDto로 변환
            taskDtoList.add(taskDto); // 변환된 TaskDto를 리스트에 추가
        }
        result.put("data", taskDtoList);

        return result;
    }

    @Transactional(readOnly = true)
    public List<TaskDto> getTasks() {
        return taskRepository.findAll().stream()
                .map(TaskDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public TaskDto createTask(TaskDto taskDto) {
        // DB에 저장
        Task task = TaskDto.toEntity(taskDto);

        // Elasticsearch에 인덱싱
        TaskDocument taskDocument = TaskDocument.builder()
                .id(taskDto.getId())
                .title(taskDto.getTitle())
                .description(taskDto.getDescription())
                .createdAt(taskDto.getCreatedAt())
                .build();
        taskSearchRepository.save(taskDocument);

        return TaskDto.fromEntity(taskRepository.save(task));
    }

    @Transactional
    public TaskDto updateTask(Long taskId, TaskDto taskDto) {
        Task existingTask = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("할 일을 찾을 수 없습니다. id = " + taskId));
        Task updatedTask = Task.builder()
                .id(existingTask.getId())
                .title(taskDto.getTitle())
                .description(taskDto.getDescription())
                .createdAt(existingTask.getCreatedAt())
                .build();
        return TaskDto.fromEntity(taskRepository.save(updatedTask));
    }

    @Transactional
    public void deleteTask(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("할 일을 찾을 수 없습니다. id = " + taskId));
        taskRepository.delete(task);
    }

}
