# CotisationsApi

All URIs are relative to *http://localhost:8080*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**creerCotisation**](CotisationsApi.md#creerCotisation) | **POST** /collectivities/{collectiviteId}/cotisations | C - Creer une cotisation pour une collectivite |
| [**getCotisation**](CotisationsApi.md#getCotisation) | **GET** /collectivities/{collectiviteId}/cotisations/{cotisationId} | Recuperer une cotisation par ID |
| [**getCotisations**](CotisationsApi.md#getCotisations) | **GET** /collectivities/{collectiviteId}/cotisations | Lister les cotisations d&#39;une collectivite |
| [**toggleCotisationStatus**](CotisationsApi.md#toggleCotisationStatus) | **PATCH** /collectivities/{collectiviteId}/cotisations/{cotisationId}/toggle-status | Activer ou desactiver une cotisation |


<a id="creerCotisation"></a>
# **creerCotisation**
> CotisationResponse creerCotisation(collectiviteId, createCotisationRequest)

C - Creer une cotisation pour une collectivite

Les cotisations peuvent etre MENSUELLE, ANNUELLE ou PONCTUELLE. 

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.CotisationsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080");

    CotisationsApi apiInstance = new CotisationsApi(defaultClient);
    Long collectiviteId = 56L; // Long | 
    CreateCotisationRequest createCotisationRequest = new CreateCotisationRequest(); // CreateCotisationRequest | 
    try {
      CotisationResponse result = apiInstance.creerCotisation(collectiviteId, createCotisationRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling CotisationsApi#creerCotisation");
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
| **collectiviteId** | **Long**|  | |
| **createCotisationRequest** | [**CreateCotisationRequest**](CreateCotisationRequest.md)|  | |

### Return type

[**CotisationResponse**](CotisationResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Cotisation creee |  -  |
| **400** | Donnees invalides |  -  |
| **404** | Ressource introuvable |  -  |

<a id="getCotisation"></a>
# **getCotisation**
> CotisationResponse getCotisation(collectiviteId, cotisationId)

Recuperer une cotisation par ID

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.CotisationsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080");

    CotisationsApi apiInstance = new CotisationsApi(defaultClient);
    Long collectiviteId = 56L; // Long | 
    Long cotisationId = 56L; // Long | 
    try {
      CotisationResponse result = apiInstance.getCotisation(collectiviteId, cotisationId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling CotisationsApi#getCotisation");
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
| **collectiviteId** | **Long**|  | |
| **cotisationId** | **Long**|  | |

### Return type

[**CotisationResponse**](CotisationResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Cotisation trouvee |  -  |
| **404** | Ressource introuvable |  -  |

<a id="getCotisations"></a>
# **getCotisations**
> List&lt;CotisationResponse&gt; getCotisations(collectiviteId)

Lister les cotisations d&#39;une collectivite

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.CotisationsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080");

    CotisationsApi apiInstance = new CotisationsApi(defaultClient);
    Long collectiviteId = 56L; // Long | 
    try {
      List<CotisationResponse> result = apiInstance.getCotisations(collectiviteId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling CotisationsApi#getCotisations");
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
| **collectiviteId** | **Long**|  | |

### Return type

[**List&lt;CotisationResponse&gt;**](CotisationResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Liste des cotisations |  -  |
| **404** | Ressource introuvable |  -  |

<a id="toggleCotisationStatus"></a>
# **toggleCotisationStatus**
> CotisationResponse toggleCotisationStatus(collectiviteId, cotisationId)

Activer ou desactiver une cotisation

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.CotisationsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080");

    CotisationsApi apiInstance = new CotisationsApi(defaultClient);
    Long collectiviteId = 56L; // Long | 
    Long cotisationId = 56L; // Long | 
    try {
      CotisationResponse result = apiInstance.toggleCotisationStatus(collectiviteId, cotisationId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling CotisationsApi#toggleCotisationStatus");
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
| **collectiviteId** | **Long**|  | |
| **cotisationId** | **Long**|  | |

### Return type

[**CotisationResponse**](CotisationResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Statut mis a jour |  -  |
| **404** | Ressource introuvable |  -  |

