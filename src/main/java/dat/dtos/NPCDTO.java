package dat.dtos;

import dat.entities.NPC;
import dat.entities.NPC.RaceType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Data;

@Data
@Getter
@Setter
@NoArgsConstructor
public class NPCDTO {
    private Integer id;
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

    public NPCDTO(NPC npc){
        this.id = npc.getId();
        this.name = npc.getName();
        this.description = npc.getDescription();
        this.race = npc.getRace();
        this.professions = npc.getProfessions();
    }
}
