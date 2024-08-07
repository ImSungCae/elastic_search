package com.example.elastic_search.task.repository.entity;

import com.example.elastic_search.task.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
