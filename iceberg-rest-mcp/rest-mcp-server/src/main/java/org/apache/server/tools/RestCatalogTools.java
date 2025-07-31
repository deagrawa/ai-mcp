package org.apache.server.tools;

import org.apache.server.rest.ApiService;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

@Service
public class RestCatalogTools {

  ApiService apiService;
  public RestCatalogTools(ApiService apiService) {
    this.apiService =  apiService;
  }

  /**
   * Fetch all the namespace name from the rest catalog
   * @return List of namespace names
   */
  @Tool(name = "Retrieve the list of namespace names from the current rest catalog", description = "Fetch all the namespace names from the current REST catalog.")
  public String getNamespaces(){
    return apiService.getNamespaces();
  }

  /**
   * Fetch all the namespace name from the rest catalog
   * @return List of namespace names
   */
  @Tool(name = "Retrieve the namespace detail from the current rest catalog", description = "Fetch namespace detail from the rest catalog for the given {namespace}.")
  public String getNamespace(@ToolParam(description = "Namespace Name") String namespace){
    return apiService.getNamespace(namespace);
  }

  /**
   * Fetch all the tables name from the rest catalog for a given namespace.
   * @param namespace namespace name
   * @return List of table names
   */
  @Tool(name = "Retrieve the list of table names from the current rest catalog", description = "Fetch all the table names for the given {namespace} from the current REST catalog.")
  public String getTables(@ToolParam(description = "Namespace Name") String namespace) {
    return apiService.listTables(namespace);
  }

  /**
   * Load tables from the rest catalog for a given namespace and table name.
   * @param namespace namespace name
   * @param table table name
   * @return table details
   */
  @Tool(name = "Retrieve the table detail from the rest catalog", description = "Retrieve the table detail from the rest catalog for a given {namespace} and {table}.")
  public String getTable(@ToolParam(description = "Namespace Name") String namespace, @ToolParam(description = "Table Name") String table) {
    return apiService.getTable(namespace, table);
  }

}
