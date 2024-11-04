package com.example.springaidemo.config;

import org.springframework.ai.transformers.TransformersEmbeddingClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ai.embedding.EmbeddingClient;
import org.springframework.core.io.FileSystemResource;

@Configuration
public class EmbeddingClientConfig {
    @Bean("transformersEmbeddingClient")
    public EmbeddingClient embeddingClient() throws Exception {
        TransformersEmbeddingClient embeddingClient = new TransformersEmbeddingClient();
        embeddingClient.setTokenizerResource(new FileSystemResource("onnx-output-folder/model/tokenizer.json"));
        embeddingClient.setModelResource(new FileSystemResource("onnx-output-folder/model/model.onnx"));
        embeddingClient.setModelOutputName("token_embeddings");
        embeddingClient.afterPropertiesSet();
        return embeddingClient;
    }

}
