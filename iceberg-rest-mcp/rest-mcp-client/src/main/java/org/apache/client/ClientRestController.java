package org.apache.client;

import io.modelcontextprotocol.client.McpSyncClient;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.mcp.SyncMcpToolCallbackProvider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rest-catalog")
public class ClientRestController {
  private final ChatClient chatClient;


  public ClientRestController(ChatClient.Builder chatClientBuilder, List<McpSyncClient> mcpSyncClients) {
    this.chatClient = chatClientBuilder
        .defaultToolCallbacks(new SyncMcpToolCallbackProvider(mcpSyncClients))
        .build();
  }


  @GetMapping("/data")
  public String getAccount(@RequestParam("query") String name) {
    PromptTemplate pt = new PromptTemplate(name);
    return this.chatClient.prompt(pt.create())
        .call()
        .content();
  }

  @GetMapping("/status")
  public String getStatus() {
    return "Service is running with dummy data.";
  }

}
