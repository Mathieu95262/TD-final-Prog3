# CollectivitesApi

All URIs are relative to *http://localhost:8080*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**assignerIdentite**](CollectivitesApi.md#assignerIdentite) | **PATCH** /collectivities/{id}/identity | J - Attribuer numero et nom unique a une collectivite |
| [**creerCollectivite**](CollectivitesApi.md#creerCollectivite) | **POST** /collectivities | A - Creer une nouvelle collectivite |
| [**getAllCollectivites**](CollectivitesApi.md#getAllCollectivites) | **GET** /collectivities | Lister toutes les collectivites |
| [**getCollectivite**](CollectivitesApi.md#getCollectivite) | **GET** /collectivities/{id} | Recuperer une collectivite par ID |


<a id="assignerIdentite"></a>
# **assignerIdentite**
> CollectiviteResponse assignerIdentite(id, assignIdentityRequest)

J - Attribuer numero et nom unique a une collectivite

Une fois le numero ou le nom attribue, il ne peut plus etre modifie. Toute tentative de modification retourne 409 Conflict. 

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.CollectivitesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080");

    CollectivitesApi apiInstance = new CollectivitesApi(defaultClient);
    Long id = 56L; // Long | 
    AssignIdentityRequest assignIdentityRequest = new AssignIdentityRequest(); // AssignIdentityRequest | 
    try {
      CollectiviteResponse result = apiInstance.assignerIdentite(id, assignIdentityRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling CollectivitesApi#assignerIdentite");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **id** | **Long**|  | |
| **assignIdentityRequest** | [**AssignIdentityRequest**](AssignIdentityRequest.md)|  | |

### Return type

[**CollectiviteResponse**](CollectiviteResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Identite attribuee |  -  |
| **400** | Donnees invalides |  -  |
| **404** | Ressource introuvable |  -  |
| **409** | Conflit - ressource deja existante ou valeur immuable |  -  |

<a id="creerCollectivite"></a>
# **creerCollectivite**
> CollectiviteResponse creerCollectivite(createCollectiviteRequest)

A - Creer une nouvelle collectivite

Conditions obligatoires : - Au moins 10 membres - Dont 5 avec &gt;&#x3D; 6 mois d&#39;anciennete dans la federation - President, president adjoint, tresorier et secretaire obligatoires - Numero et nom attribues separement via PATCH /collectivities/{id}/identity 

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.CollectivitesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080");

    CollectivitesApi apiInstance = new CollectivitesApi(defaultClient);
    CreateCollectiviteRequest createCollectiviteRequest = new CreateCollectiviteRequest(); // CreateCollectiviteRequest | 
    try {
      CollectiviteResponse result = apiInstance.creerCollectivite(createCollectiviteRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling CollectivitesApi#creerCollectivite");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **createCollectiviteRequest** | [**CreateCollectiviteRequest**](CreateCollectiviteRequest.md)|  | |

### Return type

[**CollectiviteResponse**](CollectiviteResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Collectivite creee |  -  |
| **400** | Donnees invalides |  -  |
| **409** | Conflit - ressource deja existante ou valeur immuable |  -  |
| **422** | Regle metier non respectee |  -  |

<a id="getAllCollectivites"></a>
# **getAllCollectivites**
> List&lt;CollectiviteResponse&gt; getAllCollectivites()

Lister toutes les collectivites

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.CollectivitesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080");

    CollectivitesApi apiInstance = new CollectivitesApi(defaultClient);
    try {
      List<CollectiviteResponse> result = apiInstance.getAllCollectivites();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling CollectivitesApi#getAllCollectivites");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;CollectiviteResponse&gt;**](CollectiviteResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Liste des collectivites |  -  |

<a id="getCollectivite"></a>
# **getCollectivite**
> CollectiviteResponse getCollectivite(id)

Recuperer une collectivite par ID

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.CollectivitesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080");

    CollectivitesApi apiInstance = new CollectivitesApi(defaultClient);
    Long id = 56L; // Long | 
    try {
      CollectiviteResponse result = apiInstance.getCollectivite(id);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling CollectivitesApi#getCollectivite");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **id** | **Long**|  | |

### Return type

[**CollectiviteResponse**](CollectiviteResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Collectivite trouvee |  -  |
| **404** | Ressource introuvable |  -  |

