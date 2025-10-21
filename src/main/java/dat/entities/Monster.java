package dat.entities;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;


@Getter
@NoArgsConstructor
@Entity
public class Monster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private int armorClass;
    private int hitPoints;
    private String description;
    private String size;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "monster_action",
            joinColumns = @JoinColumn(name = "monster_id"),
            inverseJoinColumns = @JoinColumn(name = "action_id")
    )
    private Set<Action> actions = new HashSet<>();

    public Monster(String name, int armorClass, int hitPoints, String description, String size) {
        this.name = name;
        this.armorClass = armorClass;
        this.hitPoints = hitPoints;
        this.description = description;
        this.size = size;
    }
}
