package com.example.elastic_search.task.document;

import com.example.elastic_search.task.dto.TaskDto;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Setting;

import java.time.LocalDateTime;

@Getter
//@Document(indexName = "tasks")
@Setting(replicas = 0)
@Builder
public class TaskDocument {

    @Id
    private Long id;
    private String title;
    private String description;
    private LocalDateTime createdAt;

    public static TaskDocument fromDocument(TaskDto taskDto) {
        return TaskDocument.builder()
                .id(taskDto.getId())
                .title(taskDto.getTitle())
                .description(taskDto.getDescription())
                .createdAt(taskDto.getCreatedAt())
                .build();
    }

    public static TaskDto toEntity(TaskDocument taskDocument) {
        return TaskDto.builder()
                .id(taskDocument.getId())
                .title(taskDocument.getTitle())
                .description(taskDocument.getDescription())
                .createdAt(taskDocument.getCreatedAt())
                .build();
    }
}
