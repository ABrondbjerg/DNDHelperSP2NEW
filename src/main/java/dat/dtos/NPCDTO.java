package dat.dtos;

import dat.entities.NPC.RaceType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NPCDTO {
    private String name;
    private String description;
    private RaceType race;
    private String professions;

    public NPCDTO(String name, String description, RaceType race, String professions) {
        this.name = name;
        this.description = description;
        this.race = race;
        this.professions = professions;
    }
}
