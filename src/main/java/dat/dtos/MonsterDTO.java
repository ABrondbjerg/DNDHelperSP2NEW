package dat.dtos;

import dat.entities.Monster;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MonsterDTO {

    private String name;
    private int armorClass;
    private int hitPoints;
    private String size;


    public MonsterDTO(Monster monster) {
    }
}
