package dat.entities;

import dat.dtos.MonsterDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Monster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int armorClass;
    private int hitPoints;
    private String size;

    // Constructor used in DAO
    public Monster(String name, Integer armorClass, Integer hitPoints, String size) {
        this.name = name;
        this.armorClass = armorClass != null ? armorClass : 0;
        this.hitPoints = hitPoints != null ? hitPoints : 0;
        this.size = size;
    }


    // Optional: constructor for DTO conversion if needed
    public Monster(MonsterDTO dto) {
        this.name = dto.getName();
        this.armorClass = dto.getArmorClass();
        this.hitPoints = dto.getHitPoints();
        this.size = dto.getSize();
    }
}
