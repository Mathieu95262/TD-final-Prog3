

# CreateMembreRequest


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**collectiviteId** | **Long** |  |  |
|**nom** | **String** |  |  |
|**prenom** | **String** |  |  |
|**dateNaissance** | **LocalDate** |  |  |
|**genre** | **Genre** |  |  |
|**adresse** | **String** |  |  |
|**metier** | **String** |  |  |
|**telephone** | **String** |  |  |
|**email** | **String** |  |  |
|**parrains** | [**List&lt;ParrainRequest&gt;**](ParrainRequest.md) |  |  |
|**montantPaye** | **Long** | 50 000 MGA frais adhesion + cotisations annuelles de la collectivite |  |
|**modePaiement** | [**ModePaiementEnum**](#ModePaiementEnum) |  |  |



## Enum: ModePaiementEnum

| Name | Value |
|---- | -----|
| VIREMENT_BANCAIRE | &quot;VIREMENT_BANCAIRE&quot; |
| MOBILE_MONEY | &quot;MOBILE_MONEY&quot; |



