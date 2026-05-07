package mg.prog3.federation.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Presence {
    private Long id;
    private Long activiteId;
    private Long membreId;
    private boolean present;
    private boolean excuse;
    private String motif;
}