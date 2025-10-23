package dat.entities.subEntities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dat.entities.Monster;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class MonsterAbility {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(length = 2000)
    private String description;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "ability")
    private List<MonsterAction> damage;

    @ManyToOne
    @JoinColumn(name = "monster_id")
    private Monster monster;


}
