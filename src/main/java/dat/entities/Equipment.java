package dat.entities;

import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class Equipment {
    private String index;
    private String name;
    private String url;
}
