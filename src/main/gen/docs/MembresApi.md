# MembresApi

All URIs are relative to *http://localhost:8080*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**admettreMembre**](MembresApi.md#admettreMembre) | **POST** /members | B-2 - Admettre un nouveau membre |
| [**getMembre**](MembresApi.md#getMembre) | **GET** /members/{id} | Recuperer un membre par ID |
| [**getMembresByCollectivite**](MembresApi.md#getMembresByCollectivite) | **GET** /collectivities/{collectiviteId}/members | Lister les membres d&#39;une collectivite |


<a id="admettreMembre"></a>
# **admettreMembre**
> MembreResponse admettreMembre(createMembreRequest)

B-2 - Admettre un nouveau membre

Nouvelles conditions d&#39;admission : 1. Au moins 2 parrains confirmes (anciennete &gt; 90 jours) 2. Parrains de la collectivite cible &gt;&#x3D; parrains exterieurs 3. Montant &#x3D; 50 000 MGA (frais adhesion) + cotisations annuelles de la collectivite 4. Mode de paiement : VIREMENT_BANCAIRE ou MOBILE_MONEY uniquement 5. Nature de la relation avec chaque parrain obligatoire 

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.MembresApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080");

    MembresApi apiInstance = new MembresApi(defaultClient);
    CreateMembreRequest createMembreRequest = new CreateMembreRequest(); // CreateMembreRequest | 
    try {
      MembreResponse result = apiInstance.admettreMembre(createMembreRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling MembresApi#admettreMembre");
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
| **createMembreRequest** | [**CreateMembreRequest**](CreateMembreRequest.md)|  | |

### Return type

[**MembreResponse**](MembreResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Membre admis (poste MEMBRE_JUNIOR attribue automatiquement) |  -  |
| **400** | Donnees invalides |  -  |
| **404** | Ressource introuvable |  -  |
| **409** | Conflit - ressource deja existante ou valeur immuable |  -  |
| **422** | Regle metier non respectee |  -  |

<a id="getMembre"></a>
# **getMembre**
> MembreResponse getMembre(id)

Recuperer un membre par ID

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.MembresApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080");

    MembresApi apiInstance = new MembresApi(defaultClient);
    Long id = 56L; // Long | 
    try {
      MembreResponse result = apiInstance.getMembre(id);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling MembresApi#getMembre");
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

[**MembreResponse**](MembreResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Membre trouve |  -  |
| **404** | Ressource introuvable |  -  |

<a id="getMembresByCollectivite"></a>
# **getMembresByCollectivite**
> List&lt;MembreResponse&gt; getMembresByCollectivite(collectiviteId)

Lister les membres d&#39;une collectivite

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.MembresApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080");

    MembresApi apiInstance = new MembresApi(defaultClient);
    Long collectiviteId = 56L; // Long | 
    try {
      List<MembreResponse> result = apiInstance.getMembresByCollectivite(collectiviteId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling MembresApi#getMembresByCollectivite");
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

[**List&lt;MembreResponse&gt;**](MembreResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Liste des membres |  -  |
| **404** | Ressource introuvable |  -  |

