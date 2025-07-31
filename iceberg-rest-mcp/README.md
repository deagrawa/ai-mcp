# 📦 iceberg-rest-mcp

## Overview

`iceberg-rest-mcp` is a modular Java project developed using Java, Spring Boot AI, and Maven. It comprises two core components: the MCP server, which interfaces with the Apache Iceberg catalog via the Iceberg REST server, and the MCP client, which communicates with the MCP server. Together, they enable structured, secure, and intelligent interactions with the Apache Iceberg REST catalog.
This project consists of two primary sub-modules:

- `rest-mcp-server`: MCP server that connects to Iceberg catalog by Rest end points.
- `rest-mcp-client`: A lightweight client for querying and interacting with the server.

---

## 🔌 About MCP, Server, and Client

### 🛰 MCP (Model Context Protocol)
MCP (Model Context Protocol): A standard language for AI (especially LLMs) to securely interact with external systems and data, allowing them to use tools and access real-world information.

### 🌐 MCP Server - `rest-mcp-server`
MCP Server: The gateway that exposes external systems (databases, APIs, tools) to the AI in an MCP-compliant way, handling security and data translation.

### 🧩 MCP Client - `rest-mcp-client`
MCP Client: The AI's interface that sends requests to MCP Servers, discovers available capabilities, and receives responses, enabling the AI to "talk" to the outside world.

---

## 🛠️ Build Instructions

Follow these steps to build the project or its sub-modules.

### 1. Clone the repository

```bash
git clone <repository-url>
cd iceberg-rest-mcp
```

### 2. Build the entire project

```bash
mvn clean install
```

### 3. Build individual modules

```bash
# For rest-mcp-server
cd rest-mcp-server
mvn clean install

# For rest-mcp-client
cd ../rest-mcp-client
mvn clean install
```

---

## ⚙️ Configuration: `rest-mcp-server`

The `rest-mcp-server` must be configured to access the Iceberg REST Catalog. This is done via `application.properties` or environment variables.

### 🧾 Properties File

For non-secured environments:

```properties
api.base-uri=http://your-iceberg-rest-api-host
```

For secured environments:

```properties
api.base-uri=http://your-iceberg-rest-api-host
api.auth.token=your-auth-token
api.auth.enabled=true
```

### 🌍 Environment Variables

You can override configurations using environment variables:

| Variable     | Description                               |
|--------------|-------------------------------------------|
| `BASE_URI`   | Base URI for the Iceberg REST catalog     |
| `AUTH_TOKEN` | Bearer token for secured API access       |

---

## 🧪 Sample Prompts & API Commands

Below are example queries that can be made via the `rest-mcp-client` or `curl`.

### 📘 1. Retrieve All Namespaces

- **Prompt:**  
  *Retrieve the list of namespace names and return the response in the JSON format*

- **Curl Command:**

```bash
curl --location 'http://localhost:8040/rest-catalog/data?query=Retrieve the list of namespace names and return the response in the JSON format'
```

- **Sample Result:**

```
The list of namespace names from the current REST catalog are:
- default
- information_schema
- sys
```

---

### 📗 2. Load Iceberg Table

- **Prompt:**  
  *Load the iceberg table `test_tbl` from the namespace `default` and transform the result in the JSON format*

- **Curl Command:**

```bash
curl --location 'http://localhost:8040/rest-catalog/data?query=Load the iceberg table test_tbl from the namespace default and transform the result in the JSON format'
```

- **Sample Result (summary):**

```text
The `test_tbl` table from the `default` namespace has the following metadata:
- Location: s3a://hms-warehouse/warehouse/external/test_tbl
- Format version: 2
- Table UUID: 24608f55-4b2d-4751-8067-187a209865be
- Schema:
  - id: int
  - name: string
- No partition specs
```

- **JSON Output (truncated):**

```json
{
  "metadata": {
    "format-version": 2,
    "table-uuid": "24608f55-4b2d-4751-8067-187a209865be",
    "location": "s3a://hms-warehouse/warehouse/external/test_tbl",
    "schemas": [
      {
        "schema-id": 0,
        "type": "struct",
        "fields": [
          { "id": 1, "name": "id", "required": false, "type": "int" },
          { "id": 2, "name": "name", "required": false, "type": "string" }
        ]
      }
    ],
    "partition-specs": [],
    "properties": {
      "engine.hive.enabled": "true",
      "write.merge.mode": "merge-on-read",
      "EXTERNAL": "TRUE"
    }
  },
  "config": {
    "s3.access-key-id": "systest@ROOT.COMOPS.SITE",
    "s3.secret-access-key": "<REDACTED>",
    "client.region": "us-west-2"
  }
}
```

---

