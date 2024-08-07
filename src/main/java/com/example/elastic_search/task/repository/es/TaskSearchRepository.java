package com.example.elastic_search.task.repository.es;

import com.example.elastic_search.task.document.TaskDocument;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface TaskSearchRepository extends ElasticsearchRepository<TaskDocument,Long> {
    SearchHits<TaskDocument> findByTitle(String title);
}
