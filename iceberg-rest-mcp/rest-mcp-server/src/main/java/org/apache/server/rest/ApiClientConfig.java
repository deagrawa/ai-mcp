package org.apache.server.rest;

import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@Configuration
public class ApiClientConfig {

  @Value("${api.auth.enabled:false}")
  private boolean authEnabled;

  @Value("${api.auth.token:}")
  private String bearerToken;

  @Bean
  public WebClient apiWebClient(WebClient.Builder builder, ConfigProvider configProvider) {
    if (!authEnabled) {
      return builder.build();
    }
    try {
      /*
        This implementation is not suitable for production environments due to potential security vulnerabilities.
        It is intended solely for use in controlled development or testing environments.
       */
      SslContext sslContext =
          SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE).build();

      // 2. Create an HttpClient from Reactor Netty that uses the insecure SSLContext.
      HttpClient httpClient =
          HttpClient.create().secure(sslContextSpec -> sslContextSpec.sslContext(sslContext));

      // 3. Create a ReactorClientHttpConnector using the configured HttpClient.
      // This connector will be used by WebClient to make HTTP requests.
      ReactorClientHttpConnector connector = new ReactorClientHttpConnector(httpClient);

      // 4. Build and return the WebClient using the connector.
      return WebClient.builder()
          .clientConnector(connector)
          .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + configProvider.getBearerToken())
          .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded")
          .build();
    } catch (Exception e) {
      throw new RuntimeException("Failed to create insecure WebClient", e);
    }
  }

  @Bean
  public WebClient.Builder webClientBuilder() {
    return WebClient.builder();
  }
}
