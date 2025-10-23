package dat.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dat.entities.Monster;
import dat.entities.subEntities.*; // Hvis du har subEntities i separate filer
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@NoArgsConstructor
public class MonsterDTO {

    private Integer id;
    private String index;
    private String name;
    private String size;
    private String type;
    private String subtype;
    private String alignment;
    @JsonProperty("armor_class")
    private List<ArmorClass> armorClass;
    @JsonProperty("hit_points")
    private int hitPoints;
    @JsonProperty("hit_dice")
    private String hitDice;
    @JsonProperty("hit_points_roll")
    private String hitPointsRoll;

    private Speed speed;

    private int strength;
    private int dexterity;
    private int constitution;
    private int intelligence;
    private int wisdom;
    private int charisma;

    private List<Proficiency> proficiencies;
    @JsonProperty("damage_vulnerabilities")
    private List<String> damageVulnerabilities;
    @JsonProperty("damage_resistances")
    private List<String> damageResistances;
    @JsonProperty("damage_immunities")
    private List<String> damageImmunities;
    @JsonProperty("condition-immunities")
    private List<String> conditionImmunities;

    private Senses senses;
    private String languages;
    @JsonProperty("challenge_rating")
    private double challengeRating;
    @JsonProperty("proficiency_bonus")
    private int proficiencyBonus;
    private int xp;
    @JsonProperty("special_abilities")
    private List<MonsterAbility> specialAbilities;
    private List<MonsterAction> actions;
    @JsonProperty("legendary_actions")
    private List<MonsterAction> legendaryActions;
    private List<MonsterAction> reactions;

    private String image;
    private String url;
    @JsonProperty("updated_at")
    private String updatedAt;


    public int getArmorClass() {
        return armorClass != null && !armorClass.isEmpty() ? armorClass.get(0).getValue() : 0;
    }

    // Constructor: Entity → DTO
    public MonsterDTO(Monster monster) {
        this.id = monster.getId();
        this.index = monster.getIndex();
        this.name = monster.getName();
        this.size = monster.getSize();
        this.type = monster.getType();
        this.subtype = monster.getSubtype();
        this.alignment = monster.getAlignment();
        this.armorClass = List.of(new ArmorClass(monster.getArmorClass()));
        this.hitPoints = monster.getHitPoints();
        this.hitDice = monster.getHitDice();
        this.hitPointsRoll = monster.getHitPointsRoll();
        this.speed = monster.getSpeed();
        this.strength = monster.getStrength();
        this.dexterity = monster.getDexterity();
        this.constitution = monster.getConstitution();
        this.intelligence = monster.getIntelligence();
        this.wisdom = monster.getWisdom();
        this.charisma = monster.getCharisma();
        this.proficiencies = monster.getProficiencies();
        this.damageVulnerabilities = monster.getDamageVulnerabilities();
        this.damageResistances = monster.getDamageResistances();
        this.damageImmunities = monster.getDamageImmunities();
        this.conditionImmunities = monster.getConditionImmunities();
        this.senses = monster.getSenses();
        this.languages = monster.getLanguages();
        this.challengeRating = monster.getChallengeRating();
        this.proficiencyBonus = monster.getProficiencyBonus();
        this.xp = monster.getXp();
        this.specialAbilities = monster.getSpecialAbilities();
        this.actions = monster.getActions();
        this.legendaryActions = monster.getLegendaryActions();
        this.reactions = monster.getReactions();
        this.image = monster.getImage();
        this.url = monster.getUrl();
        this.updatedAt = monster.getUpdatedAt();
    }

    // Simpel konstruktør, hvis man vil oprette DTO manuelt
    public MonsterDTO(String name, String type, int hitPoints) {
        this.name = name;
        this.type = type;
        this.hitPoints = hitPoints;
    }

    // Liste-konvertering: Entity → DTO
    public static List<MonsterDTO> toDTOList(List<Monster> monsters) {
        return monsters.stream().map(MonsterDTO::new).collect(Collectors.toList());
    }
    // Konverterer DTO til Entity
    public Monster toEntity() {
        return Monster.builder()
                .index(this.index)
                .name(this.name)
                .size(this.size)
                .type(this.type)
                .subtype(this.subtype)
                .alignment(this.alignment)
                .armorClass(this.getArmorClass())
                .hitPoints(this.hitPoints)
                .hitDice(this.hitDice)
                .hitPointsRoll(this.hitPointsRoll)
                .speed(this.speed)
                .strength(this.strength)
                .dexterity(this.dexterity)
                .constitution(this.constitution)
                .intelligence(this.intelligence)
                .wisdom(this.wisdom)
                .charisma(this.charisma)
                .proficiencies(this.proficiencies)
                .damageVulnerabilities(this.damageVulnerabilities)
                .damageResistances(this.damageResistances)
                .damageImmunities(this.damageImmunities)
                .conditionImmunities(this.conditionImmunities)
                .senses(this.senses)
                .languages(this.languages)
                .challengeRating(this.challengeRating)
                .proficiencyBonus(this.proficiencyBonus)
                .xp(this.xp)
                .specialAbilities(this.specialAbilities)
                .actions(this.actions)
                .legendaryActions(this.legendaryActions)
                .reactions(this.reactions)
                .image(this.image)
                .url(this.url)
                .updatedAt(this.updatedAt)
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MonsterDTO dto)) return false;
        return getId() != null && getId().equals(dto.getId());
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }
}
