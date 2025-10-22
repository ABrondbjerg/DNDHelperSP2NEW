package dat.entities;

//import app.dtos.PlaceholderDTO; // TODO: Ret import til korrekt DTO
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@Entity
public class NPC {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;
    @Enumerated(EnumType.STRING)
    private RaceType race;
    private String professions;

    public NPC(String name, String description,RaceType race,String professions) {
        this.name = name;
        this.description = description;
        this.race = race;
        this.professions = professions;
    }

    public enum RaceType {
        HUMAN,ELF,HALFELF,HALFLING,HALFORC,DWARF,GNOME,TIEFLING,DRAGONBORN
    }
}
