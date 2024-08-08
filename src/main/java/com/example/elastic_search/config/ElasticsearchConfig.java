package com.example.elastic_search.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;

@Configuration
public class ElasticsearchConfig extends ElasticsearchConfiguration {
    @Override
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder()
                .connectedTo("localhost:9200")
                .withConnectTimeout(120000)  // 커넥터 유지 시간
                .withSocketTimeout(120000)
                .build();
    }
}