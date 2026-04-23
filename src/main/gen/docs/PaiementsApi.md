# PaiementsApi

All URIs are relative to *http://localhost:8080*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**enregistrerPaiement**](PaiementsApi.md#enregistrerPaiement) | **POST** /collectivities/{collectiviteId}/paiements | C - Enregistrer un encaissement (le tresorier) |
| [**getPaiements**](PaiementsApi.md#getPaiements) | **GET** /collectivities/{collectiviteId}/paiements | C - Paiements d&#39;une collectivite sur une periode |
| [**getPaiementsMembre**](PaiementsApi.md#getPaiementsMembre) | **GET** /members/{membreId}/paiements | Historique des paiements d&#39;un membre |


<a id="enregistrerPaiement"></a>
# **enregistrerPaiement**
> PaiementResponse enregistrerPaiement(collectiviteId, createPaiementRequest)

C - Enregistrer un encaissement (le tresorier)

Le tresorier enregistre un paiement d&#39;un membre pour une cotisation. Trace : montant, date d&#39;encaissement, mode de paiement (ESPECE, VIREMENT_BANCAIRE, MOBILE_MONEY). 

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.PaiementsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080");

    PaiementsApi apiInstance = new PaiementsApi(defaultClient);
    Long collectiviteId = 56L; // Long | 
    CreatePaiementRequest createPaiementRequest = new CreatePaiementRequest(); // CreatePaiementRequest | 
    try {
      PaiementResponse result = apiInstance.enregistrerPaiement(collectiviteId, createPaiementRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling PaiementsApi#enregistrerPaiement");
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
| **createPaiementRequest** | [**CreatePaiementRequest**](CreatePaiementRequest.md)|  | |

### Return type

[**PaiementResponse**](PaiementResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Paiement enregistre |  -  |
| **400** | Donnees invalides |  -  |
| **404** | Ressource introuvable |  -  |
| **422** | Regle metier non respectee |  -  |

<a id="getPaiements"></a>
# **getPaiements**
> List&lt;PaiementResponse&gt; getPaiements(collectiviteId, debut, fin)

C - Paiements d&#39;une collectivite sur une periode

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.PaiementsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080");

    PaiementsApi apiInstance = new PaiementsApi(defaultClient);
    Long collectiviteId = 56L; // Long | 
    LocalDate debut = LocalDate.parse("2026-01-01"); // LocalDate | 
    LocalDate fin = LocalDate.parse("2026-12-31"); // LocalDate | 
    try {
      List<PaiementResponse> result = apiInstance.getPaiements(collectiviteId, debut, fin);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling PaiementsApi#getPaiements");
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
| **debut** | **LocalDate**|  | |
| **fin** | **LocalDate**|  | |

### Return type

[**List&lt;PaiementResponse&gt;**](PaiementResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Liste des paiements |  -  |
| **404** | Ressource introuvable |  -  |
| **422** | Regle metier non respectee |  -  |

<a id="getPaiementsMembre"></a>
# **getPaiementsMembre**
> List&lt;PaiementResponse&gt; getPaiementsMembre(membreId)

Historique des paiements d&#39;un membre

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.PaiementsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080");

    PaiementsApi apiInstance = new PaiementsApi(defaultClient);
    Long membreId = 56L; // Long | 
    try {
      List<PaiementResponse> result = apiInstance.getPaiementsMembre(membreId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling PaiementsApi#getPaiementsMembre");
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
| **membreId** | **Long**|  | |

### Return type

[**List&lt;PaiementResponse&gt;**](PaiementResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Liste des paiements |  -  |
| **404** | Ressource introuvable |  -  |

