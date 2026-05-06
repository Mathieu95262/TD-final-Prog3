package mg.prog3.federation.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Parrainage {
    private Long id;
    private Long parrainId;
    private String relation;
    private String candidatEmail;
}