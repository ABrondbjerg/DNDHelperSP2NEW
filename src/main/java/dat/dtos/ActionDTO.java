package dat.dtos;

public class ActionDTO {
    private String name;
    private String description;
    private String damageDice;

    public ActionDTO(String name, String description, String damageDice) {
        this.name = name;
        this.description = description;
        this.damageDice = damageDice;
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getDamageDice() { return damageDice; }
}