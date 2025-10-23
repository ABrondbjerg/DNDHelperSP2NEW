package dat.entities.subEntities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class MonsterAction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(length = 2000)
    private String description;

    @JsonProperty("attack_bonus")
    private Integer attackBonus;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MonsterDamage> damage;

    @ManyToOne
    @JoinColumn(name="monster_id")
    private Monster monster;

    @ManyToOne
    @JoinColumn(name="ability_id")
    private MonsterAbility ability;

}
