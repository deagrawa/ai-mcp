package org.apache.server.rest;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Service class for interacting with an external API using WebClient.
 * <p>
 * This service provides methods to perform CRUD operations on namespaces and tables
 * in the external system. It uses a reactive WebClient for making HTTP requests.
 * The base URI for the API is provided by the {@link ConfigProvider}.
 * </p>
 */
@Service
public class ApiService {
  private final WebClient webClient;
  private final String baseUri;

  /**
   * Constructs an instance of {@code ApiService}.
   *
   * @param apiWebClient   the {@link WebClient} instance configured for API communication
   * @param configProvider the {@link ConfigProvider} providing configuration details such as the base URI
   */
  public ApiService(WebClient apiWebClient, ConfigProvider configProvider) {
    this.webClient = apiWebClient;
    this.baseUri = configProvider.getBaseUri();
  }

  /**
   * Retrieves a list of all namespaces from the external API.
   *
   * @return a JSON string containing the list of namespaces
   */
  public String getNamespaces() {
    return webClient.get().uri(baseUri + "/namespaces").retrieve().bodyToMono(String.class).block();
  }

  /**
   * Retrieves details of a specific namespace from the external API.
   *
   * @param namespace the name of the namespace to retrieve
   * @return a JSON string containing the details of the specified namespace
   */
  public String getNamespace(String namespace) {
    return webClient.get().uri(baseUri + "/namespaces/{namespace}", namespace).retrieve().bodyToMono(String.class)
        .block();
  }

  /**
   * Retrieves a list of all tables within a specific namespace from the external API.
   *
   * @param namespace the name of the namespace whose tables are to be listed
   * @return a JSON string containing the list of tables in the specified namespace
   */
  public String listTables(String namespace) {
    return webClient.get().uri(baseUri + "/namespaces/{namespace}/tables", namespace).retrieve()
        .bodyToMono(String.class).block();
  }

  /**
   * Retrieves details of a specific table within a namespace from the external API.
   *
   * @param namespace the name of the namespace containing the table
   * @param table     the name of the table to retrieve
   * @return a JSON string containing the details of the specified table
   */
  public String getTable(String namespace, String table) {
    return webClient.get().uri(baseUri + "/namespaces/{namespace}/tables/{table}", namespace, table).retrieve()
        .bodyToMono(String.class).block();
  }
}

