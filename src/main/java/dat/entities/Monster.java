package dat.entities;

import dat.dtos.MonsterDTO;
import dat.entities.subEntities.*;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "monster")
public class Monster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String index;
    private String name;
    private String size;
    private String type;
    private String subtype;
    private String alignment;

    private int armorClass;

    private int hitPoints;
    private String hitDice;
    private String hitPointsRoll;

    @Embedded
    private Speed speed;

    private int strength;
    private int dexterity;
    private int constitution;
    private int intelligence;
    private int wisdom;
    private int charisma;

    @ElementCollection
    private List<Proficiency> proficiencies;

    @ElementCollection
    private List<String> damageVulnerabilities;

    @ElementCollection
    private List<String> damageResistances;

    @ElementCollection
    private List<String> damageImmunities;

    @ElementCollection
    private List<String> conditionImmunities;

    @Embedded
    private Senses senses;

    private String languages;
    private double challengeRating;
    private int proficiencyBonus;
    private int xp;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "monster")
    private List<MonsterAction> actions;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "monster")
    private List<MonsterAbility> specialAbilities;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "monster")
    private List<MonsterAction> legendaryActions;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "monster")
    private List<MonsterAction> reactions;


    private String image;
    private String url;
    private String updatedAt;

    public Monster(MonsterDTO dto) {
        this.id = dto.getId();
        this.index = dto.getIndex();
        this.name = dto.getName();
        this.size = dto.getSize();
        this.type = dto.getType();
        this.subtype = dto.getSubtype();
        this.alignment = dto.getAlignment();
        this.armorClass = dto.getArmorClass();
        this.hitPoints = dto.getHitPoints();
        this.hitDice = dto.getHitDice();
        this.hitPointsRoll = dto.getHitPointsRoll();
        this.speed = dto.getSpeed();
        this.strength = dto.getStrength();
        this.dexterity = dto.getDexterity();
        this.constitution = dto.getConstitution();
        this.intelligence = dto.getIntelligence();
        this.wisdom = dto.getWisdom();
        this.charisma = dto.getCharisma();
        this.proficiencies = dto.getProficiencies();
        this.damageVulnerabilities = dto.getDamageVulnerabilities();
        this.damageResistances = dto.getDamageResistances();
        this.damageImmunities = dto.getDamageImmunities();
        this.conditionImmunities = dto.getConditionImmunities();
        this.senses = dto.getSenses();
        this.languages = dto.getLanguages();
        this.challengeRating = dto.getChallengeRating();
        this.proficiencyBonus = dto.getProficiencyBonus();
        this.xp = dto.getXp();
        this.specialAbilities = dto.getSpecialAbilities();
        this.actions = dto.getActions();
        this.legendaryActions = dto.getLegendaryActions();
        this.reactions = dto.getReactions();
        this.image = dto.getImage();
        this.url = dto.getUrl();
        this.updatedAt = dto.getUpdatedAt();
    }

}
