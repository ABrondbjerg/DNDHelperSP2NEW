package dat.dtos;

import java.util.List;

public class MonsterDTO {
    private String name;
    private String type;
    private List<ActionDTO> actions;

    // Constructor
    public MonsterDTO(String name, String type, List<ActionDTO> actions) {
        this.name = name;
        this.type = type;
        this.actions = actions;
    }

    // Getters & Setters
    public String getName() { return name; }
    public String getType() { return type; }
    public List<ActionDTO> getActions() { return actions; }
}
