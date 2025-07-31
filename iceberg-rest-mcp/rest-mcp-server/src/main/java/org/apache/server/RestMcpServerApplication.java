package org.apache.server;

import org.apache.server.tools.RestCatalogTools;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RestMcpServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(RestMcpServerApplication.class, args);
    }

  @Bean
  public ToolCallbackProvider weatherTools(RestCatalogTools restCatalogTools) {
    return MethodToolCallbackProvider.builder().toolObjects(restCatalogTools).build();
  }
}
