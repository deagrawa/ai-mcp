package org.apache.server.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConfigProvider {
  @Value("${api.auth.token:}")
  private String token;

  @Value("${api.base-uri:}")
  private String baseUri;

  public String getBearerToken() {
    if (token != null && !token.isBlank()) {
      return token;
    }

    String envToken = System.getenv("AUTH_TOKEN");
    if (envToken != null && !envToken.isBlank()) {
      return envToken;
    }

    throw new IllegalStateException("No Bearer token provided in properties or environment variables");
  }

  public String getBaseUri() {
    if (baseUri != null && !baseUri.isBlank()) {
      return baseUri;
    }

    String envBaseUri = System.getenv("BASE_URI");
    if (envBaseUri != null && !envBaseUri.isBlank()) {
      return envBaseUri;
    }

    throw new IllegalStateException("No base uri provided in properties or environment variables");
  }
}
