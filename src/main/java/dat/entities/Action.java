package dat.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Action {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String name;
    private String description;
    private String damageDice;

    public Action(String name, String description,String damageDice) {
        this.name = name;
        this.description = description;
        this.damageDice = damageDice;
    }
}
