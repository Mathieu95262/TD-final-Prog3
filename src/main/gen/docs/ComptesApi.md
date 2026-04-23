# ComptesApi

All URIs are relative to *http://localhost:8080*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**creerCaisseCollectivite**](ComptesApi.md#creerCaisseCollectivite) | **POST** /collectivities/{collectiviteId}/comptes/caisse | D - Creer la caisse d&#39;une collectivite (une seule autorisee) |
| [**creerCaisseFederation**](ComptesApi.md#creerCaisseFederation) | **POST** /federation/comptes/caisse | D - Creer la caisse de la federation (une seule autorisee) |
| [**creerCompteBancaireCollectivite**](ComptesApi.md#creerCompteBancaireCollectivite) | **POST** /collectivities/{collectiviteId}/comptes/bancaire | D - Creer un compte bancaire pour une collectivite |
| [**creerCompteBancaireFederation**](ComptesApi.md#creerCompteBancaireFederation) | **POST** /federation/comptes/bancaire | D - Creer un compte bancaire pour la federation |
| [**creerCompteMobileMoneyCollectivite**](ComptesApi.md#creerCompteMobileMoneyCollectivite) | **POST** /collectivities/{collectiviteId}/comptes/mobile-money | D - Creer un compte mobile money pour une collectivite |
| [**creerCompteMobileMoneyFederation**](ComptesApi.md#creerCompteMobileMoneyFederation) | **POST** /federation/comptes/mobile-money | D - Creer un compte mobile money pour la federation |
| [**getCompte**](ComptesApi.md#getCompte) | **GET** /comptes/{id} | Recuperer un compte par ID |
| [**getComptesCollectivite**](ComptesApi.md#getComptesCollectivite) | **GET** /collectivities/{collectiviteId}/comptes | Lister les comptes d&#39;une collectivite |
| [**getComptesFederation**](ComptesApi.md#getComptesFederation) | **GET** /federation/comptes | Lister les comptes de la federation |


<a id="creerCaisseCollectivite"></a>
# **creerCaisseCollectivite**
> CompteResponse creerCaisseCollectivite(collectiviteId, createCaisseRequest)

D - Creer la caisse d&#39;une collectivite (une seule autorisee)

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ComptesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080");

    ComptesApi apiInstance = new ComptesApi(defaultClient);
    Long collectiviteId = 56L; // Long | 
    CreateCaisseRequest createCaisseRequest = new CreateCaisseRequest(); // CreateCaisseRequest | 
    try {
      CompteResponse result = apiInstance.creerCaisseCollectivite(collectiviteId, createCaisseRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ComptesApi#creerCaisseCollectivite");
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
| **createCaisseRequest** | [**CreateCaisseRequest**](CreateCaisseRequest.md)|  | |

### Return type

[**CompteResponse**](CompteResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Caisse creee |  -  |
| **404** | Ressource introuvable |  -  |
| **409** | Conflit - ressource deja existante ou valeur immuable |  -  |

<a id="creerCaisseFederation"></a>
# **creerCaisseFederation**
> CompteResponse creerCaisseFederation(createCaisseRequest)

D - Creer la caisse de la federation (une seule autorisee)

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ComptesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080");

    ComptesApi apiInstance = new ComptesApi(defaultClient);
    CreateCaisseRequest createCaisseRequest = new CreateCaisseRequest(); // CreateCaisseRequest | 
    try {
      CompteResponse result = apiInstance.creerCaisseFederation(createCaisseRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ComptesApi#creerCaisseFederation");
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
| **createCaisseRequest** | [**CreateCaisseRequest**](CreateCaisseRequest.md)|  | |

### Return type

[**CompteResponse**](CompteResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Caisse creee |  -  |
| **409** | Conflit - ressource deja existante ou valeur immuable |  -  |

<a id="creerCompteBancaireCollectivite"></a>
# **creerCompteBancaireCollectivite**
> CompteResponse creerCompteBancaireCollectivite(collectiviteId, createCompteBancaireRequest)

D - Creer un compte bancaire pour une collectivite

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ComptesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080");

    ComptesApi apiInstance = new ComptesApi(defaultClient);
    Long collectiviteId = 56L; // Long | 
    CreateCompteBancaireRequest createCompteBancaireRequest = new CreateCompteBancaireRequest(); // CreateCompteBancaireRequest | 
    try {
      CompteResponse result = apiInstance.creerCompteBancaireCollectivite(collectiviteId, createCompteBancaireRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ComptesApi#creerCompteBancaireCollectivite");
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
| **createCompteBancaireRequest** | [**CreateCompteBancaireRequest**](CreateCompteBancaireRequest.md)|  | |

### Return type

[**CompteResponse**](CompteResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Compte bancaire cree |  -  |
| **400** | Donnees invalides |  -  |
| **404** | Ressource introuvable |  -  |
| **409** | Conflit - ressource deja existante ou valeur immuable |  -  |

<a id="creerCompteBancaireFederation"></a>
# **creerCompteBancaireFederation**
> CompteResponse creerCompteBancaireFederation(createCompteBancaireRequest)

D - Creer un compte bancaire pour la federation

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ComptesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080");

    ComptesApi apiInstance = new ComptesApi(defaultClient);
    CreateCompteBancaireRequest createCompteBancaireRequest = new CreateCompteBancaireRequest(); // CreateCompteBancaireRequest | 
    try {
      CompteResponse result = apiInstance.creerCompteBancaireFederation(createCompteBancaireRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ComptesApi#creerCompteBancaireFederation");
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
| **createCompteBancaireRequest** | [**CreateCompteBancaireRequest**](CreateCompteBancaireRequest.md)|  | |

### Return type

[**CompteResponse**](CompteResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Compte bancaire cree |  -  |
| **400** | Donnees invalides |  -  |
| **409** | Conflit - ressource deja existante ou valeur immuable |  -  |

<a id="creerCompteMobileMoneyCollectivite"></a>
# **creerCompteMobileMoneyCollectivite**
> CompteResponse creerCompteMobileMoneyCollectivite(collectiviteId, createCompteMobileMoneyRequest)

D - Creer un compte mobile money pour une collectivite

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ComptesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080");

    ComptesApi apiInstance = new ComptesApi(defaultClient);
    Long collectiviteId = 56L; // Long | 
    CreateCompteMobileMoneyRequest createCompteMobileMoneyRequest = new CreateCompteMobileMoneyRequest(); // CreateCompteMobileMoneyRequest | 
    try {
      CompteResponse result = apiInstance.creerCompteMobileMoneyCollectivite(collectiviteId, createCompteMobileMoneyRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ComptesApi#creerCompteMobileMoneyCollectivite");
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
| **createCompteMobileMoneyRequest** | [**CreateCompteMobileMoneyRequest**](CreateCompteMobileMoneyRequest.md)|  | |

### Return type

[**CompteResponse**](CompteResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Compte mobile money cree |  -  |
| **400** | Donnees invalides |  -  |
| **404** | Ressource introuvable |  -  |
| **409** | Conflit - ressource deja existante ou valeur immuable |  -  |

<a id="creerCompteMobileMoneyFederation"></a>
# **creerCompteMobileMoneyFederation**
> CompteResponse creerCompteMobileMoneyFederation(createCompteMobileMoneyRequest)

D - Creer un compte mobile money pour la federation

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ComptesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080");

    ComptesApi apiInstance = new ComptesApi(defaultClient);
    CreateCompteMobileMoneyRequest createCompteMobileMoneyRequest = new CreateCompteMobileMoneyRequest(); // CreateCompteMobileMoneyRequest | 
    try {
      CompteResponse result = apiInstance.creerCompteMobileMoneyFederation(createCompteMobileMoneyRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ComptesApi#creerCompteMobileMoneyFederation");
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
| **createCompteMobileMoneyRequest** | [**CreateCompteMobileMoneyRequest**](CreateCompteMobileMoneyRequest.md)|  | |

### Return type

[**CompteResponse**](CompteResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Compte mobile money cree |  -  |
| **400** | Donnees invalides |  -  |
| **409** | Conflit - ressource deja existante ou valeur immuable |  -  |

<a id="getCompte"></a>
# **getCompte**
> CompteResponse getCompte(id)

Recuperer un compte par ID

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ComptesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080");

    ComptesApi apiInstance = new ComptesApi(defaultClient);
    Long id = 56L; // Long | 
    try {
      CompteResponse result = apiInstance.getCompte(id);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ComptesApi#getCompte");
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

[**CompteResponse**](CompteResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Compte trouve |  -  |
| **404** | Ressource introuvable |  -  |

<a id="getComptesCollectivite"></a>
# **getComptesCollectivite**
> List&lt;CompteResponse&gt; getComptesCollectivite(collectiviteId)

Lister les comptes d&#39;une collectivite

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ComptesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080");

    ComptesApi apiInstance = new ComptesApi(defaultClient);
    Long collectiviteId = 56L; // Long | 
    try {
      List<CompteResponse> result = apiInstance.getComptesCollectivite(collectiviteId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ComptesApi#getComptesCollectivite");
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

[**List&lt;CompteResponse&gt;**](CompteResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Liste des comptes |  -  |
| **404** | Ressource introuvable |  -  |

<a id="getComptesFederation"></a>
# **getComptesFederation**
> List&lt;CompteResponse&gt; getComptesFederation()

Lister les comptes de la federation

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ComptesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080");

    ComptesApi apiInstance = new ComptesApi(defaultClient);
    try {
      List<CompteResponse> result = apiInstance.getComptesFederation();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ComptesApi#getComptesFederation");
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

[**List&lt;CompteResponse&gt;**](CompteResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Liste des comptes de la federation |  -  |

