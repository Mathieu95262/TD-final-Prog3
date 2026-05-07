package mg.prog3.federation.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CollectiviteStatistiqueResponse {
    private Long collectiviteId;
    private String collectiviteNom;
    private Long newMembersNumber;
    private Double overallMemberCurrentDuePercentage;
    private Double overallMemberAssiduityPercentage;
}